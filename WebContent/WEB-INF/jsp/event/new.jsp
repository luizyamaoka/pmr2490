<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
  <head>
    <meta charset="utf-8">
    <title>Novo evento</title>
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
        <form:form class="form-horizontal" action="new" modelAttribute="eventDto">
          <div class="form-group">
            <label for="nameInput" class="control-label col-sm-4 col-xs-12">Nome * </label>
            <div class="col-sm-8 col-xs-12">
			  <form:input path="name" class="form-control" id="nameInput" placeholder="Nome do evento" required="true" autofocus="true" />
			</div>
		    <form:errors path="name" cssClass="error" />
		  </div>
		  <div class="form-group">
            <label for="dayStartInput" class="control-label col-sm-4 col-xs-12">Data início * </label>
            <div class="col-sm-8 col-xs-12">
              <div class="input-group">
			  <form:input path="dayStart" class="form-control" id="dayStartInput" placeholder="DD" />
			  <span class="input-group-addon">/</span>
			  <form:input path="monthStart" class="form-control" id="monthStartInput" placeholder="MM" />
			  <span class="input-group-addon">/</span>
			  <form:input path="yearStart" class="form-control" id="yearStartInput" placeholder="YYYY" />
			  <span class="input-group-addon">às</span>
			  <form:input path="hourStart" class="form-control" id="hourStartInput" placeholder="HH" />
			  <span class="input-group-addon">:</span>
			  <form:input path="minuteStart" class="form-control" id="minuteStartInput" placeholder="mm" />
			  </div>
			</div>
		    <form:errors path="dayStart" cssClass="error" />
		  </div>
		  <div class="form-group">
            <label for="dayEndInput" class="control-label col-sm-4 col-xs-12">Data término</label>
            <div class="col-sm-8 col-xs-12">
              <div class="input-group">
			  <form:input path="dayEnd" class="form-control" id="dayEndInput" placeholder="DD" />
			  <span class="input-group-addon">/</span>
			  <form:input path="monthEnd" class="form-control" id="monthEndInput" placeholder="MM" />
			  <span class="input-group-addon">/</span>
			  <form:input path="yearEnd" class="form-control" id="yearEndInput" placeholder="YYYY" />
			  <span class="input-group-addon">às</span>
			  <form:input path="hourEnd" class="form-control" id="hourEndInput" placeholder="HH" />
			  <span class="input-group-addon">:</span>
			  <form:input path="minuteEnd" class="form-control" id="minuteEndInput" placeholder="mm" />
			  </div>
			</div>
		    <form:errors path="dayEnd" cssClass="error" />
		  </div>
		  <div class="form-group">
            <label for="phoneDddInput" class="control-label col-sm-4 col-xs-12">Telefone de contato </label>
            <div class="col-sm-2 col-xs-3">
			  <form:input path="phoneDdd" class="form-control" id="phoneDddInput" placeholder="DDD" />
			</div>
			<div class="col-sm-6 col-xs-9">
			  <form:input path="phoneNumber" class="form-control" id="phoneNumberInput" placeholder="Telefone" />
			</div>
		    <form:errors path="phoneNumber" cssClass="error" />
		  </div>
		  <div class="form-group">
            <label for="emailInput" class="control-label col-sm-4 col-xs-12">E-mail de contato * </label>
            <div class="col-sm-8 col-xs-12">
			  <form:input path="email" class="form-control" id="emailInput" placeholder="E-mail" required="true" />
			</div>
		    <form:errors path="email" cssClass="error" />
		  </div>
		  <div class="form-group">
            <label for="descriptionInput" class="control-label col-sm-4 col-xs-12">Descrição </label>
            <div class="col-sm-8 col-xs-12">
			  <form:textarea style="height: 200px;" path="description" class="form-control" id="descriptionInput" placeholder="Descrição" />
			</div>
		    <form:errors path="description" cssClass="error" />
		  </div>
		  <div class="form-group">
            <label for="localOptions" class="control-label col-sm-4 col-xs-12">Local * </label>
            <div class="col-sm-8 col-xs-12">
			  <form:select path="localId" class="form-control" id="localOptions">
			    <form:option value="">Selecione o local do evento</form:option>
          		<form:options items="${locals}" itemValue="id" itemLabel="name" required="true" />
		      </form:select>
			</div>
		    <form:errors path="localId" cssClass="error" />
		  </div>
		  <div class="form-group">
            <label for="tagsCheckbox" class="control-label col-sm-4 col-xs-12">Tags </label>
            <div class="col-sm-8 col-xs-12">
              <form:checkboxes items="${tags}" itemValue="id" itemLabel="name" path="tagIds" />
			</div>
		    <form:errors path="tagIds" cssClass="error" />
		  </div>
		  <div class="form-group">
		    <div class="col-sm-offset-4">
              <input class="form-control btn btn-primary" type="submit" value="Criar evento" />
            </div>
		  </div>
		
      </form:form>
      </div>

    </div>
    
    <c:import url="/WEB-INF/jsp/shared/footer.jsp" />
    
  </body>
</html>