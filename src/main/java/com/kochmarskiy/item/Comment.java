package com.kochmarskiy.item;

import java.util.List;

/**
 * Created by Кочмарский on 06.12.2015.
 */
public class Comment {
    private int id;
    private String comment;
    private String nickName;
    private int itemId;
    private List<AnswerComment> answer;

    public Comment() {
    }



    public Comment(int id, String comment, int itemId, List<AnswerComment> answer,String nickName) {
        this.id = id;
        this.comment = comment;
        this.itemId = itemId;
        this.answer = answer;
        this.nickName = nickName;
    }
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public List<AnswerComment> getAnswer() {
        return answer;
    }

    public void setAnswer(List<AnswerComment> answer) {
        this.answer = answer;
    }
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", nickName='" + nickName + '\'' +
                ", itemId=" + itemId +
                ", answer=" + answer +
                '}';
    }
}
