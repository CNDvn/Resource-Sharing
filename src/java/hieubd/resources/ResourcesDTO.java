/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.resources;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author CND
 */
public class ResourcesDTO implements Serializable {

    private int resourceID, quantity, categoryID;
    private String name, color;
    private Date date;

    public ResourcesDTO() {
    }

    public ResourcesDTO(int resourceID, int quantity, int categoryID, String name, String color, Date date) {
        this.resourceID = resourceID;
        this.quantity = quantity;
        this.categoryID = categoryID;
        this.name = name;
        this.color = color;
        this.date = date;
    }

    public int getResourceID() {
        return resourceID;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Date getDate() {
        return date;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    

}
