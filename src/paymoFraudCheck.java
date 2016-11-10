import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class paymoFraudCheck {

	/**
	 * Function to add nodes to the graph, and creating an undirected graph
	 * The nodes are the users making the transactions (userA and userB)
	 * @param network		a graph data-type of all users involved in transactions
	 * @param source		userA
	 * @param destination	userB
	 */
    private static void biDirectionalEdge(usersNetwork network, String source, String destination) {
		network.addEdge(source, destination); 
		network.addEdge(destination, source);
	}
    
    
    
	public static void main(String[] args) throws IOException {
		
		// Arguments to run the program: 2 input files and 3 output file names
        if(args.length > 0) {
            File input1 = new File(args[0]);
            File input2 = new File(args[1]);
            File fout1 = new File(args[2]);
            File fout2 = new File(args[3]);
            File fout3 = new File(args[4]);

        /**
		 * calling the class usersNetwork to create a network 
		 * with all users and their transactions history
		 */
		usersNetwork network = new usersNetwork();
		
		// variables to use when reading a .csv file
		String line = "";
        String csvSplitBy = ",";
        boolean header=true;
 
        /**
         * READING THE FILE WITH THE PAST TRANSACTIONS
         */
		try (BufferedReader br = new BufferedReader(new FileReader(input1))) { //paymo_input/batch_payment.csv
            
			// reading the file line by line
			while ((line = br.readLine()) != null) {
                
				// avoiding reading the first line (the header)
				if (header==true) {
                	header=false;
                	continue;
                }
				
				// store the values of a line in variable 'values'
				// values = [time, userA, userB, amount, message]
            	String[] values = line.split(csvSplitBy);
            	
            	// if there the line (the transaction) has at least 
            	// the users involved in the transaction
            	// add them to the users' network
                if (values.length>2) {
                	biDirectionalEdge(network,values[1], values[2]); 
                }
            }
        	
        } catch (IOException e) {e.printStackTrace();} // catching errors reading the csv file
		
		// creating the output files and write to them later
		try{
		    PrintWriter out1 = new PrintWriter(fout1, "UTF-8");
		    PrintWriter out2 = new PrintWriter(fout2, "UTF-8");
		    PrintWriter out3 = new PrintWriter(fout3, "UTF-8");
		 
    	/**
    	 * READING THE FILE WITH THE CURRENT TRANSACTIONS
    	 */
		try (BufferedReader br = new BufferedReader(new FileReader(input2))) { //paymo_input/stream_payment.csv
			
			// reading the file line by line
            while ((line = br.readLine()) != null) {
            	
            	// avoiding reading the first line (the header)
                if (header==true) {
                	header=false;
                	continue;
                }
                
                // store the values of a line in variable 'values'
                String[] values = line.split(csvSplitBy);
                
                // if the line (the transaction) has at least the first 3 values: time, userA, userB
                if (values.length>2) { 
                	// calling the search algorithm to determine the friendship level of userA and userB
                	searchNetwork bfs = new searchNetwork();
                	ArrayList<String> pathAB = bfs.breadthFirstSearch(network, values[1], values[2]);
                	
                	// if a path from userA to userB exist
                	if (pathAB != null) {
                		
                		// if the path is less than 3 nodes (i.e. 2 nodes and 1 edge: userA <---> userB)
                		// the users have done transaction(s) in the past: first degree friendship
                		// write 'trusted' to output1.txt
                		// if not, write 'unverified to output1.txt
                		if(pathAB.size()<3){ 
                			out1.println("trusted");
                		} else {
                			out1.println("unverified");
                		}
                		
                		// if the path is less than 4 nodes (i.e. 3 nodes and 2 edges)
                		// the users (userA and userB)  have done transaction(s) with a 
                		// common other user (userC) in the past: 2nd degree friendship
                		// write 'trusted' to output2.txt
                		// if not, write 'unverified to output2.txt
                		if(pathAB.size()<4){
                			out2.println("trusted");
                		} else {
                			out2.println("unverified");
                		}
                		
                		// if the path is more than 5 nodes
                		// the users (userA and userB)  are outside the 4th degree friendship
                		// write 'unverified' to output3.txt
                		// if not, write 'trusted to output3.txt
                		if(pathAB.size()>5){
                			out3.println("unverified");
                		} else {
                			out3.println("trusted");
                		}              		
                	
                	// if a path between userA and userB cannot be found
                	// one of the users is not even in the network
                	// write 'unverified' to output3.txt
                	} else {             		
                		out3.println("unverified");
                	}
                	               	
                } // else{} // bad transaction i.e. no "amount"
            }
        	
        	} catch (IOException e) {e.printStackTrace();} // catching errors reading the csv file
		
		 out1.close();
		 out2.close();
		 out3.close();
		} catch (Exception e) {} // catching errors writing output files
    }
	}
}
