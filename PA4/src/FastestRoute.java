import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class FastestRoute {

    public HashMap<String, Road> fastestRoute = new HashMap<>(); // Hashmap that holds the city and the paths needed to reach it to reach the cities
    private ArrayList<Road> fastestRoads = new ArrayList<>(); // The roads we use to reach the cities

    /**
     * Constructs a new {@code FastestRoute} object and initializes it by adding the starting point to the route.
     */
    public FastestRoute() {
        addRoad(MapAnalyzer.startingPoint);
    }

    /**
     * Adds roads to the fastest route based on the specified city name.
     *
     * @param cityName the name of the city from which roads will be added to the fastest route
     */
    private void addRoad(String cityName) {
        // Sort the city's neighbors
        ArrayList<Road> sortedCityNeighbours = sortCityNeighbours(cityName);
        // Add the sorted city neighbors to the fastest roads list
        if (sortedCityNeighbours != null) {
            fastestRoads.addAll(sortedCityNeighbours);
        }
        // Sort the fastest roads list
        ArrayList<Road> sortedFastestRoads = sortList();
        // Add the first road in the sorted list to the map
        if (sortedFastestRoads != null) {
            addMap(sortedFastestRoads.get(0).getEndPoint(), sortedFastestRoads.get(0));
        }
    }

    /**
     * Adds the cities and the path to reach them to the hashmap
     *
     * @param cityName destination city
     * @param road the road to reach the target city
     */
    private void addMap(String cityName, Road road) {
        // If the city is not added to the map, it will be added
        if (!fastestRoute.containsKey(cityName)) {
            fastestRoute.put(cityName, road);
        } else { // If the city has already been added to the map and the new road is shorter than the old one, it adds the new road to the map
            if (road.getLongestRoad() < fastestRoute.get(cityName).getLongestRoad()) {
                fastestRoute.put(cityName, road);
            }
        }
        // Removes the used path from the list
        fastestRoads.remove(road);
        // Sends the city to the path insertion function to find connected paths
        addRoad(cityName);
    }

    /**
     * Sorts the roads connected to the city according to their length and id
     *
     * @param cityName City to sort connected roads
     * @return List of roads connected to the city
     */
    private ArrayList<Road> sortCityNeighbours(String cityName) {

        ArrayList<Road> cityRoads = new ArrayList<>(); // List holding the sorted paths
        ArrayList<Road> cityNeighbours = new ArrayList<>();
        ArrayList<Integer> cityIDS = new ArrayList<>(); // Identities of connected roads
        ArrayList<Integer> roadLengths = new ArrayList<>(); // Lengths of connected roads

        for (Road road : MapAnalyzer.roads) {
            // Adds connected paths.
            if (road.getPoints()[0].contentEquals(cityName) || road.getPoints()[1].contentEquals(cityName)) {
                // Adds to the list if the path is not added
                if (!road.isAdded()) {
                    // If the city is on the map, it adds the previous roads to the new road.
                    if (fastestRoute.containsKey(cityName)) {
                        // Determines the beginning of the path
                        road.setStartPoint(cityName);
                        for (String city : road.getPoints()) {
                            if (!city.contentEquals(cityName)) {
                                // Determines the end of the path
                                road.setEndPoint(city);
                                break;
                            }
                        }
                        // Add old paths
                        for (Road usedRoad : fastestRoute.get(cityName).getUsedRoads()) {
                            road.addRoad(usedRoad);
                        }
                        road.addRoad(road);
                    } else {
                        // Determines the beginning of the path
                        road.setStartPoint(cityName);
                        for (String city : road.getPoints()) {
                            if (!city.contentEquals(cityName)) {
                                // Determines the beginning of the path
                                road.setEndPoint(city);
                                break;
                            }
                        }
                        // Add old paths
                        road.addRoad(road);
                    }
                    cityRoads.add(road);
                    // Makes the flag true
                    road.setAdded(true);
                }
            }
        }
        // Transfers roads to a list sorted by length and ID number.
        if (!cityRoads.isEmpty()) {
            for (Road road : cityRoads) {
                cityIDS.add(road.getRoadID());
            }
            Collections.sort(cityIDS); // Sorts the roads according to their length
            for (Road road : cityRoads) {
                roadLengths.add(road.getLongestRoad());
            }
            Collections.sort(roadLengths); // Sorts the roads according to their identification numbers
            for (int length : roadLengths) {
                for (int id : cityIDS) {
                    for (Road road : cityRoads) {
                        if ((road.getLongestRoad() == length) && (road.getRoadID() == id) && !cityNeighbours.contains(road)) {
                            road.setAdded(true);
                            cityNeighbours.add(road);
                            break;
                        }
                    }
                }
            }
            // Returns sorted list
            return cityNeighbours;
        }
        // Exits the function if there is no path left.
        return null;
    }

    /**
     * Sorts the paths according to their length and order of addition.
     *
     * @return sorted road list
     */
    private ArrayList<Road> sortList () {
        ArrayList<Integer> roadLengths = new ArrayList<>(); // The lengths of roads
        ArrayList<Road> sortedRoads = new ArrayList<>(); // The list of sorted roads
        if (!fastestRoads.isEmpty()) {
            for (Road road : fastestRoads) {
                roadLengths.add(road.getLongestRoad());
            }
            Collections.sort(roadLengths); // Sorts the roads according to their length
            for (int length : roadLengths) {
                for (Road road : fastestRoads) {
                    if (road.getLongestRoad() == length && !sortedRoads.contains(road)) {
                        sortedRoads.add(road);
                        break;
                    }
                }
            }
            // Returns sorted list
            return sortedRoads;
        } else {
            // If list is empty, return null
            return null;
        }
    }
}
