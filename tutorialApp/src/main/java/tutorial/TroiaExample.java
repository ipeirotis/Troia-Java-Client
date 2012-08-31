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
    
    /* Name of request that will be used in this example*/
    private static final String REQUEST_NAME = "ExampleRequest";

    /* URL of Troia*/
    private static final String TROIA_URL = "http://localhost:8080/GetAnotherLabel/rest/";

    /* Time after with connection with Troia server will be considered broken*/
    private static final int TIMEOUT = 1000;

    /*Number of iterations that Dawid-Skene algorithm will be exacuted*/
    private static final int DS_ITERATIONS = 3;

    /*If this is true then if request with this name exists on server it will be updated*/
    private static final boolean ENABLE_UPDATE = true;

    /*This function contains example of Troia java client implementation */
    public void processRequest(Collection<Label> labels,Collection<GoldLabel> goldLabels){
	try{

	    //Creating a request for Troia
	    TroiaRequest request = new TroiaRequest(TROIA_URL,REQUEST_NAME,TIMEOUT);

	    //Checking if Troia server is at given address
	    System.out.println(request.ping());

	    //Checking if there is already request with given name
	    if(!request.exists()&&ENABLE_UPDATE){

		/*Extracting categories from labels*/
		Collection<Category> categories = CategoryFactory.getInstance().extractCategories(labels);

		//Uploading categories into Troia server
		request.loadCategories(categories);

		//Uploading labels into Troia server
		request.loadLabels(labels);

		//Uploading gold labels into Troia server
		request.loadGoldLabels(goldLabels);

		//Ordering Troia server to execute Dawid-Skene algorithm give number of times
		request.computeBlocking(DS_ITERATIONS);

		//Map that contains categories associated to objects by Troia
		Map<String,String> categories = request.majorityVotes();

		Collection<String> categoryNames = categories.keySet();

		//Printing out categories associated to object by Troia
		for (String categoryName : categoryNames) {
		    System.out.println(categoryName+" category is "+categories.get(categoryName));
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
