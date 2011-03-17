package org.ivansopin.x3dui;

import java.util.ArrayList;

public class Group {
	String name;
	String description;
	ArrayList<Prototype> prototypes;
	
	public Group() {
		prototypes = new ArrayList<Prototype>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription(ArrayList<Group> groups, ArrayList<Prototype> prototypes, String path) {
		String text = description;
		text = DocumentationBuilder.linker.embedProtoReferences(text, path);
		text = DocumentationBuilder.linker.embedGroupReferences(text, path);
		
		return text;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public ArrayList<Prototype> getPrototypes() {
		return prototypes;
	}

	public void addPrototype(Prototype prototype) {
		this.prototypes.add(prototype);
	}
	
	public int getNumOfPrototypes() {
		return prototypes.size();
	}

	public Prototype getPrototype(int index) {
		return prototypes.get(index);
	}

}
