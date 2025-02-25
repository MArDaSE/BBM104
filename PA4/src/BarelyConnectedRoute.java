import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * This class finds barely connected paths and calculates the shortest distance between two points on these paths.
 */
public class BarelyConnectedRoute {

    private String barelyStartingPoint; // The starting point of the barely connected road
    public ArrayList<Road> barelyRoute = new ArrayList<>(); // List of barely connected roads
    private ArrayList<Road> barelyRoads = new ArrayList<>(); // List used to calculate barely connected paths
    private ArrayList<String> cities = new ArrayList<>(); // Cities on the map
    private ArrayList<Road> barelyFastestRoad = new ArrayList<>(); // Shortest distance on barely connected roads
    public HashMap<String, Road> barelyFastestRoadMap = new HashMap<>(); // Map of cities on barely connected roads and their shortest distances

    /**
     * Initializes a route for a map with barely connected cities and roads.
     */
    public BarelyConnectedRoute() {
        // Add cities to the route based on map data
        addCities(MapAnalyzer.lines);
        // Add barely connected roads starting from a specific point
        addBarelyRoads(barelyStartingPoint);

        // Sort the barely connected roads
        ArrayList<Road> sortedBarelyRoad = sortBarelyRoute();
        // Clear the current route and add the sorted barely connected roads
        barelyRoute.clear();
        barelyRoute.addAll(sortedBarelyRoad);

        // Find the fastest road on this barely connected map
        fastestRoadOnBarelyMap();
    }

    /**
     * Adds barely connected roads starting from a specified city.
     *
     * @param cityName The name of the city from which barely connected roads should start.
     */
    private void addBarelyRoads(String cityName) {
        // If there are no cities, return without adding roads
        if (cities.isEmpty()) {
            return;
        }
        // Remove the specified city from the list of cities
        cities.remove(cityName);
        for (Road road : MapAnalyzer.roads) {
            // Check if the road connects to the specified city
            if (road.getPoints()[0].contentEquals(cityName) || road.getPoints()[1].contentEquals(cityName)) {
                // If the road hasn't already been added to barely roads
                if (!road.isBarelyIsAdded()) {
                    // Set the starting point of the barely connected road
                    road.setBarelyStartPoint(cityName);
                    for (String city : road.getPoints()) {
                        if (!city.contentEquals(cityName)) {
                            // Set the end point of the barely connected road
                            road.setBarelyEndPoint(city);
                            break;
                        }
                    }
                    // Mark the road as added to barely roads
                    road.setBarelyIsAdded(true);
                    // Add the road to the list of barely connected roads
                    barelyRoads.add(road);
                }
            }
        }
        // Find the smallest road among barely connected roads
        Road smallestRoad = barelySortRoad();
        // If no smallest road is found, return
        if (smallestRoad == null) {
            return;
        }
        // Recursively add more barely connected roads
        addBarelyMap(smallestRoad.getBarelyEndPoint(), smallestRoad);
    }

    /**
     * Adds barely connected roads to the route recursively starting from a specified city.
     *
     * @param cityName The name of the city from which barely connected roads should start.
     * @param road The road connected to the specified city.
     */
    private void addBarelyMap(String cityName, Road road) {
        // If the city is still in the list of cities
        if (cities.contains(cityName)) {
            // Add the road to the barely route
            barelyRoute.add(road);
            // Remove the road from the list of barely roads
            barelyRoads.remove(road);
            // Recursively add more barely connected roads
            addBarelyRoads(cityName);
        } else {
            // If the city is no longer in the list of cities, remove the road and continue adding barely connected roads
            barelyRoads.remove(road);
            addBarelyRoads(cityName);
        }

    }

    /**
     * Sorts barely connected roads based on their length and ID, and returns the road with the smallest length.
     *
     * @return The road with the smallest length, or null if no roads are present.
     */
    private Road barelySortRoad() {
        // Create lists to store road lengths
        ArrayList<Integer> sortedForLength = new ArrayList<>();
        for (Road road : barelyRoads) {
            sortedForLength.add(road.getRoadLength());
        }
        Collections.sort(sortedForLength); // Sort the list
        // Create lists to store road IDs
        ArrayList<Integer> sortedForID = new ArrayList<>();
        for (Road road : barelyRoads) {
            sortedForID.add(road.getRoadID());
        }
        Collections.sort(sortedForID); // Sort the list

        for (int i : sortedForID) {
            for (Road road : barelyRoads) {
                // Check if the road has the smallest length and matches the current ID
                if ((road.getRoadLength() == sortedForLength.get(0)) && (road.getRoadID() == i)) {
                    // Return the road with the smallest length
                    return road;
                }
            }
        }
        // Return null if no roads are present
        return null;
    }

    /**
     * Adds cities to the list of cities based on the provided map.
     *
     * @param lines An array of strings representing lines containing city information.
     */
    private void addCities(String[] lines) {
        for (int i = 1; i < lines.length; i++) {
            String[] otherPoints = lines[i].split("\t");
            if (!cities.contains(otherPoints[0])) {
                cities.add(otherPoints[0]);
            }
            if (!cities.contains(otherPoints[1])) {
                cities.add(otherPoints[1]);
            }
        }
        // Sort the list of cities alphabetically
        Collections.sort(cities);
        // Set the barely starting point to the first city in the sorted list
        barelyStartingPoint = cities.get(0);
    }

