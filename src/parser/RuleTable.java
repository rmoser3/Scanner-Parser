package parser;

public class RuleTable 
{
	rules[] ruleTable = new rules[50];
	int lastIndex;
	
	public RuleTable()
	{
		ruleTable[0] = new rules("import");
		ruleTable[1] = new rules("symbol");
	}
	

}
