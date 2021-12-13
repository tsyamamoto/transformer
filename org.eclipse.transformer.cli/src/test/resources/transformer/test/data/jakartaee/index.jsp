<%-- 
  Copyright (c) Contributors to the Eclipse Foundation
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
  which is available at https://www.apache.org/licenses/LICENSE-2.0.
 
  SPDX-License-Identifier: (EPL-2.0 OR Apache-2.0)
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page
	import="javax.servlet.http.Cookie"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>JSP Page</title>
</head>
<body>
	<%
			javax.servlet.jsp.JspWriter stream = out;
			stream.println("Hello, World!");
			Cookie c1 = new Cookie("cookie1", "value1");
			response.addCookie(c1);
	%>
	<%@ include file="include.jspf"%>
</body>
</html>