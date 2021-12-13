/********************************************************************************
 * Copyright (c) Contributors to the Eclipse Foundation
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: (EPL-2.0 OR Apache-2.0)
 ********************************************************************************/

package transformer.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.cli.ParseException;
import org.eclipse.transformer.TransformException;
import org.eclipse.transformer.TransformOptions;
import org.eclipse.transformer.Transformer;
import org.eclipse.transformer.action.impl.ClassActionImpl;
import org.eclipse.transformer.action.impl.InputBufferImpl;
import org.eclipse.transformer.action.impl.JavaActionImpl;
import org.eclipse.transformer.action.impl.SelectionRuleImpl;
import org.eclipse.transformer.action.impl.SignatureRuleImpl;
import org.eclipse.transformer.action.impl.TextActionImpl;
import org.eclipse.transformer.util.InputStreamData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import aQute.lib.utf8properties.UTF8Properties;
import transformer.test.util.CaptureLoggerImpl;

public class TestTransformJakartaRelatedFiles extends CaptureTest {

	public static final String	TEST_DATA_PREFIX					= "transformer/test/data/jakartaee";

	public static final String	SUN_RESOURCES_XML_SIMPLE_NAME		= "sun-resources.xml";
	public static final String	SUN_RESOURCES_XML_PATH				= TEST_DATA_PREFIX + '/' + SUN_RESOURCES_XML_SIMPLE_NAME;
	public static final String	GLASSFISH_RESOURCES_XML_SIMPLE_NAME	= "glassfish-resources.xml";
	public static final String	GLASSFISH_RESOURCES_XML_PATH		= TEST_DATA_PREFIX + '/' + GLASSFISH_RESOURCES_XML_SIMPLE_NAME;

	public static final String	INCLUDE_JSPF_SIMPLE_NAME			= "include.jspf";
	public static final String	INCLUDE_JSPF_PATH					= TEST_DATA_PREFIX + '/' + INCLUDE_JSPF_SIMPLE_NAME;
	public static final String	INDEX_JSP_SIMPLE_NAME				= "index.jsp";
	public static final String	INDEX_JSP_PATH						= TEST_DATA_PREFIX + '/' + INDEX_JSP_SIMPLE_NAME;
	public static final String	INDEX_JSPX_SIMPLE_NAME				= "index.jspx";
	public static final String	INDEX_JSPX_PATH						= TEST_DATA_PREFIX + '/' + INDEX_JSPX_SIMPLE_NAME;

	public static final String	INCLUDE_TAGF_SIMPLE_NAME			= "include.tagf";
	public static final String	INCLUDE_TAGF_PATH					= TEST_DATA_PREFIX + '/' + INCLUDE_TAGF_SIMPLE_NAME;
	public static final String	TEST1_TAG_SIMPLE_NAME				= "test1.tag";
	public static final String	TEST1_TAG_PATH						= TEST_DATA_PREFIX + '/' + TEST1_TAG_SIMPLE_NAME;
	public static final String	TEST2_TAGX_SIMPLE_NAME				= "test2.tagx";
	public static final String	TEST2_TAGX_PATH						= TEST_DATA_PREFIX + '/' + TEST2_TAGX_SIMPLE_NAME;

	public static final String	TAGLIB_TLD_SIMPLE_NAME				= "taglib.tld";
	public static final String	TAGLIB_TLD_PATH						= TEST_DATA_PREFIX + '/' + TAGLIB_TLD_SIMPLE_NAME;


	public static final String	INDEX_XHTML_SIMPLE_NAME				= "index.xhtml";
	public static final String	INDEX_XHTML_PATH					= TEST_DATA_PREFIX + '/' + INDEX_XHTML_SIMPLE_NAME;

	public static final String	RA_XML_SIMPLE_NAME					= "ra.xml";
	public static final String	RA_XML_PATH							= TEST_DATA_PREFIX + '/' + RA_XML_SIMPLE_NAME;

	public static final String	WEB_XML_SIMPLE_NAME					= "web.xml";
	public static final String	WEB_XML_PATH						= TEST_DATA_PREFIX + '/' + WEB_XML_SIMPLE_NAME;

	public static final String	EJB_JAR_XML_SIMPLE_NAME				= "ejb-jar.xml";
	public static final String	EJB_JAR_XML_PATH					= TEST_DATA_PREFIX + '/' + EJB_JAR_XML_SIMPLE_NAME;

