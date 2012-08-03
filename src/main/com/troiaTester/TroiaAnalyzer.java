package main.com.troiaTester;

import java.io.IOException;
import java.util.Collection;

import org.apache.log4j.Logger;

import main.com.troiaClient.TroiaRequest;
import main.com.troiaClient.Label;

/**
 * Object of this class is used for preforming test on Troia service.
 * 
 * @author piotr.gnys@10clouds.com
 */
public class TroiaAnalyzer {

	
	
	


	public AnalyzeResults analyze(TroiaRequest request, TestData data,
			int iterations){
		AnalyzeResults results = new AnalyzeResults();
		
		try {
			request.ping();
			request.loadCategories(data.getCategories());
			request.loadGoldLabels(data.getGoldLabels());
			request.loadLabels(data.getLabels());
			request.loadMisclassificationCosts(data.getMisclassificationCost());
			long start = System.currentTimeMillis();
			request.computeBlocking(iterations);
			long finish = System.currentTimeMillis();
			results.setProcessingTime(finish-start);
			results.setMajorityVotes(request.majorityVotes());
		} catch (IOException e) {
			logger.error("Unable to execute analyze : "+e.getMessage());
		}
		TestObjectCollection testObjects = data.getObjectCollection();
		Collection<Label> sourceLabels = data.getLabels();
		double correctLabels=0;
		double totalLabels=0;
		
		for (Label label : sourceLabels) {
			if(label.getCategoryName().equalsIgnoreCase(testObjects.getCategory(label.getObjectName()))){
				correctLabels++;
			}
			totalLabels++;
		}
		results.setSourceLabelQuality((correctLabels/totalLabels)*100);
		
		correctLabels=0;
		totalLabels=0;
		for (String object : testObjects) {
			if(results.getMajorityVotes().get(object).equalsIgnoreCase(testObjects.getCategory(object))){
				correctLabels++;
			}
			totalLabels++;
		}
		results.setDawidSkeneLabelQuality((correctLabels/totalLabels)*100);
		
		return results;
	}
	
	
	
	public static TroiaAnalyzer getInstance(){
		return instance;
	}
	
	private TroiaAnalyzer(){
		
	}

	private static TroiaAnalyzer instance = new TroiaAnalyzer();
	
	/**
	 * Logger for this class
	 */
	private static Logger logger = Logger.getLogger(TroiaAnalyzer.class);
	
}
