/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.users;

import java.io.Serializable;

/**
 *
 * @author CND
 */
public class UsersRegisterError implements Serializable {

    private String emailErr, passwordErr, nameErr, phoneErr;

    public UsersRegisterError() {
    }

    public String getEmailErr() {
        return emailErr;
    }

    public void setEmailErr(String emailErr) {
        this.emailErr = emailErr;
    }

    public String getPasswordErr() {
        return passwordErr;
    }

    public void setPasswordErr(String passwordErr) {
        this.passwordErr = passwordErr;
    }

    public String getNameErr() {
        return nameErr;
    }

    public void setNameErr(String nameErr) {
        this.nameErr = nameErr;
    }

    public String getPhoneErr() {
        return phoneErr;
    }

    public void setPhoneErr(String phoneErr) {
        this.phoneErr = phoneErr;
    }

}
