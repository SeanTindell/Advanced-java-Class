package edu.trident.tindellS.assignmet2;
/*
 * program designed to simulate a cab and return numerical data for a report
 * this extends the cab program 
 * 
 * @author Sean Tindell*/

public class ExtendedCab extends Cab
{
	private final double SERVICE_COST = 25;
	private double totalCosts;
	private int milesSinceService;
	
	public ExtendedCab(double gas)
	{
		addGas(gas);
	}
	//returns total costs
	public double getTotalCosts()
	{
		return totalCosts;
	}
	//returns miles since last service
	public double getMilesSinceService()
	{
		return milesSinceService;
	}
	//adds miles to miles since service
	public void addMilesSinceService(double miles)
	{
		milesSinceService+=miles;
	}
	
	//overloads the addGas in the cab class to add a cast
	public double addGas(double gallons, double cost)
	{
		
		double leftOver;
		if((gallons+gasAvailable) < TANK_SIZE)
		{
			gasAvailable += gallons;
			totalCosts += gallons*cost;
			return gasAvailable;
		}
		else
		{
			leftOver =  TANK_SIZE-(gallons+gasAvailable);
			gasAvailable = TANK_SIZE;
			totalCosts += ((gallons*cost)+(leftOver*cost));
			return leftOver;
		}
		
		
	}
	//method to show the cab has been serviced and adds 25 to cost
	public void serviceCab()
	{
		totalCosts +=SERVICE_COST;
		milesSinceService = 0;
		
	}
	
	//returns net earnings
	public double getNetEarnings()
	{
		double netEarnings = earnings -totalCosts;
		return netEarnings;
	}
	//overrides the reset option to reset earnings and total cost
	@Override
	public void reset()
	{
		earnings = 0;
		totalCosts = 0;
	}
}
