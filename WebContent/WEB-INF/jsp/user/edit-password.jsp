<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <head>
    <meta charset="utf-8">
    <title>Alterar senha</title>
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

	  <c:import url="/WEB-INF/jsp/shared/alert.jsp" />

	  <form class="form-signin" role="form" action="update-password" method="post">
        <h2 class="form-signin-heading">Alterar senha</h2>
        <input class="form-control" name="password" placeholder="Senha" required type="password">
        <input class="form-control" name="new_password" placeholder="Nova senha" required type="password">
        <input class="form-control" name="new_password_confirmation" placeholder="Confirmação da nova senha" required type="password">
        <button class="btn btn-lg btn-primary btn-block" type="submit">Atualizar</button>
      </form>

    </div>
    
    <c:import url="/WEB-INF/jsp/shared/footer.jsp" />
    
  </body>
</html>