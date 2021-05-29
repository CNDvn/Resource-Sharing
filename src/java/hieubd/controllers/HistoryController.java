/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.controllers;

import hieubd.requests.RequestsDAO;
import hieubd.resources.ResourcesDAO;
import hieubd.users.UsersDTO;
import hieubd.utils.MyConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
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
@WebServlet(name = "HistoryController", urlPatterns = {"/HistoryController"})
public class HistoryController extends HttpServlet {

    private final String HISTORY_PAGE = "history.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = HISTORY_PAGE;
        String searchByName = request.getParameter("searchByName");
        String searchByDate = request.getParameter("searchByDate");
        String page = request.getParameter("page");
        try {
            HttpSession session = request.getSession();
            UsersDTO user = (UsersDTO) session.getAttribute("user");

            if (user.getRoleID() == MyConstants.ROLE_EMPLOYEE) {
                int pageTotal = 0;
                int pageInt = 1;
                if (page != null && !page.isEmpty()) {
                    pageInt = Integer.parseInt(page);
                }
                Date date = null;
                if (searchByDate != null && !searchByDate.isEmpty()) {
                    date = Date.valueOf(searchByDate);
                }
                if (searchByName == null) {
                    searchByName = "";
                }

                RequestsDAO dao = new RequestsDAO();
                dao.getOrderByDate(pageInt, searchByName, date, user.getEmail());
                if (dao.getList() == null || dao.getList().isEmpty()) {
                    request.setAttribute("searchNotValue", "No have request");
                } else {
                    request.setAttribute("listRequest", dao.getList());
                    ResourcesDAO resourceDao = new ResourcesDAO();
                    resourceDao.getByName(searchByName);
                    request.setAttribute("listResource", resourceDao.getList());

                    int totalItem = dao.getCount(pageInt, searchByName, date, user.getEmail());
                    pageTotal = totalItem / MyConstants.TOTAL_ITEM_IN_PAGE;
                    if (totalItem % MyConstants.TOTAL_ITEM_IN_PAGE != 0) {
                        pageTotal += 1;
                    }
                    request.setAttribute("pageTotal", pageTotal);
                }
            }
        } catch (NamingException e) {
            log("Error NamingException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (SQLException e) {
            log("Error SQLException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (Exception e) {
            log("Error Exception at " + this.getClass().getName() + ": " + e.getMessage());
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
