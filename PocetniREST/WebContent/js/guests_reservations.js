$(document).ready(function() {
	
	//checkLoggedUser();
	
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
	
	$('#first_lowest').click(function() {
		
		$.ajax({
			type:"GET", 
			url: "rest/reservations/sort_ascending",
			contentType: "application/json",
			success:function(reservations){
				$('div#div_reservation').empty();
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
					$('div#div_reservation').empty();
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
			
			let status = "ZAVŠENA";
			
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
			if(status == "KREIRANA" || status == "PRIHVAĆENA"){
			btn_type = '<button class="btn btn-danger reservation" data-toggle="modal" data-target="#modalConfirmCancel" id="' + r.id +'" onclick="cancelReservation(this.id)">Odustanite</button>';
			}
			if(status == "ZAVŠENA"){
			btn_type ='<button data-toggle="modal" data-target="#modalFeedbackForm" class="btn btn-brown reservation" type="submit" id="' + r.apartmentId +'" onclick="commentApartment(this.id)">Komentarišite</button>';
			}
			
			let reservation = $('<div class="border_apartments">' 
					   + '<table class="table_apartments">'
					   + '<tr><td>'
					   + '<img class="img_apartment" src="http://localhost:8800/PocetniREST/rest/apartments/first_image/' + apartment.id +'" alt="thumbnail"/>'
		 			 + '</td><td><table style="height: 220px; margin-left: 40px; width: 350px;">'
			 	    + '<tr><td colspan="2">'
			 	   	+	'<h5>'+ apartment.name + '</h5></td></tr>'
			 	   	+ '<tr><th>Dana:</th><td>'+ r.startDate +'</td></tr>'
			 	   	+ '<tr><th>Ukupno noćenja:</th><td>'+ r.numberOfNights +'</td></tr>'
			     	+ '<tr><th>Ukupna cijena:</th><td>'+ r.totalPrice +'</td></tr>'
			     	+ '<tr><th>Staus rezervacije:</th><td>' + status + '</td></tr>'
			     	+ '<tr><th>Poruka:</th><td>' + r.message + '</td></tr>'
			 	    + '</table></td><td style="vertical-align: bottom;">'
		            + btn_type + '</td></tr></table></div>');
		
		$('div#div_reservation').append(reservation);
		},
		error:function(){
			console.log('error getting reservation');
		}
	});
		
};

function cancelReservation(id){
	
	$('a#yes_cancel').click(function(event){
		
		event.preventDefault();
		
		$.ajax({
			type: "POST",
			url: "rest/cancel_reservation",
			contentType: "application/json",
			data: id,
			success: function(){
				$('a#no_cancel').click();
				toastr["success"]("Uspješno ste otkazali rezervaciju!");
				setTimeout(function(){
					location.reload(); }, 500); 
			},
			error:  function()  {
				toastr["error"]("Došlo je do greške. Pokušajte ponovo.");
			}
		});
		
	});
};

function commentApartment(apartmentId){
	
	$('a#yes_comment').click(function(event){
		
		event.preventDefault();
		
		var new_id;
		
		let description = $('#text_comment').val();
		
		let rating = 1;
		
		if($('#ratio_2').is(":checked")){
			rating = 2;
		}
		if($('#ratio_3').is(":checked")){
			rating = 3;
		}
		if($('#ratio_4').is(":checked")){
			rating = 4;
		}
		if($('#ratio_5').is(":checked")){
			rating = 5;
		}
		
		
		$.ajax({
			type:"GET", 
			url: "rest/comments/new_id",
			contentType: "application/json",
			success:function(id){
				new_id = id;
				
				$.ajax({
					type:"POST", 
					url: "rest/comments/add",
					data: JSON.stringify({ 
						id: new_id,
						guestUsername : "",
						apartmentId : apartmentId,
						description: description,
						rating : rating,
						status : "CREATED"}),
					contentType: "application/json",
					success:function(){
						toastr["success"]("Uspješno ste dodali komentar");
						setTimeout(function(){
					           location.reload(); 
					      }, 50); 
					},
					error:function(){
						toastr["error"]("Došlo je do greške prilikom dodavanja komentara!");
					}
				});
			},
			error:function(){
				console.log('error getting last id');
			}
		});
		
	});
};

function checkLoggedUser(){
	
	$.ajax({
		type: "GET",
		url: "rest/verification/guest",
		error:  function(jqXHR, textStatus, errorThrown)  {
			$('body#guest_res').hide(function() {
				alert(jqXHR.responseText);
				window.history.back();
			});
		}
	});
};