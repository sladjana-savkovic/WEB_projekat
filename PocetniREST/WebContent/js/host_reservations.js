$(document).ready(function() {
	
	//checkLoggedUser();
	
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
					
									
					$('div#div_host_reservation').empty();
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
				$('div#div_host_reservation').empty();
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
			$('div#div_host_reservation').empty();
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
				$('div#div_host_reservation').empty();
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
			let btn_type='';
			if(status == "KREIRANA"){
			btn_type = '<button class="btn btn-green edit_delete" type="submit" data-toggle="modal" data-target="#modalConfirmAccept" id="' + r.id +'" onclick="acceptReservation(this.id)">Prihvati</button><button class="btn btn-red edit_delete" type="submit" data-toggle="modal" data-target="#modalConfirmRefuse" id="' + r.id +'" onclick="refuseReservation(this.id)">Odbij</button>';
			}
			
			var today = new Date();
			today.setHours(0,0,0,0);
			var startDay = new Date(r.startDate);			
			
			if(status == "PRIHVAĆENA" && startDay < today){
				btn_type ='<button class="btn btn-green edit_delete" type="submit" data-toggle="modal" data-target="#modalConfirmFinish" id="' + r.id +'" onclick="finishReservation(this.id)">Završi</button>';
			}
			
			if(status == "PRIHVAĆENA" && startDay >= today){
				btn_type = '<button class="btn btn-red edit_delete" type="submit" data-toggle="modal" data-target="#modalConfirmRefuse" id="' + r.id +'" onclick="refuseReservation(this.id)">Odbij</button>';
			}
			
			let reservation = $('<div class="border_apartments">' 
					   + '<table class="table_apartments">'
					   + '<tr><td>'
					   + '<img class="img_apartment" src="http://localhost:8800/PocetniREST/rest/apartments/first_image/' + apartment.id +'" alt="thumbnail"/>'
		 			 + '</td><td><table style="height: 220px; margin-left: 40px; width: 350px;">'
			 	    + '<tr><td colspan="2">'
			 	   	+	'<h5>'+ apartment.name + '</h5></td></tr>'
			 	   + '<tr><th>Tip:</th><td>'+ type +'</td></tr>'
			 	   	+ '<tr><th>Dana:</th><td>'+ r.startDate +'</td></tr>'
			 	   	+ '<tr><th>Ukupno noćenja:</th><td>'+ r.numberOfNights +'</td></tr>'
			     	+ '<tr><th>Ukupna cijena:</th><td>'+ r.totalPrice +'</td></tr>'
			     	+ '<tr><th>Staus rezervacije:</th><td>' + status + '</td></tr>'
			     	+ '<tr><th>Poruka:</th><td>' + r.message + '</td></tr>'
			     	+ '<tr><th>Gost:</th><td>' + r.guestUsername + '</td></tr>'
			 	    + '</table></td><td style="vertical-align: bottom;">'
		            + btn_type + '</td></tr></table></div>');
		
		$('div#div_host_reservation').append(reservation);
		},
		error:function(){
			console.log('error getting reservation');
		}
	});
		
};

function acceptReservation(id){
	
	$('a#yes_accept').click(function(event){
		
		event.preventDefault();
		
		$.ajax({
			type: "POST",
			url: "rest/accept_reservation",
			contentType: "application/json",
			data: id,
			success: function(){
				$('a#no_accept').click();
				toastr["success"]("Uspješno ste prihvatili rezervaciju!");
				setTimeout(function(){
					location.reload(); }, 500); 
			},
			error:  function()  {
				toastr["error"]("Došlo je do greške. Pokušajte ponovo.");
			}
		});
		
	});
};

function finishReservation(id){
	
	$('a#yes_finish').click(function(event){
		
		event.preventDefault();
		
		$.ajax({
			type: "POST",
			url: "rest/finish_reservation",
			contentType: "application/json",
			data: id,
			success: function(){
				$('a#no_finish').click();
				toastr["success"]("Uspješno ste završili rezervaciju!");
				setTimeout(function(){
					location.reload(); }, 500); 
			},
			error:  function()  {
				toastr["error"]("Došlo je do greške. Pokušajte ponovo.");
			}
		});
		
	});
};


function refuseReservation(id){
	
	$('a#yes_refuse').click(function(event){
		
		event.preventDefault();
		
		$.ajax({
			type: "POST",
			url: "rest/refuse_reservation",
			contentType: "application/json",
			data: id,
			success: function(){
				$('a#no_refuse').click();
				toastr["success"]("Uspješno ste odbili rezervaciju!");
				setTimeout(function(){
					location.reload(); }, 500); 
			},
			error:  function()  {
				toastr["error"]("Došlo je do greške. Pokušajte ponovo.");
			}
		});
		
	});
};

function checkLoggedUser(){
	
	$.ajax({
		type: "GET",
		url: "rest/verification/host",
		error:  function(jqXHR, textStatus, errorThrown)  {
			$('body#host_res').hide(function() {
				alert(jqXHR.responseText);
				window.history.back();
			});
		}
	});
};
