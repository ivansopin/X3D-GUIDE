package org.ivansopin.x3dui;

import java.util.ArrayList;
import java.util.HashMap;

public class Linker {
	ArrayList<Group> groups;
	private ArrayList<Prototype> prototypes;
	
	
	private HashMap<String, Integer> prototypeIndices;
	private HashMap<String, Integer> groupIndices;
	
	Linker(ArrayList<Prototype> prototypes, ArrayList<Group> groups) {
		this.groups = groups;
		this.prototypes = prototypes;
		
		indexPrototypes();
		indexGroups();

		Prototype prototype;
		Prototype otherPrototype;

		String inherited;

		int numOfPrototypes = prototypes.size();
		int numOfInherited;

		// interlinking inheriting and descending prototype objects
		for (int i = 0; i < numOfPrototypes; i++) {
			prototype = prototypes.get(i);
			
			numOfInherited = prototype.getNumOfInherited();
			
			for (int j = 0; j < numOfInherited; j++) {
				inherited = prototype.getInheritedNameAt(j, true);
				otherPrototype = prototypes.get(getPrototypeIndex(inherited));
				prototype.addInherited(otherPrototype);
				otherPrototype.addInheriting(prototype);
				otherPrototype.addInheritingName(prototype.getName());
			}
		}
	}
	
	private void indexPrototypes() {
		prototypeIndices = new HashMap<String, Integer>();
		
		for (int i = 0; i < prototypes.size(); i++) {
			prototypeIndices.put(prototypes.get(i).getName(), i);
		}
	}
	
	private void indexGroups() {
		groupIndices = new HashMap<String, Integer>();
		
		for (int i = 0; i < groups.size(); i++) {
			groupIndices.put(groups.get(i).getName(), i);
		}
	}
	
	private int getPrototypeIndex(String name) {
		return prototypeIndices.get(name);
	}
	
	private int getGroupIndex(String name) {
		return groupIndices.get(name);
	}
	
	String embedProtoReferences(String text, String protoPath) {
		String workText = text;
		String formattedText = "";
		
		int begIndex;
		int endIndex;
		
		String element;
		Prototype otherPrototype;
		
		while (true) {
			begIndex = workText.indexOf("<proto>");
			endIndex = workText.indexOf("</proto>");
			
			if (begIndex > -1 && endIndex > -1) {
				element = workText.substring(begIndex + 7, endIndex);

				otherPrototype = prototypes.get(getPrototypeIndex(element));
				
				formattedText += 
					workText.substring(0, begIndex) +
					"<a href=\"" + Utils.getRelativePath(otherPrototype.getPath(), protoPath, true) + element + Settings.DEST_EXT + "\">" +
					otherPrototype.getName() + 
					"</a>";
				
				workText = workText.substring(endIndex + 8);
			} else {
				formattedText += workText;
				break;
			}
		}
		
		return formattedText;
	}
	
	String embedGroupReferences(String text, String path) {
		String workText = text;
		String formattedText = "";
		
		int begIndex;
		int endIndex;
		
		String element;
		Group otherGroup;
		
		while (true) {
			begIndex = workText.indexOf("<group>");
			endIndex = workText.indexOf("</group>");
			
			if (begIndex > -1 && endIndex > -1) {
				element = workText.substring(begIndex + 7, endIndex);

				otherGroup = groups.get(getGroupIndex(element));
				
				formattedText += 
					workText.substring(0, begIndex) +
					"<a href=\"" + Utils.getRelativePath(Settings.DEST_DIR + otherGroup.getName(), path, true) + "index.html\">" +
					otherGroup.getName() + 
					"</a>";
				
				workText = workText.substring(endIndex + 8);
			} else {
				formattedText += workText;
				break;
			}
		}
		
		return formattedText;
	}
	
	String embedFieldReferences(String text, String pathFromRoot, String protoName, String path) {
		String workText = text;
		String formattedText = "";
		
		int begIndex;
		int endIndex;
		
		String element;
		
		while (true) {
			begIndex = workText.indexOf("<field>");
			endIndex = workText.indexOf("</field>");
			
			if (begIndex > -1 && endIndex > -1) {
				element = workText.substring(begIndex + 7, endIndex);
				
				formattedText += 
					workText.substring(0, begIndex) +
					"<a href=\"" + Utils.getRelativePath(pathFromRoot, path, true) +
					protoName + Settings.DEST_EXT + 
					"#" + element + 
					"\">" +
					element + 
					"</a>";
				
				workText = workText.substring(endIndex + 8);
			} else {
				formattedText += workText;
				break;
			}
		}
		
		return formattedText;
	}
}
