/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.pojo;

/**
 *
 * @author acer
 */
public class Receipt_Detail {
     private int id;
    private int productid;
    private int receiptid;
    private int quantity;
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
     * @return the productid
     */
    public int getProductid() {
        return productid;
    }

    /**
     * @param productid the productid to set
     */
    public void setProductid(int productid) {
        this.productid = productid;
    }

    /**
     * @return the receiptid
     */
    public int getReceiptid() {
        return receiptid;
    }

    /**
     * @param receiptid the receiptid to set
     */
    public void setReceiptid(int receiptid) {
        this.receiptid = receiptid;
    }

    /**
     * @return the quatity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quatity the quatity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
   
    
}
