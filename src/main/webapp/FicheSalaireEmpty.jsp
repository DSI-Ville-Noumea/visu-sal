<BR><BR>
<TABLE width="100%">
<TR><TD class="sigp2-majuscule-titre" style="text-align : center; vertical-align : middle;">
Vous n'avez pas les droits nécessaire pour accéder à l'application de visualisation des salaires.
<BR>Veuillez contacter la DSI.
<BR><BR>
<% if (null!=aUserAppli){ %>
Compte: <FONT color="red"><%=aUserAppli.getUserName() %></FONT>
<% } %>
<BR><BR>
</TD></TR>
	<%@ include file="CartoucheQuit.jsp" %>   
</TABLE>


