package main;

public class identifierTable 
{
	identifier[] identifierTable = new identifier[500];
	int lastIndex;
	static String[] types = {"double","string","char","integer","short","real","float","long","bool","byte","array","function","enum","struct"};
	
	public identifierTable()
	{
		this.lastIndex = 0;
	}
	
	public int push(String symbol, String type)
	{
		if(isIdentifier(symbol) != -1)
		{	
			return -1;
		}
		switch(type)
		{
		case "double":
			identifierTable[lastIndex] = new doubleIdentifier(symbol);
			lastIndex++;
			return lastIndex-1;
		case "string":
			identifierTable[lastIndex] = new stringIdentifier(symbol);
			lastIndex++;
			return lastIndex-1;
		case "char":
			identifierTable[lastIndex] = new charIdentifier(symbol);
			lastIndex++;
			return lastIndex-1;
		case "integer":
			identifierTable[lastIndex] = new intIdentifier(symbol);
			lastIndex++;
			return lastIndex-1;
		case "short":
			identifierTable[lastIndex] = new shortIdentifier(symbol);
			lastIndex++;
			return lastIndex-1;
		case "real":
			identifierTable[lastIndex] = new realIdentifier(symbol);
			lastIndex++;
			return lastIndex-1;
		case "float":
			identifierTable[lastIndex] = new floatIdentifier(symbol);
			lastIndex++;
			return lastIndex-1;
		case "long":
			identifierTable[lastIndex] = new longIdentifier(symbol);
			lastIndex++;
			return lastIndex-1;
		case "bool":
			identifierTable[lastIndex] = new boolIdentifier(symbol);
			lastIndex++;
			return lastIndex-1;
		case "byte":
			identifierTable[lastIndex] = new byteIdentifier(symbol);
			lastIndex++;
			return lastIndex-1;
		}	
	return lastIndex-1;
	}
	
	public void pushArray(String symbol, String elementType, String size)
	{
		if(isIdentifier(symbol) != -1)
		{
			return;
		}
		identifierTable[lastIndex] = new arrayIdentifier(symbol, elementType, size);
		lastIndex++;
	}
	
	public void pushSymbol(String symbol, String value)
	{
		if(isIdentifier(symbol) != -1)
		{
			return;
		}
		identifierTable[lastIndex] = new symbolIdentifier(symbol,value);
		lastIndex++;
	}
	
	public void pushStruct(struct struct)
	{
		identifierTable[lastIndex] = struct;
		identifierTable[lastIndex].type = "struct";
		lastIndex++;
	}
	
	public void pushStructCopy(String symbol, String structType)
	{
		struct newStruct = new struct(symbol);
		newStruct.type = "struct";
		newStruct.structType = structType;
		int index = getIdenIndex(structType);
		struct currentStruct = (struct)identifierTable[index];
		newStruct.copyVariables(currentStruct);
		pushStruct(newStruct);		
	}
	
	public void pushFunction(String symbol, String returnType)
	{
		if(isIdentifier(symbol) != -1)
		{
			return;
		}
		identifierTable[lastIndex] = new functionIdentifier(symbol, returnType);
		lastIndex++;
	}
	
	public void pushEnum(String symbol, String value)
	{
		if(isIdentifier(symbol) != -1)
		{
			return;
		}
		identifierTable[lastIndex] = new enumIdentifier(symbol, value);
		lastIndex++;
	}
	public void pushEnumCopy(String symbol, String enumType)
	{
		String value = "";
		for(int i = 0; i < lastIndex-1; i++)
		{
			if(identifierTable[i].symbol.equals(enumType))
			{
				value = identifierTable[i].stringValue;
			}
		}
		identifierTable[lastIndex] = new enumIdentifier(symbol, value);
		lastIndex++;
	}
	public void displayTable()
	{
		for(int i = 0; i <= lastIndex-1; i++)
		{
			if(identifierTable[i].type == "array")
			{
				System.out.println("Identifier: "+identifierTable[i].symbol+" Type: "+identifierTable[i].elementType+" "+identifierTable[i].type+" of size "+identifierTable[i].size+" Value: "+identifierTable[i].stringValue);
			}
			else if(identifierTable[i].type == "function")
			{
				System.out.println("Identifier: "+identifierTable[i].symbol+" Type: "+identifierTable[i].type);
			}
			else if(identifierTable[i].type == "struct")
			{
				struct currentStruct = (struct)identifierTable[i];
				currentStruct.displayStruct();
			}
			else
			{
				System.out.println("Identifier: "+identifierTable[i].symbol+" Type: "+identifierTable[i].type+" Value: "+identifierTable[i].stringValue);
			}
		}
	}
	
	public static Boolean isType(String str)
	{
		for(int i = 0; i < types.length; i++)
		{
			if(str.equals(types[i]))
			{
				return true;
			}
		}
		return false;
	}
	
	public int isIdentifier(String symbol)
	{
		for(int i = 0; i <= lastIndex-1; i++)
		{
			if(identifierTable[i].symbol.equals(symbol))
			{
				return i;
			}
			else if(identifierTable[i].type.equals("struct"))
			{
				struct current = (struct)identifierTable[i];
				for(int j = 0; j < current.lastIndex; j++)
				{
					if(current.variables[j].symbol.equals(symbol))
					{
						return i;
					}
				}
			}
		}
		return -1;
	}
	
	public int getIdenIndex(String symbol)
	{
		for(int i = 0; i <= lastIndex-1; i++)
		{
			if(identifierTable[i].symbol.equals(symbol))
			{
				return i;
			}
		}
		return -1;
	}
	
	public void setIdenValue(int index, String value)
	{
		identifierTable[index].stringValue = value;
	}
}
