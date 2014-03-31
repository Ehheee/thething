package thething.one.dataobjects;

public class Settings {

	Integer id;
	String tag;
	String mainTemplate;
	String mainCSS;
	Boolean standAlone;
	String startPage;
	Integer tagOrder;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getMainTemplate() {
		return mainTemplate;
	}
	public void setMainTemplate(String mainTemplate) {
		this.mainTemplate = mainTemplate;
	}
	public String getMainCSS() {
		return mainCSS;
	}
	public void setMainCSS(String mainCSS) {
		this.mainCSS = mainCSS;
	}
	public Boolean getStandAlone() {
		return standAlone;
	}
	public void setStandAlone(Boolean standAlone) {
		this.standAlone = standAlone;
	}
	public String getStartPage() {
		return startPage;
	}
	public void setStartPage(String startPage) {
		this.startPage = startPage;
	}
	public Integer getTagOrder() {
		return tagOrder;
	}
	public void setTagOrder(Integer tagOrder) {
		this.tagOrder = tagOrder;
	}
	
	
	
	
}
