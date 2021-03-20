package main;

public class arrayIdentifier extends identifier
{

	public arrayIdentifier(String symbol, String elementType, String size)
	{
		this.symbol = symbol;
		this.type = "array";
		this.elementType = elementType;
		this.size = size; 
	}
	
}
