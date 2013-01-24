package nc.mairie.visusal.robot;

import nc.mairie.technique.BasicProcess;
import nc.mairie.visusal.process.FicheSalaire;
import nc.mairie.visusal.process.OeAGENTSelection;
import nc.mairie.visusal.test.ZZZTESTEUR;

/**
 * Ins�rez la description du type � cet endroit.
 *  Date de cr�ation : (28/10/02 10:14:36)
 * @author : Luc Bourdil
 */
public class RobotSalRap extends nc.mairie.robot.Robot{ 
/**
 * Commentaire relatif au constructeur Robot.
 */
public RobotSalRap() {
	super();
}
/**
 * Ins�rez la description de la m�thode � cet endroit.
 *  Date de cr�ation : (28/10/02 10:16:34)
 */
public BasicProcess getDefaultProcess() {
	return new FicheSalaire();
}
/**
 * Ins�rez la description de la m�thode � cet endroit.
 *  Date de cr�ation : (28/10/02 10:16:34)
 */
public BasicProcess getFirstProcess(String activite) throws Exception {

	//System.out.println("ACTIVITE="+activite);
	if (activite.equals("FicheSalaire")) {
		return new FicheSalaire();
	}else {
		//return null;
		System.out.println("ACTIVITE IMPOSSIBLE: "+activite);
	}

	throw new Exception("Activite "+activite+" non d�clar�e dans le robot de navigation");
	
}
/**
 * Ins�rez la description de la m�thode ici.
 *  Date de cr�ation : (28/10/2002 11:59:52)
 * @return java.util.Hashtable
 */
protected java.util.Hashtable initialiseNavigation() {

	java.util.Hashtable navigation = new java.util.Hashtable();

	//Classe FicheSalaire
	navigation.put(FicheSalaire.class.getName() + FicheSalaire.STATUT_RECHERCHER_AGENT, OeAGENTSelection.class.getName());
	//navigation.put(FicheSalaire.class.getName() + FicheSalaire.STATUT_SALAIRE_RAPPEL, FicheSalaire.class.getName());
	return navigation;
}
/**
 * Ins�rez la description de la m�thode ici.
 *  Date de cr�ation : (28/10/2002 11:59:52)
 * @return java.util.Hashtable
 */
protected nc.mairie.robot.Testeur initialiseTesteur() {

	return new ZZZTESTEUR();
}
}
