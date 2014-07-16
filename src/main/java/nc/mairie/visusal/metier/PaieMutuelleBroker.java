package nc.mairie.visusal.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier PaieMutuelle
 */
public class PaieMutuelleBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur PaieMutuelleBroker.
 */
public PaieMutuelleBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return src/nc.mairie.salairerappels.metier.PaieMutuelleMetier
 */
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new PaieMutuelle() ;
}
/**
 * @return src/nc.mairie.salairerappels.metier.PaieMutuelleMetier
 */
protected PaieMutuelle getMyPaieMutuelle() {
	return (PaieMutuelle)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
protected java.lang.String definirNomTable() {
	return "MAIRIE.SPMUTU";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("CDMUTU", new BasicRecord("CDMUTU", "NUMERIC", getMyPaieMutuelle().getClass().getField("cdmutu"), "STRING"));
	mappage.put("TXSAL", new BasicRecord("TXSAL", "DECIMAL", getMyPaieMutuelle().getClass().getField("txsal"), "STRING"));
	mappage.put("TXPAT", new BasicRecord("TXPAT", "DECIMAL", getMyPaieMutuelle().getClass().getField("txpat"), "STRING"));
	mappage.put("LIMUTU", new BasicRecord("LIMUTU", "CHAR", getMyPaieMutuelle().getClass().getField("limutu"), "STRING"));
	return mappage;
}
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerPaieMutuelle(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierPaieMutuelle(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerPaieMutuelle(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : PaieMutuelle.
 * @return java.util.ArrayList
 */
public java.util.ArrayList<PaieMutuelle> listerPaieMutuelle(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" with UR");
}
/**
 * Retourne un PaieMutuelle.
 * @return PaieMutuelle
 */
public PaieMutuelle chercherPaieMutuelle(nc.mairie.technique.Transaction aTransaction, String nomatr) throws Exception {
	return (PaieMutuelle)executeSelect(aTransaction,"select * from "+getTable()+", MAIRIE.SPCHGE  where MAIRIE.SPCHGE.CDCHAR="+getTable()+".CDMUTU AND NOMATR="+nomatr+" AND NORUBR='3000' AND DATFIN='0' with UR");
}
}
