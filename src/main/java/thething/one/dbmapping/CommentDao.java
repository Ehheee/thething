package thething.one.dbmapping;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import thething.one.dataobjects.Comment;
import thething.one.dbmapping.mappers.CommentMapper;

public class CommentDao extends BaseDao{

	
	public List<Comment> getComments(long thingId){
		List<Comment> comments = new ArrayList<Comment>();
		SqlRowSet rs = this.namedParameterJdbcTemplate.getJdbcOperations().queryForRowSet(Statements.Select_comments_By_thingId, thingId);
		try {
			comments = commentMapper.MapRows(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return comments;
	}
	
	public void deleteComment(Long id){
		jdbcTemplate.update(Statements.Delete_comments_By_id, id);
	}
	
	public void updateComment(Comment comment){
		SqlParameterSource pm = new BeanPropertySqlParameterSource(comment);
		// TODO SqlParameterSource needs userId
		jdbcTemplate.update(Statements.Update_comments_By_id, pm);
	}
	
	
	public Comment getComment(Long id){
		SqlRowSet rs = this.jdbcTemplate.queryForRowSet(Statements.Select_comments_By_id, id);
		Comment comment = null;
		try {
			comment = commentMapper.MapRow(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comment;
	}
	
	public Long insertComment(Comment comment){
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource pm = new BeanPropertySqlParameterSource(comment);
		this.namedParameterJdbcTemplate.update(Statements.Insert_comment, pm, keyHolder);
		return (Long) keyHolder.getKey();
	}
	CommentMapper commentMapper;

}
