/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.controllers;

import hieubd.confirmcode.ConfirmCodeDAO;
import hieubd.confirmcode.ConfirmCodeDTO;
import hieubd.users.UsersDAO;
import hieubd.users.UsersDTO;
import hieubd.utils.MyConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;
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
@WebServlet(name = "ConfirmMailController", urlPatterns = {"/ConfirmMailController"})
public class ConfirmMailController extends HttpServlet {

    private final String CONFIRM_EMAIL_PAGE = "confirmEmail.jsp";
    private final String SEARCH_CONTROLLER = "SearchController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String confirmCode = request.getParameter("confirmCode");
        String email = request.getParameter("email");
        String url = CONFIRM_EMAIL_PAGE;
        try {
            ConfirmCodeDAO dao = new ConfirmCodeDAO();
            ConfirmCodeDTO dto = dao.getCode(email);
            if (confirmCode.trim().length() != 0 && confirmCode.equals(dto.getCode() + "")) {
                Timestamp currentDate = Timestamp.valueOf(LocalDateTime.now());
                Timestamp dateInDB = dto.getCurrentDate();
                long getDiff = currentDate.getTime() - dateInDB.getTime();
                long getDaysDiff = TimeUnit.MILLISECONDS.toMinutes(getDiff);
                if (getDaysDiff < MyConstants.TIME_OUT_CONFIRM_CODE) {
                    dao.resetCode(email);
                    UsersDAO userDao = new UsersDAO();
                    userDao.updatetatusActive(email);
                    UsersDTO user = userDao.checkLoginGoogle(email);
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    response.sendRedirect(SEARCH_CONTROLLER);
                    return;
                } else {
                    request.setAttribute("error", "expired code");
                    request.setAttribute("email", email);
                }
            } else {
                request.setAttribute("email", email);
                request.setAttribute("error", "Code invalid");
            }

            request.getRequestDispatcher(url).forward(request, response);
        } catch (NamingException e) {
            log("Error NamingException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (SQLException e) {
            log("Error NamingException at " + this.getClass().getName() + ": " + e.getMessage());
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
