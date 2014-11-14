<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <head>
    <meta charset="utf-8">
    <title>${user.firstName}</title>
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

	  <form class="form-signin" role="form" action="update" method="post">
        <h2 class="form-signin-heading">Editar usuário</h2>
        <input class="form-control" name="first_name" placeholder="Nome" value="${user.firstName}" required autofocus type="text">
        <input class="form-control" name="last_name" placeholder="Sobrenome" value="${user.lastName}" required type="text">
        <input class="form-control" name="birth_day" placeholder="Dia" type="number" value="${user.birthDate.date}" min="1" max="31">
        <input class="form-control" name="birth_month" placeholder="Mês" type="number" value="${user.birthDate.month+1}" min="1" max="12">
        <input class="form-control" name="birth_year" placeholder="Ano" type="number" value="${1900+user.birthDate.year}" min="1900" max="2100">
        <select class="form-control" name="genre">
          <option>Sexo</option>
          <c:if test="${user.genre == 'm'}">
		  	<option value="m" selected>Masculino</option>
		  	<option value="f">Feminino</option>
		  </c:if>
		  <c:if test="${user.genre == 'f'}">
		  	<option value="m">Masculino</option>
		  	<option value="f" selected>Feminino</option>
		  </c:if>
		</select> 
        <input class="form-control" name="phone_ddd" placeholder="DDD" type="number" value="${user.phoneDdd}" min="1" max="99">
        <input class="form-control" name="phone_number" placeholder="Telefone" value="${user.phoneNumber}" type="number">
        <input class="form-control" name="email" placeholder="Email" value="${user.email}" required type="email">
        <select class="form-control" name="profession_id" required >
          <option>Ocupação</option>
          <c:forEach var="profession" items="${professions}">
            <c:choose>
              <c:when test="${user.profession.id == profession.id}">
                <option selected value="${profession.id}">${profession.name}</option>
              </c:when>
              <c:otherwise>
                <option value="${profession.id}">${profession.name}</option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
		</select> 
		<select class="form-control" name="college_id">
          <option selected>Faculdade</option>
          <c:forEach var="college" items="${colleges}">
            <c:choose>
              <c:when test="${user.college.id == college.id}">
                <option selected value="${college.id}">${college.name}</option>
              </c:when>
              <c:otherwise>
                <option value="${college.id}">${college.name}</option>
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