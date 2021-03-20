package parser;

public class parser 
{
	main.tokenQueue queue = main.tokenIdentifier.queue;
	
	//Control variables
	boolean imports = false;
	boolean symbol = false;
	boolean forward = false;
	boolean function = false;
	boolean funcDef = false;
	boolean specifications = false;
	boolean global = false;
	boolean implementations = false;
	boolean parameters = false;
	boolean enumerate = false;
	
	public void getNext()
	{
//		System.out.println("get next");
		String nextToken = queue.pop();
//		System.out.println(nextToken);
		if(!changeState(nextToken))
		{
			isLegal(nextToken);
		}
	}
	
	public boolean changeState(String token)
	{	
		if(token.equals("import"))
		{
//			System.out.println("Changing state to import");
			imports = true;
			return true;
		}
		else if(token.equals("symbol"))
		{
//			System.out.println("Changing state to symbol");
			imports = false;
			symbol = true;
			return true;
		}
		else if(token.equals("forward"))
		{
			token += " " + queue.peep();
			if(token.equals("forward declarations"))
			{
//				System.out.println("Changing state to forward");
				queue.pop();
				//when in forward, the only thing we can have are function definitions
				symbol = false;
				forward = true;
				return true;
			}
		}
		else if(token.equals("specifications"))
		{
//			System.out.println("Changing state to specifications");
			forward = false;
			function = false;
			specifications = true;
		}
		else if(token.equals("global"))
		{
			token += " " + queue.peep();
			if(token.equals("global declarations"))
			{
//				System.out.println("Changing state to global");
				queue.pop();
				specifications = false;
				global = true;
			}
		}
		else if(token.equals("implementations"))
		{
			System.out.println("Changing state to implementations");
			implementations = true;
			global = false;
		}	
		else if(token.equals("parameters"))
		{
			parameters = true;
		}
		return false;
	}
	
	public boolean isLegal(String token)
	{
//		System.out.println("is legal");
		if(imports)
		{
			return isLegalImport(token);
		}
		else if(parameters)
		{
			return isLegalParameters(token);
		}
		else if(function)
		{
			return isLegalFunctionDef(token);
		}
		else if(symbol)
		{
			return isLegalSymbol(token);
		}
		else if(forward)
		{
			return isLegalForward(token);
		}
		else if(specifications)
		{
			return isLegalSpecs(token);
		}
		else if(global)
		{
			return isLegalGlobal(token);
		}
		else if(implementations)
		{
			return isLegalImplementations(token);
		}
		return false;
	}
	
	public boolean isLegalImplementations(String token)
	{
		boolean flag = true;
		while(queue.head != null)
		{
			while(queue.peep().equals("description"))
			{
				queue.pop();
			}
			if(queue.groupPeep(2).equals("function main is"))
			{
				queue.groupPop(3);
				System.out.println(queue.peep());
				boolean functionImp = isLegalFunctionImp();
			}
			else if(queue.groupPeep(1).equals("function identifier"))
			{
				queue.pop();
				System.out.println(queue.peep());
				token = queue.pop();
				isLegalFunctionDef(token);
				token = queue.pop();
				isLegalParameters(token);
				System.out.println(queue.peep()+queue.head.lineNum);
				//LEFT OFF HERE
			}
		}	
		return false;
	}
	
	public boolean isLegalFunctionImp()
	{
		boolean variables = false;
		boolean actions = false;
		if(queue.peep().equals("variables"))
		{
			queue.pop();
			variables = isLegalVariables();
		}
		if(queue.peep().equals("begin"))
		{
			queue.pop();
			actions = isLegalActions();
		}
		if(queue.groupPeep(2).equals("exit endfun main") || queue.groupPeep(2).equals("exit endfun identifier"))
		{
			queue.groupPop(3);
		}
		return false;
	}
	
	public boolean isLegalActions()
	{
		boolean set = false;
		boolean input = false;
		boolean forlp = false;
		boolean display = false;
		while(!queue.peep().equals("exit"))
		{
			if(queue.peep().equals("set"))
			{
				set = isLegalSet();
			}
			else if(queue.peep().equals("input"))
			{
				input = isLegalInput();
			}
			else if(queue.peep().equals("for"))
			{
				forlp = isLegalForLoop();
			}
			else if(queue.peep().equals("display"))
			{
				display = isLegalDisplay();
			}
		}
		return false;
	}
	
