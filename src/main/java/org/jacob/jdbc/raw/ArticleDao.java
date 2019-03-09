package org.jacob.jdbc.raw;

import java.util.List;

/**
 * Data Access Object. 데이터베이스에 접속해서 데이터를 조작하는 인터페이스.
 * 
 * @author Jacob
 */
public interface ArticleDao {
	
	/**
	 * 목록
	 */
	List<Article> listArticles();

	/**
	 * 조회
	 */
	Article getArticle(String articleId);

	/**
	 * 등록
	 */
	void addArticle(Article article);

	/**
	 * 수정
	 */
	void updateArticle(Article article);

	/**
	 * 삭제
	 */
	void deleteArticle(String articleId);
}
