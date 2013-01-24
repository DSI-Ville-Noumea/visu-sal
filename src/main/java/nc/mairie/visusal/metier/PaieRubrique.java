package nc.mairie.visusal.metier;

import nc.mairie.technique.MairieMessages;
/**
 * Objet m�tier PaieRubrique
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
 */
public String getNorubr() {
	return norubr;
}
/**
 * Setter de l'attribut norubr.
 */
public void setNorubr(String newNorubr) { 
	norubr = newNorubr;
}
/**
 * Getter de l'attribut lirubr.
 */
public String getLirubr() {
	return lirubr;
}
/**
 * Setter de l'attribut lirubr.
 */
public void setLirubr(String newLirubr) { 
	lirubr = newLirubr;
}
/**
 * Getter de l'attribut tyimpo.
 */
public String getTyimpo() {
	return tyimpo;
}
/**
 * Setter de l'attribut tyimpo.
 */
public void setTyimpo(String newTyimpo) { 
	tyimpo = newTyimpo;
}
/**
 * Getter de l'attribut cdrcaf.
 */
public String getCdrcaf() {
	return cdrcaf;
}
/**
 * Setter de l'attribut cdrcaf.
 */
public void setCdrcaf(String newCdrcaf) { 
	cdrcaf = newCdrcaf;
}
/**
 * Getter de l'attribut rubrap.
 */
public String getRubrap() {
	return rubrap;
}
/**
 * Setter de l'attribut rubrap.
 */
public void setRubrap(String newRubrap) { 
	rubrap = newRubrap;
}
/**
 * Getter de l'attribut typrim.
 */
public String getTyprim() {
	return typrim;
}
/**
 * Setter de l'attribut typrim.
 */
public void setTyprim(String newTyprim) { 
	typrim = newTyprim;
}
/**
 * Getter de l'attribut tyrubr.
 */
public String getTyrubr() {
	return tyrubr;
}
/**
 * Setter de l'attribut tyrubr.
 */
public void setTyrubr(String newTyrubr) { 
	tyrubr = newTyrubr;
}
/**
 * Getter de l'attribut catpos.
 */
public String getCatpos() {
	return catpos;
}
/**
 * Setter de l'attribut catpos.
 */
public void setCatpos(String newCatpos) { 
	catpos = newCatpos;
}
/**
 * Getter de l'attribut datina.
 */
public String getDatina() {
	return datina;
}
/**
 * Setter de l'attribut datina.
 */
public void setDatina(String newDatina) { 
	datina = newDatina;
}
/**
 Methode � d�finir dans chaque objet M�tier pour instancier un Broker 
*/
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new PaieRubriqueBroker(this); 
}
/**
 Methode � d�finir dans chaque objet M�tier pour instancier un Broker 
*/
protected PaieRubriqueBroker getMyPaieRubriqueBroker() {
	return (PaieRubriqueBroker)getMyBasicBroker();
}
/**
* Renvoie une cha�ne correspondant � la valeur de cet objet.
* @return une repr�sentation sous forme de cha�ne du destinataire
*/
public String toString() {
	// Ins�rez ici le code pour finaliser le destinataire
	// Cette impl�mentation transmet le message au super. Vous pouvez remplacer ou compl�ter le message.
	return super.toString();
}
/**
 * Retourne un ArrayList d'objet m�tier : PaieRubrique.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerPaieRubrique(nc.mairie.technique.Transaction aTransaction) throws Exception{
	PaieRubrique unPaieRubrique = new PaieRubrique();
	return unPaieRubrique.getMyPaieRubriqueBroker().listerPaieRubrique(aTransaction);
}
/**
 * Retourne un PaieRubrique.
 * @return PaieRubrique
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
 */
public boolean creerPaieRubrique(nc.mairie.technique.Transaction aTransaction )  throws Exception {
	//Creation du PaieRubrique
	return getMyPaieRubriqueBroker().creerPaieRubrique(aTransaction);
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierPaieRubrique(nc.mairie.technique.Transaction aTransaction) throws Exception {
	//Modification du PaieRubrique
	return getMyPaieRubriqueBroker().modifierPaieRubrique(aTransaction);
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 */
public boolean supprimerPaieRubrique(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'PaieRubrique
	return getMyPaieRubriqueBroker().supprimerPaieRubrique(aTransaction);
}
}
