package Project;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class BasicModel {
	
	Boolean dateProblem=false;
	Boolean urlProblem=false;
	Boolean insertionProblem=false;
	
	public static ArrayList reportList=new ArrayList();
	String root_path_server = System.getenv("root_path_server");
	JSONObject Basicmodel = new JSONObject();
	Validation validate = new Validation();
	public static int noOfRecords=0;
	
	
	
	public Boolean insertIntoDb(String compId) throws InterruptedException {
		// TODO Auto-generated method stub
		
				
		        String result = null;
		        //5696870ff5b6762f385aadc0
			    String url="https://document-service-nrstaging.meltwater.net/documents/"+compId+"/editorial";
				//curl -H "Content-Type: application/json" -X POST -d @C:\\Users\\Shiny\\Desktop\\test.json https://document-service-nrstaging.meltwater.net/documents/5696870ff5b6762f385aadc0/editorial
			    String[] command = {"curl", "-H", "Content-Type:application/json", "-X", "POST", "-d", "@"+root_path_server+"/Bulk_Upload/Temp", url };
			       
			    
			    ProcessBuilder process = new ProcessBuilder(command);
			    
			        Process p;
			        try
			        {
			            p = process.start();
			      //  process.wait();
			            BufferedReader reader =  new BufferedReader(new InputStreamReader(p.getInputStream()));
			                StringBuilder builder = new StringBuilder();
			                String line = null;
			                while ( (line = reader.readLine()) != null) {
			                        builder.append(line);
			                        builder.append(System.getProperty("line.separator"));
			                }
			                result = builder.toString();
			                

			        }
			        catch (IOException e)
			        {   System.out.print("error");
			            e.printStackTrace();
			        }
			        
			       if(result.equals("{\"httpStatus\":400,\"message\":\"Not possible to parse the JSON request, it contains errors.\"}"))
			       {
			    	   return false;
			       }
			       else {
			    	   return true;
			       }
			    	   
			    	   
			        

			}


	
	public boolean writeToFile(JSONObject basicmodel) throws IOException {
		
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(root_path_server+"/Bulk_Upload/Temp"));
	    writer.write(basicmodel.toString());
	     
	    writer.close();
		
		
		return true;
		
	}
	
	
	public JSONObject getJSONObject(String urlString) {
		
		JSONObject json = null;
		
		try {
			
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			String output;
			StringBuilder sb=new StringBuilder();
			//System.out.println("Output from Server .... \n");
			
			while ((output = br.readLine()) != null) {
				sb.append(output);//now original string is changed  
			}
			
			JSONParser parser = new JSONParser();
			try {
				json = (JSONObject) parser.parse(sb.toString());
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			
		}
			
		
	 catch (MalformedURLException e) {

				e.printStackTrace();

			  } catch (IOException e) {

				e.printStackTrace();

			  }
		
			return json; //JSON Object response recieved through the API call. 
	}





	
	public long getEPOCHTime(String sDate) throws ParseException, java.text.ParseException{
		
	       Date date1=(Date) new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(sDate);  
		   long epoch=date1.getTime();
		   return epoch;
		}  
	
	
	
	public JSONArray getJSONArray(String urlString) {
		
		JSONArray json = null;
		try {
			
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			String output;
			StringBuilder sb=new StringBuilder();
			//System.out.println("Output from Server .... \n");
			
			while ((output = br.readLine()) != null) {
				sb.append(output);//now original string is changed  
			}
			
			JSONParser parser = new JSONParser();
			try {
				
				json = (JSONArray) parser.parse(sb.toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
			
		
			catch (MalformedURLException e) {

				e.printStackTrace();

			  } catch (IOException e) {

				e.printStackTrace();

			  }
		
			return json;
	}


	
public long getCurrentEPOCHTime() throws ParseException, java.text.ParseException{
		
		Date today = (Date) Calendar.getInstance().getTime();
		 
		SimpleDateFormat crunchifyFormat = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz");
 
	    String currentTime = crunchifyFormat.format(today);
		
	    Date date = (Date) crunchifyFormat.parse(currentTime);
 
		long epochTime = date.getTime();
		
		return epochTime;
		
	}
	
public void CreateBasicModel() throws ParseException, java.text.ParseException {
		
		JSONObject metadata_data=new JSONObject();
        metadata_data.put("_quiddityType","document.DocumentMetaData");
        
        metadata_data.put("fetchingTime",getCurrentEPOCHTime());
        Basicmodel.put("metaData",metadata_data);
        
        JSONObject provider_data=new JSONObject();
        provider_data.put("_quiddityType", "document.Provider");
        provider_data.put("type", "mw");
        provider_data.put("_quiddityVersion", "1.0");
        metadata_data.put("provider",provider_data);
        metadata_data.put("mediaType","news");
        
        JSONObject source_data=new JSONObject();
        source_data.put("_quiddityType","document.Source");
        source_data.put("allowPdf",false);
        source_data.put("name","mLife");
        
        JSONObject location_data=new JSONObject();
        location_data.put("_quiddityType","common.GeoLocation");
        location_data.put("countryCode","");
        location_data.put("_quiddityVersion","1.1");
        
        source_data.put("location", location_data);
        source_data.put("_quiddityVersion","1.3");
        source_data.put("informationType","news");
        metadata_data.put("source",source_data);
        metadata_data.put("_quiddityVersion","1.3");
        metadata_data.put("url","");
        
        
        Basicmodel.put("_quiddityType","document.Document");
        JSONObject system_data=new JSONObject();
        system_data.put("_quiddityType", "system.SystemData");
        
        JSONObject policies_data=new JSONObject();
        policies_data.put("_quiddityType","system.Policies");
        JSONObject storage_data=new JSONObject();
        storage_data.put("_quiddityType","system.StoragePolicy");
        storage_data.put("overwrite", false);
        storage_data.put("_quiddityVersion","1.2");
        policies_data.put("_quiddityVersion", "1.0");
        system_data.put("_quiddityVersion","1.1");
        system_data.put("status","active");
        Basicmodel.put("systemData", system_data);
        policies_data.put("storage", storage_data);
        system_data.put("policies", policies_data);
        JSONArray attachments_data=new JSONArray();
       
        JSONObject attachments_data_obj = new JSONObject();
        
        attachments_data_obj.put("_quiddityType","common.Attachment");
        attachments_data_obj.put("link","");
        attachments_data_obj.put("_quiddityVersion","1.1");
        attachments_data_obj.put("comscoreUniqueVisitors"," ");
        attachments_data.add(attachments_data_obj);
        Basicmodel.put("attachments",attachments_data);
        
        
       
        JSONObject body_data=new JSONObject();
       
        JSONObject ingress_data=new JSONObject();
        ingress_data.put("_quiddityType", "common.Text");
        ingress_data.put("text", "");
        ingress_data.put("type", "plain");
        ingress_data.put("_quiddityVersion", "1.0");
        body_data.put("_quiddityType", "document.DocumentBodyData");
      
        JSONObject byLine_data=new JSONObject();
        byLine_data.put("_quiddityType","common.Text");
        byLine_data.put("text","");
        byLine_data.put("type","plain");
        byLine_data.put("_quiddityVersion","1.0");
        Basicmodel.put("body",body_data);
        body_data.put("ingress",ingress_data);
        body_data.put("byLine", byLine_data);
  
        
       JSONObject publish_data=new JSONObject();
       publish_data.put("date","1234576543123");
       publish_data.put("_quiddityType","common.TimeWithTimezone");
       publish_data.put("_quiddityVersion","1.0");
       JSONObject title_data=new JSONObject();
       title_data.put("_quiddityType", "common.Text");
       title_data.put("text", "");
       title_data.put("type", "plain");
       title_data.put("_quiddityVersion", "1.0");
       
       JSONObject content_data=new JSONObject();
       content_data.put("_quiddityType", "common.Text");
       content_data.put("text", "");
       content_data.put("type", "plain");
       content_data.put("_quiddityVersion", "1.0");
       
       
       
       
       body_data.put("publishDate", publish_data);
       body_data.put("title",title_data);
       body_data.put("content", content_data);
       body_data.put("_quiddityVersion","1.2");
       Basicmodel.put("_quiddityVersion","1.2");
       
    }





public void Map_posts_to_Model(JSONArray Records, String companyId)throws ParseException, java.text.ParseException, IOException, InterruptedException
{
	
	//?per_page=100
    
	System.out.println("Hello");
	CreateBasicModel();
	System.out.println(Basicmodel);
	System.out.println(Records);
	
	
	
    noOfRecords = Records.size();
	int RecordNo=0;
	
	System.out.println(noOfRecords);
	//System.out.println(RecordNo);
    
	reportList.clear();
	
    while(RecordNo < noOfRecords)
    {
    	
    	 int start;
    	 int end;
    	 
    	 Dictionary reportDic = new Hashtable();
    	
    	 String currRecord = (String) Records.get(RecordNo);
    	 
    	   
    	 
    	 String[] currentRecord = currRecord.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
    	 System.out.println("\n");
    	 
    	 
    	 
    	
    	 System.out.println("This is current record");
    	 System.out.println(currRecord);
    	 
    	 try {
    		 
    		 System.out.println("I am inside the try block\n");
    	 
    	//Mapping the source
    	 JSONObject metaOBJ = (JSONObject) Basicmodel.get("metaData");
    	 JSONObject sourceOBJ = (JSONObject)metaOBJ.get("source");
    	 sourceOBJ.put("name",currentRecord[0]);
    	
    	 
    	//Mapping the author
		JSONObject bodyOBJ = (JSONObject) Basicmodel.get("body");
		JSONObject byLineOBJ = (JSONObject) bodyOBJ.get("byLine");
		String author = currentRecord[1].replaceAll(",", "");
	//	String author1 = author.replaceAll("\"", " ");
		
		byLineOBJ.put("text",author);
		
		
		//Mapping the date
		if(!validate.date(currentRecord[2]))
			dateProblem=true;
		else
		{
			
			JSONObject publishObj = (JSONObject) bodyOBJ.get("publishDate");
	        String date = currentRecord[2]+"T00:00:00.000-0000";
	        publishObj.put("date", getEPOCHTime(date));
	        dateProblem=false;
			
		}
		
		//Mapping the title
		JSONObject titleOBJ = (JSONObject)bodyOBJ.get("title");
		String title = currentRecord[3].replaceAll(",", "");
	//	String title1 = title.replaceAll("\"", "");
		
		titleOBJ.put("text",title);
		
		
		
		//Mapping the url
		if(!validate.validateURL(currentRecord[4]))
			urlProblem=true;
		else {
			
			JSONObject metaObj = (JSONObject) Basicmodel.get("metaData");
			metaObj.put("url",currentRecord[4] );
			urlProblem=false;
			
		}
		
		//Mapping the description
		JSONObject contentObj = (JSONObject) bodyOBJ.get("content");
		//System.out.println(contentObj);
		String des = currentRecord[5].replaceAll(",", "");
	//	String des1 = des.replaceAll("\"", " ");
		contentObj.put("text",des);
		System.out.println(contentObj);
		
		//Mapping the reach value
		JSONArray attach_array =  (JSONArray) Basicmodel.get("attachments");
		JSONObject attach_array_obj =  (JSONObject) attach_array.get(0);
		String reach = currentRecord[6].replaceAll(",", "");
		long reach_value= 0;
		try
		{
		reach_value = Long.parseLong(reach);
		
		}
		catch(NumberFormatException e)
		{
			System.out.println("Exception occured while converting string to long");
		}
		attach_array_obj.put("comscoreUniqueVisitors",reach_value);
		
		
				
     }	
    	 catch(ArrayIndexOutOfBoundsException e) {
    		 
    		   
    		
    		 reportDic.put("ArrayException", true);
    		 reportDic.put("recordNo", RecordNo);
    		 
    		 reportList.add(reportDic);   
    		    
    		    RecordNo++;
    		    
    		    continue;
    		 
    		 
    	 }
    	 
    	
    	
		writeToFile(Basicmodel);
	   // System.out.println(Basicmodel);
		if(urlProblem==false && dateProblem==false)
		{
			
			if(!insertIntoDb(companyId))
		    	insertionProblem=true;
		    else 
		    	insertionProblem=false;
			
		}
		else {
			insertionProblem=true;
		}
		
		
		
	    
	    //System.out.println(insertionProblem+"\t"+dateProblem+"\t"+urlProblem);
		reportDic.put("recordNo", RecordNo);
	    reportDic.put("dateProblem", dateProblem);
	    reportDic.put("urlProblem", urlProblem);
	    reportDic.put("insertionProblem", insertionProblem);
	    reportDic.put("url",currentRecord[4] );
	    reportDic.put("ArrayException", false);
	    
	    reportList.add(reportDic);
	    
	    
			
			
	    RecordNo++;
			
			
		}
		
		    	
  //System.out.println(reportList);

    	
		
}


		
		
		}


	

