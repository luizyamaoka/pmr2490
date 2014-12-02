<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <head>
    <meta charset="utf-8">
    <title>Usu�rios</title>
    <c:import url="/WEB-INF/jsp/shared/css.jsp" />
    <c:import url="/WEB-INF/jsp/shared/js.jsp" />
    <style>
    .crud-buttons {
      float: right;
      margin: 0 5px;
    }
    </style>
  </head>
  <body>
    <c:import url="/WEB-INF/jsp/shared/header.jsp" />
    
    <div class="container theme-showcase" role="main">
    
    <c:import url="/WEB-INF/jsp/shared/alert.jsp" />
    
      <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8">
          <table class="table">
            <thead>
              <tr>
                <th>Id</th>
                <th>Nome</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="user" items="${users}">
              <tr>
                <td>${user.id}</td>
                <td>${user.firstName} ${user.lastName}</td>
                <td>
                  <form class="crud-buttons" action="users/${user.id}/destroy" method="post"><input type="submit" value="Deletar" class="btn btn-danger btn-xs" /></form>
                  <a href="users/${user.id}/edit" class="btn btn-warning btn-xs crud-buttons" role="button">Editar</a>
                  <a href="users/${user.id}" class="btn btn-info btn-xs crud-buttons" role="button">Visualizar</a>
                </td>
              </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    
    <c:import url="/WEB-INF/jsp/shared/footer.jsp" />
    
  </body>
</html>