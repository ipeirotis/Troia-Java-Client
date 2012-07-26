<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="main.com.troiaClient.*"%>
<%@ page import="main.com.troiaTester.*"%>
<%@ page import="java.util.*"%>

<script type="text/javascript">
<!--
	/* Script by: www.jtricks.com 
	 * Version: 20090221 
	 * Latest version: 
	 * www.jtricks.com/javascript/blocks/showinghiding.html 
	 */
	function showPageElement(what) {
		var obj = typeof what == 'object' ? what : document
				.getElementById(what);

		obj.style.display = 'block';
		return false;
	}

	function hidePageElement(what) {
		var obj = typeof what == 'object' ? what : document
				.getElementById(what);

		obj.style.display = 'none';
		return false;
	}

	function togglePageElementVisibility(what) {
		var obj = typeof what == 'object' ? what : document
				.getElementById(what);

		if (obj.style.display == 'none')
			obj.style.display = 'block';
		else
			obj.style.display = 'none';
		return false;
	}
//-->
</script>


<%!TroiaRequest dawidSkene;
	TestData data = new TestData();
	TestDataGenerator generator = TestDataGenerator.getInstance();
	Map<String, String> majorityVotes;
	DawidSkeneAnalyzer analyzer;%>
<%
	int categoryCount = Integer.parseInt(request
	.getParameter("categories"));
	int objectCount = Integer.parseInt(request
	.getParameter("objectsCount"));
	int workerCount = Integer.parseInt(request
	.getParameter("workersCount"));
	double minQuality = Double.parseDouble(request
	.getParameter("minQuality"));
	double maxQuality = Double.parseDouble(request
	.getParameter("maxQuality"));
	double goldRatio = Double.parseDouble(request
	.getParameter("goldRatio"));
	int workersPerObject = Integer.parseInt(request
	.getParameter("workersPerObject"));
	int iterations = Integer.parseInt(request
	.getParameter("iterations"));

	//Create request

	dawidSkene = new TroiaRequest(request.getParameter("url"),
	request.getParameter("requestId"), 0);

	Collection<String> categoryNames = generator
	.generateCategoryNames(categoryCount);
	TestObjectCollection testObjects = generator.generateTestObjects(
	objectCount, categoryNames);
	Collection<ArtificialWorker> workers = generator
	.generateArtificialWorkers(workerCount, categoryNames,
			minQuality, maxQuality);
	Collection<String> workerNames = new ArrayList<String>();
	Collection<Category> categories = CategoryFactory.getInstance()
	.createCategories(categoryNames);
	Collection<MisclassificationCost> msCosts = MisclassificationCostFactory
	.getInstance().getMisclassificationCosts(categories);

	for (ArtificialWorker worker : workers) {
		workerNames.add(worker.getName());
	}

	data.setCategories(categories);
	data.setObjectCollection(testObjects);
	data.setGoldLabels(generator.generateGoldLabels(testObjects,
	goldRatio));
	data.setWorkers(workerNames);
	data.setLabels(generator.generateLabels(workers, testObjects,
	workersPerObject));
	data.setMisclassificationCost(msCosts);

	analyzer = new DawidSkeneAnalyzer(dawidSkene, data, iterations);
	analyzer.execute();

	majorityVotes = analyzer.getMajorityVotes();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=dawidSkene.getRequestId()%></title>
</head>
<body>
	<center>
		<font size="8"> <%=dawidSkene.getRequestId()%><br />
		</font>
		<button onclick="togglePageElementVisibility('GeneralInfo')">
			Request parameters</button>
		<div id="GeneralInfo">
			<table border="1">
				<tr>
					<td>
						<center>DAWID SKENE REQUEST PARAMETERS</center>
					</td>
				</tr>
				<tr>

					<td>URL address of DSaS service</td>
					<td>
						<%
							out.print(dawidSkene.getServiceUrl());
						%>
					</td>
				</tr>
				<tr>

					<td>RequestId</td>
					<td>
						<%
							out.print(dawidSkene.getRequestId());
						%>
					</td>
				</tr>
				<tr>

					<td>Gold labels ratio</td>
					<td>
						<%
							out.print(goldRatio);
						%>
					</td>
				</tr>
			</table>
		</div>
		<button onclick="togglePageElementVisibility('Statistics')">
			Statistics</button>
		<div id="Statistics">
			<table border="1">
				<tr>

					<td>Source labels quality</td>
					<td>
						<%
							out.print(analyzer.getSourceLabelQuality());
						%>
					</td>
				</tr>
				<tr>

					<td>Dawid Skene (majority vote) label quality</td>
					<td>
						<%
							out.print(analyzer.getDawidSkeneLabelQuality());
						%>
					</td>
				</tr>

				<tr>

					<td>Processing time</td>
					<td>
						<%
							out.print(analyzer.getProcessingTime());
						%>
					</td>
				</tr>
			</table>
		</div>
		<button onclick="togglePageElementVisibility('MajorityVotes')">
			Majority vote results</button>
		<div id="MajorityVotes">
			<table>
				<%
					Collection<String> objects = majorityVotes.keySet();
					int objectNumber = 0;
					for (String object : objects) {
						if (objectNumber % 5 == 0) {
							out.print("<tr>");
						}
						out.print("<td>");
						out.print(object);
						out.print("</td>");
						out.print("<td>");
						out.print(majorityVotes.get(object));
						out.print("</td>");

						objectNumber++;
					}
				%>
			</table>
		</div>
	</center>
</body>
</html>