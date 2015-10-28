package edu.trident.tindellS.assignmet5;
/*
 * program designed create a Management ui frame using the extended cab object
 * to create a new exteneded cab object and track a cabs data
 * edited to use drop downs instead of buttons
 * edited for cabbies
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

public class CabUI extends JFrame implements ActionListener, ItemListener, FocusListener, MaintenanceListener
{

	private ExtendedCab taxi;
	private String[] taxiChoices ={"Choose Action","Record Fares","Add Gas","Report Gas Availible","Report Miles Availible",
			"Service"};
	private String error;
	private double gas;
	private double cab;
	
	

	JComboBox cabChoice = new JComboBox(taxiChoices);
	JButton okBtn = new JButton("Ok");

	Font bigFont = new Font("Arial", Font.BOLD, 32);
	JTextField recordTripMiles = new JTextField(5);

	JTextField gasToAdd = new JTextField(5);
	
	JLabel title = new JLabel("Acme Taxi Co. Employee");
	JLabel notification = new JLabel("");
	JLabel errorIssue = new JLabel("");
	JLabel service = new JLabel("");
	JLabel label1 = new JLabel("");
	JLabel label2 = new JLabel("");
	
	final int WIDTH =500;
	final int HEIGHT = 250;
	
	public CabUI(ExtendedCab cb)
	{	
		super("Acme Taxi Co.");
		taxi = cb;		
		setSize(WIDTH, HEIGHT);
		setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
		// buttons and Text Areas
		title.setFont(bigFont);
		add(title);
		add(cabChoice);
		add(label1);
		add(gasToAdd);
		add(label2);
		add(recordTripMiles);
		add(okBtn);
		//labels that change in the display
		add(notification);
		add(errorIssue);
		add(service);
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		
		/*Set of action listeners for the buttons*/
		okBtn.setEnabled(false);
		recordTripMiles.setEnabled(false);
		gasToAdd.setEnabled(false);
		
		gasToAdd.addFocusListener(this);
		cabChoice.addItemListener(this);
		okBtn.addActionListener(this);
		taxi.addMaintenanceListener(this);

		
	}
	
	

	//follows dropdown changes
	public void itemStateChanged(ItemEvent e) 
	{
		Object choice = cabChoice.getSelectedItem();
	    if (e.getStateChange() == ItemEvent.SELECTED) {
	    	if(choice == "Record Fares")
	    	{	
	    		double sinService = taxi.getMilesSinceService();
	    		if(taxi.getNeedsService() == true)
	    		{
	    			taxi.overrideServiceNeeded();
	    		}
	    		else
	    		{
	    			label2.setText("Miles:");
	    			label1.setText("");
	    			recordTripMiles.setToolTipText("Enter Miles For Trip");
	    			gasToAdd.setToolTipText("");
	    			gasToAdd.setEnabled(false);
	    			recordTripMiles.setEnabled(true);
	    			okBtn.setEnabled(true);
	    		}
	    		

	    	}
	    	else if (choice == "Add Gas")
	    	{
	    		label2.setText("Cost:");
    			label1.setText("Gallons: ");
	    		recordTripMiles.setEnabled(true);
	    		gasToAdd.setEnabled(true);
	    		gasToAdd.setToolTipText("Enter gas to add");
	    		recordTripMiles.setToolTipText("Enter Cost Per Gallon"); 
	    	}
	    	else
	    	{
	    		label2.setText("");
    			label1.setText("");
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
		
		Object choice = cabChoice.getSelectedItem();
		//button that records the trip and warns if unable
		
		if (taxi.getNeedsService() == false)
				service.setText("");
		
		
		if(choice == "Record Fares")
		{
			taxi.setOverride(false);
			String trip = recordTripMiles.getText();
			int miles = Integer.parseInt(trip);
			
			cab = taxi.recordTrip(miles);
			taxi.overrideServiceNeeded();
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

		//services the taxi
		else if(choice == "Service")
		{
			taxi.serviceCab();				
			notification.setText("Service to the taxi has been performed. \n $25 has been charged.");
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
	public void maintenanceNeeded()
	{
		
		if(taxi.getIsServiceOverriden() == true)
		{			
			service.setText("Overide given 1 fare allowed");
			gasToAdd.setEnabled(false);
			recordTripMiles.setEnabled(true);
			okBtn.setEnabled(true);
			
		}
		else
		{
			gasToAdd.setEnabled(false);
			recordTripMiles.setEnabled(false);
			okBtn.setEnabled(false);
			service.setText("Service Needed. No Fairs will be allowed till service has been Completed. Overide possible Via management.");
		}
		
	}
	public void maintenancePerformed()
	{
		okBtn.setEnabled(true);
		service.setText("Maintenance Performed");
	}
	
	
}
