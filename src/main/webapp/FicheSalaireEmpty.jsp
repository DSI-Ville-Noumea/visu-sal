<%@page import="nc.mairie.technique.UserAppli"%>
<%@page import="nc.mairie.technique.VariableGlobale"%>
<%@page import="nc.mairie.servlets.Frontale"%>
<BR><BR>
<TABLE width="100%">
<TR><TD class="sigp2-majuscule-titre" style="text-align : center; vertical-align : middle;">
Vous n'avez pas les droits nécessaire pour accéder à  l'application de visualisation des salaires.
<BR>Veuillez contacter la DSI.
<BR><BR>
<% if (null!=VariableGlobale.recuperer(request,VariableGlobale.GLOBAL_USER_APPLI)){ %>
Compte: <FONT color="red"><%=((UserAppli)VariableGlobale.recuperer(request,VariableGlobale.GLOBAL_USER_APPLI)).getUserName() %></FONT>
<% } %>
<BR><BR>
</TD></TR>
	<%@ include file="CartoucheQuit.jsp" %>   
</TABLE>


