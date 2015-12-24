package com.kochmarskiy.dao;

import com.kochmarskiy.item.Characteristic;
import com.kochmarskiy.item.Item;
import com.kochmarskiy.item.ItemShortDescribe;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Кочмарский on 05.12.2015.
 */
public class ItemDAOImpl implements ItemDAO {
    private JdbcTemplate jdbcTemplate;
    public ItemDAOImpl(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    private List<Characteristic> getCharacteristics(int id)
    {
        String sql = "SELECT * FROM characteristic WHERE ID_PRODUCT=" + id;


        List<Characteristic> list = jdbcTemplate.query(sql,(ResultSet rs, int rowNum)-> {
                    return new Characteristic(rs.getString("DENOMINATION"),rs.getString("CONTENT"));
                }
        );
        return list;
    }
    @Override
    public Item get(int id) {

        String sql = "SELECT * FROM product  WHERE ID_PRODUCT=" + id;
        return jdbcTemplate.query(sql, new ResultSetExtractor<Item>() {

            @Override
            public Item extractData(ResultSet rs) throws SQLException,
                    DataAccessException {
                if (rs.next()) {
                    return new Item(
                            id
                            ,rs.getString("NAME_PRODUCT"),
                            rs.getInt("PRICE"),
                            rs.getString("DESCRIBE"),
                            rs.getString("IMAGE"),
                                    getCharacteristics(id),
                                    rs.getInt("ID_CATEGORY"));

                }

                return null;
            }
        });
    }

    @Override
    public void add(Item item) {

    }

    @Override
    public int count(int category) {
        String sql = "SELECT COUNT(*) from PRODUCT WHERE ID_CATEGORY="+category;
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
