/*
 * Created on 6 févr. 2009
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nc.mairie.visusal.metier;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * @author fonol77
 * Méthodes génériques utilitaires
 * 
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Utils {
	
	public static int IHeuresParJour=8;
	
	/**
	 * 
	 * @param l1
	 * @param l2
	 * @return ArrayListe l1 ayant éléminé de la liste l1 les éléments en communs avec l2
	 * fonctionne uniquement avec une liste l1 n'ayant pas 2 elements identiques
	 */
	public static ArrayList<?> Elim_doubure(ArrayList<?> l1,ArrayList<?> l2)
	{
		//System.out.println("L1="+l1);
		//System.out.println("L2="+l2);
		if (null==l1)
			return null;
		
		if (null!=l2){
			for(int i = 0; i <l2.size(); i++){
				for(int j = 0; j <l1.size(); j++)
				{
					//System.out.println("L2:"+l2.get(i));
					//System.out.println("L1:"+l1.get(j));
					if(l2.get(i) == l1.get(j))
						l1.remove(j);
					
				}
			}
		}
		return l1;
		
	} 
	
	
	/**
	 * 
	 * @param al
	 * @return ArrayList entrée moins ses doublons.
	 */
	public static ArrayList<Object> antiDoublon(ArrayList<?> al) {
        
        ArrayList<Object> al2 = new ArrayList<Object>();
        for (int i=0; i<al.size(); i++) {
            Object o = al.get(i);
            if (!al2.contains(o))
                al2.add(o);
        }
        al = null;
        return al2;
        
    }

	public static String TraiterMoisFr(String datePerCou){
		//JANVIER
		Date madate = new Date();
		SimpleDateFormat dateformentree=new SimpleDateFormat("yyyyMM");
		SimpleDateFormat dateformsortie=new SimpleDateFormat("MMMMM", Locale.FRANCE);
			
		try{	
		madate = dateformentree.parse(datePerCou);			
		}catch (Exception e){
			System.out.println("Erreur de format de date AAAAMM impossible de traiter :"+datePerCou);
			return "";
		}
		return dateformsortie.format(madate).toUpperCase();
	}
	
		
	public static String TraiterAnneeFr(String datePerCou){
		//2009
		Date madate = new Date();
		SimpleDateFormat dateformentree=new SimpleDateFormat("yyyyMM");
		SimpleDateFormat dateformsortie=new SimpleDateFormat("yyyy", Locale.FRANCE);
			
		try{	
		madate = dateformentree.parse(datePerCou);			
		}catch (Exception e){
			System.out.println("Erreur de format de date AAAAMM impossible de traiter :"+datePerCou);
			return "";
		}
		return dateformsortie.format(madate).toUpperCase();
	}
	
	public static String TraiterDateFr(String datePerCou){
		//JANVIER 2009
		Date madate = new Date();
		SimpleDateFormat dateformentree=new SimpleDateFormat("yyyyMM");
		SimpleDateFormat dateformsortie=new SimpleDateFormat("MMMMM yyyy", Locale.FRANCE);
//DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);
			
		try{	
		madate = dateformentree.parse(datePerCou);			
		}catch (Exception e){
			System.out.println("Erreur de format de date AAAAMM impossible de traiter :"+datePerCou);
			return "";
		}
		return dateformsortie.format(madate).toUpperCase();
		
	}
	
	public static String TraiterDateFrShort(String datePerCou){
		//JAN. 2009
		//JUL. 2008
		//JUN. 2007
		String datesortie="";
		Date madate = new Date();
		SimpleDateFormat dateformentree=new SimpleDateFormat("yyyyMM");
		SimpleDateFormat dateformsortie=new SimpleDateFormat("MMM. yyyy", Locale.FRANCE);
			
		try{	
		madate = dateformentree.parse(datePerCou);			
		}catch (Exception e){
			System.out.println("Erreur de format de date AAAAMM impossible de traiter :"+datePerCou);
			return "";
		}
		datesortie=dateformsortie.format(madate).toUpperCase();
		
		if (datesortie.indexOf("JUIN")!=-1)
			datesortie=datesortie.replaceAll("JUIN", "JUN");
		if (datesortie.indexOf("JUIL")!=-1)
			datesortie=datesortie.replaceAll("JUIL", "JUL");
		
		return datesortie; 
	}
	
	public static String TraiterNombreIt(String sNombre, int iNombreVirgule){
		double dNombre;
		// NB US=123,456,789.23
		// NB IT=123.456.789,23
		// NB FR=123 456 789,23
		NumberFormat nfi = NumberFormat.getInstance(Locale.ITALY);
		nfi.setMaximumFractionDigits(2);
		nfi.setMinimumFractionDigits(iNombreVirgule);
		try{
			dNombre=Double.parseDouble(sNombre.replace(',','.'));
			sNombre=nfi.format(dNombre);
		}catch(Exception e){
			System.out.println("Erreur de format de nombre impossible de traiter :"+sNombre);
			return sNombre;
		}
		return sNombre;
	}
	
	public static String TraiterNombreIt(String sNombre){
		return TraiterNombreIt(sNombre,0);
	}
	

	public static String ConcatDebutValeurStringTaille(String monStringDepart, String sValeurDevant, int iTailleVoulue){
		if (sValeurDevant!=null&&sValeurDevant.length()>0){
			while(monStringDepart.length()<iTailleVoulue){
				monStringDepart=sValeurDevant.concat(monStringDepart);
			}
		}
		return monStringDepart;
	}
	
	
	/**
	 * 
	 * Fonction qui transforme les heures en jours (8 heures par jour), à la demi journée près.
	 * @author fonol77
	 * @param fHeures un certain nombre d'heures
	 * @return le nombre de jour à la demi journée près (123 ou 132,5)
	 */
	public static String getJoursDemiJourneePres(Float fHeures){
		String sJours="";
		double dHeures=fHeures.doubleValue();
		Double dJoursEntiers=(dHeures/IHeuresParJour);
		int iJoursEntier=dJoursEntiers.intValue();
		sJours=String.valueOf(iJoursEntier);
		Double dHeuresRestantes=dHeures-(iJoursEntier*IHeuresParJour);
		if (dHeuresRestantes>=4)
			sJours=sJours+",5";
		return sJours;
	}
	/**
	 * Fonction qui transforme les heures en demi journée (à la demi journée près)
	 * @param fHeures
	 * @return le nombre de demi journées dans un entier
	 */
	public static String getDemiJournees(Float fHeures){
		double dHeures=fHeures.doubleValue();
		Double dDemiJours=(dHeures/(IHeuresParJour/2));
		int iDemiJours=dDemiJours.intValue();
		return String.valueOf(iDemiJours);
	}

}
