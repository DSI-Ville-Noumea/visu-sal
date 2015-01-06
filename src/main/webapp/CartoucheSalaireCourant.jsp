
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="nc.mairie.visusal.process.FicheSalaire"%>
<% FicheSalaire cscbp = (FicheSalaire)request.getSession().getAttribute("process"); %>
<LINK href="theme/sigp2.css" rel="stylesheet" type="text/css">
<TR><TD width="100%">
	<FIELDSET style="text-align : center;">
	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
		<TBODY>
			<TR>
				<TD valign="top" width="49%">
				<div class="libellefondu">
				<TABLE border="0" align="left" width="100%">
					<TBODY>
						<TR>
							<TD class="sigp2-majuscule-titre" colspan="4" width="55%">CATEGORIE	ADMINISTRATIVE</TD>
							<TD width="45%"></TD>
						</TR>
						<TR>
							<TD class="sigp2-texte-fond1" colspan="5" width="100%"><%= cscbp.getVAL_ST_CATEGORIE_ADMINISTRATIVE() %></TD>
						</TR>
						<TR>
							<TD class="sigp2-majuscule-titre" width="15%">GRADE</TD>
							<TD class="sigp2-texte-fond1" colspan="4" width="85%"><%= cscbp.getVAL_ST_GRADE() %></TD>
						</TR>
						<TR>
							<TD class="sigp2-texte-fond1" colspan="5" width="100%"><%= cscbp.getVAL_ST_GRADE2() %></TD>
						</TR>
						<TR>
							<TD class="sigp2-majuscule-titre" colspan="3" width="35%">INDICES OU FORFAIT</TD>
							<TD class="sigp2-texte-fond1" colspan="2" width="65%"><%= cscbp.getVAL_ST_INDICES() %></TD>
						</TR>
						<TR>
							<TD class="sigp2-majuscule-titre" colspan="3" width="35%">BASE DE REGLEMENT</TD>
							<TD class="sigp2-texte-fond1" colspan="2" width="65%"><%= cscbp.getVAL_ST_BASE_REGLEMENT() %></TD>
						</TR>
						<TR>
							<TD class="sigp2-majuscule-titre" colspan="2" width="25%">BASE HORAIRE</TD>
							<TD class="sigp2-texte-fond1" colspan="3" width="75%"><%= cscbp.getVAL_ST_BASE_HORAIRE() %></TD>
						</TR>
						<TR>
							<TD class="sigp2-majuscule-titre" colspan="4" width="55%">DROITS A CONGES ANNUELS AU</TD>
							<TD class="sigp2-texte-fond1" width="45%"><%= cscbp.getVAL_ST_DROITS_CONGES() %></TD>
						</TR>
						<TR>
							<TD class="sigp2-majuscule-titre" colspan="2" width="25%">DROITS A RC</TD>
							<TD class="sigp2-texte-fond1" colspan="3" width="75%">N: <%= cscbp.getVAL_ST_DROITS_RC_N() %>&nbsp;&nbsp;&nbsp;&nbsp;N-1: <%= cscbp.getVAL_ST_DROITS_RC_N_1() %></TD>
						</TR>
					</TBODY>
				</TABLE>
				</div>
				</TD>
				<TD width="2%"></TD>
				<TD valign="top" width="49%">
				<TABLE border="0" align="left" width="100%">
					<TBODY>
						<TR>
							<TD class="sigp2-majuscule-titre" colspan="2" width="21%">MATRICULE</TD>
							<TD class="sigp2-texte-fond1" width="21%"><%= cscbp.getVAL_ST_MATRICULE() %></TD>
							<TD class="sigp2-majuscule-titre" width="21%">PERIODE</TD>
							<TD class="sigp2-texte-fond1" width="37%"><%= cscbp.getVAL_ST_PERIODE() %></TD>
						</TR>
						<TR>
							<TD class="sigp2-majuscule-titre" colspan="4" width="63%">NOM/PRENOMS</TD>
							<TD width="37%"></TD>
						</TR>
						<TR>
							<TD class="sigp2-texte-fond1" colspan="5" width="100%"><%= cscbp.getVAL_ST_NOM() %></TD>
						</TR>
						<TR>
							<TD class="sigp2-majuscule-titre" colspan="2" width="21%">N° RETRAITE</TD>
							<TD class="sigp2-texte-fond1" width="21%"><%= cscbp.getVAL_ST_NUM_RETRAITE() %></TD>
							<TD class="sigp2-majuscule-titre" width="21%">N° MUTUELLE</TD>
							<TD class="sigp2-texte-fond1" width="37%"><%= cscbp.getVAL_ST_NUM_MUTUELLE() %></TD>
						</TR>
						<TR>
							<TD class="sigp2-majuscule-titre" width="16%">SERVICE</TD>
							<TD width="5%"></TD>
							<TD width="21%"></TD>
							<TD width="21%"></TD>
							<TD width="37%"></TD>
						</TR>
						<TR>
							<TD class="sigp2-texte-fond1" colspan="5" width="100%"><%= cscbp.getVAL_ST_SERVICE() %></TD>
						</TR>
						<TR>
							<TD class="sigp2-texte-fond1" colspan="5" width="100%"><%= cscbp.getVAL_ST_ECOL() %></TD>
						</TR>
						<TR>
							<TD colspan="5" width="100%"></TD>
						</TR>
					</TBODY>
				</TABLE>
		</TD>
		</TR>
		</TBODY>
	</TABLE>

