package com.kochmarskiy.entity;

/**
 * Created by Кочмарский on 02.12.2015.
 */
public class ItemShortDescribe {
    private String name;
    private int price;
    private String pathToImage;
    private String linkItem;

    public ItemShortDescribe()
    {

    }
    public ItemShortDescribe(String name,int price,String pathToImage,String linkItem)
    {
        this.name=name;
        this.pathToImage=pathToImage;
        this.linkItem=linkItem;
        this.price=price;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPathToImage() {
        return pathToImage;
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }

    public String getLinkItem() {
        return linkItem;
    }

    public void setLinkItem(String linkItem) {
        this.linkItem = linkItem;
    }
}
