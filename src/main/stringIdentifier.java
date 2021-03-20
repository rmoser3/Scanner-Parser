package main;

public class stringIdentifier extends identifier
{
	String value;
	
	public stringIdentifier(String symbol)
	{
		this.symbol = symbol;
		this.type = "string";
	}
}
