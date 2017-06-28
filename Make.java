package com.depmaven.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Make {

	private static String EXTENTION = ".jar";
	
	private static String TEMP_FILE = "temp_pom.xml";

	public static void main(String[] args) {

		final Make make = new Make();

		final List<String> listJar = make.listerRepertoire(new File("C:\\temp\\MavenDep"));

		StringBuilder data = make.makeFileDep(listJar);
		
		try {
			make.writeFile(data, new File(Make.TEMP_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void writeFile(StringBuilder data, File file) throws IOException {
		Path path = Paths.get(file.getAbsolutePath());
		if(!Files.exists(path)){
		    Files.createFile(path);
		}
		BufferedWriter writer = Files.newBufferedWriter(path);
		writer.write(data.toString());
		writer.close();
	}

	private StringBuilder makeFileDep(List<String> listJar) {

		final StringBuilder depdep = new StringBuilder();
		for (String jar : listJar) {
			String[] tab = jar.split(Pattern.quote("."));			
			String tutu = tab[0];
			
			depdep.append("<dependency>");
			depdep.append("<groupId>org."+tutu+"</groupId>");
			depdep.append("<artifactId>"+tutu+"</artifactId>");
			depdep.append("<version>0.0.0</version>");
			depdep.append("<scope>system</scope>");
			depdep.append("<systemPath>${lib-tom}/"+jar+"</systemPath>");
			depdep.append("<type>jar</type>");
			depdep.append("</dependency>");
			//depdep.append(System.lineSeparator());
		}
		
		return depdep;
	}

	private List<String> listerRepertoire(File repertoire) {

		String[] listefichiers;

		final List<String> listName = new ArrayList<>();
		listefichiers = repertoire.list();
		int i;
		for (i = 0; i < listefichiers.length; i++) {
			if (listefichiers[i].endsWith(Make.EXTENTION)) {
				listName.add(listefichiers[i]);
			}
		}

		return listName;
	}

}
