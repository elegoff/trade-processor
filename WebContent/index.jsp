
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="utf-8">

<script type="text/javascript"
    src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">
    function refresh() {
        $.ajax({
            url : 'displayResult',
            success : function(data) {
                $('#result').html(data);
            }
        });

	var intervalId = 0;
    //every 30 sec refresh should be enough
    intervalId = setInterval(refresh, 30000);
    }
</script>

<script type="text/javascript">
    refresh();
</script>

<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">


</head>

<body>
    <section>
        <div class="container">
            <div class="row">
            <div class="panel panel-default">

 				<div class="col-sm-8">
                    <h1>Trade market summary</h1>
                    <p>Some statistics about placed trades </p>
                    
                </div>
                
                
                              <div class="col-sm-12" id="result"></div>
                
                
</div>
</div>
</div>



    </section>
</body>
</html>