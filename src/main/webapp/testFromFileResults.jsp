<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="main.com.troiaClient.*"%>
<%@ page import="main.com.troiaTester.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="org.apache.log4j.Logger"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%!Collection<Label> labels = new ArrayList<Label>();
	TestDataManager manager = TestDataManager.getInstance();
	Logger logger = Logger.getLogger("testFromFileResults.jsp");
	TroiaAnalyzer analyzer = TroiaAnalyzer.getInstance();
	TroiaRequest troiaRequest;%>

<%
	try {
		
		Scanner scanner = new Scanner(request.getInputStream());
		while (scanner.hasNextLine()) {
			labels.add(manager.parseLabelFromString(scanner.nextLine()));
		}
	} catch (Exception e) {
		logger.error("Uanble to parse input file " + e.getMessage());
	}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test results</title>
</head>
<body>

</body>
</html>