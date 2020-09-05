$(document).ready(function() {
	
	$.ajax({
		type:"GET", 
		url: "rest/hosts_reservations",
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
				url: "rest/hosts_reservations/search/" + username,
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
			if(status == "KREIRANA"){
			btn_type = '<button class="btn btn-green edit_delete" type="submit" data-toggle="modal" data-target="#modalConfirmDelete">Prihvati</button><button class="btn btn-red edit_delete" type="submit" >Odbij</button>';
			}
			if(status == "PRIHVAĆENA"){
			btn_type ='<button class="btn btn-green edit_delete" type="submit" data-toggle="modal" data-target="#modalConfirmDelete">Završi</button><button class="btn btn-red edit_delete" type="submit" >Odbij</button>';
			}
			
			let reservation = $('<div class="border_apartments">' 
					   + '<table class="table_apartments">'
					   + '<tr><td><a href="reserve_an-apartment.html">'
					   + '<img class="img_apartment" src="https://apartmanialexandria.rs/wp-content/uploads/2015/03/Apartman-1-02.jpg" alt="thumbnail" class="img-thumbnail"/></a>'
		 			 + '</td><td><table style="height: 220px; margin-left: 40px; width: 350px;">'
			 	    + '<tr><td colspan="2"><a href="reserve_an-apartment.html">'
			 	   	+	'<h5>'+ apartment.name + '</h5></a></td></tr>'
			 	   + '<tr><th>Tip:</th><td>'+ type +'</td></tr>'
			 	   	+ '<tr><th>Dana:</th><td>'+ r.startDate +'</td></tr>'
			 	   	+ '<tr><th>Ukupno noćenja:</th><td>'+ r.numberOfNights +'</td></tr>'
			     	+ '<tr><th>Ukupna cijena:</th><td>'+ r.totalPrice +'</td></tr>'
			     	+ '<tr><th>Staus rezervacije:</th><td>' + status + '</td></tr>'
			     	+ '<tr><th>Poruka:</th><td>' + r.message + '</td></tr>'
			     	+ '<tr><th>Gost:</th><td>' + r.guestUsername + '</td></tr>'
			 	    + '</table></td><td>'
		            + btn_type + '</td></tr></table></div>');
		
		$('div#div_host_reservation').append(reservation);
		},
		error:function(){
			console.log('error getting apartment');
		}
	});
		
}

function addReservationSearch(r) {
	
	$.ajax({
		type:"GET", 
		url: "rest/apartments/" + r.apartmentId,
		contentType: "application/json",
		success:function(apartment){
			
			let type = "Soba";
			if(apartment.type == "WHOLE_APARTMENT"){
				type="Apartman";
			}
			
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
			if(status == "KREIRANA"){
			btn_type = '<button class="btn btn-green edit_delete" type="submit" data-toggle="modal" data-target="#modalConfirmDelete">Prihvati</button><button class="btn btn-red edit_delete" type="submit" >Odbij</button>';
			}
			if(status == "PRIHVAĆENA"){
			btn_type ='<button class="btn btn-green edit_delete" type="submit" data-toggle="modal" data-target="#modalConfirmDelete">Završi</button><button class="btn btn-red edit_delete" type="submit" >Odbij</button>';
			}
			
			let reservation = $('<div class="border_apartments">' 
					   + '<table class="table_apartments">'
					   + '<tr><td><a href="reserve_an-apartment.html">'
					   + '<img class="img_apartment" src="https://apartmanialexandria.rs/wp-content/uploads/2015/03/Apartman-1-02.jpg" alt="thumbnail" class="img-thumbnail"/></a>'
		 			 + '</td><td><table style="height: 220px; margin-left: 40px; width: 350px;">'
			 	    + '<tr><td colspan="2"><a href="reserve_an-apartment.html">'
			 	   	+	'<h5>'+ apartment.name + '</h5></a></td></tr>'
			 	   + '<tr><th>Tip:</th><td>'+ type +'</td></tr>'
			 	   	+ '<tr><th>Dana:</th><td>'+ r.startDate +'</td></tr>'
			 	   	+ '<tr><th>Ukupno noćenja:</th><td>'+ r.numberOfNights +'</td></tr>'
			     	+ '<tr><th>Ukupna cijena:</th><td>'+ r.totalPrice +'</td></tr>'
			     	+ '<tr><th>Staus rezervacije:</th><td>' + status + '</td></tr>'
			     	+ '<tr><th>Poruka:</th><td>' + r.message + '</td></tr>'
			     	+ '<tr><th>Gost:</th><td>' + r.guestUsername + '</td></tr>'
			 	    + '</table></td><td>'
		            + btn_type + '</td></tr></table></div>');
		
		$('div#div_host_reservation_searched').append(reservation);
		},
		error:function(){
			console.log('error getting apartment');
		}
	});
		
}