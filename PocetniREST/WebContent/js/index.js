$(document).ready(function() {
	
	let split_href = window.location.href.split('/');
	
	if(split_href[split_href.length - 1] == "guest_new-reservation.html"){
		//checkLoggedUser();
	}
	
	//map
	var map = L.map('map').setView([45.267136, 19.833549], 10);
	
	  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
	    attribution: '&copy; <a href="https://osm.org/copyright">OpenStreetMap</a> contributors'
	  }).addTo(map);
	
	  var geocodeService = L.esri.Geocoding.geocodeService();
	  
	  var marker;
	  var address = "";
	  
	  map.on('click', function (e) {
		
	    geocodeService.reverse().latlng(e.latlng).run(function (error, result) {
	      if (error) {
	        return;
	      }
	     
	      if(marker != undefined){
	    	  map.removeLayer(marker)
	      }
	      
	       marker = new L.Marker(result.latlng);
	       map.addLayer(marker);
	       marker.bindPopup(transliterate(result.address.Match_addr)).openPopup();
	       address = transliterate(result.address.Match_addr);
	      
	    });
	  });
	
    $('#st_date').prop("min",new Date().toISOString().split("T")[0]);
	$('#end_date').prop("min",new Date().toISOString().split("T")[0]);
	
	$('input[type="text"]').each(function(){
	  	$(this).val('');
	});
	
	$('input[type="number"]').each(function(){
	  	$(this).val('');
	});
    
  
	//getting all apartments
	$.ajax({
		type:"GET", 
		url: "rest/apartments",
		contentType: "application/json",
		success:function(apartments){
			for (let a of apartments) {
				addApartmentTable(a);
			}
		},
		error:function(){
			console.log('error getting apartments');
		}
	});
	  
	  $('#first_lowest').click(function() {
			
			$.ajax({
				type:"GET", 
				url: "rest/apartments/sort_ascending",
				contentType: "application/json",
				success:function(apartments){
					$('div#apartments').empty();
					for (let a of apartments) {
						addApartmentTable(a);
					}
					
				},
				error:function(){
					console.log('error getting ascending sort apartments');
				}
			});
			
		});	
		
		$('#first_highest').click(function() {
			
			$.ajax({
				type:"GET", 
				url: "rest/apartments/sort_descending",
				contentType: "application/json",
				success:function(apartments){
					$('div#apartments').empty();
					for (let a of apartments) {
						addApartmentTable(a);
					}
					
				},
				error:function(){
					console.log('error getting descending sort apartments');
				}
			});
			
		});	
		
		$('#search_apartments').submit(function(event) {
			
			event.preventDefault();
			
			let start_date = $('#st_date').val();
			let end_date = $('#end_date').val();
			let price_min = $('#price_min').val();
			let price_max = $('#price_max').val();
			let rooms_min = $('#rooms_min').val();
			let rooms_max = $('#rooms_max').val();
			let persons = $('#persons').val();
			let city = "null";
			
			if(address){
				let split_address = address.split(",");
				let city1 = split_address[split_address.length - 3];
				city = city1.substring(1, city1.length);
			}
						
			if(end_date && (end_date < start_date)){
				toastr["error"]("Krajnji datum ne smije biti manji od početnog");
				$('#end_date').val('');
				return;
			}
			if(parseInt(price_max) < parseInt(price_min)){
				toastr["error"]("Maksimalna cijena ne smije biti manji od minimalne");
				$('#price_max').val('');
				return;
			}
			if(parseInt(rooms_max) < parseInt(rooms_min)){
				toastr["error"]("Maksimalan broj soba ne smije biti manji od minimalnog");
				$('#end_date').val('');
				return;
			}
			
			let counter = 0;
					
			if(!start_date){
				start_date = "null";
				counter++;
			}
			if(!end_date){
				end_date="null";
				counter++;
			}
			if(city == "null"){
				counter++;
			}
			if(!price_min){
				price_min = 0;
				counter++;
			}
			if(!price_max){
				price_max = 0;
				counter++;
			}
			if(!rooms_min){
				rooms_min = 0;
				counter++;
			}
			if(!rooms_max){
				rooms_max = 0;
				counter++;
			}
			if(!persons){
				persons = 0;
				counter++;
			}
			
			if(counter == 8){
				return;
			}
			
			$.ajax({
				type:"POST", 
				url: "rest/apartments/search",
				data: JSON.stringify({ 
					city: city, 
					startDate: start_date, 
					endDate: end_date,
					minPrice:parseInt(price_min),
					maxPrice:parseInt(price_max),
					minRooms:parseInt(rooms_min),
					maxRooms:parseInt(rooms_max),
					persons:parseInt(persons)}),
				contentType: "application/json",
				success:function(apartments){
					$('div#apartments').empty();
					for (let a of apartments) {
						addApartmentTable(a);
					}
					
				},
				error:function(){
					console.log('error getting apartments');
				}
			});
	  });
	
});

