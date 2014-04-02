package test;

import java.sql.Array;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;
import javax.sql.RowSet;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.social.facebook.api.Reference;
import org.springframework.social.facebook.api.UserOperations;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.client.RestTemplate;

import com.mysql.jdbc.Connection;

import thething.one.services.ExternalUserService;
import thething.one.dataobjects.AbstractThing;
import thething.one.dataobjects.Article;
import thething.one.dataobjects.Comment;
import thething.one.dataobjects.LegacyGoogleProfile;
import thething.one.dataobjects.Photo;
import thething.one.dbmapping.BaseDao.ActionType;
import thething.one.dbmapping.TagDao;
import thething.one.dbmapping.ThingDao;
import thething.one.dbmapping.ThingFilter;
import thething.one.dbmapping.BaseDao.ObjectType;
import thething.one.dbmapping.ThingFilter.ThingType;
import thething.one.dbmapping.mappers.AbstractThingExtractor;
import thething.one.dbmapping.mappers.AbstractThingMapper;
public class Main {

	/*print(
	 * 
	 * ELECT maker, Product.model AS model_1, PC.model AS model_2, price
 FROM Product LEFT JOIN PC ON PC.model = Product.model
 WHERE type = 'PC'
 ORDER BY maker, PC.model;
 
 
 
	 * 
	*/
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		/*
		ObjectType t = ObjectType.valueOf("user".toUpperCase());
		print(t);
		OAuthClientRequest request = null;
		try {
			FacebookTemplate facebookTemplate = new FacebookTemplate("CAAE3ejZCJ7RMBADPIdDckZCXdEZBMqctSfXlQfYvIyAd4uP89P5pfsybyMwYdvxz3lvnE756rZAQAxn1iVSTWhHRXHN0q3eVBViSkwEV6vcIqxUZBWRyLEER0rPcMDiCZBPAFUbPZBuxfSqqZCbyC4nMEQRk7jN0bkNL8Cgb9PPfHeM38ZBV6YZBlKZBhXlGGXNUqAZD");
			UserOperations userOperations = facebookTemplate.userOperations();
			String userName = userOperations.getUserProfile().getName();
			String facebookUserId = userOperations.getUserProfile().getId();
			String userLink = userOperations.getUserProfile().getLink();
			List<Reference> friends = facebookTemplate.friendOperations().getFriends();
			for(Reference r: friends){
				print(r.getId() + "  " + r.getName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		
		/*
		Long a = System.currentTimeMillis();
		
		for(int i = 0;i < 10000000; i++){
			Map<String, Long> things = new HashMap<String, Long>();
			things.getClass();
			things = null;
		}
		
		Long b = System.currentTimeMillis();
		print(b-a);
		
		
		Long c = System.currentTimeMillis();
		for(int i = 0;i < 40000000; i++){
			Long[] blas = {1L};
			blas.getClass();
			blas = null;
		}
		
		Long d = System.currentTimeMillis();
		print(d-c);
		
		
		*/
		
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUsername("thething01");
		ds.setPassword("karuaabits");
		ds.setUrl("jdbc:mysql://localhost:3306/thething01");
		ds.setMaxActive(10);
		ds.setMaxIdle(5);
		ds.setInitialSize(5);
		ds.setValidationQuery("SELECT 1");
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		ThingDao thingDao = new ThingDao();
		Photo p = new Photo();
		p.setAuthorId(2L)
		.setCommentCount(3)
		.setDate(new Date())
		.setDescription("insertTest")
		.setInBlog(true)
		.setPriv(false)
		.setPublished(true)
		.setTitle("Esimene foto");
		thingDao.insertThing(p);
		
		
		
		
		/*
		ThingFilter tf = new ThingFilter();
		String startPage = "type:a,p";
		startPage = startPage.substring(5);
		for(String type: startPage.split(",")){
			tf.addType(ThingType.fromString(type));
		}
		
		tf.setPage(0);
		tf.setPageSize(30);
		tf.setOrderBy("t.order");
		tf.setOrderHow("ASC");
		print(tf.createQuery());
		for(Object s: tf.getTypes().toArray()){
			print(s);
		}
			*/
		
			/*
		
		String test = "tag:alfa,beeta,gamma";
		test = test.substring(4);
		String[] tests = test.split(",");
		for(String t: tests){
			System.out.println(t);
		}
		
		ThingType ttt = ThingType.fromString("a");
		System.out.println(ttt);
		
		
		ThingFilter tf = new ThingFilter();
		tf.setType(ThingType.PHOTO);
		tf.addType(ThingType.ARTICLE);
		tf.addType(ThingType.PHOTO_ARTICLE);
		tf.setPublished(true);
		tf.setAuthorId(2);
		tf.setEndDate(new Date());
		tf.setStartDate(new Date());
		tf.setUserId((long) 2);
		tf.setOrderBy("t.date");
		tf.setPage(3);
		tf.setPageSize(20);
		tf.setTags(new HashSet<String>(Arrays.asList("a", "b")));
		System.out.println(tf.createQuery());
		*/
		
		/*
		System.out.println(tf.toString());
		List<AbstractThing> things = new ArrayList<AbstractThing>();
		things.add(giveThing());
		things.get(0).getComments().add(new Comment(2L, 2L, "text ", new Date(), " author", null));
		AbstractThing t = things.get(0);
		System.out.println(t.getComments().get(0).getText());
		Object o = giveThing();
		System.out.println(o.getClass());
		if(o instanceof AbstractThing){
			System.out.println("true");
		
		}
		BeanMap m = new BeanMap(t);
		System.out.println(m.get("fileName"));
		if(t instanceof Photo){
			Photo p = (Photo)t;
			System.out.println(p.getFileName());
		}
		
		
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		java.sql.Connection c;
		try {
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/thething01", "thething01", "karuaabits");
		
			PreparedStatement ps = c.prepareStatement("select t.*, tags.*, c.* from things t left join thingTags tags on tags.thingId = t.id left join comments c on t.id = c.thingId left join userTags ut on ut.tag = tags.tag where t.type in (1, 2, 3)  AND t.published = true and t.priv != 1 or ut.userId=2;");
			
			if(ps.execute()){
				
				ResultSet rs = ps.getResultSet();
				while(rs.next()){
					Long cId = rs.getLong("c.id");
					System.out.println("success");
					System.out.println(cId);
					Object obj = rs.getObject("tags.tag");
					System.out.println(obj.getClass());
					if(o instanceof String){
						String s = (String)o;
						System.out.println(s);
					}
					String s2 =rs.getString("text");
					System.out.println(s2);
				}
				c.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			
		}
		*/
		
	
	}

	
	public static void print(Object o){
		System.out.println(o);
	}
	
	private static AbstractThing giveThing(){
		Photo p = new Photo();
		p.setFileName("fileName222");
		return p;
	}
}
