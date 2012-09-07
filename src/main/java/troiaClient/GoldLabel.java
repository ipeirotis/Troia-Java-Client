package troiaClient;

/**
 * 
 * Label that have known category.
 * 
 * @author piotr.gnys@10clouds.com
 */
public class GoldLabel {

    /**
     * @param objectName Object to with label is assigned
     * @param correctCategory Category that is assumed as 100% correct
     */
    public GoldLabel(String objectName, String correctCategory) {
	super();
	this.objectName = objectName;
	this.correctCategory = correctCategory;
    }

    /**
     * @return Object to with label is assigned
     */
    public String getObjectName() {
	return objectName;
    }

    /**
     * @param objectName Object to with label is assigned
     */
    public void setObjectName(String objectName) {
	this.objectName = objectName;
    }

    /**
     * @return Category that is assumed as 100% correct
     */
    public String getCorrectCategory() {
	return correctCategory;
    }

    /**
     * @param correctCategory Category that is assumed as 100% correct
     */
    public void setCorrectCategory(String correctCategory) {
	this.correctCategory = correctCategory;
    }


    public String toString(){
	return "Gold label associates object" + this.objectName + " to category " + this.correctCategory;
    }

    /**
     * Object to with label is assigned
     */
    private String objectName;
	
    /**
     * Category that is assumed as 100% correct
     */
    private String correctCategory;
}
