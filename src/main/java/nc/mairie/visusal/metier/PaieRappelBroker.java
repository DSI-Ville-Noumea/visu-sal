package nc.mairie.visusal.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier PaieRappel
 */
public class PaieRappelBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur PaieRappelBroker.
 * @param aMetier aMetier
 */
public PaieRappelBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.salairerappels.metier.PaieRappelMetier
 */
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new PaieRappel() ;
}
/**
 * @return JavaSource/nc.mairie.salairerappels.metier.PaieRappelMetier
 */
protected PaieRappel getMyPaieRappel() {
	return (PaieRappel)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
protected java.lang.String definirNomTable() {
	return "MAIRIE.SPRAPL";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("PERCOU", new BasicRecord("PERCOU", "NUMERIC", getMyPaieRappel().getClass().getField("percou"), "STRING"));
	mappage.put("PERRAP", new BasicRecord("PERRAP", "NUMERIC", getMyPaieRappel().getClass().getField("perrap"), "STRING"));
	mappage.put("NOMATR", new BasicRecord("NOMATR", "NUMERIC", getMyPaieRappel().getClass().getField("nomatr"), "STRING"));
	mappage.put("NORUBR", new BasicRecord("NORUBR", "NUMERIC", getMyPaieRappel().getClass().getField("norubr"), "STRING"));
	mappage.put("TXSAL", new BasicRecord("TXSAL", "DECIMAL", getMyPaieRappel().getClass().getField("txsal"), "STRING"));
	mappage.put("NB", new BasicRecord("NB", "DECIMAL", getMyPaieRappel().getClass().getField("nb"), "STRING"));
	mappage.put("MTBASE", new BasicRecord("MTBASE", "DECIMAL", getMyPaieRappel().getClass().getField("mtbase"), "STRING"));
	mappage.put("MTPSAL", new BasicRecord("MTPSAL", "DECIMAL", getMyPaieRappel().getClass().getField("mtpsal"), "STRING"));
	mappage.put("TXPAT", new BasicRecord("TXPAT", "DECIMAL", getMyPaieRappel().getClass().getField("txpat"), "STRING"));
	mappage.put("MTPPAT", new BasicRecord("MTPPAT", "DECIMAL", getMyPaieRappel().getClass().getField("mtppat"), "STRING"));
	return mappage;
}
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @return boolean
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public boolean creerPaieRappel(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @return boolean
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public boolean modifierPaieRappel(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @return boolean
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public boolean supprimerPaieRappel(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : PaieRappel.
 * @return java.util.ArrayList
 * @param aTransaction Transaction
 * @param NoMatr NoMatr
 * @param PeriodeCou PeriodeCou
 * @param NoRubrique NoRubrique
 * @throws Exception Exception
 */
public java.util.ArrayList<PaieRappel> listerPaieRappel(nc.mairie.technique.Transaction aTransaction, String NoMatr, String PeriodeCou, String NoRubrique) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where NOMATR="+NoMatr+" AND PERCOU="+PeriodeCou+" AND NORUBR="+NoRubrique+" ORDER BY PERRAP desc with UR");
}
/**
 * Retourne un PaieRappel.
 * @param aTransaction aTransaction
 * @param cle cle
 * @return PaieRappel
 * @throws Exception Exception
 */
public PaieRappel chercherPaieRappel(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (PaieRappel)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+" with UR");
}
}
