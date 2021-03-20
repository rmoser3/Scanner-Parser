package main;

public class intIdentifier extends identifier
{
	int value;
	
	public intIdentifier(String symbol)
	{
		this.symbol = symbol;
		this.type = "integer";
	}

}
