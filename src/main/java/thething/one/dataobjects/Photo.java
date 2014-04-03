package thething.one.dataobjects;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class Photo extends AbstractThing {

	
	public Photo(Long id, String title, Date date, String description,
			Long authorId, Set<String> tags, List<Comment> comments,  Boolean priv, Boolean published, Boolean inBlog, Integer commentCount, String fileName, Integer order) {
		super(id, title, date, description, authorId, tags, comments, priv, published, inBlog, commentCount, order );
		this.fileName = fileName;
	}



	public Photo() {
	}



	String fileName;

	public String getFileName() {
		return fileName;
	}

	public Photo setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}
	/*
	public String toString(){
		String result = super.toString();
		result += "fileName=" + fileName + "}";
		return result;
	}
	*/
}
