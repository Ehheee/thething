insert into thingTags (tag, thingId) values (?, ?) ;
insert into comments (thingId, text, date, author) values(:thingId, :text, :date, :author) ;
insert into userTags (tag, userId) values (?, ?) ;
insert into things (title, type, date, description, authorId, priv, published, text, fileName) values (:title, :type, :date, :description, :authorId, :priv, :published, :text, :fileName) ;

insert into things (title, type, date, description, authorId, priv, published, text, fileName) values ("uimastid", 1, sysdate() , "kirjeldus", 1, 1, 1, "jutt uimastitest text text text", null) ;
insert into things (title, type, date, description, authorId, priv, published, text, fileName) values ("alkohol", 1, sysdate() , "kirjeldus", 1, 1, 1, "jutt alkoholist text text text", null) 
insert into things (title, type, date, description, authorId, priv, published, text, fileName) values ("tubakas", 1, sysdate() , "kirjeldus", 1, 1, 1, "jutt tubakast text text text", null) 
insert into things (title, type, date, description, authorId, priv, published, text, fileName) values ("läbu", 1, sysdate() , "kirjeldus", 1, 1, 1, "jutt läbust text text text", null) 
insert into things (title, type, date, description, authorId, priv, published, text, fileName) values ("avalik", 1, sysdate() , "kirjeldus", 1, 0, 1, "jutt avalikul teemal text text text", null) 

insert into thingTags (tag, thingId) values ("head", 2) ;
insert into thingTags (tag, thingId) values ("head", 3) ;
insert into thingTags (tag, thingId) values ("halvad", 1) ;
insert into thingTags (tag, thingId) values ("head", 4);
insert into thingTags (tag, thingId) values ("halvad", 4);


insert into comments (thingId, text, date, author) values(4, "hea läbu oli", sysdate(), "maido") ;
insert into comments (thingId, text, date, author) values(4, "oli oli", sysdate(), "peeter") ;

insert into userTags (userId, tag) values (1, "head") ;
insert into userTags (userId, tag) values (1, "halvad") ;
insert into userTags (userId, tag) values (2, "head") ;