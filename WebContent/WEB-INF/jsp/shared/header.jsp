<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="/pmr2490/">Eventos USP</a>
    </div>
    <div class="navbar-collapse collapse">
      <ul class="nav navbar-nav">
        <li><a href="/pmr2490/">Home</a></li>
        <li><a href="/pmr2490/users">Usuários</a></li>
        <li><a href="/pmr2490/tags">Tags</a></li>
        <li><a href="/pmr2490/locals">Locais</a></li>
        <li><a href="/pmr2490/professions">Ocupações</a></li>
        <li><a href="/pmr2490/colleges">Faculdades</a></li>
            <!-- li><a href="#about">About</a></li> -->
            <!--li><a href="#contact">Contact</a></li> -->
            <!--li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li class="divider"></li>
                <li class="dropdown-header">Nav header</li>
                <li><a href="#">Separated link</a></li>
                <li><a href="#">One more separated link</a></li>
              </ul>
            </li> -->
      </ul>
      <security:authorize access="isAuthenticated()">
        <ul class="nav navbar-nav navbar-right">
          <li><a href="<c:url value='j_spring_security_logout' />">Sair</a></li>
        </ul>
      </security:authorize>
    </div><!--/.nav-collapse -->
  </div>
</div>