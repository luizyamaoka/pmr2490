<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
  <head>
    <meta charset="utf-8">
    <title>${event.name}</title>
    <c:import url="/WEB-INF/jsp/shared/css.jsp" />
    <style>
    .form-signin {
      max-width: 330px;
      padding: 15px;
      margin: 0px auto;
    }
    .form-signin .form-control {
      position: relative;
      height: auto;
      box-sizing: border-box;
      padding: 10px;
      font-size: 16px;
    }
    </style>
  </head>
  <body>
    <c:import url="/WEB-INF/jsp/shared/header.jsp" />
    
    <div class="container theme-showcase" role="main">

	  <form class="form-signin" role="form" action="update" method="post">
        <h2 class="form-signin-heading">Editar evento</h2>
        <input class="form-control" name="name" placeholder="Nome" value="${event.name}" required autofocus type="text">
        <input class="form-control" name="date_start" placeholder="Início" value="<fmt:formatDate value="${event.dateStart}" pattern="yyyyMMdd" />" type="number">
        <input class="form-control" name="hour_start" placeholder="Hora" value="<fmt:formatDate value="${event.dateStart}" pattern="hh" />" type="number" min="0" max="23">
        <input class="form-control" name="minute_start" placeholder="Minuto" value="<fmt:formatDate value="${event.dateStart}" pattern="mm" />" type="number" min="0" max="59">
        <input class="form-control" name="date_end" placeholder="Final" value="<fmt:formatDate value="${event.dateEnd}" pattern="yyyyMMdd" />" type="number">
        <input class="form-control" name="hour_end" placeholder="Hora" value="<fmt:formatDate value="${event.dateEnd}" pattern="hh" />" type="number" min="0" max="23">
        <input class="form-control" name="minute_end" placeholder="Minuto" value="<fmt:formatDate value="${event.dateEnd}" pattern="mm" />" type="number" min="0" max="59">
        <input class="form-control" name="phone_ddd" placeholder="DDD" value="${event.phoneDdd}" type="number" min="1" max="99">
        <input class="form-control" name="phone_number" value="${event.phoneNumber}" placeholder="Telefone" type="number">
        <input class="form-control" name="email" placeholder="Email" value="${event.email}" required type="email">
		<textarea class="form-control" name="description" placeholder="Descrição" rows="3">${event.description}</textarea>
        <select class="form-control" name="local_id" required >
          <option>Local</option>
          <c:forEach var="local" items="${locals}">
            <c:choose>
              <c:when test="${event.local.id == local.id}">
                <option selected value="${local.id}">${local.name}</option>
              </c:when>
              <c:otherwise>
                <option value="${local.id}">${local.name}</option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
		</select> 
        <button class="btn btn-lg btn-primary btn-block" type="submit">Atualizar</button>
      </form>

    </div>
    
    <c:import url="/WEB-INF/jsp/shared/footer.jsp" />
    
  </body>
</html>