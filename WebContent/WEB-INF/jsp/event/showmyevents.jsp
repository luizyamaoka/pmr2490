<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<html>
  <head>
    <meta charset="utf-8">
    <title>Meus Eventos</title>
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
    <c:set var="temEvento" scope="session" value="${0}"/>
      <c:if test="${not empty myevents}">
        <div class="page-header" style="text-align: center">
          <h3>Eventos criados por mim</h3>
        </div>
      </c:if>
      <div class="row">
        <c:forEach var="event" items="${myevents}">
          	<div class="col-md-4 col-sm-6">
           	 <div style="border-style: solid; border-width: thin; padding: 15px; margin: 10px;">
           	 <h3 style="text-align: center;">${event.name}</h3>
				<p style="text-align: center;">
				  <span style="font-weight: bold;">Quando? </span>
				  <fmt:formatDate value="${event.dateStart}" pattern="dd/MM/yyyy hh:mm" />
				</p>
       	     <p style="text-align: center;"><span style="font-weight: bold;">Onde? </span>${event.local.name}</p>
       	     <p class="event-description">${event.description}</p>
       	     <div class="text-center">
       	       <a href="/pmr2490/events/${event.id}" class="btn btn-primary btn-sm" role="button">Ver evento</a>
       	       
       	       <c:if test="${event.creator.email == username}">
       	         <a href="/pmr2490/events/${event.id}/edit" class="btn btn-warning btn-sm" role="button">Editar evento</a>
       	       </c:if>
       	     </div>
       	     </div>
       	   </div>
       	   <c:set var="temEvento" scope="session" value="${1}"/>
       	 </c:forEach>
      </div>
      
      <c:if test="${not empty myeventsocorridos}">
        <div class="page-header" style="text-align: center">
          <h3>Meus Eventos já Ocorridos</h3>
        </div>
      </c:if>
      <div class="row">
        <c:forEach var="event" items="${myeventsocorridos}">
          	<div class="col-md-4 col-sm-6">
           	 <div style="border-style: solid; border-width: thin; padding: 15px; margin: 10px;">
           	 <h3 style="text-align: center;">${event.name}</h3>
				<p style="text-align: center;">
				  <span style="font-weight: bold;">Quando? </span>
				  <fmt:formatDate value="${event.dateStart}" pattern="dd/MM/yyyy hh:mm" />
				</p>
       	     <p style="text-align: center;"><span style="font-weight: bold;">Onde? </span>${event.local.name}</p>
       	     <p class="event-description">${event.description}</p>
       	     <div class="text-center">
       	       <a href="/pmr2490/events/${event.id}" class="btn btn-primary btn-sm" role="button">Ver evento</a>
       	       
       	       <c:if test="${event.creator.email == username}">
       	         <a href="/pmr2490/events/${event.id}/edit" class="btn btn-warning btn-sm" role="button">Editar evento</a>
       	       </c:if>
       	     </div>
       	     </div>
       	   </div>
       	   <c:set var="temEvento" scope="session" value="${1}"/>
       	 </c:forEach>
      </div>
      
      <c:if test="${not empty eventsparticipados}">
        <div class="page-header" style="text-align: center">
          <h3>Eventos que estou participando</h3>
        </div>
      </c:if>
      <div class="row">
        <c:forEach var="event" items="${eventsparticipados}">
          	<div class="col-md-4 col-sm-6">
           	 <div style="border-style: solid; border-width: thin; padding: 15px; margin: 10px;">
           	 <h3 style="text-align: center;">${event.name}</h3>
				<p style="text-align: center;">
				  <span style="font-weight: bold;">Quando? </span>
				  <fmt:formatDate value="${event.dateStart}" pattern="dd/MM/yyyy hh:mm" />
				</p>
       	     <p style="text-align: center;"><span style="font-weight: bold;">Onde? </span>${event.local.name}</p>
       	     <p class="event-description">${event.description}</p>
       	     <div class="text-center">
       	       <a href="/pmr2490/events/${event.id}" class="btn btn-primary btn-sm" role="button">Ver evento</a>
       	       
       	       <c:if test="${event.creator.email == username}">
       	         <a href="/pmr2490/events/${event.id}/edit" class="btn btn-warning btn-sm" role="button">Editar evento</a>
       	       </c:if>
       	     </div>
       	     </div>
       	   </div>
       	   <c:set var="temEvento" scope="session" value="${1}"/>
       	 </c:forEach>
      </div>
      
      <c:if test="${not empty eventsparticipadosocorridos}">
        <div class="page-header" style="text-align: center">
          <h3>Eventos Participados já Ocorridos</h3>
        </div>
      </c:if>
      <div class="row">
        <c:forEach var="event" items="${eventsparticipadosocorridos}">
          	<div class="col-md-4 col-sm-6">
           	 <div style="border-style: solid; border-width: thin; padding: 15px; margin: 10px;">
           	 <h3 style="text-align: center;">${event.name}</h3>
				<p style="text-align: center;">
				  <span style="font-weight: bold;">Quando? </span>
				  <fmt:formatDate value="${event.dateStart}" pattern="dd/MM/yyyy hh:mm" />
				</p>
       	     <p style="text-align: center;"><span style="font-weight: bold;">Onde? </span>${event.local.name}</p>
       	     <p class="event-description">${event.description}</p>
       	     <div class="text-center">
       	       <a href="/pmr2490/events/${event.id}" class="btn btn-primary btn-sm" role="button">Ver evento</a>
       	       
       	       <c:if test="${event.creator.email == username}">
       	         <a href="/pmr2490/events/${event.id}/edit" class="btn btn-warning btn-sm" role="button">Editar evento</a>
       	       </c:if>
       	     </div>
       	     </div>
       	   </div>
       	   <c:set var="temEvento" scope="session" value="${1}"/>
       	 </c:forEach>
      </div>
      
      <c:if test="${temEvento==0}">
        <div class="jumbotron" style="text-align: center">
          <h3>Nenhum evento encontrado</h3>
        </div>
    </c:if>
    </div>

      
    
    <c:import url="/WEB-INF/jsp/shared/footer.jsp" />
    
  </body>
</html>