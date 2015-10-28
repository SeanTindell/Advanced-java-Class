package edu.trident.tindellS.assignmet4;
/*
 * program designed create a Management ui frame using the extended cab object
 * to create a new exteneded cab object and track a cabs data
 * edited to use drop downs instead of buttons
 * edited for management
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

public class ManagementUI extends JFrame implements ActionListener, MaintenanceListener
{
	private ExtendedCab taxi;
	private String[] taxiChoices ={"Choose Action","Report Miles Driven","Report Gross Earnings",
			"Miles Since Service","Report Net Earnings", "Reset"};
	private String error;
	private double gas;
	private double cab;
	
	

	JComboBox cabChoice = new JComboBox(taxiChoices);
	JButton okBtn = new JButton("Ok");
	JButton overrideBtn = new JButton("Override");
	JLabel service = new JLabel("");
	Font bigFont = new Font("Arial", Font.BOLD, 32);
	JTextField recordTripMiles = new JTextField(5);	
	JLabel title = new JLabel("Acme Taxi Co. Management");
	JLabel notification = new JLabel("");
	JLabel errorIssue = new JLabel("");
	
	final int WIDTH =500;
	final int HEIGHT = 250;
	
	public ManagementUI(ExtendedCab cb)
	{	
		super("Acme Taxi Co.");
		taxi = cb;		
		setSize(WIDTH, HEIGHT);
		setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
		// buttons and Text Areas
		title.setFont(bigFont);
		add(title);
		add(cabChoice);
		add(okBtn);
		add(overrideBtn);
		//labels that change in the display
		add(notification);
		add(errorIssue);
		add(service);
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		
		/*Set of action listeners for the buttons*/
		okBtn.setEnabled(true);
		recordTripMiles.setEnabled(false);
		overrideBtn.setEnabled(false);
		overrideBtn.addActionListener(this);
		okBtn.addActionListener(this);
		taxi.addMaintenanceListener(this);
		
		
	}
	
	


	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		Object choice = cabChoice.getSelectedItem();
		//taxi.overrideServiceNeeded();
		
		
			if(source == overrideBtn)
			{
				taxi.setOverride(true);
			}
			else if(source == okBtn)
			{
				//displays total miles driven 
				if(choice == "Report Miles Driven")
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
				
				//displays net earnings
				else if(choice == "Report Net Earnings")
				{
					cab = taxi.getNetEarnings();				
					notification.setText("Net Earnings: "+cab);
					errorIssue.setText("");
				}
				//resets earnings and milage
				else if(choice == "Miles Since Service")
				{
					cab =  taxi.getMilesSinceService();
					notification.setText("Miles Since Service: " + cab );
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
	}





	@Override
	public void maintenanceNeeded() 
	{
		overrideBtn.setEnabled(true);
		service.setText("Maintenance Required. No Fairs Allowed. Overide Availible");
		
	}





	@Override
	public void maintenancePerformed() 
	{
		overrideBtn.setEnabled(false);
		service.setText("Maintenance Performed");
		taxi.setOverride(false);
		
	}			
}
