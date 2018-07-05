<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Bulk CSV File Upload</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
  <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
  <script src="Script1.js"></script>
 </head>
 
 <script type="text/javascript">
</script>
 
 
 
 
 
<!--  -->
<body style="background-color: #28BBBB">

<!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
         
         <h4 class="modal-title" style="color:green"><center>CSV Format</center></h4>
        </div>
        <div class="modal-body">
          <img src="finalformat1.PNG" style="width:780px" alt="smiley">
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

 	 



<div class="container" style="width:400px; margin-top:100px">
  <center><h2 style="color: white">BULK UPLOAD</h2></center><br>
  <center><div class="card" style="width:400px">
    
    <div class="card-body">
    
    	
    <form class="md-form" action="MyServlet" method="post" enctype="multipart/form-data">
    <p class="h4 text-center mb-4"></p>

    <!--  Default input Company Id -->
    <label for="companyId" class="grey-text float-left">Your Company Id</label>
    <input type="text"  id="companyId" class="form-control" name="CompanyId" required>
	
    <br>

    <!-- Default input file-->
    <label for="fileinput" class="grey-text float-left">Your CSV file</label>
    <input type="file" id="fileinput" class="form-control" name="fname" required>

	<br>
    <div class="text-center mt-4">
        <button class="btn btn-primary float-left" type="submit">Submit</button>
        <button class="btn btn-success float-right" type="button" data-toggle="modal" data-target="#myModal">CSV format</button>
    </div>
</form>
</div> 
    </div><!-- card close -->
    
    <script>
		document.getElementById('fileinput').addEventListener('change', readSingleFile, false);
    </script>
  </center>
</div>

</body></html>