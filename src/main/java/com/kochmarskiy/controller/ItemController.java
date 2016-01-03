package com.kochmarskiy.controller;

import com.kochmarskiy.dao.ItemDAO;
import com.kochmarskiy.dao.ItemShortDescribeDAO;
import com.kochmarskiy.entity.Item;
import com.kochmarskiy.entity.ItemShortDescribe;
import com.kochmarskiy.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Кочмарский on 02.12.2015.
 */
@RestController
@RequestMapping(value="/")
public class ItemController {
    @Autowired
    private ItemShortDescribeDAO itemShortDescribeDAO;
    @Autowired
    private ItemDAO itemDAO;
    @RequestMapping(value="/user", method = RequestMethod.GET)
    public User getUserInfo(@AuthenticationPrincipal User user) {
        return user;
    }

    @RequestMapping(value="/{page}", method = RequestMethod.GET)
    public String getPage(@PathVariable String page) throws IOException {
        if(!(page.equals("items") || page.equals("item"))) new RuntimeException("mapping");
        StringBuilder sb = new StringBuilder();
        try(Scanner sc = new Scanner(new ClassPathResource("static/index.html").getInputStream(),"UTF-8")){
            while(sc.hasNextLine())
                sb.append(sc.nextLine());

        }

        return sb.toString();
    }
    @RequestMapping(value="listofbasket", method=RequestMethod.POST, consumes = {"application/json"})
    public List<ItemShortDescribe> listOfBasket(@RequestBody List<Integer> arr){
        return itemShortDescribeDAO.listItemForIds(arr);
    }

    @RequestMapping(value="databooks", params={"category","c"}, method = RequestMethod.GET)
    public List<ItemShortDescribe> listbook(@RequestParam(value = "category") int category, @RequestParam(value = "c") int count)
    {
        return itemShortDescribeDAO.list(count, category);

    }
    @RequestMapping(value="dataitem", params = {"id"}, method = RequestMethod.GET)
    public Item item(@RequestParam(value = "id") int id){

        return itemDAO.get(id);
    }
    @RequestMapping(value="count", params = {"category"}, method = RequestMethod.GET)
    public Integer count(@RequestParam(value="category") int category){
        return itemDAO.count(category);
    }

}
