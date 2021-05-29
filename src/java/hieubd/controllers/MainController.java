/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.controllers;

import hieubd.users.UsersDTO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author CND
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    private final String LOGIN_PAGE = "login.html";
    private final String LOGIN_CONTROLLER = "LoginController";
    private final String LOGOUT_CONTROLLER = "LogoutController";
    private final String REGISTER_CONTROLLER = "RegisterController";
    private final String LOGIN_GOOGLE_CONTROLLER = "LoginGoogleController";
    private final String CONFIRM_EMAIL_CONTROLLER = "ConfirmMailController";
    private final String SEND_CODE_AGAIN_CONTROLLER = "SendCodeAgainController";
    private final String SEARCH_CONTROLLER = "SearchController";
    private final String BOOKING_CONTROLLER = "BookingController";
    private final String HISTORY_CONTROLLER = "HistoryController";
    private final String DELETE_HISTORY_CONTROLLER = "DeleteHistoryController";
    private final String REQUEST_PROCESS_CONTROLLER = "RequestProcessController";
    private final String ACCEPT_REQUEST_CONTROLLER = "AcceptRequestController";
    private final String DELETE_REQUEST_CONTROLLER = "DeleteRequestController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("btnAction");
        String url = LOGIN_PAGE;
        try {

            UsersDTO user = (UsersDTO) (request.getSession()).getAttribute("user");
            
            if (user == null) {
                if (action == null) {
                    url = LOGIN_PAGE;
                } else if (action.equals("Login")) {
                    url = LOGIN_CONTROLLER;
                } else if (action.equals("LoginGoogle")) {
                    url = LOGIN_GOOGLE_CONTROLLER;
                } else if (action.equals("Register")) {
                    url = REGISTER_CONTROLLER;
                } else if (action.equals("Confirm")) {
                    url = CONFIRM_EMAIL_CONTROLLER;
                } else if (action.equals("send code again")) {
                    url = SEND_CODE_AGAIN_CONTROLLER;
                }
            } else {
                if (action == null) {
                    url = LOGIN_PAGE;
                } else if (action.equals("Logout")) {
                    url = LOGOUT_CONTROLLER;
                } else if (action.equals("Confirm")) {
                    url = CONFIRM_EMAIL_CONTROLLER;
                } else if (action.equals("send code again")) {
                    url = SEND_CODE_AGAIN_CONTROLLER;
                } else if (action.equals("Search")) {
                    url = SEARCH_CONTROLLER;
                } else if (action.equals("Booking")) {
                    url = BOOKING_CONTROLLER;
                } else if (action.equals("History")) {
                    url = HISTORY_CONTROLLER;
                } else if (action.equals("DeleteHistory")) {
                    url = DELETE_HISTORY_CONTROLLER;
                } else if (action.equals("SearchRequestProcess")) {
                    url = REQUEST_PROCESS_CONTROLLER;
                } else if (action.equals("AcceptRequest")) {
                    url = ACCEPT_REQUEST_CONTROLLER;
                } else if (action.equals("DeleteRequest")) {
                    url = DELETE_REQUEST_CONTROLLER;
                }
            }
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
