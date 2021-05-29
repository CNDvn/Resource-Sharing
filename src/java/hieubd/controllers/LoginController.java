/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.controllers;

import hieubd.confirmcode.ConfirmCodeDAO;
import hieubd.users.UsersDAO;
import hieubd.users.UsersDTO;
import hieubd.utils.MyConstants;
import hieubd.utils.SendMailUtil;
import hieubd.utils.VerifyRecaptchaUtil;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author CND
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    private final String SEARCH_CONTROLLER = "SearchController";
    private final String LOGIN_PAGE = "login.jsp";
    private final String CONFIRM_EMAIL_PAGE = "confirmEmail.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String userID = request.getParameter("userID");
        String password = request.getParameter("password");
        String url = LOGIN_PAGE;
        boolean sendCode = false;
        UsersDTO dto = null;
        try {
            boolean valid = true;
            String errorString = null;

            String recaptchaResponse = request.getParameter("g-recaptcha-response");
            //Verify CAPTCHA.
            valid = VerifyRecaptchaUtil.verify(recaptchaResponse);
            if (!valid) {
                errorString = "Captcha invalid!";
                request.setAttribute("errorString", errorString);
            }
            if (valid) {
                UsersDAO dao = new UsersDAO();
                dto = dao.checkLogin(userID, password);
                if (dto != null & userID.trim().length() != 0 & password.trim().length() != 0) {
                    if (dto.getStatusID() == MyConstants.STATUS_ACTIVE) {
                        HttpSession session = request.getSession();
                        session.setAttribute("user", dto);
                        response.sendRedirect(SEARCH_CONTROLLER);
                        return;
                    }
                    if (dto.getStatusID() == MyConstants.STATUS_NEW) {
                        sendCode = true;
                        request.setAttribute("email", dto.getEmail());
                        url = CONFIRM_EMAIL_PAGE;
                        request.getRequestDispatcher(url).forward(request, response);
                        if (sendCode) {
                            ConfirmCodeDAO codeDao = new ConfirmCodeDAO();
                            int confirmCode = codeDao.updateCode(dto.getEmail());
                            SendMailUtil.sendMail(dto.getEmail(), dto.getName(), confirmCode);
                        }
                        return;
                    }
                } else {
                    errorString = "UserID or Password invalid!";
                    request.setAttribute("errorString", errorString);
                }
            }
            request.getRequestDispatcher(url).forward(request, response);
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
