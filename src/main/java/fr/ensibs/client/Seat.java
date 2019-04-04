package fr.ensibs.client;

import net.jini.core.entry.Entry;

public class Seat implements Entry
{
	public Integer seat_number ;
	public String flight_code ;
	public Integer price ;
	public Boolean reserved ;
	
	public Seat()
	{
		this.seat_number = null ;
		this.flight_code = null ;
		this.price = null ;
		this.reserved = null ;
	}

	public Seat( Integer number , String code , Integer price )
	{
		this.seat_number = number ;
		this.flight_code = code ;
		this.price = price ;
		this.reserved = false ;
	}
	
	public String toString()
	{
		return "The seat number " + this.seat_number + " from te flight " + this.flight_code + " is currently " + (this.reserved?"":"not ") + "reserved" ;
	}

	public int getSeat_number()
	{
		return seat_number ;
	}

	public void setSeat_number( Integer seat_number )
	{
		this.seat_number = seat_number ;
	}

	public String getFlight_code()
	{
		return flight_code ;
	}

	public void setFlight_code( String flight_code )
	{
		this.flight_code = flight_code ;
	}

	public Integer getPrice()
	{
		return price ;
	}

	public void setPrice( Integer price )
	{
		this.price = price ;
	}

	public Boolean getReserved()
	{
		return reserved ;
	}

	public void setReserved( Boolean reserved )
	{
		this.reserved = reserved ;
	}
}