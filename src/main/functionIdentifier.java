package main;

public class functionIdentifier extends identifier
{
	String returnType;
	public functionIdentifier(String symbol, String returnType)
	{
		this.symbol = symbol; 
		this.returnType = returnType;
		this.type = "function";
	}
}
