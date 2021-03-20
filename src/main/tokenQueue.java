package main;

public class tokenQueue 
{
	public class node
	{
		String token;
		node next;
		public int lineNum;
		public int columnNum;
		
		node(String token, int lineNum, int columnNum)
		{
			this.token = token;
			this.lineNum = lineNum;
			this.columnNum = columnNum;
		}
	}
	
	public node head;
	node tail;
	Boolean empty;
	
	tokenQueue()
	{
		head = null;
		tail = null;
		empty = true;
	}
	
	public void push(String token, int lineNum, int columnNum)
	{
		node temp = new node(token, lineNum, columnNum);
		if(empty)
		{
			head = temp;
			tail = temp;
			empty = false;
		}
		else
		{
			tail.next = temp;
			tail = temp;
		}
	}
	
	public String pop()
	{
		String result = head.token;
		if(head.next != null)
		{
			head = head.next;
		}
		else
		{
			head = null;
			tail = null;
			empty = true; 
		}		
		return result;
	}
	
	public void groupPop(int count)
	{
		while(count > 0)
		{
			pop();
			count--;
		}
	}
	
	public String peep()
	{
		return head.token;
	}
	
	public String groupPeep(int count)
	{
		String result = head.token;
		node temp = head;
		while(count > 0)
		{
			temp = temp.next;
			result += " "+temp.token;
			count--;
		}
		return result;
	}
	
	public void returnn(String token, int lineNum, int columnNum)
	{
		node temp = new node(token, lineNum, columnNum);
		if(head != null)
		{
			temp.next = head;
		}
		head = temp;
	}

}
