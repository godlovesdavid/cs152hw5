import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import backend.Executor;
import frontend.Parser;

public class Main
{
	/**
	 * runs program, calling parser to parse all content and then and calls content printer to print contents.
	 * @param args first argument being the name of file to read in
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException
	{
		new Parser().parse(readFileAsString(args[0]));
		Executor.execute();
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
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		char[] buffer = new char[1024];
		int numread = 0;
		while ((numread = reader.read(buffer)) != -1)
			data.append(String.valueOf(buffer, 0, numread));
		reader.close();
		return data.toString();
	}
}
