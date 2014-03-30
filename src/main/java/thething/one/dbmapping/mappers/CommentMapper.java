package thething.one.dbmapping.mappers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import thething.one.dataobjects.Comment;
import thething.one.dataobjects.User;

public class CommentMapper {

	
	public Comment MapRow(SqlRowSet rs) throws SQLException {
		Long id = rs.getLong("id");
		Long thingId = rs.getLong("thingId");
		String text = rs.getString("text");
		Date date = rs.getDate("date");
		String author = rs.getString("auhor");
		Long userId = rs.getLong("userId");
		
		return new Comment(id,thingId, text, date, author, userId);
	}
	
	public List<Comment> MapRows(SqlRowSet rs) throws SQLException {
		List<Comment> comments = new ArrayList<Comment>();
		
		while(rs.next()){
			comments.add(MapRow(rs));
		}
		return comments;
	}
}
