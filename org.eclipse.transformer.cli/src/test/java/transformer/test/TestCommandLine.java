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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.eclipse.transformer.TransformOptions;
import org.eclipse.transformer.Transformer;
import org.eclipse.transformer.action.impl.DirectoryActionImpl;
import org.eclipse.transformer.action.impl.JavaActionImpl;
import org.eclipse.transformer.action.impl.ManifestActionImpl;
import org.eclipse.transformer.jakarta.JakartaTransformer;
import org.eclipse.transformer.util.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestCommandLine {

	private static final String	STATIC_CONTENT_DIR			= "src/test/data/command-line";
	private static final String DYNAMIC_CONTENT_DIR          = "target/test/data/command-line";

	private String currentDirectory	= ".";

	@BeforeEach
	public void setUp() {
		currentDirectory = System.getProperty("user.dir");
		System.out.println("setUp: Current directory is: [" + currentDirectory + "]");
		System.out.println("setUp: Static content directory is: [" + STATIC_CONTENT_DIR + "]");
		System.out.println("setUp: Dynamic content directory is: [" + DYNAMIC_CONTENT_DIR + "]");

		TestUtils.verifyDirectory(STATIC_CONTENT_DIR, !TestUtils.DO_CREATE, "static content");
		TestUtils.verifyDirectory(DYNAMIC_CONTENT_DIR, TestUtils.DO_CREATE, "dynamic content");
	}

	@AfterEach
	public void tearDown() {
		File staticContentDir = new File(STATIC_CONTENT_DIR);
		File outputAjava = new File(staticContentDir, "output_A.java");
		File outputCommandLine = new File(staticContentDir.getParentFile(), "output_command-line");
		if (outputAjava.exists()) {
			outputAjava.delete();
		}
		if (outputCommandLine.exists()) {
			for (File file : outputCommandLine.listFiles()) {
				file.delete();
			}
			outputCommandLine.delete();
		}
	}

	@Test
	void testManifestActionAccepted() throws Exception {
		String inputFileName = STATIC_CONTENT_DIR + '/' + "MANIFEST.MF";
		String outputFileName = DYNAMIC_CONTENT_DIR + '/' + "MANIFEST.MF";
		verifyAction(ManifestActionImpl.class.getName(), inputFileName, outputFileName, outputFileName);
	}

	@Test
	void testJavaActionAccepted() throws Exception {
		String inputFileName = STATIC_CONTENT_DIR + '/' + "A.java";
		String outputFileName = DYNAMIC_CONTENT_DIR + '/' + "A.java";
		verifyAction(JavaActionImpl.class.getName(), inputFileName, outputFileName, outputFileName);
	}

	@Test
	void testInputFileAndOutputDirectoryAccepted() throws Exception {
		String inputFileName = new File(STATIC_CONTENT_DIR + '/' + "A.java").getAbsolutePath()
			.replace("\\", "/");
		File tmp = new File(DYNAMIC_CONTENT_DIR, "work" + System.currentTimeMillis());
		tmp.mkdir();
		String outputFileName = DYNAMIC_CONTENT_DIR + '/' + tmp.getName();
		verifyAction(JavaActionImpl.class.getName(), inputFileName, outputFileName,
			outputFileName + "/A.java");
	}

	@Test
	void testInputDirectoryNameOnlyAccepted() throws Exception {
		String inputFileName = STATIC_CONTENT_DIR;
		File inputFile = new File(inputFileName);
		String realOutputFileName = new File(inputFile.getParentFile(), "output_" + inputFile.getName())
			.getCanonicalPath();
		verifyAction(DirectoryActionImpl.class.getName(), inputFileName, null, FileUtils.normalize(realOutputFileName));
	}

	@Test
	void testInputDirectoryNameOnlyWithLastSlashAccepted() throws Exception {
		String inputFileName = STATIC_CONTENT_DIR + '/';
		File inputFile = new File(inputFileName);
		String realOutputFileName = new File(inputFile.getParentFile(), "output_" + inputFile.getName())
			.getCanonicalPath();
		verifyAction(DirectoryActionImpl.class.getName(), inputFileName, null, FileUtils.normalize(realOutputFileName));
	}

	@Test
	void testInputFileNameOnlyAccepted() throws Exception {
		String inputFileName = STATIC_CONTENT_DIR + "/A.java";
		File inputFile = new File(inputFileName);
		String realOutputFileName = new File(inputFile.getParentFile(), "output_" + inputFile.getName())
			.getCanonicalPath();
		verifyAction(JavaActionImpl.class.getName(), inputFileName, null, FileUtils.normalize(realOutputFileName));
	}

	@Test
	void testInvalidOutputDirectoryRejected() throws Exception {
		String inputFileName = STATIC_CONTENT_DIR + '/';
		String outputFileName = STATIC_CONTENT_DIR + '/' + "foo";
		verifyActionInvalidDirectoryRejected(DirectoryActionImpl.class.getName(), inputFileName, outputFileName);
	}

	@Test
	void testSetLogLevel() throws Exception {
		Transformer t = new Transformer(System.out, System.err);
		t.setArgs(new String[] {
			"-ll", "debug"
		});
		t.setParsedArgs();
		TransformOptions options = t.createTransformOptions();
		options.setLogging();
	}

	private void verifyAction(String actionClassName, String inputFileName, String outputFileName,
		String realOutputFileName) throws Exception {
		Transformer t = new Transformer(System.out, System.err);

		t.setOptionDefaults(JakartaTransformer.class, JakartaTransformer.getOptionDefaults());

		String[] args = outputFileName != null ?
			new String[] {
				inputFileName, outputFileName, "-o"
			} : new String[] {
				inputFileName, "-o"
			};

		t.setArgs(args);
		t.setParsedArgs();

		TransformOptions options = t.createTransformOptions();
		options.setLogging();

		assertTrue(options.setInput(), "options.setInput() failed");
		assertEquals(inputFileName, options.getInputFileName(),
			"input file name is not correct [" + options.getInputFileName() + "]");

		assertTrue(options.setOutput(), "options.setOutput() failed");
		assertEquals(realOutputFileName, options.getOutputFileName(),
			"output file name is not correct [" + options.getOutputFileName() + "]");

		assertTrue(options.setRules(), "options.setRules() failed");
		assertTrue(options.acceptAction(), "options.acceptAction() failed");
		assertEquals(actionClassName, options.acceptedAction.getClass().getName());

		options.transform();
		assertTrue((new File(realOutputFileName)).exists(), "output file not created");
	}

	private void verifyActionInvalidDirectoryRejected(String actionClassName, String inputFileName,
		String outputFileName) throws Exception {
		Transformer t = new Transformer(System.out, System.err);

		t.setOptionDefaults(JakartaTransformer.class, JakartaTransformer.getOptionDefaults());

		String[] args = new String[] {
			inputFileName, outputFileName, "-o"
		};

		t.setArgs(args);
		t.setParsedArgs();

		TransformOptions options = t.createTransformOptions();
		options.setLogging();

		assertTrue(options.setInput(), "options.setInput() failed");
		assertEquals(inputFileName, options.getInputFileName(),
			"input file name is not correct [" + options.getInputFileName() + "]");

		assertTrue(!options.setOutput(), "options.setOutput() unexpectedly successed");
	}
}