	public boolean isLegalDisplay()
	{
		if(queue.groupPeep(3).equals("display string literal , identifier"))
		{
			queue.groupPop(4);
		}
		return false;
	}
	public boolean isLegalForLoop()
	{
		boolean declaration = false;
		boolean input = false;
		boolean end = false;
		if(queue.groupPeep(7).equals("for identifier = number to identifier number do"))
		{
			declaration = true;
			queue.groupPop(8);	
		}
		input = isLegalInput();
		if(queue.peep().equals("endfor"))
		{
			queue.pop();
			end = true;
		}
		if(declaration && input && end)
		{
			return true;
		}
		return false;
	}
	
	public boolean isLegalInput()
	{
		int num = queue.head.lineNum;
		if(queue.groupPeep(3).equals("input string literal , identifier"))
		{
			queue.groupPop(4);
			if(queue.groupPeep(2).equals("[ identifier ]"))
			{
				queue.groupPop(3);
				return true;
			}
			else if(queue.head.lineNum > num)
			{
				return true;
			}
		}
		return false;
	}
	public boolean isLegalSet()
	{
		boolean flag = true;
		while(flag)
		{
			if(queue.groupPeep(2).equals("set identifier ="))
			{
				queue.groupPop(3);
				if(queue.groupPeep(5).equals("identifier ( identifier , identifier )"))
				{
					queue.groupPop(6);
				}
				else if(queue.groupPeep(2).equals("identifier . identifier"))
				{
					queue.groupPop(3);
				}
				else if(queue.peep().equals("identifier")||queue.peep().equals("number"))
				{
					queue.pop();
				}
				else if(queue.groupPeep(4).equals("( identifier lshift number )"))
				{
					queue.groupPop(5);				
				}

				if(!queue.peep().equals("set"))
				{
					flag = false;
					return true;
				}
				
			}
		}
		return false;
	}
	
	public boolean isLegalGlobal(String token)
	{
		boolean constants = false;
		boolean variables = false;
		boolean structures = false;
		if(queue.peep().equals("constants"))
		{
			queue.pop();
			constants = isLegalConstants();
		}
		if(queue.peep().equals("variables"))
		{
			queue.pop();
			variables = isLegalGlobalVariables();
		}
		if(queue.peep().equals("structures"))
		{
			queue.pop();
			structures = isLegalStructures();
		}
		if(constants && variables && structures)
		{
			return true;
		}
		return false;
	}
	
