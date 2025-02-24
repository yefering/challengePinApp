package com.qaautomation.models.Response.EntriesResponse;

public class Item {
    private String cat;
    private String desc;
    private int id;
    private String img;
    private double price;
    private String title;

    // Getters y Setters
    public String getCat() { return cat; }
    public void setCat(String cat) { this.cat = cat; }

    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getImg() { return img; }
    public void setImg(String img) { this.img = img; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}