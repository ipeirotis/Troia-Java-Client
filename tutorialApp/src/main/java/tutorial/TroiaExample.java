package tutorial;


import java.util.Collection;
import java.util.Map;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

/* Importing all classes from Troia client library*/
import troiaClient.*;


/*
 * This class is example of use of Troia service with
 * Java client library
 */
public class TroiaExample {
    

    /*If this is true then if request with this name exists on server it will be updated*/
    private static final boolean ENABLE_UPDATE = true;

    /*This function contains example of Troia java client implementation */
    public void processRequest(Collection<Label> labels,Collection<GoldLabel> goldLabels){
	/*Most Troia client methods may throw exception when unable to connect to 
	 *Troia so they must be put in try block.*/
	try{

	    /*Crating request that will use "www.project-troia.com" as Troia server and "TurtorialRequest"
	     *as a request identifier. Timeout for request will be 3 seconds (3000 miliseconds).
	     *This constructor checks if url is not malformed and if it is throws a exception. */
	    TroiaRequest request = new TroiaRequest("http://project-troia.com/api/","TutorialRequest",3000);

	    //Checking if Troia server is at given address
	    System.out.println(request.ping());

	    //Checking if there is already request with given name
	    if(!request.exists()||ENABLE_UPDATE){

		/*Extracting categories from labels*/
		Collection<Category> categories = CategoryFactory.getInstance().extractCategories(labels);

		//Uploading categories into Troia server
		request.loadCategories(categories);

		System.out.println("");
		System.out.println("Categories :");
		for (Category category : categories) {
		    System.out.println(category);
		}

		//Uploading labels into Troia server
		request.loadLabels(labels);

		System.out.println("");
		System.out.println("Labels :");
		for(Label label : labels){
		    System.out.println(label);
		}

		//Uploading gold labes so we can evaluate worker more efficent
		request.loadGoldLabels(goldLabels);

		System.out.println("");
		System.out.println("Gold labels :");
		for(GoldLabel label : goldLabels){
		    System.out.println(label);
		}

		//Ordering Troia server to execute Dawid-Skene algorithm with 
		//10 iterations
		request.computeBlocking(10);

		//Map that contains categories associated to objects by Troia
		Map<String,String> majorityVotes = request.majorityVotes();

		//Printing out categories associated to object by Troia
		System.out.println("");
		System.out.println("Troia results :");
		Collection<String> categoryNames = majorityVotes.keySet();
		for (String categoryName : categoryNames) {
		    System.out.println(categoryName+" category is "+majorityVotes.get(categoryName));
		}

	    }

	}catch(MalformedURLException e){
	    //This code will be reached if Troia service URL is malformed
	    System.out.println("Troia URL is malformed");
	    e.printStackTrace();
	}catch(IOException e){
	    //This code will be reached if connection with Troia is broken
	    System.out.println(e.getMessage());
	    e.printStackTrace();
	}
    }


}
