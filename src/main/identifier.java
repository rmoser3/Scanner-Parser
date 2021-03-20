package main;

public abstract class identifier
{
	String symbol;
	String type;
	public String stringValue; 
	
	//The following variables are only relevant to arrays 
	String size; 
	String elementType; 
	
	//Only relevant to structs
	String structType;
}
