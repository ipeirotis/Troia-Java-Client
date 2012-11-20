package troiaClient;

/**
 * Cost of incorrect classification contains cost value, category from and category to.
 * Category from holds name of category that was
 *
 * @author piotr.gnys@10clouds.com
 */
public class MisclassificationCost {




	/**
	 * @param categoryFrom Category that should have been assigned to object
	 * @param cost Cost of misclassification.
	 * @param categoryTo Category that was assigned to object
	 */
	public MisclassificationCost(String categoryFrom, String categoryTo,
								 double cost) {
		super();
		this.categoryFrom = categoryFrom;
		this.categoryTo = categoryTo;
		this.cost = cost;
	}




	/**
	 * @return Category that should have been assigned to object
	 */
	public String getCategoryFrom() {
		return categoryFrom;
	}

	/**
	 * @param categoryFrom Category that should have been assigned to object
	 */
	public void setCategoryFrom(String categoryFrom) {
		this.categoryFrom = categoryFrom;
	}

	/**
	 * @return Category that was assigned to object
	 */
	public String getCategoryTo() {
		return categoryTo;
	}

	/**
	 * @param categoryTo Category that was assigned to object
	 */
	public void setCategoryTo(String categoryTo) {
		this.categoryTo = categoryTo;
	}

	/**
	 * @return Cost of misclassification.
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * @param cost Cost of misclassification.
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "MisclassificationCost { " +
			   "categoryFrom = " + categoryFrom +
			   "categoryTo = " + categoryTo +
			   "cost = " + cost + " }";
	}

	/**
	 *
	 * @see java.lang.Object#equals()
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof MisclassificationCost)) {
			return false;
		}
		MisclassificationCost c = (MisclassificationCost) o;
		// XXX arbitrary chosen but it does not matter because numbers should
		// be exactly the same (used in unit tests).
		final double eps = 1E-6;
		return categoryFrom.equals(c.categoryFrom) &&
			   categoryTo.equals(c.categoryTo) &&
			   Math.abs(cost - c.cost) < eps;
	}

	/**
	 * Category that should have been assigned to object
	 */
	private String categoryFrom;

	/**
	 * Category that was assigned to object
	 */
	private String categoryTo;

	/**
	 * Cost of misclassification.
	 */
	private double cost;
}
