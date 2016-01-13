package com.kochmarskiy.controller;

import com.kochmarskiy.dao.ItemDAO;
import com.kochmarskiy.dao.ItemShortDescribeDAO;
import com.kochmarskiy.entity.Item;
import com.kochmarskiy.entity.ItemShortDescribe;
import com.kochmarskiy.entity.Order;
import com.kochmarskiy.entity.User;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.tomcat.util.http.fileupload.MultipartStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import sun.misc.BASE64Decoder;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
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
    @RequestMapping(value="/order", method=RequestMethod.POST, consumes = {"application/json"})
    public void order(@RequestBody Order order){
        System.out.println(order);
    }


    @RequestMapping(value="listofbasket", method=RequestMethod.POST, consumes = {"application/json"})
    public List<ItemShortDescribe> listOfBasket(@RequestBody List<Integer> arr){
        return itemShortDescribeDAO.listItemForIds(arr);
    }


    @RequestMapping(value="/addItem", method=RequestMethod.POST, consumes = {"application/json"})
    public void addItem(@RequestBody Item item, @AuthenticationPrincipal User user ) {
if(!user.getRank().getName().equals("ROLE_ADMIN")) return;
        itemDAO.add(item);

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
