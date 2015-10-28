package edu.trident.tindellS.assignmet5;

import java.util.ArrayList;

/*
 * program designed to simulate a cab and return numerical data for a report
 * this extends the cab program 
 * 
 * @author Sean Tindell*/

public class ExtendedCab extends Cab
{
	private double serviceCost;
	private double totalCosts;
	private int milesSinceService;
	private boolean needsService= false;
	private boolean isServiceOverriden = false;
	private boolean serviced = false;
	private String cabName;


	private ArrayList<MaintenanceListener> listener = new ArrayList<MaintenanceListener>() ;
	

	public double getServiceCost(){
		return serviceCost;
	}
	//REturns isServiceOverriden
	public boolean getIsServiceOverriden()
	{
		return isServiceOverriden;
	}
	//returns getNeedsService
	public boolean getNeedsService()
	{
		return needsService;
	}
	public ExtendedCab(String name, double milesPG, double serviceC,double tankSize)
	{
		cabName = name;
		MPG = milesPG;
		TANK_SIZE = tankSize;
		serviceCost = serviceC;

	}
	
	public String getCabName()
	{
		return cabName;
	}
	public double getMPG()
	{
		return MPG;
	}
	public double getTankSize()
	{
		return TANK_SIZE;
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
		totalCosts +=serviceCost;
		milesSinceService = 0;
		serviced = true;
		this.notifyListeners(serviced);
		
		
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
		super.reset();
		totalCosts = 0;
	}
	//creates a listener and adds to a list
	public void addMaintenanceListener(MaintenanceListener listen)
	{
		listener.add(listen);
	}
	//indicates wether or not a service is needed
	public void overrideServiceNeeded()
	{
		if(this.getMilesSinceService() >=499 ) 
		{
			needsService = true;
			this.notifyListeners(needsService);
		}
		else
		{
			needsService = false;
		}
		
	}
	//what is connceted to the listeners
	public void notifyListeners(boolean needed)
	{
		if(needed = true)
		{
			
				if(serviced == true)
				{
					for (MaintenanceListener ml : listener)
					{
						ml.maintenancePerformed();
						needsService=false;
						serviced = false;	
					}
				}
				if(needsService == true)
				{
					for (MaintenanceListener ml : listener)
					{
						ml.maintenanceNeeded();				
					}
		 	}
		}
	}
	
	//sets whether the overide has been pushed
	public void setOverride(boolean over)
	{
		isServiceOverriden = over;
		this.notifyListeners(isServiceOverriden);
	}

}


