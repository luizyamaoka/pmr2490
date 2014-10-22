<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <head>
    <meta charset="utf-8">
    <title>Usuários</title>
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
    .crud-buttons {
      float: right;
      margin: 0 5px;
    }
    </style>
  </head>
  <body>
    <c:import url="/WEB-INF/jsp/shared/header.jsp" />
    
    <div class="container theme-showcase" role="main">
    
      <form class="form-signin" role="form" action="users/create" method="post">
        <h2 class="form-signin-heading">Adicionar usuário</h2>
        <input class="form-control" name="name" placeholder="Nome" required autofocus type="text">
        <button class="btn btn-lg btn-primary btn-block" type="submit">Adicionar</button>
      </form>
    

      <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
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
                <td>${user.name}</td>
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