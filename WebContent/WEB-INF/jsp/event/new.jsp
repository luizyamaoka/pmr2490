<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <head>
    <meta charset="utf-8">
    <title>Novo evento</title>
    <c:import url="/WEB-INF/jsp/shared/css.jsp" />
    <c:import url="/WEB-INF/jsp/shared/js.jsp" />
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
    
      <form class="form-signin" role="form" action="create" method="post">
        <h2 class="form-signin-heading">Adicionar evento</h2>
        <input class="form-control" name="name" placeholder="Nome" required autofocus type="text">
        <input class="form-control" name="date_start" placeholder="Início" type="number">
        <input class="form-control" name="hour_start" placeholder="Hora" type="number" min="0" max="23">
        <input class="form-control" name="minute_start" placeholder="Minuto" type="number" min="0" max="59">
        <input class="form-control" name="date_end" placeholder="Final" type="number">
        <input class="form-control" name="hour_end" placeholder="Hora" type="number" min="0" max="23">
        <input class="form-control" name="minute_end" placeholder="Minuto" type="number" min="0" max="59">
        <input class="form-control" name="phone_ddd" placeholder="DDD" type="number" min="1" max="99">
        <input class="form-control" name="phone_number" placeholder="Telefone" type="number">
        <input class="form-control" name="email" placeholder="Email" required type="email">
		<textarea class="form-control" name="description" placeholder="Descrição" rows="3"></textarea>
        <select class="form-control" name="local_id" required >
          <option value="0" selected>Local</option>
          <c:forEach var="local" items="${locals}">
            <option value="${local.id}">${local.name}</option>
          </c:forEach>
		</select> 
		<c:forEach var="tag" items="${tags}">
		  <label class="checkbox">
			<input type="checkbox" name="tag_ids[]" value="${tag.id}"> ${tag.name}
	      </label>
		</c:forEach>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Adicionar</button>
      </form>

    </div>
    
    <c:import url="/WEB-INF/jsp/shared/footer.jsp" />
    
  </body>
</html>