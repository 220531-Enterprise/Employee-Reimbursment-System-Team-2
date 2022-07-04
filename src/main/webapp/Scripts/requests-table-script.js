let hostname = window.location.hostname;
$(document).ready(function() {
  $.ajax({
    url:`http://${hostname}:8080/employee-servlet-app/find-requests`,
    method: 'post',
    success: function(data) {

      $('#request-table').DataTable( {
        data: data,
        columns: [
          {"data": "date_submitted"},
          {"data": "amount"},
          {"data": "description"},
          {"data": "type"},
          {"data": "status"},
          {"data": "resolverId"},
          {"data": "date_resolved"}
        ]
      }) 
    }
  })
});