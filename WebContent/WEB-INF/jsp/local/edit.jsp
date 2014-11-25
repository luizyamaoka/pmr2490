<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <head>
    <meta charset="utf-8">
    <title>${local.name}</title>
    <c:import url="/WEB-INF/jsp/shared/css.jsp" />
    <c:import url="/WEB-INF/jsp/shared/js.jsp" />
    <style>
    .center-form {
        max-width: 600px;
        margin: 0 auto;
      }
    </style>
  </head>
  <body>
    <c:import url="/WEB-INF/jsp/shared/header.jsp" />
    
    <div class="container theme-showcase" role="main">
    
      <c:import url="/WEB-INF/jsp/shared/alert.jsp" />  
      
	  <div class="center-form">
	    <div class="col-xs-8 col-xs-offset-4">
	      <h2>Editar local</h2>
	    </div>
        <form class="form-horizontal" action="edit" method="post">
          <div class="form-group">
            <label for="nameInput" class="control-label col-xs-4">Nome</label>
            <div class="col-xs-8">
			  <input name="name" type="text" value="${local.name}" class="form-control" id="nameInput" placeholder="Nome do local" required autofocus />
			</div>
		  </div>
		  
		  <div class="form-group">
		    <div class="col-xs-8 col-xs-offset-4">
              <input class="form-control btn btn-primary" type="submit" value="Atualizar" />
            </div>
		  </div>
		
        </form>
      </div>

    </div>
    
    <c:import url="/WEB-INF/jsp/shared/footer.jsp" />
    
  </body>
</html>