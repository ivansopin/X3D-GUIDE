package org.ivansopin.x3dui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Initializer {
	void initializePrototypes(Parser parser, ArrayList<Prototype> prototypes, ArrayList<Group> groups) throws Exception {
		BufferedReader reader;
		File inFile;
		Prototype prototype;
		Group group;
		
		for (String fileName : Settings.FILE_LIST) {
			// initialize the reader
			inFile = new File(Settings.SRC_DIR + fileName + Settings.SRC_EXT);
			reader = new BufferedReader(new FileReader(inFile));
			
			// get the XML content of X3D file
			String content = getXML(reader);
			
			// close the reader
			reader.close();
			
			// parse X3D file
			prototype = parser.parseX3DFile(content);
			
			// set path and group
			int slashPos = fileName.lastIndexOf('/');
			prototype.setPath(Settings.DEST_DIR + fileName.substring(0, slashPos));
			prototype.setGroup(fileName.substring(0, slashPos));
			
			// add to the array
			prototypes.add(prototype);
		}
		
		int numOfPrototypes = prototypes.size();
		int numOfGroups;
		
		for (int i = 0; i < numOfPrototypes; i++) {
			numOfGroups = groups.size();
			
			group = null;
			
			for (int j = 0; j < numOfGroups; j++) {
				if (groups.get(j).getName().equals(prototypes.get(i).getGroup())) {
					group = groups.get(j);
					break;
				}
			}
			
			if (group == null) {
				group = new Group();
				group.setName(prototypes.get(i).getGroup());
				
				inFile = new File(Settings.SRC_DIR + group.getName() + "/group");
				
				if (inFile.exists()) {
					reader = new BufferedReader(new FileReader(inFile));
					
					group.setDescription(getFile(reader));
					
					reader.close();
				} else {
					group.setDescription("");
				}
				
				groups.add(group);
			}
			
			group.addPrototype(prototypes.get(i));
		}
	}
	
	
	private String getXML(BufferedReader reader) throws Exception {
		StringBuilder builder = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null) {
			line = line.trim();
			
			if (line.startsWith("<?xml")) {
				continue;
			} else if (line.startsWith("<!DOCTYPE")) {
				continue;
			} else {
				builder.append(line).append("\n");
			}
		}

		return builder.toString();
	}
	
	private String getFile(BufferedReader reader) throws Exception {
		StringBuilder builder = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null) {
			line = line.trim();
			
			builder.append(line).append("\n");
		}

		return builder.toString();
	}
}
