/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.requests;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author CND
 */
public class RequestsDTO implements Serializable {

    int requestID, resourceID, statusID;
    String email;
    Date date;
    
    public RequestsDTO() {
    }

    public RequestsDTO(int requestID, int resourceID, int statusID, String email, Date date) {
        this.requestID = requestID;
        this.resourceID = resourceID;
        this.statusID = statusID;
        this.email = email;
        this.date = date;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public int getRequestID() {
        return requestID;
    }

    public int getResourceID() {
        return resourceID;
    }

    public String getEmail() {
        return email;
    }

    public Date getDate() {
        return date;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
