/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pc
 */
@WebServlet(urlPatterns = {"/NewServlet"})
public class NewServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewServlet at " + request.getContextPath() + "</h1>");
            out.println(request.getParameter("name"));
            out.println(getFicheroAdjunto());
            //String s = new String(getFicheroAdjunto(), StandardCharsets.US_ASCII);
            
            File ruta2 = new File ("C:\\Users\\Pc\\Documents\\NetBeansProjects\\trabajo2.txt");
            try (FileOutputStream stream = new FileOutputStream(ruta2)) {
                stream.write(getFicheroAdjunto());
            }
            
            
            String separar;
            FileReader fr = new FileReader(ruta2);
            BufferedReader br = new BufferedReader(fr);
            while((separar = br.readLine())!=null) {
                
                String[] parts = separar.split(",");
                String Nombre = parts[0]; 
                String Apellido_Paterno = parts[1]; 
                String Apellido_Materno = parts[2]; 
                String DNI = parts[3];            
                Connection conec = null;
                try {
                    conec = getConection();
                    PreparedStatement ps;
                    ps = conec.prepareStatement("INSERT INTO alumnos (Nombre, Apellido_Paterno, Apellido_Materno, DNI) VALUES(?,?,?,?)");
                    ps.setString(1, Nombre);
                    ps.setString(2, Apellido_Paterno);
                    ps.setString(3, Apellido_Materno);
                    ps.setString(4, DNI);
                    ps.executeUpdate();
                    
                out.println(ps);
                
                }catch (Exception e) {  

                }  
            
            }
            
            br.close();
            out.println("</body>");
            out.println("</html>");
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

    private static byte[] getFicheroAdjunto() {
        paquetews.ServiceBinario_Service service = new paquetews.ServiceBinario_Service();
        paquetews.ServiceBinario port = service.getServiceBinarioPort();
        return port.getFicheroAdjunto();
    }

    private Connection getConection() {
        
        Connection conec = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");           
            conec = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/trabajo","root","");  
        } catch (Exception e) {
            System.out.println(e);
        }
        return conec;
    }

}
