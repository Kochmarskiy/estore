package com.kochmarskiy.dao;

import com.kochmarskiy.entity.Item;

/**
 * Created by Кочмарский on 05.12.2015.
 */
public interface ItemDAO {
    public Item get(int id);
    public void add(Item item);
    public int count(int category);


}
