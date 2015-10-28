package edu.trident.tindellS.assignmet4;

import java.util.ArrayList;
import java.util.Arrays;
/*
 *Controls the intitialization of the program
 *intializes the Extended Cabs into an array 
 * 
 * @author Sean Tindell*/


public class Controller implements CabLoader
{
	private ArrayList<ExtendedCab> cl = new ArrayList<ExtendedCab>(5);
	ExtendedCab c;
	MultiCabUI userInterface;
	
	public Controller (String[] cabs)
	{
		Arrays.sort(cabs);
		for (String taxi : cabs)
		{
			ExtendedCab c = new ExtendedCab(taxi,(Math.floor(Math.random()*5)+18),(Math.floor(Math.random()*5)+18));
			cl.add(c);

			
		}
		userInterface = new MultiCabUI(cabs);
		loadCab(cabs[0]);
		//loads the extended list in the extended cab for the cab
		int i=0;
		for(ExtendedCab o : cl )
		{
			c = cl.get(i);
			c.setCl(cl);
			i++;
		}
	}
	
	@Override
	public void loadCab(String cabName) 
	{
		int i = 0;
		for(ExtendedCab o : cl )
		{
			
			String nameOfCab = cl.get(i).getCabName();
			if(cabName == nameOfCab)
			{
				
				userInterface.setCab(cl.get(i));
			}
			
			i++;
		}
		
	}

}
