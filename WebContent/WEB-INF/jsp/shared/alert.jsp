<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${not empty success_message}">
  <div class="alert alert-success" role="alert">
    ${success_message}
  </div>
</c:if>

<c:if test="${not empty error_message}">
  <div class="alert alert-danger" role="alert">
   	${error_message}
  </div>
</c:if>

<c:if test="${not empty errors}">
  <div class="alert alert-danger" role="alert">
    <ul>
   	<c:forEach var="error" items="${errors}">
   	  <li>${error}</li>
   	</c:forEach>
   	</ul>
  </div>
</c:if>

<c:if test="${not empty info_message}">
  <div class="alert alert-info" role="alert">
   	${info_message}
  </div>
</c:if>