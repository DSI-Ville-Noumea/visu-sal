<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="nc.mairie.visusal.servlets.ServletSalRap"%>
<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Page Designer V3.5.3 for Windows">
<META http-equiv="Content-Style-Type" content="text/css">
<%@page contentType="text/html;charset=UTF-8"%> <meta http-equiv="X-UA-Compatible" content="chrome=1">
<TITLE>Visualisation du Salaire</TITLE>
<SCRIPT language="javascript" src="js/GestionBoutonDroit.js"></SCRIPT>
</HEAD>
<%
	//System.out.println("TESTPAGE"+nc.mairie.technique.VariableActivite.ACTIVITE_TEST);
	if (!ServletSalRap.controlerHabilitation(request)) {
	//System.out.println("controlerHabilitation ServletSalRap = false");
		response.setStatus(response.SC_UNAUTHORIZED); 
		response.setHeader("WWW-Authenticate","BASIC realm=\"Habilitation HTTP pour la Mairie\"");
		javax.servlet.ServletContext sc= getServletContext();
		javax.servlet.RequestDispatcher rd = sc.getRequestDispatcher("/ConnectionInsulte.jsp");
		rd.forward(request,response);
	}

%>

<frameset rows="12%, *" frameborder="0" border="0" framespacing="0">
	<FRAME src="SalaireLogo.jsp" name="Logo" scrolling="NO" noresize
			marginwidth="0" marginheight="0">
		<FRAME src="SalaireRappels?ACTIVITE=FicheSalaire" name="Main" marginwidth="0"
			marginheight="0">
	<NOFRAMES>
	<BODY>
	<P>L'affichage de cette page requiert un navigateur prenant en charge les cadres. </P>
	</BODY>
	</NOFRAMES>
</FRAMESET>
</HTML>