	public static final String	JAVAX_JMS_QUEUE_CONECTION_FACTORY	= "\"javax.jms.QueueConnectionFactory\"";
	public static final String	JAVAX_JMS_QUEUE						= "\"javax.jms.Queue\"";
	public static final String	JAKARTA_JMS_QUEUE_CONECTION_FACTORY	= "\"jakarta.jms.QueueConnectionFactory\"";
	public static final String	JAKARTA_JMS_QUEUE					= "\"jakarta.jms.Queue\"";

	public static final String	JAVAX_SERVLET_HTTP_COOKIE			= "javax.servlet.http.Cookie";
	public static final String	JAKARTA_SERVLET_HTTP_COOKIE			= "jakarta.servlet.http.Cookie";
	public static final String	JAVAX_SERVLET_JSP_JSPWRITER			= "javax.servlet.jsp.JspWriter";
	public static final String	JAKARTA_SERVLET_JSP_JSPWRITER		= "jakarta.servlet.jsp.JspWriter";

	public static final String	JAVAX_HTTP_SERVLET_REQUEST			= "javax.servlet.http.HttpServletRequest";
	public static final String	JAKARTA_HTTP_SERVLET_REQUEST        = "jakarta.servlet.http.HttpServletRequest";
	public static final String	JAVAX_HTTP_SESSION					= "javax.servlet.http.HttpSession";
	public static final String	JAKARTA_HTTP_SESSION				= "jakarta.servlet.http.HttpSession";
	public static final String 	JAVAX_SERVLET_SERVLET_EXCEPTION		= "javax.servlet.ServletException";
	public static final String 	JAKARTA_SERVLET_SERVLET_EXCEPTION	= "jakarta.servlet.ServletException";
	public static final String 	JAVAX_MAIL_SESSION					= "javax.mail.Session";
	public static final String 	JAKARTA_MAIL_SESSION				= "jakarta.mail.Session";

	public static final String	JAVAX_FACES							= "javax.faces";
	public static final String	JAKARTA_FACES						= "jakarta.faces";

	public static final String	JAVAX_RESOURCE_CCI_CONNECTIONFACTORY				= ">javax.resource.cci.ConnectionFactory<";
	public static final String	JAVAX_RESOURCE_CCI_CONNECTION						= ">javax.resource.cci.Connection<";
	public static final String	JAVAX_RESOURCE_SPI_SECURITY_PASSWORDCREDENTIAL		= ">javax.resource.spi.security.PasswordCredential<";

	public static final String	JAKARTA_RESOURCE_CCI_CONNECTIONFACTORY				= ">jakarta.resource.cci.ConnectionFactory<";
	public static final String	JAKARTA_RESOURCE_CCI_CONNECTION						= ">jakarta.resource.cci.Connection<";
	public static final String	JAKARTA_RESOURCE_SPI_SECURITY_PASSWORDCREDENTIAL	= ">jakarta.resource.spi.security.PasswordCredential<";

	public static final String		JAVAX_SEVLET_REQUEST								= "javax.servlet.request";
	public static final String		JAKARTA_SERVLET_REQUEST								= "jakarta.servlet.request";
	public static final String		TEST_DATA_SERVLET_JAVA								= "src/test/java/transformer/test/data/Sample_ServletProperty.java";
	public static final String		JAVA_SIMPLE_NAME									= "Sample_ServletProperty.java";
	public static final String		TEST_DATA_SERVLET_CLASS								= "transformer/test/data/Sample_ServletProperty.class";
	public static final String[]	JAVAX_SERVLET_PROPERTIES							= new String[] {
		"javax.servlet.request.cipher_suite", "javax.servlet.request.key_size", "javax.servlet.request.ssl_session_id",
		"javax.servlet.request.X509Certificate", "javax.servlet.context.tempdir", "javax.servlet.context.orderedLibs",
		"javax.servlet.include.request_uri", "javax.servlet.include.context_path", "javax.servlet.include.servlet_path",
		"javax.servlet.include.mapping", "javax.servlet.include.path_info", "javax.servlet.include.query_string",
		"javax.servlet.forward.mapping", "javax.servlet.forward.request_uri", "javax.servlet.forward.context_path",
		"javax.servlet.forward.servlet_path", "javax.servlet.forward.path_info", "javax.servlet.forward.query_string",
		"javax.servlet.async.mapping", "javax.servlet.async.request_uri", "javax.servlet.async.context_path",
		"javax.servlet.async.servlet_path", "javax.servlet.async.path_info", "javax.servlet.async.query_string",
		"javax.servlet.error.status_code", "javax.servlet.error.exception_type", "javax.servlet.error.message",
		"javax.servlet.error.exception", "javax.servlet.error.request_uri", "javax.servlet.error.servlet_name"
	};

