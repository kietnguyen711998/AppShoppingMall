package com.example.appshopping.model;

public class Cart {
    public int productId;
    public String productName;
    public long price;
    public String productImage;
    public int productNumber;

    public Cart(int productId, String productName, long price, String productImage, int productNumber) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.productImage = productImage;
        this.productNumber = productNumber;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }

}
