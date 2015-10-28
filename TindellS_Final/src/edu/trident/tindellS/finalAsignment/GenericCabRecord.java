package edu.trident.tindellS.finalAsignment;
/*
 *Generic cab record for use with other types of record the cab uses
 * @author Sean Tindell*/
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GenericCabRecord
{
	private static SimpleDateFormat formatted = new SimpleDateFormat("M/d/y");

	final private Date date;
	final private double price;
	final private double miles;
    
	public GenericCabRecord(String dateStr, String priceCost, String milesGiven) throws ParseException, NumberFormatException
	{
		this.date = formatted.parse(dateStr);
		this.price= Double.parseDouble(priceCost);
		this.miles = Double.parseDouble(milesGiven);
	}
	
	public Date getDate() 
	{
		return date;
	}
	public double getPrice ()
	{
		return price;
	}
	public double getMiles() 
	{
		return miles;
	}

}
