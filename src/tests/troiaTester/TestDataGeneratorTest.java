package tests.troiaTester;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import junit.framework.TestCase;
import main.com.troiaClient.GoldLabel;
import main.com.troiaClient.Label;
import main.com.troiaTester.ArtificialWorker;
import main.com.troiaTester.RouletteNoisedLabelGenerator;
import main.com.troiaTester.TestDataGenerator;
import main.com.troiaTester.TestObjectCollection;

/**
 * The class <code>TestDataGeneratorTest</code> contains tests for the class
 * {@link <code>TestDataGenerator</code>}
 * 
 * @pattern JUnit Test Case
 * 
 * @generatedBy CodePro at 7/17/12 1:48 PM
 * 
 * @author piotr.gnys@10clouds.com
 * 
 * @version $Revision$
 */
public class TestDataGeneratorTest extends TestCase {

	/**
	 * Construct new test instance
	 * 
	 * @param name
	 *            the test name
	 */
	public TestDataGeneratorTest(String name) {
		super(name);
	}

	/**
	 * Run the ArrayList<String> generateCategoryNames(int) method test
	 */
	public void testGenerateCategoryNames() {
		logger.info("--------AUTOMATIC CATEGORY GENERATION--------");
		TestDataGenerator fixture = TestDataGenerator.getInstance();
		int categoryCount = 10;
		Collection<String> result = fixture
				.generateCategoryNames(categoryCount);
		logger.info(result);
	}

	/**
	 * Run the TestObjectCollection generateTestObjects(int, int) method test
	 */
	public void testGenerateTestObjectsWithEqualPercentage() {

		logger.info("--------OBJECT GENERATION WITH AUTO-GENERATED CATEGORIES--------");
		TestDataGenerator fixture = TestDataGenerator.getInstance();
		int objectCount = 10000;
		int categoryCount = 4;
		TestObjectCollection result = fixture.generateTestObjects(objectCount,
				categoryCount);
		Map<String, Integer> occurences = new HashMap<String, Integer>();
		for (String object : result) {
			String category = result.getCategory(object);
			if (occurences.containsKey(category)) {
				occurences.put(category, Integer.valueOf(occurences.get(
						category).intValue() + 1));
			} else {
				occurences.put(category, Integer.valueOf(1));
			}
		}
		logger.info("Occurences of categories in generated objects : ");
		Collection<String> categoryNames = occurences.keySet();
		for (String category : categoryNames) {
			double occ = occurences.get(category).doubleValue();
			double pr = occ / (double) objectCount;
			logger.info(category + " : " + occ + "(" + pr + ")");
		}
	}

	public void testGenerateTestObjectsWithUnequalPercentage() {

		logger.info("--------OBJECT GENERATION WITH UNEQUALLY OCCURING CATEGORIES--------");
		TestDataGenerator fixture = TestDataGenerator.getInstance();
		int objectCount = 5000;
		Map<String, Double> categories = new HashMap<String, Double>();
		categories.put("Main category (0.6)", new Double(0.6));
		categories.put("Minor category (0.3)", new Double(0.3));
		categories.put("Small category (0.1)", new Double(0.1));
		TestObjectCollection result = fixture.generateTestObjects(objectCount,
				categories);
		Map<String, Integer> occurences = new HashMap<String, Integer>();
		for (String object : result) {
			String category = result.getCategory(object);
			if (occurences.containsKey(category)) {
				occurences.put(category, Integer.valueOf(occurences.get(
						category).intValue() + 1));
			} else {
				occurences.put(category, Integer.valueOf(1));
			}
		}
		logger.info("Occurences of categories in generated objects : ");
		Collection<String> categoryNames = occurences.keySet();
		for (String category : categoryNames) {
			double occ = occurences.get(category).doubleValue();
			double pr = occ / (double) objectCount;
			logger.info(category + " : " + occ + "(" + pr + ")");
		}
	}

	public void testCreateArtificialWorker() {
		logger.info("--------SINGLE WORKER GENERATION--------");
		TestDataGenerator fixture = TestDataGenerator.getInstance();
		Collection<String> categories = fixture.generateCategoryNames(4);
		ArtificialWorker worker = fixture.generateArtificialWorker(
				"Worker name", 0.5, categories);
		logger.info(worker);
	}

	public void testGenerateLabels() {
		Logger.getLogger(RouletteNoisedLabelGenerator.class).setLevel(Level.INFO);
		logger.info("--------LABELS GENERATION--------");
		TestDataGenerator fixture = TestDataGenerator.getInstance();
		int categoryCount = 5;
		int objectCount = 100000;
		int workerCount = 1000;
		double minQuality = 0;
		double maxQuality = 1;
		int workersPerObject = 1;
		logger.info("Number of categories : " + categoryCount);
		logger.info("Number of objects : " + objectCount);
		logger.info("Number of workers : " + workerCount);
		logger.info("Minimal worker quality : " + minQuality);
		logger.info("Maximal worker quality : " + maxQuality);
		Collection<String> categories = fixture
				.generateCategoryNames(categoryCount);
		TestObjectCollection objects = fixture.generateTestObjects(objectCount,
				categories);
		Collection<ArtificialWorker> workers = fixture
				.generateArtificialWorkers(workerCount, categories, minQuality,
						maxQuality);
		Collection<Label> labels = fixture.generateLabels(workers, objects,
				workersPerObject);
		int correctLabels = 0;
		int totalLabels = 0;
		double correctLabelsPercentage;
		for (Label label : labels) {
			if (label.getCategoryName().equalsIgnoreCase(
					objects.getCategory(label.getObjectName()))) {
				correctLabels++;
			}
			totalLabels++;
		}
		correctLabelsPercentage = ((double) correctLabels / (double) totalLabels) * 100;
		logger.info("Correct labels percentage : " + correctLabelsPercentage);

	}

	public void testGenerateGoldLabels() {
		logger.info("--------GOLD LABELS GENERATION--------");
		TestDataGenerator fixture = TestDataGenerator.getInstance();
		int objectCount = 100;
		int categoryCount = 2;
		double goldCoverage = 0.5;
		Collection<String> categories = fixture
				.generateCategoryNames(categoryCount);
		TestObjectCollection objects = fixture.generateTestObjects(objectCount,
				categories);
		Collection<GoldLabel> goldLabels = fixture.generateGoldLabels(objects,
				goldCoverage);
		logger.info("For " + objectCount + " objects and " + goldCoverage
				+ " gold coverage " + goldLabels.size()
				+ " gold labels were generated");

	}

	private static Logger logger = Logger
			.getLogger(TestDataGeneratorTest.class);
}

/*
 * $CPS$ This comment was generated by CodePro. Do not edit it. patternId =
 * com.instantiations.assist.eclipse.pattern.testCasePattern strategyId =
 * com.instantiations.assist.eclipse.pattern.testCasePattern.junitTestCase
 * additionalTestNames = assertTrue = false callTestMethod = true createMain =
 * false createSetUp = false createTearDown = false createTestFixture = false
 * createTestStubs = false methods =
 * generateCategoryNames(I),generateTestObjects
 * (I!I),generateTestObjects(I!QMap<QString;QDouble;>;) package =
 * tests.dawidSkeneTester package.sourceFolder = LabelingTester/src
 * superclassType = junit.framework.TestCase testCase = TestDataGeneratorTest
 * testClassType = main.com.dawidSkeneTester.TestDataGenerator
 */