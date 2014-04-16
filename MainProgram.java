import intermediate.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import backend.ContentPrinter;
import frontend.Parser;

public class MainProgram
{
	/**
	 * runs program, calling parser to parse all content and then and calls content printer to print contents.
	 * @param args first argument being the name of file to read in
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException
	{
		Parser parser = new Parser();
		parser.parse(readFileAsString(args[0]));

		ContentPrinter contentprinter = new ContentPrinter();
		for (Node node : parser.listrootnodes)
		{
			System.out.println();
			contentprinter.printCode(node);
			System.out.println("\nScope symbol tables:");
			contentprinter.printTokens(node);
		}
	}

	/**
	 * read file and return its contents as string
	 * @param filepath path of file
	 * @return contents of file as string
	 * @throws IOException 
	 */
	static String readFileAsString(String filepath) throws IOException
	{
		StringBuffer data = new StringBuffer();
		BufferedReader reader;
		reader = new BufferedReader(new FileReader(filepath));
		char[] buffer = new char[1024];
		int numread = 0;
		while ((numread = reader.read(buffer)) != -1)
			data.append(String.valueOf(buffer, 0, numread));
		reader.close();
		return data.toString();
	}
}
