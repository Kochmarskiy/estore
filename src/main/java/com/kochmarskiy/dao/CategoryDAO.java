package com.kochmarskiy.dao;

import com.kochmarskiy.entity.Category;

import java.util.List;

/**
 * Created by Кочмарский on 11.12.2015.
 */
public interface CategoryDAO {

    public void add(Category category);
    public List<Category> getCategories();
}
