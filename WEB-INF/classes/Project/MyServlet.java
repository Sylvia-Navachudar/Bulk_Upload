package Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.*;


/**
 * Servlet implementation class ServletDemo
 */
@WebServlet("/MyServlet")

public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		JSONArray Records = new JSONArray();
		response.setContentType("text/html");
		
		PrintWriter out=response.getWriter();
		
		
		//C:\Users\Shiny\Desktop\Workspace
		
	String root_path_server = System.getenv("root_path_server");
		
	//String FileSep = System.getProperty("file.separator");
		
	  File folder=new File(root_path_server+"/Bulk_Upload/FileUpload");
	  FileUtils.cleanDirectory(folder);
	  
	 
	   
		MultipartRequest req=new MultipartRequest(request,root_path_server+"/Bulk_Upload/FileUpload");
		String filename=request.getParameter("fname");
		
		String comp = req.getParameter("CompanyId");
        System.out.println("company Id = "+comp);
		
		
	     File[] listoffiles = folder.listFiles();
	     File file = listoffiles[0];
	      String fName = file.getName();
	      String csvFile = root_path_server+"/Bulk_Upload/FileUpload/"+fName;
		
        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        br.readLine();
       
        String line=null;
		while ((line = br.readLine()) != null) {
            if (!line.isEmpty()) {
                
                  Records.add(line);
                 
            }
        
        
        }
		
	
   br.close();

   BasicModel bm = new BasicModel();
   
   //bm.Map_posts_to_Model(Records);
   try {
	   
	bm.Map_posts_to_Model(Records,comp);
	
} catch (ParseException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (java.text.ParseException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (InterruptedException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
   
   out.println("Please wait for a few minutes.....");
   
   RequestDispatcher reqDes = request.getRequestDispatcher("Report.jsp");
   reqDes.forward(request, response);
		
	}
//	public static void main(String args[]) {
//		MyServlet ser = new MyServlet();
//		System.out.println(new File("./FileUpload").isDirectory());
	
}




