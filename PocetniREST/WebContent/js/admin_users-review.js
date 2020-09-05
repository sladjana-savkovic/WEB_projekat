$(document).ready(function() {
	
	checkLoggedUser();
	
	$.ajax({
		type:"GET", 
		url: "rest/hosts",
		contentType: "application/json",
		success:function(hosts){
			for (let h of hosts) {
				adduserTr(h);
			};
		},
		error:function(){
			console.log('error getting hosts');
		}
	});
	
	$.ajax({
		type:"GET", 
		url: "rest/guests",
		contentType: "application/json",
		success:function(guests){
			for (let g of guests) {
				adduserTr(g);
			}
		},
		error:function(){
			console.log('error getting guests');
		}
	});
	
	$('#cancel_btn').click(function() {
		$('#cancel_btn').attr("hidden",true);
		setTimeout(function(){
			location.reload(); }); 
	});
	
	$('form#host_registration').submit(function(event) {
		event.preventDefault();
		
		let name = $('#host_name').val();
		let surname = $('#host_surname').val();
		let username = $('#host_username').val();
		let password = $('#host_psw').val();
		let password_repeat = $('#host_psw-repeat').val();
		let gender = 0;
		
		if($('#host_female').is(":checked")){
			gender = 1;
		}
		
		if (!name) {
			$('#error_name1').text('Unos imena je obavezan');
			$('#error_name1').attr("hidden",false);
			return;
		}
		
		if(!surname){ 						
			$('#error_name1').attr("hidden",true);
			$('#error_surname1').text('Unos prezimena je obavezan');
			$("#error_surname1").attr("hidden",false);
			return;
		}
		
		if(!username){ 			
			$('#error_surname1').attr("hidden",true);
			$('#error_username1').text('Unos korisničkog imena je obavezan');
			$("#error_username1").attr("hidden",false);
			return;
		}
		
		if(!password){ 		
			$('#error_username1').attr("hidden",true);
			$('#error_psw1').text('Unos lozinke je obavezan');
			$("#error_psw1").attr("hidden",false);
			return;
		}
		
		if(!password_repeat){ 	
			$('#error_psw1').attr("hidden",true);
			$('#error_psw-repeat1').text('Ponovo unesite lozinku');
			$("#error_psw-repeat1").attr("hidden",false);
			return;
		}
		
		if(password != password_repeat){
			$('#error_password1').attr("hidden",true);
			$('#error_psw-repeat1').text('Unesene lozinke se ne poklapaju');
			$("#error_psw-repeat1").attr("hidden",false);
			return;
		}
		
		$('#error_psw-repeat1').attr("hidden",true);
		
		$.ajax({
			type: "POST",
			url: "rest/host_add",
			data: JSON.stringify({ 
				username: username, 
				password: password, 
				name: name,
				surname:surname,
				gender:gender,
				typeOfUser:2,
				blocked:false}),
			contentType: "application/json",
			success:function(data){
				$('#error_name1').attr("hidden",true);
				$('#error_surname1').attr("hidden",true);
				$('#error_username1').attr("hidden",true);
				$('#error_psw1').attr("hidden",true);
				$('#error_psw-repeat1').attr("hidden",true);
				
				toastr["success"]("Uspješno ste dodali domaćina");
				$("#close_btn").click();
				
				setTimeout(function(){
					location.reload(); }, 500); 
			},
			error: function(response){
				toastr["error"]("Korisničko ime već postoji!");
			}
		});
	});
	
	$('form#search_users').submit(function(event) {
		event.preventDefault();
		
		let username = $('#username_search').val();
		let user_type = $('#user_type option:selected').val();
		let gender_type = $('#gender_type option:selected').val();
		
		if(!username){
			username = "null";
		}
		if(user_type == ""){
			user_type = "null";
		}
		if(gender_type == ""){
			gender_type = "null";
		}
		
		if(username == "null" && gender_type == "null" && user_type == "null"){
			return;
		}
		
		if(user_type == "host"){
			$.ajax({
				type:"GET", 
				url: "rest/hosts/search/" + username + "/" + gender_type,
				contentType: "application/json",
				success:function(hosts){
					$('#users tbody').empty();
					$('#cancel_btn').attr("hidden",false);
					for (let h of hosts) {
						adduserTr(h);
					}
				},
				error:function(){
					console.log('error search hosts');
				}
			});
		}
		else if(user_type == "guest"){
			$.ajax({
				type:"GET", 
				url: "rest/guests/search/" + username + "/" + gender_type,
				contentType: "application/json",
				success:function(guests){
					$('#users tbody').empty();
					$('#cancel_btn').attr("hidden",false);
					for (let g of guests) {
						adduserTr(g);
					}
				},
				error:function(){
					console.log('error search guests');
				}
			});
		}
		else{
			$.ajax({
				type:"GET", 
				url: "rest/guests_hosts/search/" + username + "/" + gender_type,
				contentType: "application/json",
				success:function(users){
					$('#users tbody').empty();
					$('#cancel_btn').attr("hidden",false);
					for (let u of users) {
						adduserTr(u);
					}
				},
				error:function(){
					console.log('error search users');
				}
			});
		}
	});
});

function adduserTr(user) {
	let tr = $('<tr style="height:50px;"></tr>');
	let tdName = $('<td>' + user.name + '</td>');
	let tdSurname = $('<td>' + user.surname + '</td>');
	let tdGender = $('<td>' + 'Muški' + '</td>');
	
	if(user.gender == "FEMALE")
		tdGender = $('<td>' + 'Ženski' + '</td>');
	
    let tdType = $('<td>' + 'Gost' + '</td>');
    
    if(user.typeOfUser == "HOST")
    	tdType = $('<td>' + 'Domaćin' + '</td>');
    
	let tdUsername = $('<td>' + user.username + '</td>');
	
	let tdBlockButton = $('<td><button class="block btn del_amen" id="' + user.username + '" onclick="blockUser(this.id)"><i class="fas fa-ban" style="color: red;"></i></button></td>');
	let tdBlockedUser = $('<td style="color:red;font-size:16px;">Blokiran</td>');

	if(user.blocked){
		tr.append(tdName).append(tdSurname).append(tdGender).append(tdType).append(tdUsername).append(tdBlockedUser);
	}else{
		tr.append(tdName).append(tdSurname).append(tdGender).append(tdType).append(tdUsername).append(tdBlockButton);
	}
	
	$('#users tbody').append(tr);
};

function blockUser(username){
	
	$.ajax({
		url: "rest/users_block",
		type:"POST",
		data: username,
		success: function(){
			setTimeout(function(){
		           location.reload(); 
		      }, 50); 
		},
		error:function(){
			console.log('error')
		}
	})
};

function checkLoggedUser(){
	
	$.ajax({
		type: "GET",
		url: "rest/verification/admin",
		error:  function(jqXHR, textStatus, errorThrown)  {
			$('#users-review').hide(function() {
				alert(jqXHR.responseText);
				window.history.back();
			});
		}
	})
}
