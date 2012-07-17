package main.com.dawidSkeneClient;

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

public class DawidSkeneRequest {

	/**
	 * 
	 * @param serviceUrl
	 *            Url address of DSaS service
	 * @param timeout
	 *            Connection timeout in milliseconds
	 * @throws MalformedURLException
	 *             If service url is malformed
	 */
	public DawidSkeneRequest(String serviceUrl, String requestId, int timeout)
			throws MalformedURLException {
		super();
		this.serviceUrl = serviceUrl;
		@SuppressWarnings("unused")
		URL url = new URL(this.serviceUrl);
		this.timeout = timeout;
		this.requestId = requestId;
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
	 * Restets DawidSkene model
	 * 
	 * @return
	 * @throws IOException
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
	 */
	public boolean exists() throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		return new Gson().fromJson(this.makeGetRequest("exists", params),
				Boolean.TYPE);
	}

	/**
	 * Adds categories to request.
	 * 
	 * @param categories
	 *            Collection of categories
	 * @return
	 */
	public String loadCategories(Collection<Category> categories) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("categories", new Gson().toJson(categories).toString());
		try {
			return this.makePostRequest("loadCategories", params);
		} catch (IOException e) {
			String error = "Unable to load categories : " + e.getMessage();
			logger.error(error);
			return "error";
		}
	}

	/**
	 * Adds misclassification costs to requests
	 * 
	 * @param costs
	 * @return
	 */
	public String loadMisclassificationCosts(
			Collection<MisclassificationCost> costs) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("costs", new Gson().toJson(costs).toString());
		try {
			return this.makePostRequest("loadCosts", params);
		} catch (IOException e) {
			String error = "Unable to load misclassification costs : "
					+ e.getMessage();
			logger.error(error);
			return "error";
		}
	}

	/**
	 * Adds a single label to request
	 * 
	 * @param label
	 *            New label
	 * @return
	 */
	public String loadLabel(Label label) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("data", new Gson().toJson(label).toString());
		try {
			return this.makePostRequest("loadWorkerAssignedLabel", params);
		} catch (IOException e) {
			String error = "Unable to load label : " + e.getMessage();
			logger.error(error);
			return "error";
		}
	}

	/**
	 * Adds a collection of labels to request
	 * 
	 * @param labels
	 *            Collection of labels
	 * @return
	 */
	public String loadLabels(Collection<Label> labels) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("data", new Gson().toJson(labels).toString());
		try {
			return this.makePostRequest("loadWorkerAssignedLabels", params);
		} catch (IOException e) {
			String error = "Unable to load labels : " + e.getMessage();
			logger.error(error);
			return "error";
		}
	}

	/**
	 * Adds a single gold label to request
	 * 
	 * @param label
	 *            New gold label
	 * @return
	 */
	public String loadGoldLabel(GoldLabel label) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("data", new Gson().toJson(label).toString());
		try {
			return this.makePostRequest("loadGoldLabel", params);
		} catch (IOException e) {
			String error = "Unable to load label : " + e.getMessage();
			logger.error(error);
			return "error";
		}
	}

	/**
	 * Adds a collection of gold labels to request
	 * 
	 * @param labels
	 *            New gold labels
	 * @return
	 */
	public String loadGoldLabels(Collection<GoldLabel> labels) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("data", new Gson().toJson(labels).toString());
		try {
			return this.makePostRequest("loadGoldLabels", params);
		} catch (IOException e) {
			String error = "Unable to load label : " + e.getMessage();
			logger.error(error);
			return "error";
		}
	}

	/**
	 * Computes majority vote for object of given name
	 * 
	 * @param objectName
	 *            Name of object for with majority vote will be computed
	 * @return
	 */
	public Map<String,String>  majorityVote(String objectName) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("objectName", objectName);
		try {
			String response = this.makeGetRequest("majorityVotes", params);
			return new Gson().fromJson(response,
					new TypeToken<Map<String,String>>() {
					}.getType());
		} catch (IOException e) {
			String error = "Unable to execute majority vote : "
					+ e.getMessage();
			logger.error(error);
			return null;
		}
	}

	/**
	 * Computes majority vote for objects with given names
	 * 
	 * @param objects
	 *            Collection of names of object for with majority vote will be
	 *            executed
	 * @return
	 */
	public Map<String,String> majorityVotes(Collection<String> objects) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("objects", new Gson().toJson(objects).toString());
		try {
			String response = this.makePostRequest("majorityVotes", params);
			return new Gson().fromJson(response,
					new TypeToken<Map<String,String>>() {
					}.getType());
		} catch (IOException e) {
			String error = "Unable to execute majority vote : "
					+ e.getMessage();
			logger.error(error);
			return null;
		}
	}

	/**
	 * Computes majority vote for all objects in request.
	 * 
	 * @return
	 */
	public Map<String, String> majorityVotes() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		try {
			String response = this.makeGetRequest("majorityVotes", params);
			return new Gson().fromJson(response,
					new TypeToken<Map<String,String>>() {
				}.getType());
		} catch (IOException e) {
			String error = "Unable to execute majority vote : "
					+ e.getMessage();
			logger.error(error);
			return null;
		}
	}

	/**
	 * Computes class probs object
	 * 
	 * @param objects
	 * @return
	 */
	public Map<String, Map<String, Double>> objectProbs(
			Collection<String> objects) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("objects", new Gson().toJson(objects).toString());
		try {
			String response = this.makePostRequest("objectProbs", params);
			return new Gson().fromJson(response,
					new TypeToken<Map<String, Map<String, Double>>>() {
					}.getType());
		} catch (IOException e) {
			String error = "Unable to execute object probs : " + e.getMessage();
			logger.error(error);
			return null;
		}
	}
	
	public Map<String,Double> objectProbs(String object,double entropy){
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("entropy",String.valueOf(entropy));
		try {
			String response = this.makePostRequest("objectProbs", params);
			return new Gson().fromJson(response,
					new TypeToken<Map<String, Map<String, Double>>>() {
					}.getType());
		} catch (IOException e) {
			String error = "Unable to execute object probs : " + e.getMessage();
			logger.error(error);
			return null;
		}
	}
	
	public Map<String,Double> objectProb(String objectName){
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("object",objectName);
		try {
			String response = this.makeGetRequest("objectProb", params);
			return new Gson().fromJson(response,
					new TypeToken<Map<String, Double>>() {
					}.getType());
		} catch (IOException e) {
			String error = "Unable to execute object probs : " + e.getMessage();
			logger.error(error);
			return null;
		}
	}
	
	public String printObjectsProbs(double entropy){
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("entropy",String.valueOf(entropy));
		try {
			return this.makeGetRequest("printObjectsProbs", params);
		} catch (IOException e) {
			String error = "Unable to print objects probs : " + e.getMessage();
			logger.error(error);
			return error;
		}
	}
	
	public String computeBlocking(int iterations){
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		params.put("iterations",String.valueOf(iterations));
		try {
			return this.makeGetRequest("computeBlocking", params);
		} catch (IOException e) {
			String error = "Unable to execute computeBlocking : " + e.getMessage();
			logger.error(error);
			return error;
		}
	}
	
	public String printWorkerSummary(boolean verbose){
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		if(verbose){
			params.put("verbose", "verbose");
		}
		try {
			return this.makeGetRequest("printWorkerSummary", params);
		} catch (IOException e) {
			String error = "Unable to load label : " + e.getMessage();
			logger.error(error);
			return error;
		}
	}

	public String printPriors(){
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		try {
			return this.makeGetRequest("printPriors", params);
		} catch (IOException e) {
			String error = "Unable to load label : " + e.getMessage();
			logger.error(error);
			return error;
		}
	}
	
	public Map<String,Double> classPriors(){
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.requestId);
		try {
			String response = this.makeGetRequest("objectProb", params);
			return new Gson().fromJson(response,
					new TypeToken<Map<String, Double>>() {
					}.getType());
		} catch (IOException e) {
			String error = "Unable to get class priors : " + e.getMessage();
			logger.error(error);
			return null;
		}
	}
	
	/**
	 * @return the serviceUrl
	 */
	public String getServiceUrl() {
		return serviceUrl;
	}

	/**
	 * @param serviceUrl
	 *            the serviceUrl to set
	 */
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	/**
	 * @return the timeout
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout
	 *            the timeout to set
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return the requestId
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId
	 *            the requestId to set
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
	 *            paramater value.
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
			for (String name : parameterNames) {
				parameters += "&" + name + "=" + params.get(name);
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
			// TODO This should never happen URL format shoud be guaranteed by
			// constructor
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Generates POST request
	 * 
	 * @param data
	 * @return
	 * @throws IOException
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
				parameter.replace(' ', '+');
				value.replace(' ', '+');
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
			// TODO This should never happen URL format shoud be guaranteed by
			// constructor
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * DSaS url
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
	 * Size of char buffer used for input by "makePostRequest" function.
	 * 
	 * @see makePostRequest
	 */
	private final int INPUT_BUFFER_SIZE = 1024;

	/**
	 * Logger for this class
	 */
	private static Logger logger = Logger.getLogger(DawidSkeneRequest.class);


}
