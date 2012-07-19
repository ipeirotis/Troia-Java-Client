package main.com.dawidSkeneTester;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestDataGenerator {

	
	public TestObjectCollection generateTestObjects(int objectCount,Map<String,Double> categories){
		Collection<Double> percentages= categories.values();
		double totalPercentage = 0;
		for (Double percentage : percentages) {
			totalPercentage+= percentage.doubleValue();
		}
		if(Double.compare(totalPercentage,1)!=0){
			throw new ArithmeticException("Percentage values sum up to "+totalPercentage+" instead of 1.");
		}else{
			int[]borders = new int[percentages.size()];
			int index=0;
			for (Double percentage : percentages) {
				borders[index] = (int)(percentage.doubleValue()*objectCount);
				index++;
			}
			Map<String,String> objects = new HashMap<String,String>();
			index=0;
			int categortySwitchCounter = 0;
			int categoryIndex = 0;
			Collection<String> categoryNames = categories.keySet();
			Iterator<String> categoryNameIterator = categoryNames.iterator();
			String categoryName = categoryNameIterator.next();
			for (index=0;index<objectCount;index++) {
				if(categortySwitchCounter<borders[categoryIndex]){
					if(categoryIndex<categoryNames.size()){
						categortySwitchCounter++;
					}
				}else{
					categortySwitchCounter=0;
					categoryIndex++;
					categoryName = categoryNameIterator.next();
				}
				String objectName = "Object-"+index;
				objects.put(objectName, categoryName);
			}
			return new TestObjectCollection(objects);
		}
	}
	
	public TestObjectCollection generateTestObjects(int objectCount,int categoryCount){
		Collection<String> categoryNames = this.generateCategoryNames(categoryCount);
		double p = 1.0/(double)categoryNames.size();
		Double percentage = new Double(p);
		Map<String,Double> categories = new HashMap<String,Double>();
		for (String category : categoryNames) {
			categories.put(category, percentage);
		}
		return this.generateTestObjects(objectCount, categories);
	}
	
	public ArrayList<String> generateCategoryNames(int categoryCount){
		ArrayList<String> categories = new ArrayList<String>();
		for(int i=0;i<categoryCount;i++){
			categories.add("Category-"+i);
		}
		return categories;
	}
	
	
	
	
	
	
	public static TestDataGenerator getInstance(){
		return instance;
	}
	
	private static TestDataGenerator instance = new TestDataGenerator();
	
	private TestDataGenerator(){
		
	}
}
