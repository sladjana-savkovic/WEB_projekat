$(document).ready(function() {
	
	//checkLoggedUser();
	
});

function checkLoggedUser(){

	$.ajax({
		type: "GET",
		url: "rest/verification/host",
		error:  function(jqXHR, textStatus, errorThrown)  {
			$('#admin_amenities').hide(function() {
				alert(jqXHR.responseText);
				window.history.back();
			});
		}
	});
};


