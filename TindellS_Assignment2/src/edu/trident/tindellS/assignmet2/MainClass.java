package edu.trident.tindellS.assignmet2;

import javax.swing.JOptionPane;

/*
 * initiaites the GUI for the program
 * 
 * 
 * @author Sean Tindell*/
public class MainClass {

	public static void main(String[] args) 
	{	
		String gasText;
		double gas;

		//Makes sure that the JOptionPane is not empty
		do{
			gasText = JOptionPane.showInputDialog(null, "Please input current gas.");
			if(gasText.isEmpty() != true)
			{
				gas = Float.parseFloat(gasText);
			}
			else
			{
				gas = 0;
			}
			
		}while(gasText.isEmpty() == true && gasText != null );

		ExtendedCab taxi =new ExtendedCab(gas);
		UI cabby = new UI(taxi);
		
		cabby.setVisible(true);
		

	}

}