<TABLE border="0" width="100%">
	<TBODY>
		<TR>
			<TD class="sigp2-majuscule-titre" style="text-align: center;">INDEX DE CORRECTION</TD>
			<TD class="sigp2-majuscule-titre" style="text-align: center;">VALEUR DU POINT EN EURO</TD>
			<TD class="sigp2-majuscule-titre" style="text-align: center;">TBA EN EURO</TD>
			<TD class="sigp2-majuscule-titre" style="text-align: center;">COEFFICIENT DE MAJORATION</TD>
			<TD class="sigp2-majuscule-titre" style="text-align: center;">POINT CAFAT</TD>
			<TD class="sigp2-majuscule-titre" style="text-align: center;">SMIG</TD>
		</TR>
		<TR>
			<TD class="sigp2-texte-fond1" style="text-align: center;"><%= cscbp.getVAL_ST_INDEX_CORRECTION() %></TD>
			<TD class="sigp2-texte-fond1" style="text-align: center;"><%= cscbp.getVAL_ST_VALEUR_POINT() %></TD>
			<TD class="sigp2-texte-fond1" style="text-align: center;"><%= cscbp.getVAL_ST_TBA() %></TD>
			<TD class="sigp2-texte-fond1" style="text-align: center;"><%= cscbp.getVAL_ST_COEFF_MAJORATION() %></TD>
			<TD class="sigp2-texte-fond1" style="text-align: center;"><%= cscbp.getVAL_ST_POINT_CAFAT() %></TD>
			<TD class="sigp2-texte-fond1" style="text-align: center;"><%= cscbp.getVAL_ST_SMIG() %></TD>
		</TR>
	</TBODY>
</TABLE>
	</FIELDSET>
	</TD>
</TR>
<TR><TD width="100%">
<%= cscbp.getElementPaieFormatee() %>
</TD></TR>
<TR><TD width="100%">
	<FIELDSET style="text-align : center;">
<TABLE border="0" width="100%">
	<TBODY>
		<TR>
			<TD></TD>
			<TD class="sigp2-majuscule-titre" style="text-align: center;" colspan="2">NET IMPOSABLE</TD>
			<TD class="sigp2-majuscule-titre" style="text-align: center;">NON IMPOSABLE</TD>
			<TD></TD>
			<TD class="sigp2-majuscule-titre" style="text-align: center;" colspan="2">NET A PAYER</TD></TR>
		<TR>
			<TD class="sigp2-majuscule-titre" style="text-align: right;">MOIS EN COURS</TD>
			<TD class="sigp2-texte-fond1" style="text-align:right;" colspan="2"><%= cscbp.getVAL_ST_NET_IMPOS_MOIS() %></TD>
			<TD class="sigp2-texte-fond1" style="text-align:right;"><%= cscbp.getVAL_ST_NET_NONIMPOS_MOIS() %></TD>
			<TD></TD>
			<TD class="sigp2-texte-fond1" style="text-align:right; font-weight:bold;" colspan="2" rowspan="2"><%= cscbp.getVAL_ST_NET_XPF() %> XPF</TD></TR>
		<TR>
			<TD class="sigp2-majuscule-titre" style="text-align: right;">CUMULS</TD>
			<TD class="sigp2-texte-fond1" style="text-align:right;" colspan="2"><%= cscbp.getVAL_ST_NET_IMPOS_CUMULS() %></TD>
			<TD class="sigp2-texte-fond1" style="text-align:right;"><%= cscbp.getVAL_ST_NET_NONIMPOS_CUMULS() %></TD>
			<TD></TD>
		<TR>
			<TD class="sigp2-majuscule-titre" colspan="2">N° DE COMPTE BANCAIRE</TD>
			<TD class="sigp2-majuscule-titre" colspan="3">DOMICILIATION</TD>
			<TD class="sigp2-texte-fond1" style="text-align:right;" colspan="2" rowspan="2">SOIT: <%= cscbp.getVAL_ST_NET_EUROS() %> &euro;</TD></TR>
		<TR>
			<TD class="sigp2-texte-fond1" colspan="2"><%= cscbp.getVAL_ST_NUM_COMPTE_BANCAIRE() %></TD>
			<TD class="sigp2-texte-fond1" colspan="3"><%= cscbp.getVAL_ST_DOMICILIATION() %></TD>
		</TR>
	</TBODY>
</TABLE>
</FIELDSET></TD></TR>
<TR><TD>
</TD></TR>