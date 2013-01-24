package nc.mairie.visusal.metier;

import nc.mairie.technique.MairieMessages;
/**
 * Objet m�tier PaieMutuelle
 */
public class PaieMutuelle extends nc.mairie.technique.BasicMetier {
	public String cdmutu;
	public String txsal;
	public String txpat;
	public String limutu;
/**
 * Constructeur PaieMutuelle.
 */
public PaieMutuelle() {
	super();
}
/**
 * Getter de l'attribut cdmutu.
 */
public String getCdmutu() {
	return cdmutu;
}
/**
 * Setter de l'attribut cdmutu.
 */
public void setCdmutu(String newCdmutu) { 
	cdmutu = newCdmutu;
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
 * Getter de l'attribut limutu.
 */
public String getLimutu() {
	return limutu;
}
/**
 * Setter de l'attribut limutu.
 */
public void setLimutu(String newLimutu) { 
	limutu = newLimutu;
}
/**
 Methode � d�finir dans chaque objet M�tier pour instancier un Broker 
*/
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new PaieMutuelleBroker(this); 
}
/**
 Methode � d�finir dans chaque objet M�tier pour instancier un Broker 
*/
protected PaieMutuelleBroker getMyPaieMutuelleBroker() {
	return (PaieMutuelleBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet m�tier : PaieMutuelle.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerPaieMutuelle(nc.mairie.technique.Transaction aTransaction) throws Exception{
	PaieMutuelle unPaieMutuelle = new PaieMutuelle();
	return unPaieMutuelle.getMyPaieMutuelleBroker().listerPaieMutuelle(aTransaction);
}
/**
 * Retourne un PaieMutuelle.
 * @return PaieMutuelle
 */
public static PaieMutuelle chercherPaieMutuelle(nc.mairie.technique.Transaction aTransaction, String nomatr) throws Exception{
	PaieMutuelle unPaieMutuelle = new PaieMutuelle();
	return unPaieMutuelle.getMyPaieMutuelleBroker().chercherPaieMutuelle(aTransaction, nomatr);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */
public boolean creerPaieMutuelle(nc.mairie.technique.Transaction aTransaction )  throws Exception {
	//Creation du PaieMutuelle
	return getMyPaieMutuelleBroker().creerPaieMutuelle(aTransaction);
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierPaieMutuelle(nc.mairie.technique.Transaction aTransaction) throws Exception {
	//Modification du PaieMutuelle
	return getMyPaieMutuelleBroker().modifierPaieMutuelle(aTransaction);
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 */
public boolean supprimerPaieMutuelle(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'PaieMutuelle
	return getMyPaieMutuelleBroker().supprimerPaieMutuelle(aTransaction);
}
}
