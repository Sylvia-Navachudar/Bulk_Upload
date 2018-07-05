package Project;
import java.util.Date;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;



public class Validation {

	
	
	public boolean date(String date) throws java.text.ParseException
	{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		sdf.setLenient(false);
		
		try {
			
			//if not valid, it will throw ParseException
			 Date Validatedate =  sdf.parse(date);
			//System.out.println(Validatedate);
			 return true;
		
		} catch (ParseException e) {
			
			//System.out.println("Problem with date");
			
			return false;
		}
		
		
		
	}
	
	
	
	
	public boolean validateURL(String url) {
		
		 try {
	            new URL(url).toURI();
	            return true;
	        }
	         
	        // If there was an Exception
	        // while creating URL object
	        catch (Exception e) {
	        	
	        	return false;
	        }
		
		
		
	}
	
	
}
