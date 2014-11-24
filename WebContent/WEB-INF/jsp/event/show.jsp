<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
  <head>
    <meta charset="utf-8">
    <title>${event.name}</title>
    <c:import url="/WEB-INF/jsp/shared/css.jsp" />
    <c:import url="/WEB-INF/jsp/shared/js.jsp" />
    <style>
      .bold {
        font-weight: bold;
      }
    </style>
  </head>
  <body>
    <c:import url="/WEB-INF/jsp/shared/header.jsp" />
    
    <div class="container theme-showcase" role="main">

	<h1>${event.name}</h1>
	<h3>
	  <c:forEach var="tagging" items="${event.taggings}">
	    <span class="label label-default">${tagging.tag.name}</span>
	  </c:forEach>
	</h3>

      <div class="row">
        <div class="col-md-6 col-xs-12">
          <h3 style="text-align: center;">Destalhes do evento</h3>
          <p style="text-align: center;">
            <span class="bold">Início </span>
            <fmt:formatDate value="${event.dateStart}" pattern="dd/MM/yyyy hh:mm" />
          </p>
          <c:if test="${!empty event.dateEnd}">
            <p style="text-align: center;">
              <span class="bold">Término </span>
              <fmt:formatDate value="${event.dateEnd}" pattern="dd/MM/yyyy hh:mm" />
            </p>
          </c:if>
          <p style="text-align: center;">
            <span class="bold">Local </span>
            ${event.local.name}
          </p>
          <c:if test="${!empty event.phoneNumber}">
            <p style="text-align: center;">
              <span class="bold">Telefone de contato </span>
              (${event.phoneDdd}) ${event.phoneNumber}
            </p>
          </c:if>
          <p style="text-align: center;">
            <span class="bold">Email de contato </span>
            ${event.email}
          </p>
          <div class="text-center">
            <c:if test="${event.creator.email == username}">
              <a href="/pmr2490/events/${event.id}/edit" class="btn btn-warning btn-sm" role="button">Editar evento</a>
            </c:if>
          </div>
        </div>
        <div class="col-md-6 col-xs-12">
          <c:if test="${!empty event.description}">
            <h3 style="text-align: center;">Descrição</h3>
            <p style="text-align: justify;">
              ${event.description}
            </p>
          </c:if>
        </div>
      </div>
    </div>
    
    <c:import url="/WEB-INF/jsp/shared/footer.jsp" />
    
  </body>
</html>