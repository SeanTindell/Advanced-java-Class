package edu.trident.tindellS.assignmet5;

import static java.nio.file.StandardOpenOption.CREATE;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
/*
 *Controls the intitialization of the program
 *intializes the Extended Cabs into an array 
 * as well as reads information from a file for the list of cabs
 * @author Sean Tindell*/



public class Controller implements CabLoader
{
	
	
	
	//Assignment 4 stuff
	private ArrayList<ExtendedCab> cl = new ArrayList<ExtendedCab>();
	
	ExtendedCab c;
	MultiCabUI userInterface;

	public Controller (String fileName)
	{
		
		fileName = "C:\\Users\\Sean\\workspace\\TindellS_Assignment5\\"+fileName;
		Path file = Paths.get(fileName);
		//Cab Format
		final String CAB_ID_FORMAT = "  ";
		final String MPG_FORMAT = "0.0";
		final String SERVICE_FORMAT = "0.0";
		final String TANK_SIZE_FORMAT = "0.0";
		String delimiter =",";
		//default cab values
		final double MPG_DEFAULT = 19;
		final double DEFAULT_SERVICE_PRICE= 25;
		final double TANK_SIZE_DEFAULT =19;
		String s = CAB_ID_FORMAT + delimiter + MPG_FORMAT + delimiter + SERVICE_FORMAT + delimiter +TANK_SIZE_FORMAT + System.getProperty("line.seperator");
		
		String[] array = new String[4];
		double MPG=0.0;
		double service=0.0;
		double tankSize=0.0;
		String cabId="";
		ArrayList<String> cabNames =new ArrayList<String>();

		//opens and reads the file
		try
		{
			InputStream iStream = new BufferedInputStream(Files.newInputStream(file));
			BufferedReader reader = new BufferedReader(new InputStreamReader(iStream));
			
			s = reader.readLine();
			while (s != null)
			{
				
				array = s.split(delimiter);
				cabId = array[0];
				cabNames.add(cabId);
				try{ 	MPG = Double.parseDouble(array[1]);	}	
					catch(Exception e)
						{	MPG = MPG_DEFAULT;	}
				try{ 	service  = Double.parseDouble(array[2]);}	
					catch(Exception e)
						{	service = DEFAULT_SERVICE_PRICE;	}
				try{ 	tankSize = Double.parseDouble(array[3]);}	
					catch(Exception e)
					{	tankSize = TANK_SIZE_DEFAULT;	}
				
				//creates an extended cab object and places into an array
				ExtendedCab c = new ExtendedCab(cabId, MPG, service,tankSize);
				cl.add(c);	
				
				s =reader.readLine();
				
			}			
			reader.close();
		}
		catch(Exception e)
		{
			System.out.println("Message: " +e);
		}
		//converts cabnames from an array list of strings to a string
		String[] cabs = cabNames.toArray(new String[cabNames.size()]);
		userInterface = new MultiCabUI(cabs, cl);
		
		loadCab(cl.get(0).getCabName());

	}
	
	@Override
	public void loadCab(String cabName) 
	{
		int i = 0;
		for(ExtendedCab o : cl )
		{			
			String nameOfCab = o.getCabName();
			if(cabName == nameOfCab)
			{				
				userInterface.setCab(cl.get(i));
			}			
			i++;
		}		
	}


}
