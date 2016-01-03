package com.kochmarskiy.dao;

import com.kochmarskiy.entity.ItemShortDescribe;

import java.util.List;

/**
 * Created by Кочмарский on 02.12.2015.
 */
public interface ItemShortDescribeDAO {


    public void delete(int itemId);

    public ItemShortDescribe get(int itemId);

    public List<ItemShortDescribe> list(int startWith, int categoryId);
    public int count(int category);
    public List<ItemShortDescribe> listItemForIds(List<Integer> ids);

}
