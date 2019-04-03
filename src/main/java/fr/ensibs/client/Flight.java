package fr.ensibs.client;

import net.jini.core.entry.Entry;

public class Flight implements Entry
{
	public String flight_code ;
	public String departure_date ;
	public String departure_airport_code ;
	public String arrival_airport_code ;
	
	public Flight()
	{
		this.flight_code = null ;
		this.departure_date = null ;
		this.departure_airport_code = null ;
		this.arrival_airport_code = null ;
	}
	
	public Flight( String code , String date , String departure_code , String arrival_code )
	{
		this.flight_code = code ;
		this.departure_date = date ;
		this.departure_airport_code = departure_code ;
		this.arrival_airport_code = arrival_code ;
	}

	public String toString()
	{
		return "The flight " + this.flight_code + " from " + this.departure_airport_code + " to " + this.arrival_airport_code + " will take off " + this.departure_date ;
	}

	public String getFlight_code()
	{
		return flight_code ;
	}

	public void setFlight_code( String flight_code )
	{
		this.flight_code = flight_code ;
	}

	public String getDeparture_date()
	{
		return departure_date ;
	}

	public void setDeparture_date( String departure_date )
	{
		this.departure_date = departure_date ;
	}

	public String getDeparture_airport_code()
	{
		return departure_airport_code ;
	}

	public void setDeparture_airport_code( String departure_airport_code )
	{
		this.departure_airport_code = departure_airport_code ;
	}

	public String getArrival_airport_code()
	{
		return arrival_airport_code ;
	}

	public void setArrival_airport_code( String arrival_airport_code )
	{
		this.arrival_airport_code = arrival_airport_code ;
	}
}
