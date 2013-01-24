package nc.mairie.visusal.metier;

import nc.mairie.technique.MairieMessages;
/**
 * Objet m�tier PaieRappel
 */
public class PaieRappel extends nc.mairie.technique.BasicMetier {
	public String percou;
	public String perrap;
	public String nomatr;
	public String norubr;
	public String txsal;
	public String nb;
	public String mtbase;
	public String mtpsal;
	public String txpat;
	public String mtppat;
/**
 * Constructeur PaieRappel.
 */
public PaieRappel() {
	super();
}
/**
 * Getter de l'attribut percou.
 */
public String getPercou() {
	return percou;
}
/**
 * Setter de l'attribut percou.
 */
public void setPercou(String newPercou) { 
	percou = newPercou;
}
/**
 * Getter de l'attribut perrap.
 */
public String getPerrap() {
	return perrap;
}
/**
 * Setter de l'attribut perrap.
 */
public void setPerrap(String newPerrap) { 
	perrap = newPerrap;
}
/**
 * Getter de l'attribut nomatr.
 */
public String getNomatr() {
	return nomatr;
}
/**
 * Setter de l'attribut nomatr.
 */
public void setNomatr(String newNomatr) { 
	nomatr = newNomatr;
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
 * Getter de l'attribut txsal.
 */
public String getTxsal() {
	return txsal;
}
/**
 * Setter de l'attribut txsal.
 */
public void setTxsal(String newTxsal) { 
	txsal = newTxsal;
}
/**
 * Getter de l'attribut nb.
 */
public String getNb() {
	return nb;
}
/**
 * Setter de l'attribut nb.
 */
public void setNb(String newNb) { 
	nb = newNb;
}
/**
 * Getter de l'attribut mtbase.
 */
public String getMtbase() {
	return mtbase;
}
/**
 * Setter de l'attribut mtbase.
 */
public void setMtbase(String newMtbase) { 
	mtbase = newMtbase;
}
/**
 * Getter de l'attribut mtpsal.
 */
public String getMtpsal() {
	return mtpsal;
}
/**
 * Setter de l'attribut mtpsal.
 */
public void setMtpsal(String newMtpsal) { 
	mtpsal = newMtpsal;
}
/**
 * Getter de l'attribut txpat.
 */
public String getTxpat() {
	return txpat;
}
/**
 * Setter de l'attribut txpat.
 */
public void setTxpat(String newTxpat) { 
	txpat = newTxpat;
}
/**
 * Getter de l'attribut mtppat.
 */
public String getMtppat() {
	return mtppat;
}
/**
 * Setter de l'attribut mtppat.
 */
public void setMtppat(String newMtppat) { 
	mtppat = newMtppat;
}
/**
 Methode � d�finir dans chaque objet M�tier pour instancier un Broker 
*/
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new PaieRappelBroker(this); 
}
/**
 Methode � d�finir dans chaque objet M�tier pour instancier un Broker 
*/
protected PaieRappelBroker getMyPaieRappelBroker() {
	return (PaieRappelBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet m�tier : PaieRappel.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerPaieRappel(nc.mairie.technique.Transaction aTransaction, String NoMatr, String PeriodeCou, String NoRubrique) throws Exception{
	PaieRappel unPaieRappel = new PaieRappel();
	return unPaieRappel.getMyPaieRappelBroker().listerPaieRappel(aTransaction, NoMatr, PeriodeCou, NoRubrique);
}
/**
 * Retourne un PaieRappel.
 * @return PaieRappel
 */
public static PaieRappel chercherPaieRappel(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PaieRappel unPaieRappel = new PaieRappel();
	return unPaieRappel.getMyPaieRappelBroker().chercherPaieRappel(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */
public boolean creerPaieRappel(nc.mairie.technique.Transaction aTransaction )  throws Exception {
	//Creation du PaieRappel
	return getMyPaieRappelBroker().creerPaieRappel(aTransaction);
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierPaieRappel(nc.mairie.technique.Transaction aTransaction) throws Exception {
	//Modification du PaieRappel
	return getMyPaieRappelBroker().modifierPaieRappel(aTransaction);
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 */
public boolean supprimerPaieRappel(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'PaieRappel
	return getMyPaieRappelBroker().supprimerPaieRappel(aTransaction);
}
}