    /**
     * Finds the fastest road on the barely connected map.
     */
    private void fastestRoadOnBarelyMap() {
        // Add the fastest road on the barely connected map starting from starting point
        addFastestOnBarelyMap(MapAnalyzer.startingPoint);
    }

    /**
     * Adds the fastest road on the barely connected map starting from starting city.
     *
     * @param cityName The name of the city from which to start finding the fastest road.
     */
    private void addFastestOnBarelyMap(String cityName) {
        // Check if the fastest road map already contains the endpoint
        if (barelyFastestRoadMap.containsKey(MapAnalyzer.endPoint)) {
            return;
        }
        for (Road road : barelyRoute) {
            // Check if the road connects to the specified city
            if (road.getPoints()[0].contentEquals(cityName) || road.getPoints()[1].contentEquals(cityName)) {
                // If the road hasn't already been added to the fastest road map
                if (!road.isBarelyFastestIsAdded()) {
                    // Set the road as added to the fastest road map
                    road.setBarelyFastestIsAdded(true);
                    // Set the starting point of the road
                    road.setBarelyFastestStartPoint(cityName);
                    for (String city : road.getPoints()) {
                        if (!city.contentEquals(cityName)) {
                            // Set the end point of the road
                            road.setBarelyFastestEndPoint(city);
                        }
                    }
                    // Add the road to the list of fastest roads
                    barelyFastestRoad.add(road);
                }
            }
        }
        // Find the smallest road among the fastest roads
        Road smallestRoad = barelyFastestSortRoad();
        // If a smallest road is found, recursively add more fastest roads
        if (smallestRoad != null) {
            addBarelyFastestMap(smallestRoad.getBarelyFastestEndPoint(), smallestRoad);
        }
    }

    /**
     * Adds the fastest road on the barely connected map to the fastest road map, recursively updating the map.
     *
     * @param cityName The name of the city to add to the fastest road map.
     * @param road The fastest road connected to the specified city.
     */
    private void addBarelyFastestMap(String cityName, Road road) {
        // Check if the fastest road map doesn't contain the start point of the road
        if (!barelyFastestRoadMap.containsKey(road.getBarelyFastestStartPoint())) {
            // Add the road to the fastest road map
            road.setBarelyFastestUsedRoads(road);
            barelyFastestRoadMap.put(cityName, road);
            // Remove the road from the list of fastest roads
            barelyFastestRoad.remove(road);
            // Recursively add more fastest roads
            addFastestOnBarelyMap(cityName);
        } else {
            // If the fastest road map contains the start point of the road
            // Iterate through the used roads of the fastest road connected to the start point
            for (Road usedRoad : barelyFastestRoadMap.get(road.getBarelyFastestStartPoint()).getBarelyFastestUsedRoads()) {
                // Add the used road to the current road's used roads
                road.setBarelyFastestUsedRoads(usedRoad);
            }
            // Add the current road to its used roads
            road.setBarelyFastestUsedRoads(road);
            // Add the road to the fastest road map
            barelyFastestRoadMap.put(cityName, road);
            // Remove the road from the list of fastest roads
            barelyFastestRoad.remove(road);
            // Recursively add more fastest roads
            addFastestOnBarelyMap(cityName);
        }
    }

    /**
     * Sorts barely fastest roads based on their length and ID, and returns the road with the smallest length.
     *
     * @return The road with the smallest length, or null if no roads are present.
     */
    private Road barelyFastestSortRoad() {
        ArrayList<Integer> sortedForLength = new ArrayList<>();
        for (Road road : barelyFastestRoad) {
            sortedForLength.add(road.getRoadLength());
        }
        Collections.sort(sortedForLength);
        ArrayList<Integer> sortedForID = new ArrayList<>();
        for (Road road : barelyFastestRoad) {
            sortedForID.add(road.getRoadID());
        }
        Collections.sort(sortedForID);
        for (int i : sortedForID) {
            for (Road road : barelyFastestRoad) {
                // Check if the road has the smallest length and matches the current ID
                if ((road.getRoadLength() == sortedForLength.get(0)) && (road.getRoadID() == i)) {
                    // Return the road with the smallest length
                    return road;
                }
            }
        }
        // Return null if no roads are present
        return null;
    }

    /**
     * Sorts the barely route based on road length and ID, and returns the sorted list.
     *
     * @return The sorted list of barely routes.
     */
    private ArrayList<Road> sortBarelyRoute () {
        ArrayList<Road> sortedBarelyRoute = new ArrayList<>();
        ArrayList<Integer> sortedForLength = new ArrayList<>();
        for (Road road : barelyRoute) {
            sortedForLength.add(road.getRoadLength());
        }
        Collections.sort(sortedForLength);
        ArrayList<Integer> sortedForID = new ArrayList<>();
        for (Road road : barelyRoute) {
            sortedForID.add(road.getRoadID());
        }
        Collections.sort(sortedForID);
        for (int length : sortedForLength) {
            for (int id : sortedForID) {
                for (Road road : barelyRoute) {
                    // Check if the road matches the current length, ID, and hasn't been added yet
                    if ((road.getRoadLength() == length) && (road.getRoadID() == id) && !sortedBarelyRoute.contains(road)) {
                        // Add the road to the sorted barely route
                        sortedBarelyRoute.add(road);
                    }
                }
            }
        }
        // Return the sorted barely route
        return sortedBarelyRoute;
    }
}
