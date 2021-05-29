/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.categories;

import java.io.Serializable;

/**
 *
 * @author CND
 */
public class CategoriesDTO implements Serializable {

    private int categoryID;
    private String name;

    public CategoriesDTO() {
    }

    public CategoriesDTO(int categoryID, String name) {
        this.categoryID = categoryID;
        this.name = name;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
