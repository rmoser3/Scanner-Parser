package main;

public class struct extends identifier 
{
	identifier[] variables;
	int lastIndex;
	identifierTable identifierTable = new identifierTable();
	
	public struct(String symbol) 
	{
		this.symbol = symbol;
		variables = new identifier[10];
		lastIndex = 0;
	}
	
	public int pushVariable(String symbol, String type)
	{
		switch(type)
		{
		case "double":
			variables[lastIndex] = new doubleIdentifier(symbol);
			lastIndex++;
			return lastIndex-1;
		case "string":
			variables[lastIndex] = new stringIdentifier(symbol);
			lastIndex++;
			return lastIndex-1;
		case "char":
			variables[lastIndex] = new charIdentifier(symbol);
			lastIndex++;
			return lastIndex-1;
		case "integer":
			variables[lastIndex] = new intIdentifier(symbol);
			lastIndex++;
			return lastIndex-1;
		case "short":
			variables[lastIndex] = new shortIdentifier(symbol);
			lastIndex++;
			return lastIndex-1;
		case "real":
			variables[lastIndex] = new realIdentifier(symbol);
			lastIndex++;
			return lastIndex-1;
		case "float":
			variables[lastIndex] = new floatIdentifier(symbol);
			lastIndex++;
			return lastIndex-1;
		case "long":
			variables[lastIndex] = new longIdentifier(symbol);
			lastIndex++;
			return lastIndex-1;
		case "bool":
			variables[lastIndex] = new boolIdentifier(symbol);
			lastIndex++;
			return lastIndex-1;
		case "byte":
			variables[lastIndex] = new byteIdentifier(symbol);
			lastIndex++;
			return lastIndex-1;
		}	
	return lastIndex-1;
	}
	
	public void pushArray(String symbol, String elementType, String size)
	{
		if(identifierTable.isIdentifier(symbol) != -1)
		{
			return;
		}
		variables[lastIndex] = new arrayIdentifier(symbol, elementType, size);
		lastIndex++;
	}
	
	public void displayStruct()
	{
		System.out.print("Identifier: "+this.symbol+" Type: struct variables: ");
		for(int i = 0; i < this.lastIndex; i++)
		{
			System.out.print(variables[i].symbol+", "+variables[i].type+"; ");
		}
		System.out.println("");
	}
	
	public void copyVariables(struct struct)
	{
		for(int i = 0; i < struct.lastIndex; i++)
		{
			this.variables[i] = struct.variables[i];
			this.lastIndex++;
		}
	}
	
}
