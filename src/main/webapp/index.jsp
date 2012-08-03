<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DSaS based test aplication</title>

</head>
<body>
	<center>
		<font size="4"> Welcome to Dawid Skene as Service tester
			application.<br />
		</font> Please choose test parameters <br />
		<form name="testForm" action="resultDisplay.jsp" method="post">
			URL address of DSaS service : <input type="text" name="url"
				value="http://localhost:8080/GetAnotherLabel/rest/" /><br />
			Identifier of this request : <input type="text" name="requestId"
				value="TestRequest" /><br /> Number of workers in simulation : <input
				type="text" name="workersCount" value="30" /><br /> <br />
			Minimal worker quality : <input type="text" name="minQuality"
				value="0" /><br /> <br /> Maximal worker quality : <input
				type="text" name="maxQuality" value="1" /><br /> <br /> Workers
			per object : <input type="text" name="workersPerObject" value="9" /><br />

			Number of objects for labeling in simulation : <input type="text"
				name="objectsCount" value="500" /><br /> Ratio of gold labels in
			simulation : <input type="text" name="goldRatio" value="0.2" /><br />
			Number of iterations to perform : <input type="text"
				name="iterations" value="3" /><br /> Number of categories : <input
				type="text" name="categories" value="10" /><br /> <input
				type="hidden" name="actionType" value="Stress test" /> <input
				type="submit" value="Start test" />
		</form>


	</center>
</body>
</html>