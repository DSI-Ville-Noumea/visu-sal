package nc.mairie.visusal.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet m�tier AgentMairie
 */
public class AgentMairieBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur AgentMairieBroker.
 */
public AgentMairieBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return src/nc.mairie.metier.agent.AgentMairieMetier
 */
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new AgentMairie() ;
}
/**
 * @return src/nc.mairie.metier.agent.AgentMairieMetier
 */
protected AgentMairie getMyAgentMairie() {
	return (AgentMairie)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
protected java.lang.String definirNomTable() {
	return "MAIRIE.SPPERS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("IDINDI", new BasicRecord("IDINDI", "DECIMAL", getMyAgentMairie().getClass().getField("idindi"), "STRING"));
	mappage.put("NOMATR", new BasicRecord("NOMATR", "NUMERIC", getMyAgentMairie().getClass().getField("nomatr"), "STRING"));
	mappage.put("NOM", new BasicRecord("NOM", "CHAR", getMyAgentMairie().getClass().getField("nom"), "STRING"));
	mappage.put("PRENOM", new BasicRecord("PRENOM", "CHAR", getMyAgentMairie().getClass().getField("prenom"), "STRING"));
	mappage.put("NOMJFI", new BasicRecord("NOMJFI", "CHAR", getMyAgentMairie().getClass().getField("nomjfi"), "STRING"));
	mappage.put("DATNAI", new BasicRecord("DATNAI", "NUMERIC", getMyAgentMairie().getClass().getField("datnai"), "STRING"));
	mappage.put("LIEUNA", new BasicRecord("LIEUNA", "CHAR", getMyAgentMairie().getClass().getField("lieuna"), "STRING"));
	mappage.put("CDDESI", new BasicRecord("CDDESI", "CHAR", getMyAgentMairie().getClass().getField("cddesi"), "STRING"));
	mappage.put("SEXE", new BasicRecord("SEXE", "CHAR", getMyAgentMairie().getClass().getField("sexe"), "STRING"));
	mappage.put("NATION", new BasicRecord("NATION", "CHAR", getMyAgentMairie().getClass().getField("nation"), "STRING"));
	mappage.put("CDFAMI", new BasicRecord("CDFAMI", "CHAR", getMyAgentMairie().getClass().getField("cdfami"), "STRING"));
	mappage.put("NINSEE", new BasicRecord("NINSEE", "DECIMAL", getMyAgentMairie().getClass().getField("ninsee"), "STRING"));
	mappage.put("DATTIT", new BasicRecord("DATTIT", "NUMERIC", getMyAgentMairie().getClass().getField("dattit"), "STRING"));
	mappage.put("DATDEC", new BasicRecord("DATDEC", "NUMERIC", getMyAgentMairie().getClass().getField("datdec"), "STRING"));
	mappage.put("CDREGL", new BasicRecord("CDREGL", "NUMERIC", getMyAgentMairie().getClass().getField("cdregl"), "STRING"));
	mappage.put("IDADRS", new BasicRecord("IDADRS", "NUMERIC", getMyAgentMairie().getClass().getField("idadrs"), "STRING"));
	mappage.put("IDCPTE", new BasicRecord("IDCPTE", "NUMERIC", getMyAgentMairie().getClass().getField("idcpte"), "STRING"));
	mappage.put("TELDOM", new BasicRecord("TELDOM", "NUMERIC", getMyAgentMairie().getClass().getField("teldom"), "STRING"));
	mappage.put("NOPORT", new BasicRecord("NOPORT", "DECIMAL", getMyAgentMairie().getClass().getField("noport"), "STRING"));
	mappage.put("BISTER", new BasicRecord("BISTER", "CHAR", getMyAgentMairie().getClass().getField("bister"), "STRING"));
	mappage.put("LIDOPU", new BasicRecord("LIDOPU", "CHAR", getMyAgentMairie().getClass().getField("lidopu"), "STRING"));
	mappage.put("LIRUE", new BasicRecord("LIRUE", "CHAR", getMyAgentMairie().getClass().getField("lirue"), "STRING"));
	mappage.put("BP", new BasicRecord("BP", "CHAR", getMyAgentMairie().getClass().getField("bp"), "STRING"));
	mappage.put("LICARE", new BasicRecord("LICARE", "CHAR", getMyAgentMairie().getClass().getField("licare"), "STRING"));
	mappage.put("CDVILL", new BasicRecord("CDVILL", "DECIMAL", getMyAgentMairie().getClass().getField("cdvill"), "STRING"));
	mappage.put("LIVILL", new BasicRecord("LIVILL", "CHAR", getMyAgentMairie().getClass().getField("livill"), "STRING"));
	mappage.put("CDBANQ", new BasicRecord("CDBANQ", "DECIMAL", getMyAgentMairie().getClass().getField("cdbanq"), "STRING"));
	mappage.put("CDGUIC", new BasicRecord("CDGUIC", "DECIMAL", getMyAgentMairie().getClass().getField("cdguic"), "STRING"));
	mappage.put("NOCPTE", new BasicRecord("NOCPTE", "CHAR", getMyAgentMairie().getClass().getField("nocpte"), "STRING"));
	mappage.put("CLERIB", new BasicRecord("CLERIB", "NUMERIC", getMyAgentMairie().getClass().getField("clerib"), "STRING"));
	mappage.put("CDELEC", new BasicRecord("CDELEC", "CHAR", getMyAgentMairie().getClass().getField("cdelec"), "STRING"));
	mappage.put("DATEMB", new BasicRecord("DATEMB", "NUMERIC", getMyAgentMairie().getClass().getField("datemb"), "STRING"));
	mappage.put("CDETUD", new BasicRecord("CDETUD", "NUMERIC", getMyAgentMairie().getClass().getField("cdetud"), "STRING"));
	return mappage;
}
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerAgentMairie(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierAgentMairie(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerAgentMairie(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet m�tier : AgentMairie.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerAgentMairie(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" with UR");
}

public java.util.ArrayList listerAgentMairieAvecNomCommencant(nc.mairie.technique.Transaction aTransaction, String debNom) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where upper(nom) like '"+debNom.toUpperCase()+"%' order by nom with UR");
}

public java.util.ArrayList listerAgentMairieAvecPrenomCommencant(nc.mairie.technique.Transaction aTransaction, String debPrenom) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where upper(prenom) like '"+debPrenom.toUpperCase()+"%' order by nom with UR");
}
/**
 * Retourne un AgentMairie.
 * @return AgentMairie
 */
public AgentMairie chercherAgentMairie(nc.mairie.technique.Transaction aTransaction, String nomatr) throws Exception {
	return (AgentMairie)executeSelect(aTransaction,"select * from "+getTable()+" where nomatr = "+nomatr+" with UR");
}
}
