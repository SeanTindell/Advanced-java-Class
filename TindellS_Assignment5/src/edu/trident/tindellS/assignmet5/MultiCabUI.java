package edu.trident.tindellS.assignmet5;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.nio.file.*;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.ByteBuffer;
import static java.nio.file.StandardOpenOption.*;
import java.text.*;
import java.util.Scanner;
import javax.swing.*;

/*
 * program designed create a Mulitple cab ui frame using the extended cab object
 * to create multiple new exteneded cab object and track a cabs data
 * Also create and writes to a file for keeping records
 * 
 * @author Sean Tindell*/
public class MultiCabUI implements CabLoader, ActionListener, ItemListener
{
	private ExtendedCab taxi;
	private ArrayList<ExtendedCab> cl;
	
	private ActionRecorder recordCab = new ActionRecorder();
	private RecordType record;
	
	private JTextField gasAvailOut = new JTextField(12);
	private JTextField milesAvailOut = new JTextField(12);
	private JTextField milesSinceServiceOut = new JTextField(12);
	private JTextField milesSinceResetOut = new JTextField(12);
	private JTextField grossSinceResetOut = new JTextField(12);
	private JTextField netSinceResetOut = new JTextField(12);

	private JTextField amountInput = new JTextField(12);
	private JTextField gasPriceInput = new JTextField(12);
	private JTextField output = new JTextField(12);
	private JButton okBtn = new JButton("OK");
    private JButton refreshBtn = new JButton("Refresh");
    private JLabel errorIssue = new JLabel();
    
    private String[] cabNames;
    
    
    private JComboBox<String> cabCombo;
    private JComboBox<String> actionCombo;
    //cabCombo = new JComboBox<String>(cabNames);
	
	
	public MultiCabUI(String[] cabs,  ArrayList<ExtendedCab> cabList)
	{
		cl = cabList;
		cabNames = cabs;
		init();
		
	}

