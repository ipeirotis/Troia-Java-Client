package main.com.dawidSkeneClient;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CategoryFactory {
	

	/**
	 *  Creates labels with given names and default misclassification costs
	 * @param labelNames Names of labels
	 * @return labels with given names and default misclassification costs
	 */
	public HashMap<String,Category> createCategories(Collection<String> labelNames){
		HashMap<String,Category> labels=new HashMap<String,Category>();
		for (String labelName : labelNames) {
			Category l = new Category(labelName);
			for (String otherLabel : labelNames) {
				if(!otherLabel.equalsIgnoreCase(labelName)){
					l.setMisclassificationCost(otherLabel, Category.DEFAULT_MISCLASSIFICATION_COST);
				}
			}
			labels.put(l.getName(),l);
		}
		return labels;
	}
	
	public HashMap<String,Category> createCategories(Collection<String> labelNames,Map<String,Double>priorities,Map<String,Map<String,Double>> misclassificationMatrix){
		HashMap<String,Category> labels=new HashMap<String,Category>();
		for (String labelName : labelNames) {
			Category l = new Category(labelName,priorities.get(labelName),misclassificationMatrix.get(labelName));
			labels.put(l.getName(),l);
		}
		return labels;
	}
	
	
	
	/**
	 * @return Instance of label factory
	 */
	public static CategoryFactory getInstance() {
		return instance;
	}



	private static CategoryFactory instance = new CategoryFactory();
	
	private CategoryFactory(){
		
	}
}
