<%@page import="nc.mairie.visusal.process.FicheSalaire"%>
<% FicheSalaire cpbp = (FicheSalaire)request.getSession().getAttribute("process"); %>
<TR valign="top">
	<TD>
	<FIELDSET style="text-align : center;">
	<INPUT type="image"	src="images/print.gif" name="<%= cpbp.getNOM_PB_IMPRIMER() %>" alt="Imprimer" title="Imprimer">
	</FIELDSET>
	</TD>
</TR>

