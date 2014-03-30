package thething.one.dataobjects;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class PhotoArticle extends Photo {
	
	
	
	String text;
	
	public PhotoArticle(Long id, String title, Date date, String description,
			Long authorId, Set<String> tags, List<Comment> comments, Boolean priv,
			Boolean published, Boolean inBlog, Integer commentCount, String fileName, String text, Integer order) {
		super(id, title, date, description, authorId, tags, comments, priv, published, inBlog, commentCount, fileName, order);
		this.text = text;
	}

	public PhotoArticle(Photo photo, String text) {
		super(photo.id, photo.title, photo.date, photo.description, photo.authorId, photo.tags, photo.comments, photo.priv, photo.published, photo.inBlog,  photo.commentCount, photo.fileName, photo.order);
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public PhotoArticle setText(String text) {
		this.text = text;
		return this;
	}

	public String toString(){
		String result = super.toString();
		result += "fileName=" + fileName + "}";
		return result;
	}
	

}
