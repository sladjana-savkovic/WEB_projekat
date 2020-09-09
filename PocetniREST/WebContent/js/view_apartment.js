$(document).ready(function() {
	
	let url_split = window.location.href.split("?")[1];
	let parameters = url_split.split("&");
	
	var id = parameters[0].split("=")[1];
	var reservation_date = parameters[1].split("=")[1];
	
	//map
	var map = L.map('map').setView([44.815071, 20.460480], 6);
	
	  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
	    attribution: '&copy; <a href="https://osm.org/copyright">OpenStreetMap</a> contributors'
	  }).addTo(map);
		  
	  var marker;
	 
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
		
	$.ajax({
		type:"GET", 
		url: "rest/apartments/" + id,
		contentType: "application/json",
		success: function(apartment){	
			var lat = (apartment.location.latitude);
		    var lng = (apartment.location.longitude);
		    var newLatLng = new L.LatLng(lat, lng);
		    marker = new L.Marker(newLatLng);
		    map.addLayer(marker);
		    map.setView([lat,lng], 12);
			
			$('#apartman_name').text(apartment.name);
			addInfoApartment(apartment);
		},
		error:function(){
			console.log('error search reservations');
		}
	});
	
	$.ajax({
		type:"GET", 
		url: "rest/apartments_amenities/" + id,
		contentType: "application/json",
		success:function(amenities){
			
			if(amenities.length == 0){
				$('div#div_amenities').append('Nema sadržaja');
				return;
			}
			
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
		  + '</tr></table>'
		  + '<td style="vertical-align:bottom;">'
	     + '<button  data-toggle="modal" data-target="#modalReservationForm" class="btn btn-dark-green reservation" type="submit" >Rezervišite</button>'
	    + '</td></tr></table></div>');

$('div#div_about_apartment').append(a);



};


function addAmenities(a) {
	
	let amenities = $('<ul class="list-group list-group-flush"><ul class="list-group"><li class="list-group-item">'
			   +'<div class="md-v-line"></div>' + a.name +'</li></ul>'
			   +'</ul>');

$('div#div_amenities').append(amenities);



};


function newReservation(apartmentId){
	
	//var now = new Date();
	//var day = ("0" + now.getDate()).slice(-2);
	//var month = ("0" + (now.getMonth() + 1)).slice(-2);
	//var today = now.getFullYear() + "-" + (month) + "-" + (day);
	
	// $('#start_date').attr('min', "2020-09-09");
	 //$('#start_date').attr('max', "2020-09-09");
	 
	 
	// $('#start_date').attr('min', "2020-09-13");
	 //$('#start_date').attr('max', "2020-09-13");
	
	
	 $.ajax({
			type:"GET", 
			url: "rest/apartments/" + apartmentId,
			contentType: "application/json",
			success: function(apartment){	
				
				$("#name_of_apartment").append('<strong>' + apartment.name + '</strong>')
				
				let dates = apartment.availableDates;
				
				for(let d of dates){
					let optionDate = $('<option id="' + d +'">' + d + '</option>');
					$('#start_date').append(optionDate);
				}
				//uzmi iz pretrage
				$("#2020-09-09").prop("selected",true);
				$('#night_number').val(1);
				
				let selectDate = $("#start_date :selected").text();
				let numberOfNights = $('#night_number').val();
				
				$.ajax({
					type:"GET", 
					url: "rest/reservations/total_price/" + selectDate + "/" + numberOfNights + "/" + apartmentId,
					contentType: "application/json",
					success: function(total){									
						
						$("#total_price").val(total);
						
					},
					error:function(){
						console.log('error calculatin total price of reservation');
					}
				});
				
			},
			error:function(){
				console.log('error search reservations');
			}
		});
	 
	 
	// $('#start_date').on('select', function() {
	//$('#start_date option').each(function() {
	 $('#start_date').change(function () {
		 
	let selectDate = $("#start_date :selected").text();
	//let numberOfNights = $('#night_number').val();
		 
	 $.ajax({
			type:"GET", 
			url: "rest/reservations/max_num_night/" + selectDate + "/" + apartmentId,
			contentType: "application/json",
			success: function(maxNumber){		
			
			 	$('#night_number').attr('max', maxNumber);
			 	
			 	let numberOfNights = $('#night_number').val();
				$.ajax({
					type:"GET", 
					url: "rest/reservations/total_price/" + selectDate + "/" + numberOfNights + "/" + apartmentId,
					contentType: "application/json",
					success: function(total){									
						
						$("#total_price").val(total);
						
					},
					error:function(){
						console.log('error calculatin total price of reservation');
					}
				});
				
			},
			error:function(){
				console.log('error get max number of night');
			}
		});
	 
	 
	 });
	 
	$('#night_number').on('input', function() {
		
		let selectDate = $("#start_date :selected").text();
		let numberOfNights = $('#night_number').val();
		
		$.ajax({
			type:"GET", 
			url: "rest/reservations/total_price/" + selectDate + "/" + numberOfNights + "/" + apartmentId,
			contentType: "application/json",
			success: function(total){									
				
				$("#total_price").val(total);
				
			},
			error:function(){
				console.log('error calculatin total price of reservation');
			}
		});
		
		 
	});
	
	$('#reserve_confirm').click(function(event){
		
		event.preventDefault();
		
		var new_id;
		
		let message = $('#message_text').val();
		
		let startDate = $('#start_date').val();
	
		
		let numberOfNights = $('#night_number').val();
		
		
		let total_price = $('#total_price').val();
		
		
		$.ajax({
			type:"GET", 
			url: "rest/reservations/new_id",
			contentType: "application/json",
			success:function(id){
				new_id = id;
				
				$.ajax({
					type:"POST", 
					url: "rest/reservations/add",
					data: JSON.stringify({ 
						id: new_id,
						apartmentId : apartmentId,
						startDate : startDate,
						numberOfNights : numberOfNights,
						totalPrice : total_price,
						message : message,
						guestUsername : "pero123",
						status : "CREATED"}),
					contentType: "application/json",
					success:function(){
						$('#modalReservationForm').modal('toggle');
						toastr["success"]("Uspješno ste rezervisali apartman!");
						setTimeout(function(){
					           location.reload(); 
					      }, 50); 
					},
					error:function(){
						toastr["error"]("Došlo je do greške prilikom rezervisanja apartmana!");
					}
				});
			},
			error:function(){
				console.log('error getting last id');
			}
		});
		
	});
};