<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<html>
  <head>
    <meta charset="utf-8">
    <title>${event.name}</title>
    <c:import url="/WEB-INF/jsp/shared/css.jsp" />
    <c:import url="/WEB-INF/jsp/shared/js.jsp" />
    <style>
      .info-title {
        font-weight: bold;
        text-align: right;
      }
      .info-row {
        padding: 5px 0;
      }
    </style>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">

      // Load the Visualization API and the piechart package.
      google.load('visualization', '1.0', {'packages':['corechart']});

      // Set a callback to run when the Google Visualization API is loaded.
      google.setOnLoadCallback(drawCharts);

      function drawCharts() {
    	  drawGenderChart();
      }
      
      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.
      function drawGenderChart() {

    	var genderMale = parseInt(document.getElementById("genderMale").value);
    	var genderFemale = parseInt(document.getElementById("genderFemale").value);
    	var genderUnknown = parseInt(document.getElementById("genderUnknown").value);
    	  
        // Create the data table.
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Sexo');
        data.addColumn('number', 'Número de participantes');
        data.addRows([
          ['Homens', genderMale],
          ['Mulheres', genderFemale],
          ['Não informado', genderUnknown]
        ]);

        // Set chart options
        var options = {'title':'Sexo dos participantes'};

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.PieChart(document.getElementById('gender_chart'));
        chart.draw(data, options);
      }
    </script>
    
  </head>
  <body>
    <c:import url="/WEB-INF/jsp/shared/header.jsp" />
    
    <div class="container theme-showcase" role="main">

      <c:import url="/WEB-INF/jsp/shared/alert.jsp" />

        <h1>${event.name}</h1>
	    <h3>
	      <c:forEach var="tagging" items="${event.taggings}">
	        <span class="label label-default">${tagging.tag.name}</span>
	      </c:forEach>
	    </h3>

      <div class="row info-row">
        
        <div class="col-md-4 col-xs-12">
          <h3 style="text-align: center;">Detalhes do evento</h3>
          <div class="row">
            <div class="col-xs-4 info-title">Início</div>
            <div class="col-xs-8">
              <fmt:formatDate value="${event.dateStart}" pattern="dd/MM/yyyy hh:mm" />
            </div>
          </div>
          
          <c:if test="${!empty event.dateEnd}">
            <div class="row info-row">
              <div class="col-xs-4 info-title">Término</div>
              <div class="col-xs-8">
                <fmt:formatDate value="${event.dateEnd}" pattern="dd/MM/yyyy hh:mm" />
              </div>
            </div>
          </c:if>
          
          <div class="row info-row">
            <div class="col-xs-4 info-title">Local</div>
            <div class="col-xs-8">${event.local.name}</div>
          </div>
          
          <div class="row info-row">
            <div class="col-xs-4 info-title">Organizador</div>
            <div class="col-xs-8">
              <a href="/pmr2490/users/${event.creator.id}">${event.creator.firstName} ${event.creator.lastName}</a>
            </div>
          </div>
          
          <c:if test="${!empty event.phoneNumber}">
            <div class="row info-row">
              <div class="col-xs-4 info-title">Telefone</div>
              <div class="col-xs-8">(${event.phoneDdd}) ${event.phoneNumber}</div>
            </div>
          </c:if>
          
          <div class="row info-row">
            <div class="col-xs-4 info-title">Email</div>
            <div class="col-xs-8">${event.email}</div>
          </div>
          
          <div class="row info-row">
            <div class="text-center">
              <c:if test="${event.creator.email == username}">
                <a href="/pmr2490/events/${event.id}/edit" class="btn btn-warning btn-sm" role="button">Editar evento</a>
              </c:if>
            </div>
          </div>
          
        </div>
        
        <div class="col-md-4 col-xs-12">
          <c:if test="${!empty event.description}">
            <h3 style="text-align: center;">Descrição</h3>
            <p style="text-align: justify;">
              ${event.description}
            </p>
          </c:if>
        </div>
        
        <div class="col-md-4 col-xs-12">
          <security:authorize access="isAuthenticated()">
            <c:forEach items="${event.participants}" var="participant">
			  <c:if test="${participant.user.email.equals(username)}">
			    <c:set var="participantId" value="${participant.id}" />
			  </c:if>
			</c:forEach>
		  </security:authorize>
        
            <c:if test="${fn:length(event.participants) gt 1}">
              <h3 style="text-align: center;">${fn:length(event.participants)} Partcipantes Confirmados</h3>
            </c:if>
            <c:if test="${fn:length(event.participants) eq 1}">
              <h3 style="text-align: center;">${fn:length(event.participants)} Partcipante Confirmado</h3>
            </c:if>
            <security:authorize access="isAuthenticated()">
              <div class="text-center">
              <c:choose>
                <c:when test="${participantId == null}">
                  <form class="crud-buttons" action="/pmr2490/participants/new" method="post">
                    <input type="hidden" name="eventId" value="${event.id}" />
                    <input type="submit" value="Confirmar presença" class="btn btn-success btn-sm" />
                  </form>
                </c:when>
                <c:otherwise>
                  <form class="crud-buttons" action="/pmr2490/participants/${participantId}/destroy" method="post">
                    <input type="submit" value="Cancelar presença" class="btn btn-danger btn-sm" />
                  </form>
                </c:otherwise>
              </c:choose>
              </div>
            </security:authorize>
            <c:if test="${fn:length(event.participants) gt 0}">
              
              <c:set var="genderMale" value="${0}" />
              <c:set var="genderFemale" value="${0}" />
              <c:set var="genderUnknown" value="${0}" />
              
              <c:forEach var="participant" items="${event.participants}">
                <c:choose>
                  <c:when test="${participant.user.genre == 'm'}">
                    <c:set var="genderMale" value="${genderMale+1}" />
                  </c:when>
                  <c:when test="${participant.user.genre == 'f'}">
                    <c:set var="genderFemale" value="${genderFemale+1}" />
                  </c:when>
                  <c:otherwise>
                    <c:set var="genderUnknown" value="${genderUnknown+1}" />
                  </c:otherwise>
                </c:choose>
              </c:forEach>
              
              <input id="genderMale" type="hidden" value="${genderMale}"/>
              <input id="genderFemale" type="hidden" value="${genderFemale}"/>
              <input id="genderUnknown" type="hidden" value="${genderUnknown }"/>
            
              <div class="text-center">
                <div id="gender_chart"></div>
              </div>
            </c:if>
        </div>
      </div>
    </div>
    
    <c:import url="/WEB-INF/jsp/shared/footer.jsp" />
    
  </body>
</html>