<TR valign="top">
	<TD>
	<FIELDSET style="text-align : center;">
<SELECT class="sigp2-liste" name="<%= process.getNOM_LB_MOIS_ANCIENNETE() %>" onclick='executeBouton("<%=process.getNOM_PB_MOIS_ANCIENNETE()%>")' size="15" style="width : 100%;">
<%=process.forComboHTML(process.getVAL_LB_MOIS_ANCIENNETE(), process.getVAL_LB_MOIS_ANCIENNETE_SELECT()) %>
</SELECT>
	</FIELDSET>
	</TD>
<INPUT type="submit" style="visibility : hidden;" name="<%=process.getNOM_PB_MOIS_ANCIENNETE()%>">
</TR>

