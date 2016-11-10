import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class usersNetwork {

    /**
     * List for all nodes.
     */
    private ArrayList<String> nodes = new ArrayList<String>();

    
    /**
     * Map for a node and its neighbors.
     */
    private Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();

    
    /**
     * Constructs a graph.
     */
    public usersNetwork() {
    }

    
    /**
     * Adds an edge between two nodes.
     *
     * @param source      the source node.
     * @param destination the destination node, to be connected from source. Requires:
     *                    source != null, destination != null.
     */
    public void addEdge(String source, String destination) {
        
    	// Adds a new path.
        if (!map.containsKey(source)) {
            
        	//Stores a list of neighbors for the node.
            ArrayList<String> neighbors = new ArrayList<String>();
            neighbors.add(destination);
            map.put(source, neighbors);
            
        } else {
            
        	// Updates a path.
            ArrayList<String> exPath = map.get(source);
            
            if (!exPath.contains(destination)) {
            	exPath.add(destination);
                map.put(source, exPath);
            }
        }
        
        storeNodes(source, destination);
    }
    

    /**
     * Storing the nodes.
     */
    private void storeNodes(String source, String destination) {
        if (!source.equals(destination)) {
            if (!nodes.contains(destination)) {
                nodes.add(destination);
            }
        }
        if (!nodes.contains(source)) {
            nodes.add(source);
        }
    }

    
    /**
     * Returns the neighbors for this node as a list.
     *
     * @param node the node for which to search the neighbors.
     *             Requires: node must be present in this Graph and not null.
     * @return the the list of the neighbors for this node.
     */
    
    public ArrayList<String> getNeighbors(String node) {
    	
    	ArrayList<String> neighborsList;
        Set<String> keys = map.keySet();
        for (String key : keys) {
            if (key.equals(node)) {
                neighborsList = map.get(key);
                return new ArrayList<String>(neighborsList);
            }
        }
        return new ArrayList<String>();
        
    }
    

    /**
     * Checks if the node is in the Graph.
     *
     * @return true if the node is in this Graph.
     */
    public boolean nodeExistence(String node) {
        return nodes.contains(node);
    }

}

