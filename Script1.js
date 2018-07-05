function readSingleFile(evt) {
	  
	 
	    //Retrieve the first (and only!) File from the FileList object
	    var f = evt.target.files[0]; 
	    // Checking for the csv file extension
	    var fileInput = document.getElementById('fileinput'); 
	    var filePath = fileInput.value; var allowedExtensions = /(\.csv)$/i; 
	    if(!allowedExtensions.exec(filePath))
	    { 
		//alert('Please upload file having extension .csv only.'); 
		    fileInput.value = ''; 
		    swal("Sorry !", "CSV Only!", "error", {
		    	  button: "Got it!",
		    	});
			
			
		
		}
	    // Checking for the headers in a csv file
	       else if (f) {
	      var r = new FileReader();
	      r.onload = function(e) { 
		      var contents = e.target.result;
		      var headers=contents.substr(0, contents.indexOf("\n"));
		      var line=headers.split(",");
		     
		       
		      if((line[0].toLowerCase()=='source')&&(line[1].toLowerCase()=='author') && (line[2].toLowerCase()=='date') && (line[3].toLowerCase()=='title') && (line[4].toLowerCase()=='url') && (line[5].toLowerCase().trim()=='description') && (line[6].toLowerCase().trim()=='reach_value'))
		    				{
		    	
		    	//alert("correct csv format");
		    	  }
		    		else
		    		  {
		    			swal("Sorry !", "Please follow the correct CSV format!", "error", {
		  		    	  button: "Got it!",
		  		    	});
		    			
		    			
		    			
		    			 
		    		  var fileInput = document.getElementById('fileinput'); 
		    		  fileInput.value = ''; 
		    		   
		    		  
		    		  		     			
		    		  
		    		  }
		       }
	      r.readAsText(f);
	    } else { 
	      alert("Failed to load file");
	    }
	  }
