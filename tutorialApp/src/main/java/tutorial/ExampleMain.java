package tutorial;



/**
 * This is class containing main function
 * for usefull code see TroiaExample.java or DataFileProcessor.java
 */
public class ExampleMain {
    
    
    public static void main(String[] args) {
	DataFilesProcessor processor = new DataFilesProcessor();
	TroiaExample example = new TroiaExample();
	try{
	    example.processRequest(processor.parseLabels("data/tutorialLabels.txt"),
				   processor.parseGoldLabels("data/tutorialGoldLabels.txt"));
	}catch(Exception e){
	    System.out.println("Error occured : "+e.getMessage());
	    e.printStackTrace();
	}
    }
}
