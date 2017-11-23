<!DOCTYPE html>
<html>
    <head>
        <title>DARC Report</title>
        <link rel="stylesheet" type="text/css" href="Style.css">
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script type="text/javascript">
        	function validate(){
        	var x = document.forms["myForm"]["inputField"].value;
              if (x == "") {
             alert("URL must be filled out");
              return false;
              }
           }
        </script>
    </head>
    <body>
        <div style="padding-top: 0px;margin:auto;">
        	<img src="barChart.gif" style="height: 100px;width: 200px; left: 100px; position: relative;">
            <img src="mainDarc.png" style="height: 100px;width: 300px; left: 350px; position: relative;">
            <img src="barChart.gif" style="height: 100px;width: 200px; left: 700px; position: relative;">
        </div>
        <div id="divStyle">
            <img src="side1.png" align="right" style="width: 300px;" />
            <h1>Analyze your site performance</h1>
            <div style="padding: 50px 0px 0px 100px;">
                <form name="myForm" action="/Evaluation.jsp" onsubmit="return validate()" method="post">
                    <input type="text" name="url" id="inputData" placeholder="http://www.example.com"><br /><br />
                    <input type="submit" value="Analyze" id="button"/>
                </form>
            </div>
        </div>
    </body>
</html>