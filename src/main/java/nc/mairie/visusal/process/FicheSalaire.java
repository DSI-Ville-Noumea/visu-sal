package nc.mairie.visusal.process;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

import nc.mairie.technique.FormateListe;
import nc.mairie.technique.MairieMessages;
import nc.mairie.technique.Services;
import nc.mairie.technique.StarjetGenerationVFS;
import nc.mairie.technique.VariableActivite;
import nc.mairie.technique.VariableGlobale;
import nc.mairie.visusal.metier.AgentMairie;
import nc.mairie.visusal.metier.BulletinElement;
import nc.mairie.visusal.metier.PaieElement;
import nc.mairie.visusal.metier.PaieEntete;
import nc.mairie.visusal.metier.PaieMutuelle;
import nc.mairie.visusal.metier.PaieRappel;
import nc.mairie.visusal.metier.PaieRubrique;
import nc.mairie.visusal.metier.Utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs.FileObject;

/**
 * Process FicheSalaire
 * Date de création : (23/02/09 15:35:45)
 * @author : Générateur de process
 */
public class FicheSalaire extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static Logger logger = Logger.getLogger(FicheSalaire.class.getName());
	private static final long serialVersionUID = -5571225984096176183L;
	public static final int STATUT_RECHERCHER_AGENT = 1;
	public static final int STATUT_SALAIRE_RAPPEL = 2;
	public static final String MOIS_COURANT ="MOIS_COURANT";
	private java.lang.String[] LB_ELEMENTS_PAIE;
	private java.lang.String[] LB_MOIS_ANCIENNETE;
	private AgentMairie agentMCourant=null;
	private PaieElement paieElementMoisCourant=null;
	private PaieEntete paieEnteteMoisCourant=null;
	private java.util.ArrayList<PaieElement>  listeMoisAnciennete;
	private java.util.ArrayList<PaieElement>  listeElementsPaye;
	public static double dValeurEuroXPF=119.331742;
	private String script;
	private java.util.ArrayList<BulletinElement>  listeBulletinSalarie=null;
	private java.util.ArrayList<BulletinElement>  listeBulletinPatronal=null;
	private String totalRetenir="";
	private String totalPayer="";
	private String netImposMois="";
	private String nonImposMois="";
	private String netPayer="";
	private String netImposCumul="";
	private String nonImposCumul="";
	private String sTBA="";
	private String sDemiJoursRCN="0";
	private String sDemiJoursRCN_1="0";
	private String elementPaieFormatee;

	/**
	 * @return elementPaieFormatee
	 */
	public String getElementPaieFormatee() {
		return elementPaieFormatee;
	}

	/**
	 * @param elementPaieFormatee elementPaieFormatee
	 */
	public void setElementPaieFormatee(String elementPaieFormatee) {
		this.elementPaieFormatee = elementPaieFormatee;
	}
	
	/**
	 * Initialisation des zones à afficher dans la JSP
	 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
	 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
	 * Utilisation de la méthode addZone(getNOMxxx, String);
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 */
	public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
		
		//logger.info("PASS initZone FicheSalaire");
		initialiseAgent(request);
		if (null!=agentMCourant){
			initialiseMenuGauche(request);
			initialiseSalaire(request);
		}
		
		if (getPaieElementMoisCourant() != null) {
			setElementPaieFormatee(generateELEMENTS_PAIE());
		}
	}
	
	
	public void initialiseAgent(javax.servlet.http.HttpServletRequest request) throws Exception{
		
		//logger.info("PASS initZone initialiseAgent");
		if(null!=VariableActivite.recuperer(this, VariableActivite.ACTIVITE_AGENT_MAIRIE)){
			//logger.info("PASS recherche initialiseAgent");	
			agentMCourant=(AgentMairie)AgentMairie.chercherAgentMairie(getTransaction(),(String)VariableActivite.recuperer(this,VariableActivite.ACTIVITE_AGENT_MAIRIE));
			VariableActivite.enlever(this, VariableActivite.ACTIVITE_AGENT_MAIRIE);
			setPaieElementMoisCourant(null);
			setListeMoisAnciennete(null);
			setListeElementsPaye(null);
		}else if (null!=VariableGlobale.recuperer(request, VariableGlobale.GLOBAL_AGENT_MAIRIE)){
			//logger.info("PASS recupere initialiseAgent");
			agentMCourant=(AgentMairie)VariableGlobale.recuperer(request,VariableGlobale.GLOBAL_AGENT_MAIRIE);
		}
		
		if (null!=agentMCourant){
			VariableGlobale.ajouter(request,VariableGlobale.GLOBAL_AGENT_MAIRIE,agentMCourant);
			addZone(getNOM_ST_AGENT_NOM(),agentMCourant.getNom().trim());
			addZone(getNOM_ST_AGENT_PRENOM(),agentMCourant.getPrenom().trim());
			addZone(getNOM_ST_AGENT_MATRICULE(),agentMCourant.getNomatr());
		}
		
	}
	
	/**
	 * Initialise le Menu de gauche où les mois travaillés s'affichent 
	 * @param request request
	 * @throws Exception Exception
	 */
	public void initialiseMenuGauche(javax.servlet.http.HttpServletRequest request) throws Exception{
		
		//logger.info("PASS initZone initialiseMenuGauche allmois");
		java.util.ArrayList<PaieElement> a;
			if(null==getListeMoisAnciennete()){
				//long timestart=System.currentTimeMillis();
					a=PaieElement.listerdatesPaiesforAgent(getTransaction(),agentMCourant.getNomatr());
				//long timestop=System.currentTimeMillis();
				//timestop=timestop-timestart;
				//logger.info("PASS initZone initialiseMenuGauche RECHERCHE ANCIENNETE EFFECTUEE en "+timestop+"ms !!");
				setListeMoisAnciennete(a);
			}else{
				a=getListeMoisAnciennete();
				//logger.info("PASS recupere l'init MenuGauche");
			}

		
		//Si au moins un Mois Anciennete
		if (a.size() !=0 ) {
			int tailles [] = {15};
			FormateListe aFormat = new FormateListe(tailles);
			for (java.util.ListIterator<PaieElement> list = a.listIterator(); list.hasNext(); ) {
				String mois = Utils.TraiterDateFr((list.next()).getPercou());
				String ligne [] = {mois};
				aFormat.ajouteLigne(ligne);
			}
			setLB_MOIS_ANCIENNETE(aFormat.getListeFormatee());
		} else {
			setLB_MOIS_ANCIENNETE(null);
		}
	}
	
	
	public void initialiseSalaire(javax.servlet.http.HttpServletRequest request) throws Exception{
		
		//logger.info("PASS initZone initialiseSalaire");
		
		//MODIF OFONTENEAU 20090410 SELCTION DU MOIS D'ANCIENNETE
		if (null==getPaieElementMoisCourant()&&null!=getListeMoisAnciennete()){
			//logger.info("ON SELECTIONNE LE DERNIER BULLETIN DANS LA LISTE");
			addZone(getNOM_LB_MOIS_ANCIENNETE_SELECT(),"0");
			performPB_MOIS_ANCIENNETE(request);
		}
		
		if(null!=getPaieElementMoisCourant()){
			if (null==VariableGlobale.recuperer(request, MOIS_COURANT)){
				VariableGlobale.ajouter(request,MOIS_COURANT,getPaieElementMoisCourant());
			}
			
			paieEnteteMoisCourant=PaieEntete.chercherPaieEntete(getTransaction(), agentMCourant.getNomatr(), paieElementMoisCourant.getPercou());
			
			java.util.ArrayList<PaieElement> a =PaieElement.listerPaieElement(getTransaction(), agentMCourant.getNomatr(), paieElementMoisCourant.getPercou());
			getTransaction().rollbackTransaction();
			setListeElementsPaye(a);
		}
		
		if (null!=paieEnteteMoisCourant && null!=paieEnteteMoisCourant.getLicate()){
			addZone(getNOM_ST_CATEGORIE_ADMINISTRATIVE(),paieEnteteMoisCourant.getLicate().trim());
			String sGrade=paieEnteteMoisCourant.getLigrad().trim();
			if (sGrade.length()>35){
				addZone(getNOM_ST_GRADE(),sGrade.substring(0,35));
				addZone(getNOM_ST_GRADE2(),sGrade.substring(35,sGrade.length()));
			}else{
				addZone(getNOM_ST_GRADE(),sGrade);
			}
			
			//traitement forfaite ou indexé
			if (paieEnteteMoisCourant.getForf()!=null&&paieEnteteMoisCourant.getForf().equals("0")==false&&paieEnteteMoisCourant.getForf().equals("0,00")==false)
				addZone(getNOM_ST_INDICES(),Utils.TraiterNombreIt(paieEnteteMoisCourant.getForf()));
			else{
				StringBuffer sbIndices=new StringBuffer("INA ");
				sbIndices.append(paieEnteteMoisCourant.getIna());
				sbIndices.append("   ");
				sbIndices.append("IBA ");
				try{
					sbIndices.append(Integer.parseInt(paieEnteteMoisCourant.getIban()));
				}catch (Exception ei){
					sbIndices.append(paieEnteteMoisCourant.getIban());
				}
				sbIndices.append("   ");
				sbIndices.append("INM ");
				sbIndices.append(paieEnteteMoisCourant.getInm());
				addZone(getNOM_ST_INDICES(),sbIndices.toString());
			}
				
			addZone(getNOM_ST_BASE_REGLEMENT(),paieEnteteMoisCourant.getLimodr());
			addZone(getNOM_ST_BASE_HORAIRE(),paieEnteteMoisCourant.getLibhor());

			String joursconges="";
			try{
				joursconges=(Integer.parseInt(paieEnteteMoisCourant.getPercou())>=200708) ? paieEnteteMoisCourant.getNbcong() : "-,--";
			}catch (Exception eI){
				logger.severe("ERREUR de parsing de Percou "+eI);
			}
			
			//ofonteneau ajout 17 mai 2011
			String rcn="N/A";
			String rcn_1="N/A";

			float solden=0;
			float solden_1=0;
			float soldepris=0;
			try{
				if(Integer.parseInt(paieEnteteMoisCourant.getPercou())>=201103)
				{
					try{
						solden=Float.parseFloat(paieEnteteMoisCourant.getSolde1());
					}catch (Exception e1){
						solden=0;
					}
					try{
						solden_1=Float.parseFloat(paieEnteteMoisCourant.getSolde2());
					}catch (Exception e2){
						solden_1=0;
					}
					try{
						soldepris=Float.parseFloat(paieEnteteMoisCourant.getNbrcp());
					}catch (Exception e3){
						soldepris=0;
					}

					//on enlève le solde pris à l'année n-1 et si c'est négatif on continue d'enlever sur année n
					solden_1=solden_1-soldepris;
					if (solden_1<0){
						solden=solden+solden_1;
						solden_1=0;
					}
				
					rcn=Utils.getJoursDemiJourneePres(solden)+" Jrs";
					rcn_1=Utils.getJoursDemiJourneePres(solden_1)+" Jrs";
					sDemiJoursRCN=Utils.getDemiJournees(solden);
					sDemiJoursRCN_1=Utils.getDemiJournees(solden_1);
				}
			}catch (Exception eI){
				logger.severe("ERREUR de parsing de Percou "+eI);
			}
			addZone(getNOM_ST_DROITS_RC_N(), rcn);
			addZone(getNOM_ST_DROITS_RC_N_1(), rcn_1);
			
			//On n'ajoute pas les jours de garde pour les pompiers car ca n'a pas été validé en production. Règle=(JoursCongés DIV3) jours de gardes.
			//il faudrait la condition être un pompier donc tester si dans SPSOLD par nomatr où BASCON='F'
			addZone(getNOM_ST_DROITS_CONGES(),"01/"+paieEnteteMoisCourant.getPercou().substring(4)+"   "+ joursconges +" Jrs");
			
			addZone(getNOM_ST_DROITS_RC_N(),rcn);
			addZone(getNOM_ST_DROITS_RC_N_1(),rcn_1);
			
			addZone(getNOM_ST_MATRICULE(),paieEnteteMoisCourant.getNomatr());
			addZone(getNOM_ST_PERIODE(),Utils.TraiterDateFr(paieEnteteMoisCourant.getPercou()));
			addZone(getNOM_ST_NOM(),paieEnteteMoisCourant.getNom()+" "+paieEnteteMoisCourant.getPrenom());
			addZone(getNOM_ST_NUM_RETRAITE(),paieEnteteMoisCourant.getNuretr());
			addZone(getNOM_ST_NUM_MUTUELLE(),paieEnteteMoisCourant.getNumutu());
			addZone(getNOM_ST_SERVICE(),paieEnteteMoisCourant.getLiserv());
			addZone(getNOM_ST_ECOL(),paieEnteteMoisCourant.getLiecol());
			addZone(getNOM_ST_INDEX_CORRECTION(),Utils.TraiterNombreIt(paieEnteteMoisCourant.getIndcor()));
			
			Double dValeurPoint=new Double(0);
			String sValeurPoint="";
			try{
				double dValP=Double.parseDouble(paieEnteteMoisCourant.getVap().replace(',','.'))/100;
				dValeurPoint=new Double(dValP);
			}catch (Exception e){
				logger.severe("Erreur dans ma division Double:"+paieEnteteMoisCourant.getVap()+"/100");
			}
			sValeurPoint=Utils.TraiterNombreIt(dValeurPoint.toString(),4);
			addZone(getNOM_ST_VALEUR_POINT(),sValeurPoint);
			
			// TBA (inm x vap)
			Double dTBA=new Double(0);
			sTBA="";
			try{
				double dcalcultba=Double.parseDouble(paieEnteteMoisCourant.getVap().replace(',','.'))*Double.parseDouble(paieEnteteMoisCourant.getInm().replace(',','.'))/100;
				dTBA=new Double(dcalcultba);
			}catch (Exception e){
				logger.severe("Erreur dans ma multiplication Double:"+paieEnteteMoisCourant.getVap()+" x "+paieEnteteMoisCourant.getInm());
			}
			if (dTBA==0)
				sTBA="";
			else
				sTBA=Utils.TraiterNombreIt(dTBA.toString(),2);
			addZone(getNOM_ST_TBA(),sTBA);
			
			
			//#12429 Modifier VISUSAL de la même façon que les bulletins papier
			addZone(getNOM_ST_COEFF_MAJORATION(), Integer.parseInt(paieEnteteMoisCourant.getPercou()) <201412 ? Utils.TraiterNombreIt(paieEnteteMoisCourant.getCoef()): "");
			
			addZone(getNOM_ST_POINT_CAFAT(),Utils.TraiterNombreIt(paieEnteteMoisCourant.getPtcafat()));
			addZone(getNOM_ST_SMIG(),Utils.TraiterNombreIt(paieEnteteMoisCourant.getSmig()));

			addZone(getNOM_ST_NUM_COMPTE_BANCAIRE(),paieEnteteMoisCourant.getCdbanq()+" "
					+Utils.ConcatDebutValeurStringTaille(paieEnteteMoisCourant.getCdguic(),"0",5)+" "
					+paieEnteteMoisCourant.getNocpte()+" "
					+paieEnteteMoisCourant.getClerib());
			addZone(getNOM_ST_DOMICILIATION(),paieEnteteMoisCourant.getLibanq());		
		}
		
	}
	
	/**
	 * Méthode appelée par la servlet qui aiguille le traitement : 
	 * en fonction du bouton de la JSP 
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 */
	public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{
		
		//Si on arrive de la JSP alors on traite le get
		if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {
			
			//Si clic sur le bouton PB_RECHERCHER
			if (testerParametre(request, getNOM_PB_RECHERCHER())) {
				return performPB_RECHERCHER(request);
			}
			
			//Si clic sur le bouton PB_MOIS_ANCIENNETE
			if (testerParametre(request, getNOM_PB_MOIS_ANCIENNETE())) {
				return performPB_MOIS_ANCIENNETE(request);
			}
			
			//Si clic sur le bouton PB_MOIS_ANCIENNETE
			if (testerParametre(request, getNOM_PB_ELEMENT())) {
				return performPB_ELEMENT(request);
			}
			
			//Si clic sur le bouton PB_IMPRIMER
			if (testerParametre(request, getNOM_PB_IMPRIMER())) {
				return performPB_IMPRIMER(request);
			}
		}
		
		//Si TAG INPUT non géré par le process
		//Si pas de retour définit
		setStatut(STATUT_MEME_PROCESS,false,"Erreur : TAG INPUT non géré par le process");
		return false;
	}
	/**
	 * Constructeur du process FicheSalaire.
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 */
	public FicheSalaire() {
		super();
	}
	/**
	 * Retourne le nom de la JSP du process
	 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 */
	public String getJSP() {
		return "FicheSalaire.jsp";
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_BASE_HORAIRE
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_BASE_HORAIRE() {
		return "NOM_ST_BASE_HORAIRE";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_BASE_HORAIRE
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_BASE_HORAIRE() {
		return getZone(getNOM_ST_BASE_HORAIRE());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_BASE_REGLEMENT
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_BASE_REGLEMENT() {
		return "NOM_ST_BASE_REGLEMENT";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_BASE_REGLEMENT
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_BASE_REGLEMENT() {
		return getZone(getNOM_ST_BASE_REGLEMENT());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_CATEGORIE_ADMINISTRATIVE
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_CATEGORIE_ADMINISTRATIVE() {
		return "NOM_ST_CATEGORIE_ADMINISTRATIVE";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_CATEGORIE_ADMINISTRATIVE
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_CATEGORIE_ADMINISTRATIVE() {
		return getZone(getNOM_ST_CATEGORIE_ADMINISTRATIVE());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_COEFF_MAJORATION
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_COEFF_MAJORATION() {
		return "NOM_ST_COEFF_MAJORATION";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_COEFF_MAJORATION
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_COEFF_MAJORATION() {
		return getZone(getNOM_ST_COEFF_MAJORATION());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_DOMICILIATION
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_DOMICILIATION() {
		return "NOM_ST_DOMICILIATION";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_DOMICILIATION
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_DOMICILIATION() {
		return getZone(getNOM_ST_DOMICILIATION());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_DROITS_CONGES
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_DROITS_CONGES() {
		return "NOM_ST_DROITS_CONGES";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_DROITS_CONGES
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_DROITS_CONGES() {
		return getZone(getNOM_ST_DROITS_CONGES());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_DROITS_RC_N
	 * Date de création : (16/05/11 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_DROITS_RC_N() {
		return "NOM_ST_DROITS_RC_N";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_DROITS_RC_N
	 * Date de création : (16/05/11 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_DROITS_RC_N() {
		return getZone(getNOM_ST_DROITS_RC_N());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_DROITS_RC_N_1
	 * Date de création : (16/05/11 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_DROITS_RC_N_1() {
		return "NOM_ST_DROITS_RC_N_1";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_DROITS_RC_N_1
	 * Date de création : (16/05/11 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_DROITS_RC_N_1() {
		return getZone(getNOM_ST_DROITS_RC_N_1());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_GRADE
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_GRADE() {
		return "NOM_ST_GRADE";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_GRADE
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_GRADE() {
		return getZone(getNOM_ST_GRADE());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_GRADE2
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_GRADE2() {
		return "NOM_ST_GRADE2";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_GRADE2
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_GRADE2() {
		return getZone(getNOM_ST_GRADE2());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_INDEX_CORRECTION
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_INDEX_CORRECTION() {
		return "NOM_ST_INDEX_CORRECTION";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_INDEX_CORRECTION
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_INDEX_CORRECTION() {
		return getZone(getNOM_ST_INDEX_CORRECTION());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_INDICES
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_INDICES() {
		return "NOM_ST_INDICES";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_INDICES
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_INDICES() {
		return getZone(getNOM_ST_INDICES());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_MATRICULE
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_MATRICULE() {
		return "NOM_ST_MATRICULE";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_MATRICULE
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_MATRICULE() {
		return getZone(getNOM_ST_MATRICULE());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_NET_EUROS
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_NET_EUROS() {
		return "NOM_ST_NET_EUROS";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_NET_EUROS
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_NET_EUROS() {
		return getZone(getNOM_ST_NET_EUROS());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_NET_IMPOS_CUMULS
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_NET_IMPOS_CUMULS() {
		return "NOM_ST_NET_IMPOS_CUMULS";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_NET_IMPOS_CUMULS
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_NET_IMPOS_CUMULS() {
		return getZone(getNOM_ST_NET_IMPOS_CUMULS());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_NET_IMPOS_MOIS
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_NET_IMPOS_MOIS() {
		return "NOM_ST_NET_IMPOS_MOIS";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_NET_IMPOS_MOIS
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_NET_IMPOS_MOIS() {
		return getZone(getNOM_ST_NET_IMPOS_MOIS());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_NET_NONIMPOS_CUMULS
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_NET_NONIMPOS_CUMULS() {
		return "NOM_ST_NET_NONIMPOS_CUMULS";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_NET_NONIMPOS_CUMULS
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_NET_NONIMPOS_CUMULS() {
		return getZone(getNOM_ST_NET_NONIMPOS_CUMULS());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_NET_NONIMPOS_MOIS
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_NET_NONIMPOS_MOIS() {
		return "NOM_ST_NET_NONIMPOS_MOIS";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_NET_NONIMPOS_MOIS
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_NET_NONIMPOS_MOIS() {
		return getZone(getNOM_ST_NET_NONIMPOS_MOIS());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_NET_XPF
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_NET_XPF() {
		return "NOM_ST_NET_XPF";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_NET_XPF
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_NET_XPF() {
		return getZone(getNOM_ST_NET_XPF());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_NOM
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_NOM() {
		return "NOM_ST_NOM";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_NOM
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_NOM() {
		return getZone(getNOM_ST_NOM());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_NUM_COMPTE_BANCAIRE
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_NUM_COMPTE_BANCAIRE() {
		return "NOM_ST_NUM_COMPTE_BANCAIRE";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_NUM_COMPTE_BANCAIRE
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_NUM_COMPTE_BANCAIRE() {
		return getZone(getNOM_ST_NUM_COMPTE_BANCAIRE());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_NUM_MUTUELLE
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_NUM_MUTUELLE() {
		return "NOM_ST_NUM_MUTUELLE";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_NUM_MUTUELLE
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_NUM_MUTUELLE() {
		return getZone(getNOM_ST_NUM_MUTUELLE());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_NUM_RETRAITE
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_NUM_RETRAITE() {
		return "NOM_ST_NUM_RETRAITE";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_NUM_RETRAITE
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_NUM_RETRAITE() {
		return getZone(getNOM_ST_NUM_RETRAITE());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_PERIODE
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_PERIODE() {
		return "NOM_ST_PERIODE";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_PERIODE
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_PERIODE() {
		return getZone(getNOM_ST_PERIODE());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_POINT_CAFAT
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_POINT_CAFAT() {
		return "NOM_ST_POINT_CAFAT";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_POINT_CAFAT
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_POINT_CAFAT() {
		return getZone(getNOM_ST_POINT_CAFAT());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_SERVICE
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_SERVICE() {
		return "NOM_ST_SERVICE";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_SERVICE
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_SERVICE() {
		return getZone(getNOM_ST_SERVICE());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_SERVICE
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_ECOL() {
		return "NOM_ST_ECOL";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_SERVICE
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_ECOL() {
		return getZone(getNOM_ST_ECOL());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_SMIG
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_SMIG() {
		return "NOM_ST_SMIG";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_SMIG
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_SMIG() {
		return getZone(getNOM_ST_SMIG());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_TBA
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_TBA() {
		return "NOM_ST_TBA";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_TBA
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_TBA() {
		return getZone(getNOM_ST_TBA());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_TOTAUX_APAYER
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_TOTAUX_APAYER() {
		return "NOM_ST_TOTAUX_APAYER";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_TOTAUX_APAYER
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_TOTAUX_APAYER() {
		return getZone(getNOM_ST_TOTAUX_APAYER());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_TOTAUX_ARETENIR
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_TOTAUX_ARETENIR() {
		return "NOM_ST_TOTAUX_ARETENIR";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_TOTAUX_ARETENIR
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_TOTAUX_ARETENIR() {
		return getZone(getNOM_ST_TOTAUX_ARETENIR());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_VALEUR_POINT
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_VALEUR_POINT() {
		return "NOM_ST_VALEUR_POINT";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_VALEUR_POINT
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_VALEUR_POINT() {
		return getZone(getNOM_ST_VALEUR_POINT());
	}
	/**
	 * Getter de la liste avec un lazy initialize :
	 * LB_ELEMENTS_PAIE
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 */
	private String [] getLB_ELEMENTS_PAIE() {
		if (LB_ELEMENTS_PAIE == null)
			LB_ELEMENTS_PAIE = initialiseLazyLB();
		return LB_ELEMENTS_PAIE;
	}
	/**
	 * Retourne le nom de la zone pour la JSP :
	 * NOM_LB_ELEMENTS_PAIE
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_LB_ELEMENTS_PAIE() {
		return "NOM_LB_ELEMENTS_PAIE";
	}
	/**
	 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
	 * NOM_LB_ELEMENTS_PAIE_SELECT
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_LB_ELEMENTS_PAIE_SELECT() {
		return "NOM_LB_ELEMENTS_PAIE_SELECT";
	}
	/**
	 * Méthode à personnaliser
	 * Retourne la valeur à afficher pour la zone de la JSP :
	 * LB_ELEMENTS_PAIE
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String [] getVAL_LB_ELEMENTS_PAIE() {
		return getLB_ELEMENTS_PAIE();
	}
	/**
	 * Méthode à personnaliser
	 * Retourne l'indice à sélectionner pour la zone de la JSP :
	 * LB_ELEMENTS_PAIE
	 * Date de création : (23/02/09 15:35:45)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_LB_ELEMENTS_PAIE_SELECT() {
		return getZone(getNOM_LB_ELEMENTS_PAIE_SELECT());
	}
	/**
	 * Retourne le nom d'un bouton pour la JSP :
	 * PB_RECHERCHER
	 * Date de création : (23/02/09 15:40:50)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_PB_RECHERCHER() {
		return "NOM_PB_RECHERCHER";
	}
	
	public java.lang.String getNOM_PB_MOIS_ANCIENNETE() {
		return "NOM_PB_MOIS_ANCIENNETE";
	}
	
	public java.lang.String getNOM_PB_ELEMENT() {
		return "NOM_PB_ELEMENT";
	}
	/**
	 * - Traite et affecte les zones saisies dans la JSP.
	 * - Implémente les règles de gestion du process
	 * - Positionne un statut en fonction de ces règles :
	 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
	 * Date de création : (23/02/09 15:40:50)
	 * @author : Générateur de process
	 * @param request request
	 * @return boolean
	 * @throws Exception Exception 
	 */
	public boolean performPB_RECHERCHER(javax.servlet.http.HttpServletRequest request) throws Exception {
		//setActivite("AgentSelection");
		VariableGlobale.enlever(request,VariableGlobale.GLOBAL_AGENT_MAIRIE);
		VariableGlobale.enlever(request,MOIS_COURANT);
		setStatut(STATUT_RECHERCHER_AGENT,true);
		return true;
	}
	
	public boolean performPB_MOIS_ANCIENNETE(javax.servlet.http.HttpServletRequest request) throws Exception {
		//setActivite("AgentSelection");
		//setStatut(STATUT_RECHERCHER_AGENT,true);
		
		//	Test si ligne sélectionnée
		int numligne = (Services.estNumerique(getZone(getNOM_LB_MOIS_ANCIENNETE_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_MOIS_ANCIENNETE_SELECT())) : -1);
		if (numligne == -1 || getListeMoisAnciennete().size() == 0 || numligne > getListeMoisAnciennete().size() -1 ) {
			//"ERR997","Aucun élément n'est sélectionné dans la liste des @."
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","mois d'ancienneté"));
			return false;
		}
		
		//Récup du courant
		PaieElement pe = getListeMoisAnciennete().get(numligne);
		setPaieElementMoisCourant(pe);
		VariableGlobale.ajouter(request,MOIS_COURANT,pe);
		
		setStatut(STATUT_MEME_PROCESS);
		return true;	
	}
	
	public boolean performPB_ELEMENT(javax.servlet.http.HttpServletRequest request) throws Exception {

		//AJOUTER LES VARIABLES ACTIVITE EN COURS????
		setStatut(STATUT_SALAIRE_RAPPEL,true);
		return true;	
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_AGENT_NOM
	 * Date de création : (23/02/09 15:40:50)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_AGENT_NOM() {
		return "NOM_ST_AGENT_NOM";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_AGENT_NOM
	 * Date de création : (23/02/09 15:40:50)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_AGENT_NOM() {
		return getZone(getNOM_ST_AGENT_NOM());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_AGENT_PRENOM
	 * Date de création : (23/02/09 15:40:50)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_AGENT_PRENOM() {
		return "NOM_ST_AGENT_PRENOM";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_AGENT_PRENOM
	 * Date de création : (23/02/09 15:40:50)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_AGENT_PRENOM() {
		return getZone(getNOM_ST_AGENT_PRENOM());
	}
	/**
	 * Retourne pour la JSP le nom de la zone statique :
	 * ST_AGENT_MATRICULE
	 * Date de création : (23/02/09 15:40:50)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_ST_AGENT_MATRICULE() {
		return "NOM_ST_AGENT_MATRICULE";
	}
	/**
	 * Retourne la valeur à afficher par la JSP  pour la zone :
	 * ST_AGENT_MATRICULE
	 * Date de création : (23/02/09 15:40:50)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_ST_AGENT_MATRICULE() {
		return getZone(getNOM_ST_AGENT_MATRICULE());
	}
	/**
	 * Retourne le nom d'une zone de saisie pour la JSP :
	 * EF_AGENT
	 * Date de création : (23/02/09 15:40:50)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_EF_AGENT() {
		return "NOM_EF_AGENT";
	}
	/**
	 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
	 * EF_AGENT
	 * Date de création : (23/02/09 15:40:50)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_EF_AGENT() {
		return getZone(getNOM_EF_AGENT());
	}
	/**
	 * Getter de la liste avec un lazy initialize :
	 * LB_MOIS_ANCIENNETE
	 * Date de création : (23/02/09 15:40:50)
	 * @author : Générateur de process
	 */
	private String [] getLB_MOIS_ANCIENNETE() {
		if (LB_MOIS_ANCIENNETE == null)
			LB_MOIS_ANCIENNETE = initialiseLazyLB();
		return LB_MOIS_ANCIENNETE;
	}
	/**
	 * Setter de la liste:
	 * LB_MOIS_ANCIENNETE
	 * Date de création : (23/02/09 15:40:50)
	 * @author : Générateur de process
	 */
	private void setLB_MOIS_ANCIENNETE(java.lang.String[] newLB_MOIS_ANCIENNETE) {
		LB_MOIS_ANCIENNETE = newLB_MOIS_ANCIENNETE;
	}
	/**
	 * Retourne le nom de la zone pour la JSP :
	 * NOM_LB_MOIS_ANCIENNETE
	 * Date de création : (23/02/09 15:40:50)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_LB_MOIS_ANCIENNETE() {
		return "NOM_LB_MOIS_ANCIENNETE";
	}
	/**
	 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
	 * NOM_LB_MOIS_ANCIENNETE_SELECT
	 * Date de création : (23/02/09 15:40:50)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getNOM_LB_MOIS_ANCIENNETE_SELECT() {
		return "NOM_LB_MOIS_ANCIENNETE_SELECT";
	}
	/**
	 * Méthode à personnaliser
	 * Retourne la valeur à afficher pour la zone de la JSP :
	 * LB_MOIS_ANCIENNETE
	 * Date de création : (23/02/09 15:40:50)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String [] getVAL_LB_MOIS_ANCIENNETE() {
		return getLB_MOIS_ANCIENNETE();
	}
	/**
	 * Méthode à personnaliser
	 * Retourne l'indice à sélectionner pour la zone de la JSP :
	 * LB_MOIS_ANCIENNETE
	 * Date de création : (23/02/09 15:40:50)
	 * @author : Générateur de process
	 * @return String
	 */
	public java.lang.String getVAL_LB_MOIS_ANCIENNETE_SELECT() {
		return getZone(getNOM_LB_MOIS_ANCIENNETE_SELECT());
	}
	
	public java.lang.String getNOM_PB_IMPRIMER(){
		return "NOM_NOM_PB_IMPRIMER";
	}
	
	public boolean performPB_IMPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
		
		generateCommonsVFSBulletinPDFData();
		
		return true;
	}
	
	public AgentMairie getAgentMCourant() {
		return agentMCourant;
	}
	public void setAgentMCourant(AgentMairie agentMCourant) {
		this.agentMCourant = agentMCourant;
	}
	public java.util.ArrayList<PaieElement> getListeElementsPaye() {
		return listeElementsPaye;
	}
	public void setListeElementsPaye(java.util.ArrayList<PaieElement> listeElementsPaye) {
		this.listeElementsPaye = listeElementsPaye;
	}
	public java.util.ArrayList<PaieElement> getListeMoisAnciennete() {
		return listeMoisAnciennete;
	}
	public void setListeMoisAnciennete(java.util.ArrayList<PaieElement> listeMoisAnciennete) {
		this.listeMoisAnciennete = listeMoisAnciennete;
	}
	
	public PaieElement getPaieElementMoisCourant() {
		return paieElementMoisCourant;
	}
	public void setPaieElementMoisCourant(PaieElement paieElementMoisCourant) {
		this.paieElementMoisCourant = paieElementMoisCourant;
	}
	
	public PaieEntete getPaieEnteteMoisCourant() {
		return paieEnteteMoisCourant;
	}
	public void setPaieEnteteMoisCourant(PaieEntete paieEnteteMoisCourant) {
		this.paieEnteteMoisCourant = paieEnteteMoisCourant;
	}
	
	
//	ATTENTION APPEND.(STRING_BUFFER) NE MARCHE PAS SUR STRING BUFFER !!!!!!
//  ATTENTION LES STRING.REPLACEALL doivent être testés avant d'être effectués, car si le caractère à remplacer n'y est pas, il renvoit chaine vide !!	
/**
 * 
 * Affichage des elements de paie de la fiche de salaire.
 * Si le numéro de la rubrique est dans les 8000 (rubriques de rappel) on affiche un lien qui déroule les mois de rappel
 * @return String
 * @throws Exception Exception
 * @throws NumberFormatException NumberFormatException
 */
	private String generateELEMENTS_PAIE() throws Exception, NumberFormatException{
		listeBulletinSalarie= new ArrayList<BulletinElement> ();
		listeBulletinPatronal= new ArrayList<BulletinElement> ();
		BulletinElement bulletinElemSal=null;
		BulletinElement bulletinElemPat=null;
		//Elements Globaux
		StringBuffer sbElements = new StringBuffer();
		//Elements Salariés
		StringBuffer sbElementsSal = new StringBuffer();
		//Elements Patronaux
		StringBuffer sbElementsPat = new StringBuffer();

		String sNomRubrique="";
		
		sbElements.append("<TABLE border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
		sbElements.append("<TBODY>");
		sbElements.append("<TR class=\"sigp2\">");
		sbElements.append("<TD colspan=\"3\">");
		sbElements.append("<TABLE width=\"100%\" border=\"0\">");
		sbElements.append("<TBODY>");
		sbElements.append("<TR>");
		sbElements.append("<TD class=\"sigp2-majuscule-titre\" style=\"text-align:center;\" width=\"44%\">ELEMENTS DE PAIE</TD>");
		sbElements.append("<TD class=\"sigp2-majuscule-titre\" style=\"text-align:center;\" width=\"14%\">NOMBRE ou TAUX</TD>");
		sbElements.append("<TD class=\"sigp2-majuscule-titre\" style=\"text-align:center;\" width=\"14%\">BASE ou ASSIETTE</TD>");
		sbElements.append("<TD class=\"sigp2-majuscule-titre\" style=\"text-align:center;\" width=\"14%\">A RETENIR</TD>");
		sbElements.append("<TD class=\"sigp2-majuscule-titre\" style=\"text-align:center;\" width=\"14%\">A PAYER</TD>");
		sbElements.append("</TR>");
		
		sbElementsPat.append("\n");
		sbElementsPat.append("<TBODY>");
		sbElementsPat.append("\n");
		sbElementsPat.append("<TR>");
		sbElementsPat.append("<TD class=\"sigp2-texte-fond2\" colspan=\"5\">&nbsp;").append("</TD>");
		sbElementsPat.append("</TR>");
		sbElementsPat.append("<TR>");
		sbElementsPat.append("<TD class=\"sigp2-texte-fond2\" style=\"text-align:center;font-weight: bold;color:#333333;\" colspan=\"5\">").append("PART PATRONALE").append("</TD>");
		sbElementsPat.append("</TR>");
		sbElementsPat.append("<TR>");
		sbElementsPat.append("<TD class=\"sigp2-texte-fond2\" colspan=\"5\">&nbsp;").append("</TD>");
		sbElementsPat.append("</TR>");
		
		try{
			
			boolean alreadyTreated8000or8001=false;
			int iSalaireBrutAPayer=0;
			int iSalaireBrutARetenir=0;
			
			boolean alreadyTreatedCAFATPat2900=false;
			boolean alreadyTreatedCAFATPat8220=false;
			String sMoisCAFATPat8220="";
			StringBuffer sbRappelsCAFATPat = new StringBuffer("");
			double dTauxCafatPat=0;
			int iAPayerCafatPat=0;
			int iARetenirCafatPat=0;
			String sBaseAssietteCafatPat="";

			//Pour garder le fonctionnement séquentiel de cette fonction il est important que listeElementsPaye soit classé par ordre de NORUBR
			if (null!=listeElementsPaye && listeElementsPaye.size()>0){
				//on liste les elements
				for (java.util.ListIterator<PaieElement> list = listeElementsPaye.listIterator(); list.hasNext(); ) {
					PaieElement pe = (list.next());
					
					//logger.info("LIGNE:"+i+" RUBRNBR="+pe.getNorubr());
					
					int iNoRubrique=Integer.parseInt(pe.getNorubr());

					//Conditions Spéciales d'affichage:
					if (iNoRubrique==9000){
						//SALAIRE NET
						addZone(getNOM_ST_NET_XPF(),Utils.TraiterNombreIt(pe.getMtpsal()));
						netPayer=pe.getMtpsal();
						try{
							double dEurosNet=0;
							dEurosNet=Double.parseDouble(pe.getMtpsal())/dValeurEuroXPF;
							addZone(getNOM_ST_NET_EUROS(),Utils.TraiterNombreIt(""+dEurosNet,2));
							}catch (Exception e){
								logger.severe("Exception XPF TO EUROS===="+e);
							}

					}else if(iNoRubrique==9001){
						//SALAIRE NET IMPOSABLE
						addZone(getNOM_ST_NET_IMPOS_MOIS(),Utils.TraiterNombreIt(pe.getMtpsal()));
						netImposMois=pe.getMtpsal();
						
					}else if(iNoRubrique==9002){
						//SALAIRE NET NON IMPOSABLE
						addZone(getNOM_ST_NET_NONIMPOS_MOIS(),Utils.TraiterNombreIt(pe.getMtpsal()));
						nonImposMois=pe.getMtpsal();
						
					}else if(iNoRubrique==9003){
						//SALAIRE NET CUMULE IMPOSABLE
						addZone(getNOM_ST_NET_IMPOS_CUMULS(),Utils.TraiterNombreIt(pe.getMtpsal()));
						netImposCumul=pe.getMtpsal();
						
					}else if(iNoRubrique==9004){
						//SALAIRE NET CUMULE NON IMPOSABLE
						addZone(getNOM_ST_NET_NONIMPOS_CUMULS(),Utils.TraiterNombreIt(pe.getMtpsal()));
						nonImposCumul=pe.getMtpsal();
						
				//	}else if(iNoRubrique==9030){
						//SALAIRE BRUT APAYER
				//		addZone(getNOM_ST_TOTAUX_APAYER(),Utils.TraiterNombreIt(pe.getMtpsal()));
					
						
					}else if (iNoRubrique<9000){
					//on n'affiche pas les rubriques >=9000
						
						StringBuffer sbRappelsJsSal = new StringBuffer("");
						StringBuffer sbRappelsJsPat = new StringBuffer("");
						StringBuffer elementPaie = new StringBuffer("");
						StringBuffer nombreTauxSal  = new StringBuffer("");
						StringBuffer nombreTauxPat  = new StringBuffer("");
						StringBuffer baseAssiette = new StringBuffer("");
						StringBuffer aRetenirSal = new StringBuffer("");
						StringBuffer aPayerSal = new StringBuffer("");
						StringBuffer aRetenirPat = new StringBuffer("");
						StringBuffer aPayerPat = new StringBuffer("");
						
						boolean bAffichRappPat=false;
						boolean bAffichRappSal=false;
						int iPSal=0;
						int iPPat=0;
						String mtBases=""; 
						String sMoisRubriqueRappel="";
						
						bulletinElemSal=new BulletinElement();
						bulletinElemPat=new BulletinElement();
						
						PaieRubrique paieRubrique=(PaieRubrique)PaieRubrique.chercherPaieRubrique(getTransaction(),""+iNoRubrique);
						//noRubrique	
						bulletinElemSal.setNoRubr(""+iNoRubrique);
						bulletinElemPat.setNoRubr(""+iNoRubrique);
						//Libelle
						sNomRubrique=paieRubrique.getLirubr();
						if (3000==iNoRubrique){
							PaieMutuelle paiemutu=PaieMutuelle.chercherPaieMutuelle(getTransaction(), agentMCourant.getNomatr());	
							sNomRubrique="MUTUELLE "+paiemutu.getLimutu();
						}
						bulletinElemSal.setLiRubr(sNomRubrique);
						bulletinElemPat.setLiRubr(sNomRubrique);
						
						//Nombre ou taux
						nombreTauxSal = new StringBuffer(calculerNombreTaux(pe,true));
						nombreTauxPat = new StringBuffer(calculerNombreTaux(pe,false));
						//test sur la virgule pour savoir si c'est bien un taux renvoyé par AS400
						if (nombreTauxSal.indexOf(",")!=-1 && iNoRubrique != 1001)
							bulletinElemSal.setTaux(nombreTauxSal.toString().replace(",", ""));
						else
							bulletinElemSal.setNombre(nombreTauxSal.toString());
						bulletinElemPat.setTaux(nombreTauxPat.toString().replace(",", ""));
						//Base ou assiette
						if (Double.parseDouble(pe.getMtbase())!=0){
							baseAssiette.append(Utils.TraiterNombreIt(pe.getMtbase(),2));
							mtBases=baseAssiette.toString();
							mtBases=mtBases.replace(".", "");
							mtBases=mtBases.replace(",", "");
							mtBases=mtBases.replace("-", "");
							bulletinElemSal.setMtBases(mtBases);
							bulletinElemPat.setMtBases(mtBases);
						}

						try{
							iPSal=Integer.parseInt(pe.getMtpsal());
						}catch (Exception e){
							logger.severe("ERREUR Mtpsal="+pe.getMtpsal());
						}
						if (iPSal>0){
							aPayerSal.append(Utils.TraiterNombreIt(pe.getMtpsal()));
							bulletinElemSal.setMontant(pe.getMtpsal());
							bulletinElemSal.setSigne("P");
						}
						else if (iPSal<0){
							aRetenirSal.append(Utils.TraiterNombreIt(pe.getMtpsal().substring(1)));
							bulletinElemSal.setMontant(pe.getMtpsal().substring(1));
							bulletinElemSal.setSigne("R");
						}

						try{
							iPPat=Integer.parseInt(pe.getMtppat());
						}catch (Exception e){
							logger.severe("ERREUR getMtppat="+pe.getMtppat());
						}
						if (iPPat>0){
							aPayerPat.append("(").append(Utils.TraiterNombreIt(pe.getMtppat())).append(")");
							bulletinElemPat.setMontant(pe.getMtppat());
							bulletinElemPat.setSigne("P");
						}
						else if (iPPat<0){
							aRetenirPat.append("(").append(Utils.TraiterNombreIt(pe.getMtppat().substring(1))).append(")");
							bulletinElemPat.setMontant(pe.getMtppat().substring(1));
							bulletinElemPat.setSigne("R");
						}

						//DEBUT Elements CAFAT Patronal à regrouper (id RUBR compris entre 2900 et 2909) ou (id RUBR compris entre 8220 et 8229 pour les rappels cafat)
						if((iNoRubrique>2900&&iNoRubrique<=2909)||(iNoRubrique>8220&&iNoRubrique<=8229)){
							dTauxCafatPat=dTauxCafatPat+Double.parseDouble(pe.getTxpat());
							if(Integer.parseInt(pe.getMtppat())>0)
								iAPayerCafatPat=iAPayerCafatPat+Integer.parseInt(pe.getMtppat());
							else if (Integer.parseInt(pe.getMtppat())<0)
								iARetenirCafatPat=iARetenirCafatPat+Integer.parseInt(pe.getMtppat());
							sBaseAssietteCafatPat=baseAssiette.toString();
							//pour ne pas afficher les éléments 2900 à 2909 courant ainsi que leurs rappels en 8220 à 8229
							iPPat=0;
						}

						if(!alreadyTreatedCAFATPat2900&&iNoRubrique>2909){
							//on affiche la rubrique 2900 avec les somme concaténées. 
							if(iARetenirCafatPat!=0||iAPayerCafatPat!=0){
								dTauxCafatPat=dTauxCafatPat*100;
								String sARetenirCafatPat=((iARetenirCafatPat)!=0)?"("+Utils.TraiterNombreIt(new String(""+iARetenirCafatPat).substring(1))+")":"";
								String sAPayerCafatPat=((iAPayerCafatPat)!=0)?"("+Utils.TraiterNombreIt(""+iAPayerCafatPat)+")":"";
								String sNbreOuTauxCafatPat=Utils.TraiterNombreIt(new String(""+dTauxCafatPat),2);
								sbElementsPat.append("\n");
								sbElementsPat.append("<TR>");
								sbElementsPat.append("<TD class=\"sigp2-texte-fond2\">").append("2900 COTISATION CAFAT").append("</TD>");
								sbElementsPat.append("<TD class=\"sigp2-texte-fond1\" style=\"text-align:right;\">").append(sNbreOuTauxCafatPat).append("</TD>");
								sbElementsPat.append("<TD class=\"sigp2-texte-fond2\" style=\"text-align:right;\">").append(sBaseAssietteCafatPat).append("</TD>");
								sbElementsPat.append("<TD class=\"sigp2-texte-fond1\" style=\"text-align:right;\">").append(sARetenirCafatPat).append("</TD>");
								sbElementsPat.append("<TD class=\"sigp2-texte-fond1\" style=\"text-align:right;\">").append(sAPayerCafatPat).append("</TD>");
								sbElementsPat.append("</TR>");

								BulletinElement bulletinElemPat2900=new BulletinElement();
								bulletinElemPat2900.setNoRubr("2900");
								bulletinElemPat2900.setLiRubr("COTISATION CAFAT");
								sNbreOuTauxCafatPat=sNbreOuTauxCafatPat.trim();
								sNbreOuTauxCafatPat=sNbreOuTauxCafatPat.replace(".", "");
								sNbreOuTauxCafatPat=sNbreOuTauxCafatPat.replace(",", "");
								bulletinElemPat2900.setTaux(sNbreOuTauxCafatPat);
								bulletinElemPat2900.setMtBases(mtBases);
								if(iARetenirCafatPat!=0){
									bulletinElemPat2900.setMontant(new String(""+iARetenirCafatPat).substring(1));
									bulletinElemPat2900.setSigne("R");
								}else if(iAPayerCafatPat!=0){
									bulletinElemPat2900.setMontant(new String(""+iAPayerCafatPat));
									bulletinElemPat2900.setSigne("P");
								}
								listeBulletinPatronal.add(bulletinElemPat2900);
							}
							alreadyTreatedCAFATPat2900=true;
							dTauxCafatPat=0;
							iAPayerCafatPat=0;
							iARetenirCafatPat=0;
							sBaseAssietteCafatPat="";
						}

						if(!alreadyTreatedCAFATPat8220&&iNoRubrique>8229){
							//on affiche la rubrique 8220 avec les sommes aditionnées. 
							//dTauxCafatPat=dTauxCafatPat*100;
							if(iARetenirCafatPat!=0||iAPayerCafatPat!=0){
								String sARetenirCafatPat=((iARetenirCafatPat)!=0)?"("+Utils.TraiterNombreIt(new String(""+iARetenirCafatPat).substring(1))+")":"";
								String sAPayerCafatPat=((iAPayerCafatPat)!=0)?"("+Utils.TraiterNombreIt(""+iAPayerCafatPat)+")":"";
								String sNbreOuTauxCafatPat=Utils.TraiterNombreIt(new String(""+dTauxCafatPat),2);
								elementPaie=new StringBuffer("");
								elementPaie.append("<A style=\"color:#008080;cursor:pointer;\" onclick='javascript:montreblock(\"smenuP").append("8220").append("\");' >");
								elementPaie.append("8220").append(" ").append("RAP CAFAT");
								elementPaie.append("</A>");
								sbElementsPat.append("\n");
								sbElementsPat.append("<TR id=\"trP").append("8220").append("\">");
								sbElementsPat.append("<TD class=\"sigp2-texte-fond2\">").append(elementPaie.toString()).append("</TD>");
								sbElementsPat.append("<TD class=\"sigp2-texte-fond1\" style=\"text-align:right;\">").append(sNbreOuTauxCafatPat).append("</TD>");
								sbElementsPat.append("<TD class=\"sigp2-texte-fond2\" style=\"text-align:right;\">").append(sBaseAssietteCafatPat).append("</TD>");
								sbElementsPat.append("<TD class=\"sigp2-texte-fond1\" style=\"text-align:right;\">").append(sARetenirCafatPat).append("</TD>");
								sbElementsPat.append("<TD class=\"sigp2-texte-fond1\" style=\"text-align:right;\">").append(sAPayerCafatPat).append("</TD>");
								sbElementsPat.append("</TR>");
								sbElementsPat.append("\n");
								sbElementsPat.append("<TBODY style=\"display: none;\" id=\"smenuP").append("8220").append("\">");
								sbElementsPat.append(sbRappelsCAFATPat.toString());
								sbElementsPat.append("</TBODY>");

								BulletinElement bulletinElemPat8220=new BulletinElement();
								bulletinElemPat8220.setNoRubr("8220");
								bulletinElemPat8220.setLiRubr("RAP CAFAT           "+sMoisCAFATPat8220);
								sNbreOuTauxCafatPat=sNbreOuTauxCafatPat.trim();
								sNbreOuTauxCafatPat=sNbreOuTauxCafatPat.replace(".", "");
								sNbreOuTauxCafatPat=sNbreOuTauxCafatPat.replace(",", "");
								bulletinElemPat8220.setTaux(sNbreOuTauxCafatPat);
								bulletinElemPat8220.setMtBases(mtBases);
								if(iARetenirCafatPat!=0){
									bulletinElemPat8220.setMontant(new String(""+iARetenirCafatPat).substring(1));
									bulletinElemPat8220.setSigne("R");
								}else if(iAPayerCafatPat!=0){
									bulletinElemPat8220.setMontant(new String(""+iAPayerCafatPat));
									bulletinElemPat8220.setSigne("P");
								}
								listeBulletinPatronal.add(bulletinElemPat8220);
							}
							alreadyTreatedCAFATPat8220=true;
							dTauxCafatPat=0;
							iAPayerCafatPat=0;
							iARetenirCafatPat=0;
							sBaseAssietteCafatPat="";
						}
						//FIN Elements CAFAT Patronal à regrouper (id compris entre 2900 et 2909) ou (id RUBR compris entre 8220 et 8229 pour les rappels cafat)

						//cas des rappels, lien cliquable
						if (iNoRubrique>=8000){ 
							//LES RAPPELS SONT ICI !!!!
							//la rubrique correspondante entre 1000 et 8000
							PaieRubrique paieRubriquedeRappel=(PaieRubrique)PaieRubrique.chercherPaieRubriqueRappelCorrespondante(getTransaction(),pe.getNorubr());
							//#12744 Si pas trouvé le rappel alors pas de message d'erreur
							if (getTransaction().isErreur()) getTransaction().traiterErreur();
							if (null!=paieRubriquedeRappel.getNorubr() || pe.getNorubr().equals("8000") || pe.getNorubr().equals("8001")){
								java.util.ArrayList<PaieRappel> a=new ArrayList<PaieRappel>();
								
								if(null!=paieRubriquedeRappel.getNorubr())
									a=PaieRappel.listerPaieRappel(getTransaction(), agentMCourant.getNomatr(), paieElementMoisCourant.getPercou(), paieRubriquedeRappel.getNorubr());
								
								
								//Bizarrerie: si la rubrique est 8000 ou 8001 (RAPPELS DE SALAIRE), la rubrique de rappel peut être indépendament 1001 ou 1003
								if (!alreadyTreated8000or8001 && pe.getNorubr().equals("8000")){
									//rub 1003 correspond à la rubrique rappel 8001
									a.addAll(PaieRappel.listerPaieRappel(getTransaction(), agentMCourant.getNomatr(), paieElementMoisCourant.getPercou(),"1003"));
									alreadyTreated8000or8001=true;
								}
								if (!alreadyTreated8000or8001 && pe.getNorubr().equals("8001")){
									paieRubriquedeRappel=(PaieRubrique)PaieRubrique.chercherPaieRubriqueRappelCorrespondante(getTransaction(),"8000");
									//rub 1001 correspond à la rubrique rappel 8000
									a.addAll(PaieRappel.listerPaieRappel(getTransaction(), agentMCourant.getNomatr(), paieElementMoisCourant.getPercou(),"1001"));
									alreadyTreated8000or8001=true;
								}
								
								if(iNoRubrique<=8229&&iNoRubrique>8220){
									elementPaie=new StringBuffer("");
									elementPaie.append(pe.getNorubr()).append(" ").append(sNomRubrique);
									
									sbRappelsJsPat.append("\n");
									sbRappelsJsPat.append("<TR>");
									sbRappelsJsPat.append("<TD class=\"sigp2-texte-fond3\" colspan=\"5\">").append("&nbsp;&nbsp;").append(paieRubriquedeRappel.getNorubr()).append(" ").append(paieRubriquedeRappel.getLirubr()).append("</TD>");
									sbRappelsJsPat.append("</TR>");

									elementPaie=new StringBuffer("");
									elementPaie.append("<A style=\"color:#008080;cursor:pointer;\" onclick='javascript:montreblock(\"smenu@").append(iNoRubrique).append("\");' >");
									elementPaie.append(pe.getNorubr()).append(" ").append(sNomRubrique);
									elementPaie.append("</A>");

									sbRappelsJsSal.append("\n");
									sbRappelsJsSal.append("<TBODY style=\"display: none;\" id=\"smenuS").append(iNoRubrique).append("\">");
									sbRappelsJsSal.append("\n");
									sbRappelsJsSal.append("<TR>");
									sbRappelsJsSal.append("<TD class=\"sigp2-texte-fond3\" colspan=\"5\">").append("&nbsp;&nbsp;").append(paieRubriquedeRappel.getNorubr()).append(" ").append(paieRubriquedeRappel.getLirubr()).append("</TD>");
									sbRappelsJsSal.append("</TR>");

								}else{
									elementPaie=new StringBuffer("");
									elementPaie.append("<A style=\"color:#008080;cursor:pointer;\" onclick='javascript:montreblock(\"smenu@").append(iNoRubrique).append("\");' >");
									elementPaie.append(pe.getNorubr()).append(" ").append(sNomRubrique);
									elementPaie.append("</A>");

									sbRappelsJsSal.append("\n");
									sbRappelsJsSal.append("<TBODY style=\"display: none;\" id=\"smenuS").append(iNoRubrique).append("\">");
									sbRappelsJsSal.append("\n");
									sbRappelsJsSal.append("<TR>");
									sbRappelsJsSal.append("<TD class=\"sigp2-texte-fond3\" colspan=\"5\">").append("&nbsp;&nbsp;").append(paieRubriquedeRappel.getNorubr()).append(" ").append(paieRubriquedeRappel.getLirubr()).append("</TD>");
									sbRappelsJsSal.append("</TR>");

									sbRappelsJsPat.append("\n");
									sbRappelsJsPat.append("<TBODY style=\"display: none;\" id=\"smenuP").append(iNoRubrique).append("\">");
									sbRappelsJsPat.append("\n");
									sbRappelsJsPat.append("<TR>");
									sbRappelsJsPat.append("<TD class=\"sigp2-texte-fond3\" colspan=\"5\">").append("&nbsp;&nbsp;").append(paieRubriquedeRappel.getNorubr()).append(" ").append(paieRubriquedeRappel.getLirubr()).append("</TD>");
									sbRappelsJsPat.append("</TR>");

								}

								//on liste les elements de rappels:
								bAffichRappPat=false;
								bAffichRappSal=false;
								if (a.size() !=0 ) {
									for (java.util.ListIterator<PaieRappel> listrappels = a.listIterator(); listrappels.hasNext(); ) {
										PaieRappel paieRappel=(listrappels.next());

										String sNomRubriqueRappel="";
										StringBuffer nombreTauxRappelSal  = new StringBuffer("");
										StringBuffer nombreTauxRappelPat  = new StringBuffer("");
										StringBuffer baseAssietteRappel = new StringBuffer("");
										StringBuffer aRetenirRappelSal = new StringBuffer("");
										StringBuffer aPayerRappelSal = new StringBuffer("");
										StringBuffer aRetenirRappelPat = new StringBuffer("");
										StringBuffer aPayerRappelPat  = new StringBuffer("");
										int iPSalRap=0;
										int iPPatRap=0;

										sNomRubriqueRappel=Utils.TraiterDateFr(paieRappel.getPerrap());
										if (sMoisRubriqueRappel.length()==0){
											sMoisRubriqueRappel=Utils.TraiterDateFrShort(paieRappel.getPerrap());
										}else if (sMoisRubriqueRappel.length()>0&&sMoisRubriqueRappel.length()<11){
											if(!Utils.TraiterDateFrShort(paieRappel.getPerrap()).equals(sMoisRubriqueRappel))
												sMoisRubriqueRappel=Utils.TraiterDateFrShort(paieRappel.getPerrap())+"/"+sMoisRubriqueRappel;
										}else if (sMoisRubriqueRappel.length()>=19){
												sMoisRubriqueRappel=Utils.TraiterDateFrShort(paieRappel.getPerrap())+"/"+sMoisRubriqueRappel.substring(10,19);
										}
										nombreTauxRappelSal = new StringBuffer(calculerNombreTaux(paieRappel, true));	
										nombreTauxRappelPat= new StringBuffer(calculerNombreTaux(paieRappel, false));

										if (Double.parseDouble(paieRappel.getMtbase())!=0){
											baseAssietteRappel.append(Utils.TraiterNombreIt(paieRappel.getMtbase(),2));
										}

										try{
											iPSalRap=Integer.parseInt(paieRappel.getMtpsal());
										}catch (Exception e){
											logger.severe("ERREUR RAPPEL getMtppat="+paieRappel.getMtpsal());
										}
										if (iPSalRap>0)
											aPayerRappelSal.append(Utils.TraiterNombreIt(paieRappel.getMtpsal()));
										else if (iPSalRap<0)
											aRetenirRappelSal.append(Utils.TraiterNombreIt(paieRappel.getMtpsal().substring(1)));

										try{
											iPPatRap=Integer.parseInt(paieRappel.getMtppat());
										}catch (Exception e){
											logger.severe("ERREUR RAPPEL getMtppat="+paieRappel.getMtppat());
										}
										if (iPPatRap>0)
											aPayerRappelPat.append(Utils.TraiterNombreIt(paieRappel.getMtppat()));
										else if (iPPatRap<0)
											aRetenirRappelPat.append(Utils.TraiterNombreIt(paieRappel.getMtppat().substring(1)));

										if (0!=iPSalRap){
											bAffichRappSal=true;
											sbRappelsJsSal.append("\n");
											sbRappelsJsSal.append("<TR>");
											sbRappelsJsSal.append("<TD class=\"sigp2-texte-fond5\">").append("&nbsp;&nbsp;&nbsp;&nbsp;").append(sNomRubriqueRappel).append("</TD>");
											sbRappelsJsSal.append("<TD class=\"sigp2-texte-fond6\" style=\"text-align:right;\">").append(nombreTauxRappelSal.toString()).append("</TD>");
											sbRappelsJsSal.append("<TD class=\"sigp2-texte-fond5\" style=\"text-align:right;\">").append(baseAssietteRappel.toString()).append("</TD>");
											sbRappelsJsSal.append("<TD class=\"sigp2-texte-fond6\" style=\"text-align:right;\">").append(aRetenirRappelSal.toString()).append("</TD>");
											sbRappelsJsSal.append("<TD class=\"sigp2-texte-fond6\" style=\"text-align:right;\">").append(aPayerRappelSal.toString()).append("</TD>");
											sbRappelsJsSal.append("</TR>");
										}

										if (0!=iPPatRap){
											bAffichRappPat=true;
											sbRappelsJsPat.append("\n");
											sbRappelsJsPat.append("<TR>");
											sbRappelsJsPat.append("<TD class=\"sigp2-texte-fond5\">").append("&nbsp;&nbsp;&nbsp;&nbsp;").append(sNomRubriqueRappel).append("</TD>");
											sbRappelsJsPat.append("<TD class=\"sigp2-texte-fond6\" style=\"text-align:right;\">").append(nombreTauxRappelPat.toString()).append("</TD>");
											sbRappelsJsPat.append("<TD class=\"sigp2-texte-fond5\" style=\"text-align:right;\">").append(baseAssietteRappel.toString()).append("</TD>");
											sbRappelsJsPat.append("<TD class=\"sigp2-texte-fond6\" style=\"text-align:right;\">").append(aRetenirRappelPat.toString()).append("</TD>");
											sbRappelsJsPat.append("<TD class=\"sigp2-texte-fond6\" style=\"text-align:right;\">").append(aPayerRappelPat.toString()).append("</TD>");
											sbRappelsJsPat.append("</TR>");
										}
									}
								}

								sbRappelsJsSal.append("<TR>");
								sbRappelsJsSal.append("<TD class=\"sigp2-texte-fond3\" colspan=\"5\">").append("&nbsp;").append("</TD>");
								sbRappelsJsSal.append("</TR>");
								sbRappelsJsSal.append("</TBODY>");
								if(iNoRubrique>8229||iNoRubrique<8220){
									sbRappelsJsPat.append("<TR>");
									sbRappelsJsPat.append("<TD class=\"sigp2-texte-fond3\" colspan=\"5\">").append("&nbsp;").append("</TD>");
									sbRappelsJsPat.append("</TR>");
									sbRappelsJsPat.append("</TBODY>");
								}

							}
						}

						//les autres cas: juste du texte, pas un lien cliquable
						//Dans le cas où le rappel n'a pas d'associé dans sprapl, le rappel n'est pas un lien:
						//OU Dans le cas où on est en RAPPEL CAFAT
						if(!bAffichRappSal && !bAffichRappPat){
							elementPaie=new StringBuffer("");
							elementPaie.append(pe.getNorubr()).append(" ").append(sNomRubrique);
						}

						if (0!=iPSal){
							sbElementsSal.append("\n");
							sbElementsSal.append("<TR id=\"trS").append(iNoRubrique).append("\">");
							sbElementsSal.append("<TD class=\"sigp2-texte-fond2\">").append(elementPaie.toString().replace('@','S')).append("</TD>");
							sbElementsSal.append("<TD class=\"sigp2-texte-fond1\" style=\"text-align:right;\">").append(nombreTauxSal.toString()).append("</TD>");
							sbElementsSal.append("<TD class=\"sigp2-texte-fond2\" style=\"text-align:right;\">").append(baseAssiette.toString()).append("</TD>");
							sbElementsSal.append("<TD class=\"sigp2-texte-fond1\" style=\"text-align:right;\">").append(aRetenirSal.toString()).append("</TD>");
							sbElementsSal.append("<TD class=\"sigp2-texte-fond1\" style=\"text-align:right;\">").append(aPayerSal.toString()).append("</TD>");
							sbElementsSal.append("</TR>");
							if (bAffichRappSal){
								sbElementsSal.append(sbRappelsJsSal.toString());
							}
							if (sMoisRubriqueRappel.length()>0){
								if (bulletinElemSal.getLiRubr().length()>19)
									bulletinElemSal.setLiRubr(bulletinElemSal.getLiRubr().substring(0, 19)+" "+sMoisRubriqueRappel);
								else
									bulletinElemSal.setLiRubr(bulletinElemSal.getLiRubr()+" "+sMoisRubriqueRappel);
							}
							listeBulletinSalarie.add(bulletinElemSal);
							
							//Calcul des Salaires Bruts
							if (aRetenirSal.length()>0){
								iSalaireBrutARetenir=iSalaireBrutARetenir+iPSal;
							}
							if (aPayerSal.length()>0){
								iSalaireBrutAPayer=iSalaireBrutAPayer+iPSal;
							}
						}

						if (0!=iPPat){
							sbElementsPat.append("\n");
							sbElementsPat.append("<TR id=\"trP").append(iNoRubrique).append("\">");
							sbElementsPat.append("<TD class=\"sigp2-texte-fond2\">").append(elementPaie.toString().replace('@','P')).append("</TD>");
							sbElementsPat.append("<TD class=\"sigp2-texte-fond1\" style=\"text-align:right;\">").append(nombreTauxPat.toString()).append("</TD>");
							sbElementsPat.append("<TD class=\"sigp2-texte-fond2\" style=\"text-align:right;\">").append(baseAssiette.toString()).append("</TD>");
							sbElementsPat.append("<TD class=\"sigp2-texte-fond1\" style=\"text-align:right;\">").append(aRetenirPat.toString()).append("</TD>");
							sbElementsPat.append("<TD class=\"sigp2-texte-fond1\" style=\"text-align:right;\">").append(aPayerPat.toString()).append("</TD>");
							sbElementsPat.append("</TR>");
							if (bAffichRappPat){
								sbElementsPat.append(sbRappelsJsPat.toString());
							}
							if (sMoisRubriqueRappel.length()>0){
								if (bulletinElemPat.getLiRubr().length()>19)
									bulletinElemPat.setLiRubr(bulletinElemPat.getLiRubr().substring(0, 19)+" "+sMoisRubriqueRappel);
								else
									bulletinElemPat.setLiRubr(bulletinElemPat.getLiRubr()+" "+sMoisRubriqueRappel);
							}
							listeBulletinPatronal.add(bulletinElemPat);
						}

						if(!alreadyTreatedCAFATPat8220&&iNoRubrique>=8220&&iNoRubrique<=8229&&iPPat==0){
							elementPaie=new StringBuffer("");
							elementPaie.append(pe.getNorubr()).append(" ").append(sNomRubrique);
							sbRappelsCAFATPat.append("\n");
							sbRappelsCAFATPat.append("<TR>");
							sbRappelsCAFATPat.append("<TD class=\"sigp2-texte-fond3\" style=\"font-style : normal;\">").append(elementPaie.toString()).append("</TD>");
							sbRappelsCAFATPat.append("<TD class=\"sigp2-texte-fond5\" style=\"text-align:right;font-weight: bold;\">").append(nombreTauxPat.toString()).append("</TD>");
							sbRappelsCAFATPat.append("<TD class=\"sigp2-texte-fond6\" style=\"text-align:right;font-weight: bold;\">").append(baseAssiette.toString()).append("</TD>");
							sbRappelsCAFATPat.append("<TD class=\"sigp2-texte-fond5\" style=\"text-align:right;font-weight: bold;\">").append(aRetenirPat.toString()).append("</TD>");
							sbRappelsCAFATPat.append("<TD class=\"sigp2-texte-fond5\" style=\"text-align:right;font-weight: bold;\">").append(aPayerPat.toString()).append("</TD>");
							sbRappelsCAFATPat.append("</TR>");
							if (bAffichRappPat){
								sbRappelsJsPat.append("<TR>");
								sbRappelsJsPat.append("<TD class=\"sigp2-texte-fond3\" colspan=\"5\">").append("&nbsp;").append("</TD>");
								sbRappelsJsPat.append("</TR>");
								sbRappelsCAFATPat.append(sbRappelsJsPat.toString());
								sMoisCAFATPat8220=sMoisRubriqueRappel;
							}
						}
					}
				}
			}


			//SALAIRE BRUT APAYER
			totalPayer=""+iSalaireBrutAPayer;
			addZone(getNOM_ST_TOTAUX_APAYER(),Utils.TraiterNombreIt(totalPayer));
			//SALAIRE BRUT A RETENIR
			totalRetenir=(""+iSalaireBrutARetenir).substring(1);
			addZone(getNOM_ST_TOTAUX_ARETENIR(),Utils.TraiterNombreIt(totalRetenir));

		}catch (Exception e){
			getTransaction().rollbackTransaction();
			logger.severe("Ex ==== "+e);
			e.printStackTrace();
		}

		sbElements.append(sbElementsSal.toString());
		sbElements.append(sbElementsPat.toString());
		sbElements.append("\n");
		sbElements.append("<TR>");
		sbElements.append("<TD colspan=\"5\"></TD>");
		sbElements.append("</TR>");
//		sbElements.append("<BR/>");
		sbElements.append("<TR>");
		sbElements.append("<TD class=\"sigp2-texte-fond1\" colspan=\"2\"></TD>");
		sbElements.append("<TD class=\"sigp2-texte-2\" style=\"font-size : 14px;font-family : Arial;font-weight : bold;text-align : right;\">TOTAUX</TD>");
		sbElements.append("<TD class=\"sigp2-texte-fond1\" style=\"text-align:right;\">").append(getVAL_ST_TOTAUX_ARETENIR()).append("</TD>");
		sbElements.append("<TD class=\"sigp2-texte-fond1\" style=\"text-align:right;\">").append(getVAL_ST_TOTAUX_APAYER()).append("</TD>");
		sbElements.append("</TR>");
		sbElements.append("</TBODY>");
		sbElements.append("</TABLE>");
		sbElements.append("</TD>");
		sbElements.append("</TR>");

		getTransaction().rollbackTransaction();
		return sbElements.toString();
	}

	/**
	 * En fonction du noRubrique retourne la valeur à afficher dans la colonne Taux
	 * @return String
	 */
	private String calculerNombreTaux(String noRubr, String nBre, String TxSal, String TxPat, boolean isTauxSal){
		String NombreouTaux="";
		try {
			int iNoRubrique=Integer.parseInt(noRubr);

			if (isTauxSal){

				//cas où on cherche le taux salarié
				//ici on restreint les conditions du switch qui en a déjà bcp:
				if (iNoRubrique>=8900 && iNoRubrique<=8940)
					iNoRubrique=8900;

				switch (iNoRubrique){

				case 1001:case 1002:case 1003:
					NombreouTaux=Utils.TraiterNombreIt(nBre)+"/30";
					break;

				case 1070: case 1300: case 1350: case 1601: case 1602: case 1603: case 1604:
				case 2000: case 2001: case 2002: case 2003: case 2010: case 2011: case 2012:
				case 2013: case 2014: case 2016: case 5000: case 5010: case 7094: case 7750:
				case 8001: case 8041: case 8052: case 8053: /*case 8091:*/ case 8092: case 8093:
				case 8094: /*case 8095:*/ case 8100: case 8101: case 8102: case 8109: case 8116:
				case 8120: case 8121: case 8122: case 8123: case 8180: case 8190: case 8299:
				case 8300: case 8324: case 8325: case 8332: case 8900: /* de 8900 à 8940 */
					NombreouTaux=Utils.TraiterNombreIt(nBre,2);
					break;

				case 1050: case 1100: case 2400: case 2401: case 2500: case 2501: case 2850:
				case 2901: case 2902: case 2903: case 8220: case 8221: case 8222: case 8223:
				case 3000: case 3500: case 4000: case 8700: case 8701: case 8702: case 8703:
				case 8704: case 8705: case 8706: case 8707: case 8708: case 8011: case 8354:
				case 8711: case 8712: case 8713: case 1030: case 1198: case 2600: case 6000:
				case 7510: case 7520: case 7521: case 7530: case 7547: case 7548: case 7071:
				case 7072: case 7073: case 7074: case 7075: case 7076: case 7077: case 7078:
				case 7079: case 7129: case 8079: case 8129: case 7081: case 7082: case 7084:
				case 7085: case 7086: case 7087: case 7088: case 7089: case 7090: case 7091:
				case 7092: case 7093: case 7097: case 7098: case 7099: case 7580: case 8547:
				case 8548: case 8720: case 8721: case 2801: case 8471: case 7025:
					NombreouTaux=Utils.TraiterNombreIt(""+(Double.parseDouble(TxSal)*100),2);			
					break;

					//Prime d'ancienneté
				case 1200: case 8050: case 8091: case 8095: case 2700:
					NombreouTaux=Utils.TraiterNombreIt(""+(Double.parseDouble(TxSal)*10),2);
					break;
					
					//Prime VIE CHèRE (présentation) 
				case 1250: case 8051:
					NombreouTaux=Utils.TraiterNombreIt(""+(Double.parseDouble(TxSal)*100),2);
					break;
				
				case 1320 :
					//Action #12429 Modifier VISUSAL de la même façon que les bulletins papier
					NombreouTaux= Integer.parseInt(paieEnteteMoisCourant.getPercou()) <201412 ? Utils.TraiterNombreIt(TxSal,2) : Utils.TraiterNombreIt(""+(Double.parseDouble(TxSal)*100),2);
					break;
					
				default :
					NombreouTaux=Utils.TraiterNombreIt(TxSal,2);
					break;
				}



			}else{
				//cas où on cherche le taux patronal
				switch (iNoRubrique){
				case 2700:
					NombreouTaux=Utils.TraiterNombreIt(""+(Double.parseDouble(TxPat)*10),2);
					break;
				case 1040: case 2800: case 2810: case 2811: case 2812: case 2750:
					NombreouTaux=Utils.TraiterNombreIt(TxPat,2);
					break;
				//Pour rubrique 2900 COTISATION CAFAT
				case 2901: case 2902: case 2903: case 2904: case 2905: case 2906: case 2907: case 2908: case 2909:
					NombreouTaux=Utils.TraiterNombreIt(TxPat,4);
					break;
				//Pour rubrique 8220 RAPPEL COTISATION CAFAT
				case 8221: case 8222: case 8223: case 8224: case 8225: case 8226: case 8227: case 8228: case 8229:
					NombreouTaux=Utils.TraiterNombreIt(TxPat,2);
					break;
				default :
					NombreouTaux=Utils.TraiterNombreIt(""+(Double.parseDouble(TxPat)*100),2);
					break;
				}

			}
			
			//Un nombre ou un taux ne peut pas être négatif
			try{
				if (NombreouTaux.startsWith("-")){
					NombreouTaux=NombreouTaux.substring(1);
				}
					/*
				Double dNbreouTaux=Double.parseDouble(NombreouTaux);
				if (dNbreouTaux<0){
					dNbreouTaux=-dNbreouTaux;
				}
				*/
			}catch (Exception ep){
				//on continue le traitement en affichant
			}
			
			if(NombreouTaux.equals("0,00"))
					return "";
			
			return NombreouTaux;
		}catch (Exception e){
			logger.severe("Pb Norubr ="+noRubr+" \nException e="+e);
			return"";
		}
	}
	
	
	/**
	 * 
	 * @param pe pe
	 * @param isTauxSal true si on veut le taux salarié, false si on veut le taux patronal
	 * @return String
	 */
	public String calculerNombreTaux(PaieElement pe, boolean isTauxSal){
		return calculerNombreTaux(pe.getNorubr(), pe.getNb(), pe.getTxsal(), pe.getTxpat(), isTauxSal);
	}
	
	/**
	 * 
	 * @param pr pr
	 * @param isTauxSal  true si on veut le taux salarié, false si on veut le taux patronal
	 * @return String
	 */
	public String calculerNombreTaux(PaieRappel pr, boolean isTauxSal){
		return calculerNombreTaux(pr.getNorubr(), pr.getNb(), pr.getTxsal(), pr.getTxpat(), isTauxSal);
	}
	
	
	/**
	 * Genere le fichier des DATA pour l'affichage dans le PDF.
	 * @throws Exception Exception
	 */
	private void generateCommonsVFSBulletinPDFData() throws Exception{
	
		
		String sPscript="bula.sp";
		String sPdata="bull.dat";
		
		//A partir de 201103 le format du bulletin de salaire change, ajout des Repos Compensateurs sur le bulletin
		try{
			int iPercou=Integer.parseInt(paieEnteteMoisCourant.getPercou());
			if (iPercou>=201103){
				sPscript="bulf.sp";
				sPdata="bull.dat";
			}
		}catch(Exception e){
			sPscript="bula.sp";
			sPdata="bull.dat";
		}
		
		StarjetGenerationVFS g = new StarjetGenerationVFS(getTransaction(), sPscript, sPdata);
		FileObject f = g.getFileData();
		OutputStream output = f.getContent().getOutputStream();
		OutputStreamWriter ouw = new OutputStreamWriter(output, "8859_1");
		BufferedWriter out = new BufferedWriter(ouw);
		try {
			
			/*******************
	  				INFO
			 *******************/
			out.write("0Ce bulletin a été édité par l'application de Visualisation des salaires.\n");

		
			/*******************
			  		ENTETE
			 *******************/
			out.write("1");
			//mois
			out.write(StringUtils.rightPad(Utils.TraiterMoisFr(paieEnteteMoisCourant.getPercou()),9," "));
			//annee
			out.write(StringUtils.leftPad(Utils.TraiterAnneeFr(paieEnteteMoisCourant.getPercou()),4," "));
			//libcat
			out.write(StringUtils.rightPad(paieEnteteMoisCourant.getLicate().trim(),30," "));
			//nomatr
			out.write(StringUtils.leftPad(paieEnteteMoisCourant.getNomatr().trim(),5," "));
			//nom prenom
			out.write(StringUtils.rightPad(paieEnteteMoisCourant.getNom().trim()+" "+paieEnteteMoisCourant.getPrenom().trim(),40," "));
			//grade
			out.write(StringUtils.rightPad(paieEnteteMoisCourant.getLigrad().trim(),43," "));
			//base reglement
			out.write(StringUtils.rightPad(paieEnteteMoisCourant.getLimodr().trim(),15," "));
			//Indice ou Forfait: MtBase
			out.write(StringUtils.leftPad(paieEnteteMoisCourant.getForf().replace(",","").trim(),9," "));
			//pnetan INA
			out.write(StringUtils.leftPad(paieEnteteMoisCourant.getIna(),4," "));
			//plebar IBAN
			String iban="";
			try{
				iban=""+Integer.parseInt(paieEnteteMoisCourant.getIban());
			}catch (Exception ei){
				iban=paieEnteteMoisCourant.getIban();
			}
			out.write(StringUtils.leftPad(iban,7," "));
			//pajore INM
			out.write(StringUtils.leftPad(paieEnteteMoisCourant.getInm(),4," "));
			//pmajor INM
			out.write(StringUtils.leftPad(paieEnteteMoisCourant.getInm(),4," "));
			//numero retraite
			out.write(StringUtils.leftPad(paieEnteteMoisCourant.getNuretr().trim(),8," "));
			//numero mutuelle
			out.write(StringUtils.leftPad(paieEnteteMoisCourant.getNumutu(),15," "));
			//Service
			out.write(StringUtils.rightPad(paieEnteteMoisCourant.getLiserv(),60," "));
			
			//at conges
			out.write(StringUtils.rightPad("01/"+paieEnteteMoisCourant.getPercou().substring(4),5," "));
			//Nb conges
			out.write(StringUtils.leftPad(paieEnteteMoisCourant.getNbcong().replace(",",""),7," "));
			//index de correction paux
			out.write(StringUtils.leftPad(paieEnteteMoisCourant.getIndcor().replace(",",""),5," "));
			//valeur du point en euro pap
			out.write(StringUtils.leftPad(paieEnteteMoisCourant.getVap().replace(",",""),9," "));
			//pase TBA (inm x vap)
			out.write(StringUtils.leftPad(sTBA.replace(",",""),9," "));
			//coeff de majoration pcv
			out.write(StringUtils.leftPad(paieEnteteMoisCourant.getCoef().replace(",",""),5," "));
			//point CAFAT pcv
			out.write(StringUtils.leftPad(paieEnteteMoisCourant.getPtcafat().replace(",",""),5," "));
			//smig
			out.write(StringUtils.leftPad(paieEnteteMoisCourant.getSmig().replace(",",""),5," "));
			//BASE HORAIRE pibhor
			out.write(StringUtils.rightPad(paieEnteteMoisCourant.getLibhor(),30," "));
			//pbecol   liecol
			out.write(StringUtils.rightPad(paieEnteteMoisCourant.getLiecol().trim(),40," "));
			//odreg mode de reglement
			out.write(StringUtils.leftPad(paieEnteteMoisCourant.getModreg(),2," "));
			//ppomp IPOMP (jours de garde pour les pompiers): on n'implï¿½mente pas car ce n'a pas ï¿½tï¿½ mis en production. Affichage serait (Nbrjoursgarde g.)
			out.write(StringUtils.rightPad("",1," "));
			//Repos Compendateurs Solde1 Annï¿½e N (heures)
			out.write(StringUtils.leftPad(sDemiJoursRCN,3," "));
			//Repos Compendateurs Solde2 Annï¿½e N-1 (heures)
			out.write(StringUtils.leftPad(sDemiJoursRCN_1,3," "));

			out.write("\n");

			/*******************
	  			Part salariale
			 *******************/
			if (null!=listeBulletinSalarie&& listeBulletinSalarie.size()>0){
				for (int i=0; i<listeBulletinSalarie.size(); i++){
					out.write("2");
					//norubr
					out.write(StringUtils.rightPad(listeBulletinSalarie.get(i).getNoRubr(),4," "));
					//lirubr
					String lirubr="";
					try{
						lirubr=listeBulletinSalarie.get(i).getLiRubr().substring(0,40);
					}catch (Exception elex){
						lirubr=listeBulletinSalarie.get(i).getLiRubr();
					}
					out.write(StringUtils.rightPad(lirubr.trim(),40," "));
					//nbresal
					out.write(StringUtils.leftPad(listeBulletinSalarie.get(i).getNombre(),7," "));
					//TauxSal
					out.write(StringUtils.leftPad(listeBulletinSalarie.get(i).getTaux(),5," "));
					//MtBase
					out.write(StringUtils.leftPad(listeBulletinSalarie.get(i).getMtBases(),9," "));
					//MtSal
					out.write(StringUtils.leftPad(listeBulletinSalarie.get(i).getMontant(),7," "));
					//Signe
					out.write(StringUtils.rightPad(listeBulletinSalarie.get(i).getSigne(),1," ")); //P=A PAYER    R=A RETENIR
					
					out.write("\n");
				}
			}
			
			/*******************
	  			Part patronale
			 *******************/
			if (null!=listeBulletinPatronal&& listeBulletinPatronal.size()>0){
				for (int i=0; i<listeBulletinPatronal.size(); i++){
					out.write("3");
					//norubr
					out.write(StringUtils.rightPad(listeBulletinPatronal.get(i).getNoRubr(),4," "));
					//lirubr
					String lirubr="";
					try{
						lirubr=listeBulletinPatronal.get(i).getLiRubr().substring(0,40);
					}catch (Exception elex){
						lirubr=listeBulletinPatronal.get(i).getLiRubr();
					}
					out.write(StringUtils.rightPad(lirubr.trim(),40," "));
					//tauxpat
					out.write(StringUtils.leftPad(listeBulletinPatronal.get(i).getTaux(),5," "));
					//MtBase
					out.write(StringUtils.leftPad(listeBulletinPatronal.get(i).getMtBases(),9," "));
					//MtPat
					out.write(StringUtils.leftPad(listeBulletinPatronal.get(i).getMontant(),7," "));
					//Signe
					out.write(StringUtils.rightPad(listeBulletinPatronal.get(i).getSigne(),1," ")); //P=A PAYER    R=A RETENIR
					
					out.write("\n");
				}
			}
			/*******************
	  			Total
			 *******************/
			out.write("4");
			//totret
			out.write(StringUtils.leftPad(totalRetenir,7," "));
			//totpay
			out.write(StringUtils.leftPad(totalPayer,7," "));
			//netimp
			out.write(StringUtils.leftPad(netImposMois,7," "));
			//nonimp
			out.write(StringUtils.leftPad(nonImposMois,7," "));
			//netpay
			out.write(StringUtils.leftPad(netPayer,7," "));
			//netian
			out.write(StringUtils.leftPad(netImposCumul,9," "));
			//nonian
			out.write(StringUtils.leftPad(nonImposCumul,7," "));
			//cdbanq
			out.write(StringUtils.leftPad(paieEnteteMoisCourant.getCdbanq(),5," "));
			//cdguic
			out.write(StringUtils.leftPad(Utils.ConcatDebutValeurStringTaille(paieEnteteMoisCourant.getCdguic(),"0",5),5," "));
			//nocpte
			out.write(StringUtils.leftPad(paieEnteteMoisCourant.getNocpte(),11," "));
			//clerib
			out.write(StringUtils.leftPad(paieEnteteMoisCourant.getClerib(),2," "));
			//banque
			out.write(StringUtils.rightPad(paieEnteteMoisCourant.getLibanq(),29," "));
				
			out.write("\n");
		
			out.flush();
			out.close();
			ouw.close();
		} catch (Exception e) {
			output.close();
			f.close();
			throw e;
		}
			setScript(g.getScriptOuverture());		

	}
	
	public String getScript() {
		if (script == null) script="";
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	public String afficheScript() {	
		String res = new String(getScript());
		setScript(null);
		return res;
	}
}
