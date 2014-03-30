package thething.one.dataobjects;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class Article extends AbstractThing {

	
	public Article(Long id, String title, Date date, String description,
			Long authorId, Set<String> tags, List<Comment> comments, Boolean priv, Boolean published, Boolean inBlog, Integer commentCount, String text, Integer order) {
		super(id, title, date, description, authorId, tags, comments, priv, published, inBlog, commentCount, order);
		this.text = text;
	}

	public Article() {
		super();
	}

	String text;

	public String getText() {
		return text;
	}

	public Article setText(String text) {
		this.text = text;
		return this;
	}
	
}
