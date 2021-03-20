package main;

public class floatIdentifier extends identifier
{
	float value;
	
	public floatIdentifier(String symbol)
	{
		this.symbol = symbol;
		this.type = "float";
	}
}
