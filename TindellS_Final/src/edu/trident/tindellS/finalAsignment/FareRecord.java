package edu.trident.tindellS.finalAsignment;
/*
 *handles the fare record of the program
 * @author Sean Tindell*/
import java.text.ParseException;

public class FareRecord extends GenericCabRecord
{
	private double fareCost;
	private double tripLength;
	
	public FareRecord(String dateStr, String priceCost, String milesGiven)
			throws ParseException, NumberFormatException 
	{
		super(dateStr, priceCost, milesGiven);
		fareCost = Double.parseDouble(priceCost);
		tripLength = Double.parseDouble(milesGiven);
	}

	public double getFareCost()
	{
		return fareCost;
	}
	public double getTripLength()
	{
		return tripLength;
	}

}
