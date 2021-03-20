package main;

public class operatorTable 
{
	operator[] opTable = new operator[22];
	
	public operatorTable()
	{
		opTable[0] = new operator("=","assignment",1);
		opTable[1] = new operator("+","addition",2);
		opTable[2] = new operator("-","subtraction",3);
		opTable[3] = new operator("*","multiplication",4);
		opTable[4] = new operator("/","division",5);
		opTable[5] = new operator("==","equality",6);
		opTable[6] = new operator("!","relational not",7);
		opTable[7] = new operator("<","relational less than",8);
		opTable[8] = new operator(">","relational greater than",9);
		opTable[9] = new operator("<=","relational less than or equal to",10);
		opTable[10] = new operator(">=","relational greater than or equal to",11);
		opTable[11] = new operator("[","left bracket",12);
		opTable[12] = new operator("]","right bracket",13);
		opTable[13] = new operator("(","left paranthesis",14);
		opTable[14] = new operator(")","right paranthesis",15);
		opTable[15] = new operator("<","left angular",16);
		opTable[16] = new operator(">","right angular",17);
		opTable[17] = new operator(",","comma",18);
		opTable[18] = new operator("^","exponent",19);
        opTable[19] = new operator("lshift","left shift",20);
        opTable[20] = new operator("rshift","right shift",21);
        opTable[21] = new operator(".","dot",22);
	}
	
	public int isOperator(String str)
	{
		for(int i = 0; i < opTable.length;i++)
		{
			if(opTable[i].symbol.equals(str))
			{
				return i;
			}
		}
		return -1;
	}
	
	public String getType(String str)
	{
		for(int i = 0; i < opTable.length;i++)
		{
			if(opTable[i].symbol.equals(str))
			{
				return opTable[i].description;
			}
		}
		return "";
	}
	
	public Boolean containsOperator(String str)
	{
		for(int i = 0; i < str.length(); i++)
		{
			if(isOperator(String.valueOf(str.charAt(i)))!= -1)
			{
				return true;
			}
		}
		return false;
	}
}
