<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <meta charset="utf-8">
  <title>Login</title>
  <c:import url="/WEB-INF/jsp/shared/css.jsp" />
  <c:import url="/WEB-INF/jsp/shared/js.jsp" />
  <style>
  	.form-signin {
	  max-width: 330px;
	  padding: 15px;
	  margin: 0 auto;
	}
	.form-signin .form-signin-heading,
	.form-signin .checkbox {
	  margin-bottom: 10px;
	}
	.form-signin-heading {
	  text-align: center;
	}
	.form-signin .checkbox {
	  font-weight: normal;
	}
	.form-signin .form-control {
	  position: relative;
	  height: auto;
	  -webkit-box-sizing: border-box;
	     -moz-box-sizing: border-box;
	          box-sizing: border-box;
	  padding: 10px;
	  font-size: 16px;
	}
	.form-signin .form-control:focus {
	  z-index: 2;
	}
	.form-signin input[type="email"] {
	  margin-bottom: -1px;
	  border-bottom-right-radius: 0;
	  border-bottom-left-radius: 0;
	}
	.form-signin input[type="password"] {
	  margin-bottom: 10px;
	  border-top-left-radius: 0;
	  border-top-right-radius: 0;
	}
  </style>
</head>
  <body>
  
    <div id="wrapper">
  
      <c:import url="/WEB-INF/jsp/shared/header.jsp" /> 
  
  	  <div class="container theme-showcase" role="main">
	    
	      <c:import url="/WEB-INF/jsp/shared/alert.jsp" /> 
	    
	      <form class="form-signin" role="form" action="/pmr2490/<c:url value='j_spring_security_check' />" method="post">
	        <h2 class="form-signin-heading">Seja bem vindo!</h2>
	        <input type="email" name="username" class="form-control" placeholder="Usuário" required autofocus>
	        <input type="password" name="password" class="form-control" placeholder="Senha" required>
	        <input class="btn btn-lg btn-primary btn-block" type="submit" value="Entrar">
	      </form>
    
      </div>
    
      <c:import url="/WEB-INF/jsp/shared/footer.jsp" />
    
    </div>
  
  </body>
</html>