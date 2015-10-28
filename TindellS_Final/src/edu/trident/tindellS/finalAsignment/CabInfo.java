package edu.trident.tindellS.finalAsignment;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
/*
 *loads the cab records into lists and 
 *s
 *
 * @author Sean Tindell*/


public class CabInfo 
{
	private Date endDate;
	private Date startDate;
	private String idCab;
	private double grossEarnings;
	private double totalGasCost;
	private double totalServiceCost;
	private double netEarnings;
	private double totalMiles;
	private int serviceCount;
	private double averageServiceDays;
	private int maxServiceDays;
	
	
	private ArrayList<FareRecord> fareR = new ArrayList<FareRecord>();
	private ArrayList<GasRecord> gasR = new ArrayList<GasRecord>();
	private ArrayList<ServiceRecord> serviceR = new ArrayList<ServiceRecord>();


	public CabInfo(String CabID) throws ParseException
	{		
		this.idCab = CabID;
	}	
	public void addFare(FareRecord newFare)
	{
		fareR.add(newFare);
	}
	public void addGas(GasRecord newGas)
	{
		gasR.add(newGas);
	}
	public void addService(ServiceRecord newService)
	{
		serviceR.add(newService);
	}	
	//sets many of the cab items to expedite the program process
	public void setCabItems()
	{
		Date date1 = fareR.get(0).getDate();
		Date date2;
		Date earliestDate = date1;
		Date lastDate = date1;
		Date tempDateE= date1;
		Date tempDateL= date1;
		double earnings = 0;
		double gCost=0;
		double service = 0;
		double miles = 0;
		
		for (ServiceRecord sr : serviceR)
		{
			
			date2 = sr.getDate();
			if ((date1.getTime()-date2.getTime())> 0)
			{
				tempDateE = date2;
			}
			if ((date1.getTime()-date2.getTime()) < 0)
			{
				tempDateL = date2;
			}
			service += sr.getPrice();
			date1 = date2;
		}
		
		if ((earliestDate.getTime()>tempDateE.getTime()))
			earliestDate = tempDateE;
		if ((lastDate.getTime()<tempDateL.getTime()))
			lastDate = tempDateL;
		
		for (FareRecord fr : fareR)
		{
			
			date2 = fr.getDate();
			if ((date1.getTime()-date2.getTime())> 0)
			{
				tempDateE = date2;
			}
			if ((date1.getTime()-date2.getTime()) < 0)
			{
				tempDateL = date2;
			}
			miles += fr.getMiles();
			earnings += fr.getFareCost();
			date1 = date2;
		}
		if ((earliestDate.getTime()>tempDateE.getTime()))
			earliestDate = tempDateE;
		if ((lastDate.getTime()<tempDateL.getTime()))
			lastDate = tempDateL;
		
		for (GasRecord gr : gasR)
		{
			
			date2 = gr.getDate();
			if ((date1.getTime()-date2.getTime())> 0)
			{
				tempDateE = date2;
			}
			if ((date1.getTime()-date2.getTime()) < 0)
			{
				tempDateL = date2;
			}
			gCost += gr.getGasPrice();
			date1 = date2;
		}
		if ((earliestDate.getTime()>tempDateE.getTime()))
			earliestDate = tempDateE;
		if ((lastDate.getTime()<tempDateL.getTime()))
			lastDate = tempDateL;
		
		totalMiles =miles;			
		totalServiceCost = service;		
		totalGasCost = gCost;
		grossEarnings = earnings;
		startDate = earliestDate;
		endDate = lastDate;	
	}
	public Date getEndDate(){return endDate;}
	public Date getStartDate(){	return startDate;}
	public String getIDCab(){return idCab;}
	
	public double getGrossEarnings()
	{		
		return grossEarnings;
	}
	public double getTotalGasCost()
	{
		return totalGasCost;
	}
	public double getTotalServiceCost()
	{
		return totalServiceCost;
	}
	//calculates the total net earnings
	public double getNetEarnings()
	{
		double costs = 0;
		
		costs = getTotalServiceCost() + getTotalGasCost();
		netEarnings =getGrossEarnings()- costs;
		
		return netEarnings;
	}
	public double getTotalMiles()
	{
		return totalMiles;
	}
	public int getServiceCount()
	{
		serviceCount = serviceR.size();
		return serviceCount;
	}
	
	//calculates the average days between services
	public double getAverageServiceDays()
	{
		
		Date date1 = fareR.get(0).getDate();
		Date date2;
		double days = 0;
		int diff = 0;
		
		for (ServiceRecord sr : serviceR)
		{
			
			date2 = sr.getDate();
			
			diff = (int)( (date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24)); 
			if(diff<0)
			{
				days += diff * -1;
			}
			else
			{
				days+=diff;
			}
			date1 = date2;
		}
		
		if(serviceR.size()!= 0)
			averageServiceDays = days/(serviceR.size()-1);
		else
			averageServiceDays =0;
		
		return averageServiceDays;
	}
	//finds the max amount of days between service
	public int getMaxServiceDays() throws ParseException
	{
		Date date1 = fareR.get(0).getDate();
		Date date2;

		int diff = 0;
		
		for (ServiceRecord sr : serviceR)
		{
			
			date2 = sr.getDate();
						
			diff = (int)( (date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24)); 
			
			if(diff < 0)
			{
				diff = diff * -1;
				if (diff > maxServiceDays)
					maxServiceDays = diff;
			}
			else
			{
				if (diff > maxServiceDays)
					maxServiceDays = diff;
			}
			date1 = date2;
		}
		
		return maxServiceDays;
	}

	
	

}
