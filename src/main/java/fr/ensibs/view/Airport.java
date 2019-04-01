package fr.ensibs.view;

import net.jini.core.entry.Entry;

public class Airport implements Entry
{
	public String airport_code ;
	public String city_name ;
	
	public Airport()
	{
		this.airport_code = null ;
		this.city_name = null ;
	}
	
	public Airport( String code , String city )
	{
		this.airport_code = code ;
		this.city_name = city ;
	}

	public String toString()
	{
		return "Airport " + this.airport_code + " from " + this.city_name ;
	}

	public String getAirport_code()
	{
		return airport_code ;
	}

	public void setAirport_code( String airport_code )
	{
		this.airport_code = airport_code ;
	}

	public String getCity_name()
	{
		return city_name ;
	}

	public void setCity_name( String city_name )
	{
		this.city_name = city_name ;
	}
}
