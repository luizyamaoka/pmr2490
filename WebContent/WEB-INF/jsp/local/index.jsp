<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <head>
    <meta charset="utf-8">
    <title>Locais</title>
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
 
      <div class="text-center">
        <a href="/pmr2490/locals/new" class="btn btn-success btn-lg">Novo Local</a>
      </div>
    
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
              <c:forEach var="local" items="${locals}">
              <tr>
                <td>${local.id}</td>
                <td>${local.name}</td>
                <td>
                  <form class="crud-buttons" action="locals/${local.id}/destroy" method="post"><input type="submit" value="Deletar" class="btn btn-danger btn-xs" /></form>
                  <a href="locals/${local.id}/edit" class="btn btn-warning btn-xs crud-buttons" role="button">Editar</a>
                  <a href="locals/${local.id}" class="btn btn-info btn-xs crud-buttons" role="button">Visualizar</a>
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