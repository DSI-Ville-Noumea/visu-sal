<%@page import="nc.mairie.visusal.process.FicheSalaire"%>
<% FicheSalaire cmbp = (FicheSalaire)request.getSession().getAttribute("process"); %>
<TR valign="top">
	<TD>
	<FIELDSET style="text-align : center;">
<SELECT class="sigp2-liste" name="<%= cmbp.getNOM_LB_MOIS_ANCIENNETE() %>" onclick='executeBouton("<%=cmbp.getNOM_PB_MOIS_ANCIENNETE()%>")' size="15" style="width : 100%;">
<%=cmbp.forComboHTML(cmbp.getVAL_LB_MOIS_ANCIENNETE(), cmbp.getVAL_LB_MOIS_ANCIENNETE_SELECT()) %>
</SELECT>
	</FIELDSET>
	<INPUT type="submit" style="visibility : hidden;" name="<%=cmbp.getNOM_PB_MOIS_ANCIENNETE()%>">
	</TD>
</TR>

