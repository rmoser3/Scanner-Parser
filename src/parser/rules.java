package parser;

public class rules 
{

	String name;
	int lastIndex;
	String[] lhs = new String[10];
	
	public rules(String name)
	{
		this.name = name;
		this.lastIndex = 0;
		
		if(name == "import")
		{
			lhs[0] = "string literal";
		}
		else if(name == "symbol")
		{
			lhs[0] = "identifier number";
		}
		else if(name == "forward")
		{
			lhs[0] = "<function_def>";
		}
	}
	

	
}
