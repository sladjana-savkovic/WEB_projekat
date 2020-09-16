$(document).ready(function() {
	
	 $('#signin_username').val('');
	 $('#signin_password').val('');

	$('form#sign_in').submit(function(event) {
		event.preventDefault();
		
		var username = $('#signin_username').val();
		var password = $('#signin_password').val();
		
		if(!username) {
			$('#error_uname').text('Unos korisničkog imena je obavezan');
			$('#error_uname').attr("hidden",false);
			return;
		}
		
		if(!password) {
			$('#error_uname').attr("hidden",true);
			$('#error_pas').text('Unos lozinke je obavezan');
			$('#error_pas').attr("hidden",false);
			return;
		}
		
		$('#error_pas').attr("hidden",true);
		
		$.ajax({
			type: "POST",
			url: "rest/signin",
			data: JSON.stringify({ 
				username: username, 
				password: password}),
			contentType: "application/json",
			success:function(data, textStatus, XmlHttpRequest){
				toastr["success"]("Uspješno ste se prijavili");
				$('#error_uname').attr("hidden",true);
				$('#error_pass').attr("hidden",true);
				
			    $('#signin_username').val('');
			    $('#signin_password').val('');
				 
				$("#signin_close").click();
				
				setTimeout(function() {
					location.href = XmlHttpRequest.responseText;
					$('#sign_in')[0].reset();
				}, 1000);
			},
			error: function(jqXHR, textStatus, errorThrown){
				toastr["error"](jqXHR.responseText);
				$('#signin_username').val('');
			    $('#signin_password').val('');
			}
		});
		
	});
	
	
});