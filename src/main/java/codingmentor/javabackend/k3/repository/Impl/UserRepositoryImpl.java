package codingmentor.javabackend.k3.repository.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

import java.util.List;

import codingmentor.javabackend.k3.Utils.PBKDF2Hasher;
import codingmentor.javabackend.k3.Utils.RandomUtils;
import codingmentor.javabackend.k3.mapper.UserMapper;
import codingmentor.javabackend.k3.model.User;
import codingmentor.javabackend.k3.repository.AbstractRepository;
import codingmentor.javabackend.k3.repository.UserRepository;

public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {
	private static UserRepository repository = null;
	private PBKDF2Hasher hasher = null;

    private final UserMapper mapper;
    
    private UserRepositoryImpl() {
    	mapper = new UserMapper();
    	hasher = new PBKDF2Hasher();
    }
    
    public static UserRepository getInstance() {
    	if (repository == null) {
    		repository = new UserRepositoryImpl();
    	}
    	return repository;
    }
    
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
	 * GET List 
	 */
	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public List<User> getUsers() {
		return executeQuery(connection -> {
			final String query = "SELECT * FROM users";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet results = statement.executeQuery();
			System.out.println("getUsers: " + statement);
			List<User> usersList = new ArrayList<>();
			while(results.next()) {
				usersList.add(mapper.map(results));
			}
			close(connection, statement, results);
			return usersList;
		});
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> getUsersFromDepartmentId(int departmentId) {
		return executeQuery(connection -> {
			final String query = "SELECT \r\n"
					+ "	U.id, U.email, U.admin, U.first_name, U.last_name, U.preferred_name, U.set_up, U.disabled, U.deleted \r\n"
					+ "FROM users as U\r\n"
					+ "INNER JOIN department_professors as DP \r\n"
					+ "	ON DP.user_id = U.id\r\n"
					+ "INNER JOIN departments as D\r\n"
					+ "	ON DP.department_id = D.id and D.id = ?;";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, departmentId);
			ResultSet results = statement.executeQuery();
			System.out.println("getUsersFromDepartmentId: " + statement);
			List<User> usersList = new ArrayList<>();
			while(results.next()) {
				usersList.add(mapper.map(results));
			}
			close(connection, statement, results);
			return usersList;
		});
	}
    
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> getUsersFromKlassId(int klassId) {
		return executeQuery(connection -> {
			final String query = "SELECT U.id, U.email, U.admin, U.first_name, U.last_name, U.preferred_name, U.set_up, U.disabled, U.deleted from professors as P"
					+ "	INNER JOIN users AS U"
					+ " ON P.user_id = U.id and P.klass_id = ?;";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, klassId);
			ResultSet results = statement.executeQuery();
			System.out.println("getUsersFromKlassId: " + statement);
			List<User> usersList = new ArrayList<>();
			while(results.next()) {
				usersList.add(mapper.map(results));
			}
			close(connection, statement, results);
			return usersList;
		});
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> getStudentUsersByKlassId(int klassId) {
		return executeQuery(connection -> {
			final String query = "SELECT U.id, U.email, U.admin, U.first_name, U.last_name, U.preferred_name, U.set_up, U.disabled, U.deleted from students as S"
					+ "	INNER JOIN users AS U"
					+ " ON S.user_id = U.id and S.klass_id = ?;";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, klassId);
			ResultSet results = statement.executeQuery();
			System.out.println("getStudentUsersByKlassId: " + statement);
			List<User> usersList = new ArrayList<>();
			while(results.next()) {
				usersList.add(mapper.map(results));
			}
			close(connection, statement, results);
			return usersList;
		});
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> getGraderUsersByKlassId(int klassId) {
		return executeQuery(connection -> {
			final String query = "SELECT T1.*, ifnull(T2.assigned_assignments, 0) as assigned_assignments\r\n"
					+ "FROM (SELECT U.id, U.email, U.admin, U.first_name, U.last_name, U.preferred_name, U.set_up, U.disabled, U.deleted \r\n"
					+ "	from graders as G INNER JOIN users as U ON U.id = G.user_id AND G.klass_id = ?) AS T1\r\n"
					+ "LEFT JOIN (SELECT A.user_id, count(user_id) as assigned_assignments\r\n"
					+ "	from (SELECT AG.* FROM assigned_graders as AG INNER JOIN assigneds as S ON AG.assigned_id = S.id) as A group by user_id) AS T2\r\n"
					+ "ON T1.id = T2.user_id;";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, klassId);
			ResultSet results = statement.executeQuery();
			System.out.println("getGraderUsersByKlassId: " + statement);
			List<User> usersList = new ArrayList<>();
			while(results.next()) {
				usersList.add(mapper.map(results));
			}
			close(connection, statement, results);
			return usersList;
		});
	}

	/*
	 * GET Item
	 */
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findUserById(int id) {
		return executeQuerySingle(connection -> {

    		// Query to find user by email
    		final String query = "SELECT * FROM users WHERE id = ?  LIMIT 1;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            System.out.println("findUserById: " + statement);
            User user = (results.next()) ? mapper.map(results) : null;
            close(connection, statement, results);
            return user;
    	});
	}
	

    /**
     * {@inheritDoc}
     */
    
    @Override
    public User findUserByEmail(String email) { 
		return executeQuerySingle(connection -> {
	
			// Query to find user by email
			final String query = "SELECT * FROM users WHERE email = ? LIMIT 1;";
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, email);
	        ResultSet results = statement.executeQuery();
	        System.out.println("findUserByEmail: " + statement);
	        User user = (results.next()) ? mapper.map(results) : null;
	        close(connection, statement, results);
	        return user;
	    });
    }
 
	/*
	 * GET Check True/false METHOD
	 */
    
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean existedByEmail(String email) {
		return executeQuerySingle(connection -> {
			 final String query = "SELECT 1 AS ONE FROM users WHERE email = ?  LIMIT 1;";
			 PreparedStatement statement = connection.prepareStatement(query);
			 statement.setString(1, email);
			 ResultSet results = statement.executeQuery();
			 System.out.println("existedByEmail: " + statement);
			 User user = (results.next()) ? new User() : null;
			 close(connection, statement, results);
			 return user;
		}) != null;
	}
    
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isDepartmentProfessor(int userId) {
		return executeQuerySingle(connection -> {
			 final String query = "SELECT 1 AS one FROM department_professors WHERE user_id = ?;";
			 PreparedStatement statement = connection.prepareStatement(query);
			 statement.setInt(1, userId);
			 ResultSet results = statement.executeQuery();
			 System.out.println("isDepartmentProfessor: " + statement);
			 User user = (results.next()) ? new User() : null;
			 close(connection, statement, results);
			 return user;
		}) != null;
	}
	@Override
	
	/**
	 * {@inheritDoc}
	 */
	public boolean isDepartmentProfessorByDepartmentId(int userId, int departmentId) {
		return executeQuerySingle(connection -> {
			 final String query = "SELECT 1 AS one FROM department_professors WHERE user_id = ? and department_id = ? LIMIT 1;";
			 PreparedStatement statement = connection.prepareStatement(query);
			 statement.setInt(1, userId);
			 statement.setInt(2, departmentId);
			 ResultSet results = statement.executeQuery();
			 System.out.println("isDepartmentProfessorByDepartmentId: " + statement);
			 User user = (results.next()) ? new User() : null;
			 close(connection, statement, results);
			 return user;
		}) != null;
	 }
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isKlassStudentByKlassId(int userId, int klassId) {
		return executeQuerySingle(connection -> {
			 final String query = "SELECT 1 AS one FROM students WHERE user_id = ? and klass_id = ? LIMIT 1;";
			 PreparedStatement statement = connection.prepareStatement(query);
			 statement.setInt(1, userId);
			 statement.setInt(2, klassId);
			 ResultSet results = statement.executeQuery();
			 System.out.println("isKlassStudentByKlassId: " + statement);
			 User user = (results.next()) ? new User() : null;
			 close(connection, statement, results);
			 return user;
		}) != null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isKlassGraderByKlassId(int userId, int klassId) {
		return executeQuerySingle(connection -> {
			 final String query = "SELECT 1 AS one FROM graders WHERE user_id = ? and klass_id = ? LIMIT 1;";
			 PreparedStatement statement = connection.prepareStatement(query);
			 statement.setInt(1, userId);
			 statement.setInt(2, klassId);
			 ResultSet results = statement.executeQuery();
			 System.out.println("isKlassGraderByKlassId: " + statement);
			 User user = (results.next()) ? new User() : null;
			 close(connection, statement, results);
			 return user;
		}) != null;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isKlassProfessorByKlassId(int userId, int klassId) {
		return executeQuerySingle(connection -> {
			 final String query = "SELECT 1 AS one FROM professors WHERE user_id = ? and klass_id = ? LIMIT 1;";
			 PreparedStatement statement = connection.prepareStatement(query);
			 statement.setInt(1, userId);
			 statement.setInt(2, klassId);
			 ResultSet results = statement.executeQuery();
			 System.out.println("isKlassProfessorByKlassId: " + statement);
			 User user = (results.next()) ? new User() : null;
			 close(connection, statement, results);
			 return user;
		}) != null;
	}
	
    
    /*
     * POST(CREATE) PUT(REPLACE) PATCH(UPDATE) METHODS
     */
    
  	/*
  	 * # IMPORTANT: Accounts get a random password when they are first created. #
  	 * # This password cannot actually be used as the set_up boolean is false, and
  	 * # using a default password would be dumb # if set_up ever stopped working
  	 */
	
	
	//POST(CREATE)
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean createUserSendInvite(String email, boolean admin) {
		return executeUpdate(connection -> {
			final String query = "INSERT INTO users(email, admin, password_digest)"
					+ "VALUES (?, ?, ?);";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, email);
			statement.setBoolean(2, admin);
			String password_digest = hasher.hash(RandomUtils.unique64().toCharArray());
			statement.setString(3, password_digest);
			System.out.println("createUserSendInvite: " + statement);
			int result = statement.executeUpdate();
    		close(connection, statement, null);			
			return result;
		}) != 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean updateUserEditAdmin(String first_name, String last_name, String preferred_name, boolean admin, boolean disabled, int id) {
		return executeUpdate(connection -> {
			final String query = "UPDATE users SET first_name = ?, last_name = ?, preferred_name = ?, admin = ?, disabled = ? WHERE id = ?;";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, first_name);
			statement.setString(2, last_name);
			statement.setString(3, preferred_name);
			statement.setBoolean(4, admin);
			statement.setBoolean(5, disabled);
			statement.setInt(6, id);
			System.out.println("updateUserEditAdmin: " + statement);
			int result = statement.executeUpdate();		 
			close(connection, statement, null);
			return result;
		}) != 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean updatePreferredNameById(String preferred_name, int id) {
		return executeUpdate(connection -> {
			 final String query = "UPDATE users SET preferred_name = ? WHERE id = ?;";
			 PreparedStatement statement = connection.prepareStatement(query);
			 statement.setString(1, preferred_name);
			 statement.setInt(2, id);
			 System.out.println("updatePreferredNameById: " +statement);
			 int result = statement.executeUpdate();
			 close(connection, statement, null);
			 return result;
		}) != 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean updatePassword(String new_password, User user) {
		return executeUpdate(connection -> {
			 final String query = "UPDATE users SET password_digest = ? WHERE id = ?;";
			 PreparedStatement statement = connection.prepareStatement(query);
			 String password_digest = hasher.hash(new_password.toCharArray());
			 statement.setString(1, password_digest);
			 statement.setInt(2, user.getId());
			 System.out.println("updatePassword: " + statement);
			 int result = statement.executeUpdate();
			 close(connection, statement, null);
			 return result;
		}) != 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean updateResetDigest(int userid, String reset_digest) {
		return executeUpdate(connection -> {
			 final String query = " UPDATE users SET reset_digest = ? WHERE id = ?;";
			 PreparedStatement statement = connection.prepareStatement(query);
			 statement.setString(1, reset_digest);
			 statement.setInt(2, userid);
			 System.out.println("updateResetDigest: " + statement);
			 int result = statement.executeUpdate();
			 close(connection, statement, null);
			 return result;
		}) != 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean updateResetExpires(int userid, LocalDateTime reset_expires) {
		return executeUpdate(connection -> {
			 final String query = " UPDATE users SET reset_expires = ?, deleted = 0 WHERE id = ?;";
			 PreparedStatement statement = connection.prepareStatement(query);
			 statement.setTimestamp(1, Timestamp.from(reset_expires.toInstant(ZoneOffset.of("-05:00"))));
			 statement.setInt(2, userid);
			 System.out.println("updateResetExpires: " + statement);
			 int result = statement.executeUpdate();
			 close(connection, statement, null);
			 return result;
		}) != 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean updateUserInviteParams(int userid, String first_name, String last_name, String preferred_name, String password) {
		return executeUpdate(connection -> {
			 final String query = " UPDATE users SET first_name = ?, last_name = ?, preferred_name = ?, password_digest = ? WHERE id = ?;";
			 
			 PreparedStatement statement = connection.prepareStatement(query);
			 statement.setString(1, first_name);
			 statement.setString(2, last_name);
			 statement.setString(3, preferred_name);
			 String password_digest = hasher.hash(password.toCharArray());
			 statement.setString(4, password_digest);
			 statement.setInt(5, userid);
			 System.out.println("updateUserInviteParams: " + statement);
			 int result = statement.executeUpdate();
			 close(connection, statement, null);
			 return result;
		}) != 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean updateSetUpUser(int userid) {
		return executeUpdate(connection -> {
			 final String query = " UPDATE users SET set_up = 1 WHERE id = ?;";
			 PreparedStatement statement = connection.prepareStatement(query);
			 statement.setInt(1, userid);
			 System.out.println("updateSetUpUser: " + statement);
			 int result = statement.executeUpdate();
			 close(connection, statement, null);
			 return result;
		}) != 0;
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean deleteUser(int id) {
		return executeUpdate(connection -> {
			 final String query = " UPDATE users SET deleted = 1, set_up = 0 WHERE id = ?;";
			 PreparedStatement statement = connection.prepareStatement(query);
			 statement.setInt(1, id);
			 System.out.println("deleteUser: " + statement);
			 int result = statement.executeUpdate();
			 close(connection, statement, null);
			 return result;
		}) != 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean recoverUser(int id) {
		return executeUpdate(connection -> {
			 final String query = " UPDATE users SET deleted = 0 WHERE id = ?;";
			 PreparedStatement statement = connection.prepareStatement(query);
			 statement.setInt(1, id);
			 System.out.println("recoverUser: " + statement);
			 int result = statement.executeUpdate();
			 close(connection, statement, null);
			 return result;
		}) != 0;
	}

// DEPRECATED: NOT USE ANYMORE	
//	@Override
//	public List<User> getUserFromIdList (ArrayList<String> userIdsList) {
//		return executeQuery(connection -> {
//			String query = "SELECT users.* FROM users WHERE users.id IN (";
//			
//			if (userIdsList.size() == 0) {
//				return null;
//			}
//			
//			for (int i = 0; i < userIdsList.size(); i++) {
//				query += (i == 0 ? "" :",") + "?";
//			}
//			query += ");";
//			
//			System.out.println(query);
//			PreparedStatement statement = connection.prepareStatement(query);
//			
//			for (int i = 0; i < userIdsList.size(); i++) {
//				statement.setInt(i + 1, Integer.parseInt(userIdsList.get(i)));
//			}
//			
//			ResultSet results = statement.executeQuery();
//			System.out.println(statement);
//			 
//			List<User> usersList = new ArrayList<>();
//			while(results.next()) {
//				usersList.add(mapper.map(results));
//			}
//			close(connection, statement, results);
//			return usersList;
//		 });
//	 }
}
