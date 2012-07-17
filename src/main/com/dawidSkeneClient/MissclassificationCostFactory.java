package main.com.dawidSkeneClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * 
 * @author Piotr Gnys
 * 
 * This class is used for easy generation of MisclassificationCost object
 * basing on existing categories
 *
 */
public class MissclassificationCostFactory {

	
	
	
	public ArrayList<MisclassificationCost> getMisclassificationCosts(Collection<Category> categories){
		ArrayList<MisclassificationCost> costs = new ArrayList<MisclassificationCost>();
		for (Category category : categories) {
			Map<String,Double> costMap = category.getMisclassificationCostsMap();
			Collection<String> keys = costMap.keySet();
			for (String key : keys) {
				costs.add(new MisclassificationCost(category.getName(),key,costMap.get(key)));
			}
		}
		return costs;
	}
	
	/**
	 * @return Instance of MissclassificationCostFactory
	 */
	public static MissclassificationCostFactory getInstance(){
		return instance;
	}
	
	/**
	 * Constructor is private so only one object of this class will exist
	 */
	private MissclassificationCostFactory(){
		
	}
	
	/**
	 * Singleton of MissclassificationCostFactory
	 */
	private static final MissclassificationCostFactory instance = new MissclassificationCostFactory();
	
}
