/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.controllers;

import hieubd.confirmcode.ConfirmCodeDAO;
import hieubd.users.UsersDAO;
import hieubd.users.UsersRegisterError;
import hieubd.utils.SendMailUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author CND
 */
@WebServlet(name = "RegisterController", urlPatterns = {"/RegisterController"})
public class RegisterController extends HttpServlet {

    private final String REGISTER_PAGE = "register.jsp";
    private final String CONFIRM_EMAIL_PAGE = "confirmEmail.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String url = REGISTER_PAGE;
        UsersRegisterError registerError = new UsersRegisterError();
        int confirmCode = 0;
        boolean valid = true;
        try {
            String regexEmail = "^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$";
            if (!email.matches(regexEmail)) {
                registerError.setEmailErr("Email invalid");
                valid = false;
            }
            if (password.trim().length() > 50 || password.trim().length() < 6) {
                registerError.setPasswordErr("password can not empty or greater than 50 and less than 6");
                valid = false;
            }
            if (name.trim().length() == 0) {
                registerError.setNameErr("Name can not empty");
                valid = false;
            }
            if (phone.trim().length() != 0) {
                if (phone.trim().length() < 10 || phone.trim().length() > 13) {
                    registerError.setPhoneErr("Phone number invalid");
                    valid = false;
                }
            }

            if (valid) {
                UsersDAO userDao = new UsersDAO();
                userDao.addNewUser(email, password, name, phone, address);
                ConfirmCodeDAO codeDao = new ConfirmCodeDAO();
                confirmCode = codeDao.addNewCode(email);
                request.setAttribute("email", email);
                url = CONFIRM_EMAIL_PAGE;
            } else {
                request.setAttribute("error", registerError);
            }
        } catch (SQLException e) {
            log("Error SQLException at " + this.getClass().getName() + ": " + e.getMessage());
            registerError.setEmailErr("Email existed");
            request.setAttribute("error", registerError);
        } catch (NamingException e) {
            log("Error NamingException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (Exception e) {
            log("Error Exception at " + this.getClass().getName() + ": " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);

            try {
                if (valid) {
                    SendMailUtil.sendMail(email, name, confirmCode);
                }
            } catch (AddressException e) {
                log("Error AddressException at " + this.getClass().getName() + ": " + e.getMessage());
            } catch (MessagingException e) {
                log("Error MessagingException at " + this.getClass().getName() + ": " + e.getMessage());
            } catch (Exception e) {
                log("Error Exception at " + this.getClass().getName() + ": " + e.getMessage());
            }

            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
