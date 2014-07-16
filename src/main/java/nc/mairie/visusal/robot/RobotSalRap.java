package nc.mairie.visusal.robot;

import nc.mairie.visusal.process.FicheSalaire;
import nc.mairie.visusal.process.OeAGENTSelection;
import nc.mairie.visusal.test.ZZZTESTEUR;
import nc.mairie.technique.BasicProcess;

/**
 * Insérez la description du type à cet endroit.
 *  Date de création : (28/10/02 10:14:36)
 * @author : Luc Bourdil
 */
public class RobotSalRap extends nc.mairie.robot.Robot{ 
/**
	 * 
	 */
	private static final long serialVersionUID = -1372860056078662006L;
/**
 * Commentaire relatif au constructeur Robot.
 */
public RobotSalRap() {
	super();
}
/**
 * Insérez la description de la méthode à cet endroit.
 *  Date de création : (28/10/02 10:16:34)
 */
public BasicProcess getDefaultProcess() {
	return new FicheSalaire();
}
/**
 * Insérez la description de la méthode à cet endroit.
 *  Date de création : (28/10/02 10:16:34)
 */
public BasicProcess getFirstProcess(String activite) throws Exception {

	//System.out.println("ACTIVITE="+activite);
	if (activite.equals("FicheSalaire")) {
		return new nc.mairie.visusal.process.FicheSalaire();
	}else {
		//return null;
		System.out.println("ACTIVITE IMPOSSIBLE: "+activite);
	}

	throw new Exception("Activite "+activite+" non déclarée dans le robot de navigation");
	
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (28/10/2002 11:59:52)
 * @return java.util.Hashtable
 */
protected java.util.Hashtable<String, String> initialiseNavigation() {

	java.util.Hashtable<String, String> navigation = new java.util.Hashtable<String, String>();

	//Classe FicheSalaire
	navigation.put(FicheSalaire.class.getName() + FicheSalaire.STATUT_RECHERCHER_AGENT, OeAGENTSelection.class.getName());
	//navigation.put(FicheSalaire.class.getName() + FicheSalaire.STATUT_SALAIRE_RAPPEL, FicheSalaire.class.getName());
	return navigation;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (28/10/2002 11:59:52)
 * @return java.util.Hashtable
 */
protected nc.mairie.robot.Testeur initialiseTesteur() {

	return new ZZZTESTEUR();
}
}