function addApartmentTable(a){
	
	var host = "";
	var amenities = "";
	
	//Preuzimanje domacina
	$.ajax({
		type:"GET", 
		url: "rest/users/" + a.hostUsername,
		contentType: "application/json",
		success:function(h){
			host = h.name + " " + h.surname;
			
			//Preuzimanje svih sadrzaja
			$.ajax({
				type:"GET", 
				url: "rest/amenities",
				contentType: "application/json",
				success:function(amen){
					for (let am of amen) {
						for(let am1 of a.amenities){
							if(am.id == am1){
								amenities += am.name + ", ";
								break;
							}
						}
						
					}
					
					let type = "Soba";
					if(a.type == "WHOLE_APARTMENT"){
						type="Apartman";
					}
					let status = "Aktivan";
					if(a.active == false){
						status = "Neaktivan";
					}
					
					let address = a.location.address.street + " " + a.location.address.number + ", " + a.location.address.city + " " + a.location.address.zipCode + ", " + a.location.address.country; 
					
					let td_status = '<td style="color: green; font-weight: bolder;">' + status + '</td>';
					if(status == "Neaktivan"){
						td_status = '<td style="color: red; font-weight: bolder;">' + status + '</td>';
					}
					
					let apartment = $('<div class="border_apartments">'
					 				+ '<table class="table_apartments">'
					 				+ '<tr><td><a id="' + a.id + '" onclick="viewApartment(this.id)">'
				 					+ '<img class="img_apartment" src="https://apartmanialexandria.rs/wp-content/uploads/2015/03/Apartman-1-02.jpg" alt="thumbnail" class="img-thumbnail"/>'
				 					+ '</a></td>'
				 					+ '<td><table style="height: 220px; margin-left: 40px; width: 350px;">'
				 					+ '<tr><td colspan="2"><a id="' + a.id + '" onclick="viewApartment(this.id)">'
				 					+ '<h5 style="color:#1E90FF;">' + a.name + '</h5></a></td></tr>'
				 					+ '<tr><th style="width: 35%;">Tip apartmana:</th><td>' + type + '</td></tr>'
				 					+ '<tr><th>Adresa:</th><td>' + address + '</td></tr>'
				 					+ '<tr><th>Cijena po noći:</th><td> ' + a.pricePerNight + ' rsd</td></tr>'
				 					+ '<tr><th>Domaćin:</th><td>' + host + '</td></tr>'
				 					+ '<tr><th>Sadržaj:</th><td>' + amenities.substring(0, amenities.length-2) + '</td></tr>'
				 					+ '</table></td>'
				 					+ '<td style="vertical-align: bottom;">'
				 					+ '<button class="btn btn-green edit_delete"  data-toggle="modal" data-target="#modalConfirmDelete" id="' + a.id +'" onclick="reserveApartment(this.id)">Rezerviši</button>'
				 					+ '</td></tr></table></div>');
					
					$('div#apartments').append(apartment);
				},
				error:function(){
					console.log('error getting amenities');
					return;
				}
			});
		},
		error:function(){
			console.log('error getting host');
			return;
		}
	});
};

function viewApartment(id){
	let start_date = $('#st_date').val();
	window.location.href = "reserve_an-apartment.html?id=" + id + '&start_date=' + start_date;
};

function reserveApartment(id){
	
	$.ajax({
		type:"GET", 
		url: "rest/verification/is_logged",
		contentType: "application/json",
		success:function(user){
			if(user == null){
				toastr["error"]("Registrujte se ili prijavite da biste rezervisali apartman");
				return;
			}else{
				let start_date = $('#st_date').val();
				window.location.href = "reserve_an-apartment.html?id=" + id + '&start_date=' + start_date;
			}
		},
		error:function(){
			console.log('error getting user');
		}
	});
	
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

function checkLoggedUser(){
	
	$.ajax({
		type: "GET",
		url: "rest/verification/guest",
		error:  function(jqXHR, textStatus, errorThrown)  {
			$('#admin_amenities').hide(function() {
				alert(jqXHR.responseText);
				window.history.back();
			});
		}
	});
}