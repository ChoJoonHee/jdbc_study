# jdbc_study

이 것은 데이터베이스에 접속해서 데이터를 읽고 쓰는 자바 애플리케이션이다.

이 애플리케이션을 실행하기 전에 먼저 데이터베이스를 설정해야 한다.\
데이터베이스는 MariaDB를 사용한다.\
root로 접속해서 다음과 같이 스키마를 만들고 사용자를 만든다.

SCHEMA : 자신이 사용할 스키마(데이터베이스). 예) mydb\
USERNAME : 스키마에 접속할 사용자. 예) jacob\
PASSWORD : 사용자의 비밀번호. 예) xxxxxxxx

>$ mysql -uroot -p\
>Enter password:

>MariaDB [(none)]> grant all on SCHEMA.* to USERNAME@localhost identified by 'PASSWORD';\
>MariaDB [(none)]> quit

root 접속을 끊고, 위에서 만든 사용자로 접속해서 스키마에 테이블을 생성한다.

$ mysql -uUSERNAME -p\
Enter password:

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

이제 애플리케이션을 실행할 준비가 되었다.
