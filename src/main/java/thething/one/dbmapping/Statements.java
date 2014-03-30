package thething.one.dbmapping;

import java.util.Date;

public final class Statements {

	final static String Select_users_By_userName = "select * from users u, left join externalUsers eu on u.id = eu.userId where u.userName = ? ;";
	final static String Delete_users_By_id = "delete from users where u.id = ? ;";
	final static String Delete_users_By_userName = "delete from users where userName = ? ;";
	final static String Select_users_For_Update = "select * from users where id = ? for update ;";
	final static String Update_users_By_userName_And_id = "update users set enabled=:enabled, externalUserId=:externalUserId, external=:external, eMail=:eMail, confirmed=:confirmed, registeredDate=:registeredDate, role=:role, password=:password, salt=:salt where userName=:userName and id=:id ;";
	final static String Insert_user = "insert into users (userName, enabled, externalUserId, eMail, confirmed, registeredDate, role, password, salt) values (:userName, :enabled, :externalUserId, :eMail, :confirmed, :registeredDate, :role, :password, :salt) ;";
	
	final static String Select_externalUsers_By_externalId_And_type = "select eu.*, u.* from externalUsers eu left join users u on eu.id = u.externalUserId where eu.externalId = :externalId and eu.loginType = :type ;";
	final static String Select_externalUsers_By_email = "select eu.*, u.* from externalUsers eu left join users u on eu.id = u.externalUserId  where eu.email = ? ;";
	final static String Select_externalUsers_By_id = "select eu.*, u.* from externalUsers eu left join users u on eu.id = u.externalUserId  where eu.id = ? ;";
	final static String Select_externalUsers_By_id_For_Update = "select * from externalUsers where id = ? for update;";
	final static String Delete_externalUsers_By_id = "delete from externalUsers where id = ? ;";
	final static String Update_externalUsers_By_id = "update externalUsers set loginType = :loginType, externalId = :externalId, fullName = :fullName, email = :email, refreshToken = :refreshToken, accessToken = :accessToken, tokenExpireDate = :tokenExpireDate, userId = :userId where id = :id ;";
	final static String Insert_externalUser = "insert into externalUsers (loginType, externalId, fullName, email, refreshToken, accessToken, tokenExpireDate, userId) values  (:loginType, :externalId, :fullName, :email, :refreshToken, :accessToken, :tokenExpireDate, :userId);";
	
	final static String Select_things_By_id = "select t.*, tags.tag from things t, thingTags tags where id = ? and tags.thingId = t.id ;";
	final static String Delete_things_By_id = "delete from things where id = ? ;";
	final static String Select_things_For_Update = "select * from things where id = ? for update ;";
	final static String Update_things_By_id = "update things set title=:title, date=:date, description=:description, authorId=:authorId, priv=:priv, published=:published, text=:text, fileName=:fileName where id = :id ;";
	final static String Insert_thing = "insert into things (title, type, date, description, authorId, priv, published, text, fileName) values (:title, :type, :date, :description, :authorId, :priv, :published, :text, :fileName) ;";
	
	
	final static String Select_thingTags = "select * from thingTags where thingId = ? ;";
	final static String Select_Distinct_Tags = "select distinct tag from thingTags ;";
	final static String Delete_thingTags = "delete from thingTags where tag = ? and thingId = ? ;";
	final static String Insert_thingTags = "insert into thingTags (tag, thingId) values (?, ?) ;";
	
	final static String Select_userTags = "select * from userTags where userId = ? ;";
	final static String Delete_userTags = "delete from userTags where userId = ? and tag = ? ;";
	final static String Insert_userTags = "insert into userTags (tag, userId) values (?, ?) ;";
	
	
	final static String Select_comments_By_thingId = "select * from comments where thingId = ? ;";
	final static String Select_comments_By_id = "select c.* from comments c for update where id = ? ;";
	final static String Delete_comments_By_id = "delete from comments where id = ? ;";
	final static String Select_comments_For_Update = "select c.* from comments c for update where id = ? ;";
	final static String Update_comments_By_id = "update comments set thingId=:thingId, text=:text, date=:date, author=:author where id=:id ;";
	final static String Insert_comment = "insert into comments (thingId, text, date, author) values(:thingId, :text, :date, :author) ;";
	
	final static String Select_Settings = "select * from settings ;";
	final static String Select_Settings_By_tag = "select * from settings where tag = ? ;";
	final static String Delete_Settings_By_id = "delete from settings where id = ? ;";
	final static String Insert_Settings = " ;"; //TODO
	final static String Update_Settings_By_id = " ;"; //TODO
}
