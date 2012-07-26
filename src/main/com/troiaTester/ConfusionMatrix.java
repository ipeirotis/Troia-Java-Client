package main.com.troiaTester;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


import org.apache.log4j.Logger;

/**
 * 
 * @author piotr.gnys@10clouds.com This class represents actual confusion matrix
 *         of artificial worker.
 */
public class ConfusionMatrix {

	public ConfusionMatrix(Map<String, Map<String, Double>> confMatrix) {
		super();
		this.confusionMap = new HashMap<String, Map<Double, String>>();
		Map<Double, String> limiterVector;
		Collection<String> correctClasses = confMatrix.keySet();
		for (String correctClass : correctClasses) {
			Map<String, Double> confVector = confMatrix.get(correctClass);
			Collection<String> labeledClasses = confVector.keySet();
			limiterVector = new TreeMap<Double, String>();
			double limiter = 0;
			for (String labeledClass : labeledClasses) {
				double prob = confVector.get(labeledClass).doubleValue();
				limiter += prob;
				if (prob != 0)
					limiterVector.put(limiter, labeledClass);
			}
			this.confusionMap.put(correctClass, limiterVector);
		}
	}

	/**
	 * This function adds noises to labbeling process. Noise is determined by
	 * confusion matrix with represents probabilities of specific
	 * misclassifications. If category given as parameter is not present in
	 * confusion matrix unnoised rcategory is returned.
	 * 
	 * @param correctCategory
	 *            Correct category of object for with noise will be generated
	 * @return Label with noise created by this confusion matrix
	 */
	public String getCategoryWithNoise(String correctCategory){
		Map<Double,String> confusionVector = this.confusionMap.get(correctCategory);
		double randomVal = Math.random();
		Collection<Double> limiters = confusionVector.keySet();
		String noisedCategory=null;
		boolean generated = false;
		for (Double limiter : limiters) {
			if(randomVal<=limiter.doubleValue()){
				noisedCategory = confusionVector.get(limiter);
				generated=true;
				logger.debug("Generated noised category \""+noisedCategory+"\" for \""+correctCategory+
						"\" with roulette algorithm.");
				break;
			}
		}
		//Probabilities do not always add perfectly to one so this part of function
		//bumps up probability of first incorrect category to fill it.
		if(!generated){
			for(Double limiter : limiters){
				noisedCategory = confusionVector.get(limiter);
				if(!noisedCategory.equalsIgnoreCase(correctCategory)){
					generated=true;
					logger.debug("Generated noised category \""+noisedCategory+"\" for \""+correctCategory+
							"\" with last chance algorithm.");
					break;
				}
			}
		}
		
		if(!generated){
			noisedCategory=correctCategory;
			logger.error("Unable to create noise for category "+correctCategory);
		}
		return noisedCategory;
	}

	/**
	 * @return the confusionMap
	 */
	public Map<String, Map<Double, String>> getConfusionMap() {
		return confusionMap;
	}

	/**
	 * @param confusionMap
	 *            the confusionMap to set
	 */
	public void setConfusionMap(Map<String, Map<Double, String>> confusionMap) {
		this.confusionMap = confusionMap;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ConfusionMatrix [confusionMap=" + confusionMap + "]";
	}

	/**
	 * Confusion map used for generating categories with noise
	 */
	private Map<String, Map<Double, String>> confusionMap;

	/**
	 * Logger for this class
	 */
	private static Logger logger = Logger.getLogger(ConfusionMatrix.class);
}
