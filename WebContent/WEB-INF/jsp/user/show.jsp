<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
  <head>
    <meta charset="utf-8">
    <title>${user.firstName}</title>
    <c:import url="/WEB-INF/jsp/shared/css.jsp" />
    <c:import url="/WEB-INF/jsp/shared/js.jsp" />
  </head>
  <body>
    <c:import url="/WEB-INF/jsp/shared/header.jsp" />
    
    <div class="container theme-showcase" role="main">

	  <c:import url="/WEB-INF/jsp/shared/alert.jsp" />

      <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
          <table class="table">
            <tbody>
              <tr>
                <td style="font-weight: bold;">Id</td>
                <td>${user.id}</td>
              </tr>
              <tr>
                <td style="font-weight: bold;">Nome</td>
                <td>${user.firstName} ${user.lastName}</td>
              </tr>
              <tr>
                <td style="font-weight: bold;">Data de nascimento</td>
                <td><fmt:formatDate value="${user.birthDate}" pattern="dd/MM/yyyy" /></td>
              </tr>
              <tr>
                <td style="font-weight: bold;">Sexo</td>
                <td>${user.genre}</td>
              </tr>
              <tr>
                <td style="font-weight: bold;">Telefone</td>
                <td>(${user.phoneDdd}) ${user.phoneNumber}</td>
              </tr>
              <tr>
                <td style="font-weight: bold;">Email</td>
                <td>${user.email}</td>
              </tr>
              <tr>
                <td style="font-weight: bold;">Faculdade</td>
                <td>${user.college.name}</td>
              </tr>
              <tr>
                <td style="font-weight: bold;">Ocupação</td>
                <td>${user.profession.name}</td>
              </tr>
              
            </tbody>
          </table>
          <div>
            <a href="/pmr2490/users/${user.id}/edit" class="btn btn-large btn-warning center-block">Editar perfil</a>
          </div>
        </div>
      </div>
    </div>
    
    <c:import url="/WEB-INF/jsp/shared/footer.jsp" />
    
  </body>
</html>