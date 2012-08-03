package main.com.troiaTester;

import java.util.Map;

public class AnalyzeResults {

	
	
	
	
	/**
	 * @return the majorityVotes
	 */
	public Map<String, String> getMajorityVotes() {
		return majorityVotes;
	}

	/**
	 * @param majorityVotes the majorityVotes to set
	 */
	public void setMajorityVotes(Map<String, String> majorityVotes) {
		this.majorityVotes = majorityVotes;
	}

	/**
	 * @return the objectProbabilities
	 */
	public Map<String, Map<String, Double>> getObjectProbabilities() {
		return objectProbabilities;
	}

	/**
	 * @param objectProbabilities the objectProbabilities to set
	 */
	public void setObjectProbabilities(
			Map<String, Map<String, Double>> objectProbabilities) {
		this.objectProbabilities = objectProbabilities;
	}

	/**
	 * @return the processingTime
	 */
	public long getProcessingTime() {
		return processingTime;
	}

	/**
	 * @param processingTime the processingTime to set
	 */
	public void setProcessingTime(long processingTime) {
		this.processingTime = processingTime;
	}

	/**
	 * @return the sourceLabelQuality
	 */
	public double getSourceLabelQuality() {
		return sourceLabelQuality;
	}

	/**
	 * @param sourceLabelQuality the sourceLabelQuality to set
	 */
	public void setSourceLabelQuality(double sourceLabelQuality) {
		this.sourceLabelQuality = sourceLabelQuality;
	}

	/**
	 * @return the dawidSkeneLabelQuality
	 */
	public double getDawidSkeneLabelQuality() {
		return dawidSkeneLabelQuality;
	}

	/**
	 * @param dawidSkeneLabelQuality the dawidSkeneLabelQuality to set
	 */
	public void setDawidSkeneLabelQuality(double dawidSkeneLabelQuality) {
		this.dawidSkeneLabelQuality = dawidSkeneLabelQuality;
	}

	Map<String, String> majorityVotes;

	Map<String, Map<String, Double>> objectProbabilities;

	long processingTime;

	double sourceLabelQuality;

	double dawidSkeneLabelQuality;
}
