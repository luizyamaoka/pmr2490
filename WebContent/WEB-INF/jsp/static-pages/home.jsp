<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<html>
  <head>
    <meta charset="utf-8">
    <title>Home page</title>
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
    
    <c:import url="/WEB-INF/jsp/shared/alert.jsp" />

      <!-- Main jumbotron for a primary marketing message or call to action -->
      <div class="jumbotron" style="text-align: center">
        <h1>Bem-vindo ao Poli Cultural</h1>
        
        <a href="/pmr2490/events/search" class="btn btn-success btn-lg" role="button">Procure um evento</a>
        
      </div>
      
      <c:if test="${not empty events}">
        <div class="page-header">
		  <h2>Próximos eventos</h2>
	    </div>
	  </c:if>
	  
	  <c:import url="/WEB-INF/jsp/event/_events.jsp" />
	  
    </div>
    
    <c:import url="/WEB-INF/jsp/shared/footer.jsp" />
    
  </body>
</html>