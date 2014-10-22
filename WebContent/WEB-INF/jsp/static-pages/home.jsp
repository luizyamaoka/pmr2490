<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <head>
    <meta charset="utf-8">
    <title>Home page</title>
    <c:import url="/WEB-INF/jsp/shared/css.jsp" />
  </head>
  <body>
    <c:import url="/WEB-INF/jsp/shared/header.jsp" />
    
    <div class="container theme-showcase" role="main">

      <!-- Main jumbotron for a primary marketing message or call to action -->
      <div class="jumbotron">
        <h1>Eventos Poli USP</h1>
        <p>Esta é a página inicial do projeto de SI PMR2490.</p>
        <p>Gerenciador de eventos da Escola Politécnica da Universidade de São Paulo</p>
        <p><a href="users" class="btn btn-primary btn-lg" role="button">User</a></p>
      </div>
    </div>
    
    <c:import url="/WEB-INF/jsp/shared/footer.jsp" />
    
  </body>
</html>