/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.controllers;

import hieubd.confirmcode.ConfirmCodeDAO;
import hieubd.users.UsersDAO;
import hieubd.users.UsersDTO;
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
@WebServlet(name = "SendCodeAgainController", urlPatterns = {"/SendCodeAgainController"})
public class SendCodeAgainController extends HttpServlet {

    private final String CONFIRM_EMAIL_PAGE = "confirmEmail.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String email = request.getParameter("email");
        String url = CONFIRM_EMAIL_PAGE;

        try {
            ConfirmCodeDAO codeDao = new ConfirmCodeDAO();
            int confirmCode = codeDao.updateCode(email);
            UsersDTO dto = new UsersDAO().checkLoginGoogle(email);
            request.setAttribute("user", dto);
            request.getRequestDispatcher(url).forward(request, response);
            new SendMailUtil().sendMail(dto.getEmail(), dto.getName(), confirmCode);
        } catch (AddressException e) {
            log("Error AddressException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (MessagingException e) {
            log("Error MessagingException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (NamingException e) {
            log("Error NamingException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (SQLException e) {
            log("Error SQLException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (Exception e) {
            log("Error Exception at " + this.getClass().getName() + ": " + e.getMessage());
        } finally {
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
