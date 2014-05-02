package thething.one.dbmapping;

import org.springframework.beans.factory.annotation.Autowired;

public class ExtendedBaseDao extends BaseDao {

	
	@Autowired
	TagDao tagDao;
	public void setTagDao(TagDao tagDao) {
		this.tagDao = tagDao;
	}


	

	@Autowired
	CommentDao commentDao;
	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
}
