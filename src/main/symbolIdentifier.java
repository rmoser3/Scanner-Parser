package main;

public class symbolIdentifier extends identifier
{
	String value;
	
	public symbolIdentifier(String symbol, String value)
	{
		this.symbol = symbol;
		this.value = value;
		this.stringValue = value;
		this.type = "symbol";
	}
}
