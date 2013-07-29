<!-- Sample JSP file --> <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Page Designer V3.5.3 for Windows">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/sigp2.css" rel="stylesheet" type="text/css">
<TITLE>Sélection d'un agent</TITLE>

<!-- SCRIPT language="javascript" src="js/GestionBoutonDroit.js"></SCRIPT -->

<SCRIPT language="JavaScript">
//afin de sélectionner un élément dans une liste
function executeBouton(nom)
{
document.formu.elements[nom].click();
}

// afin de mettre le focus sur une zone précise
function setfocus(nom)
{
if (document.formu.elements[nom] != null)
document.formu.elements[nom].focus();
}
 function showGif() 
  { 
  document.getElementById("contentrech").style.display="none"; 
  document.getElementById("banerr").style.display="none"; 
  document.getElementById("giffume").style.display="block"; 
  }
function sub()
{
var frm=document.forms.item(1);
frm.BUTTON_ACTION.value ='OK';
frm.submit();
}

</SCRIPT>
<%@page contentType="text/html;charset=UTF-8"%> <meta http-equiv="X-UA-Compatible" content="chrome=1">
</HEAD>
<jsp:useBean class="nc.mairie.visusal.process.OeAGENTSelection" id="process" scope="session"></jsp:useBean>
<BODY bgcolor="#ffffff" background="images/fond_menu.jpg" text="#000000" style="cursor : auto;" onload="return setfocus('<%=process.getFocus() %>')"><BASEFONT FACE="Arial" SIZE=2> 
<TABLE border="0" width="580" style="text-align : center;" align="center">
  <TBODY align="center">
  <TBODY id="banerr">
  <%@ include file="BanniereErreur.jsp" %>
  </TBODY>
    <TBODY style="display:none;" id="giffume">
	<TR><TD>&nbsp;</TD></TR>
    <TR>
	    <TD style="text-align : center;">
			<img align="middle" src="images/souris.gif">
		</TD>
	</TR>
	<TR>
	    <TD style="text-align:center;font-weight:bold;color:#555555;">
			Recherche en cours...
		</TD>
	</TR>
	</TBODY>
    <TR id="contentrech">
      <TD style="text-align : center;">
      <FORM name="formu" method="POST" onsubmit="showGif();">
      <TABLE width="560" border="0" cellpadding="0" cellspacing="0" align="center">
        <TBODY>
          <TR>
            <TD class="sigp2-titre" style="text-align : center;">Sélection d'un agent<BR>
            <BR>
            </TD>
          </TR>
          <TR>
            <TD>
            <FIELDSET style="text-align : center;"><TABLE border="0" cellpadding="0" cellspacing="0">
              <TBODY>
                <TR>
                  <TD class="sigp2" valign="top"><INPUT
										type="radio" align="bottom"
										<%= process.forRadioHTML(process.getNOM_RG_RECHERCHE(),process.getNOM_RB_RECH_NOM())%>> </TD>
									<TD class="sigp2" width="200" valign="top">Matricule ou début du nom de l'agent</TD>
									<TD rowspan="2"><INPUT size="1" type="text" class="sigp2-saisie" maxlength="1" name="ZoneTampon" style="display : none;">
<INPUT class="sigp2-saisie" maxlength="60" name="<%= process.getNOM_EF_NOM_AGENT() %>" size="20" type="text" value="<%= process.getVAL_EF_NOM_AGENT() %>">
</TD>
                  <TD rowspan="2"><INPUT type="submit" value="Rechercher" class="sigp2-Bouton-100" name="<%=process.getNOM_PB_RECHERCHER()%>" accesskey="R"></TD>
                </TR>
								<TR>
									<TD class="sigp2"><INPUT type="radio"
										<%= process.forRadioHTML(process.getNOM_RG_RECHERCHE(),process.getNOM_RB_RECH_PRENOM())%>></TD>
									<TD class="sigp2" width="180">Début du prénom de l'agent</TD>
								</TR>
							</TBODY>
            </TABLE></FIELDSET>
            </TD>
          </TR>
          <TR>
            <TD><BR>
            </TD>
          </TR>
          <TR>
            <TD>
            <FIELDSET style="text-align : center;"><INPUT type="submit"
							style="visibility : hidden;"
							name="<%=process.getNOM_PB_TRI()%>" value="TRI"><BR>
            <TABLE border="0" cellpadding="0" cellspacing="0">
              <TBODY>
                <TR>
                  <TD style="text-align : center;" width="500">
                  <TABLE border="0" class="sigp2" cellpadding="0" cellspacing="0" width="100%">
                    <TBODY>
                      <TR class="sigp2">
                        <TD style="text-align : center;"><INPUT
													type="radio" onclick='executeBouton("<%=process.getNOM_PB_TRI() %>")'
													<%= process.forRadioHTML(process.getNOM_RG_TRI(),process.getNOM_RB_TRI_NOMATR())%>></TD>
												<TD width="47" class="sigp2-titre-liste">Matr</TD>
												<TD style="text-align : center;"><INPUT type="radio" onclick='executeBouton("<%=process.getNOM_PB_TRI() %>")'
													<%= process.forRadioHTML(process.getNOM_RG_TRI(),process.getNOM_RB_TRI_NOM())%>></TD>
												<TD width="290" class="sigp2-titre-liste">Nom d'usage</TD>
												<TD><INPUT type="radio" onclick='executeBouton("<%=process.getNOM_PB_TRI() %>")'
													<%= process.forRadioHTML(process.getNOM_RG_TRI(),process.getNOM_RB_TRI_PRENOM())%>></TD>
												<TD width="164" class="sigp2-titre-liste">Prénom</TD>
                      </TR>
                    </TBODY>
            </TABLE>

<SELECT class="sigp2-liste" name="<%= process.getNOM_LB_AGENT() %>" ondblclick='executeBouton("<%=process.getNOM_PB_OK()%>")' size="10" style="width : 100%;">
<%=process.forComboHTML(process.getVAL_LB_AGENT(), process.getVAL_LB_AGENT_SELECT()) %>
                 </TD>
                </TR>
              </TBODY>
            </TABLE>
            <BR>
            <TABLE border="0" style="text-align : center;" cellpadding="0" cellspacing="0">
              <TBODY>
                <TR>
                  <TD>
                  	<INPUT type="submit" value="OK" class="sigp2-Bouton-100" name="<%=process.getNOM_PB_OK()%>">
                  </TD>
                  <TD width="51"></TD>
                  <TD width="61"><INPUT type="submit" value="Annuler" class="sigp2-Bouton-100" accesskey="A" name="<%=process.getNOM_PB_ANNULER()%>"></TD>
                </TR>
              </TBODY>
            </TABLE>
            <BR>
            </FIELDSET>
            </TD>
          </TR>
        </TBODY>
      </TABLE>
      <INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
      </TD>
    </TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>