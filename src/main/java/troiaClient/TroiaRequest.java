package troiaClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.CharBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * TroiaRequest is most important class in Troia client, it represents single request to service.
 * Sometimes, especially in Troia inter works descriptions, request may be called Troia model. From client
 * side request is identified by service URL and request ID. Request object to not contain any data besides
 * that required for it identification, however it provides access to data stored in Troia server and enables
 * access to it services. All methods throw IOException if connection to Troia failed.
 *
 * @author piotr.gnys@10clouds.com
 */
public class TroiaRequest {

	/**
	 *
	 * @param serviceUrl
	 *            Url address of DSaS service
	 * @param timeout
	 *            Connection timeout in milliseconds
	 * @param incremental
	 *            Set to true to create incremental DS and false to create batch DS
	 * @throws MalformedURLException
	 *             If service url is malformed
	 */
	public TroiaRequest(String serviceUrl, String requestId, int timeout,boolean incremental)
	throws MalformedURLException {
		super();
		this.serviceUrl = serviceUrl;
		@SuppressWarnings("unused")
		URL url = new URL(this.serviceUrl);
		this.timeout = timeout;
		this.requestId = requestId;
		this.incremental = incremental;
	}

	/**
	 *
	 * @param serviceUrl
	 *            Url address of DSaS service
	 * @param timeout
	 *            Connection timeout in milliseconds
	 * @throws MalformedURLException
	 *             If service url is malformed
	 */
	public TroiaRequest(String serviceUrl, String requestId, int timeout)
	throws MalformedURLException {
		this(serviceUrl,requestId,timeout,false);
	}

	/**
	 * Pings DSaS, used to check if service is working at all.
	 *
	 * @return Information about exact moment of ping
	 * @throws IOException
	 *             Exception is thrown if connection to DSaS failed
	 */
	public String ping() throws IOException {
		return this.makeGetRequest("ping", null);
	}

	/**
	 * Adds and removes one row in database. Used for checking if database is
	 * up.
	 *
	 * @return Information about results of database operations
	 * @throws IOException
	 *             Exception is thrown if connection to DSaS failed
	 */
	public String pingDatabase() throws IOException {
		return this.makeGetRequest("pingDB", null);
	}

