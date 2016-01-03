package com.kochmarskiy.dao;

import com.kochmarskiy.entity.ItemShortDescribe;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Кочмарский on 02.12.2015.
 */
public class ItemShortDescribeDAOImpl implements ItemShortDescribeDAO {

    private JdbcTemplate jdbcTemplate;
    public ItemShortDescribeDAOImpl(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public void delete(int itemId) {
        String sql = "DELETE FROM product where ID_PRODUCT=?";
        jdbcTemplate.update(sql,itemId);
    }

    @Override
    public ItemShortDescribe get(int itemId) {

        String sql = "SELECT * FROM contact WHERE ID_PRODUCT=" + itemId;
        return jdbcTemplate.query(sql, new ResultSetExtractor<ItemShortDescribe>() {

            @Override
            public ItemShortDescribe extractData(ResultSet rs) throws SQLException,
                    DataAccessException {
                if (rs.next()) {
                    return new ItemShortDescribe(rs.getString("NAME_PRODUCT"),
                            rs.getInt("PRICE"),
                            rs.getString("IMAGE"),
                            ("id?=" + rs.getInt("ID")).toLowerCase());

                }

                return null;
            }
        });

    }
    @Override
    public List<ItemShortDescribe> list(int startWith,int categoryId) {
        int count = 12;
        String sql = "SELECT * FROM PRODUCT where ID_CATEGORY="+categoryId+" LIMIT "+startWith+", "+count;


        List<ItemShortDescribe> list = jdbcTemplate.query(sql,new ItemShort());

        return list;
    }
    @Override
    public int count(int categoryId) {
        String sql = "SELECT COUNT(*) from product WHERE ID_CATEGORY="+categoryId;
        return jdbcTemplate.queryForObject(sql, Integer.class);

    }
    public List<ItemShortDescribe> listItemForIds(List<Integer> ids){
        StringBuilder sql = new StringBuilder("Select * from PRODUCT where ID_PRODUCT IN (");
        for(int i=0;i<ids.size()-1;i++)  sql.append(ids.get(i)+",");
        sql.append(ids.get(ids.size()-1)+")");
        return jdbcTemplate.query(sql.toString(),new ItemShort());
    }


private static final class ItemShort implements RowMapper<ItemShortDescribe>{

    @Override
    public ItemShortDescribe mapRow(ResultSet rs, int i) throws SQLException {
        return new ItemShortDescribe(rs.getString("NAME_PRODUCT"),
                rs.getInt("PRICE"),
                rs.getString("IMAGE"),
                Integer.toString(rs.getInt("ID_PRODUCT")));
    }
}


}
