package main.com.dawidSkeneTester;

import java.util.Collection;
import java.util.Map;

public class ConfusionMatrix {

	
	/**
	 * This function adds noises to labbeling process.
	 * Noise is determined by confusion matrix with represents probabilities
	 * of specific misclassifications. If category given as parameter is not present
	 * in confusion matrix unnoised rcategory is returned.
	 * @param correctCategory Correct category of object for with noise will be generated
	 * @return Label with noise created by this confusion matrix
	 */
	public String getCategoryWithNoise(String correctCategory){
		Map<Double,String> confusionVector = this.confusionMap.get(correctCategory);
		double randomVal = Math.random();
		Collection<Double> limiters = confusionVector.keySet();
		for (Double limiter : limiters) {
			if(randomVal<=limiter.doubleValue()){
				return confusionVector.get(limiter);
			}
		}
		return correctCategory;
	}
	
	/**
	 * @return the confusionMap
	 */
	public Map<String, Map< Double,String>> getConfusionMap() {
		return confusionMap;
	}

	/**
	 * @param confusionMap the confusionMap to set
	 */
	public void setConfusionMap(Map<String, Map<Double,String>> confusionMap) {
		this.confusionMap = confusionMap;
	}

	/**
	 * Confusion map used for 
	 */
	private Map<String,Map<String,Double>> representativeConfusionMap;
	
	/**
	 * Confusion map used for generating categories with noise
	 */
	private Map<String,Map<Double,String>> confusionMap;
	
}
