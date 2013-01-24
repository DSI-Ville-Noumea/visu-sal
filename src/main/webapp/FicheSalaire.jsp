<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Page Designer V3.5.3 for Windows">
<META http-equiv="Content-Style-Type" content="text/css">
<TITLE>Main</TITLE>
<SCRIPT language="javascript" src="js/GestionBoutonDroit.js"></SCRIPT>
<SCRIPT language="javascript" src="js/GestionMenu.js"> </SCRIPT>
<SCRIPT language="JavaScript">
//afin de sélectionner un élément dans une liste
function executeBouton(nom)
{
document.leForm.elements[nom].click();
}

// afin de mettre le focus sur une zone précise
function setfocus(nom)
{
if (document.formu.elements[nom] != null)
document.leForm.elements[nom].focus();
}

function prpage(nom){
 
features = 
   'toolbar=no,location=no,directories=no,status=no,menubar=no,' +
   'scrollbars=yes,resizable=yes,width=1024,height=768,screenX=0,screenY=0,left=0,top=0' ;

window.open(nom,"impression",features); 
}

</SCRIPT>
<LINK href="theme/sigp2.css" rel="stylesheet" type="text/css">
</HEAD>
<jsp:useBean class="nc.mairie.visusal.process.FicheSalaire" id="process" scope="session"></jsp:useBean>
<BODY bgcolor="#ffffff" background="images/fond_menu.jpg" text="#000000" onload="" style="cursor : auto;"><BASEFONT FACE="Arial" SIZE=2> 
    <%@ include file="BanniereErreur.jsp" %>
<nobr>
<FORM name="leForm" method="POST" target="Main" action="SalaireRappelsServlet">
<TABLE>
<TR valign="top"><TD width="20%">
<TABLE width="100%">
<%  
	nc.mairie.technique.UserAppli aUserAppli = (nc.mairie.technique.UserAppli)nc.mairie.technique.VariableGlobale.recuperer(request,nc.mairie.technique.VariableGlobale.GLOBAL_USER_APPLI);
	
	java.util.ArrayList listeDroits = aUserAppli.getListeDroits();
	
if (null!=listeDroits&&listeDroits.size()>0&&listeDroits.contains("VisuDesSalaires")){ %>
<%@ include file="CartoucheAgentSalaire.jsp" %>
<% if (null!=nc.mairie.technique.VariableGlobale.recuperer(request, nc.mairie.technique.VariableGlobale.GLOBAL_AGENT_MAIRIE)) { %>
	<%@ include file="CartoucheMois.jsp" %>
	<% if (null!=nc.mairie.technique.VariableGlobale.recuperer(request, nc.mairie.visusal.process.FicheSalaire.MOIS_COURANT)) { %>
	<%@ include file="CartouchePrint.jsp" %>
<% }  } %>
	<%@ include file="CartoucheQuit.jsp" %>
</TABLE>
</TD><TD width="780">
<TABLE width="100%">
<% if (null!=nc.mairie.technique.VariableGlobale.recuperer(request, nc.mairie.visusal.process.FicheSalaire.MOIS_COURANT)) { %>
	<%@ include file="CartoucheSalaireCourant.jsp" %>
<% } } else { %>
<%@ include file="FicheSalaireEmpty.jsp" %>    
<% } %>
</TABLE>
</TD></TR>
</TABLE>
<BR>
<BR>
      <INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>">
</FORM>
</BODY>
</HTML>
<%=process.afficheScript() %>