package edu.trident.tindellS.finalAsignment;
/*
 *Sorts the cabID and can be customized to be sorted by whatever needed.
 *
 * @author Sean Tindell*/
import java.util.Comparator;

public class Sorter  { 
    
    /*Comparator for sorting the list by CabID Name*/
    public static Comparator<CabInfo> CabIdSort = new Comparator<CabInfo>() 
    {

	public int compare(CabInfo s1, CabInfo s2) {
	   String CabID1 = s1.getIDCab().toUpperCase();
	   String CabID2 = s2.getIDCab().toUpperCase();

	   //ascending order
	   return CabID1.compareTo(CabID2);


    }};
}