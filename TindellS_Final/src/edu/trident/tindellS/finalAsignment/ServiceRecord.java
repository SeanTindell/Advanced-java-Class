package edu.trident.tindellS.finalAsignment;
/*
 *handles the service record of the program
 * @author Sean Tindell*/
import java.text.ParseException;

public class ServiceRecord extends GenericCabRecord
{
	private double milesSinceService;
	private double serviceCost;

	public ServiceRecord(String dateStr, String priceCost, String miles) throws ParseException, NumberFormatException 
	{
		super(dateStr, priceCost, miles);
		serviceCost = Double.parseDouble(priceCost);
		milesSinceService = Double.parseDouble(miles);
	}
	
	public double getServiceCost()
	{
		return serviceCost;
	}
	
	public double getMilesSinceService()
	{
		return milesSinceService;
	}

	

}
