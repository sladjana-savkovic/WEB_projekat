$(document).ready(function() {
	
	$.ajax({
		type:"GET", 
		url: "rest/apartments_comments",
		contentType: "application/json",
		success:function(comments){
			for (let c of comments) {
				addComment(c);
			}
		},
		error:function(){
			console.log('error getting comments');
		}
	});
	
	let id=1;
	
	$.ajax({
		type:"GET", 
		url: "rest/apartments/" + id,
		contentType: "application/json",
		success: function(apartment){					
	
		addInfoApartment(apartment);
				
		},
		error:function(){
			console.log('error search reservations');
		}
	});
	
	$.ajax({
		type:"GET", 
		url: "rest/apartments_amenities",
		contentType: "application/json",
		success:function(amenities){
			for (let a of amenities) {
				addAmenities(a);
			}
		},
		error:function(){
			console.log('error getting amenities');
		}
	});
	
});

function addComment(c) {
	
			let stars = '<i class="fas fa-star"></i><i class="far fa-star"></i><i class="far fa-star"></i><i class="far fa-star"></i><i class="far fa-star"></i>';
			
			if(c.rating == 2){
				stars = '<i class="fas fa-star"></i><i class="fas fa-star"></i><i class="far fa-star"></i><i class="far fa-star"></i><i class="far fa-star"></i>';
			}
			if(c.rating == 3){
				stars = '<i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i><i class="far fa-star"></i><i class="far fa-star"></i>';
			}
			if(c.rating == 4){
				stars = '<i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i><i class="far fa-star"></i>';
			}
			if(c.rating == 5){
				stars = '<i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i>';
			}
			
			
			let comment = $('<div class="testimonial">'
				     +'<h5 class="font-weight-bold dark-grey-text mt-4">' + c.guestUsername +'</h5>'
			        +'<p class="font-weight-normal dark-grey-text">'
			         +' <i class="fas fa-quote-left pr-2"></i>'+ c.description +'</p>'
			        +'<div class="orange-text">' 
			        + stars +
			        +'</div></div>');
		
		$('div#div_comments').append(comment);
		
	
		
};

function addInfoApartment(apartment) {
	
	let type = "Soba";
	if(apartment.type == "WHOLE_APARTMENT"){
		type="Apartman";
	}
	
	let a = $(' <div class="border_apartments" style="width: 680px; ">'
 		+'<table class="table_apartments">'
 			+'<tr><td>'
		 	   +'<table style="height: 220px; margin-left: 40px; width: 350px;">'
		 	   +'<tr><td colspan="2"><h5>Podaci o objektu</h5></td>'
		 	 +'</tr><tr><th>Tip smještaja:</th><td>'+ type +'</td>'
		    +'</tr><tr><th>Adresa:</th><td>' + apartment.location.address.street + " " + apartment.location.address.number + ", " + apartment.location.address.city + " "+ apartment.location.address.zipCode +'</td>'
		     + '</tr><tr><th>Cijena po noći:</th><td>' + apartment.pricePerNight + 'RSD</td>'
		     +	'</tr><tr><th>Broj gostiju:</th><td>'+ apartment.numberOfGuests +'</td>'
		   + '</tr><tr><th>Broj soba:</th><td>' + apartment.numberOfRooms +'</td>'
		  + '</tr><tr><th>Domaćin:</th><td>' + apartment.hostUsername +'</td>'
		  + '</tr></table></td><td style="vertical-align: bottom;">'
	     + '<div><button data-toggle="modal" data-target="#modalReservationForm" class="btn btn-dark-green reservation" type="submit" >Rezervišite</button></div>'
	    + '</td></tr></table></div>');

$('div#div_about_apartment').append(a);



};


function addAmenities(a) {
	
	let u = ''
	
	let amenities = $('<ul class="list-group list-group-flush"><ul class="list-group"><li class="list-group-item">'
			   +'<div class="md-v-line"></div>' + a.name +'</li></ul>'
			   +'</ul>');

$('div#div_amenities').append(amenities);



};