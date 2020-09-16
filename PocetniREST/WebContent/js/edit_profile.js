$(document).ready(function() {
	
	var old_password = "";
	var username = "";
		
	$('a#edit_profile').click(function(event){	
		
		$.ajax({
			type:"GET", 
			url: "rest/get_loggedUser",
			contentType: "application/json",
			success:function(user){
				$('#name').val(user.name);
				$('#surname').val(user.surname);
				if(user.gender == "MALE"){
					$('#male').attr('checked', 'checked');
				}else{
					$('#female').attr('checked', 'checked');
				}
				username = user.username;
				old_password = user.password;
			},
			error:function(){
				console.log("Došlo je do greške prilikom učitavanja korisnika!");
			}
		});
	});

	$('form#edit').submit(function(event) {
		
		event.preventDefault();
			
		let name = $('#name').val();
		let surname = $('#surname').val();
		let old_pass = $('#old_psw').val();
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
		
		if(old_pass != old_password){
			$('#error_surname').attr("hidden",true);
			$('#error_old_psw').text('Pogrešna stara lozinka');
			$("#error_old_psw").attr("hidden",false);
			return;
		}
		
		if(!password){ 		
			$('#error_old_psw').attr("hidden",true);
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
		
		$("#error_psw-repeat").attr("hidden",true);
	
		$.ajax({
			type: "POST",
			url: "rest/edit_profile",
			data: JSON.stringify({ 
				username: username, 
				password: password, 
				name: name,
				surname:surname,
				gender:gender,
				typeOfUser:1}),
			contentType: "application/json",
			success:function(data){
				$('#error_name').attr("hidden",true);
				$('#error_surname').attr("hidden",true);
				$('#error_old_psw').attr("hidden",true);
				$('#error_psw').attr("hidden",true);
				$('#error_psw-repeat').attr("hidden",true);
				
				$('#name').val('');
				$('#surname').val('');
				$('#old_psw').val('');
				$('#psw').val('');
				$('#psw-repeat').val('');
				
				$("#close_btn").click();
				toastr["success"]("Uspješno ste izmijenili svoje informacije.");
			},
			error: function(response){
				toastr["error"]("Došlo je do greške!");
			}
		});	
	});
	
});