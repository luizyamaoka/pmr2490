<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<html>
  <head>
    <meta charset="utf-8">
    <title>Eventos</title>
    <c:import url="/WEB-INF/jsp/shared/css.jsp" />
    <c:import url="/WEB-INF/jsp/shared/js.jsp" />
    <style>
    .event-description {
      text-align: justify; 
      height: 7em; 
      overflow: hidden;
    }
    </style>
  </head>
  <body>
    <c:import url="/WEB-INF/jsp/shared/header.jsp" />
    
    <div class="container theme-showcase" role="main">
    
      <c:if test="${empty events}">
        <div class="jumbotron" style="text-align: center">
          <h3>Nenhum evento encontrado</h3>
        </div>
      </c:if>

	  <c:if test="${not empty events}">
	    <div class="page-header">
		  <h2>Eventos</h2>
	    </div>
	  </c:if>

      <c:import url="/WEB-INF/jsp/event/_events.jsp" />
      
    </div>
       
    <c:import url="/WEB-INF/jsp/shared/footer.jsp" />
    
  </body>
</html>