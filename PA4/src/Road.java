import java.util.ArrayList;

/**
 * The Road class represents a road with a start point, end point, road length, and other attributes
 * related to its state and usage.
 */
public class Road {

    private String[] points; // end points of the road
    private int roadID; // id of the road
    private int roadLength; // length of the road
    private String startPoint; // the beginning of the original road
    private String endPoint; // the end of the original road
    private boolean isAdded; // a flag indicating whether the road is being used for original road
    private ArrayList<Road> usedRoads = new ArrayList<>(); // roads used to reach the road
    private int longestRoad; // the length of the road used to reach the road

    private String barelyStartPoint; // the beginning of the barely road
    private String barelyEndPoint; // the end of the barely road
    private boolean barelyIsAdded; // a flag indicating whether the road is being used for barely road

    private String barelyFastestStartPoint; // the beginning of the barely fastest road
    private String barelyFastestEndPoint; // the end of the barely fastest road
    private boolean barelyFastestIsAdded; // a flag indicating whether the road is being used for barely fast road
    private ArrayList<Road> barelyFastestUsedRoads = new ArrayList<>(); // roads used to reach the barely fastest oad

    /**
     * Constructs a new {@code Road} object with specified points, length, and ID.
     *
     * @param point1 the start point of the road
     * @param point2 the end point of the road
     * @param roadLength the length of the road
     * @param roadID the ID of the road
     */
    public Road(String point1, String point2, int roadLength, int roadID) {
        points = new String[]{point1, point2};
        this.roadID = roadID;
        this.roadLength = roadLength;
    }

    /**
     * Returns the points of the road.
     *
     * @return an array containing the start and end points of the road
     */
    public String[] getPoints() {
        return points;
    }

    /**
     * Returns the ID of the road.
     *
     * @return the road ID
     */
    public int getRoadID() {
        return roadID;
    }

    /**
     * Returns the length of the road.
     *
     * @return the road length
     */
    public int getRoadLength() {
        return roadLength;
    }

    /**
     * Returns the longest road distance.
     *
     * @return the longest road distance
     */
    public int getLongestRoad() {
        return longestRoad;
    }

    /**
     * Checks if the road is added.
     *
     * @return {@code true} if the road is added; {@code false} otherwise
     */
    public boolean isAdded() {
        return isAdded;
    }

    /**
     * Sets the added state of the road.
     *
     * @param added the new added state
     */
    public void setAdded(boolean added) {
        isAdded = added;
    }

    /**
     * Adds a road to the list of used roads and updates the longest road distance.
     *
     * @param road the road to be added
     */
    public void addRoad(Road road) {
        usedRoads.add(road);
        int totalDistance = 0;
        for (Road usedRoad : usedRoads) {
            totalDistance += usedRoad.getRoadLength();
        }
        this.longestRoad = totalDistance;
    }

    /**
     * Returns the list of used roads.
     *
     * @return the list of used roads
     */
    public ArrayList<Road> getUsedRoads() {
        return usedRoads;
    }

    /**
     * Checks if the road is barely added.
     *
     * @return {@code true} if the road is barely added; {@code false} otherwise
     */
    public boolean isBarelyIsAdded() {
        return barelyIsAdded;
    }

    /**
     * Sets the barely added state of the road.
     *
     * @param barelyIsAdded the new barely added state
     */
    public void setBarelyIsAdded(boolean barelyIsAdded) {
        this.barelyIsAdded = barelyIsAdded;
    }

    /**
     * Checks if the road is barely fastest added.
     *
     * @return {@code true} if the road is barely fastest added; {@code false} otherwise
     */
    public boolean isBarelyFastestIsAdded() {
        return barelyFastestIsAdded;
    }

    /**
     * Sets the barely fastest added state of the road.
     *
     * @param barelyFastestIsAdded the new barely fastest added state
     */
    public void setBarelyFastestIsAdded(boolean barelyFastestIsAdded) {
        this.barelyFastestIsAdded = barelyFastestIsAdded;
    }

    /**
     * Sets the start point of the road.
     *
     * @param startPoint the new start point
     */
    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    /**
     * Returns the end point of the road.
     *
     * @return the end point of the road
     */
    public String getEndPoint() {
        return endPoint;
    }

    /**
     * Sets the end point of the road.
     *
     * @param endPoint the new end point
     */
    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    /**
     * Sets the barely start point of the road.
     *
     * @param barelyStartPoint the new barely start point
     */
    public void setBarelyStartPoint(String barelyStartPoint) {
        this.barelyStartPoint = barelyStartPoint;
    }

    /**
     * Returns the barely end point of the road.
     *
     * @return the barely end point
     */
    public String getBarelyEndPoint() {
        return barelyEndPoint;
    }

    /**
     * Sets the barely end point of the road.
     *
     * @param barelyEndPoint the new barely end point
     */
    public void setBarelyEndPoint(String barelyEndPoint) {
        this.barelyEndPoint = barelyEndPoint;
    }

    /**
     * Returns the barely fastest start point of the road.
     *
     * @return the barely fastest start point
     */
    public String getBarelyFastestStartPoint() {
        return barelyFastestStartPoint;
    }

    /**
     * Sets the barely fastest start point of the road.
     *
     * @param barelyFastestStartPoint the new barely fastest start point
     */
    public void setBarelyFastestStartPoint(String barelyFastestStartPoint) {
        this.barelyFastestStartPoint = barelyFastestStartPoint;
    }

    /**
     * Returns the barely fastest end point of the road.
     *
     * @return the barely fastest end point
     */
    public String getBarelyFastestEndPoint() {
        return barelyFastestEndPoint;
    }

    /**
     * Sets the barely fastest end point of the road.
     *
     * @param barelyFastestEndPoint the new barely fastest end point
     */
    public void setBarelyFastestEndPoint(String barelyFastestEndPoint) {
        this.barelyFastestEndPoint = barelyFastestEndPoint;
    }

    /**
     * Returns the list of barely fastest used roads.
     *
     * @return the list of barely fastest used roads
     */
    public ArrayList<Road> getBarelyFastestUsedRoads() {
        return barelyFastestUsedRoads;
    }

    /**
     * Adds a road to the list of barely fastest used roads.
     *
     * @param road the road to be added
     */
    public void setBarelyFastestUsedRoads(Road road) {
        this.barelyFastestUsedRoads.add(road);
    }

    /**
     * Returns a string representation of the road.
     *
     * @return a string representation of the road in the format "point1\tpoint2\troadLength\troadID"
     */
    @Override
    public String toString() {
        return points[0] + "\t" + points[1] + "\t" + roadLength + "\t" + roadID;
    }
}
