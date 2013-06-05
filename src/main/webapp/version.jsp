<%@page import="java.net.InetAddress"%>
${artifactId}.version=${version}
${artifactId}.localhost.hostaddress=<%=InetAddress.getLocalHost().getHostAddress() %>
${artifactId}.localhost.canonicalhostname=<%=InetAddress.getLocalHost().getCanonicalHostName() %>
${artifactId}.localhost.hostname=<%=InetAddress.getLocalHost().getHostName() %>
<% 
HttpSession theSession = request.getSession( false );

// print out the session id
try {
	if( theSession != null ) {
	  //pw.println( "<BR>Session Id: " + theSession.getId() );
	  synchronized( theSession ) {
	    // invalidating a session destroys it
	    theSession.invalidate();
	    //pw.println( "<BR>Session destroyed" );
	  }
	}
} catch (Exception e) {
//Bhen on s'en fou
}
%>
	