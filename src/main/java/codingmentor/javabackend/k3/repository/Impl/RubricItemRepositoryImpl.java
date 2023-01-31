package codingmentor.javabackend.k3.repository.Impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import codingmentor.javabackend.k3.mapper.RubricItemMapper;
import codingmentor.javabackend.k3.model.RubricItem;
import codingmentor.javabackend.k3.repository.AbstractRepository;
import codingmentor.javabackend.k3.repository.RubricItemRepository;

public class RubricItemRepositoryImpl extends AbstractRepository<RubricItem> implements RubricItemRepository{
	private static RubricItemRepository repository = null;
	private final RubricItemMapper mapper;
	private RubricItemRepositoryImpl() {
		mapper = new RubricItemMapper();
	}
	 
	public static RubricItemRepository getInstance() {
    	if (repository == null) {
    		repository = new RubricItemRepositoryImpl();
    	}
    	return repository;
    }
   
	/**
	 * close connection to save resources
	 * @param connection
	 * @param ps
	 * @param rs
	 */
    private void close (Connection connection, Statement ps, ResultSet rs) {
    	try {
    		if (rs != null) {
    			rs.close();
    		}
    		if (ps != null) {
    			ps.close();
    		}
    		
    		if (connection != null) {
    			connection.close();
    		}

    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }

    
    
	/*
	 * GET LIST METHOD
	 */
    
	/**
	 * {@inheritDoc}
	 */

	@Override
	public List<RubricItem> getRubricItems() {
		return executeQuery(connection -> {
			final String query = "SELECT * FROM rubric_items";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet results = statement.executeQuery();
			System.out.println("getRubricItems: " + statement);
			List<RubricItem> rubricItemsList = new ArrayList<>();
			while(results.next()) {
				rubricItemsList.add(mapper.map(results));
			}
			close(connection, statement, results);
			return rubricItemsList;
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<RubricItem> getRubricItemsByProblemId(int problem_id) {
		return executeQuery(connection -> {
			final String query = "SELECT * FROM rubric_items where problem_id = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, problem_id);
			ResultSet results = statement.executeQuery();
			System.out.println("getRubricItemsByAssignmentId: " + statement);
			List<RubricItem> rubricItemsList = new ArrayList<>();
			while(results.next()) {
				rubricItemsList.add(mapper.map(results));
			}
			close(connection, statement, results);
			return rubricItemsList;
		});
	}
	
	@Override
	public List<RubricItem> getRubricItemsByProblemIdOrderByLocationAsc(int problem_id) {
		return executeQuery(connection -> {
			final String query = "SELECT * FROM rubric_items where problem_id = ? ORDER BY location ASC";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, problem_id);
			ResultSet results = statement.executeQuery();
			System.out.println("getRubricItemsByAssignmentId: " + statement);
			List<RubricItem> rubricItemsList = new ArrayList<>();
			while(results.next()) {
				rubricItemsList.add(mapper.map(results));
			}
			close(connection, statement, results);
			return rubricItemsList;
		});
	}
	
	/*
	 * GET ITEM METHOD
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public RubricItem getRubricItemById(int id) {
		return executeQuerySingle(connection -> {
			final String query = "SELECT * FROM rubric_items WHERE id = ? LIMIT 1;";
		    PreparedStatement statement = connection.prepareStatement(query);
		    statement.setInt(1, id);
		    ResultSet results = statement.executeQuery();
		    System.out.println("getRubricItemById: " + statement);
		    RubricItem rubricItem = (results.next()) ? mapper.map(results) : null;
		    close(connection, statement, results);
		    return rubricItem;
    	});
	}


	
	/*
	 * GET Check True/false METHOD
	 */
    
    /*
	 * POST(CREATE) PUT(REPLACE) PATCH(UPDATE) METHODS
	 */
	
	//POST(INSERT INTO)
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean insertRubricItem(int problem_id, String title, double points, int location) {
		return executeUpdate(connection -> {
			final String query = "INSERT INTO rubric_items (problem_id, title, points, location) VALUES (?, ?, ?, ?);";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, problem_id);
			statement.setString(2, title);
			statement.setDouble(3, points);
			statement.setInt(4, location);
			System.out.println("insertRubricItem: " + statement);
			int result = statement.executeUpdate();
			close(connection, statement, null);			
			return result;	
		}) != 0;
	}

	

	
	
	//PATCH
	
}