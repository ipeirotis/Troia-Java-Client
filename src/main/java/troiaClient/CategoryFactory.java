package troiaClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Allows simple creation of category class objects.
 * The most basic way to do it is to call function createCategories with collection
 * of String objects representing category names. This will result in creation of categories with
 * given names an default misclassification costs. This is perfect approach if you do not need to 
 * create nonstandard misclassification cost maps as it allows you to completely ignore this part
 * of Troia model and simplifies it use. To be able to create more complex classification models
 * you can simply pass another argument, this time of type Map<String,Map<String,Double>>, representing
 * misclassification cost matrix. Correct format of this map is that first key indicates from what and second to 
 * with category  cost is in map. So  if one will call a function costs.get("CategoryA").get("CategoryB"), where 
 * "costs" is correctly formatted misclassification matrix, it will return cost of misclassification in situation when
 * object that belong to CateogryA was classified as CategoryB.
 *
 * @author piotr.gnys@10clouds.com
 */
public class CategoryFactory {
	

    /**
     *  Creates labels with given names and default misclassification costs
     * @param labelNames Names of labels
     * @return labels with given names and default misclassification costs
     */
    public Collection<Category> createCategories(Collection<String> labelNames){
	Collection<Category> labels=new ArrayList<Category>();
	for (String labelName : labelNames) {
	    Category l = new Category(labelName);
	    for (String otherLabel : labelNames) {
		if(!otherLabel.equalsIgnoreCase(labelName)){
		    l.setMisclassificationCost(otherLabel, Category.DEFAULT_MISCLASSIFICATION_COST);
		}
	    }
	    labels.add(l);
	}
	return labels;
    }



    /**
     * Creates categories extracted from misclassification cost map
     * @param missclassificationMatrix Map of misclassification costs
     */
    public Collection<Category> createCategories(Map<String,Map<String,Double>> misclassificationMatrix,Map<String,Double>priorities){
	Collection<Category> categories=new ArrayList<Category>();
	Collection<String> categoryNames = misclassificationMatrix.keySet();
	for (String categoryName : categoryNames) {
	    Category category = new Category(categoryName,priorities.get(categoryName),misclassificationMatrix.get(categoryName));
	    categories.add(category);
	}
	return categories;
    }

    /**
     * Creates categories extracted from misclassification cost map
     * @param missclassificationMatrix Map of misclassification costs
     */
    public Collection<Category> createCategories(Map<String,Map<String,Double>> misclassificationMatrix){
	Collection<Category> categories=new ArrayList<Category>();
	Collection<String> categoryNames = misclassificationMatrix.keySet();
	for (String categoryName : categoryNames) {
	    Category category = new Category(categoryName,misclassificationMatrix.get(categoryName));
	    categories.add(category);
	}
	return categories;
    }



    /**
     * This method extracts categories from collection of labels and associates
     * default misclassification costs to them
     * @param labels Collection of labels that will be processed
     */
    public Collection<Category> extractCategories(Collection<Label> labels){
	Collection<String> categoryNames = new ArrayList<String>();
	for (Label label : labels) {
	    categoryNames.add(label.getCategoryName());    
	}
	return this.createCategories(categoryNames);
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
