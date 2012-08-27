package tutorial;



public class ExampleMain {
    

    private static final String LABELS_FILE = "data/tutorialLabels.txt";
    private static final String GOLD_LABELS_FILE = "data/tutorialGoldLabels.txt";
    
    public static void main(String[] args) {
	DataFilesProcessor processor = new DataFilesProcessor();
	TroiaExample example = new TroiaExample();
	try{
	    example.processRequest(processor.parseLabels(LABELS_FILE),processor.parseGoldLabels(GOLD_LABELS_FILE));
	}catch(Exception e){
	    System.out.println("Error occured : "+e.getMessage());
	}
    }
}
