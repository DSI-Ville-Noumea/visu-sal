package nc.mairie.visusal.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier PaieElement
 */
public class PaieElementBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur PaieElementBroker.
 * @param aMetier azMetier
 */
public PaieElementBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.salairerappels.metier.PaieElementMetier
 */
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new PaieElement() ;
}
/**
 * @return JavaSource/nc.mairie.salairerappels.metier.PaieElementMetier
 */
protected PaieElement getMyPaieElement() {
	return (PaieElement)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
protected java.lang.String definirNomTable() {
	return "MAIRIE.SPSALRV1";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("PERCOU", new BasicRecord("PERCOU", "NUMERIC", getMyPaieElement().getClass().getField("percou"), "STRING"));
	mappage.put("NOMATR", new BasicRecord("NOMATR", "NUMERIC", getMyPaieElement().getClass().getField("nomatr"), "STRING"));
	mappage.put("NORUBR", new BasicRecord("NORUBR", "NUMERIC", getMyPaieElement().getClass().getField("norubr"), "STRING"));
	mappage.put("TXSAL", new BasicRecord("TXSAL", "DECIMAL", getMyPaieElement().getClass().getField("txsal"), "STRING"));
	mappage.put("NB", new BasicRecord("NB", "DECIMAL", getMyPaieElement().getClass().getField("nb"), "STRING"));
	mappage.put("MTBASE", new BasicRecord("MTBASE", "DECIMAL", getMyPaieElement().getClass().getField("mtbase"), "STRING"));
	mappage.put("MTPSAL", new BasicRecord("MTPSAL", "DECIMAL", getMyPaieElement().getClass().getField("mtpsal"), "STRING"));
	mappage.put("TXPAT", new BasicRecord("TXPAT", "DECIMAL", getMyPaieElement().getClass().getField("txpat"), "STRING"));
	mappage.put("MTPPAT", new BasicRecord("MTPPAT", "DECIMAL", getMyPaieElement().getClass().getField("mtppat"), "STRING"));
	mappage.put("DATTRT", new BasicRecord("DATTRT", "NUMERIC", getMyPaieElement().getClass().getField("dattrt"), "STRING"));
	return mappage;
}
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @return boolean
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public boolean creerPaieElement(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @return boolean
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public boolean modifierPaieElement(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @return boolean
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public boolean supprimerPaieElement(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : PaieElement.
 * @param aTransaction aTransaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public java.util.ArrayList<PaieElement> listerPaieElement(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" with UR");
}
/**
 * Retourne un PaieElement.
 * @param aTransaction aTransaction
 * @param cle cle
 * @return PaieElement
 * @throws Exception Exception
 */
public PaieElement chercherPaieElement(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (PaieElement)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+" with UR");
}


public java.util.ArrayList<PaieElement> listerdatesPayesforAgent(nc.mairie.technique.Transaction aTransaction, String matriculeAgent) throws Exception {
	//return executeSelectListe(aTransaction,"select distinct PERCOU from "+getTable()+" WHERE NOMATR="+matriculeAgent+" order by PERCOU DESC");
	return executeSelectListe(aTransaction,"with toto as (select percou from "+getTable()+" where nomatr = "+matriculeAgent+") select percou from toto group by percou order by percou desc with UR");
}

public java.util.ArrayList<PaieElement> listerPaieElement(nc.mairie.technique.Transaction aTransaction, String nomatr, String percou) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" WHERE NOMATR ="+nomatr+" AND PERCOU="+percou+" order by NORUBR with UR");
}
}
