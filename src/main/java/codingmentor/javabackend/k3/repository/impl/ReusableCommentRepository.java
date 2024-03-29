package codingmentor.javabackend.k3.repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import codingmentor.javabackend.k3.mapper.ReusableCommentMapper;
import codingmentor.javabackend.k3.model.ReusableComment;
import codingmentor.javabackend.k3.repository.BaseRepository;

public class ReusableCommentRepository extends BaseRepository<ReusableCommentMapper, ReusableComment> {
	private static ReusableCommentRepository repository;
	
	public static synchronized ReusableCommentRepository getInstance() {
    	if (repository == null) {
    		repository = new ReusableCommentRepository();
    	}
    	
    	return repository;
    }
	
	private ReusableCommentRepository() {
		super(new ReusableCommentMapper());
	}
	 

	/*
	 * GET LIST METHOD
	 */

	public List<ReusableComment> getReusableComments() {
		return executeQuery(connection -> {
			final String query = "\nSELECT * FROM reusable_comments";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet results = statement.executeQuery();
			logger.info("-- getReusableComments: " + statement);
			List<ReusableComment> reusable_commentsList = new ArrayList<>();
			while(results.next()) {
				reusable_commentsList.add(mapper.map(results));
			}
			close(connection, statement, results);
			return reusable_commentsList;
		});
	}

	public List<ReusableComment> getReusableCommentsByProblemId(int problem_id) {
		return executeQuery(connection -> {
			final String query = "\nSELECT * FROM reusable_comments where problem_id = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, problem_id);
			ResultSet results = statement.executeQuery();
			logger.info("-- getReusableCommentsByProblemId: " + statement);
			List<ReusableComment> reusable_commentsList = new ArrayList<>();
			while(results.next()) {
				reusable_commentsList.add(mapper.map(results));
			}
			close(connection, statement, results);
			return reusable_commentsList;
		});
	}

	
	/*
	 * GET ITEM METHOD
	 */

	public ReusableComment getReusableCommentById(int id) {
		return executeQuerySingle(connection -> {
			final String query = "\nSELECT * FROM reusable_comments WHERE id = ? LIMIT 1;";
		    PreparedStatement statement = connection.prepareStatement(query);
		    statement.setInt(1, id);
		    ResultSet results = statement.executeQuery();
		    logger.info("-- getReusableCommentById: " + statement);
		    ReusableComment reusableComment = (results.next()) ? mapper.map(results) : null;
		    close(connection, statement, results);
		    return reusableComment;
    	});
	}
	
	/*
	 * GET Check True/false METHOD
	 */

	public boolean existedByCommentAndProblemId(String comment, int problemId) {
		return executeQuerySingle(connection -> {
			 final String query = "\nSELECT 1 as ONE FROM reusable_comments WHERE comment = ? and problem_id = ? LIMIT 1;";
			 PreparedStatement statement = connection.prepareStatement(query);
			 statement.setString(1, comment);
			 statement.setInt(2, problemId);
			 ResultSet results = statement.executeQuery();
			 logger.info("-- existedByCommentAndProblemId" + statement);
			 ReusableComment reusableComment = (results.next()) ? new ReusableComment() : null;
			 close(connection, statement, results);
			 return reusableComment;
		}) != null;
	}
	
	
    /*
	 * POST(CREATE) PATCH(UPDATE) METHODS
	 */
	
	//POST(INSERT INTO)

	public boolean insertReusableComment(int problem_id, String comment) {
		return executeUpdate(connection -> {
			final String query = "\nINSERT INTO reusable_comments (problem_id, comment) VALUES (?, ?);";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, problem_id);
			statement.setString(2, comment);
			logger.info("-- insertReusableComment: " + statement);
			int result = statement.executeUpdate();
			close(connection, statement, null);			
			return result;	
		}) != 0;
	}
	//PATCH	    
}