	UTF8Properties					jakartaRenamesProperties;
	UTF8Properties					jakartaXmlDdProperties;
	UTF8Properties					jakartaTextMasterProperties;
	//

	public Set<String> getIncludes() {
		return Collections.emptySet();
	}

	public Set<String> getExcludes() {
		return Collections.emptySet();
	}

	//

	public Map<String, Map<String, String>> masterXmlUpdates;

	public Map<String, Map<String, String>> getMasterXmlUpdates() {
		if (masterXmlUpdates == null) {
			Map<String, Map<String, String>> filetypeUpdates = new HashMap<>();

			Map<String, String> xmlUpdates = new HashMap<>();
			for (Entry<Object, Object> jakartaXmlDdProperty : jakartaXmlDdProperties.entrySet()) {
				xmlUpdates.put(jakartaXmlDdProperty.getKey()
					.toString(),
					jakartaXmlDdProperty.getValue()
						.toString());
			}

			for (Entry<Object, Object> entry : jakartaTextMasterProperties.entrySet()) {
				HashMap<String, String>	map = new HashMap<>();
				filetypeUpdates.put(entry.getKey().toString(), map);
				if(entry.getValue().toString().equals("jakarta-xml-dd.properties")) {
					map.putAll(xmlUpdates);
				} else if (entry.getValue().toString().equals("jakarta-renames.properties")) {
					map.putAll(getMasterJavaUpdates());
				}
			}

			masterXmlUpdates = filetypeUpdates;
		}

		return masterXmlUpdates;
	}

	//


	public TextActionImpl textAction;

	public TextActionImpl getTextAction() {
		if (textAction == null) {
			CaptureLoggerImpl useLogger = getCaptureLogger();

			textAction = new TextActionImpl(useLogger, false, false, new InputBufferImpl(),
				new SelectionRuleImpl(useLogger, getIncludes(), getExcludes()),
				new SignatureRuleImpl(useLogger, null, null, null, null, getMasterXmlUpdates(), null,
					Collections.emptyMap()));
		}

		return textAction;
	}

	public Map<String, String> masterJavaUpdates;

	public Map<String, String> getMasterJavaUpdates() {
		if (masterJavaUpdates == null) {
			Map<String, Map<String, String>> useJavaUpdates = new HashMap<>(2);

			masterJavaUpdates = new HashMap<>();
			for (Entry<Object, Object> jakartaRenamesProperty : jakartaRenamesProperties.entrySet()) {
				masterJavaUpdates.put(jakartaRenamesProperty.getKey().toString(), jakartaRenamesProperty.getValue().toString());
			}
		}

		return masterJavaUpdates;
	}

	public JavaActionImpl javaAction;

	public JavaActionImpl getJavaAction() {
		if (javaAction == null) {
			CaptureLoggerImpl useLogger = getCaptureLogger();

			javaAction = new JavaActionImpl(useLogger, false, false, new InputBufferImpl(),
				new SelectionRuleImpl(useLogger, getIncludes(), getExcludes()), new SignatureRuleImpl(useLogger,
					getMasterJavaUpdates(), null, null, null, null, null, Collections.emptyMap()));
		}

		return javaAction;
	}

	public ClassActionImpl classAction;

	public ClassActionImpl getClassAction() {
		if (classAction == null) {
			CaptureLoggerImpl useLogger = getCaptureLogger();

			classAction = new ClassActionImpl(useLogger, false, false, new InputBufferImpl(),
				new SelectionRuleImpl(useLogger, getIncludes(), getExcludes()), new SignatureRuleImpl(useLogger,
					getMasterJavaUpdates(), null, null, null, null, null, Collections.emptyMap()));
		}

		return classAction;
	}

	//

	protected static final class Occurrences {
		public final String	tag;
		public final int	count;

		public Occurrences(String tag, int count) {
			this.tag = tag;
			this.count = count;
		}
	}