	/**
	 * Restets DawidSkene model, in other words removes request with id of one
	 * that called this method from database.
	 *
	 * @return Information about success or failue of operation
	 * @throws IOException
	 *             Exception is thrown if connection to DSaS failed
	 */
	public String reset() throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		return this.makeGetRequest("reset", params);
	}

	/**
	 * Checks if request with given id already exists
	 *
	 * @return True if DSaS contains request with id as this one
	 * @throws IOException
	 *             Exception is thrown if connection to DSaS failed
	 */
	public boolean exists() throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		return new Gson().fromJson(this.makeGetRequest("exists", params),
								   Boolean.TYPE);
	}

	/**
	 * Uploads categories to data model of this request.
	 *
	 * @param categories
	 *            Collection of categories that will be uploaded.
	 * @return String with information about category count and their JSONified
	 *         form.
	 * @throws IOException
	 *             Exception is thrown if connection to DSaS failed
	 */
	public String loadCategories(Collection<Category> categories)
	throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("categories", new Gson().toJson(categories).toString());
		if(this.incremental) {
			params.put("incremental","incremental");
		}
		return this.makePostRequest("loadCategories", params);

	}

	/**
	 * Uploads misclassification costs to data model of this request.
	 *
	 * @param costs
	 *            Collection of costs that will be uploaded.
	 * @return String with information about number of misclassification costs
	 *         added.
	 * @throws IOException
	 *             Exception is thrown if connection to DSaS failed
	 */
	public String loadMisclassificationCosts(
		Collection<MisclassificationCost> costs) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("costs", new Gson().toJson(costs).toString());
		return this.makePostRequest("loadCosts", params);

	}

	/**
	 * Uploads a single worker assigned label to data model of this request.
	 *
	 * @param label
	 *            Label thats going to be uploaded.
	 * @return String describing added label.
	 * @throws IOException
	 *             Exception is thrown if connection to DSaS failed
	 */
	public String loadLabel(Label label) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("data", new Gson().toJson(label).toString());
		return this.makePostRequest("loadWorkerAssignedLabel", params);
	}

	/**
	 * Uploads a collection of labels to data model of this request.
	 *
	 * @param labels
	 *            Collection of labels that will be uploaded
	 * @return String describing number of labels added.
	 * @throws IOException
	 *             Exception is thrown if connection to DSaS failed
	 */
	public String loadLabels(Collection<Label> labels) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("data", new Gson().toJson(labels).toString());
		return this.makePostRequest("loadWorkerAssignedLabels", params);

	}

	/**
	 * Uploads a single gold label to data model of this request.
	 *
	 * @param label
	 *            Gold label that will be uploaded
	 * @return String describing added label.
	 * @throws IOException
	 *             Exception is thrown if connection to DSaS failed
	 */
	public String loadGoldLabel(GoldLabel label) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("data", new Gson().toJson(label).toString());
		return this.makePostRequest("loadGoldLabel", params);
	}

	/**
	 * Uploads a collection of gold labels to data model of this request.
	 *
	 * @param labels
	 *            Collection of gold labels that will be uploaded.
	 * @return String describing number of labels added.
	 * @throws IOException
	 *             Exception is thrown if connection to DSaS failed
	 */
	public String loadGoldLabels(Collection<GoldLabel> labels)
	throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("data", new Gson().toJson(labels).toString());
		return this.makePostRequest("loadGoldLabels", params);
	}

	/**
	 * Computes majority vote for object with name given as parameter.
	 *
	 * @param objectName
	 *            Name of object for with majority vote will be computed
	 * @return Name of category with highest probability of fitting this object.
	 * @throws IOException
	 *             Exception is thrown if connection to DSaS failed
	 */
	public String majorityVote(String objectName) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("objectName", objectName);
		return this.makeGetRequest("majorityVotes", params);
	}

	/**
	 * Computes majority vote for group of objects with names included in
	 * collection that's given as a parameter.
	 *
	 * @param objects
	 *            Collection of names of object for with majority vote will be
	 *            calculated.
	 * @return Map that assigns name of category to object name.
	 * @throws IOException
	 *             Exception is thrown if connection to DSaS failed
	 */
	public Map<String, String> majorityVotes(Collection<String> objects)
	throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("objects", new Gson().toJson(objects).toString());
		String response = this.makePostRequest("majorityVotes", params);
		return new Gson().fromJson(response,
		new TypeToken<Map<String, String>>() {
		} .getType());

	}

	/**
	 * Computes majority vote for all objects held in request data model.
	 *
	 * @return Map that assigns name of category to object name.
	 * @throws IOException
	 *             Exception is thrown if connection to DSaS failed
	 */
	public Map<String, String> majorityVotes() throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		String response = this.makeGetRequest("majorityVotes", params);
		return new Gson().fromJson(response,
		new TypeToken<Map<String, String>>() {
		} .getType());

	}

	/**
	 * Computes category probabilities for object with given name.
	 *
	 * @param objectName
	 *            Object for with probabilities will be computed.
	 * @return Map that associates category name with it probability.
	 * @throws IOException
	 *             Exception is thrown if connection to DSaS failed
	 */
	public Map<String, Double> objectProb(String objectName) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("object", objectName);
		String response = this.makeGetRequest("objectProb", params);
		return new Gson().fromJson(response,
		new TypeToken<Map<String, Double>>() {
		} .getType());

	}

	/**
	 * Calculates object probabilities and returns them only if their entropy is
	 * equal or above level given as a parameter. If entropy is smaller then
	 * parameter this function returns empty map.
	 *
	 * @param object
	 *            Object for with probabilities will be computed.
	 * @param entropy
	 *            Entropy level below with probabilities will not be calculated
	 * @return Map that associates category name with it probability.
	 * @throws IOException
	 *             Exception is thrown if connection to DSaS failed
	 */
	public Map<String, Double> objectProb(String object, double entropy)
	throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("entropy", String.valueOf(entropy));
		String response = this.makePostRequest("objectProbs", params);
		return new Gson().fromJson(response,
		new TypeToken<Map<String, Map<String, Double>>>() {
		} .getType());

	}

	/**
	 * Computes category probabilities for group of objects with names included
	 * in collection that's given as a parameter.
	 *
	 * @param objects
	 *            Collection of names of object for with probabilites will be
	 *            calculated
	 * @return Map that associates object names with their probability maps
	 * @throws IOException
	 *             Exception is thrown if connection to DSaS failed
	 */
	public Map<String, Map<String, Double>> objectProbs(
		Collection<String> objects) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("objects", new Gson().toJson(objects).toString());
		String response = this.makePostRequest("objectProbs", params);
		return new Gson().fromJson(response,
		new TypeToken<Map<String, Map<String, Double>>>() {
		} .getType());

	}

	/**
	 * Creates String with readable representation of probabilities of the
	 * objects that have probability distributions with entropy higher than the
	 * given threshold.
	 *
	 * @param entropy
	 *            Object with entropy smaller then this value won't be printed
	 *            out
	 * @return String with object probabilities distributions formatted to human
	 *         readable form.
	 * @throws IOException
	 *             Exception is thrown if connection to DSaS failed
	 */
	public String printObjectsProbs(double entropy) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("entropy", String.valueOf(entropy));
		return this.makeGetRequest("printObjectsProbs", params);

	}

	/**
	 * Runs Dawid-Skene algorithm on request data and returns string that
	 * describes how many iterations were execudet and how much time it took.
	 *
	 * @param iterations
	 *            How many Dawid-Skene iteration will be run.
	 * @return String that describes how many Dawid-Skene algorithm iteration
	 *         have been run.
	 * @throws IOException
	 *             Exception is thrown if connection to DSaS failed
	 */
	public String computeBlocking(int iterations) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("iterations", String.valueOf(iterations));
		return this.makeGetRequest("computeBlocking", params);

	}



	/**
	 * Runs Dawid-Skene algorithm on request data and returns string that
	 * describes how many iterations were execudet and how much time it took.
	 *
	 * @param iterations
	 *            How many Dawid-Skene iteration will be run.
	 * @return String that describes how many Dawid-Skene algorithm iteration
	 *         have been run.
	 * @throws IOException
	 *             Exception is thrown if connection to DSaS failed
	 */
	public String compute(int iterations) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("iterations", String.valueOf(iterations));
		return this.makeGetRequest("computeNotBlocking", params);

	}

	/**
	 * Prepares String with summaries of all workers that participated in this
	 * request. It is possible to set if summaries should be detailed or not.
	 *
	 * @param verbose
	 *            Is worker summary supposed to be detailed.
	 * @return String with summaries of all workers from this request in
	 *         readable format.
	 * @throws IOException
	 *             Exception is thrown if connection to DSaS failed
	 */
	public String printWorkerSummary(boolean verbose) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		if (verbose) {
			params.put("verbose", "verbose");
		}
		return this.makeGetRequest("printWorkerSummary", params);

	}

	/**
	 * Creates String with priorities of all classes from this request in
	 * readable format
	 *
	 * @return Priorities of all classes from this request in readable format.
	 * @throws IOException
	 *             Exception is thrown if connection to DSaS failed
	 */
	public String printPriors() throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		return this.makeGetRequest("printPriors", params);

	}

	/**
	 * Function retrieves priorities of all classes in request and puts them in
	 * map that associates class name with it priority.
	 *
	 * @return Map that associates class name with it priority.
	 * @throws IOException
	 *             Exception is thrown if connection to DSaS failed
	 */
	public Map<String, Double> classPriors() throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		String response = this.makeGetRequest("objectProb", params);
		return new Gson().fromJson(response,
		new TypeToken<Map<String, Double>>() {
		} .getType());

	}

	/**
	 * @return DSaS URL address
	 */
	public String getServiceUrl() {
		return serviceUrl;
	}

	/**
	 * @param serviceUrl
	 *            DSaS URL address
	 */
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	/**
	 * @return Timeout in milliseconds
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout
	 *            Timeout in milliseconds
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return Id of this request
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId
	 *            Id of this request
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * Executes get request with given parameters
	 *
	 * @param service
	 *            Path to requested service
	 * @param params
	 *            Parameter map where key is parameter name and value is
	 *            parameter value.
	 * @return Response from DSaS
	 * @throws IOException
	 *             Exception thrown when there are problems with connection
	 */
	private String makeGetRequest(String service, Map<String, String> params)
	throws IOException {

		String urlStr = this.serviceUrl + service;

		if (params != null) {
			String parameters = new String();
			Collection<String> parameterNames = params.keySet();
			String paramName,paramValue;
			for (String name : parameterNames) {
				paramName = name.replace(' ','+');
				paramValue = params.get(name).replace(' ','+');
				parameters += "&" + paramName + "=" + paramValue;
			}
			urlStr += "?" + parameters;
		}

		logger.info("Get URL = \"" + urlStr + "\"");
		URL url;
		try {
			url = new URL(urlStr);
			URLConnection connection = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
			StringBuffer buffer = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			return buffer.toString();
		} catch (MalformedURLException e) {
			// This should never happen URL format should be guaranteed by
			// constructor
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Generates POST request with given parameters
	 *
	 * @param data
	 *            Parameter map where key is parameter name and value is
	 *            parameter value.
	 * @return Response from DSaS
	 * @throws IOException
	 *             Exception thrown when there are problems with connection
	 */
	private String makePostRequest(String service, Map<String, String> params)
	throws IOException {
		try {
			URL url = new URL(this.serviceUrl + service);
			HttpURLConnection connection = (HttpURLConnection) url
										   .openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setAllowUserInteraction(false);
			connection.setRequestProperty("Content-type", "text/xml; charset="
										  + "UTF-8");

			String data = "";
			Collection<String> parameters = params.keySet();
			int i = 0;
			for (String parameter : parameters) {
				String value = params.get(parameter);
				parameter = parameter.replace(' ', '+');
				value = value.replace(' ', '+');
				if (i != 0) {
					data += "&";
				}
				data += parameter + "=" + value;
				i++;
			}
			logger.info("POST request parameters = \"" + data + "\"");

			OutputStream connectionOutput = connection.getOutputStream();
			Writer writer = new OutputStreamWriter(connectionOutput, "UTF-8");
			writer.write(data.toCharArray());
			writer.close();
			connectionOutput.close();

			InputStream connectionInput = connection.getInputStream();
			Reader reader = new InputStreamReader(connectionInput);
			CharBuffer inputBuffer = CharBuffer.allocate(INPUT_BUFFER_SIZE);
			reader.read(inputBuffer);
			reader.close();
			connectionInput.close();
			connection.disconnect();
			return inputBuffer.toString();

		} catch (MalformedURLException e) {
			// This should never happen URL format should be guaranteed by
			// constructor
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * DSaS URL address
	 */
	private String serviceUrl;

	/**
	 * Timeout in milliseconds
	 */
	private int timeout;

	/**
	 * Id of this request
	 */
	private String requestId;

	/**
	 * If true Incremental DS is created if not Batch DS
	 */
	private boolean incremental;

	/**
	 * Size of char buffer used for input by "makePostRequest" function.
	 *
	 * @see makePostRequest
	 */
	private final int INPUT_BUFFER_SIZE = 1024;

	/**
	 * Logger for this class
	 */
	private static Logger logger = Logger.getLogger(TroiaRequest.class);

}
