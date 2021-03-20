package main;

public class realIdentifier extends identifier
{
	double value;
	
	public realIdentifier(String symbol)
	{
		this.symbol = symbol;
		this.type = "real";
	}
}
