package com.kochmarskiy.item;

import java.util.List;
import java.util.Objects;

/**
 * Created by Кочмарский on 05.12.2015.
 */
public class Item {
    private int id;
    private String name;
    private int price;
    private String describe;
    private String pathToImage;
    private List<Characteristic> characteristics;
    private int category;

    public Item()
    {

    }
    public Item(int id,String name, int price, String describe, String pathToImage,
            List<Characteristic> characteristics, int category) {
        this.id=id;
        this.name = name;
        this.price = price;
        this.describe = describe;
        this.pathToImage = pathToImage;
        this.characteristics = characteristics;
        this.category = category;
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getPathToImage() {
        return pathToImage;
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }

    public List<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Item))
            return false;
        Item item = (Item) o;
        return Objects.equals(price, item.price) &&
                Objects.equals(category, item.category) &&
                Objects.equals(name, item.name) &&
                Objects.equals(describe, item.describe) &&
                Objects.equals(pathToImage, item.pathToImage) &&
                Objects.equals(characteristics, item.characteristics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, describe, pathToImage, characteristics, category);
    }

}
