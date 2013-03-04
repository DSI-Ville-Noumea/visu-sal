package nc.mairie.visusal.test;

import javax.servlet.http.HttpServletRequest;

import nc.mairie.technique.*;
import nc.mairie.commun.process.*;
/**
 * Process ZZZTESTEUR
 * Date de création : (23/01/03 15:13:31)
 * @author : Générateur de process
*/
public class ZZZTESTEUR extends nc.mairie.robot.Testeur {
/**
 * Constructeur du process ZZZTESTEUR.
 * Date de création : (23/01/03 15:13:31)
 * @author : Générateur de process
 */
public ZZZTESTEUR() {
	super();
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_PROCESS_SELECT
 * Date de création : (27/01/03 15:56:56)
 * @author : Générateur de process
 */
public java.lang.String [] definirListeProcess() {
	String [] res = {	
			//OeAffichSalaire.class.getName()
					};
	return res;
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (27/01/03 15:56:56)
 * @author : Générateur de process
 */
public String getJSP() {
	return "ZZZTESTEUR.jsp";
}

/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (23/01/03 15:13:31)
 * @author : Générateur de process
 */
public void initialiseTestAppel(javax.servlet.http.HttpServletRequest request) throws Exception{
	java.sql.Connection conn = null;
	//String requeteSQL = "SELECT T_Bon_ID as \"idBon\" ,i.code as \"codeImp\" ,ta.code as \"codeAide\" ,eap.label as \"labelAP\" ,f.ENSCOM as \"nomFourn\" ,d.numero as \"numeroDossier\" ,CASE WHEN p.T_Personne_ID IS NOT NULL THEN p.nom CONCAT ' ' CONCAT p.prenom ELSE NULL END as \"nomCDF\" FROM MAIRIE.SIETBS as f , GASEL.T_Bon AS bo INNER JOIN GASEL.T_Aide as a ON (bo.T_Aide_ID = a.T_Aide_ID) INNER JOIN GASEL.T_TypeAide as ta ON(a.T_TypeAide_ID = ta.T_TypeAide_ID) INNER JOIN GASEL.T_Imputation as i ON(ta.T_Imputation_ID = i.T_Enum_ID) INNER JOIN GASEL.T_ArticlePrestation as ap ON(ta.T_ArticlePrestation_ID = ap.T_Enum_ID) INNER JOIN GASEL.T_Enum as eap ON(ap.T_Enum_ID = eap.T_Enum_ID) INNER JOIN GASEL.T_Dossier as d ON(a.T_Dossier_ID = d.T_Dossier_ID) LEFT OUTER JOIN GASEL.V_ChefDeFamille as p ON (d.T_Dossier_ID = p.T_Dossier_ID) LEFT OUTER JOIN GASEL.T_ArticleFournisseur as af ON (af.T_ArticlePrestation_ID = ap.T_Enum_ID) where (af.T_Fournisseur_ID = f.IDETBS) and T_Bon_ID in(175)";
	//String requeteSQL = "SELECT * from mairie.sietbs";
	//String requeteSQL = "SELECT * from gasel.T_ArticleFournisseur as af LEFT OUTER JOIN mairie.SIETBS as f ON (af.T_Fournisseur_ID = f.IDETBS) where t_articlefournisseur_id = 6";
/*	String requeteSQL = "SELECT  * "+
	"FROM gasel.T_Bon AS bo "+
	"INNER JOIN gasel.T_Aide as a ON (bo.T_Aide_ID = a.T_Aide_ID) "+
	"INNER JOIN gasel.T_TypeAide as ta ON(a.T_TypeAide_ID = ta.T_TypeAide_ID) "+
	"INNER JOIN gasel.T_Imputation as i ON(ta.T_Imputation_ID = i.T_Enum_ID) "+
	"INNER JOIN gasel.T_ArticlePrestation as ap ON(ta.T_ArticlePrestation_ID = ap.T_Enum_ID)"+ 
	"INNER JOIN gasel.T_Enum as eap ON(ap.T_Enum_ID = eap.T_Enum_ID) "+
	"INNER JOIN gasel.T_Dossier as d ON(a.T_Dossier_ID = d.T_Dossier_ID) "+
	"LEFT OUTER JOIN gasel.V_ChefDeFamille as p ON (d.T_Dossier_ID = p.T_Dossier_ID) "+
	"LEFT OUTER JOIN gasel.T_ArticleFournisseur as af ON (af.T_ArticlePrestation_ID = ap.T_Enum_ID) "+ 
	"LEFT OUTER JOIN mairie.SIETBS as f ON (af.T_Fournisseur_ID = f.IDETBS) "+ 
	"WHERE T_Bon_ID in(175)";
*/
	String requeteSQL = "SELECT  * "+
	"FROM gasel.T_Bon AS bo "+
	"INNER JOIN gasel.T_Aide as a ON (bo.T_Aide_ID = a.T_Aide_ID) "+
	"INNER JOIN gasel.T_TypeAide as ta ON(a.T_TypeAide_ID = ta.T_TypeAide_ID) "+
	"INNER JOIN gasel.T_Imputation as i ON(ta.T_Imputation_ID = i.T_Enum_ID) "+
	"INNER JOIN gasel.T_ArticlePrestation as ap ON(ta.T_ArticlePrestation_ID = ap.T_Enum_ID)"+ 
	"INNER JOIN gasel.T_Enum as eap ON(ap.T_Enum_ID = eap.T_Enum_ID) "+
	"INNER JOIN gasel.T_Dossier as d ON(a.T_Dossier_ID = d.T_Dossier_ID) "+
/*	"LEFT OUTER  JOIN gasel.V_ChefLuc as p ON (d.T_Dossier_ID = p.T_Dossier_ID) "+
*/
	"INNER JOIN    GASEL.T_PERSONNE AS P ON (P.T_DOSSIER_ID = d.T_dossier_ID)"+
		"INNER JOIN    GASEL.T_ADMINISTRE AS AD ON (AD.T_PERSONNE_ID = P.T_PERSONNE_ID)"+
	 "INNER JOIN    GASEL.T_LIE00002 AS L ON (AD.T_LIENACHARGE_T_ENUM_ID = L.T_ENUM_ID)"+
	 "INNER JOIN    GASEL.T_ENUM AS E ON (L.T_ENUM_ID = E.T_ENUM_ID)"+
	
	"LEFT OUTER JOIN gasel.T_ArticleFournisseur as af ON (af.T_ArticlePrestation_ID = ap.T_Enum_ID) "+ 
	"LEFT OUTER JOIN mairie.SIETBS as f ON (11555 = f.IDETBS) "+
 
	"WHERE T_Bon_ID in(175)";
	try{	
	

	conn = getTransaction().getConnection();
	java.sql.Statement stmt = conn.createStatement();
	java.sql.ResultSet rs = stmt.executeQuery(requeteSQL);

//	Fermeture du resultSet
	rs.close();
	stmt.close();
}catch (Exception e) {
 	if (conn!=null && ! conn.isClosed()) {
 		getTransaction().rollbackTransaction();
	 	conn.close();
 	}
	throw new Exception("Exception dans 'executeSelectListe' du basicBroker avec la requete "+requeteSQL+" : " + e);
	}
	
	addZone(getNOM_ST_RESULTAT(), "STATUT_TESTAPPEL" );
}
@Override
public void initialiseZones(HttpServletRequest request) throws Exception {
	// TODO Auto-generated method stub
	
}
}
