package io.github.purpleloop.test.liqtest;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Generates a given amount of change sets. Files are created under /generated
 * directory. Inclusion elements for the root change set file are written on
 * system output.
 */
public class Duplicate {

	/** Number of change sets. */
	private static final int NUMBER_OF_SCRIPTS = 30;

	/** Default constructor of utility class. */
	private Duplicate() {
	}

	/**
	 * Generator entry point.
	 * 
	 * @param args no use here
	 */
	public static void main(String[] args) {

		try {

			Path templatePath = Paths.get("src/main/resources/parts/template.xml");
			Path generationPath = Paths.get("generated");

			Files.createDirectories(generationPath);

			for (int i = 1; i <= NUMBER_OF_SCRIPTS; i++) {

				final int index = i;

				Path targetPath = generationPath.resolve(i + ".xml");

				try (Stream<String> lines = Files.lines(templatePath);
						BufferedWriter writer = Files.newBufferedWriter(targetPath);) {

					lines.map(line -> line.replaceAll("NUMBER", Integer.toString(index))).forEach(line -> {
						try {
							writer.write(line + "\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
					});

					writer.flush();
				}

				System.out.println("    <include file=\"parts/" + i + ".xml\" relativeToChangelogFile=\"true\" />");
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
