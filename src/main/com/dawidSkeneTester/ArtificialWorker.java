package main.com.dawidSkeneTester;

import java.util.Map;

public class ArtificialWorker {

	
	
	
	
	/**
	 * @return Worker name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name Worker name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Worker confusion matrix.
	 */
	public Map<String, Map<String, Double>> getConfusionMatrix() {
		return confusionMatrix;
	}

	/**
	 * @param confusionMatrix Worker confusion matrix.t
	 */
	public void setConfusionMatrix(Map<String, Map<String, Double>> confusionMatrix) {
		this.confusionMatrix = confusionMatrix;
	}

	/**
	 * Worker name
	 */
	private String name;
	
	/**
	 * Worker confusion matrix.
	 * Confusion matrix is used to indicate what
	 */
	private Map<String,Map<String,Double>> confusionMatrix;
}
