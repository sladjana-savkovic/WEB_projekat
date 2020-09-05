$(document).ready(function() {
	
	$.ajax({
		type:"GET", 
		url: "rest/guests_reservations",
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
			btn_type = '<button data-toggle="modal" data-target="#modalConfirmDelete" class="btn btn-danger reservation" type="submit" >Odustanite</button>';
			}
			if(status == "ZAVŠENA"){
			btn_type ='<button data-toggle="modal" data-target="#modalFeedbackForm" class="btn btn-brown reservation" type="submit" id="submit_btns">Komentarišite</button>';
			}
			
			let reservation = $('<div class="border_apartments">' 
					   + '<table class="table_apartments">'
					   + '<tr><td><a href="reserve_an-apartment.html">'
					   + '<img class="img_apartment" src="https://apartmanialexandria.rs/wp-content/uploads/2015/03/Apartman-1-02.jpg" alt="thumbnail" class="img-thumbnail"/></a>'
		 			 + '</td><td><table style="height: 220px; margin-left: 40px; width: 350px;">'
			 	    + '<tr><td colspan="2"><a href="reserve_an-apartment.html">'
			 	   	+	'<h5>'+ apartment.name + '</h5></a></td></tr>'
			 	   	+ '<tr><th>Dana:</th><td>'+ r.startDate +'</td></tr>'
			 	   	+ '<tr><th>Ukupno noćenja:</th><td>'+ r.numberOfNights +'</td></tr>'
			     	+ '<tr><th>Ukupna cijena:</th><td>'+ r.totalPrice +'</td></tr>'
			     	+ '<tr><th>Staus rezervacije:</th><td>' + status + '</td></tr>'
			     	+ '<tr><th>Poruka:</th><td>' + r.message + '</td></tr>'
			 	    + '</table></td><td>'
		            + btn_type + '</td></tr></table></div>');
		
		$('div#div_reservation').append(reservation);
		},
		error:function(){
			console.log('error getting apartment');
		}
	});
		
}