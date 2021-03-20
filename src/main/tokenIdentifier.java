package main;
public class tokenIdentifier 
{
	//Control variables
    Boolean define = false; //tells us if the next token is being defined as an identifier
    Boolean setType = false; //that tells us if we are declaring a variable type
    Boolean set = false; //tells us if we are setting a variables value
    Boolean setValue = false; //tells us if the next token is a variables value
    Boolean symbol = false; //that tells us if we are defining a symbol 
    Boolean setSymbolValue = false; //that tells us if we are setting a symbol value
    Boolean parameters = false; //tells us if we are scanning a function parameter list
    Boolean array = false; //tells us if we are about to identify an array
    Boolean function = false; //tells us if we are defining a function 
    Boolean enumerate = false; //tells uf if we are defining an enum 
    Boolean enumer = false; //tells us if we are setting a variable to an enum type
    Boolean defineStruct = false; //tells us if we are defining a struct 
    Boolean struct = false; //tells us if we are setting a variable to a struct type 
    
    //Information holders 
    String lastIdentifier = ""; //holds the symbol of the last identifier scanned 
    String lastValue = ""; //holds the value to be assigned to an identifier
    String arraySize = "undeclared"; //holds value of size of array to be declared 
    struct lastStruct;
    int result; //gives the index of a token in its respective table 
    
    //Token tables
    keywordTable keywordTable = new keywordTable();
    identifierTable identifierTable = new identifierTable();
    operatorTable operatorTable = new operatorTable();
    
    //A queue of tokens that is populated during analysis and accessed by the parser as needed
    public static tokenQueue queue = new tokenQueue();
    
