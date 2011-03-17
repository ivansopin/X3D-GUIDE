package org.ivansopin.x3dui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LibraryBuilder {
	private static boolean LOG = false;
	private static boolean MINIFY = true;
	
	private static final String EXTENSION = ".x3d";
	
	private static final String SRC_DIR = "D:/workspace/X3D GUI Library/x3dui/protos/";
	private static final String DEST_DIR = "D:/workspace/X3D GUI Library/x3dui/library/";
	
	private static final String[] FILE_LIST = new String[] {
		// from layout folder
		"layout/LayoutManager", "layout/BorderLayout", "layout/BoxLayout", "layout/FlowLayout", "layout/GridLayout",
		// from visual folder
		"visual/Rectangle", "visual/Layer", "visual/Plane", "visual/Label", "visual/Button", "visual/CheckBox", "visual/ComboBox", "visual/ControlButton", "visual/Frame", "visual/Panel", "visual/RadioButton", "visual/HorizontalRunner", "visual/HorizontalSlider", "visual/TabPanel", "visual/TaskBar", "visual/TextButton", "visual/TextField", "visual/ToggleButton", "visual/TextToggleButton",
		// from group folder
		"group/RadioButtonGroup",
		// from system folder
		"system/Settings", "system/Display"
	};
	private static final String OUTPUT_FILE_NAME = "x3dui";
	
	private static final String X3D_HEADER = 
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
		"<!DOCTYPE X3D PUBLIC \"ISO//Web3D//DTD X3D 3.2//EN\"   \"http://www.web3d.org/specifications/x3d-3.2.dtd\">\n" +
		"<X3D profile=\"Immersive\" version=\"3.2\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema-instance\" xsd:noNamespaceSchemaLocation=\"http://www.web3d.org/specifications/x3d-3.2.xsd\">\n" +
		"<head />\n" +
		"<Scene>";

	private static final String X3D_FOOTER = "\n</Scene>\n" + "</X3D>";
	
	
	private static File getFile(String fileName) {
		File file = new File(SRC_DIR + fileName + EXTENSION);
		
		if (file.exists()) {
			return file;
		} else {
			return null;
		}
	}
	
	private static boolean isImportantLine(String line) {
		if (isScriptComment(line)) {
			return false;
		} else if (line.length() < 1) {
			return false;
		} else {
			return true;
		}
	}
	
	private static boolean isScriptComment(String content) {
	    return content.startsWith("//");
	}
	
	private static String stripComments(String content) {
	    return content.replaceAll("<!--(?:(?!-->).)*-->", "");
	}
	
	private static String stripTabs(String content) {
		return content.replaceAll("\t", "");
	}
	
	/** reads up to, and returns the first needed line of text */
	private static String skipToStart(BufferedReader reader) throws IOException {
		String line;
		
		while ((line = reader.readLine()) != null) {
			if (line.contains("<ProtoDeclare")) {
				return "\n" + line + (MINIFY ? "" : "\n");
			}
		}
		
		return "";
	}
	
	/** reads up to, and returns everything until the last needed line of text */
	private static String readToEnd(BufferedReader reader) throws IOException {
		StringBuilder builder = new StringBuilder();
		String line;
		
		if (MINIFY) {
			while ((line = reader.readLine().trim()) != null) {
				if (line.contains("</Scene>")) {
					return builder.toString();
				} else if (isImportantLine(line)) {
					builder.append(line);
				}
			}
		} else {
			while ((line = reader.readLine()) != null) {
				if (line.contains("</Scene>")) {
					return builder.toString();
				} else {
					builder.append(line).append("\n");
				}
			}
		}
		
		return builder.toString();
	}
	
	/** writes the string to the file */
	private static void write(BufferedWriter writer, String content) throws IOException {
		if (MINIFY) {
			writer.write(stripTabs(stripComments(content)));
		} else {
			writer.write(content);
		}
	}
	
	public static void main(String[] args) {
		BufferedReader reader;
		BufferedWriter writer;
		File inFile;
		File outFile;
		String content;
		
		outFile = new File(DEST_DIR + OUTPUT_FILE_NAME + EXTENSION);
		
		if (outFile.exists()) {
			outFile.delete();
		}
		
		try {
			writer = new BufferedWriter(new FileWriter(outFile));
			write(writer, X3D_HEADER);
		} catch (Exception e) {
			e.printStackTrace();
			
			return;
		}
		
		for (String fileName : FILE_LIST) {
			try {
				inFile = getFile(fileName);
				
				if (inFile == null) {
					continue;
				}
				
				if (LOG) System.out.println("Parsing " + fileName);
				
				reader = new BufferedReader(new FileReader(inFile));

				content = skipToStart(reader) + /*"\n" +*/ readToEnd(reader);
				
				reader.close();
				
				write(writer, content);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (LOG) System.out.println("Saved to  " + outFile.getAbsolutePath());
		
		try {
			write(writer, X3D_FOOTER);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