	public static final Occurrences[]	WEB_XML_INITIAL_OCCURRENCES = {
		new Occurrences(JAVAX_SERVLET_SERVLET_EXCEPTION, 1), new Occurrences(JAKARTA_SERVLET_SERVLET_EXCEPTION, 0)
	};

	public static final Occurrences[]	WEB_XML_FINAL_OCCURRENCES = {
		new Occurrences(JAVAX_SERVLET_SERVLET_EXCEPTION, 0), new Occurrences(JAKARTA_SERVLET_SERVLET_EXCEPTION, 1)
	};

	public static final Occurrences[]	EJB_JAR_XML_INITIAL_OCCURRENCES = {
		new Occurrences(JAVAX_MAIL_SESSION, 1), new Occurrences(JAKARTA_MAIL_SESSION, 0)
	};

	public static final Occurrences[]	EJB_JAR_XML_FINAL_OCCURRENCES = {
		new Occurrences(JAVAX_MAIL_SESSION, 0), new Occurrences(JAKARTA_MAIL_SESSION, 1)
	};

	public static final Occurrences[]	SUN_RESOURCES_XML_INITIAL_OCCURRENCES = {
		new Occurrences(JAVAX_JMS_QUEUE_CONECTION_FACTORY, 1),
		new Occurrences(JAKARTA_JMS_QUEUE_CONECTION_FACTORY, 0),
		new Occurrences(JAVAX_JMS_QUEUE, 2),
		new Occurrences(JAKARTA_JMS_QUEUE, 0)
	};

	public static final Occurrences[]	SUN_RESOURCES_XML_FINAL_OCCURRENCES	= {
		new Occurrences(JAVAX_JMS_QUEUE_CONECTION_FACTORY, 0),
		new Occurrences(JAKARTA_JMS_QUEUE_CONECTION_FACTORY, 1),
		new Occurrences(JAVAX_JMS_QUEUE, 0),
		new Occurrences(JAKARTA_JMS_QUEUE, 2)
	};

	public static final Occurrences[]	GLASSFISH_RESOURCES_XML_INITIAL_OCCURRENCES	= {
		new Occurrences(JAVAX_JMS_QUEUE_CONECTION_FACTORY, 1),
		new Occurrences(JAKARTA_JMS_QUEUE_CONECTION_FACTORY, 0),
		new Occurrences(JAVAX_JMS_QUEUE, 2),
		new Occurrences(JAKARTA_JMS_QUEUE, 0)
	};

	public static final Occurrences[]	GLASSFISH_RESOURCES_XML_FINAL_OCCURRENCES = {
		new Occurrences(JAVAX_JMS_QUEUE_CONECTION_FACTORY, 0),
		new Occurrences(JAKARTA_JMS_QUEUE_CONECTION_FACTORY, 1),
		new Occurrences(JAVAX_JMS_QUEUE, 0),
		new Occurrences(JAKARTA_JMS_QUEUE, 2)
	};

	public static final Occurrences[]	INCLUDE_JSPF_INITIAL_OCCURRENCES = {
		new Occurrences(JAVAX_SERVLET_HTTP_COOKIE, 3), new Occurrences(JAKARTA_SERVLET_HTTP_COOKIE, 0)
	};

	public static final Occurrences[]	INCLUDE_JSPF_FINAL_OCCURRENCES = {
		new Occurrences(JAVAX_SERVLET_HTTP_COOKIE, 0), new Occurrences(JAKARTA_SERVLET_HTTP_COOKIE, 3)
	};

	public static final Occurrences[]	INDEX_JSP_INITIAL_OCCURRENCES = {
		new Occurrences(JAVAX_SERVLET_HTTP_COOKIE, 1), new Occurrences(JAKARTA_SERVLET_HTTP_COOKIE, 0),
		new Occurrences(JAVAX_SERVLET_JSP_JSPWRITER, 1), new Occurrences(JAKARTA_SERVLET_JSP_JSPWRITER, 0)
	};

	public static final Occurrences[]	INDEX_JSP_FINAL_OCCURRENCES	= {
		new Occurrences(JAVAX_SERVLET_HTTP_COOKIE, 0), new Occurrences(JAKARTA_SERVLET_HTTP_COOKIE, 1),
		new Occurrences(JAVAX_SERVLET_JSP_JSPWRITER, 0), new Occurrences(JAKARTA_SERVLET_JSP_JSPWRITER, 1)
	};

