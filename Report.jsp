<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<title>Report</title>
</head>
<!--  style="background-color: #28BBBB" -->
<body>
<%@ page import="Project.BasicModel" %>
<%@ page import="java.util.*" %>


<div class="container">
  <center><h2>Bulk Upload Report</h2></center>
 
  <table class="table table-hover small-text responsive">
    <thead >
      <tr class="danger">
        <th>Record No.</th>
        <th>Status</th>
        <th>URL</th>
        
       </tr>
     </thead>
      <tbody>
     
     <% 
    ArrayList reportList = BasicModel.reportList;
    Iterator itr=reportList.iterator();  
    while(itr.hasNext()){  
    	
    	Dictionary  d = (Dictionary) itr.next();
    	String color = " ";
    	String message="";
    	String url=" ";
    	
    	if(d.get("ArrayException").equals(true))
    	{
    		message="Some fields are empty! Please check your csv file..!";
    		url=" ";
    	}
    	
    	else{
    		
    		url = (String)d.get("url");
        	
        	if(d.get("insertionProblem").equals(true))
        	{
        		if(d.get("dateProblem").equals(true) && d.get("urlProblem").equals(true))
        				{message="Problem in inserting, Please check the date and url. ";
        		        color="success";}
        		else if(d.get("dateProblem").equals(true))
        		{
        			    message="Problem in inserting, Please check the date format. ";
        			    color="success";
        		}
        		else if(d.get("urlProblem").equals(true))
        		{
        				message="Problem in inserting, Please check the url format." ;
        				color="success";
        		}
        				
        			    
        	}
        	else if(d.get("insertionProblem").equals(false))
        	{
        		message="Successfully Inserted." ;
    			
        	}
        	
        	
        	
        	
       
    	}
    		
    	%>
    	 	
   
   <tr class=<% out.print(color); %>>
        <td><% out.print(d.get("recordNo") ); %></td>
        <td><% out.print(message);%></td>
        <td><% out.print(url);%></td>
        
        
   </tr>
      
     
   <% }%>
     
  </tbody>
  </table>
  
  
  
</div>



</body>
</html>