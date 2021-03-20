package parser;

public class Program 
{
	public static void main(String[] args)
	{
		parser parser = new parser();
		
		//Execute the scanner
		main.program.main(args);
		
		while(parser.queue.head != null)
		{
			parser.getNext();
		}
		
//		parser.showStates();
	}
}
