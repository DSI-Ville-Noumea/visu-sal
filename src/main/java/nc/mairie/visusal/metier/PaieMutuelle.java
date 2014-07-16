package nc.mairie.visusal.metier;


/**
 * Objet métier PaieMutuelle
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
 * @return String
 */
public String getCdmutu() {
	return cdmutu;
}
/**
 * Setter de l'attribut cdmutu.
 */
/**
 * @param newCdmutu newCdmutu
 */
public void setCdmutu(String newCdmutu) { 
	cdmutu = newCdmutu;
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
 * Getter de l'attribut limutu.
 * @return String
 */
public String getLimutu() {
	return limutu;
}
/**
 * Setter de l'attribut limutu.
 */
/**
 * @param newLimutu newLimutu
 */
public void setLimutu(String newLimutu) { 
	limutu = newLimutu;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new PaieMutuelleBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
 * @return PaieMutuelleBroker
*/
protected PaieMutuelleBroker getMyPaieMutuelleBroker() {
	return (PaieMutuelleBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet métier : PaieMutuelle.
 * @return java.util.ArrayList
 * @param aTransaction Transaction
 * @throws Exception Exception
*/
public static java.util.ArrayList<PaieMutuelle> listerPaieMutuelle(nc.mairie.technique.Transaction aTransaction) throws Exception{
	PaieMutuelle unPaieMutuelle = new PaieMutuelle();
	return unPaieMutuelle.getMyPaieMutuelleBroker().listerPaieMutuelle(aTransaction);
}
/**
 * Retourne un PaieMutuelle.
 * @param aTransaction aTransaction 
 * @param nomatr nomatr 
 * @return PaieMutuelle
 * @throws Exception Exception 
 */
public static PaieMutuelle chercherPaieMutuelle(nc.mairie.technique.Transaction aTransaction, String nomatr) throws Exception{
	PaieMutuelle unPaieMutuelle = new PaieMutuelle();
	return unPaieMutuelle.getMyPaieMutuelleBroker().chercherPaieMutuelle(aTransaction, nomatr);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @return boolean
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public boolean creerPaieMutuelle(nc.mairie.technique.Transaction aTransaction )  throws Exception {
	//Creation du PaieMutuelle
	return getMyPaieMutuelleBroker().creerPaieMutuelle(aTransaction);
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 * @return boolean
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public boolean modifierPaieMutuelle(nc.mairie.technique.Transaction aTransaction) throws Exception {
	//Modification du PaieMutuelle
	return getMyPaieMutuelleBroker().modifierPaieMutuelle(aTransaction);
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 * @return boolean
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public boolean supprimerPaieMutuelle(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'PaieMutuelle
	return getMyPaieMutuelleBroker().supprimerPaieMutuelle(aTransaction);
}
}
