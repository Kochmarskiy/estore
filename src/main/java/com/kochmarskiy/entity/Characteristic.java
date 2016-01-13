package com.kochmarskiy.entity;

/**
 * Created by Кочмарский on 05.12.2015.
 */
public class Characteristic {
    private String denomination;
    private String content;

    public String getDenomination() {
        return denomination;
    }

    public Characteristic(String denomination, String content) {
        this.content = content;
        this.denomination = denomination;
    }
    public Characteristic(){

    }
    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @Override
    public String toString() {
        return "Characteristic{" +
                "denomination='" + denomination + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
