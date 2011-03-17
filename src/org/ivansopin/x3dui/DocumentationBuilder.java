package org.ivansopin.x3dui;

import java.util.ArrayList;

public class DocumentationBuilder {

	public static Linker linker;
	
	private ArrayList<Prototype> prototypes;
	private ArrayList<Group> groups;
	
	DocumentationBuilder() {
		prototypes = new ArrayList<Prototype>();
		groups = new ArrayList<Group>();
		
		Initializer intializer = new Initializer();
		Parser parser = new Parser();
		Documentor documentor = new Documentor();
		
		try {
			intializer.initializePrototypes(parser, prototypes, groups);
			linker = new Linker(prototypes, groups);
			//linker.linkPrototypes(prototypes, groups);
			documentor.generateDocumentation(prototypes, groups);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		new DocumentationBuilder();
	}
}