	public static final Occurrences[]	INDEX_JSPX_INITIAL_OCCURRENCES = {
		new Occurrences(JAVAX_SERVLET_HTTP_COOKIE, 1), new Occurrences(JAKARTA_SERVLET_HTTP_COOKIE, 0),
		new Occurrences(JAVAX_SERVLET_JSP_JSPWRITER, 1), new Occurrences(JAKARTA_SERVLET_JSP_JSPWRITER, 0)
	};

	public static final Occurrences[]	INDEX_JSPX_FINAL_OCCURRENCES = {
		new Occurrences(JAVAX_SERVLET_HTTP_COOKIE, 0), new Occurrences(JAKARTA_SERVLET_HTTP_COOKIE, 1),
		new Occurrences(JAVAX_SERVLET_JSP_JSPWRITER, 0), new Occurrences(JAKARTA_SERVLET_JSP_JSPWRITER, 1)
	};

	public static final Occurrences[]	INCLUDE_TAGF_INITIAL_OCCURRENCES = {
		new Occurrences(JAVAX_SERVLET_HTTP_COOKIE, 3), new Occurrences(JAKARTA_SERVLET_HTTP_COOKIE, 0)
	};

	public static final Occurrences[]	INCLUDE_TAGF_FINAL_OCCURRENCES = {
		new Occurrences(JAVAX_SERVLET_HTTP_COOKIE, 0), new Occurrences(JAKARTA_SERVLET_HTTP_COOKIE, 3)
	};

	public static final Occurrences[]	TEST1_TAG_INITIAL_OCCURRENCES = {
		new Occurrences(JAVAX_SERVLET_HTTP_COOKIE, 1), new Occurrences(JAKARTA_SERVLET_HTTP_COOKIE, 0),
		new Occurrences(JAVAX_SERVLET_JSP_JSPWRITER, 1), new Occurrences(JAKARTA_SERVLET_JSP_JSPWRITER, 0)
	};

	public static final Occurrences[]	TEST1_TAG_FINAL_OCCURRENCES	= {
		new Occurrences(JAVAX_SERVLET_HTTP_COOKIE, 0), new Occurrences(JAKARTA_SERVLET_HTTP_COOKIE, 1),
		new Occurrences(JAVAX_SERVLET_JSP_JSPWRITER, 0), new Occurrences(JAKARTA_SERVLET_JSP_JSPWRITER, 1)
	};

	public static final Occurrences[]	TEST2_TAGX_INITIAL_OCCURRENCES = {
		new Occurrences(JAVAX_SERVLET_HTTP_COOKIE, 1), new Occurrences(JAKARTA_SERVLET_HTTP_COOKIE, 0),
		new Occurrences(JAVAX_SERVLET_JSP_JSPWRITER, 1), new Occurrences(JAKARTA_SERVLET_JSP_JSPWRITER, 0)
	};

	public static final Occurrences[]	TEST2_TAGX_FINAL_OCCURRENCES = {
		new Occurrences(JAVAX_SERVLET_HTTP_COOKIE, 0), new Occurrences(JAKARTA_SERVLET_HTTP_COOKIE, 1),
		new Occurrences(JAVAX_SERVLET_JSP_JSPWRITER, 0), new Occurrences(JAKARTA_SERVLET_JSP_JSPWRITER, 1)
	};

	public static final Occurrences[]	TAGLIB_TLD_INITIAL_OCCURRENCES = {
		new Occurrences(JAVAX_HTTP_SERVLET_REQUEST, 1), new Occurrences(JAKARTA_HTTP_SERVLET_REQUEST, 0),
		new Occurrences(JAVAX_HTTP_SESSION, 1), new Occurrences(JAKARTA_HTTP_SESSION, 0)
	};

	public static final Occurrences[]	TAGLIB_TLD_FINAL_OCCURRENCES = {
		new Occurrences(JAVAX_HTTP_SERVLET_REQUEST, 0), new Occurrences(JAKARTA_HTTP_SERVLET_REQUEST, 1),
		new Occurrences(JAVAX_HTTP_SESSION, 0), new Occurrences(JAKARTA_HTTP_SESSION, 1)
	};

	public static final Occurrences[]	INDEX_XHTML_INITIAL_OCCURRENCES = {
		new Occurrences(JAVAX_FACES, 1), new Occurrences(JAKARTA_FACES, 0)
	};

