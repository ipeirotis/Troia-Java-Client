package tests.dawidSkeneTester;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import junit.framework.TestCase;
import main.com.troiaClient.TroiaRequest;
import main.com.troiaClient.Label;
import main.com.troiaTester.ArtificialWorker;
import main.com.troiaTester.ConfusionMatrix;
import main.com.troiaTester.TestDataGenerator;
import main.com.troiaTester.TestObjectCollection;

/**
 * The class <code>DawidSkeneRequestTest</code> contains tests for the class
 * {@link <code>DawidSkeneRequest</code>}
 *
 * @pattern JUnit Test Case
 *
 * @generatedBy CodePro at 7/26/12 11:09 AM
 *
 * @author piotr.gnys@10clouds.com
 *
 * @version $Revision$
 */
public class DawidSkeneRequestTest extends TestCase {

	/**
	 * Construct new test instance
	 *
	 * @param name the test name
	 */
	public DawidSkeneRequestTest(String name) {
		super(name);
	}

	/**
	 * Run the String ping() method test
	 */
	public void testPing() {
		try {
			TroiaRequest request = new TroiaRequest(DSAS_URL,REQUEST_NAME,TIMEOUT);
			request.ping();
		} catch (MalformedURLException e) {
			fail("Failed when creating request "+e.getMessage());
		} catch (IOException e) {
			fail("Failed during method execution "+e.getMessage());
		}
	}
	
	public void analyzeWithRandomData(){
		Logger.getLogger(ConfusionMatrix.class).setLevel(Level.INFO);
		logger.info("--------LABELS GENERATION--------");
		TestDataGenerator fixture = TestDataGenerator.getInstance();
		int categoryCount = 5;
		int objectCount = 100000;
		int workerCount = 1000;
		double minQuality = 0;
		double maxQuality = 0.01;
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
			// System.out.print(label);
			if (label.getCategoryName().equalsIgnoreCase(
					objects.getCategory(label.getObjectName()))) {
				correctLabels++;
			}
			totalLabels++;
		}
		correctLabelsPercentage = ((double) correctLabels / (double) totalLabels) * 100;
	}

	/**
	 * Run the String pingDatabase() method test
	 */
	public void testPingDatabase() {
		fail("Newly generated method - fix or disable");
		// add test code here
		// This class does not have a public, no argument constructor,
		// so the pingDatabase() method can not be tested
		assertTrue(false);
	}
	
	private static final String  DSAS_URL = "http://localhost:8080/GetAnotherLabel/rest/";
	private static final String REQUEST_NAME = "test request";
	private static final int TIMEOUT = 200;
	
	private static Logger logger = Logger
			.getLogger(DawidSkeneRequestTest.class);
}


/*$CPS$ This comment was generated by CodePro. Do not edit it.
 * patternId = com.instantiations.assist.eclipse.pattern.testCasePattern
 * strategyId = com.instantiations.assist.eclipse.pattern.testCasePattern.junitTestCase
 * additionalTestNames = 
 * assertTrue = false
 * callTestMethod = true
 * createMain = false
 * createSetUp = false
 * createTearDown = false
 * createTestFixture = false
 * createTestStubs = false
 * methods = ping(),pingDatabase()
 * package = tests.dawidSkeneTester
 * package.sourceFolder = DSaSJavaClient/src
 * superclassType = junit.framework.TestCase
 * testCase = DawidSkeneRequestTest
 * testClassType = main.com.dawidSkeneClient.DawidSkeneRequest
 */