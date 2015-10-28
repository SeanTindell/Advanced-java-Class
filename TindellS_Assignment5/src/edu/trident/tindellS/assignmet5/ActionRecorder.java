package edu.trident.tindellS.assignmet5;

/*
 * program designed create to create records for the cab company
 * in order to keep track of expenses and money
 * 
 * @author Sean Tindell*/

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ActionRecorder 
{
	
	public void recordAction (String cabId, RecordType type, double value1, double value2)
	{
		Path filePath = Paths.get("C:\\Users\\Sean\\workspace\\TindellS_Assignment5\\cabRecord.txt");
		
		final String DATE_FORMAT = "YYYY/MM/dd";
		final String CAB_NAME_FORMAT = "       ";
		final String RECORD_TYPE = "     ";
		final String AMOUNT_ONE ="0.00";
		final String  AMOUNT_TWO = "0.00";
		final String DECIMAL_FORMAT = "0.00";
		String delimiter = ",";
		
		String s = DATE_FORMAT +delimiter +CAB_NAME_FORMAT+delimiter+RECORD_TYPE+delimiter+AMOUNT_ONE+delimiter+AMOUNT_TWO+System.getProperty("line.separator");
		
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();

		String dateholder = dateFormat.format(date);		
		FileChannel cabRecord = null;
		String name= "";
	
		double amount1=0.0;
		double amount2=0.0;
		

		try
		{
			//creates and opens the file for writing
			cabRecord = (FileChannel)Files.newByteChannel(filePath,CREATE, WRITE);
			
			name = cabId;
			amount1 = value1;
			amount2 = value2;
	
			DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT);
			
			s = dateholder + delimiter + name + delimiter + type + delimiter +df.format(amount1)+delimiter+df.format(amount2)+"\n";//System.getProperty("line.seperator")
			byte[] data = s.getBytes();
			ByteBuffer buffer = ByteBuffer.wrap(data);

			//offsets the added data in order to create multiple records
			cabRecord.write(buffer, cabRecord.size());

			cabRecord.close();
		}
		catch(Exception e)
		{
			System.out.println("Error message: " + e);
			
		}
	}
}
