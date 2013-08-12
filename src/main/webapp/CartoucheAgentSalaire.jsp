<%@page import="nc.mairie.visusal.process.FicheSalaire"%>
<% FicheSalaire casbp = (FicheSalaire)request.getSession().getAttribute("process"); %>
<TR valign="top">
	<TD>
	<FIELDSET style="text-align : center;">
	<TABLE border="0">
		<TBODY>
			<TR>
				<TD class="sigp2-majuscule-titre" width="46">Agent:</TD>
				<TD align="center" width="103">
<%-- 				<INPUT src="images/jumelle.gif"  name="<%=casbp.getNOM_PB_RECHERCHER()%>" alt="Rechercher Agent" title="Rechercher Agent"> --%>
				<img src="images/jumelle.gif" onclick='executeBouton("<%=casbp.getNOM_PB_RECHERCHER()%>")' style="cursor: pointer;"/>
				<input type='submit' name='<%=casbp.getNOM_PB_RECHERCHER()%>' style='display:none'/>
				</TD>
			</TR>
			<TR>
				<TD colspan="2" class="sigp2-texte-fond1"> <!-- "sigp2"
					style="font-size : 11px;font-family : Arial;font-weight : bold;color : #555555;padding-left:10px;" -->
				<%= casbp.getVAL_ST_AGENT_NOM()%>
				</TD>
			</TR>
			<TR>
				<TD colspan="2" class="sigp2-texte-fond1"> <!-- sigp2"
					style="font-size : 11px;font-family : Arial;font-weight : bold;color : #555555;padding-left:10px;" -->
				<%= casbp.getVAL_ST_AGENT_PRENOM()%>
				</TD>
			</TR>
			<TR>
				<TD colspan="2" class="sigp2-texte-fond1"> <!-- sigp2"
					style="font-size : 11px;font-family : Arial;font-weight : bold;color : #555555;padding-left:10px;" -->
				<%= casbp.getVAL_ST_AGENT_MATRICULE()%>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
	</FIELDSET>
	</TD>
</TR>

