package edu.trident.tindellS.assignmet2;
/*
 * program designed create a ui frame using the cab object
 * to create a new cab object and track a cabs data
 * edited to use drop downs instead of buttons
 * 
 * @author Sean Tindell*/
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class UI extends JFrame implements ActionListener, ItemListener, FocusListener
{
	private ExtendedCab taxi;
	private String[] taxiChoices ={"Choose Action","Record Trip","Add Gas","Report Gas Availible","Report Miles Availible","Report Miles Driven","Report Gross Earnings",
			"Service","Miles Since Service","Report Net Earnings", "Reset"};
	private String error;
	private double gas;
	private double cab;
	
	

	JComboBox cabChoice = new JComboBox(taxiChoices);
	JButton okBtn = new JButton("Ok");

	Font bigFont = new Font("Arial", Font.BOLD, 32);
	JTextField recordTripMiles = new JTextField(5);

	JTextField gasToAdd = new JTextField(5);
	
	JLabel title = new JLabel("Acme Taxi Co.");
	JLabel notification = new JLabel("");
	JLabel errorIssue = new JLabel("");
	
	final int WIDTH =400;
	final int HEIGHT = 250;
	
	public UI(ExtendedCab cb)
	{	
		super("Acme Taxi Co.");
		taxi = cb;		
		setSize(WIDTH, HEIGHT);
		setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
		// buttons and Text Areas
		title.setFont(bigFont);
		add(title);
		add(cabChoice);
		add(gasToAdd);
		add(recordTripMiles);
		add(okBtn);
		//labels that change in the display
		add(notification);
		add(errorIssue);
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		
		/*Set of action listeners for the buttons*/
		okBtn.setEnabled(false);
		recordTripMiles.setEnabled(false);
		gasToAdd.setEnabled(false);
		
		gasToAdd.addFocusListener(this);
		cabChoice.addItemListener(this);
		okBtn.addActionListener(this);
		
		
	}
	
	

	//follows dropdown changes
	public void itemStateChanged(ItemEvent e) 
	{
		Object choice = cabChoice.getSelectedItem();
	    if (e.getStateChange() == ItemEvent.SELECTED) {
	    	if(choice == "Record Trip")
	    	{
	    		recordTripMiles.setToolTipText("Enter Miles For Trip");
	    		gasToAdd.setToolTipText("");
	    		gasToAdd.setEnabled(false);
	    		recordTripMiles.setEnabled(true);
	    		okBtn.setEnabled(true);

	    	}
	    	else if (choice == "Add Gas")
	    	{
	    		recordTripMiles.setEnabled(true);
	    		gasToAdd.setEnabled(true);
	    		gasToAdd.setToolTipText("Enter gas to add");
	    		recordTripMiles.setToolTipText("Enter Cost Per Gallon"); 
	    	}
	    	else
	    	{
	    		recordTripMiles.setEnabled(false);
	    		gasToAdd.setEnabled(false);
	    		okBtn.setEnabled(true);
	    		gasToAdd.setToolTipText("");
	    		recordTripMiles.setToolTipText("");
	    	}
	   }
	    else
	    {
		recordTripMiles.setEnabled(false);
		gasToAdd.setEnabled(false);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		Object choice = cabChoice.getSelectedItem();
		//button that records the trip and warns if unable
		if(choice == "Record Trip")
		{
			String trip = recordTripMiles.getText();
			int miles = Integer.parseInt(trip);
			
			cab = taxi.recordTrip(miles);
			
			if(cab > 0)
			{
				taxi.addMilesSinceService(miles);
				notification.setText("Fare : $"+cab);
				errorIssue.setText("");
			}
			else
			{
				notification.setText("Miles Availible for trip "+taxi.getMilesAvailable());
				errorIssue.setText("Error: Not Enough for trip please add gas.");
			}
		}
		//shows amount of gas added to the tank if it does not exceed amount in the tank
		else if(choice == "Add Gas")
		{
			String gasAdd = gasToAdd.getText();
			double gallons = Float.parseFloat(gasAdd);
			
			String costString = recordTripMiles.getText();
			double cost = Float.parseFloat(costString);
			
			gas = taxi.addGas(gallons, cost);
			if(gas >=0)
			{
				cab = taxi.getGas();
				error = ("");
			}
			else
			{
				cab = taxi.getGas();
				error = ("You have added to much gas "+ (gas*-1)+ " was not added to the tank.");
			}
					
			notification.setText("Gas Added: "+cab + " Gallons");
			errorIssue.setText(error);
		}
		//displays gas available
		else if(choice == "Report Gas Availible")
		{
			cab = taxi.getGas();					
			notification.setText("Gas Availible: "+cab + " Gallons");
			errorIssue.setText("");
		}
		//displays miles available on the current tank
		else if(choice == "Report Miles Availible")
		{
			cab = taxi.getMilesAvailable();					
			notification.setText("Miles Availible: "+cab);
			errorIssue.setText("");
		}
		//displays total miles driven 
		else if(choice == "Report Miles Driven")
		{
			cab = taxi.getMilesSinceReset();				
			notification.setText("Miles Driven: "+cab );
			errorIssue.setText("");
		}
		
		//displays total earnings
		else if(choice == "Report Gross Earnings")
		{
			cab = taxi.getGrossEarnings();				
			notification.setText("Gross Earnings: "+cab);
			errorIssue.setText("");
		}
		//services the taxi
		else if(choice == "Service")
		{
			taxi.serviceCab();				
			notification.setText("Service to the taxi has been performed. \n $25 has been charged.");
			errorIssue.setText("");
		}
		//displays net earnings
		else if(choice == "Report Net Earnings")
		{
			cab = taxi.getNetEarnings();				
			notification.setText("Net Earnings: "+cab);
			errorIssue.setText("");
		}
		//displays total earnings
		else if(choice == "Miles Since Service")
		{
			cab = taxi.getMilesSinceService();				
			notification.setText("Miles Since Servise: "+cab);
			errorIssue.setText("");
		}
		//resets earnings and milage
		else if(choice == "Reset")
		{
			taxi.reset();
			notification.setText("");
			errorIssue.setText("");
			
		}
		
		
	}



//when focused enables the ok button
	@Override
	public void focusGained(FocusEvent arg0) 
	{
			okBtn.setEnabled(true);
	}

	//when blurred if there is something in the box enables button if not disables button
	@Override
	public void focusLost(FocusEvent arg0) {
		if(gasToAdd.getText().equals(""))
		{
			okBtn.setEnabled(false);
		}
		else
		{
			okBtn.setEnabled(true);
		}
		
	}
	
	
}
