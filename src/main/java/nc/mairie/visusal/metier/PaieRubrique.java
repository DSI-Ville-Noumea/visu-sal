package nc.mairie.visusal.metier;


/**
 * Objet métier PaieRubrique
 */
public class PaieRubrique extends nc.mairie.technique.BasicMetier {
	public String norubr;
	public String lirubr;
	public String tyimpo;
	public String cdrcaf;
	public String rubrap;
	public String typrim;
	public String tyrubr;
	public String catpos;
	public String datina;
/**
 * Constructeur PaieRubrique.
 */
public PaieRubrique() {
	super();
}
/**
 * Getter de l'attribut norubr.
 * @return String
 */
public String getNorubr() {
	return norubr;
}
/**
 * Setter de l'attribut norubr.
 */
/**
 * @param newNorubr newNorubr
 */
public void setNorubr(String newNorubr) { 
	norubr = newNorubr;
}
/**
 * Getter de l'attribut lirubr.
 * @return String
 */
public String getLirubr() {
	return lirubr;
}
/**
 * Setter de l'attribut lirubr.
 */
/**
 * @param newLirubr newLirubr
 */
public void setLirubr(String newLirubr) { 
	lirubr = newLirubr;
}
/**
 * Getter de l'attribut tyimpo.
 * @return String
 */
public String getTyimpo() {
	return tyimpo;
}
/**
 * Setter de l'attribut tyimpo.
 */
/**
 * @param newTyimpo newTyimpo
 */
public void setTyimpo(String newTyimpo) { 
	tyimpo = newTyimpo;
}
/**
 * Getter de l'attribut cdrcaf.
 * @return String
 */
public String getCdrcaf() {
	return cdrcaf;
}
/**
 * Setter de l'attribut cdrcaf.
 */
/**
 * @param newCdrcaf newCdrcaf
 */
public void setCdrcaf(String newCdrcaf) { 
	cdrcaf = newCdrcaf;
}
/**
 * Getter de l'attribut rubrap.
 * @return String
 */
public String getRubrap() {
	return rubrap;
}
/**
 * Setter de l'attribut rubrap.
 */
/**
 * @param newRubrap newRubrap
 */
public void setRubrap(String newRubrap) { 
	rubrap = newRubrap;
}
/**
 * Getter de l'attribut typrim.
 * @return String
 */
public String getTyprim() {
	return typrim;
}
/**
 * Setter de l'attribut typrim.
 */
/**
 * @param newTyprim newTyprim
 */
public void setTyprim(String newTyprim) { 
	typrim = newTyprim;
}
/**
 * Getter de l'attribut tyrubr.
 * @return String
 */
public String getTyrubr() {
	return tyrubr;
}
/**
 * Setter de l'attribut tyrubr.
 */
/**
 * @param newTyrubr newTyrubr
 */
public void setTyrubr(String newTyrubr) { 
	tyrubr = newTyrubr;
}
/**
 * Getter de l'attribut catpos.
 * @return String
 */
public String getCatpos() {
	return catpos;
}
/**
 * Setter de l'attribut catpos.
 */
/**
 * @param newCatpos newCatpos
 */
public void setCatpos(String newCatpos) { 
	catpos = newCatpos;
}
/**
 * Getter de l'attribut datina.
 * @return String
 */
public String getDatina() {
	return datina;
}
/**
 * Setter de l'attribut datina.
 */
/**
 * @param newDatina newDatina
 */
public void setDatina(String newDatina) { 
	datina = newDatina;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new PaieRubriqueBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
 * @return PaieRubriqueBroker
*/
protected PaieRubriqueBroker getMyPaieRubriqueBroker() {
	return (PaieRubriqueBroker)getMyBasicBroker();
}
/**
* Renvoie une chaîne correspondant à la valeur de cet objet.
* @return une représentation sous forme de chaîne du destinataire
*/
public String toString() {
	// Insérez ici le code pour finaliser le destinataire
	// Cette implémentation transmet le message au super. Vous pouvez remplacer ou compléter le message.
	return super.toString();
}
/**
 * Retourne un ArrayList d'objet métier : PaieRubrique.
 * @return java.util.ArrayList
 * @param aTransaction Transaction
 * @throws Exception Exception
*/
public static java.util.ArrayList<PaieRubrique> listerPaieRubrique(nc.mairie.technique.Transaction aTransaction) throws Exception{
	PaieRubrique unPaieRubrique = new PaieRubrique();
	return unPaieRubrique.getMyPaieRubriqueBroker().listerPaieRubrique(aTransaction);
}
/**
 * Retourne un PaieRubrique.
 * @param aTransaction aTransaction
 * @param code code
 * @return PaieRubrique
 * @throws Exception Exception
 */
public static PaieRubrique chercherPaieRubrique(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PaieRubrique unPaieRubrique = new PaieRubrique();
	return unPaieRubrique.getMyPaieRubriqueBroker().chercherPaieRubrique(aTransaction, code);
}

public static PaieRubrique chercherPaieRubriqueRappelCorrespondante(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PaieRubrique unPaieRubrique = new PaieRubrique();
	return unPaieRubrique.getMyPaieRubriqueBroker().chercherPaieRubriqueRappelCorrespondante(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @return boolean
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public boolean creerPaieRubrique(nc.mairie.technique.Transaction aTransaction )  throws Exception {
	//Creation du PaieRubrique
	return getMyPaieRubriqueBroker().creerPaieRubrique(aTransaction);
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 * @return boolean
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public boolean modifierPaieRubrique(nc.mairie.technique.Transaction aTransaction) throws Exception {
	//Modification du PaieRubrique
	return getMyPaieRubriqueBroker().modifierPaieRubrique(aTransaction);
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 * @return boolean
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public boolean supprimerPaieRubrique(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'PaieRubrique
	return getMyPaieRubriqueBroker().supprimerPaieRubrique(aTransaction);
}
}
