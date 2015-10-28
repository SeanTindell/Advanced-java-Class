package edu.trident.tindellS.assignmet1;
/*
 * program designed to simulate a cab and return numerical data for a report
 * 
 * 
 * @author Sean Tindell*/
public class Cab {
	
	private final double MPG = 17.8;
	private final double TANK_SIZE = 22.9;
	private final double BASE_FARE = 2.00;
	private final double MILE_FARE = .585;
	private double gasAvailable;
	private int milesSinceReset;
	private double earnings;
	
	public double getGas()
	{
		return gasAvailable;
	}
	
	public int getMilesSinceReset()
	{
		return milesSinceReset;
	}
	
	//records and returns the fare for a trip
	public double recordTrip(int miles)
	{
		double trip = miles/MPG;
		
		if (gasAvailable-trip >= 0)
		{
			double fare = getFare(miles);
			gasAvailable -= (miles/MPG);
			milesSinceReset += miles;
			earnings += fare;
			return fare;
		}
		else
		{
			return 0;
		}
		
		
	}
	//adds gas to the gas tank till full
	public double addGas(double gallons)
	{
		double leftOver;
		if((gallons+gasAvailable) < TANK_SIZE)
		{
			gasAvailable += gallons;
			return gasAvailable;
		}
		else
		{
			leftOver =  TANK_SIZE-(gallons+gasAvailable);
			gasAvailable = TANK_SIZE;
			return leftOver;
		}
	}
	//returns the curent amount of earings
	public double getGrossEarnings()
	{		
		return earnings;
	}
	//returns miles available for the current tank of gas
	public double getMilesAvailable()
	{
		double milesAvailable;
		milesAvailable = MPG * gasAvailable;
		return milesAvailable;
	}
	//resets miles and earnings
	public void reset()
	{
		milesSinceReset = 0;
		earnings = 0;
	}
	//gets the current fair for certain amount of time
	public double getFare(int miles)
	{
		double fare;
		fare=(miles*MILE_FARE)+BASE_FARE;
		return fare;
	}
}