  //identifies a single token, setting control variables if necessary
    public void scanToken(String token, int lineNum, int columnNum) 
    {
    	if(token == "" || token == " ")
    	{
    		return;
    	}
    	if(parameters) //if a function parameter section
    	{
    		result = operatorTable.isOperator(token);
    		if(result != -1)
    		{
    			System.out.println(operatorTable.getType(token)+" operator, symbol: "+token+" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    			queue.push(token, lineNum, columnNum);
    			return;
    		}
    		scanParameterList(token,lineNum,columnNum);
    		return;
    	}
    	else if(defineStruct)
    	{
    		scanStruct(token,lineNum,columnNum);
    		return;
    	}
    	else if(define) //if defining variables
    	{
    		scanDefine(token,lineNum,columnNum);
    		return;
    	}
    	else if(set) //if setting a variables value 
    	{
    		scanSet(token,lineNum,columnNum);
    		return;
    	}
    	else if(function) //if defining a function
    	{
    		if(token.equals("main"))
    		{
    			queue.push(token, lineNum, columnNum);
    			function = false;
    		}
    		else
    		{
    			scanFunction(token,lineNum,columnNum);
        		return;
    		}	
    	}
    	else if(enumerate) //if defining enumerate
    	{
    		scanEnum(token,lineNum,columnNum);
    		return; 
    	}
    	else if(symbol) //if defining symbol
    	{
    		scanSymbol(token,lineNum,columnNum);
    		return;
    	}
    	else
    	{
    		result = keywordTable.isKeyword(token);
    		if(keywordTable.isKeyword(token)!= -1)
    		{
        		System.out.println("keyword, symbol: " + token +" line: "+lineNum+" column: "+(columnNum+1-token.length()));
        		queue.push(token, lineNum, columnNum);
        		if(token.equals("define"))
                {
                	define = true;
                	lastIdentifier = "";
                }
                else if(token.equals("type"))
                {
                	setType = true;
                }
                else if(token.equals("symbol"))
                {
                	symbol = true;
                }
                else if(token.equals("parameters"))
                {
                	parameters = true;
                	setType = false;
                }
                else if(token.equals("array"))
                {
                	array = true;
                }
                else if(token.equals("set"))
                {
                	set = true;
                }
                else if(token.equals("function"))
                {
                	function = true;
                }
                else if(token.equals("enumerate"))
                {
                	enumerate = true; 
                }
                else if(token.equals("struct"))
                {
                	defineStruct = true;
                }
        		return;
    		}
    		result = identifierTable.isIdentifier(token);
    		if(result != -1)
    		{
        		System.out.println("identifier, symbol: "+token+" line: "+lineNum+" column: "+(columnNum+1-token.length()));
        		queue.push("identifier", lineNum, columnNum);
        		return;
    		}
    		result = identifierTable.isIdentifier(token);
    		if(result != -1)
    		{
    			System.out.println(operatorTable.getType(token)+" operator, symbol: "+token+" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    			queue.push(token, lineNum, columnNum);
    			return;
    		}
    		else if(isNumeric(token))
    		{
    			System.out.println("real constant, symbol: "+token+" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    			queue.push("number", lineNum, columnNum);
    		}
    		else if(isHex(token))
    		{
    			System.out.println("hex constant, symbol: "+token+" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    			queue.push("number", lineNum, columnNum);
    		}
    		else if(operatorTable.containsOperator(token))
    		{
    			dissect(token,lineNum,columnNum);
    		}
    	}
    }
    
    //this function breaks down a string that contains lexemes with operators 
    public void dissect(String token, int lineNum, int columnNum)
    {    
    	if(token == "")
    	{
    		return;
    	}
    	String currentString = "";
    	int startColumn = columnNum - token.length() +1;
    	if(!operatorTable.containsOperator(token))
    	{
    		identifyToken(token,lineNum,columnNum);
    		return;
    	}
    	for(int i = 0; i < token.length(); i++)
    	{
    		if(operatorTable.isOperator(String.valueOf(token.charAt(i)))!= -1)
    		{
    			identifyToken(currentString,lineNum,columnNum-1);
    			identifyToken(String.valueOf(token.charAt(i)),lineNum,startColumn+currentString.length());
    			dissect(token.substring(i+1,token.length()),lineNum,columnNum);
    			break;
    		}
    		else
    		{
    			currentString += String.valueOf(token.charAt(i));
    		}
    	}	
    }
    
    //scans a define section to populate identifier table
    public void scanDefine(String token, int lineNum, int columnNum)
    {
    	if(lastIdentifier.equals(""))
    	{
    		lastIdentifier = token;
    		System.out.println("identifier, symbol: "+token+" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    		queue.push("identifier", lineNum, columnNum);
    	}
    	else if(keywordTable.isKeyword(token) != -1 && !setType)
    	{
    		System.out.println("keyword, symbol: " + token +" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    		queue.push(token, lineNum, columnNum);
    		if(token.equals("type"))
    		{
    			setType = true;
    		}
    		else if(token.equals("array"))
    		{
    			array = true;
    		}
    		else if(token.equals("struct"))
    		{
    			struct = true;
    		}
    		else if(token.equals("enum"))
    		{
    			enumer = true;
    		}
    		else if(token.equals("struct"))
    		{
    			struct = true;
    		}
    	}
    	else if(operatorTable.isOperator(token)!= -1)
		{
			if(token.equals("="))
			{
				setValue = true;
			}
			System.out.println(operatorTable.getType(token)+" operator, symbol: "+token+" line: "+lineNum+" column: "+(columnNum+1-token.length()));
			queue.push(token, lineNum, columnNum);
		}
    	else if(setValue) //if setting a variable value 
    	{
    		lastValue = token;
    		if(isNumeric(token))
    		{
    			System.out.println("real constant, symbol: "+token+" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    			queue.push("number", lineNum, columnNum);
    		}
    		else if(isHex(token))
    		{
    			System.out.println("hex constant, symbol: "+token+" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    			queue.push("number", lineNum, columnNum);
    		}
    		setValue = false;
    	}
    	else if(operatorTable.containsOperator(token))
    	{
    		dissect(token,lineNum,columnNum);
    	} 	
    	else if(enumer)
    	{
    		System.out.println("identifier, symbol: "+token+" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    		queue.push("identifier", lineNum, columnNum);
    		identifierTable.pushEnumCopy(lastIdentifier,token);
    		lastIdentifier = "";
    		enumer = false; 
    		define = false;
    	}
    	else if(struct)
    	{
    		System.out.println("identifier, symbol: "+token+" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    		queue.push("identifier", lineNum, columnNum);
    		identifierTable.pushStructCopy(lastIdentifier, token);
    		lastIdentifier = "";
    		struct = false; 
    		define = false;
    	}
    	else if(setType && identifierTable.isType(token) && !array)
    	{
    		System.out.println("keyword, symbol: " + token +" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    		queue.push(token, lineNum, columnNum);
    		if(defineStruct)
    		{
    			lastStruct.pushVariable(lastIdentifier, token);
    		}
    		else
    		{
        		identifierTable.push(lastIdentifier, token);
    		}
    		if(lastValue != "")
    		{
    			identifierTable.setIdenValue(identifierTable.lastIndex-1, lastValue);
    		}
    		setType = false;
    		define = false;
    		lastIdentifier = "";
    		lastValue = "";
    	}
    	else if(setType && identifierTable.isType(token) && array)
    	{
    		if(defineStruct)
    		{
    			lastStruct.pushArray(lastIdentifier, token, arraySize);
    		}
    		else
    		{
        		identifierTable.pushArray(lastIdentifier, token, arraySize);
    		}
    		System.out.println("keyword, symbol: " + token +" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    		queue.push(token, lineNum, columnNum);
    		array = false;
    		setType = false;
    		define = false;
    		lastIdentifier = "";
    		lastValue = "";
    	}
    }
    
    //scans a parameter section to populate the identifier table
    public void scanParameterList(String token, int lineNum, int columnNum) 
    {
    	if(lastIdentifier == "")
    	{
    		System.out.println("identifier, symbol: "+token+" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    		queue.push("identifier", lineNum, columnNum);
    		lastIdentifier = token;
    	}
    	else if(keywordTable.isKeyword(token) != -1 && !setType)
    	{
    		System.out.println("keyword, symbol: " + token +" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    		queue.push(token, lineNum, columnNum);
    		if(token.equals("array"))
    		{
    			array = true;
    		}
    		else if(token.equals("type"))
    		{
    			setType = true;
    		}
    	}
    	else if(operatorTable.containsOperator(token) && !setType)
    	{
    		dissect(token,lineNum,columnNum);
    	}
    	else if(setType && !array)
    	{
    		if(token.contains(","))
    		{
    			identifyToken(token.substring(0,token.length()-1),lineNum,columnNum);
    			identifyToken(token.substring(token.length()-1),lineNum,columnNum);
    			identifierTable.push(lastIdentifier, token.substring(0,token.length()-1));
    			
    		}
    		else if(keywordTable.isKeyword(token)!= -1)
    		{
        		System.out.println("keyword, symbol: " + token +" line: "+lineNum+" column: "+(columnNum+1-token.length()));
        		queue.push(token, lineNum, columnNum);
    			identifierTable.push(lastIdentifier, token);
    			parameters = false;
    		}
    		lastIdentifier = "";
    		setType = false;
    	}
    	else if(setType && array)
    	{
    		if(token.contains(","))
    		{
    			identifyToken(token.substring(0,token.length()-1),lineNum,columnNum);
    			identifyToken(token.substring(token.length()-1),lineNum,columnNum);
    			identifierTable.pushArray(lastIdentifier, token.substring(0,token.length()-1),arraySize);
    		}
    		else if(keywordTable.isKeyword(token) != -1)
    		{
        		System.out.println("keyword, symbol: " + token +" line: "+lineNum+" column: "+(columnNum+1-token.length()));
        		queue.push(token, lineNum, columnNum);
    			identifierTable.pushArray(lastIdentifier,token,arraySize);
    			parameters = false;
    		}
    		lastIdentifier = "";
    		setType = false;
    		array = false;
    		arraySize = "undeclared";
    	}
    }
    
    
    //scans a function declaration line 
    public void scanFunction(String token, int lineNum, int columnNum)
    {
    	if(keywordTable.isKeyword(token) != -1)
    	{
    		System.out.println("keyword, symbol: " + token +" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    		queue.push(token, lineNum, columnNum);
    		if(identifierTable.isType(token))
    		{
    			identifierTable.pushFunction(lastIdentifier,token);
    			lastIdentifier = "";
    			function = false;
    		}
    	}
    	else
    	{
    		System.out.println("identifier, symbol: "+token+" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    		queue.push("identifier", lineNum, columnNum);
    		lastIdentifier = token;
    	}
    }
    
    //scans an enumerate declaration section 
    public void scanEnum(String token, int lineNum, int columnNum)
    {
    	if(keywordTable.isKeyword(token) == -1 && !setValue)
    	{
    		System.out.println("identifier, symbol: "+token+" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    		queue.push("identifier", lineNum, columnNum);
    		lastIdentifier = token;
    	}
    	else if(keywordTable.isKeyword(token) != -1 && !setValue)
    	{
    		System.out.println("keyword, symbol: " + token +" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    		queue.push(token, lineNum, columnNum);
    		if(token.equals("is"))
    		{
    			setValue = true;
    		}
    	}
    	else if(keywordTable.isKeyword(token) != -1 && setValue)
    	{
    		System.out.println("keyword, symbol: " + token +" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    		queue.push(token, lineNum, columnNum);
    		if(token.equals("endenum"))
    		{
    			identifierTable.pushEnum(lastIdentifier, lastValue);
    			lastIdentifier = "";
    			lastValue = "";
    			setValue = false;
    			enumerate = false; 
    		}
    	}
    	else if(setValue)
    	{
    		lastValue += token;
    	}
    }
    
    //scans a symbol declaration 
    public void scanSymbol(String token, int lineNum, int columnNum)
    {
    	if(lastIdentifier.equals(""))
    	{
    		System.out.println("identifier, symbol: "+token+" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    		queue.push("identifier", lineNum, columnNum);
    		lastIdentifier = token;
    		setSymbolValue = true;
    	}
    	else if(setSymbolValue)
    	{
    		if(lastIdentifier == "")
    		{
    			return;
    		}
    		if(isNumeric(token))
    		{
    			System.out.println("real constant, symbol: "+token+" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    			queue.push("number", lineNum, columnNum);
    		}
    		else if(isHex(token))
    		{
    			System.out.println("hex constant, symbol: "+token+" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    			queue.push("number", lineNum, columnNum);
    		}
    		identifierTable.pushSymbol(lastIdentifier, token);
    		setSymbolValue = false;  	
    		symbol = false;
    		lastIdentifier = "";
    	}
    }
    
    //scans a "set" line to add a value to the identifier table 
    public void scanSet(String token, int lineNum, int columnNum) 
    {
    	if(!setValue)
    	{
    		lastValue = "";
    	}
    	if(identifierTable.isIdentifier(token) != -1 && !setValue)
    	{
    		lastIdentifier = token;
    		System.out.println("identifier, symbol: "+token+" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    		queue.push("identifier", lineNum, columnNum);
    	}
    	else if(operatorTable.isOperator(token) != -1 && !setValue)
    	{
			System.out.println(operatorTable.getType(token)+" operator, symbol: "+token+" line: "+lineNum+" column: "+(columnNum+1-token.length()));
			queue.push(token, lineNum, columnNum);
			if(token.equals("="))
			{
				setValue = true;
			}			
    	}
    	else if(setValue && keywordTable.isKeyword(token) == -1 )
    	{

    		if(operatorTable.containsOperator(token))
    		{
    			dissect(token,lineNum,columnNum);
    		}
    		else
    		{
    			identifyToken(token,lineNum,columnNum);
    		}
    		lastValue += token;
    	}
    	else if(setValue && keywordTable.isKeyword(token) != -1)
    	{
    		identifyToken(token,lineNum,columnNum);
    		int index = identifierTable.getIdenIndex(lastIdentifier);
        	identifierTable.setIdenValue(index, lastValue);
    		lastIdentifier = "";
    		lastValue = "";
    		set = false;
    		if(token.equals("set"))
    		{
    			set = true;
    		}
    		setValue = false;
    	}
    }
    
    //scans a struct definition
    public void scanStruct(String token, int lineNum, int columnNum)
    {
    	if(lastStruct == null && lastIdentifier.equals(""))
    	{
    		lastIdentifier = token; 
    		System.out.println("identifier, symbol: "+token+" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    		queue.push("identifier", lineNum, columnNum);
    		lastStruct = new struct(lastIdentifier);
    		lastIdentifier = "";
    	}
    	else if(define)
    	{
    		scanDefine(token,lineNum,columnNum);
    	}
    	else if(keywordTable.isKeyword(token) != -1)
    	{
    		System.out.println("keyword, symbol: " + token +" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    		queue.push(token, lineNum, columnNum);
    		if(token.equals("define"))
    		{
    			define = true; 
    		}
    		else if(token.equals("endstruct"))
    		{
    			defineStruct = false;
    			identifierTable.pushStruct(lastStruct);
    			lastStruct = null;
    		}
    	}

    }
    
    //a terminal function to identify a single token
    public void identifyToken(String token, int lineNum, int columnNum) 
    {
    	if(token.equals("")||token.equals(" "))
    	{
    		return;
    	}
    	if(keywordTable.isKeyword(token) != -1)
		{
    		System.out.println("keyword, symbol: " + token +" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    		queue.push(token, lineNum, columnNum);
    		if(token.equals("array"))
    		{
    			array = true;
    		}
		}
		else if(identifierTable.isIdentifier(token) != -1)
		{
    		System.out.println("identifier, symbol: "+token+" line: "+lineNum+" column: "+(columnNum+1-token.length()));
    		queue.push("identifier", lineNum, columnNum);
    		if(array)
    		{
    			arraySize = token;
    		}
		}
		else if(operatorTable.isOperator(token) != -1)
		{
			System.out.println(operatorTable.getType(token)+" operator, symbol: "+token+" line: "+lineNum+" column: "+(columnNum+1-token.length()));
			queue.push(token, lineNum, columnNum);
		}
		else if(isNumeric(token))
		{
			System.out.println("real constant, symbol: "+token+" line: "+lineNum+" column: "+(columnNum+1-token.length()));
			queue.push("number", lineNum, columnNum);
			if(array)
			{
				arraySize = token;
			}
		}
		else if(isHex(token))
		{
			System.out.println("hex constant, symbol: "+token+" line: "+lineNum+" column: "+(columnNum+1-token.length()));
			queue.push("number", lineNum, columnNum);
		}
    }
    
    //tells us if a lexeme is a real constant 
    public Boolean isNumeric(String token) 
    {
    	try
    	{
    		Double.parseDouble(token);
    		return true;
    	}
    	catch(NumberFormatException e)
    	{
    		return false;
    	}
    }
    public Boolean isHex(String token)
    {
    	try
    	{
    		Long.parseLong(token.substring(0, token.length()-1),16);
    		return true;
    	}
    	catch(NumberFormatException e)
    	{
    		return false;
    	}
    }
}
