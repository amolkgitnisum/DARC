<!DOCTYPE html>
<html>
    <head>
        <title>DARC Report</title>
        <link rel="stylesheet" type="text/css" href="Style.css">
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
           
    </head>
   <%
   String url=request.getParameter("url");
   System.out.println("url"+url);
   %>
    <body style="background-image: url('background.png');">
    <input type="hidden" value=<%=url%> id="url">
        <div style="padding-top: 0px;left: -350px;position: relative;">
            <img src="mainDarc.png" style="height: 100px;width: 300px; left: 350px; position: relative;">
        </div>
        <div id="evalStyle"> <!-- Loading Graph -->
        	<h1 class="evalH">Generating Report</h1>
            <div style="width: 900px;height: 400px;border: 1px solid black;position: relative;left: 300px;background-color: grey;">
                <div id="animation" style="width: 900px;height: 20px;border: 1px solid black;position: absolute;left: 0px;background-color: lightblue;text-align: center;font-weight: bold;">Loading....</div>
            </div>
        </div> 
        <div id="reportData" style="width:1530px;height:600px;"">  <!-- Displaying Data -->
        <br/>
        	<span>Latest Performance Report for: <span id="reportUrl"><%=url%></span></span><br /><br />

        	<div id="reportDetails">
        	
        		<table cellspacing="50" id="tableStyle">
        			<tr>
        				<th>Alexa Ranking</th>
        				<th>Images<span style="font-size:11px">(No.)</span></th>
        				<th>hrefs<span style="font-size:11px">(No.)</span></th>
        				<th>Page Size<span style="font-size:11px">(KB)</span></th>
        				<th>Good & Bad links</th>
        			</tr>
        			<tr>
        				<td>
        					
        					<span id="gAlexa">1</span>
        					<br /><br />
        					<span id="lAlexa">1</span>
        					
        				</td>
        				<td>
        					<span id="imageCount">1</span>
        				</td>
        				<td>
        					<span id="hrefCount">1</span>
        				</td>
        				<td>
        					<span id="sizeCount">1</span>
        				</td>
        				<td>
        					<span>
        						Good Links : <span id="glinks">1</span>
        					</span><br /><br />
        					<span>Bad Links : <span id="llinks">1</span>
        					</span>
        				</td>
        			</tr>        			
        		</table>
        	</div>
        </div>        
    </body>
    
   <script type="text/javascript">
   
   $(document).ready(function(){
	
	   $("#reportData").hide();
	   var url=$('#url').val();
	   console.log('/generatereport?url='+url+'');
	   
	    $.get('/generatereport?url='+url+'', function(data, status){
	        //alert("Data: " + data + "\nStatus: " + status);
	        var result=data;
	        console.log(result);
	        var alexaranking=result.totals["0"].alexaranking;
	         
	        alexaranking.split("@").forEach(function(data,index){
	          if(index==0)	
	          $("#gAlexa").text(data);
	          else
	        	  $("#lAlexa").text(data);
	        });
	        
	        $("#imageCount").text(result.totals["0"].noOfImages);
	        $("#hrefCount").text(result.totals["0"].linkssize);
	        $("#sizeCount").text(result.totals["0"].webpagesize);
	        $("#glinks").text(parseInt(result.totals["0"].linkssize)-parseInt(result.headers.status.length));
	        $("#llinks").text(parseInt(result.headers.status.length));
	        //console.log(result.totals["0"].alexaranking);
	       /*  $.each(data,function(key,value){
	        	console.log(value.totals);
	        	$.each(value.totals,function(key1,value1)
	        	{
	        		console.log(value1.alexaranking);
	        		$('#gAlexa').text(value1.alexaranking);
	        	});
	        }); */
	        if(status)
	        	{
	        	
	        	$("#evalStyle").hide();
	            $("#reportData").show(); 
	        	//console.log(data);
	        	
	        	}
	        
	    }); 
	   
   });
   
   
 
     
   </script>
</html>