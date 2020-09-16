$(document).ready(function() {
	
	checkLoggedUser();
	 $('#guest_name').val(''); 
	
	$('input[type="checkbox"]').each(function(){
	  	$(this).prop('checked', false);
	});
	
	$.ajax({
		type:"GET", 
		url: "rest/reservations",
		contentType: "application/json",
		success:function(reservations){
			for (let r of reservations) {
				addReservation(r);
			}
		},
		error:function(){
			console.log('error getting reservations');
		}
	});
	

	$('form#search_reservation').submit(function(event) {
		event.preventDefault();
		
		let username = $('#guest_name').val(); 
		
		if(!username){
			username = "null";
		}
		
			$.ajax({
				type:"GET", 
				url: "rest/reservations/search/" + username,
				contentType: "application/json",
				success: function(response){					
					let reservations = response;
					
									
					$('div#div_admin_reservation').empty();
					for(let r of reservations){
						addReservation(r);
					}
											
					
				},
				error:function(){
					console.log('error search reservations');
				}
			});
		
	});
	
$('#filter').click(function() {
		
		var status = [];
		
		if($('#created_status').is(":checked")){
			status.push("CREATED");
		}
		if($('#refused_status').is(":checked")){
			status.push("REFUSED");
		}
		if($('#canceled_status').is(":checked")){
			status.push("CANCELED");
		}
		if($('#accepted_status').is(":checked")){
			status.push("ACCEPTED");
		}
		if($('#finished_status').is(":checked")){
			status.push("FINISHED");
		}
		
		
		$.ajax({
			type:"POST", 
			url: "rest/reservations/filter",
			data:JSON.stringify(status),
			contentType: "application/json",
			success:function(reservations){
				$('div#div_admin_reservation').empty();
				for (let r of reservations) {
					addReservation(r);
				}
				
			},
			error:function(){
				console.log('error getting reservations');
			}
		});
		
				

	});

	$('#first_lowest').click(function() {
	
	$.ajax({
		type:"GET", 
		url: "rest/reservations/sort_ascending",
		contentType: "application/json",
		success:function(reservations){
			$('div#div_admin_reservation').empty();
			for (let r of reservations) {
				addReservation(r);
			}
			
		},
		error:function(){
			console.log('error getting ascending sort reservations');
		}
	});
	
	});	
	
	$('#first_highest').click(function() {
		
		$.ajax({
			type:"GET", 
			url: "rest/reservations/sort_descending",
			contentType: "application/json",
			success:function(reservations){
				$('div#div_admin_reservation').empty();
				for (let r of reservations) {
					addReservation(r);
				}
				
			},
			error:function(){
				console.log('error getting ascending sort reservations');
			}
		});
		
		});	
		
	
});


function addReservation(r) {
	
	$.ajax({
		type:"GET", 
		url: "rest/apartments/" + r.apartmentId,
		contentType: "application/json",
		success:function(apartment){
			
			let type = "Soba";
			if(apartment.type == "WHOLE_APARTMENT"){
				type="Apartman";
			}
			
			let status = "ZAVRŠENA";
			
			if(r.status == "ACCEPTED"){
				status="PRIHVAĆENA";
			}
			if(r.status == "CANCELED"){
				status="OTKAZANA";
			}
			if(r.status == "REFUSED"){
				status="ODBIJENA";
			}
			if(r.status == "CREATED"){
				status="KREIRANA";
			}
			
			
			let reservation = $('<div class="border_apartments">' 
					   + '<table class="table_apartments">'
					   + '<tr><td>'
					   + '<img class="img_apartment" src="http://localhost:' + location.port +'/PocetniREST/rest/apartments/first_image/' + apartment.id +'" alt="thumbnail"/>'
		 			 + '</td><td><table style="height: 220px; margin-left: 40px; width: 350px;">'
			 	    + '<tr><td colspan="2">'
			 	   	+	'<h5>'+ apartment.name + '</h5></td></tr>'
			 	   + '<tr><th>Tip:</th><td>'+ type +'</td></tr>'
			 	   	+ '<tr><th>Dana:</th><td>'+ r.startDate +'</td></tr>'
			 	   	+ '<tr><th>Ukupno noćenja:</th><td>'+ r.numberOfNights +'</td></tr>'
			     	+ '<tr><th>Ukupna cijena:</th><td>'+ r.totalPrice +'</td></tr>'
			     	+ '<tr><th>Status rezervacije:</th><td>' + status + '</td></tr>'
			     	+ '<tr><th>Poruka:</th><td>' + r.message + '</td></tr>'
			     	+ '<tr><th>Gost:</th><td>' + r.guestUsername + '</td></tr>'
			 	    + '</table></td><td style="vertical-align: bottom;">'
		            + '</td></tr></table></div>');
		
		$('div#div_admin_reservation').append(reservation);
		},
		error:function(){
			console.log('error getting reservation');
		}
	});
		
};


function checkLoggedUser(){
	
	$.ajax({
		type: "GET",
		url: "rest/verification/admin",
		error:  function(jqXHR, textStatus, errorThrown)  {
			$('body#admin_res').hide(function() {
				alert(jqXHR.responseText);
				window.history.back();
			});
		}
	});
};
