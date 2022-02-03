package com.example.etlap;

public class Etel {
    private int id;
    private String name;
    private String category;
    private int price;
    private String detail;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Etel(int id, String name, String detail, int price, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.detail = detail;
    }
}
