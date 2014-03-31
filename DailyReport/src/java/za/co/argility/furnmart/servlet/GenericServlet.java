/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author tmaleka
 */
public class GenericServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }
    
    protected void saveSession(HttpServletRequest request, Object data, String attribute) {
        HttpSession session = request.getSession(true);
        session.setAttribute(attribute, data);
    }
    
    protected Object getSessionData(HttpServletRequest request, String attribute) {
        HttpSession session = request.getSession(true);
        return session.getAttribute(SessionAttribute.OVERVIEW_DATA_TAG);
    }
    
    protected void showErrorPage(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); 
    }
}
