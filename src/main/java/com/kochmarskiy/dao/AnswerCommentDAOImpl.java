package com.kochmarskiy.dao;

import com.kochmarskiy.item.AnswerComment;
import com.kochmarskiy.item.Comment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Кочмарский on 06.12.2015.
 */
public class AnswerCommentDAOImpl implements CommentDAO<AnswerComment> {

    private JdbcTemplate jdbcTemplate;
    public AnswerCommentDAOImpl(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public void add(AnswerComment comment) {

    }

    @Override
    public List<AnswerComment> commentsFor(int commentId, int count) {
        String sql = "SELECT * FROM answer_comment WHERE ID_COMMENT=" + commentId;

        List<AnswerComment> list = jdbcTemplate.query(sql,(ResultSet rs, int rowNum)-> {

                    return new AnswerComment(rs.getInt("ID_COMMENT"),rs.getString("ANSWER_COMMENT"),rs.getString("ANICKNAME"));

                }
        );
        return list;
    }

    @Override
    public int count(int itemId) {
        return 0;
    }
}
