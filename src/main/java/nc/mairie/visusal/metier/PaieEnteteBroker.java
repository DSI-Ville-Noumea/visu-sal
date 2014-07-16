package nc.mairie.visusal.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier PaieEntete
 */
public class PaieEnteteBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur PaieEnteteBroker.
 * @param aMetier aMetier 
 */
public PaieEnteteBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.salairerappels.metier.PaieEnteteMetier
 */
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new PaieEntete() ;
}
/**
 * @return JavaSource/nc.mairie.salairerappels.metier.PaieEnteteMetier
 */
protected PaieEntete getMyPaieEntete() {
	return (PaieEntete)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
protected java.lang.String definirNomTable() {
	return "PERSONNEL.HISTORIQUE";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NOMATR", new BasicRecord("NOMATR", "NUMERIC", getMyPaieEntete().getClass().getField("nomatr"), "STRING"));
	mappage.put("PERCOU", new BasicRecord("PERCOU", "NUMERIC", getMyPaieEntete().getClass().getField("percou"), "STRING"));
	mappage.put("LICATE", new BasicRecord("LICATE", "CHAR", getMyPaieEntete().getClass().getField("licate"), "STRING"));
	mappage.put("LIGRAD", new BasicRecord("LIGRAD", "CHAR", getMyPaieEntete().getClass().getField("ligrad"), "STRING"));
	mappage.put("FORF", new BasicRecord("FORF", "NUMERIC", getMyPaieEntete().getClass().getField("forf"), "STRING"));
	mappage.put("IBAN", new BasicRecord("IBAN", "CHAR", getMyPaieEntete().getClass().getField("iban"), "STRING"));
	mappage.put("INA", new BasicRecord("INA", "NUMERIC", getMyPaieEntete().getClass().getField("ina"), "STRING"));
	mappage.put("INM", new BasicRecord("INM", "NUMERIC", getMyPaieEntete().getClass().getField("inm"), "STRING"));
	mappage.put("LIMODR", new BasicRecord("LIMODR", "CHAR", getMyPaieEntete().getClass().getField("limodr"), "STRING"));
	mappage.put("LIBHOR", new BasicRecord("LIBHOR", "CHAR", getMyPaieEntete().getClass().getField("libhor"), "STRING"));
	mappage.put("NBCONG", new BasicRecord("NBCONG", "NUMERIC", getMyPaieEntete().getClass().getField("nbcong"), "STRING"));
	mappage.put("NOM", new BasicRecord("NOM", "CHAR", getMyPaieEntete().getClass().getField("nom"), "STRING"));
	mappage.put("PRENOM", new BasicRecord("PRENOM", "CHAR", getMyPaieEntete().getClass().getField("prenom"), "STRING"));
	mappage.put("NURETR", new BasicRecord("NURETR", "CHAR", getMyPaieEntete().getClass().getField("nuretr"), "STRING"));
	mappage.put("NUMUTU", new BasicRecord("NUMUTU", "CHAR", getMyPaieEntete().getClass().getField("numutu"), "STRING"));
	mappage.put("LISERV", new BasicRecord("LISERV", "CHAR", getMyPaieEntete().getClass().getField("liserv"), "STRING"));
	mappage.put("LIECOL", new BasicRecord("LIECOL", "CHAR", getMyPaieEntete().getClass().getField("liecol"), "STRING"));
	mappage.put("CDCATE", new BasicRecord("CDCATE", "NUMERIC", getMyPaieEntete().getClass().getField("cdcate"), "STRING"));
	mappage.put("CDCHAR", new BasicRecord("CDCHAR", "NUMERIC", getMyPaieEntete().getClass().getField("cdchar"), "STRING"));
	mappage.put("CDBANQ", new BasicRecord("CDBANQ", "NUMERIC", getMyPaieEntete().getClass().getField("cdbanq"), "STRING"));
	mappage.put("CDGUIC", new BasicRecord("CDGUIC", "NUMERIC", getMyPaieEntete().getClass().getField("cdguic"), "STRING"));
	mappage.put("NOCPTE", new BasicRecord("NOCPTE", "CHAR", getMyPaieEntete().getClass().getField("nocpte"), "STRING"));
	mappage.put("CLERIB", new BasicRecord("CLERIB", "NUMERIC", getMyPaieEntete().getClass().getField("clerib"), "STRING"));
	mappage.put("LIBANQ", new BasicRecord("LIBANQ", "CHAR", getMyPaieEntete().getClass().getField("libanq"), "STRING"));
	mappage.put("INDCOR", new BasicRecord("INDCOR", "NUMERIC", getMyPaieEntete().getClass().getField("indcor"), "STRING"));
	mappage.put("VAP", new BasicRecord("VAP", "NUMERIC", getMyPaieEntete().getClass().getField("vap"), "STRING"));
	mappage.put("COEF", new BasicRecord("COEF", "NUMERIC", getMyPaieEntete().getClass().getField("coef"), "STRING"));
	mappage.put("PTCAFAT", new BasicRecord("PTCAFAT", "NUMERIC", getMyPaieEntete().getClass().getField("ptcafat"), "STRING"));
	mappage.put("SMIG", new BasicRecord("SMIG", "NUMERIC", getMyPaieEntete().getClass().getField("smig"), "STRING"));
	mappage.put("MODREG", new BasicRecord("MODREG", "CHAR", getMyPaieEntete().getClass().getField("modreg"), "STRING"));
	mappage.put("IPOMP", new BasicRecord("IPOMP", "CHAR", getMyPaieEntete().getClass().getField("ipomp"), "STRING"));
	mappage.put("SOLDE1", new BasicRecord("SOLDE1", "DECIMAL", getMyPaieEntete().getClass().getField("solde1"), "STRING"));
	mappage.put("SOLDE2", new BasicRecord("SOLDE2", "DECIMAL", getMyPaieEntete().getClass().getField("solde2"), "STRING"));
	mappage.put("NBRCP", new BasicRecord("NBRCP", "DECIMAL", getMyPaieEntete().getClass().getField("nbrcp"), "STRING"));
	return mappage;
}
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @return boolean
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public boolean creerPaieEntete(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @return boolean
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public boolean modifierPaieEntete(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @return boolean
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public boolean supprimerPaieEntete(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : PaieEntete.
 * @return java.util.ArrayList
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public java.util.ArrayList<PaieEntete> listerPaieEntete(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" with UR");
}
/**
 * Retourne un PaieEntete.
 * @param aTransaction Traansaction
 * @param nomatr nomatr 
 * @param moiscourant moiscourant 
 * @return PaieEntete
 * @throws Exception Exception
 */
public PaieEntete chercherPaieEntete(nc.mairie.technique.Transaction aTransaction, String nomatr, String moiscourant) throws Exception {
	return (PaieEntete)executeSelect(aTransaction,"select * from "+getTable()+" where NOMATR = "+nomatr+" AND PERCOU="+moiscourant+" with UR");
}
}
