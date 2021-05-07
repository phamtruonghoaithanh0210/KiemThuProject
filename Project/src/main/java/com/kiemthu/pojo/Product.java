/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.pojo;

import java.math.BigDecimal;

/**
 *
 * @author acer
 */
public class Product {
     @Override
    public String toString(){
        return this.name;
    }
    
    public String getPath_img() {
        return path_img;
    }


    public void setPath_img(String path_img) {
        this.path_img = path_img;
    }
  
    
    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the categoryid
     */
    public int getCategoryid() {
        return categoryid;
    }

    /**
     * @param categoryid the categoryid to set
     */
    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }
    private int id;
    private String name;
    private BigDecimal price;
    private String description;
    private int categoryid;
    private int quantity;
    private String path_img;


   
 
}
