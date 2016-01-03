package com.kochmarskiy.controller;

import com.kochmarskiy.dao.CategoryDAO;
import com.kochmarskiy.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Кочмарский on 11.12.2015.
 */
@RestController
@RequestMapping(value="/category", method = RequestMethod.GET)
public class CategoryController {
    @Autowired
    private CategoryDAO categoryDAO;
    @RequestMapping(value="/list")
    public List<Category> getCategories(){
        return categoryDAO.getCategories();
    }
}
