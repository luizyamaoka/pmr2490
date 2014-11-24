<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>	

<html>
  <head>
    <meta charset="utf-8">
    <title>Tags</title>
    <c:import url="/WEB-INF/jsp/shared/css.jsp" />
    <c:import url="/WEB-INF/jsp/shared/js.jsp" />
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
    
      <form class="form-signin" role="form" action="/pmr2490/taggings/new" method="post">
        <h2 class="form-signin-heading">Adicionar nova tag</h2>
        <input type="hidden" name="eventId" value="${event.id}">
        <select class="form-control" name="tagId">
          <option value="">Selecione uma tag</option>
          <c:forEach var="tag" items="${tags}">
            <c:set var="found" value="0" />
            <c:forEach items="${event.taggings}" var="tagging">
			  <c:if test="${tagging.tag.id == tag.id}">
			    <c:set var="found" value="1" />
			  </c:if>
			</c:forEach>
			<c:if test="${found == 0}">
              <option value="${tag.id}">${tag.name}</option>
            </c:if>
          </c:forEach>
        </select>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Adicionar</button>
      </form>
    
      <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
          <table class="table">
            <thead>
              <tr>
                <th>Tag</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="tagging" items="${event.taggings}">
              <tr>
                <td>${tagging.tag.name}</td>
                <td>
                  <form class="crud-buttons" action="/pmr2490/taggings/${tagging.id}/destroy" method="post">
                    <input type="submit" value="Deletar" class="btn btn-danger btn-xs" />
                  </form>
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