package main;

public class byteIdentifier extends identifier
{
	Byte value;
	
	public byteIdentifier(String symbol)
	{
		this.symbol = symbol;
		this.type = "byte";
	}
}
