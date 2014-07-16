package nc.mairie.visusal.metier;


/**
 * Objet métier PaieRappel
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
 * @return String
 */
public String getPercou() {
	return percou;
}
/**
 * Setter de l'attribut percou.
 */
/**
 * @param newPercou newPercou
 */
public void setPercou(String newPercou) { 
	percou = newPercou;
}
/**
 * Getter de l'attribut perrap.
 * @return String
 */
public String getPerrap() {
	return perrap;
}
/**
 * Setter de l'attribut perrap.
 */
/**
 * @param newPerrap newPerrap
 */
public void setPerrap(String newPerrap) { 
	perrap = newPerrap;
}
/**
 * Getter de l'attribut nomatr.
 * @return String
 */
public String getNomatr() {
	return nomatr;
}
/**
 * Setter de l'attribut nomatr.
 */
/**
 * @param newNomatr newNomatr
 */
public void setNomatr(String newNomatr) { 
	nomatr = newNomatr;
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
 * Getter de l'attribut txsal.
 * @return String
 */
public String getTxsal() {
	return txsal;
}
/**
 * Setter de l'attribut txsal.
 */
/**
 * @param newTxsal newTxsal
 */
public void setTxsal(String newTxsal) { 
	txsal = newTxsal;
}
/**
 * Getter de l'attribut nb.
 * @return String
 */
public String getNb() {
	return nb;
}
/**
 * Setter de l'attribut nb.
 */
/**
 * @param newNb newNb
 */
public void setNb(String newNb) { 
	nb = newNb;
}
/**
 * Getter de l'attribut mtbase.
 * @return String
 */
public String getMtbase() {
	return mtbase;
}
/**
 * Setter de l'attribut mtbase.
 */
/**
 * @param newMtbase newMtbase
 */
public void setMtbase(String newMtbase) { 
	mtbase = newMtbase;
}
/**
 * Getter de l'attribut mtpsal.
 * @return String
 */
public String getMtpsal() {
	return mtpsal;
}
/**
 * Setter de l'attribut mtpsal.
 */
/**
 * @param newMtpsal newMtpsal
 */
public void setMtpsal(String newMtpsal) { 
	mtpsal = newMtpsal;
}
/**
 * Getter de l'attribut txpat.
 * @return String
 */
public String getTxpat() {
	return txpat;
}
/**
 * Setter de l'attribut txpat.
 */
/**
 * @param newTxpat newTxpat
 */
public void setTxpat(String newTxpat) { 
	txpat = newTxpat;
}
/**
 * Getter de l'attribut mtppat.
 * @return String
 */
public String getMtppat() {
	return mtppat;
}
/**
 * Setter de l'attribut mtppat.
 */
/**
 * @param newMtppat newMtppat
 */
public void setMtppat(String newMtppat) { 
	mtppat = newMtppat;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new PaieRappelBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
 * @return PaieRappelBroker
 */
protected PaieRappelBroker getMyPaieRappelBroker() {
	return (PaieRappelBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet métier : PaieRappel.
 * @return java.util.ArrayList
 * @param aTransaction Transaction
 * @param NoMatr NoMatr
 * @param PeriodeCou PeriodeCou
 * @param NoRubrique NoRubrique
 * @throws Exception Exception
*/
public static java.util.ArrayList<PaieRappel> listerPaieRappel(nc.mairie.technique.Transaction aTransaction, String NoMatr, String PeriodeCou, String NoRubrique) throws Exception{
	PaieRappel unPaieRappel = new PaieRappel();
	return unPaieRappel.getMyPaieRappelBroker().listerPaieRappel(aTransaction, NoMatr, PeriodeCou, NoRubrique);
}
/**
 * Retourne un PaieRappel.
 * @param aTransaction aTransaction
 * @param code code
 * @return PaieRappel
 * @throws Exception Exception
 */
public static PaieRappel chercherPaieRappel(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PaieRappel unPaieRappel = new PaieRappel();
	return unPaieRappel.getMyPaieRappelBroker().chercherPaieRappel(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @return boolean
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public boolean creerPaieRappel(nc.mairie.technique.Transaction aTransaction )  throws Exception {
	//Creation du PaieRappel
	return getMyPaieRappelBroker().creerPaieRappel(aTransaction);
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 * @return boolean
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public boolean modifierPaieRappel(nc.mairie.technique.Transaction aTransaction) throws Exception {
	//Modification du PaieRappel
	return getMyPaieRappelBroker().modifierPaieRappel(aTransaction);
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 * @return boolean
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public boolean supprimerPaieRappel(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'PaieRappel
	return getMyPaieRappelBroker().supprimerPaieRappel(aTransaction);
}
}
