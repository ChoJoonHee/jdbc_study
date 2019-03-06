package org.jacob.jdbc.raw;

import org.mariadb.jdbc.MariaDbDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ArticleDaoImplUsingRawJdbc implements ArticleDao {
    static final String LIST_ARTICLES = "SELECT articleId, title, name, cdate FROM article LIMIT 10";
    static final String GET_ARTICLE = "SELECT articleId, title, content, name, cdate FROM article WHERE articleId=?";
    static final String ADD_ARTICLE = "INSERT INTO article(title, content, userId, name) VALUES (?,?,?,?)";
    static final String UPDATE_ARTICLE = "UPDATE article SET title=?, content=? WHERE articleId=?";
    static final String DELETE_ARTICLE = "DELETE FROM article WHERE articleId=?";

    DataSource ds;

    /**
     * Default Constructor
     */
    public ArticleDaoImplUsingRawJdbc() {
        // db.properties 파일에서 url을 읽어서 dataSource 생성
        Properties props = new Properties();
        try(InputStream is = getClass().getResourceAsStream("/db.properties")) {
            props.load(is);
            ds = new MariaDbDataSource(props.getProperty("url"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 게시글 목록
     */
    @Override
    public List<Article> listArticles() throws DaoException {
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(LIST_ARTICLES);
             ResultSet rs = ps.executeQuery()) {
            List<Article> list = new ArrayList<>();
            while (rs.next()) {
                Article article = new Article();
                article.setArticleId(rs.getString("articleId"));
                article.setTitle(rs.getString("title"));
                article.setName(rs.getString("name"));
                article.setCdate(rs.getString("cdate"));
                list.add(article);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e);
        }
    }

    /**
     * 게시글 상세
     */
    @Override
    public Article getArticle(String articleId) throws DaoException {
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_ARTICLE)) {
            ps.setString(1, articleId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Article article = new Article();
                article.setArticleId(rs.getString("articleId"));
                article.setTitle(rs.getString("title"));
                article.setContent(rs.getString("content"));
                article.setName(rs.getString("name"));
                article.setCdate(rs.getString("cdate"));
                return article;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e);
        }
    }

    /**
     * 게시글 등록
     */
    @Override
    public void addArticle(Article article) throws DaoException {
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(ADD_ARTICLE)) {
            ps.setString(1, article.getTitle());
            ps.setString(2, article.getContent());
            ps.setString(3, article.getUserId());
            ps.setString(4, article.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e);
        }
    }

    /**
     * 게시글 수정
     */
    @Override
    public void updateArticle(Article article) throws DaoException {
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_ARTICLE)) {
            ps.setString(1, article.getTitle());
            ps.setString(2, article.getContent());
            ps.setString(3, article.getArticleId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e);
        }
    }

    /**
     * 게시글 삭제
     */
    @Override
    public void deleteArticle(String articleId) throws DaoException {
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_ARTICLE)) {
            ps.setString(1, articleId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e);
        }
    }
}
