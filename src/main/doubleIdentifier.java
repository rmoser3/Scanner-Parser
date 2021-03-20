package main;

public class doubleIdentifier extends identifier
{
	double value;
	
	public doubleIdentifier(String symbol)
	{
		this.symbol = symbol;
		this.type = "double";
	}
}
