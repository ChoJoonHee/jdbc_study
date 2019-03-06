CREATE TABLE article (
  articleId int primary key AUTO_INCREMENT,
  title varchar(100) NOT NULL,
  content text NOT NULL,
  userId int NOT NULL,
  name varchar(20) NOT NULL,
  cdate datetime NOT NULL DEFAULT current_timestamp()
);