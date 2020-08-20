$(document).ready(function() {

	$('form#registration').submit(function(event) {
		e.preventDefault();
		
		let name = $('#name').val();
		let surname = $('#surname').val();
		let username = $('#username').val();
		let password = $('#psw').val();
		let password_repeat = $('#psw-repeat').val();
		let gender;
		
		if($('#male').checked){
			gender = 0;
		}else if($('#female').checked){
			gender = 1;
		}
		
		if (!name) {
			$('#error_name').text('Unos imena je obavezan');
			$("#error_name").show().delay(3000).fadeOut();
			return;
		}
		
		if(!surname){ 						
			$('#error_surname').text('Unos prezimena je obavezan');
			$("#error_surname").show().delay(3000).fadeOut();
			return;
		}
		
		$('#error_name').hide();
		$('#error_surname').hide();

		$.ajax({
			type: "POST",
			url: "rest/users/registration",
			data: JSON.stringify({ 
				username: username, 
				password: password, 
				name: name,
				surname:surname,
				gender:gender}),
			contentType: "application/json",
			success:function(data){
				toastr["success"]("Uspješno ste se registrovali!");
				window.location.href = 'index.html';
			},
			error: function(response){
				toastr["error"](jqXHR.responseText);
			}
		});
	});

});