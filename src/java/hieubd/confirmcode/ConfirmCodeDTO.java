/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.confirmcode;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author CND
 */
public class ConfirmCodeDTO implements Serializable {

    private String email;
    private int code;
    Timestamp currentDate;

    public ConfirmCodeDTO() {
    }

    public ConfirmCodeDTO(String email, int code, Timestamp currentDate) {
        this.email = email;
        this.code = code;
        this.currentDate = currentDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Timestamp getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Timestamp currentDate) {
        this.currentDate = currentDate;
    }

}
