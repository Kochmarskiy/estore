package com.kochmarskiy.item;

/**
 * Created by Кочмарский on 06.12.2015.
 */
public class AnswerComment {
    private int idComment;
    private String answerComment;
    private String nickName;

    public AnswerComment() {
    }

    public AnswerComment(int idComment, String answerComment,String nickName) {
        this.answerComment = answerComment;
        this.idComment = idComment;
        this.nickName = nickName;
    }
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public String getAnswerComment() {
        return answerComment;
    }

    public void setAnswerComment(String answerComment) {
        this.answerComment = answerComment;
    }
}