	public boolean isLegalStructures()
	{
		boolean flag = true;
		while(flag)
		{
			if(queue.groupPeep(4).equals("define identifier of struct identifier"))
			{
				queue.groupPop(5);
				if(!queue.peep().equals("define"))
				{
					flag = false;
					return true;
				}
			}
			else if(queue.groupPeep(4).equals("define identifier of enum identifier"))
			{
				queue.groupPop(5);
				if(!queue.peep().equals("define"))
				{
					flag = false;
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isLegalGlobalVariables()
	{
		boolean flag = true;
		while(flag)
		{
			if(queue.groupPeep(7).equals("define identifier array [ identifier ] of type"))
			{
				queue.groupPop(8);
				if(isType(queue.peep()))
				{
					queue.pop();
				}
				if(!queue.peep().equals("define"))
				{
					flag = false;
					return true;
				}
			}
			else if(queue.groupPeep(5).equals("define identifier = number of type"))
			{
				queue.groupPop(6);
				if(isType(queue.peep()))
				{
					queue.pop();
				}
				if(!queue.peep().equals("define"))
				{
					flag = false;
					return true;
				}
			}
		}

		return false;
	}
	
	public boolean isLegalConstants()
	{
		boolean flag = true;
		while(flag)
		{
			if(queue.groupPeep(5).equals("define identifier = number of type"))
			{
				queue.groupPop(6);
				if(isType(queue.peep()))
				{
					queue.pop();
				}
				if(!queue.peep().equals("define"))
				{
					flag = false;
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isLegalImport(String token)
	{
		if(token.equals("string literal"))
		{
//			System.out.println("import followed by "+token+" is good");
			imports = false;
			return true;
		}
		return false;
	}
	
	public boolean isLegalSymbol(String token)
	{
		String lhs = token + " " + queue.pop();
//		System.out.println("lhs is "+lhs);
		if(lhs.equals("identifier number"))
		{
//			System.out.println("symbol followed by "+lhs+" is good");
			symbol = false;
			return true;
		}
		return false;
	}
	
	public boolean isLegalForward(String token)
	{
		if(token.equals("function"))
		{
//			System.out.println("function found after forward is good");
			function = true;
			return true;
		}
		return false;
	}
	
	
	public boolean isLegalFunctionDef(String token)
	{
		//check if the next three tokens are identifier return type, followed by parameters	
		token += " "+queue.groupPeep(1);
//		System.out.println("TOKEN IS "+token);
		if(token.equals("identifier return type"))
		{
			queue.groupPop(2);
			if(isType(queue.peep()))
			{
				//System.out.println("function definition with "+token+" "+queue.pop()+" is good");
				queue.pop();
				if(queue.peep().equals("parameters"))
				{					
					queue.pop();
					parameters = true;
//					System.out.println("parameters found");
					return true;
				}
				
			}
		}
		return false;
	}
	
	public boolean isLegalParameters(String token)
	{
		//when we enter here after the word parameters, the next line should read IDENTIFIER OF TYPE <type>, or IDENTIFIER array [] of type <type>
		System.out.println("entering legal parameters");
		if(token.equals("identifier"))
		{
			//System.out.println("parameter identifier found");
//			System.out.println(queue.peep());
			if(queue.peep().equals("array"))
			{
				if(queue.groupPeep(4).equals("array [ ] of type"))
				{
//					System.out.println(queue.groupPeep(4));
					queue.groupPop(5);
					if(isType(queue.peep()))
					{
					    queue.pop();
						if(queue.peep().equals(","))
						{
							queue.pop();
						}
						else
						{
							parameters = false;
						}
						return true;
					}
				}
			}
			//left off here - do non array variables for the parameter section next 
			else if(queue.groupPeep(1).equals("of type"))
			{
				queue.groupPop(2);
				if(isType(queue.peep()))
				{
					queue.pop();
					if(queue.peep().equals(","))
					{
						queue.pop();
					}
					else
					{
						parameters = false;
					}
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isLegalSpecs(String token)
	{
		if(queue.peep().equals("enumerate"))
		{
			queue.pop();
			return isLegalEnum();
		}
		if(token.equals("struct"))
		{
			return isLegalStruct();
		}
		return false;
	}
	
	public boolean isLegalStruct()
	{
		if(queue.groupPeep(2).equals("identifier is variables"))
		{
			queue.groupPop(3);
			if(isLegalVariables() && queue.groupPeep(1).equals("endstruct identifier"));
			{
				queue.groupPop(2);
				return true;
			}
		}
		return false;
	}
	
	public boolean isLegalVariables()
	{
		boolean flag = true;
		while(flag)
		{
			if(queue.groupPeep(3).equals("define identifier of type"))
			{
				queue.groupPop(4);
				if(isType(queue.peep()))
				{
					queue.pop();
				}
				if(!queue.peep().equals("define"))
				{
					flag = false;
					return true;
				}
			}
			else if(queue.groupPeep(6).equals("define identifier array [ ] of type"))
			{
				queue.groupPop(7);
				if(isType(queue.peep()))
				{
					queue.pop();
				}
				if(!queue.peep().equals("define"))
				{
					flag = false;
					return true;
				}
			}
			else if(queue.groupPeep(7).equals("define identifier array [ identifier ] of type"))
			{
				queue.groupPop(8);
				if(isType(queue.peep()))
				{
					queue.pop();
				}
				if(!queue.peep().equals("define"))
				{
					flag = false;
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean isLegalEnum()
	{
		if(queue.groupPeep(3).equals("identifier is endenum identifier"))
		{
			queue.groupPop(4);
			return true;
		}
		return false;
	}
	
	public void showStates()
	{
		System.out.println("import: "+imports);
		System.out.println("symbol: "+symbol);
		System.out.println("forward: "+forward);
		System.out.println("specifications: "+specifications);
		System.out.println("global: "+global);
		System.out.println("implementations: "+implementations);
	}
	
	public boolean isType(String token)
	{
		if(main.identifierTable.isType(token))
		{
			return true;
		}
		return false;
	}
}
