<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="main.com.dawidSkeneClient.*"%>
<%@ page import="main.com.dawidSkeneTester.*"%>
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


<%!DawidSkeneRequest dawidSkene;
	DSaSStressTest dTest;%>

<%
	dawidSkene = new DawidSkeneRequest(request.getParameter("url"),
			request.getParameter("requestId"), 0);
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
			</table>
		</div>
	</center>
</body>
</html>