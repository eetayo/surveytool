package ilu.surveytool.databasemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.Project;
import ilu.surveytool.databasemanager.DataObject.Question;
import ilu.surveytool.databasemanager.DataObject.Questionnaire;
import ilu.surveytool.databasemanager.DataObject.Resource;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.databasemanager.DataObject.SurveyTableInfo;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.databasemanager.constants.DBFieldNames;
import ilu.surveytool.databasemanager.constants.DBSQLQueries;
import ilu.surveytool.databasemanager.factory.ConnectionFactoryJDBC;

public class QuestionDB {

	public QuestionDB() {
		// TODO Auto-generated constructor stub
	}
	
	private Connection _openConnection()
	{
		ConnectionFactoryJDBC connectionFactory  = new ConnectionFactoryJDBC();
		return connectionFactory.getConnection();
	}
	
	private void _closeConnections(Connection con, PreparedStatement pstm, ResultSet rs)
	{
		 try {
			 if(pstm != null)
				 pstm.close();
			 if(rs != null)
				 rs.close();
			 if (con != null)
				 con.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	}
	
	/**
	 * Selects
	 */
	
	public List<Question> getQuestionsBySurveyId(int surveyId, String lang)
	{
		List<Question> questions = new ArrayList<Question>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTION_BY_SURVEYID);			
	   		pstm.setInt(1, surveyId);
	   		
	   		rs = pstm.executeQuery();
	   		questions = this._getQuestionList(rs, lang);
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return questions;
	}

	public List<Question> getQuestionsBySectionId(int sectionId, String lang)
	{
		List<Question> questions = new ArrayList<Question>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTION_BY_SECTIONID);			
	   		pstm.setInt(1, sectionId);
	   		