	public static final Occurrences[]	INDEX_XHTML_FINAL_OCCURRENCES = {
		new Occurrences(JAVAX_FACES, 0), new Occurrences(JAKARTA_FACES, 1)
	};

	public static final Occurrences[]	RA_XML_INITIAL_OCCURRENCES = {
		new Occurrences(JAVAX_RESOURCE_CCI_CONNECTIONFACTORY, 1),
		new Occurrences(JAKARTA_RESOURCE_CCI_CONNECTIONFACTORY, 0),
		new Occurrences(JAVAX_RESOURCE_CCI_CONNECTION, 1),
		new Occurrences(JAKARTA_RESOURCE_CCI_CONNECTION, 0),
		new Occurrences(JAVAX_RESOURCE_SPI_SECURITY_PASSWORDCREDENTIAL, 1),
		new Occurrences(JAKARTA_RESOURCE_SPI_SECURITY_PASSWORDCREDENTIAL, 0)
	};

	public static final Occurrences[]	RA_XML_FINAL_OCCURRENCES = {
		new Occurrences(JAVAX_RESOURCE_CCI_CONNECTIONFACTORY, 0),
		new Occurrences(JAKARTA_RESOURCE_CCI_CONNECTIONFACTORY, 1),
		new Occurrences(JAVAX_RESOURCE_CCI_CONNECTION, 0),
		new Occurrences(JAKARTA_RESOURCE_CCI_CONNECTION, 1),
		new Occurrences(JAVAX_RESOURCE_SPI_SECURITY_PASSWORDCREDENTIAL, 0),
		new Occurrences(JAKARTA_RESOURCE_SPI_SECURITY_PASSWORDCREDENTIAL, 1)
	};

	public static final Occurrences[]	SERVLET_INITIAL_OCCURRENCES;

	public static final Occurrences[]	SERVLET_FINAL_OCCURRENCES;

	static {
		List<Occurrences> initialList = new ArrayList<>();
		List<Occurrences> finalList = new ArrayList<>();

		for (String servletProperty : JAVAX_SERVLET_PROPERTIES) {
			Occurrences o1 = new Occurrences(servletProperty, 1);
			if (servletProperty.equals("javax.servlet.error.exception")) {
				o1 = new Occurrences(servletProperty, 2);
			}
			initialList.add(o1);
			initialList.add(new Occurrences(servletProperty.replace("javax", "jakarta"), 0));
			finalList.add(new Occurrences(servletProperty, 0));

			Occurrences o2 = new Occurrences(servletProperty.replace("javax", "jakarta"), 1);
			if (servletProperty.equals("javax.servlet.error.exception")) {
				o2 = new Occurrences(servletProperty.replace("javax", "jakarta"), 2);
			}
			finalList.add(o2);
		}
		SERVLET_INITIAL_OCCURRENCES = initialList.toArray(new Occurrences[0]);
		SERVLET_FINAL_OCCURRENCES = finalList.toArray(new Occurrences[0]);
	}

	//
	public List<String> display(String resourceRef, InputStream resourceStream) throws IOException {
		System.out.println("Resource [ " + resourceRef + " ]");
		List<String> lines = TestUtils.loadLines(resourceStream); // throws
																	// IOException

		int numLines = lines.size();
		for (int lineNo = 0; lineNo < numLines; lineNo++) {
			System.out.printf("[ %3d ] [ %s ]\n", lineNo, lines.get(lineNo));
		}

		return lines;
	}

	public void testTransform(String resourceRef, Occurrences[] initialOccurrences, Occurrences[] finalOccurrences)
		throws TransformException, IOException, URISyntaxException, ParseException {

		System.out.println("Transform [ " + resourceRef + " ] ...");
		loadProperties();
		List<String> initialLines;
		try (InputStream resourceInput = TestUtils.getResourceStream(resourceRef)) { // throws
																						// IOException
			initialLines = display(resourceRef, resourceInput);
		}

		TextActionImpl useTextAction = getTextAction();
		System.out.println("Transform [ " + resourceRef + " ] using [ " + useTextAction.getName() + " ]");

		List<String> finalLines;
		try (InputStream resourceInput = TestUtils.getResourceStream(resourceRef)) { // throws
																						// IOException
			InputStreamData xmlOutput = useTextAction.apply(resourceRef, resourceInput); // throws
																						// JakartaTransformException
			finalLines = display(resourceRef, xmlOutput.stream);
		}

		verify(resourceRef, "initial lines", initialOccurrences, initialLines);
		verify(resourceRef, "final lines", finalOccurrences, finalLines);

		System.out.println("Transform [ " + resourceRef + " ] ... OK");
	}

