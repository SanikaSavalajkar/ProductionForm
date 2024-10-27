package com.servlet.submit;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/submit")
public class SubmitServlet extends HttpServlet{
	
	//create the query
    private static final String INSERT_QUERY ="INSERT INTO PRODUCTION_FORM(DATE,MACHINE_NUMBER,PLAN_QUANTITY,ACTUAL_QUANTITY,REJECT_QUANTITY,BD_MINUTES) VALUES(?,?,?,?,?,?)";
	
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get PrintWriter
        PrintWriter pw = res.getWriter();
        //set Content type
        res.setContentType("text/hmtl");
        //read the form values
        String date = req.getParameter("date");
        String machine_number = req.getParameter("machine-number");
        String plan_quantity = req.getParameter("plan-quantity");
        String actual_quantity = req.getParameter("actual-quantity");
        String reject_quantity = req.getParameter("reject-quantity");
        String bd_minutes = req.getParameter("bd");
        
      //load the jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
      //create the connection
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/production_data","root","Sanika@12345");
                PreparedStatement ps = con.prepareStatement(INSERT_QUERY);){
            //set the values
            ps.setString(1, date);
            ps.setString(2, machine_number);
            ps.setString(3, plan_quantity);
            ps.setString(4, actual_quantity);
            ps.setString(5, reject_quantity);
            ps.setString(6, bd_minutes);

            //execute the query
            int count = ps.executeUpdate();

            if(count==0) {
                pw.println("Record not stored into database");
            }else {
                pw.println("Record Stored into Database");
            }
        }catch(SQLException se) {
            pw.println(se.getMessage());
            se.printStackTrace();
        }catch(Exception e) {
            pw.println(e.getMessage());
            e.printStackTrace();
        }
        
        //close the stream
        pw.close();
    }
	
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(req, resp);
    }
}