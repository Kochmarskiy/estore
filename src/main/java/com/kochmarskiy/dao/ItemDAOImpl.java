package com.kochmarskiy.dao;

import com.kochmarskiy.entity.Characteristic;
import com.kochmarskiy.entity.Item;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import sun.misc.BASE64Decoder;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    private String getRandomString(){
        StringBuffer sb = new StringBuffer("img");

        for(int i=0;i<10;i++)
            sb.append((char)((int)(Math.random()*25)+'a'));
        return sb.toString();
    }
    private void saveImageBASE64(String base64, String path){
        try (OutputStream base64OutputStream = new FileOutputStream(path)) {
            BASE64Decoder decoder = new BASE64Decoder();
            base64OutputStream.write(decoder.decodeBuffer(base64.split(",")[1]));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void add(Item item) {
        String name = getRandomString()+".jpg";
        saveImageBASE64(item.getFiles()[0], "E://images/" + name);
        item.setPathToImage("/static/images/items/" + name);

        String sql = "INSERT INTO product (`NAME_PRODUCT`, `DESCRIBE`, `ID_CATEGORY`, `IMAGE`, `PRICE`) VALUES(?,?,?,?,?)";
        jdbcTemplate.execute(sql, new PreparedStatementCallback<Object>() {
                    @Override
                    public Object doInPreparedStatement(PreparedStatement preparedStatement)
                            throws SQLException, DataAccessException {

                        preparedStatement.setString(1,item.getName());
                        preparedStatement.setString(2,item.getDescribe());
                        preparedStatement.setInt(3, item.getCategory());
                        preparedStatement.setString(4, item.getPathToImage());
                        preparedStatement.setInt(5,item.getPrice());
                        preparedStatement.execute();
                        return null;
                    }
                }



        );

        int k = jdbcTemplate.queryForObject("SELECT ID_PRODUCT from product where IMAGE='" + item.getPathToImage() + "'",
                Integer.class);
        for(Characteristic ch : item.getCharacteristics()){
            String sq = "INSERT INTO characteristic VALUES('"
                    + k+ "','"
                    + ch.getDenomination()+"','"
                    + ch.getContent()+"')";
            jdbcTemplate.execute(sq);
        }





    }

    @Override
    public int count(int category) {
        String sql = "SELECT COUNT(*) from PRODUCT WHERE ID_CATEGORY="+category;
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