	public void verify(String resourceRef, String caseTag, Occurrences[] occurrences, List<String> lines) {

		System.out.println("Verify [ " + resourceRef + " ] [ " + caseTag + " ] ...");

		for (Occurrences occurrence : occurrences) {
			String occurrenceTag = occurrence.tag;
			int expected = occurrence.count;

			int actual = TestUtils.occurrences(lines, occurrenceTag);

			if (expected != actual) {
				Assertions.assertEquals(expected, actual, "Resource [ " + resourceRef + " ] [ " + caseTag
					+ " ] Value [ " + occurrenceTag + " ] Expected [ " + expected + " ] Actual [ " + actual + " ]");
			}
		}

		System.out.println("Verify [ " + resourceRef + " ] [ " + caseTag + " ] ... done");
	}

	public void testTransformJava(String resourceRef, Occurrences[] initialOccurrences, Occurrences[] finalOccurrences)
		throws TransformException, IOException, URISyntaxException, ParseException {

		System.out.println("Transform [ " + resourceRef + " ] ...");
		loadProperties();
		List<String> initialLines;
		try (InputStream resourceInput = new FileInputStream(resourceRef)) { // throws
																						// IOException
			initialLines = display(resourceRef, resourceInput);
		}

		JavaActionImpl useJavaAction = getJavaAction();
		System.out.println("Transform [ " + resourceRef + " ] using [ " + useJavaAction.getName() + " ]");

		List<String> finalLines;
		try (InputStream resourceInput = new FileInputStream(resourceRef)) { // throws
																						// IOException
			InputStreamData xmlOutput = useJavaAction.apply(resourceRef, resourceInput); // throws
																							// JakartaTransformException
			finalLines = display(resourceRef, xmlOutput.stream);
		}

		verify(resourceRef, "initial lines", initialOccurrences, initialLines);
		verify(resourceRef, "final lines", finalOccurrences, finalLines);

		System.out.println("Transform [ " + resourceRef + " ] ... OK");
	}

	public void testTransformClass(String resourceRef)
		throws TransformException, IOException, URISyntaxException, ParseException {

		System.out.println("Transform [ " + resourceRef + " ] ...");
		loadProperties();

		ClassActionImpl useClassAction = getClassAction();
		System.out.println("Transform [ " + resourceRef + " ] using [ " + useClassAction.getName() + " ]");

		try (InputStream resourceInput = TestUtils.getResourceStream(resourceRef)) { // throws
			InputStreamData xmlOutput = useClassAction.apply(resourceRef, resourceInput); // throws
		}

		int expected = JAVAX_SERVLET_PROPERTIES.length * 2 + 5;
		int actual = useClassAction.getLastActiveChanges()
			.getModifiedConstants();

		useClassAction.getLastActiveChanges()
			.display(System.out, "input", "output");
		Assertions.assertEquals(expected, actual,
			"Resource [ " + resourceRef + " ] Expected [ " + expected + " ] Actual [ " + actual + " ]");

		System.out.println("Transform [ " + resourceRef + " ] ... OK");
	}

	public void loadProperties() throws URISyntaxException, IOException, TransformException, ParseException {
		if (jakartaRenamesProperties != null) {
			return;
		}
		Transformer jTrans = new Transformer(System.out, System.err);
		TransformOptions options = jTrans.createTransformOptions();
		jTrans.setArgs(new String[0]);
		jTrans.setParsedArgs();
		options.setLogging();
		jakartaRenamesProperties = jTrans.loadExternalProperties("jakarta-renames.properties",
			"src/main/resources/org/eclipse/transformer/jakarta/jakarta-renames.properties");
		jakartaTextMasterProperties = jTrans.loadExternalProperties("jakarta-text-master.properties",
			"src/main/resources/org/eclipse/transformer/jakarta/jakarta-text-master.properties");
		jakartaXmlDdProperties = jTrans.loadExternalProperties("jakarta-renames.properties",
			"src/main/resources/org/eclipse/transformer/jakarta/jakarta-xml-dd.properties");
	}

