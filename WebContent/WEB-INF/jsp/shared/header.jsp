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
      <a class="navbar-brand" href="/pmr2490/">PoliCultural</a>
    </div>
    <div class="navbar-collapse collapse">
      
        <!-- left side of the header -->
        <ul class="nav navbar-nav">
        
          <li><a href="/pmr2490/events/search">Procurar evento</a></li>
      
          <security:authorize access="isAuthenticated()">
            <li><a href="/pmr2490/events/new">Criar evento</a></li>
        
            <security:authorize access="hasRole('ADMIN')">
              <li><a href="/pmr2490/users">Usuários</a></li>
              <li><a href="/pmr2490/tags">Tags</a></li>
              <li><a href="/pmr2490/locals">Locais</a></li>
              <li><a href="/pmr2490/professions">Ocupações</a></li>
              <li><a href="/pmr2490/colleges">Faculdades</a></li>
              <li><a href="/pmr2490/events">Eventos</a></li>
            </security:authorize>
          </security:authorize>
        </ul>
      
      <security:authorize access="isAuthenticated()">
        <!-- right side of the header -->
        <ul class="nav navbar-nav navbar-right">
          <li class="dropdown">
            <a href="#" class="dropdown-toggle glyphicon glyphicon-cog" data-toggle="dropdown" role="button" aria-expanded="false"></a>
            <ul class="dropdown-menu" role="menu">
              <li><a href="/pmr2490/users/profile">Perfil</a></li>
              <li><a href="/pmr2490/users/my-events">Meus eventos</a></li>
              <li><a href="/pmr2490/users/edit-password">Alterar senha</a></li>
              <li class="divider"></li>
              <!-- li class="dropdown-header">Nav header</li> -->
              <li><a href="/pmr2490/<c:url value='j_spring_security_logout' />">Logout</a></li>
            </ul>
          </li>
        </ul>
      </security:authorize>
    </div><!--/.nav-collapse -->
  </div>
</div>