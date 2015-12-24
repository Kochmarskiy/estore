package com.kochmarskiy.dao;

import com.kochmarskiy.item.Comment;

import java.util.List;

/**
 * Created by Кочмарский on 06.12.2015.
 */
public interface CommentDAO<T> {
    public void add(T comment);

    public List<T> commentsFor(int id, int count);
    public int count(int itemId);
}