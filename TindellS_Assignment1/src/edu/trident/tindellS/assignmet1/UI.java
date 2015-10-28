package edu.trident.tindellS.assignmet1;
/*
 * program designed create a ui frame using the cab object
 * to create a new cab object and track a cabs data
 * 
 * @author Sean Tindell*/
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class UI extends JFrame implements ActionListener
{
	private Cab taxi;
	private String error;
	private double gas;
	private double cab;
	

	
	JButton recordTrip = new JButton("Record Trip");
	Font bigFont = new Font("Arial", Font.BOLD, 32);
	JTextField recordTripMiles = new JTextField(5);
	JButton addGas = new JButton("Add Gas");
	JTextField gasToAdd = new JTextField(5);
	JButton rptGasAvail = new JButton("Report Gas Availible");
	JButton rptMilesAvail = new JButton("Report Miles Availible");
	JButton rptMilesDriven= new JButton("Report Miles Driven");
	JButton rptGrossEarnings = new JButton("Report Gross Earnings");
	JButton reset = new JButton("reset");
	JLabel title = new JLabel("Acme Taxi Co.");
	JLabel notification = new JLabel("");
	JLabel errorIssue = new JLabel("");
	
	final int WIDTH =250;
	final int HEIGHT = 400;
	
	public UI(Cab cb)
	{	
		super("Acme Taxi Co.");
		taxi =cb;		
		setSize(WIDTH, HEIGHT);
		setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
		// buttons and Text Areas
		title.setFont(bigFont);
		add(title);
		add(recordTrip);
		add(recordTripMiles);
		add(addGas);
		add(gasToAdd);
		add(rptGasAvail);
		add(rptMilesAvail);
		add(rptMilesDriven);
		add(rptGrossEarnings);
		add(reset);
		//labels that change in the display
		add(notification);
		add(errorIssue);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		/*Set of action listeners for the buttons*/
		recordTrip.addActionListener(this);
		addGas.addActionListener(this);
		rptGasAvail.addActionListener(this);
		rptMilesAvail.addActionListener(this);
		rptMilesDriven.addActionListener(this);
		rptGrossEarnings.addActionListener(this);
		reset.addActionListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		//button that records the trip and warns if unable
		if(source == recordTrip)
		{
			String trip = recordTripMiles.getText();
			int miles = Integer.parseInt(trip);
			
			cab = taxi.recordTrip(miles);
			
			if(cab > 0)
			{
				
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
		else if(source == addGas)
		{
			String gasAdd = gasToAdd.getText();
			double gallons = Integer.parseInt(gasAdd);
			gas = taxi.addGas(gallons);
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
		else if(source == rptGasAvail)
		{
			cab = taxi.getGas();					
			notification.setText("Gas Availible: "+cab + " Gallons");
			errorIssue.setText("");
		}
		//displays miles available on the current tank
		else if(source == rptMilesAvail)
		{
			cab = taxi.getMilesAvailable();					
			notification.setText("Miles Availible: "+cab);
			errorIssue.setText("");
		}
		//displays total miles driven 
		else if(source == rptMilesDriven)
		{
			cab = taxi.getMilesSinceReset();				
			notification.setText("Miles Driven: "+cab );
			errorIssue.setText("");
		}
		
		//displays total earnings
		else if(source == rptGrossEarnings)
		{
			cab = taxi.getGrossEarnings();				
			notification.setText("Gross Earnings: "+cab);
			errorIssue.setText("");
		}
		//resets earnings and milage
		else if(source == reset)
		{
			taxi.reset();
			notification.setText("");
			errorIssue.setText("");
			
		}
		
		
	}
	
	
}
