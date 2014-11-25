<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <head>
    <meta charset="utf-8">
    <title>${college.name}</title>
    <c:import url="/WEB-INF/jsp/shared/css.jsp" />
    <c:import url="/WEB-INF/jsp/shared/js.jsp" />
  </head>
  <body>
    <c:import url="/WEB-INF/jsp/shared/header.jsp" />
    
    <div class="container theme-showcase" role="main">

	  <c:import url="/WEB-INF/jsp/shared/alert.jsp" />

      <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
          <table class="table">
            <tbody>
              <tr>
                <td style="font-weight: bold;">Id</td>
                <td>${college.id}</td>
              </tr>
              <tr>
                <td style="font-weight: bold;">Nome</td>
                <td>${college.name}</td>
              </tr>
            </tbody>
          </table>
          <a href="/pmr2490/colleges/${college.id}/edit" class="btn btn-warning" style="width:100%;">Editar</a>
        </div>
      </div>
    </div>
    
    <c:import url="/WEB-INF/jsp/shared/footer.jsp" />
    
  </body>
</html>