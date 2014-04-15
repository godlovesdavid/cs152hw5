import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import frontend.Parser;

public class MainProgram
{
	/**
	 * runs program, calling parser to parse all content and then and calls content printer to print contents.
	 * @param args first argument being the name of file to read in
	 */
	public static void main(String[] args)
	{
		Parser parser = new Parser();
		parser.parse(readFileAsString(args[0]));
	}

	/**
	 * read file and return its contents as string
	 * @param filePath path of file
	 * @return contents of file
	 */
	static String readFileAsString(String filePath)
	{
		StringBuffer fileData = new StringBuffer();
		BufferedReader reader;
		try
		{
			reader = new BufferedReader(new FileReader(filePath));

			char[] buf = new char[1024];
			int numRead = 0;
			while ((numRead = reader.read(buf)) != -1)
			{
				String readData = String.valueOf(buf, 0, numRead);
				fileData.append(readData);
			}
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return fileData.toString();
	}
}