	   		rs = pstm.executeQuery();
	   		questions = this._getQuestionList(rs, lang);
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return questions;
	}

	public List<Question> getQuestionsByPageId(int pageId, String lang)
	{
		List<Question> questions = new ArrayList<Question>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTION_BY_PAGEID);			
	   		pstm.setInt(1, pageId);
	   		
	   		rs = pstm.executeQuery();
	   		questions = this._getQuestionList(rs, lang);
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return questions;
	}

	public List<Question> getQuestionsByPollId(int pollId, String lang)
	{
		List<Question> questions = new ArrayList<Question>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTION_BY_POLLID);			
	   		pstm.setInt(1, pollId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			int contentId = rs.getInt(DBFieldNames.s_CONTENTID);
	   			ContentDB contentDB = new ContentDB();
	   			HashMap<String, Content> contents = contentDB.getContentByIdAndLanguage(contentId, lang);
	   			Question question = new Question(rs.getInt(DBFieldNames.s_QUESTION_ID), 
	   					rs.getString(DBFieldNames.s_QUESTION_TAG), 
	   					null, 
	   					rs.getString(DBFieldNames.s_QUESTIONTYPE_NAME), 
	   					contents, 
	   					rs.getString(DBFieldNames.s_CATEGORY_NAME), 
	   					true,  
	   					false,
	   					rs.getString(DBFieldNames.s_QUESTIONTYPE_TEMPLATE_FILE),
	   					rs.getString(DBFieldNames.s_QUESTIONTYPE_FORM_FILE));
	   			
	   			OptionDB optionDB = new OptionDB();
	   			question.setOptionsGroups(optionDB.getOptionsGroupByQuestionId(question.getQuestionId(), lang));
	   			
	   			ResourceDB resourceDB = new ResourceDB();
	   			question.setResources(resourceDB.getResourcesByQuestionId(question.getQuestionId(), lang));
	   			
	   			questions.add(question);
	   			
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return questions;
	}

	public List<Question> getQuestionsByPageId(int pageId, int currentIndex, int prevIndex)
	{
		List<Question> questions = new ArrayList<Question>();
		
		String maxMin = this._maxMinIndex(currentIndex, prevIndex);
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String query = DBSQLQueries.s_SELECT_QUESTIONBYPAGE_BY_PAGEID_MAX_MIN.replaceAll("##", maxMin);
		   
		try{
		   	pstm = con.prepareStatement(query);			
	   		pstm.setInt(1, pageId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			Question question = new Question();
	   			
	   			question.setQuestionId(rs.getInt(DBFieldNames.s_QUESTION_ID));
	   			question.setIndex(rs.getInt(DBFieldNames.s_INDEX));
	   			
	   			questions.add(question);
	   			
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return questions;
	}
	
	public String getQuestionTypeTemplateFileByName(String questionType)
	{
		String response = "";
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONTYPE_TEMPLATE_FILE_BY_ID);			
	   		pstm.setString(1, questionType);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			response = rs.getString(DBFieldNames.s_QUESTIONTYPE_TEMPLATE_FILE);
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return response;
	}
	
	public HashMap<String, String> getQuestionContentByQuestionId(int questionId, String lang)
	{
		HashMap<String, String> contents = new HashMap<String, String>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTION_CONTENT_BY_QUESTIONID);			
	   		pstm.setInt(1, questionId);
	   		pstm.setString(2, lang);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			contents.put(rs.getString(DBFieldNames.s_QUESTIONTYPE_NAME), rs.getString(DBFieldNames.s_CONTENT_TEXT));	   			
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return contents;
	}
	
	public int getQuestionContentIdByQuestionId(int questionId)
	{
		int contentId = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTION_CONTENTID_BY_QUESTIONID);			
	   		pstm.setInt(1, questionId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			contentId = rs.getInt(DBFieldNames.s_CONTENTID);	   			
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return contentId;
	}
	
	public boolean getQuestionByPageMandatory(int questionId, int pageId)
	{
		boolean mandatory = false;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONBYPAGE_MANDATORY);			
	   		pstm.setInt(1, questionId);
	   		pstm.setInt(2, pageId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			mandatory = rs.getBoolean(DBFieldNames.s_QUESTION_MANDATORY);	   			
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return mandatory;
	}

	public int getQuestionByPageIndex(int questionId, int pageId)
	{
		int index = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
			
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONBYPAGE_INDEX);			
	   		pstm.setInt(1, questionId);
	   		pstm.setInt(2, pageId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			index = rs.getInt(DBFieldNames.s_INDEX);	   			
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return index;
	}

	public int getQuestionByPageIdIndex(int index, int pageId)
	{
		int questionId = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
			
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONBYPAGE_QUESTIONID_BY_PAGEID_INDEX);			
	   		pstm.setInt(1, pageId);
	   		pstm.setInt(2, index);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			questionId = rs.getInt(DBFieldNames.s_QUESTION_ID);	   			
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return questionId;
	}

	public int getNumQuestionByPage(int pageId)
	{
		int numQuestions = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
			
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_NUMQUESTION_BY_PAGE);			
	   		pstm.setInt(1, pageId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			numQuestions = rs.getInt(DBFieldNames.s_NUM_QUESTION);	   			
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return numQuestions;
	}
	
	/**
	 * Inserts 
	 */
	
	public int insertQuestion(Question question, int contentId) {
		//System.out.println("inserUser");
		int questionId = 0;
		//int contentId = this.insertContentIndex();
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_QUESTION, Statement.RETURN_GENERATED_KEYS);
		   pstm.setString(1, question.getTag());
		   pstm.setString(2, question.getQuestionType()); 
		   pstm.setInt(3, contentId); 
		   pstm.setString(4, question.getCategory()); 
		   
		   boolean notInserted = pstm.execute();
		   
		   if(!notInserted)
		   {
			   ResultSet rs = pstm.getGeneratedKeys();
			   if(rs.next())
				   questionId = rs.getInt(1);
		   }
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return questionId;
	}

	public boolean insertQuestionByPage(int questionId, int pageId, boolean mandatory, int numQuestion) {
		//System.out.println("inserUser");
		boolean inserted = false;
		//int contentId = this.insertContentIndex();
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_QUESTION_BY_PAGE, Statement.RETURN_GENERATED_KEYS);
		   pstm.setInt(1, pageId);
		   pstm.setInt(2, questionId);
		   pstm.setInt(3, numQuestion);
		   pstm.setBoolean(4, mandatory);
		   
		   boolean notInserted = pstm.execute();
		   
		   if(!notInserted)
		   {
			   inserted = true;
		   }
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return inserted;
	}
	
	/*
	 * Update
	 */
	
	public void updateQuestionMandatory(int questionId, int pageId, boolean mandatory) {
		//System.out.println("updateState");
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_QUESTIONBYPAGE_MANDATORY);
			pstm.setBoolean(1, mandatory);
			pstm.setInt(2, pageId);
			pstm.setInt(3, questionId);
		   		
			int numUpdated = pstm.executeUpdate();
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}
		   
	}

	public void updateQuestionIndex(int questionId, int pageId, int index) {
		//System.out.println("updateState");
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_QUESTIONBYPAGE_INDEX);
			pstm.setInt(1, index);
			pstm.setInt(2, pageId);
			pstm.setInt(3, questionId);
		   		
			int numUpdated = pstm.executeUpdate();
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}
		   
	}
	
	/*
	 * Remove
	 */
	
	public void removeQuestionByPage(int questionId, int pageId) {
		//System.out.println("removeUserOptionValues");
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_DELETE_QUESTION_BY_PAGE);
		   	pstm.setInt(1, pageId);
		   	pstm.setInt(2, questionId);
	   		
		   	pstm.execute();
		   	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}

	}
	
	private String _maxMinIndex(int currentIndex, int prevIndex)
	{
		String maxMin = "";
		
		if(prevIndex != 0 && prevIndex < currentIndex)
		{
			maxMin = "and `index` > " + prevIndex + " and `index` <= " + currentIndex;
		}
		else if(prevIndex != 0 && currentIndex < prevIndex)
		{
			maxMin = "and `index` >= " + currentIndex + " and `index` <= " + prevIndex;
		}
		else if(prevIndex == 0)
		{
			maxMin = "and `index` > " + prevIndex;
		}
		
		return maxMin;
	}
	
	private List<Question> _getQuestionList(ResultSet rs, String lang)
	{
		List<Question> questions = new ArrayList<Question>();
		
		try
		{
			while(rs.next())
	   		{
	   			int contentId = rs.getInt(DBFieldNames.s_CONTENTID);
	   			ContentDB contentDB = new ContentDB();	   			
	   			HashMap<String, Content> contents = contentDB.getContentByIdAndLanguage(contentId, lang);
	   			Question question = new Question(rs.getInt(DBFieldNames.s_QUESTION_ID), 
	   					rs.getString(DBFieldNames.s_QUESTION_TAG), 
	   					null, 
	   					rs.getString(DBFieldNames.s_QUESTIONTYPE_NAME), 
	   					contents, 
	   					rs.getString(DBFieldNames.s_CATEGORY_NAME), 
	   					rs.getBoolean(DBFieldNames.s_QUESTION_MANDATORY), 
	   					false,
	   					rs.getString(DBFieldNames.s_QUESTIONTYPE_TEMPLATE_FILE),
	   					rs.getString(DBFieldNames.s_QUESTIONTYPE_FORM_FILE));
	   			
	   			question.setIndex(rs.getInt(DBFieldNames.s_INDEX));
	   			
	   			OptionDB optionDB = new OptionDB();
	   			question.setOptionsGroups(optionDB.getOptionsGroupByQuestionId(question.getQuestionId(), lang));
	   			
	   			ResourceDB resourceDB = new ResourceDB();
	   			question.setResources(resourceDB.getResourcesByQuestionId(question.getQuestionId(), lang));
	   			
	   			questions.add(question);
	   			
	   		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return questions;
	}
	
}
