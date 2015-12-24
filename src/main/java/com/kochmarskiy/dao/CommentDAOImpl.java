package com.kochmarskiy.dao;

import com.kochmarskiy.item.AnswerComment;
import com.kochmarskiy.item.Characteristic;
import com.kochmarskiy.item.Comment;
import com.kochmarskiy.item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Кочмарский on 06.12.2015.
 */
public class CommentDAOImpl implements CommentDAO<Comment> {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier("AnswerCommentDAO")
    private CommentDAO<AnswerComment> answerCommentDAO;

    public CommentDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public void add(Comment comment) {
        String sql = "INSERT INTO comment (NICKNAME,COMMENT,ID_PRODUCT) VALUES(?,?,?)";
        jdbcTemplate.execute(sql, new PreparedStatementCallback<Object>() {
                    @Override
                    public Object doInPreparedStatement(PreparedStatement preparedStatement)
                            throws SQLException, DataAccessException {

                        preparedStatement.setInt(3,comment.getItemId());
                        preparedStatement.setString(1, comment.getNickName());
                        preparedStatement.setString(2, comment.getComment());

                        preparedStatement.execute();
                        return null;
                    }
                }

        );
    }

    @Override
    public List<Comment> commentsFor(int itemId, int startWith) {
        int countCommentForPage = 10;
        int countCommentForItem  = count(itemId);
        if(startWith>countCommentForItem) throw new IndexOutOfBoundsException();
        System.out.println((countCommentForItem-startWith-countCommentForPage) + " " + (countCommentForItem-startWith) );

        String sql = "SELECT * FROM comment WHERE ID_PRODUCT=" + itemId+" ORDER BY ID_COMMENT DESC LIMIT "+startWith+","+countCommentForPage;


        List<Comment> list = jdbcTemplate.query(sql, (ResultSet rs, int rowNum) -> {

                    return new Comment(rs.getInt("ID_COMMENT"), rs.getString("COMMENT"), itemId,
                            answerCommentDAO.commentsFor(rs.getInt("ID_COMMENT"),0),rs.getString("NICKNAME"));
                }
        );
        return list;
    }

    @Override
    public int count(int itemId) {
        String sql = "SELECT COUNT(*) from comment WHERE ID_PRODUCT="+itemId;
       return jdbcTemplate.queryForObject(sql, Integer.class);

 }
}
