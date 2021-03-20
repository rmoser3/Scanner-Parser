package main;
import java.io.*;
import java.util.*;
public class fileReader 
{

	//Control variables
    Boolean description = false;//tells us if the current token is part of code description 
    Boolean quote = false; //tells us if the current token is insides of quotes
    
    tokenIdentifier tokenIdentifier = new tokenIdentifier();

    public void scanLines() //extracts individual lines from the input file 
    {
    	File file = new File("arrayex1b.scl");
		try 
		{
			int lineNum = 1; //tracks which line is currently being processed
			Scanner sc = new Scanner(file);
			while(sc.hasNext())
			{
				scanLexemes(sc.nextLine(),lineNum); //scans lines 1 by 1, identifying tokens 
				lineNum++;
			}
			System.out.println("END OF FILE");
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
    }

    public void scanLexemes(String line, int lineNum) //extracts individual strings from the input line
    {

        String currentString = "";
        
        for(int i = 0; i < line.length(); i++)
        {
            if(!description && String.valueOf(line.charAt(i)).contains("\"")) //if quote found, do not look for keywords, skip to end of quoted phrase and output string literal
            {
            	int column = i+1;
                if(quote == false)
                {
                    quote = true;
                }
                while(quote && i < line.length())
                {
                    currentString += line.charAt(i);
                    i++;
                    if (String.valueOf(line.charAt(i)).contains("\""))
                    {
                        currentString += line.charAt(i);
                        quote = false;
                    }
                }
                System.out.println("string literal, symbol: " + currentString+" line: "+lineNum+" column: "+column);
                tokenIdentifier.queue.push("string literal", lineNum, column);
            }
            else if(line.charAt(i) == ' ') //if current index blank 
            {
                if(currentString != "") //evaluate the currentString, then reset it 
                {
                    if (currentString.contains("//")) //if comment, stop scanning this line
                    {
                        return;
                    }
                    else if (!description) //if not part of description, not within a quote and keyword is found, identify the token
                    {
                        tokenIdentifier.scanToken(currentString, lineNum, i);
                    }                    
                    if (currentString.equals("description")) //if description begin
                    {
                        description = true;
                    }
                    else if(currentString.equals("*/")) //if description end
                    {
                        description = false;
                    }
                    currentString = "";
                }
            }
            else //if not white space, add next char to string
            {
                currentString += line.charAt(i);
                if (i == line.length() - 1) //if end of line, evaluate currentstring, then reset it and move to next line
                {
                    if (currentString.contains("//")) //if comment, stop scanning this line
                    {
                        return;
                    }
                    else if (!description) //if not part of the description, identify the token 
                    {
                        tokenIdentifier.scanToken(currentString, lineNum, i+1);
                    }                                
                    if (currentString.equals("description")) //if description begin
                    {
                        description = true;
                    }
                    else if(currentString.equals("*/")) //if description end 
                    {
                        description = false; 
                    }
                    currentString = "";
                }
            }
        }
    }
}
