<%-- 
  Copyright (c) Contributors to the Eclipse Foundation
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
  which is available at https://www.apache.org/licenses/LICENSE-2.0.
 
  SPDX-License-Identifier: (EPL-2.0 OR Apache-2.0)
--%>
<%@tag import="javax.servlet.http.Cookie" %>
<%
  javax.servlet.jsp.JspWriter stream = out;
  stream.println("Hello, World!");
  Cookie c1 = new Cookie("cookie1", "value1");
  response.addCookie(c1);
%>
<%@ include file="include.tagf" %>
