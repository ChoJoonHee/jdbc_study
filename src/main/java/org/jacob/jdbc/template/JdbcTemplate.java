package org.jacob.jdbc.template;

import org.jacob.jdbc.raw.DaoException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    DataSource dataSource;

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * select 목록을 가져오는 템플릿 메서드
     */
    public <T> List<T> queryForList(String query, Object[] params, RowMapper<T> rowMapper) throws DaoException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            setParams(ps, params);
            List<T> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                list.add(rowMapper.mapRow(rs));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e);
        }
    }

    /**
     * select 한 줄을 가져오는 템플릿 메서드
     */
    public <T> T queryForObject(String query, Object[] params, RowMapper<T> rowMapper) throws DaoException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            setParams(ps, params);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rowMapper.mapRow(rs);
            else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e);
        }
    }

    /**
     * insert, update, delete를 하는 템플릿 메서드
     */
    public int update(String query, Object... params) throws DaoException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            setParams(ps, params);
            if (params != null) {
                for (int i = 0; i < params.length; i++)
                    ps.setObject(i + 1, params[i]);
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e);
        }
    }

    /**
     * preparedStatement에 파라미터 세팅
     */
    private void setParams(PreparedStatement ps, Object[] params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++)
                ps.setObject(i + 1, params[i]);
        }
    }
}
