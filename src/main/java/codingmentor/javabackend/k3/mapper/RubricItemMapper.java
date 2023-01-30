package codingmentor.javabackend.k3.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import codingmentor.javabackend.k3.model.RubricItem;

public class RubricItemMapper implements RowMapper<RubricItem> {

	@Override
	public RubricItem map(ResultSet results) throws SQLException {
		
		return new RubricItem()
			.id(results.getInt("id"))
			.problem_id(results.getInt("problem_id"))
			.title(results.getString("title"))
			.points(results.getDouble("points"))
			.location(results.getInt("location"));
	}
}