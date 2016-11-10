import java.util.ArrayDeque;
import java.util.ArrayList;

public class searchNetwork {

    /**
     * Constructor
     */
	public searchNetwork(){
    	
    }
	
	/**
     * The shortest path between two nodes in a graph.
     */
    private static ArrayList<String> shortestPath = new ArrayList<String>();

    /**
     * Finds the shortest path between two nodes (source and destination) in a graph.
     *
     * @param graph       The graph to be searched for the shortest path.
     * @param source      The source node of the graph specified by user.
     * @param destination The destination node of the graph specified by user.
     *
     * @return the shortest path stored as a list of nodes.
     * or null if a path is not found.
     * Requires: source != null, destination != null and must have a name (e.g.
     * cannot be an empty string).
     */
    public ArrayList<String> breadthFirstSearch(usersNetwork network, String source,
                                                       String destination) {
        shortestPath.clear();

        // A list that stores the path.
        ArrayList<String> path = new ArrayList<String>();

        // If the source is the same as destination, the search is completed.
        if (source.equals(destination) && network.nodeExistence(source)) {
            path.add(source);
            return path;
        }

        // A queue to store the visited nodes.
        ArrayDeque<String> queue = new ArrayDeque<String>();

        // A queue to store the visited nodes.
        ArrayDeque<String> visited = new ArrayDeque<String>();

        // the java implementation of the BFS algorithm
        queue.offer(source);
        while (!queue.isEmpty()) {
            String vertex = queue.poll();
            visited.offer(vertex);

            ArrayList<String> neighboursList = network.getNeighbors(vertex);
            int index = 0;
            int neighboursSize = neighboursList.size();
            while (index != neighboursSize) {
                String neighbour = neighboursList.get(index);

                path.add(neighbour);
                path.add(vertex);

                if (neighbour.equals(destination)) {
                    return processPath(source, destination, path);
                } else {
                    if (!visited.contains(neighbour)) {
                        queue.offer(neighbour);
                    }
                }
                index++;
            }
        }
        return null;
    }

    /**
     * Adds the nodes involved in the shortest path.
     *
     * @param src         The source node.
     * @param destination The destination node.
     * @param path        The path that has nodes and their neighbors.
     * @return The shortest path.
     */
    private static ArrayList<String> processPath(String source, String destination,
                                                 ArrayList<String> path) {

        // Position at the destination node and do the search
    	// from there until the source is reached
    	// As moving away from the destination, the current position
    	// is the temporary source (source_temp)
        int index = path.indexOf(destination);
        String source_temp = path.get(index + 1);

        // Adds the destination node to the shortestPath.
        shortestPath.add(0, destination);
        
        //do not continue the search beyond 5 nodes (4th degree friendship)
        if (shortestPath.size()<5) {
        	// if the source is reached, returned the path
        	if (source_temp.equals(source)) { 
        		shortestPath.add(0, source); 
                return shortestPath;
        	// if not re-process the path
        	} else {
            	return processPath(source, source_temp, path);
            }     	 
        } else {
        	return null;
        }
    }
	
}
