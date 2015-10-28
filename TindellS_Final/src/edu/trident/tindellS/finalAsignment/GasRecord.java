package edu.trident.tindellS.finalAsignment;
/*
 *handles the gas record of the program
 * @author Sean Tindell*/
import java.text.ParseException;

public class GasRecord extends GenericCabRecord
{
	private double gasPrice;
	private double gallonsAdded;
	
	public GasRecord(String dateStr, String priceCost, String miles)throws ParseException, NumberFormatException 
	{
		super(dateStr, priceCost, miles);
		gasPrice = Double.parseDouble(priceCost);
		gallonsAdded = Double.parseDouble(miles);
	}
	
	public double getGasPrice()
	{
		return gasPrice;
	}
	public double getGallonsAdded()
	{
		return gallonsAdded;
	}

}
