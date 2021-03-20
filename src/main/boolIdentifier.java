package main;

public class boolIdentifier extends identifier
{
	Boolean value;
	
	public boolIdentifier(String symbol)
	{
		this.symbol = symbol;
		this.type = "boolean";
	}
}
