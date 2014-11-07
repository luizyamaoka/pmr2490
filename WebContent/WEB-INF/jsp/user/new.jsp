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
    </style>
  </head>
  <body>
    <c:import url="/WEB-INF/jsp/shared/header.jsp" />
    
    <div class="container theme-showcase" role="main">
    
      <form class="form-signin" role="form" action="users/create" method="post">
        <h2 class="form-signin-heading">Adicionar usuário</h2>
        <input class="form-control" name="first_name" placeholder="Nome" required autofocus type="text">
        <input class="form-control" name="last_name" placeholder="Sobrenome" required type="text">
        <input class="form-control" name="birth_day" placeholder="Dia" type="number" min="1" max="31">
        <input class="form-control" name="birth_month" placeholder="Mês" type="number" min="1" max="12">
        <input class="form-control" name="birth_year" placeholder="Ano" type="number" min="1900" max="2100">
        <select class="form-control" name="genre">
          <option selected>Sexo</option>
		  <option value="m">Masculino</option>
		  <option value="f">Feminino</option>
		</select> 
        <input class="form-control" name="phone_ddd" placeholder="DDD" type="number" min="1" max="99">
        <input class="form-control" name="phone_number" placeholder="Telefone" type="number">
        <input class="form-control" name="email" placeholder="Email" required type="email">
        <input class="form-control" name="password" placeholder="Senha" required type="password">
        <input class="form-control" name="password_confirmation" placeholder="Confirmação" required type="password">
        <input class="form-control" name="profession" placeholder="Ocupação" required type="text">
        <input class="form-control" name="college" placeholder="Faculdade" type="text">
        <button class="btn btn-lg btn-primary btn-block" type="submit">Adicionar</button>
      </form>

    </div>
    
    <c:import url="/WEB-INF/jsp/shared/footer.jsp" />
    
  </body>
</html>