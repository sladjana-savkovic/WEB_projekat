$(document).ready(function() {
	
	$('form#registration').submit(function(event) {
		event.preventDefault();
		
		let name = $('#name').val();
		let surname = $('#surname').val();
		let username = $('#username').val();
		let password = $('#psw').val();
		let password_repeat = $('#psw-repeat').val();
		let gender = 0;
		
		if($('#female').is(":checked")){
			gender = 1;
		}
		
		if (!name) {
			$('#error_name').text('Unos imena je obavezan');
			$('#error_name').attr("hidden",false);
			return;
		}
		
		if(!surname){ 						
			$('#error_name').attr("hidden",true);
			$('#error_surname').text('Unos prezimena je obavezan');
			$("#error_surname").attr("hidden",false);
			return;
		}
		
		if(!username){ 			
			$('#error_surname').attr("hidden",true);
			$('#error_username').text('Unos korisničkog imena je obavezan');
			$("#error_username").attr("hidden",false);
			return;
		}
		
		if(!password){ 		
			$('#error_username').attr("hidden",true);
			$('#error_psw').text('Unos lozinke je obavezan');
			$("#error_psw").attr("hidden",false);
			return;
		}
		
		if(!password_repeat){ 	
			$('#error_psw').attr("hidden",true);
			$('#error_psw-repeat').text('Ponovo unesite lozinku');
			$("#error_psw-repeat").attr("hidden",false);
			return;
		}
		
		if(password != password_repeat){
			$('#error_password').attr("hidden",true);
			$('#error_psw-repeat').text('Unesene lozinke se ne poklapaju');
			$("#error_psw-repeat").attr("hidden",false);
			return;
		}
		
		$('#error_psw-repeat').attr("hidden",true);

		$.ajax({
			type: "POST",
			url: "rest/registration",
			data: JSON.stringify({ 
				username: username, 
				password: password, 
				name: name,
				surname:surname,
				gender:gender,
				typeOfUser:1,
				blocked:false}),
			contentType: "application/json",
			success:function(data){
				$('#error_name').attr("hidden",true);
				$('#error_surname').attr("hidden",true);
				$('#error_username').attr("hidden",true);
				$('#error_psw').attr("hidden",true);
				$('#error_psw-repeat').attr("hidden",true);
				
				toastr["success"]("Uspješno ste se registrovali");
				$("#close_btn").click();
				window.location.href = 'guest_new-reservation.html';
			},
			error: function(response){
				toastr["error"]("Korisničko ime već postoji!");
			}
		});
	});
	
	$('a#sign_in').click(function(event) {
		$("#close_btn").click();
	});

});