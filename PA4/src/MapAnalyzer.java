import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * This class represents main class of the MapAnalyzer program
 */
public class MapAnalyzer {

    public static String startingPoint; // the starting point of the map we will calculate
    public static String endPoint; // the end point of the map we will calculate
    public static ArrayList<Road> roads = new ArrayList<>(); // list holding all the roads on the map
    public static String[] lines; // lines in the file

    /**
     * The entry point of the application. This method initializes the program, processes the input file,
     * calculates routes, and writes the results to the output file.
     *
     * @param args the command line arguments. The first argument should be the input file name and the second
     *             argument should be the output file name.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage of this program: MapAnalyzer <input.txt> <output.txt>");
            return;
        }
        lines = Reader.readFile(args[0], true, true);
        Locale.setDefault(Locale.US);
        if (lines != null) {
            initializer(lines);
        } else {
            return;
        }
        // Starts the program
        FastestRoute fastestRoute = new FastestRoute();
        BarelyConnectedRoute barelyConnectedRoute = new BarelyConnectedRoute();
        writer(fastestRoute.fastestRoute, barelyConnectedRoute.barelyRoute, barelyConnectedRoute.barelyFastestRoadMap, args[1]);
    }

    /**
     * Initializes the road network from the provided input lines. The first line is used to set the starting
     * and ending points, and subsequent lines are used to create {@code Road} objects that are added to the
     * road network.
     *
     * @param lines the array of strings representing the lines from the input file. The first line contains
     *              the starting and ending points separated by a tab, and each subsequent line contains
     *              road data with points, length, and ID separated by tabs.
     */
    private static void initializer(String[] lines) {
        String[] firstPoints = lines[0].split("\t");
        startingPoint = firstPoints[0];
        endPoint = firstPoints[1];
        for (int i = 1; i < lines.length; i++) {
            String[] otherPoints = lines[i].split("\t");
            roads.add(new Road(otherPoints[0], otherPoints[1], Integer.parseInt(otherPoints[2]), Integer.parseInt(otherPoints[3])));
        }
    }

    /**
     * Writes the results of the analysis to the specified output file.
     *
     * @param fastestRoute       the HashMap representing the fastest route from starting point to end point
     * @param barelyConnectedRoute the ArrayList representing the roads of the barely connected map
     * @param fastestRouteOnBarely the HashMap representing the fastest route on the barely connected map
     * @param path               the path of the output file
     */
    private static void writer(HashMap<String, Road> fastestRoute,
                               ArrayList<Road> barelyConnectedRoute,
                               HashMap<String, Road> fastestRouteOnBarely,
                               String path) {
        // Calculate the length of the fastest route
        int fastestRouteLength = fastestRoute.get(endPoint).getLongestRoad();
        Writer.writeToFile(path, String.format("Fastest Route from %s to %s (%d KM):\n", startingPoint, endPoint, fastestRouteLength), true, false);
        for (Road road : fastestRoute.get(endPoint).getUsedRoads()) {
            Writer.writeToFile(path, road + "\n", true, false);
        }
        // Write the roads of the barely connected map to the file
        Writer.writeToFile(path, "Roads of Barely Connected Map is:\n", true, false);
        for (Road road : barelyConnectedRoute) {
            Writer.writeToFile(path, road + "\n", true, false);
        }
        // Calculate the length of the fastest route on the barely connected map
        int fastestRouteLengthOnBarely = 0;
        for (Road road : fastestRouteOnBarely.get(endPoint).getBarelyFastestUsedRoads()) {
            fastestRouteLengthOnBarely += road.getRoadLength();
        }
        // Write the fastest route on the barely connected map to the file
        Writer.writeToFile(path, String.format("Fastest Route from %s to %s on Barely Connected Map (%d KM):\n", startingPoint, endPoint, fastestRouteLengthOnBarely), true, false);
        for (Road road : fastestRouteOnBarely.get(endPoint).getBarelyFastestUsedRoads()) {
            Writer.writeToFile(path, road + "\n", true, false);
        }
        // Calculate the total construction material usage for both maps
        double constructionMaterialOriginal = 0;
        for (Road road : roads) {
            constructionMaterialOriginal += road.getRoadLength();
        }
        double constructionMaterialBarely = 0;
        for (Road road : barelyConnectedRoute) {
            constructionMaterialBarely += road.getRoadLength();
        }
        // Write the analysis results to the file
        Writer.writeToFile(path, "Analysis:\n", true, false);
        Writer.writeToFile(path, String.format("Ratio of Construction Material Usage Between Barely Connected and Original Map: %.2f\n",
                constructionMaterialBarely / constructionMaterialOriginal), true, false);
        Writer.writeToFile(path, String.format("Ratio of Fastest Route Between Barely Connected and Original Map: %.2f",
                (double) fastestRouteLengthOnBarely / (double) fastestRouteLength), true, false);
    }
}