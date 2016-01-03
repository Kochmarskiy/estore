package com.kochmarskiy.controller;

import com.kochmarskiy.dao.CommentDAO;
import com.kochmarskiy.entity.AnswerComment;
import com.kochmarskiy.entity.Comment;
import com.kochmarskiy.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * Created by Кочмарский on 06.12.2015.
 */
@RestController
@RequestMapping(value="/comment")
public class CommentController {
    @Autowired
    @Qualifier("CommentDAO")
    private CommentDAO<Comment> commentDAO;
    @Autowired
    @Qualifier("AnswerCommentDAO")
    private CommentDAO<AnswerComment> answerCommentDAO;

    @RequestMapping(value="/datacomment",params = {"id","c"}, method = RequestMethod.GET)
    public List<Comment> commentsForItem(@RequestParam(value = "id") int itemId, @RequestParam(value = "c") int count)
    {
       return commentDAO.commentsFor(itemId,count);
    }
    @RequestMapping(value="addcomment", method = RequestMethod.POST)
    public void addComment(@RequestBody Comment comment)
    {

        System.out.println(comment + " " + commentDAO.count(comment.getItemId()));
        commentDAO.add(comment);

    }
    @RequestMapping(value="count", params={"item"}, method = RequestMethod.GET)
    public int count(@RequestParam(value = "item") int itemID)
    {
        return commentDAO.count(itemID);
    }

    @RequestMapping(value="delete", method = RequestMethod.POST)
    public void delete(@RequestParam(value="id") Integer id, @AuthenticationPrincipal User user){
        commentDAO.delete(id);
    }

}
