				<%@page import="ilu.surveytool.constants.Parameter"%>
<%@page import="ilu.surveytool.constants.Address"%>
<div class="container-fluid">
	  				<div class="title-content">
	  					<h2>User Panel</h2>
	  				</div>
	  				
	  				<div class="content">
	  					<p>Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno est�ndar de las industrias desde el a�o 1500, cuando un impresor (N. del T. persona que se dedica a la imprenta) desconocido us� una galer�a de textos y los mezcl� de tal manera que logr� hacer un libro de textos especimen. No s�lo sobrevivi� 500 a�os, sino que tambien ingres� como texto de relleno en documentos electr�nicos, quedando esencialmente igual al original. Fue popularizado en los 60s con la creaci�n de las hojas "Letraset", las cuales contenian pasajes de Lorem Ipsum, y m�s recientemente con software de autoedici�n, como por ejemplo Aldus PageMaker, el cual incluye versiones de Lorem Ipsum.</p>
	  					<p>Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno est�ndar de las industrias desde el a�o 1500, cuando un impresor (N. del T. persona que se dedica a la imprenta) desconocido us� una galer�a de textos y los mezcl� de tal manera que logr� hacer un libro de textos especimen.</p>
	  					<div class="user-panel-menu">
		  					<ul class="row row-menu">
		  						<li class="col-md-3 center"><a href="<%= Address.s_SERVLET_USER_PANEL_HOME + "?" + Parameter.s_UPOPTION + "=" + Address.s_BODY_SURVEYS %>"><i class="fa fa-list-alt fa-5x"></i><div>Survey</div></a></li>
		  						<li class="col-md-3 center"><i class="fa fa-users fa-5x" aria-label="User list disabled"></i><p>User list</p></li>
		  						<li class="col-md-3 center"><i class="fa fa-area-chart fa-5x" aria-label="Panel statistics disabled"></i><p>Panel statistics</p></li>
		  						<li class="col-md-3 center"><i class="fa fa-cogs fa-5x" aria-label="Panel settings disabled"></i><p>Panel settings</p></li>
		  						<!-- <li class="col-md-3 center"><a href="#"><i class="fa fa-users fa-5x"></i><div>User list</div></a></li>
		  						<li class="col-md-3 center"><a href="#"><i class="fa fa-area-chart fa-5x"></i><div>Panel statistics</div></a></li>
		  						<li class="col-md-3 center"><a href="#"><i class="fa fa-cogs fa-5x"></i><div>Panel settings</div></a></li> -->
		  					</ul>
		  				</div>
	  				</div>
	  			</div>