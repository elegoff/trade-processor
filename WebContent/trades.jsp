
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="utf-8">

<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
</head>

<body>
    <section>
        <div class="container">
            <div class="row">
            <div class="panel panel-default">

 <div class="col-sm-8">
                    <h1>Trade market summary</h1>
                    <p>Some stats about placed trades </p>
                    
                </div>




<table class="table table-striped">
<th>Currency pair</th>
<th>Count</th>
<th>Min rate</th>
<th>Max rate</th>
<th>Volume by day</th>
<th>Volume by country</th>

<c:forEach items="${data}" var="item">

<tr>
<td><c:out value="${item.currencyPair}"/></td>
<td><c:out value="${item.count}"/></td>
<td><c:out value="${item.minRate}"/></td>
<td><c:out value="${item.maxRate}"/></td>

<td>
<table>
<th>Date</th>
<th>Total Amount</th>
<c:forEach items="${item.volumeByDay}" var="vd">
<tr>
<td>
<c:out value="${vd.key}"/>
</td>
<td align="right">
<c:out value="${vd.value}"/>
</td>
</tr>
</c:forEach>
</table>
</td>

<td>
<table>
<th>Country</th>
<th>Total Amount</th>
<c:forEach items="${item.volumeByCountry}" var="vc">
<tr>
<td>
<c:out value="${vc.key}"/>
</td>
<td align="right">
<c:out value="${vc.value}"/>
</td>
</tr>
</c:forEach>
</table>
</td>


</tr>
</c:forEach>




</table>
     
     </div>
        </div>
    </section>
</body>
</html>