	//
	@Test
	public void testTransform_webXml() throws TransformException, IOException, URISyntaxException, ParseException {
		testTransform(WEB_XML_PATH, WEB_XML_INITIAL_OCCURRENCES, WEB_XML_FINAL_OCCURRENCES);
	}

	@Test
	public void testTransform_ejbJarXml() throws TransformException, IOException, URISyntaxException, ParseException {
		testTransform(EJB_JAR_XML_PATH, EJB_JAR_XML_INITIAL_OCCURRENCES, EJB_JAR_XML_FINAL_OCCURRENCES);
	}

	@Test
	public void testTransform_sunResourcesXml()
		throws TransformException, IOException, URISyntaxException, ParseException {
		testTransform(SUN_RESOURCES_XML_PATH, SUN_RESOURCES_XML_INITIAL_OCCURRENCES, SUN_RESOURCES_XML_FINAL_OCCURRENCES);
	}

	@Test
	public void testTransform_glassfishResourcesXml()
		throws TransformException, IOException, URISyntaxException, ParseException {
		testTransform(GLASSFISH_RESOURCES_XML_PATH, GLASSFISH_RESOURCES_XML_INITIAL_OCCURRENCES, GLASSFISH_RESOURCES_XML_FINAL_OCCURRENCES);
	}

	@Test
	public void testTransform_includeJspf() throws TransformException, IOException, URISyntaxException, ParseException {
		testTransform(INCLUDE_JSPF_PATH, INCLUDE_JSPF_INITIAL_OCCURRENCES, INCLUDE_JSPF_FINAL_OCCURRENCES);
	}

	@Test
	public void testTransform_indexJsp() throws TransformException, IOException, URISyntaxException, ParseException {
		testTransform(INDEX_JSP_PATH, INDEX_JSP_INITIAL_OCCURRENCES, INDEX_JSP_FINAL_OCCURRENCES);
	}

	@Test
	public void testTransform_indexJspx() throws TransformException, IOException, URISyntaxException, ParseException {
		testTransform(INDEX_JSPX_PATH, INDEX_JSPX_INITIAL_OCCURRENCES, INDEX_JSPX_FINAL_OCCURRENCES);
	}

	@Test
	public void testTransform_includeTagf() throws TransformException, IOException, URISyntaxException, ParseException {
		testTransform(INCLUDE_TAGF_PATH, INCLUDE_TAGF_INITIAL_OCCURRENCES, INCLUDE_TAGF_FINAL_OCCURRENCES);
	}

	@Test
	public void testTransform_indexTag() throws TransformException, IOException, URISyntaxException, ParseException {
		testTransform(TEST1_TAG_PATH, TEST1_TAG_INITIAL_OCCURRENCES, TEST1_TAG_FINAL_OCCURRENCES);
	}

	@Test
	public void testTransform_indexTagx() throws TransformException, IOException, URISyntaxException, ParseException {
		testTransform(TEST2_TAGX_PATH, TEST2_TAGX_INITIAL_OCCURRENCES, TEST2_TAGX_FINAL_OCCURRENCES);
	}

	@Test
	public void testTransform_taglibTld() throws TransformException, IOException, URISyntaxException, ParseException {
		testTransform(TAGLIB_TLD_PATH, TAGLIB_TLD_INITIAL_OCCURRENCES, TAGLIB_TLD_FINAL_OCCURRENCES);
	}

	@Test
	public void testTransform_indexXhtml() throws TransformException, IOException, URISyntaxException, ParseException {
		testTransform(INDEX_XHTML_PATH, INDEX_XHTML_INITIAL_OCCURRENCES, INDEX_XHTML_FINAL_OCCURRENCES);
	}

	@Test
	public void testTransform_raXml() throws TransformException, IOException, URISyntaxException, ParseException {
		testTransform(RA_XML_PATH, RA_XML_INITIAL_OCCURRENCES, RA_XML_FINAL_OCCURRENCES);
	}

	@Test
	public void testTransform_JavaServletProperty()
		throws TransformException, IOException, URISyntaxException, ParseException {
		testTransformJava(TEST_DATA_SERVLET_JAVA, SERVLET_INITIAL_OCCURRENCES, SERVLET_FINAL_OCCURRENCES);
	}

	@Test
	public void testTransform_ClassServletProperty()
		throws TransformException, IOException, URISyntaxException, ParseException {
		testTransformClass(TEST_DATA_SERVLET_CLASS);
	}
}
