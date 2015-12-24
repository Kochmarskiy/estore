package com.kochmarskiy.dao;

import com.kochmarskiy.item.Category;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Кочмарский on 11.12.2015.
 */
public class CategoryDAOImpl implements CategoryDAO {
    private JdbcTemplate jdbcTemplate;
    public CategoryDAOImpl(DataSource dataSource)
    {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public void add(Category category) {

    }

    @Override
    public List<Category> getCategories()
        {
            String sql = "SELECT * FROM product_category";
            List<Category> list = jdbcTemplate.query(sql,(ResultSet rs, int rowNum)-> {
                        return new Category(rs.getInt("ID_CATEGORY"),rs.getString("NAME_CATEGORY"));
                    }
            );
            return list;
        }
    }

