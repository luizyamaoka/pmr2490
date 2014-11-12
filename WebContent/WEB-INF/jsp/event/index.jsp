<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <head>
    <meta charset="utf-8">
    <title>Eventos</title>
    <c:import url="/WEB-INF/jsp/shared/css.jsp" />
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
              <c:forEach var="event" items="${events}">
              <tr>
                <td>${event.id}</td>
                <td>${event.name}</td>
                <td>
                  <form class="crud-buttons" action="events/${event.id}/destroy" method="post"><input type="submit" value="Deletar" class="btn btn-danger btn-xs" /></form>
                  <a href="events/${event.id}/edit" class="btn btn-warning btn-xs crud-buttons" role="button">Editar</a>
                  <a href="events/${event.id}" class="btn btn-info btn-xs crud-buttons" role="button">Visualizar</a>
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