	private void init()
    {
        JLabel gasAvail = new JLabel("Gas Available");
        JLabel milesAvail = new JLabel("Miles Available");
        JLabel milesSinceService = new JLabel("Miles since service");
        JLabel milesSinceReset = new JLabel("Miles since reset");
        JLabel grossSinceReset = new JLabel("Gross earnings since reset");
        JLabel netSinceReset = new JLabel("Net earnings since reset");
        JFrame frame = new JFrame("Acme Cabs");
        JPanel pane1 = new JPanel();
        JPanel cabControlPanel = new JPanel();
        Container contentPane = frame.getContentPane();
        
        String[] actionNames = {"Record Trip", "Add Gas", "Service", "Reset"};

        cabCombo = new JComboBox<String>(cabNames);
        actionCombo = new JComboBox<String>(actionNames);

        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(BorderFactory.createTitledBorder("Cab Info"));
        statusPanel.setLayout(new GridLayout(0,2,5,5));
        statusPanel.add(cabCombo);
        statusPanel.add(refreshBtn);

        // disable all of the output fields
        // western panel
        gasAvailOut.setEnabled(false);
        milesAvailOut.setEnabled(false);
        milesSinceServiceOut.setEnabled(false);
        milesSinceResetOut.setEnabled(false);
        grossSinceResetOut.setEnabled(false);
        netSinceResetOut.setEnabled(false);

       // eastern panel
        gasPriceInput.setEditable(false);
        output.setEditable(false);
        
        // add the labels and output fields in pairs
        statusPanel.add(gasAvail);
        statusPanel.add(gasAvailOut);

        statusPanel.add(milesAvail);
        statusPanel.add(milesAvailOut);
        
        statusPanel.add(milesSinceService);
        statusPanel.add(milesSinceServiceOut);
        
        statusPanel.add(milesSinceReset);
        statusPanel.add(milesSinceResetOut);
        
        statusPanel.add(grossSinceReset);
        statusPanel.add(grossSinceResetOut);
        
        statusPanel.add(netSinceReset);
        statusPanel.add(netSinceResetOut);
        
        // construct the second panel, place it in a secondary panel to
        // allow better sized fields
        contentPane.add(statusPanel, BorderLayout.WEST);
        JPanel p1 = new JPanel(); // dummy panel
        contentPane.add(p1, BorderLayout.EAST);
        p1.setBorder(BorderFactory.createTitledBorder("Cab Control"));
        p1.add(cabControlPanel);

        cabControlPanel.setLayout(new GridLayout(0,1,5,5));
        cabControlPanel.add(actionCombo);
        cabControlPanel.add(amountInput);
        cabControlPanel.add(gasPriceInput);
        cabControlPanel.add(output);
        JSeparator js = new JSeparator();
        js.setVisible(false);
        cabControlPanel.add(js);
        cabControlPanel.add(okBtn);
        cabControlPanel.add(errorIssue);
        

        // contentPane.add(cabControlPanel, BorderLayout.EAST);
        
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //listeners
        okBtn.addActionListener(this);
        refreshBtn.addActionListener(this);
        actionCombo.addItemListener(this);
        cabCombo.addItemListener(this);
        
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) 
	{
		Object choiceCab = cabCombo.getSelectedItem();
		Object choiceAction =actionCombo.getSelectedItem();
		
		String loadedCab = choiceCab.toString();
		loadCab(loadedCab);
		

		if (arg0.getStateChange() == ItemEvent.SELECTED) 
	    {
			output.setText("");
			errorIssue.setText("");
			
	    	if(choiceAction == "Record Trip")
	    	{	
	    		amountInput.setEditable(true);
	    		gasPriceInput.setEditable(false);
	    		amountInput.setText("");
	    		gasPriceInput.setText("");   		

	    	}
	    	else if (choiceAction == "Add Gas")
	    	{
	    		amountInput.setEditable(true);
	    		gasPriceInput.setEditable(true);
	    		amountInput.setText("");
	    		gasPriceInput.setText("");
	    	}
	    	else if (choiceAction == "Service")
	    	{
	    		amountInput.setEditable(false);
	    		gasPriceInput.setEditable(false);
	    		amountInput.setText("");
	    		gasPriceInput.setText("");
	    	}
	    	else if (choiceAction == "Reset")
	    	{
	    		amountInput.setEditable(false);
	    		gasPriceInput.setEditable(false);
	    		amountInput.setText("");
	    		gasPriceInput.setText("");

	    	}
	    	
	    	if(arg0.getItemSelectable() == cabCombo)
	    	{
	    		gasAvailOut.setText(""+taxi.getGas());
	            milesAvailOut.setText(""+taxi.getMilesAvailable());
	            milesSinceServiceOut.setText(""+taxi.getMilesSinceService());
	            milesSinceResetOut.setText(""+taxi.getMilesSinceReset());
	            grossSinceResetOut.setText(""+taxi.getGrossEarnings());
	            netSinceResetOut.setText(""+ taxi.getNetEarnings());
	    	}
	   }
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		Object source = arg0.getSource();
		Object choice = actionCombo.getSelectedItem();
		double cab;
		double gas;
		String error;
		
		if(source == okBtn)
		{
			//button that records the trip and warns if unable
			if(choice == "Record Trip")
			{				
				String trip = amountInput.getText();
				
				
				if(trip.isEmpty() == false)
			{
					int miles = Integer.parseInt(trip);
					
					cab = taxi.recordTrip(miles);
					
					if(cab > 0)
					{
						taxi.addMilesSinceService(miles);
						output.setText("Fare : $"+cab);
						errorIssue.setText("");
						record= RecordType.FARE;
						recordCab.recordAction(taxi.getCabName(), record, miles, cab);
						
					}
					else
					{
						output.setText("");
						errorIssue.setText("Error: Not Enough gas.");
					}
				}
					
			}
			//shows amount of gas added to the tank if it does not exceed amount in the tank
			else if(choice == "Add Gas")
			{
				String gasAdd = amountInput.getText();
				String costString = gasPriceInput.getText();
				
				if(gasAdd.isEmpty() == false && costString.isEmpty() == false)
				{
					double gallons = Float.parseFloat(gasAdd);		
					double cost = Float.parseFloat(costString);
					record= RecordType.GAS;
					gas = taxi.addGas(gallons, cost);
					if(gas >=0)
					{
						cab = taxi.getGas();
						error = ("");
						
						recordCab.recordAction(taxi.getCabName(), record, gallons, gallons*cost);
					}
					else
					{
						cab = taxi.getGas();
						error = ((gas*-1)+ " was not added.");
						recordCab.recordAction(taxi.getCabName(), record, (gallons-(gas*-1)), (gallons-(gas*-1))*cost);
					}
							
					output.setText("Gas: "+cab + " Glns");
					errorIssue.setText(error);
				}
			}
			//services the taxi
			else if(choice == "Service")
			{
				record= RecordType.SERVICE;
				recordCab.recordAction(taxi.getCabName(), record, taxi.getMilesSinceService(), taxi.getServiceCost());
				taxi.serviceCab();				
				output.setText("Service performed.");
				errorIssue.setText("$"+taxi.getServiceCost()+" has been charged.");
				
				
			}	
			//services the taxi
			else if(choice == "Reset")
			{
				taxi.reset();				
				output.setText("Taxi Reset ");
				errorIssue.setText("");
						
			}
		}
		
		if(source == refreshBtn)
		{
			gasAvailOut.setText(""+taxi.getGas());
            milesAvailOut.setText(""+taxi.getMilesAvailable());
            milesSinceServiceOut.setText(""+taxi.getMilesSinceService());
            milesSinceResetOut.setText(""+taxi.getMilesSinceReset());
            grossSinceResetOut.setText(""+taxi.getGrossEarnings());
            netSinceResetOut.setText(""+ taxi.getNetEarnings());
           
		}	
		
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
				setCab(cl.get(i));
			}			
			i++;
		}		
	}
	
	public void setCab(ExtendedCab c)
	{
		taxi = c;
	}

}
