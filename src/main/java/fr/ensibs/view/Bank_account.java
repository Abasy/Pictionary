package fr.ensibs.view;

import net.jini.core.entry.Entry;

public class Bank_account implements Entry
{
	public String account_code ;
	public Integer balance ;
	
	public Bank_account()
	{
		this.account_code = null ;
		this.balance = null ;
	}
	
	public Bank_account( String code )
	{
		this.account_code = code ;
		this.balance = 0 ;
	}

	public String getAccount_code()
	{
		return account_code ;
	}

	public void setAccount_code( String account_code )
	{
		this.account_code = account_code ;
	}

	public Integer getBalance()
	{
		return balance ;
	}

	public void setBalance( Integer balance )
	{
		this.balance = balance ;
	}
}
