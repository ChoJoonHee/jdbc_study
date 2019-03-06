# jdbc_study

이 앱을 실행하기 전에 먼저 데이터베이스를 설정해야 한다.\
데이터베이스는 MariaDB를 사용한다.\
root로 접속해서 다음과 같이 schema를 만들고 사용자를 만든다.

SCHEMA에 자신이 사용할 schema 이름을 적는다. 예) mydb\
USERNAME에 schema에 접속할 사용자를 적는다. 예) jacob

$ mysql -uroot -p\
Enter password:\

MariaDB [(none)]> grant all on SCHEMA.* to USERNAME@localhost identfied by 'PASSWORD';\
MariaDB [(none)]> quit

$ mysql -uUSERNAME -p\
Enter password:\

MariaDB [(none)]> use SCHEMA;\
MariaDB [SCHEMA]> CREATE TABLE article (\
  articleId int primary key AUTO_INCREMENT,\
  title varchar(100) NOT NULL,\
  content text NOT NULL,\
  userId int NOT NULL,\
  name varchar(20) NOT NULL,\
  cdate datetime NOT NULL DEFAULT current_timestamp()\
);

MariaDB [SCHEMA]>


