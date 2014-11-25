<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<html>
  <head>
    <meta charset="utf-8">
    <title>Procurar evento</title>
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
        <form class="form-horizontal" action="search" method="post">
          <div class="form-group">
            <label for="nameInput" class="control-label col-xs-4">Nome</label>
            <div class="col-xs-8">
			  <input name="name" class="form-control" id="nameInput" placeholder="Procurar por nome" autofocus />
			</div>
		  </div>
		  
		  <div class="form-group">
            <label for="dateInput" class="control-label col-xs-4">Data do evento</label>
            <div class="col-xs-8">
			  <tags:datefield id="date" placeholder="Procurar por data" />
			</div>
		  </div>
		  
		  <div class="form-group">
            <label for="tagOptions" class="control-label col-xs-4">Tag </label>
            <div class="col-xs-8">
			  <select name="tagId" class="form-control" id="tagOptions">
			    <option value="">Procurar por Tag</option>
			    <c:forEach var="tag" items="${tags}">
          		  <option value="${tag.id}">${tag.name}</option>
          		</c:forEach>
		      </select>
			</div>
		  </div>
		  
		  <div class="form-group">
            <label for="localOptions" class="control-label col-xs-4">Local </label>
            <div class="col-xs-8">
			  <select name="localId" class="form-control" id="localOptions">
			    <option value="">Procurar por Local</option>
			    <c:forEach var="local" items="${locals}">
          		  <option value="${local.id}">${local.name}</option>
          		</c:forEach>
		      </select>
			</div>
		  </div>
		  
		  <div class="form-group">
		    <div class="col-xs-offset-4">
              <input class="form-control btn btn-primary" type="submit" value="Procurar" />
            </div>
		  </div>
		
      </form>
      </div>

    </div>
    
    <c:import url="/WEB-INF/jsp/shared/footer.jsp" />
    
  </body>
</html>