package nc.mairie.visusal.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier PaieRubrique
 */
public class PaieRubriqueBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur PaieRubriqueBroker.
 * @param aMetier aMetier
 */
public PaieRubriqueBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.salairerappels.metier.PaieRubriqueMetier
 */
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new PaieRubrique() ;
}
/**
 * @return JavaSource/nc.mairie.salairerappels.metier.PaieRubriqueMetier
 */
protected PaieRubrique getMyPaieRubrique() {
	return (PaieRubrique)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
protected java.lang.String definirNomTable() {
	return "MAIRIE.SPRUBR";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NORUBR", new BasicRecord("NORUBR", "NUMERIC", getMyPaieRubrique().getClass().getField("norubr"), "STRING"));
	mappage.put("LIRUBR", new BasicRecord("LIRUBR", "CHAR", getMyPaieRubrique().getClass().getField("lirubr"), "STRING"));
	mappage.put("TYIMPO", new BasicRecord("TYIMPO", "CHAR", getMyPaieRubrique().getClass().getField("tyimpo"), "STRING"));
	mappage.put("CDRCAF", new BasicRecord("CDRCAF", "CHAR", getMyPaieRubrique().getClass().getField("cdrcaf"), "STRING"));
	mappage.put("RUBRAP", new BasicRecord("RUBRAP", "NUMERIC", getMyPaieRubrique().getClass().getField("rubrap"), "STRING"));
	mappage.put("TYPRIM", new BasicRecord("TYPRIM", "CHAR", getMyPaieRubrique().getClass().getField("typrim"), "STRING"));
	mappage.put("TYRUBR", new BasicRecord("TYRUBR", "CHAR", getMyPaieRubrique().getClass().getField("tyrubr"), "STRING"));
	mappage.put("CATPOS", new BasicRecord("CATPOS", "CHAR", getMyPaieRubrique().getClass().getField("catpos"), "STRING"));
	mappage.put("DATINA", new BasicRecord("DATINA", "NUMERIC", getMyPaieRubrique().getClass().getField("datina"), "STRING"));
	return mappage;
}
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @return boolean
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public boolean creerPaieRubrique(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @return boolean
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public boolean modifierPaieRubrique(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @return boolean
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public boolean supprimerPaieRubrique(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : PaieRubrique.
 * @return java.util.ArrayList
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public java.util.ArrayList<PaieRubrique> listerPaieRubrique(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" with UR");
}
/**
 * Retourne un PaieRubrique par NORUBR
 * @param aTransaction aTransaction
 * @param cle cle
 * @return PaieRubrique
 * @throws Exception Exception
 */
public PaieRubrique chercherPaieRubrique(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (PaieRubrique)executeSelect(aTransaction,"select * from "+getTable()+" where NORUBR = "+cle+" with UR");
}

/**
 * 
 * Retourne un PaieRubrique par RUBRAP
 * @param aTransaction aTransaction
 * @param cle cle
 * @return PaieRubrique
 * @throws Exception Exception
 */
public PaieRubrique chercherPaieRubriqueRappelCorrespondante(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (PaieRubrique)executeSelect(aTransaction,"select * from "+getTable()+" where RUBRAP = "+cle+" with UR");
}
}
