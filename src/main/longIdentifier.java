package main;

public class longIdentifier extends identifier
{
	long value;
	
	public longIdentifier(String symbol)
	{
		this.symbol = symbol;
		this.type = "long";
	}
}
