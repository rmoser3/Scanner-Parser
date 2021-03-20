package main;

public class charIdentifier extends identifier
{
	char value;
	
	public charIdentifier(String symbol)
	{
		this.symbol = symbol;
		this.type = "char";
	}
}
