package main.com.dawidSkeneTester;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestObjectCollection implements Collection<String> {

	
	
	/**
	 * Constructor that creates empty test objects collection
	 */
	public TestObjectCollection() {
		this(new HashMap<String,String>());
	}
	
	/**
	 * @param testObject Map that holds pairs of {name,category} where name is key and category value.
	 */
	public TestObjectCollection(Map<String, String> testObject) {
		super();
		this.testObject = testObject;
	}
	
	
	
	/** 
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	@Override
	public boolean add(String objectName) {
		if(this.testObject.containsKey(objectName)){
			return false;
		}else{
			this.testObject.put(objectName, null);
			return true;
		}
	}
	
	public boolean add(String objectName,String category) {
		if(this.testObject.containsKey(objectName)){
			return false;
		}else{
			this.testObject.put(objectName, category);
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends String> objects) {
		boolean retVal = false;
		for (String string : objects) {
			if(this.add(string)){
				retVal=true;
			}
		}
		return retVal;
	}
	

	/* (non-Javadoc)
	 * @see java.util.Collection#clear()
	 */
	@Override
	public void clear() {
		this.testObject.clear();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		return this.testObject.containsKey(o);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object object : c) {
			if(!this.testObject.containsKey(object)) return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return this.testObject.isEmpty();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#iterator()
	 */
	@Override
	public Iterator<String> iterator() {
		return this.testObject.keySet().iterator();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		return this.testObject.remove(o)!=null;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		boolean retVal = false;
		for (Object object : c) {
			if(this.remove(object)){
				retVal=true;
			}
		}
		return retVal;
	}

	/** (non-Javadoc)
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 * THIS DO NOT WORK
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#size()
	 */
	@Override
	public int size() {
		return this.testObject.size();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#toArray()
	 */
	@Override
	public Object[] toArray() {
		return this.testObject.keySet().toArray();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#toArray(T[])
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		return this.testObject.keySet().toArray(a);
	}

	/**
	 * @param objectName Name of object for with category is returned
	 * @return Category of given object
	 */
	public String getCategory(String objectName){
		return this.testObject.get(objectName);
	}
	
	/**
	 * Adds object with given name and category, or changes object
	 * category if it already exists. 
	 * @param objectName Name of object for with category is set
	 * @param category Name of category
	 */
	public void setCategory(String objectName,String category){
		this.testObject.put(objectName,category);
	}
	
	
	
	/** 
	 * Generates multiple lines string, each line format is as follow <object name><tab><category>
	 * @return List of object, category pairs in easy to read format
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String retStr="";
		Collection<String> objects = this.testObject.keySet();
		for (String object : objects) {
			retStr+=object+'\t'+this.getCategory(object)+'\n';
		}
		return retStr;
	}



	/**
	 * Map that holds pairs of {name,category} where
	 * name is key and category value.
	 */
	Map<String,String> testObject;


}
