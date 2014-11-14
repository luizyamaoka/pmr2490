<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
  <head>
    <meta charset="utf-8">
    <title>${event.name}</title>
    <c:import url="/WEB-INF/jsp/shared/css.jsp" />
    <c:import url="/WEB-INF/jsp/shared/js.jsp" />
  </head>
  <body>
    <c:import url="/WEB-INF/jsp/shared/header.jsp" />
    
    <div class="container theme-showcase" role="main">

	

      <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
          <table class="table">
            <tbody>
              <tr>
                <td style="font-weight: bold;">Id</td>
                <td>${event.id}</td>
              </tr>
              <tr>
                <td style="font-weight: bold;">Nome</td>
                <td>${event.name}</td>
              </tr>
              <tr>
                <td style="font-weight: bold;">Início</td>
                <td><fmt:formatDate value="${event.dateStart}" pattern="dd/MM/yyyy hh:mm" /></td>
              </tr>
              <tr>
                <td style="font-weight: bold;">Fim</td>
                <td><fmt:formatDate value="${event.dateEnd}" pattern="dd/MM/yyyy hh:mm" /></td>
              </tr>
              <tr>
                <td style="font-weight: bold;">Telefone</td>
                <td>(${event.phoneDdd}) ${event.phoneNumber}</td>
              </tr>
              <tr>
                <td style="font-weight: bold;">Email</td>
                <td>${event.email}</td>
              </tr>
              <tr>
                <td style="font-weight: bold;">Descrição</td>
                <td>${event.description}</td>
              </tr>
              <tr>
                <td style="font-weight: bold;">Local</td>
                <td>${event.local.name}</td>
              </tr>
              <tr>
                <td style="font-weight: bold;">Criador</td>
                <td>${event.creator.firstName} ${event.creator.lastName}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    
    <c:import url="/WEB-INF/jsp/shared/footer.jsp" />
    
  </body>
</html>