<%@ attribute name="id" required="true" %>
<%@ attribute name="placeholder" required="true" %>

<input class="form-control" id="${id}" placeholder="${placeholder}" />
<input type="hidden" id="alt-${id}" name="${id}" />
<script>
  $("#${id}").datepicker({
	dateFormat: 'dd/mm/yy', 
	altFormat: "yymmdd",
	altField: "#alt-${id}"
		  
  });
</script>