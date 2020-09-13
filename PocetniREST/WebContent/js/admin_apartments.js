$(document).ready(function() {
	
	checkLoggedUser();
	
});

function checkLoggedUser(){

	$.ajax({
		type: "GET",
		url: "rest/verification/admin",
		error:  function(jqXHR, textStatus, errorThrown)  {
			$('#admin_apartments').hide(function() {
				alert(jqXHR.responseText);
				window.history.back();
			});
		}
	});
};

