<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@page import="ilu.surveytool.databasemanager.DataObject.PollTableInfo"%>
<%@page import="ilu.surveytool.constants.Parameter"%>
<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.language.Language"%>
<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
%>
    								
						<div class="hidden" id="polls-list">	  					
							<h3><%= lang.getContent("survey_manager.polls.title") %></h3>							
							<%= lang.getContent("survey_manager.polls.description") %>
							
		  					<div class="user-panel-surveys">
		  						<div class="surveys-create-button">
		  							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#newPollModal"><%= lang.getContent("button.create_new") %></button>
		  						</div>
			  					
			  					<jsp:include page="cPollsTableList.jsp" />
			  					  					
			  				</div>
			  			</div>
<%
lang.close();
%>