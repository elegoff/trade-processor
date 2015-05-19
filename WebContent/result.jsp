 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
 <script type="text/javascript"
    src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">
    function charting() {
        $.ajax({
            url : 'chart',
            success : function(data) {
                $('#pie').html(data);
            }
        });
    }
</script>
 
 <p>
 <%= new java.util.Date()%>
 </p>
<table class="table table-striped">


<th>Volume by day</th>
<th>Volume by country</th>
<th>Total placed</th>
<th>Rate volatilty</th>

<c:set var="count" value="0" scope="page" />

<c:forEach items="${data}" var="item">



<tr>



<td>


<img src="chart?<%=System.currentTimeMillis() %>&count=${count}&column=day"/>



</td>

<td>
<img src="chart?<%=System.currentTimeMillis() %>&count=${count}&column=country"/>
<c:set var="count" value="${count + 1}" scope="page"/>

</td>
<td><c:out value="${item.count}"/></td>
<td>
<table>
<th>Min</th>
<th>Max</th>
<tr>
<td><c:out value="${item.minRate}"/></td>
<td align="right"><c:out value="${item.maxRate}"/></td>
</tr>
</table>
</td>


</tr>
</c:forEach>




</table>