package edu.trident.tindellS.assignmet1;
/*
 * initiaites the GUI for the program
 * 
 * 
 * @author Sean Tindell*/
public class MainClass {

	public static void main(String[] args) 
	{	
		Cab taxi =new Cab();
		UI cabby = new UI(taxi);
		
		cabby.setVisible(true);
		

	}

}
