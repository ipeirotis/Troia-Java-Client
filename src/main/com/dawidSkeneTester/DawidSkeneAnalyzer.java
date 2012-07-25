package main.com.dawidSkeneTester;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import main.com.dawidSkeneClient.DawidSkeneRequest;
import main.com.dawidSkeneClient.Label;

/**
 * 
 * @author piotr.gnys@10clouds.com
 * Objects of this class are used for preforming analyzes of DSaS.
 */
public class DawidSkeneAnalyzer {

	
	
	
	/**
	 * @param request Request that will be used for this analyze
	 * @param data Test data that will be used in this analyze
	 * @param iterations Number of Dawid-Skene algorithm executions
	 */
	public DawidSkeneAnalyzer(DawidSkeneRequest request, TestData data,
			int iterations) {
		super();
		this.request = request;
		this.data = data;
		this.iterations = iterations;
	}

	public void execute(){
		
		
		try {
			request.ping();
			request.loadCategories(data.getCategories());
			request.loadGoldLabels(data.getGoldLabels());
			request.loadLabels(data.getLabels());
			request.loadMisclassificationCosts(data.getMisclassificationCost());
			long start = System.currentTimeMillis();
			request.computeBlocking(iterations);
			long finish = System.currentTimeMillis();
			this.processingTime = finish-start;
			this.majorityVotes = request.majorityVotes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TestObjectCollection testObjects = this.data.getObjectCollection();
		Collection<Label> sourceLabels = this.data.getLabels();
		double correctLabels=0;
		double totalLabels=0;
		
		for (Label label : sourceLabels) {
			if(label.getCategoryName().equalsIgnoreCase(testObjects.getCategory(label.getObjectName()))){
				correctLabels++;
			}
			totalLabels++;
		}
		this.sourceLabelQuality = (correctLabels/totalLabels)*100;
		
		correctLabels=0;
		totalLabels=0;
		for (String object : testObjects) {
			if(this.majorityVotes.get(object).equalsIgnoreCase(testObjects.getCategory(object))){
				correctLabels++;
			}
			totalLabels++;
		}
		this.dawidSkeneLabelQuality = (correctLabels/totalLabels)*100;
	}
	
	
	
	
	/**
	 * @return the request
	 */
	public DawidSkeneRequest getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(DawidSkeneRequest request) {
		this.request = request;
	}

	/**
	 * @return the data
	 */
	public TestData getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(TestData data) {
		this.data = data;
	}

	/**
	 * @return the iterations
	 */
	public int getIterations() {
		return iterations;
	}

	/**
	 * @param iterations the iterations to set
	 */
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	/**
	 * @return the majorityVotes
	 */
	public Map<String, String> getMajorityVotes() {
		return majorityVotes;
	}

	/**
	 * @return the objectProbabilities
	 */
	public Map<String, Map<String, Double>> getObjectProbabilities() {
		return objectProbabilities;
	}

	/**
	 * @return the processingTime
	 */
	public long getProcessingTime() {
		return processingTime;
	}

	/**
	 * @return the sourceLabelQuality
	 */
	public double getSourceLabelQuality() {
		return sourceLabelQuality;
	}

	/**
	 * @return the dawidSkeneLabelQuality
	 */
	public double getDawidSkeneLabelQuality() {
		return dawidSkeneLabelQuality;
	}




	/**
	 * Request that will be used for this analyze
	 */
	DawidSkeneRequest request;
	
	/**
	 * Test data that will be used in this analyze 
	 */
	TestData data;
	
	/**
	 * Number of Dawid-Skene algorithm executions
	 */
	int iterations;
	
	Map<String,String> majorityVotes;
	
	Map<String,Map<String,Double>> objectProbabilities;
	
	
	
	long processingTime;
	
	double sourceLabelQuality;
	
	double dawidSkeneLabelQuality;
}
