package main;

public class enumIdentifier extends identifier
{
	
	public enumIdentifier(String symbol, String value)
	{
		this.symbol = symbol;
		this.stringValue = value;
		this.type = "enum";
	}
}
