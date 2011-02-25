package uk.ac.dundee.computing.aec.counter.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import uk.ac.dundee.computing.aec.counter.connectors.*;
import uk.ac.dundee.computing.aec.stores.*;


/**
 * Servlet implementation class RandomData
 */
public class RandomData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private HashMap Values = new HashMap();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RandomData() {
        super();
        // TODO Auto-generated constructor stub
        DataConnector dc = new DataConnector();
		  dc.CreateData();
      
       
    }
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 DataConnector dc = new DataConnector();
		 
	        List<String> ll = new LinkedList<String>(); 
	        for (int i=0; i<100;i++){
	        	String rr = dc.GetRandomKey();
	        	while (Values.containsKey(rr)==true){
	        		Values.put(rr, 0);
	        	}
	        	//System.out.println("Returned "+rr);
	        	ll.add(rr);
	        }
	        request.setAttribute("Data", ll);
			RequestDispatcher rdjson=request.getRequestDispatcher("/index.jsp");
			rdjson.forward(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
