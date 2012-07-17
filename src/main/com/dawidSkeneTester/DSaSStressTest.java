package main.com.dawidSkeneTester;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import main.com.dawidSkeneClient.Category;
import main.com.dawidSkeneClient.Label;

/**
 * 
 * @author Piotr Gnys
 * This class represents single test suite for DSaS application;
 *
 */
public class DSaSStressTest {
	
	
	
	
	/**
	 * @return Number of workers in simulation
	 */
	public int getWorkersCount() {
		return workersCount;
	}
	/**
	 * @param workersCount Number of workers in simulation
	 */
	public void setWorkersCount(int workersCount) {
		this.workersCount = workersCount;
	}
	/**
	 * @return Number of object for labeling in simulation
	 */
	public int getObjectsCount() {
		return objectsCount;
	}
	/**
	 * @param objectsCount Number of object for labeling in simulation
	 */
	public void setObjectsCount(int objectsCount) {
		this.objectsCount = objectsCount;
	}
	/**
	 * @return Ratio of gold labels in simulation
	 */
	public float getGoldRatio() {
		return goldRatio;
	}
	/**
	 * @param goldRatio Ratio of gold labels in simulation
	 */
	public void setGoldRatio(float goldRatio) {
		this.goldRatio = goldRatio;
	}
	/**
	 * @return Array of labels used in this test
	 */
	public Map<String,Category> getLabels() {
		return labels;
	}
	/**
	 * @param labels Array of labels used in this test
	 */
	public void setLabels(Map<String,Category> labels) {
		this.labels = labels;
	}
	/**
	 * @return URL address of DSaS service
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url URL address of DSaS service
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return Number of iterations to perform
	 */
	public int getIterations() {
		return iterations;
	}
	/**
	 * @param iterations Number of iterations to perform
	 */
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}


	public Collection<String> generateWorkers(){
		ArrayList<String> workers = new ArrayList<String>();
		for(int i=0;i<this.workersCount;i++){
			workers.add("Worker"+i);
		}
		return workers;
	}
	
	public Collection<String> generateObjects(){
		ArrayList<String> workers = new ArrayList<String>();
		for(int i=0;i<this.objectsCount;i++){
			workers.add("Object"+i);
		}
		return workers;
	}

	public Collection<Label> generateLabels(ArrayList<String> categories,Collection<String> workers,
			Collection<String> objects){
		ArrayList<Label> labels = new ArrayList<Label>();
		int categoriesSize = categories.size();
		for (String object : objects) {
			for (String worker : workers) {
				labels.add(new Label(worker,object,categories.get(generateRandomIndex(categoriesSize))));
			}
		}
		return labels;
	}
	
	public Collection<Label> generateLabels(){
		ArrayList<String> categories = new ArrayList<String>();
		Collection<String> cats = this.labels.keySet();
		for (String category : cats) {
			categories.add(category);
		}
		return this.generateLabels(categories,this.generateWorkers(),this.generateObjects());
	}
	
	private int generateRandomIndex(int arraySize){
		return (int)(Math.random()*arraySize);
	}

	/**
	 * Number of workers in simulation
	 */
	private int workersCount;
	
	/**
	 * Number of object for labeling in simulation
	 */
	private int objectsCount;
	
	/**
	 * Ratio of gold labels in simulation
	 */
	private float goldRatio;
	
	/**
	 * Array of labels used in this test
	 */
	private Map<String,Category> labels;
	/**
	 * URL address of DSaS service
	 */
	private String url;
	
	/**
	 * Number of iterations to perform
	 */
	private int iterations;
}
