# jdbc_study

## 데이터베이스 설정

이 것은 데이터베이스에 접속해서 데이터를 읽고 쓰는 자바 애플리케이션이다.

이 애플리케이션을 실행하기 전에 먼저 데이터베이스를 설정해야 한다.\
데이터베이스는 MariaDB를 사용한다.\
root로 접속해서 다음과 같이 스키마를 만들고 사용자를 만든다.

*SCHEMA* : 자신이 사용할 스키마(데이터베이스). 예) mydb\
*USERNAME* : 스키마에 접속할 사용자. 예) jacob\
*PASSWORD* : 사용자의 비밀번호. 예) xxxxxxxx

<pre>
$ mysql -uroot -p
Enter password:
</pre>

<pre>
MariaDB [(none)]> create schema <i>SCHEMA</i>;
MariaDB [(none)]> grant all on <i>SCHEMA</i>.* to <i>USERNAME</i>@localhost identified by '<i>PASSWORD</i>';
MariaDB [(none)]> quit
</pre>

root 접속을 끊고, 위에서 만든 사용자로 접속해서 스키마에 테이블을 생성한다.

<pre>
$ mysql -u<i>USERNAME</i> -p
Enter password:
</pre>

<pre>
MariaDB [(none)]> use <i>SCHEMA</i>;
MariaDB [<i>SCHEMA</i>]> CREATE TABLE article (
  articleId int primary key AUTO_INCREMENT,
  title varchar(100) NOT NULL,
  content text NOT NULL,
  userId int NOT NULL,
  name varchar(20) NOT NULL,
  cdate datetime NOT NULL DEFAULT current_timestamp()
);

MariaDB [<i>SCHEMA</i>]>
</pre>

이제 애플리케이션을 실행할 준비가 되었다.

## 클래스 설명

|클래스|설명|
|---|---|
|Article|도메인 오브젝트. 데이터베이스의 article 테이블에 매핑한다. article 테이블에서 데이터를 가져오거나 article 테이블에 데이터를 저장할 때 데이터를 담는다.|
|ArticleDao|Data Access Object. 데이터베이스에 접속해서 데이터를 조작하는 인터페이스.|
|ArticleDaoImplUsingRawJdbc|ArticleDao 인터페이스의 구현 클래스. 순수 JDBC 코드로 구현한다.|
|DaoException|SQLException을 wrapping하는 RuntimeException.|
|ArticleDaoImplUsingTemplate|ArticleDao 인터페이스의 구현 클래스. JdbcTemplate을 사용한다.|
|JdbcTemplate|JDBC helper 클래스.|
|RowMapper|mapRow() 
