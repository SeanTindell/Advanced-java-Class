package edu.trident.tindellS.finalAsignment;
/*
 *Controls the intitialization of the program
 *intializes cab info and writes to a file the final report as well as the totals of the report
 *
 * @author Sean Tindell*/

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;



public class CabRecordProcessor 
{
	SimpleDateFormat formatted = new SimpleDateFormat("M/d/y");
	static ArrayList<CabInfo> cabInfos = new ArrayList<CabInfo>();
	String inputFile;
	

	DecimalFormat df = new DecimalFormat("0.00");
	
	public CabRecordProcessor(String filename)
	{
		inputFile = filename;
	}
	public void sortCab()
	{
		
	}
	
	//searches for existing cab ID
	private CabInfo cabById(String id)
	{
		CabInfo info = null;
		for (CabInfo s : cabInfos)
		{
			if (id.equals(s.getIDCab()))
				info = s;
		}
		return info;
	}
	public void loadRecords() throws FileNotFoundException
	{
		int lineNumber = 0;
		// open file
		Scanner recordFile = new Scanner(new File(inputFile));
		// while loop that reads input file
		while (recordFile.hasNextLine())
		{
			String line;
			String[] fields;
			lineNumber++;
			CabInfo cabs;
			String date;
			String cabID;
			String record;
			String miles;
			String cost;
			
			
			try {
		    // read line
			line = recordFile.nextLine();
			fields = line.split(",");
			date = fields[0];
			cabID = fields[1];
			record = fields[2];
			miles = fields[4];
			cost = fields[3];

			cabs = cabById(cabID);
		    // if doesn't exist create
			if (cabs == null)
			{
				cabs = new CabInfo(cabID);
				cabInfos.add(cabs);
			}

		    // creates cab record based on type
		    
			if ("SERVICE".equalsIgnoreCase(record))
			{
				ServiceRecord ServRecord = new ServiceRecord(date, miles, cost);
				cabs.addService(ServRecord);
				
			}
			else if ("FARE".equalsIgnoreCase(record))
			{
				FareRecord fRecord = new FareRecord(date, miles, cost);
				cabs.addFare(fRecord);
				
			}
			else if ("GAS".equalsIgnoreCase(record))
			{
				//
				GasRecord gRecord = new GasRecord(date, miles, cost);
				cabs.addGas(gRecord);
				
			}
			else
			{
				throw new Exception("Unsupported record type: " + record);
			}
			}
			catch (Exception ex)
			{

				System.err.println(ex.getClass() + ": " + ex.getMessage() + " -- line: " + lineNumber);
			}
		}
		
	}
	public void produceReport () throws ParseException, IOException
	{
		Path filePath = Paths.get("C:\\Users\\Sean\\workspace\\TindellS_Final\\cabReport.csv");
		String delimiter = ",";
		FileChannel cabRecord = null;
		//variables for total services
		double grssEarnings= 0;
		double totalGasCst= 0;
		double totalServCst= 0;
		double totalNetEarn= 0;
		double totalMilesD= 0;
		double avgBwServices= 0;
		int count = 0;
		
		//sorts the array list by cabID
		Collections.sort(cabInfos, Sorter.CabIdSort);
		
		
		//sets the headers of the report
		String s = "Cab ID" + delimiter + "Start Date"+ delimiter + "End Date" + delimiter +"Gross Earnings"+
		delimiter+"Total Gas Cost"+delimiter+"Total Sevice Cost"+delimiter+	"Net Earnings"+delimiter+"Total Miles"+delimiter+
		"Service Count"+delimiter+"Average Service Days"+delimiter+"Max Service Days"+"\n";
		
		byte[] data = s.getBytes();
		ByteBuffer buffer = ByteBuffer.wrap(data);
		
		
		try
		{
			//creates and opens the file for writing
			cabRecord = (FileChannel)Files.newByteChannel(filePath,CREATE, WRITE);
			cabRecord.write(buffer);
			for (CabInfo ci : cabInfos)
			{	
				ci.setCabItems();
				s = ci.getIDCab() + delimiter + formatted.format(ci.getStartDate())
						+ delimiter + formatted.format(ci.getEndDate()) + delimiter +df.format(ci.getGrossEarnings())+
						delimiter+df.format(ci.getTotalGasCost())+delimiter+df.format(ci.getTotalServiceCost())+delimiter+
						df.format(ci.getNetEarnings())+delimiter+df.format(ci.getTotalMiles())+delimiter+
						ci.getServiceCount()+delimiter+df.format(ci.getAverageServiceDays())+
						delimiter+ci.getMaxServiceDays()+"\n";
				data = s.getBytes();
				buffer = ByteBuffer.wrap(data);
				cabRecord.write(buffer);
				
				//Calculates totals for immediate feedback
				grssEarnings +=ci.getGrossEarnings();
				totalGasCst+=ci.getTotalGasCost();
				totalServCst+=ci.getTotalServiceCost();
				totalNetEarn+=ci.getNetEarnings();
				totalMilesD+= ci.getTotalMiles();
				avgBwServices+=ci.getAverageServiceDays();
				count++;
			}
			avgBwServices = avgBwServices/count;
			
			System.out.printf("Gross Earnings: "+df.format(grssEarnings)+"%n");
			System.out.printf("Total Gas Cost: "+df.format(totalGasCst)+"%n");
			System.out.printf("Total Service Cost: "+df.format(totalServCst)+"%n");
			System.out.printf("Net Earnings: "+df.format(totalNetEarn)+"%n");
			System.out.printf("Total Miles Driven: "+df.format(totalMilesD)+"%n");
			System.out.printf("Average miles between services: "+df.format(avgBwServices)+"%n");
			
			
			
			
			
		}
		catch(Exception e)
		{
			System.out.println("Error message: " + e);
		}
		cabRecord.close();
		
	}

	public static void main(String[] args) throws ParseException, IOException
	{
		CabRecordProcessor newRecords = new CabRecordProcessor("C:\\Users\\Sean\\workspace\\TindellS_Final\\cabRecord.csv");
		newRecords.loadRecords();
		newRecords.produceReport();
	}
}
