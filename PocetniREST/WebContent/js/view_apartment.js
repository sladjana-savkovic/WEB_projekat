$(document).ready(function() {
	
	var typeOfUser = checkLoggedUser();
	
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
	
	
	  //getting all images name
	  $.ajax({
			type:"GET", 
			url: "rest/apartments/all_images/" + id,
			contentType: "application/json",
			success:function(images){
				$('div#apartment_images').empty();
				for(let i of images){
					let image_div = $('<div class="column">'
						 + '<img height="160px" width="190px" src="http://localhost:8800/PocetniREST/rest/apartments/one_image/' + i +'" onclick="myFunction(this);">'
						 + '</div>');
					$('div#apartment_images').append(image_div);
				}
			},
			error:function(){
				console.log('error getting images');
			}
		});
	  
	 
	$.ajax({
		type:"GET", 
		url: "rest/apartments_comments/" + id,
		contentType: "application/json",
		success:function(comments){
			
			if(comments.length == 0){
				let p = $('<p style="text-align:center; margin-top:30px;">Nema komentara</p>');
				$('div#div_comments').append(p);
				return;
			}
			
			
			for (let c of comments) {
				addComment(c,typeOfUser);
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
			addInfoApartment(apartment,typeOfUser);
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

function addComment(c,typeOfUser) {
		
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
	
	if(typeOfUser == "ADMIN"){
		let status_tag = '<h6 style="color: green; margin-top: 15px;">Odobren</h6>';
		
		if(c.status == "DISAPPROVED"){
			status_tag = '<h6 style="color: red; margin-top: 15px;">Odbijen</h6>';
		}
		
		comment = $('<div class="testimonial">'
			     +'<h5 class="font-weight-bold dark-grey-text mt-4">' + c.guestUsername +'</h5>'
		        + status_tag + '<p class="font-weight-normal dark-grey-text">'
		        +' <i class="fas fa-quote-left pr-2"></i>'+ c.description +'</p>'
		        +'<div class="orange-text">' 
		        + stars +
		        +'</div></div>');
	}

    $('div#div_comments').append(comment);
	
};

function addInfoApartment(apartment,typeOfUser) {
	
	$.ajax({
		type:"GET", 
		url: "rest/users/" + apartment.hostUsername,
		contentType: "application/json",
		success:function(h){
			host = h.name + " " + h.surname;
	
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
		    +'</tr><tr><th>Adresa:</th><td>' + apartment.location.address.streetAndNumber + ", " + apartment.location.address.city + " "+ apartment.location.address.zipCode +'</td>'
		     + '</tr><tr><th>Cijena po noći:</th><td>' + apartment.pricePerNight + 'RSD</td>'
		     +	'</tr><tr><th>Broj gostiju:</th><td>'+ apartment.numberOfGuests +'</td>'
		   + '</tr><tr><th>Broj soba:</th><td>' + apartment.numberOfRooms +'</td>'
		  + '</tr><tr><th>Domaćin:</th><td>' + host +'</td>'
		  + '</tr></table>'
		  + '<td style="vertical-align:bottom;">'
	     + '<div><button data-toggle="modal" data-target="#modalReservationForm" class="btn btn-dark-green reservation" type="submit" id="' + apartment.id +'" onclick="newReservation(this.id)">Rezervišite</button></div>'
	    + '</td></tr></table></div>');
	
	if(typeOfUser == "ADMIN" || typeOfUser == "HOST"){
		
		a = $(' <div class="border_apartments" style="width: 680px; ">'
		 		+'<table class="table_apartments">'
		 			+'<tr><td>'
				 	   +'<table style="height: 220px; margin-left: 40px; width: 350px;">'
				 	   +'<tr><td colspan="2"><h5>Podaci o objektu</h5></td>'
				 	 +'</tr><tr><th>Tip smještaja:</th><td>'+ type +'</td>'
				    +'</tr><tr><th>Adresa:</th><td>' + apartment.location.address.streetAndNumber + ", " + apartment.location.address.city + " "+ apartment.location.address.zipCode +'</td>'
				     + '</tr><tr><th>Cijena po noći:</th><td>' + apartment.pricePerNight + 'RSD</td>'
				     +	'</tr><tr><th>Broj gostiju:</th><td>'+ apartment.numberOfGuests +'</td>'
				   + '</tr><tr><th>Broj soba:</th><td>' + apartment.numberOfRooms +'</td>'
				  + '</tr><tr><th>Domaćin:</th><td>' + host +'</td>'
				  + '</tr></table></td>'
				  + '<td style="vertical-align: bottom; "><button class="btn btn-orange edit_delete" type="submit" id="' + apartment.id +'" onclick="editApartment(this.id)">Izmijeni</button>'
				  + '<button class="btn btn-red edit_delete" type="submit"  data-toggle="modal" data-target="#modalConfirmDelete" id="' + apartment.id +'" onclick="deleteApartment(this.id)">Obriši</button>'
 			      + '</td></tr></table></div>');
	}

    $('div#div_about_apartment').append(a);

		},
		error:function(){
			console.log('error getting host');
			return;
		}
	});

};


function addAmenities(a) {
	
	let amenities = $('<tr>'
			+ '<td> <i class="fas fa-check"></i></td><td><strong style="font-size: 20px; color: dark-grey;"><b>' + a.name + '</b></strong></td>'
			+ '</tr>');

$('#table_amenities').append(amenities);

};


function newReservation(apartmentId){
	
	//var now = new Date();
	//var day = ("0" + now.getDate()).slice(-2);
	//var month = ("0" + (now.getMonth() + 1)).slice(-2);
	//var today = now.getFullYear() + "-" + (month) + "-" + (day);
	
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
				
				let url_split = window.location.href.split("?")[1];
				let parameters = url_split.split("&");
				
				let reservation_date = parameters[1].split("=")[1];
				
				if(reservation_date.localeCompare("")==0){
					$(dates[0]).prop("selected",true);
				}else{
					$(reservation_date).prop("selected",true);
				}
		
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
	 
	
	 $('#start_date').change(function () {
		 
	let selectDate = $("#start_date :selected").text();
	$('#night_number').val(1);
		 
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
						guestUsername : "",
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

function editApartment(id){
	window.location.href = "admin_host_edit-apart.html?id=" + id;
}


function deleteApartment(id){
	
	$('a#yes_delete').click(function(event){
		
		event.preventDefault();
		
		$.ajax({
			type: "GET",
			url: "rest/reservations/apartment_delete/" + id,
			contentType: "application/json",
			success: function(result){
				if(result){
					toastr["error"]("Za apartman koji želite obrisati postoje rezervacije");
					$('a#no_delete').click();
					return;
				}else{
					$.ajax({
						type: "DELETE",
						url: "rest/apartments/delete",
						contentType: "application/json",
						data:id,
						success: function(){
							$('a#no_delete').click();
							toastr["success"]("Uspješno ste obrisali apartman");
							
							let typeOfUser = checkLoggedUser();
							if(typeOfUser == "HOST"){
								window.location.href = "host_apartments.html";
							}else{
								window.location.href = "admin_apartments.html";
							}
						},
						error:  function()  {
							console.log('Došlo je do greške prilikom brisanja apartmana');
						}
					});
					
				}
			},
			error:  function()  {
				console.log('Došlo je do greške prilikom provjere da li apartman ima rezervacije');
			}
		});
		
	});
};

function checkLoggedUser(){
	
	let retVal = "";
	
	$.ajax({
		type:"GET", 
		url: "rest/verification/is_logged",
		contentType: "application/json",
		async: false,
		success:function(user){
			if(user == null){
				$('#reserve_apartment').hide(function() {
					alert(jqXHR.responseText);
					window.history.back();
				});
			}else{
				if(user.typeOfUser == "GUEST"){
					$('#guest_navbar').attr("hidden",false);
					$('#host_navbar').attr("hidden",true);
					$('#admin_navbar').attr("hidden",true);
				}
				else if(user.typeOfUser == "HOST"){
					$('#host_navbar').attr("hidden",false);
					$('#guest_navbar').attr("hidden",true);
					$('#admin_navbar').attr("hidden",true);
				}
				else{
					$('#admin_navbar').attr("hidden",false);
					$('#guest_navbar').attr("hidden",true);
					$('#host_navbar').attr("hidden",true);
				}
			}
			retVal = user.typeOfUser;
		},
		error:function(){
			console.log('error getting user');
		}
	});
	
	return retVal;
}

function transliterate(word){
    var answer = ""
      , a = {};
    
    a["А"]="A";a["а"]="a";a["Б"]="B";a["б"]="b";a["В"]="V";a["в"]="v";a["Г"]="G";a["г"]="g";a["Д"]="D";a["д"]="d";a["Ђ"]="Đ";a["ђ"]="đ";
    a["E"]="E";a["е"]="e";a["Ж"]="Ž";a["ж"]="ž";a["З"]="Z";a["з"]="z";a["И"]="I";a["и"]="i";a["Ј"]="J";a["ј"]="j";a["К"]="K";a["к"]="k";
    a["Л"]="L";a["л"]="l";a["Љ"]="Lj";a["љ"]="lj";a["М"]="M";a["м"]="m";a["Н"]="N";a["н"]="n";a["Њ"]="Nj";a["њ"]="nj";a["О"]="O";a["о"]="o";
    a["П"]="P";a["п"]="p";a["Р"]="R";a["р"]="r";a["С"]="S";a["с"]="s";a["Т"]="T";a["т"]="t";a["Ћ"]="Ć";a["ћ"]="ć";a["У"]="U";a["у"]="u";
    a["Ф"]="F"; a["ф"]="f";a["Х"]="H";a["х"]="h";a["Ц"]="C";a["ц"]="c";a["Ч"]="Č";a["ч"]="č";a["Џ"]="dž";a["џ"]="dž";a["Ш"]="Š";a["ш"]="š";

   for (i in word){
     if (word.hasOwnProperty(i)) {
       if (a[word[i]] === undefined){
         answer += word[i];
       } else {
         answer += a[word[i]];
       }
     }
   }
   return answer;
};