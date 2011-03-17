package org.ivansopin.x3dui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Documentor {
	private String generatePrototypeAttributeDocumentation(
			Prototype prototype,
			Attribute attribute, 
			String prepend, 
			String protoPath,
			String protoName) {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(prepend + "<h4>" + attribute.getName() + "</h4>\n");

		builder.append(prepend + "<span class=\"fixedWidth\">" + attribute.getAccessType() + " " + attribute.getType() + " ");
		builder.append("<span class=\"bold\">" + attribute.getName() + "</span>");
		builder.append("; defaults to ");
		builder.append("<span class=\"bold\">" + attribute.getDefaultValue() + "</span>");
		builder.append("</span>\n");
		builder.append(prepend + "<div class=\"description\">\n");
		builder.append(prepend + "\t" + attribute.getDescription(protoPath, protoPath, protoName) + "\n");
		builder.append(prepend + "</div>\n");
		
		StringBuilder overrideBuilder = new StringBuilder();
		
		int l = prototype.getNumOfInherited();
		
		for (int i = 0; i < l; i++) {			
			if (prototype.getInheritedAt(i).hasAttribute(attribute.getName())) {
				if (overrideBuilder.length() != 0) {
					overrideBuilder.append(", ");
				}
				
				overrideBuilder.append("<a href=\"");
				overrideBuilder.append(Utils.getRelativePath(prototype.getInheritedAt(i).getPath(), protoPath, true));
				overrideBuilder.append(prototype.getInheritedAt(i).getName());
				overrideBuilder.append(Settings.DEST_EXT);
				overrideBuilder.append("\">");
				overrideBuilder.append(prototype.getInheritedAt(i).getName());
				overrideBuilder.append("</a>.");
				
				overrideBuilder.append("<a href=\"");
				overrideBuilder.append(Utils.getRelativePath(prototype.getInheritedAt(i).getPath(), protoPath, true));
				overrideBuilder.append(prototype.getInheritedAt(i).getName());
				overrideBuilder.append(Settings.DEST_EXT);
				overrideBuilder.append("#" + attribute.getName());
				overrideBuilder.append("\">");
				overrideBuilder.append(attribute.getName());
				overrideBuilder.append("</a>");
			}
		}
		
		if (overrideBuilder.length() > 0) {
			builder.append(prepend + "<div class=\"indented\">\n");
			builder.append(prepend + "<span class=\"italic\">Overrides:</span>\n");
			builder.append(prepend + overrideBuilder.toString() + "\n");
			builder.append(prepend + "</div>\n");
		}

		return builder.toString();
	}
	
	private String generatePrototypeMethodDocumentation(
			Prototype prototype,
			Method method, 
			String prepend, 
			String protoPath,
			String protoName) {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(prepend + "<h4>" + method.getName() + "</h4>\n");

		builder.append(prepend + "<span class=\"fixedWidth\">" + method.getAccessType() + " " + method.getType() + " ");
		builder.append("<span class=\"bold\">" + method.getName() + "</span>");
		builder.append("</span>\n");
		builder.append(prepend + "<div class=\"description\">\n");
		builder.append(prepend + "\t" + method.getDescription(protoPath, protoPath, protoName) + "\n");
		builder.append(prepend + "</div>\n");
		builder.append(prepend + "<br />\n");

		StringBuilder overrideBuilder = new StringBuilder();
		
		int l = prototype.getNumOfInherited();
		
		for (int i = 0; i < l; i++) {			
			if (prototype.getInheritedAt(i).hasMethod(method.getName())) {
				if (overrideBuilder.length() != 0) {
					overrideBuilder.append(", ");
				}
				
				overrideBuilder.append("<a href=\"");
				overrideBuilder.append(Utils.getRelativePath(prototype.getInheritedAt(i).getPath(), protoPath, true));
				overrideBuilder.append(prototype.getInheritedAt(i).getName());
				overrideBuilder.append(Settings.DEST_EXT);
				overrideBuilder.append("\">");
				overrideBuilder.append(prototype.getInheritedAt(i).getName());
				overrideBuilder.append("</a>.");
				
				overrideBuilder.append("<a href=\"");
				overrideBuilder.append(Utils.getRelativePath(prototype.getInheritedAt(i).getPath(), protoPath, true));
				overrideBuilder.append(prototype.getInheritedAt(i).getName());
				overrideBuilder.append(Settings.DEST_EXT);
				overrideBuilder.append("#" + method.getName());
				overrideBuilder.append("\">");
				overrideBuilder.append(method.getName());
				overrideBuilder.append("</a>");
			}
		}
		
		if (overrideBuilder.length() > 0) {
			builder.append(prepend + "<div class=\"indented\">\n");
			builder.append(prepend + "<span class=\"italic\">Overrides:</span>\n");
			builder.append(prepend + overrideBuilder.toString() + "\n");
			builder.append(prepend + "</div>\n");
		}
		
		return builder.toString();
	}
	
	private void appendPrototypeHeader(StringBuilder builder, Prototype prototype) {
		builder.append(Settings.HTML_HEADER);
		builder.append("<head>\n");
		builder.append("\t<title>" + Settings.DOC_TITLE_PREFIX + prototype.getName() + "</title>\n");
		builder.append("\t<link href=\"");
		builder.append(
			Utils.getRelativePath(
				Settings.DEST_DIR,
				prototype.getPath(),
				true)); 
		builder.append("index.css\" rel=\"stylesheet\" type=\"text/css\" />\n");
		builder.append("</head>\n");
		builder.append("<body>\n");
	}
	
	private void appendGroupHeader(StringBuilder builder, Group group) {
		builder.append(Settings.HTML_HEADER);
		builder.append("<head>\n");
		builder.append("\t<title>" + Settings.DOC_TITLE_PREFIX + group.getName() + " group</title>\n");
		builder.append("\t<link href=\"");
		builder.append(
			Utils.getRelativePath(
				Settings.DEST_DIR,
				Settings.DEST_DIR + group.getName(),
				true)); 
		builder.append("index.css\" rel=\"stylesheet\" type=\"text/css\" />\n");
		
		builder.append("\t<script type=\"text/javascript\">\n");
		builder.append( 
			"\t\tfunction expandField(t, id) {\n" + 
				"\t\t\tvar d = document.getElementById(id);\n" +
				
				"\t\t\tif (d.style.display == 'none' || d.style.display == '') {\n" +
					"\t\t\t\tt.innerHTML = '[&ndash;]';\n" +
					"\t\t\t\td.style.display = 'block';\n" +
				"\t\t\t} else {\n" +
					"\t\t\t\tt.innerHTML = '[+]';\n" +
					"\t\t\t\td.style.display = 'none';\n" +
				"\t\t\t}\n" +
			"\t\t}\n");

		builder.append("\t</script>\n");
		builder.append("</head>\n");
		builder.append("<body>\n");
	}
	
	private void appendOverviewHeader(StringBuilder builder) {
		builder.append(Settings.HTML_HEADER);
		builder.append("<head>\n");
		builder.append("\t<title>" + Settings.DOC_TITLE_PREFIX + "Overview" + "</title>\n");
		builder.append("\t<link href=\"index.css\" rel=\"stylesheet\" type=\"text/css\" />\n");
		builder.append("\t<script type=\"text/javascript\">\n");
		builder.append( 
			"\t\tfunction expandField(t, id) {\n" + 
				"\t\t\tvar d = document.getElementById(id);\n" +
				
				"\t\t\tif (d.style.display == 'none' || d.style.display == '') {\n" +
					"\t\t\t\tt.innerHTML = '[&ndash;]';\n" +
					"\t\t\t\td.style.display = 'block';\n" +
				"\t\t\t} else {\n" +
					"\t\t\t\tt.innerHTML = '[+]';\n" +
					"\t\t\t\td.style.display = 'none';\n" +
				"\t\t\t}\n" +
			"\t\t}\n");

		builder.append("\t</script>\n");
		builder.append("</head>\n");
		builder.append("<body>\n");
	}
	
	private void appendPrototypeNavigation(StringBuilder builder, Prototype prototype) {
		appendNavigation(builder, prototype.getPath(), Settings.DEST_DIR + prototype.getGroup(), "prototype");
	}
	
	private void appendGroupNavigation(StringBuilder builder, Group group) {
		appendNavigation(builder, Settings.DEST_DIR + group.getName(), "", "group");
	}
	
	private void appendOverviewNavigation(StringBuilder builder) {
		appendNavigation(builder, "", "", "overview");
	}
	
	private void appendNavigation(StringBuilder builder, String path, String group, String section) {
		builder.append("\t<div class=\"navigation\">\n");
		
		if (section.equals("overview")) {
			builder.append("\t\t<span class=\"bold\">Overview</span>\n");
		} else {
			builder.append("\t\t<a href=\"");
			builder.append(
				Utils.getRelativePath(
					Settings.DEST_DIR,
					path,
					true));
			builder.append("index" + Settings.DEST_EXT);
			builder.append("\">Overview</a>\n");
		}
		
		builder.append("\t\t | \n\t\t");
		
		if (section.equals("group")) {
			builder.append("<span class=\"bold\">Group</span>\n");
		} else if (section.equals("overview")) {
			builder.append("<span class=\"disabled\">Group</span>\n");
		} else {
			builder.append("\t\t<a href=\"");
			builder.append(
				Utils.getRelativePath(
					group,
					path,
					true)); 
			builder.append("index" + Settings.DEST_EXT);
			builder.append("\">Group</a>\n");
		}
		
		builder.append("\t\t | \n\t\t");
		
		if (section.equals("prototype")) {
			builder.append("<span class=\"bold\">Prototype</span>\n");
		} else {
			builder.append("<span class=\"disabled\">Prototype</span>\n");
		}
		
		builder.append("\t</div>\n");
		
		builder.append("\t<hr />\n");
	}
	
	private void appendPrototypeTitle(StringBuilder builder, Prototype prototype) {
		builder.append("\t<h1>" + prototype.getName() + "</h1>\n");
	}
	
	private void appendGroupTitle(StringBuilder builder, Group group) {
		builder.append("\t<h1>" + group.getName() + " group</h1>\n");
	}
	
	private void appendOverviewTitle(StringBuilder builder) {
		builder.append("\t<h1>X3DUI Overview</h1>\n");
	}
	
	private void appendPrototypeDescription(StringBuilder builder, Prototype prototype) {
		
		builder.append("\t<p class=\"indented\">\n");
		builder.append("\t\t" + prototype.getDescription(prototype.getPath() + prototype.getName()) + "\n");
		builder.append("\t</p>\n");
		builder.append("\t<br />\n");
	}
	
	private void appendGroupDescription(
			ArrayList<Group> groups, 
			ArrayList<Prototype> prototypes,
			StringBuilder builder, 
			Group group) {
		
		builder.append("\t<p class=\"indented\">\n");
		builder.append("\t\t" + group.getDescription(groups, prototypes, Settings.DEST_DIR + group.getName()) + "\n");
		builder.append("\t</p>\n");
		builder.append("\t<br />\n");
	}
	
	private void appendOverviewDescription(StringBuilder builder) {
		builder.append("\t<p class=\"indented\">\n");
		builder.append("\t\t" + Settings.OVERVIEW_DESCRIPTION + "\n");
		builder.append("\t</p>\n");
		builder.append("\t<br />\n");
	}
	
	private void appendInherited(StringBuilder builder, Prototype prototype) {
		builder.append(
			"\t<h3>Parents:</h3>\n" +
			"\t<span class=\"indented\">\n\t\t");
		
		int numOfInherited = prototype.getNumOfInherited();
		
		if (numOfInherited != 0) {
			for (int i = 0; i < numOfInherited; i++) {
				if (i != 0) {
					builder.append(", ");
				}
				
				builder.append(prototype.getInheritedNameAt(i));
			}
		} else {
			builder.append("None");
		}
		
		builder.append("\n\t</span>\n");
		
		builder.append("\n\t<br /><br />\n");
	}
	
	private void appendInheriting(StringBuilder builder, Prototype prototype) {
		builder.append(
			"\t<h3>Descendants:</h3>\n" +
			"\t<span class=\"indented\">\n\t\t");
		
		int numOfInheriting = prototype.getNumOfInheriting();
		
		if (numOfInheriting > 0) {
			for (int i = 0; i < numOfInheriting; i++) {
				if (i != 0) {
					builder.append(", ");
				}
				
				builder.append(prototype.getInheritingNameAt(i));
			}
		} else {
			builder.append("None");
		}
		
		builder.append("\n\t</span>\n");
		
		builder.append("\n\t<br /><br />\n");
	}
	
	private void appendAttributes(
			StringBuilder builder, 
			Prototype prototype) {
		
		builder.append(
			"\t<div class=\"sectionHeader\">Attributes:</div>\n" +
			"\t\t<div class=\"indented\">\n");

		int numOfAttributes = prototype.getNumOfAttributes();
		
		for (int i = 0; i < numOfAttributes; i++) {
			if (i != 0) {
				builder.append("\t\t<hr />\n");
			}
			
			builder.append("\t\t<a name=\"" + prototype.getAttributeAt(i).getName() +  "\" />\n");
			
			builder.append(generatePrototypeAttributeDocumentation(prototype, prototype.getAttributeAt(i), "\t\t\t", prototype.getPath(), prototype.getName()));
		}
		
		builder.append("\t\t</div>\n");
		
		builder.append("\n\t\t<br /><br />\n");
	}
	
	private void appendMethods(
			StringBuilder builder, 
			Prototype prototype) {
		
		builder.append(
			"\t<div class=\"sectionHeader\">Methods:</div>\n" +
			"\t\t<div class=\"indented\">\n");

		int numOfMethods = prototype.getNumOfMethods();
		
		for (int i = 0; i < numOfMethods; i++) {
			if (i != 0) {
				builder.append("\t\t<hr />\n");
			}
			
			builder.append("\t\t<a name=\"" + prototype.getMethodAt(i).getName() +  "\" />\n");
			
			builder.append(generatePrototypeMethodDocumentation(prototype, prototype.getMethodAt(i), "\t\t\t", prototype.getPath(), prototype.getName()));
		}
		
		builder.append("\t\t</div>\n");
		
		builder.append("\n\t\t<br /><br />\n");
	}
	
	private void appendPrototypes(StringBuilder builder, Group group) {
		
		builder.append(
			"\t<div class=\"sectionHeader\">Prototypes:</div>\n" +
			"\t\t<div class=\"indented\">\n");

		int numOfPrototypes = group.getNumOfPrototypes();
		int numOfAttributes, numOfMethods;
		
		Prototype prototype;
		
		builder.append("\t\t\t<table>\n");
		
		for (int i = 0; i < numOfPrototypes; i++) {
			prototype = group.getPrototype(i);
			
			builder.append("\t\t\t\t<tr>\n");
			builder.append("\t\t\t\t\t<td class=\"noWrap small\">\n");
			builder.append("\t\t\t\t\t\t<span class=\"toggler\" onclick=\"expandField(this, '" +
					prototype.getName().toLowerCase() + 
					"');\">[+]</span>\n");
			builder.append("\t\t\t\t\t</td>\n");
			builder.append("\t\t\t\t\t<td class=\"noWrap\">\n");

			
				builder.append("\t\t\t\t\t\t<a href=\"");
				builder.append(prototype.getName() + Settings.DEST_EXT);
				builder.append("\">");
				builder.append(prototype.getName());
				builder.append("</a>\n");

				
				builder.append("\t\t\t\t\t\t<div class=\"hidden indented\" id=\"" + prototype.getName().toLowerCase() + "\">\n");
				
					builder.append("\t\t\t\t\t\t\tAttributes:\n");
					builder.append("\t\t\t\t\t\t\t<div class=\"indented\">\n");
					
					numOfAttributes = prototype.getNumOfAttributes();
					
					if (numOfAttributes > 0) {
						for (int j = 0; j < numOfAttributes; j++) {
							builder.append("\t\t\t\t\t\t\t\t<a href=\"");
							builder.append(prototype.getName() + Settings.DEST_EXT);
							builder.append("#" + prototype.getAttributeNameAt(j));
							builder.append("\">");
							builder.append(prototype.getAttributeNameAt(j));
							builder.append("</a>\n");
	
							builder.append("\t\t\t\t\t\t\t\t<br />\n");
						}
					} else {
						builder.append("\t\t\t\t\t\t\t\tNone\n");
						builder.append("\t\t\t\t\t\t\t\t<br />\n");
					}
				
					builder.append("\t\t\t\t\t\t\t</div>\n");
					
					builder.append("\t\t\t\t\t\t\t<br />\n");
					
					builder.append("\t\t\t\t\t\t\tMethods:\n");
					builder.append("\t\t\t\t\t\t\t<div class=\"indented\">\n");
					
					numOfMethods = prototype.getNumOfMethods();
					
					if (numOfMethods > 0) {
						for (int j = 0; j < numOfMethods; j++) {
							builder.append("\t\t\t\t\t\t\t\t<a href=\"");
							builder.append(prototype.getName() + Settings.DEST_EXT);
							builder.append("#" + prototype.getMethodNameAt(j));
							builder.append("\">");
							builder.append(prototype.getMethodNameAt(j));
							builder.append("</a>\n");
	
							builder.append("\t\t\t\t\t\t\t\t<br />\n");
						}
					} else {
						builder.append("\t\t\t\t\t\t\t\tNone\n");
						builder.append("\t\t\t\t\t\t\t\t<br />\n");
					}
				
					builder.append("\t\t\t\t\t\t\t</div>\n");
					
				builder.append("\t\t\t\t\t\t</div>\n");
			
			builder.append("\t\t\t\t\t</td>\n");

			
			builder.append("\t\t\t\t\t<td>\n");
			
				builder.append("\t\t\t\t\t\t" + prototype.getDescription(Settings.DEST_DIR + group.getName()) + "\n");
			
			builder.append("\t\t\t\t\t</td>\n");
			builder.append("\t\t\t\t</tr>\n");
			//builder.append(generatePrototypeMethodDocumentation(prototype.getMethodAt(i), "\t\t\t", prototype.getPath()));
		}
		
		builder.append("\t\t\t</table>");
		
		builder.append("\t\t</div>\n");
		
		builder.append("\n\t\t<br /><br />\n");
	}
	
	private void appendGroups(
			ArrayList<Prototype> prototypes,
			StringBuilder builder, 
			ArrayList<Group> groups) {
		builder.append(
			"\t<div class=\"sectionHeader\">Groups:</div>\n" +
			"\t\t<div class=\"indented\">\n");

		int numOfGroups = groups.size();
		int numOfPrototypes;
		
		Group group;
		Prototype prototype;
		
		builder.append("\t\t\t<table>\n");
		
		for (int i = 0; i < numOfGroups; i++) {
			group = groups.get(i);
			
			builder.append("\t\t\t\t<tr>\n");
			builder.append("\t\t\t\t\t<td class=\"noWrap small\">\n");
			builder.append("\t\t\t\t\t\t<span class=\"toggler\" onclick=\"expandField(this, '" +
					group.getName().toLowerCase() + 
					"');\">[+]</span>\n");
			builder.append("\t\t\t\t\t</td>\n");
			builder.append("\t\t\t\t\t<td class=\"noWrap\">\n");

			
				builder.append("\t\t\t\t\t\t<a href=\"");
				builder.append(group.getName() + "/index" + Settings.DEST_EXT);
				builder.append("\">");
				builder.append(group.getName());
				builder.append("</a>\n");

				
				builder.append("\t\t\t\t\t\t<div class=\"hidden indented\" id=\"" + group.getName().toLowerCase() + "\">\n");
				
					builder.append("\t\t\t\t\t\t\tPrototypes:\n");
					builder.append("\t\t\t\t\t\t\t<div class=\"indented\">\n");
					
					numOfPrototypes = group.getNumOfPrototypes();
					
					if (numOfPrototypes > 0) {
						for (int j = 0; j < numOfPrototypes; j++) {
							prototype = group.getPrototype(j);
							
							builder.append("\t\t\t\t\t\t\t\t<a href=\"");
							builder.append(group.getName() + "/" + prototype.getName() + Settings.DEST_EXT);
							builder.append("\">");
							builder.append(prototype.getName());
							builder.append("</a>\n");
	
							builder.append("\t\t\t\t\t\t\t\t<br />\n");
						}
					} else {
						builder.append("\t\t\t\t\t\t\t\tNone\n");
						builder.append("\t\t\t\t\t\t\t\t<br />\n");
					}
				
					builder.append("\t\t\t\t\t\t\t</div>\n");
					
				builder.append("\t\t\t\t\t\t</div>\n");
			
			builder.append("\t\t\t\t\t</td>\n");

			
			builder.append("\t\t\t\t\t<td>\n");
			
				builder.append("\t\t\t\t\t\t" + group.getDescription(groups, prototypes, Settings.DEST_DIR) + "\n");
			
			builder.append("\t\t\t\t\t</td>\n");
			builder.append("\t\t\t\t</tr>\n");
			//builder.append(generatePrototypeMethodDocumentation(prototype.getMethodAt(i), "\t\t\t", prototype.getPath()));
		}
		
		builder.append("\t\t\t</table>");
		
		builder.append("\t\t</div>\n");
		
		builder.append("\n\t\t<br /><br />\n");
	}
	
	private void appendFooter(StringBuilder builder) {
		builder.append("</body>\n");
		builder.append(Settings.HTML_FOOTER);
	}
	
	private String generatePrototypeDocumentation(
			ArrayList<Group> groups, 
			ArrayList<Prototype> prototypes,
			Prototype prototype) throws IOException {
		
		StringBuilder builder = new StringBuilder();
		
		appendPrototypeHeader(builder, prototype);
		appendPrototypeNavigation(builder, prototype);
		appendPrototypeTitle(builder, prototype);
		appendPrototypeDescription(builder, prototype);
		appendInherited(builder, prototype);
		appendInheriting(builder, prototype);
		
		if (prototype.getNumOfAttributes() > 0) {
			appendAttributes(builder, prototype);
		}
		
		if (prototype.getNumOfMethods() > 0) {
			appendMethods(builder, prototype);
		}
		
		appendFooter(builder);

		return builder.toString();
	}
	
	private String generateGroupDocumentation(
			ArrayList<Group> groups, 
			ArrayList<Prototype> prototypes,
			Group group) throws IOException {
		
		StringBuilder builder = new StringBuilder();

		appendGroupHeader(builder, group);
		appendGroupNavigation(builder, group);
		appendGroupTitle(builder, group);
		appendGroupDescription(groups, prototypes, builder, group);
		appendPrototypes(builder, group);
		appendFooter(builder);
		
		return builder.toString();
	}
	
	private String generateOverviewDocumentation(ArrayList<Group> groups, ArrayList<Prototype> prototypes) throws IOException {
		StringBuilder builder = new StringBuilder();

		appendOverviewHeader(builder);
		appendOverviewNavigation(builder);
		appendOverviewTitle(builder);
		appendOverviewDescription(builder);
		appendGroups(prototypes, builder, groups);
		
		/*
		appendInherited(builder, group);
		appendInheriting(builder, group);
		appendAttributes(builder, group);
		appendMethods(builder, group);
		*/
		
		appendFooter(builder);
		
		return builder.toString();
	}
	
	void generateDocumentation(ArrayList<Prototype> prototypes, ArrayList<Group> groups) {
		BufferedWriter writer;
		File outFile;
		Prototype prototype;
		Group group;
		
		int numOfPrototypes = prototypes.size();
		int numOfGroups = groups.size();
		
		for (int i = 0; i < numOfPrototypes; i++) {
			try {
				prototype = prototypes.get(i);
				
				// initialize the writer
				outFile = new File(Settings.DEST_DIR + prototype.getGroup() + "/" + prototype.getName() + Settings.DEST_EXT);

				if (outFile.exists()) {
					outFile.delete();
				}
				
				writer = new BufferedWriter(new FileWriter(outFile));

				writer.write(generatePrototypeDocumentation(groups, prototypes, prototype));
				
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < numOfGroups; i++) {
			try {
				group = groups.get(i);
				
				// initialize the writer
				outFile = new File(Settings.DEST_DIR + group.getName() + "/index" + Settings.DEST_EXT);

				if (outFile.exists()) {
					outFile.delete();
				}
				
				writer = new BufferedWriter(new FileWriter(outFile));

				writer.write(generateGroupDocumentation(groups, prototypes, group));
				
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
			outFile = new File(Settings.DEST_DIR + "index" + Settings.DEST_EXT);
			
			if (outFile.exists()) {
				outFile.delete();
			}
			
			writer = new BufferedWriter(new FileWriter(outFile));
			
			writer.write(generateOverviewDocumentation(groups, prototypes));
			
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
