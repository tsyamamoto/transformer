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

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.transformer.TransformException;
import org.eclipse.transformer.action.impl.InputBufferImpl;
import org.eclipse.transformer.action.impl.SelectionRuleImpl;
import org.eclipse.transformer.action.impl.SignatureRuleImpl;
import org.eclipse.transformer.action.impl.TextActionImpl;
import org.eclipse.transformer.util.InputStreamData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

	public static final String	INDEX_XHTML_SIMPLE_NAME				= "index.xhtml";
	public static final String	INDEX_XHTML_PATH					= TEST_DATA_PREFIX + '/' + INDEX_XHTML_SIMPLE_NAME;

	public static final String	RA_XML_SIMPLE_NAME					= "ra.xml";
	public static final String	RA_XML_PATH							= TEST_DATA_PREFIX + '/' + RA_XML_SIMPLE_NAME;

	public static final String	JAVAX_JMS_QUEUE_CONECTION_FACTORY	= "\"javax.jms.QueueConnectionFactory\"";
	public static final String	JAVAX_JMS_QUEUE						= "\"javax.jms.Queue\"";
	public static final String	JAKARTA_JMS_QUEUE_CONECTION_FACTORY	= "\"jakarta.jms.QueueConnectionFactory\"";
	public static final String	JAKARTA_JMS_QUEUE					= "\"jakarta.jms.Queue\"";

	public static final String	JAVAX_SERVLET_HTTP_COOKIE			= "javax.servlet.http.Cookie";
	public static final String	JAKARTA_SERVLET_HTTP_COOKIE			= "jakarta.servlet.http.Cookie";
	public static final String	JAVAX_SERVLET_JSP_JSPWRITER			= "javax.servlet.jsp.JspWriter";
	public static final String	JAKARTA_SERVLET_JSP_JSPWRITER		= "jakarta.servlet.jsp.JspWriter";

	public static final String	JAVAX_FACES							= "javax.faces";
	public static final String	JAKARTA_FACES						= "jakarta.faces";

	public static final String	JAVAX_RESOURCE_CCI_CONNECTIONFACTORY				= ">javax.resource.cci.ConnectionFactory<";
	public static final String	JAVAX_RESOURCE_CCI_CONNECTION						= ">javax.resource.cci.Connection<";
	public static final String	JAVAX_RESOURCE_SPI_SECURITY_PASSWORDCREDENTIAL		= ">javax.resource.spi.security.PasswordCredential<";

	public static final String	JAKARTA_RESOURCE_CCI_CONNECTIONFACTORY				= ">jakarta.resource.cci.ConnectionFactory<";
	public static final String	JAKARTA_RESOURCE_CCI_CONNECTION						= ">jakarta.resource.cci.Connection<";
	public static final String	JAKARTA_RESOURCE_SPI_SECURITY_PASSWORDCREDENTIAL	= ">jakarta.resource.spi.security.PasswordCredential<";
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
			Map<String, Map<String, String>> useXmlUpdates = new HashMap<>(10);

			Map<String, String> sunResourcesXmlUpdates = new HashMap<>(2);
			sunResourcesXmlUpdates.put(JAVAX_JMS_QUEUE_CONECTION_FACTORY, JAKARTA_JMS_QUEUE_CONECTION_FACTORY);
			sunResourcesXmlUpdates.put(JAVAX_JMS_QUEUE, JAKARTA_JMS_QUEUE);
			useXmlUpdates.put(SUN_RESOURCES_XML_SIMPLE_NAME, sunResourcesXmlUpdates);

			Map<String, String> glassfishResourcesXmlUpdates = new HashMap<>(2);
			glassfishResourcesXmlUpdates.put(JAVAX_JMS_QUEUE_CONECTION_FACTORY, JAKARTA_JMS_QUEUE_CONECTION_FACTORY);
			glassfishResourcesXmlUpdates.put(JAVAX_JMS_QUEUE, JAKARTA_JMS_QUEUE);
			useXmlUpdates.put(GLASSFISH_RESOURCES_XML_SIMPLE_NAME, glassfishResourcesXmlUpdates);

			Map<String, String> includeJspfUpdates = new HashMap<>(1);
			includeJspfUpdates.put(JAVAX_SERVLET_HTTP_COOKIE, JAKARTA_SERVLET_HTTP_COOKIE);
			useXmlUpdates.put(INCLUDE_JSPF_SIMPLE_NAME, includeJspfUpdates);

			Map<String, String> indexJspUpdates = new HashMap<>(2);
			indexJspUpdates.put(JAVAX_SERVLET_HTTP_COOKIE, JAKARTA_SERVLET_HTTP_COOKIE);
			indexJspUpdates.put(JAVAX_SERVLET_JSP_JSPWRITER, JAKARTA_SERVLET_JSP_JSPWRITER);
			useXmlUpdates.put(INDEX_JSP_SIMPLE_NAME, indexJspUpdates);

			Map<String, String> indexJspxUpdates = new HashMap<>(2);
			indexJspxUpdates.put(JAVAX_SERVLET_HTTP_COOKIE, JAKARTA_SERVLET_HTTP_COOKIE);
			indexJspxUpdates.put(JAVAX_SERVLET_JSP_JSPWRITER, JAKARTA_SERVLET_JSP_JSPWRITER);
			useXmlUpdates.put(INDEX_JSPX_SIMPLE_NAME, indexJspxUpdates);

			Map<String, String> includeTagfUpdates = new HashMap<>(1);
			includeTagfUpdates.put(JAVAX_SERVLET_HTTP_COOKIE, JAKARTA_SERVLET_HTTP_COOKIE);
			useXmlUpdates.put(INCLUDE_TAGF_SIMPLE_NAME, includeTagfUpdates);

			Map<String, String> test1TagUpdates = new HashMap<>(2);
			test1TagUpdates.put(JAVAX_SERVLET_HTTP_COOKIE, JAKARTA_SERVLET_HTTP_COOKIE);
			test1TagUpdates.put(JAVAX_SERVLET_JSP_JSPWRITER, JAKARTA_SERVLET_JSP_JSPWRITER);
			useXmlUpdates.put(TEST1_TAG_SIMPLE_NAME, test1TagUpdates);

			Map<String, String> test2TagxUpdates = new HashMap<>(2);
			test2TagxUpdates.put(JAVAX_SERVLET_HTTP_COOKIE, JAKARTA_SERVLET_HTTP_COOKIE);
			test2TagxUpdates.put(JAVAX_SERVLET_JSP_JSPWRITER, JAKARTA_SERVLET_JSP_JSPWRITER);
			useXmlUpdates.put(TEST2_TAGX_SIMPLE_NAME, test2TagxUpdates);

			Map<String, String> indexXhtmlUpdates = new HashMap<>(1);
			indexXhtmlUpdates.put(JAVAX_FACES, JAKARTA_FACES);
			useXmlUpdates.put(INDEX_XHTML_SIMPLE_NAME, indexXhtmlUpdates);

			Map<String, String> raXmlUpdates = new HashMap<>(3);
			raXmlUpdates.put(JAVAX_RESOURCE_CCI_CONNECTIONFACTORY, JAKARTA_RESOURCE_CCI_CONNECTIONFACTORY);
			raXmlUpdates.put(JAVAX_RESOURCE_CCI_CONNECTION, JAKARTA_RESOURCE_CCI_CONNECTION);
			raXmlUpdates.put(JAVAX_RESOURCE_SPI_SECURITY_PASSWORDCREDENTIAL,
				JAKARTA_RESOURCE_SPI_SECURITY_PASSWORDCREDENTIAL);
			useXmlUpdates.put(RA_XML_SIMPLE_NAME, raXmlUpdates);

			masterXmlUpdates = useXmlUpdates;
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

	//

	protected static final class Occurrences {
		public final String	tag;
		public final int	count;

		public Occurrences(String tag, int count) {
			this.tag = tag;
			this.count = count;
		}
	}

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
		throws TransformException, IOException {

		System.out.println("Transform [ " + resourceRef + " ] ...");

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

	//
	@Test
	public void testTransform_sunResourcesXml() throws TransformException, IOException {
		testTransform(SUN_RESOURCES_XML_PATH, SUN_RESOURCES_XML_INITIAL_OCCURRENCES, SUN_RESOURCES_XML_FINAL_OCCURRENCES);
	}

	@Test
	public void testTransform_glassfishResourcesXml() throws TransformException, IOException {
		testTransform(GLASSFISH_RESOURCES_XML_PATH, GLASSFISH_RESOURCES_XML_INITIAL_OCCURRENCES, GLASSFISH_RESOURCES_XML_FINAL_OCCURRENCES);
	}

	@Test
	public void testTransform_includeJspf() throws TransformException, IOException {
		testTransform(INCLUDE_JSPF_PATH, INCLUDE_JSPF_INITIAL_OCCURRENCES, INCLUDE_JSPF_FINAL_OCCURRENCES);
	}

	@Test
	public void testTransform_indexJsp() throws TransformException, IOException {
		testTransform(INDEX_JSP_PATH, INDEX_JSP_INITIAL_OCCURRENCES, INDEX_JSP_FINAL_OCCURRENCES);
	}

	@Test
	public void testTransform_indexJspx() throws TransformException, IOException {
		testTransform(INDEX_JSPX_PATH, INDEX_JSPX_INITIAL_OCCURRENCES, INDEX_JSPX_FINAL_OCCURRENCES);
	}

	@Test
	public void testTransform_includeTagf() throws TransformException, IOException {
		testTransform(INCLUDE_TAGF_PATH, INCLUDE_TAGF_INITIAL_OCCURRENCES, INCLUDE_TAGF_FINAL_OCCURRENCES);
	}

	@Test
	public void testTransform_indexTag() throws TransformException, IOException {
		testTransform(TEST1_TAG_PATH, TEST1_TAG_INITIAL_OCCURRENCES, TEST1_TAG_FINAL_OCCURRENCES);
	}

	@Test
	public void testTransform_indexTagx() throws TransformException, IOException {
		testTransform(TEST2_TAGX_PATH, TEST2_TAGX_INITIAL_OCCURRENCES, TEST2_TAGX_FINAL_OCCURRENCES);
	}

	@Test
	public void testTransform_indexXhtml() throws TransformException, IOException {
		testTransform(INDEX_XHTML_PATH, INDEX_XHTML_INITIAL_OCCURRENCES, INDEX_XHTML_FINAL_OCCURRENCES);
	}

	@Test
	public void testTransform_raXml() throws TransformException, IOException {
		testTransform(RA_XML_PATH, RA_XML_INITIAL_OCCURRENCES, RA_XML_FINAL_OCCURRENCES);
	}
}
