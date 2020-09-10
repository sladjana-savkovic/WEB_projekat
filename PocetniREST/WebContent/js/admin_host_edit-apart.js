$(document).ready(function() {
	
	checkLoggedUser();
	
	var id = window.location.href.split("=")[1];
	
	//map
	var map = L.map('map_edit').setView([44.815071, 20.460480], 6);
	
	  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
	    attribution: '&copy; <a href="https://osm.org/copyright">OpenStreetMap</a> contributors'
	  }).addTo(map);
		  
 var geocodeService = L.esri.Geocoding.geocodeService();
	  
	  var marker;
	  var address = "";
	  var lat_select;
	  var lng_select;
	  
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
	       lat_select = result.latlng.lat;
	       lng_select = result.latlng.lng;
	      
	    });
	  });
	
	var all_amenities = [];

	//form - getting amenities
	$.ajax({
		type:"GET", 
		url: "rest/amenities",
		contentType: "application/json",
		success:function(amenities){
			for (let a of amenities) {
				let a_div = $('<div class="form-check"">'
							+ '<input type="checkbox" class="form-check-input" id="' + a.id + '">'
							+ '<label class="form-check-label" for="climaFilter">' + a.name + '</label></div>');
				$('div#div_amenities_edit').append(a_div);
				all_amenities.push(a.id);
			
			}
			
		},
		error:function(){
			console.log('error getting amenities');
		}
	});
	
	var name="";
	var type="WHOLE_APARTMENT";
	var room_number="";
	var guest_number="";
	var start_date="";
	var end_date="";
	var price="";
	var check_in="";
	var check_out="";
	var status="INACTIVE";
	var amenities=[];
	
	var host_name="";
	var available_days=[];
	var comments=[];
	var reservations=[];
	var is_deleted;
	var loc;

	$.ajax({
		type:"GET", 
		url: "rest/apartments/" + id,
		contentType: "application/json",
		success: function(apartment){					
	
		name = apartment.name;
		
		if(apartment.type == "ROOM"){
			type = "ROOM";
		}
		
		loc = apartment.location;
		
		
		var lat = (apartment.location.latitude);
	    var lng = (apartment.location.longitude);
	   
	    var newLatLng = new L.LatLng(lat, lng);
	    marker = new L.Marker(newLatLng);
	    map.addLayer(marker);
	    map.setView([lat,lng], 12);
		
		host_name = apartment.hostUsername;
		available_days = apartment.availableDates;
		comments = apartment.comments;
		reservations = apartment.reservations;
		is_deleted = apartment.deleted;
		
		room_number = apartment.numberOfRooms;
		guest_number = apartment.numberOfGuests;
		start_date = apartment.rentingDates[0];
		end_date = apartment.rentingDates[apartment.rentingDates.length-1];
		price = apartment.pricePerNight;
		check_in = apartment.checkInTime;
		check_out = apartment.checkOutTime;
		amenities = apartment.amenities;
	
		
		$('input[type="checkbox"]').each(function(){
			
			for(let aid of amenities){
				$('#' + aid).prop('checked', true);
			}
		  
		});

		
		if(apartment.active == true){
			status = "ACTIVE";
		}
		
		$('#name_apart_edit').val(name);
		
		if(type == "WHOLE_APARTMENT"){
			$('#whole_apart_edit').attr('checked', 'checked');
		}else{
			$('#room_edit').attr('checked', 'checked');
		}
		
		$('#nmb_rooms_edit').val(room_number);
		
		$('#nmb_guests_edit').val(guest_number);
		
		$('#start_date_edit').val(start_date);
		
		$('#end_date_edit').val(end_date);
		
		$('#price_edit').val(price);
		
		$('#checkin_edit').val(check_in);
		
		$('#checkout_edit').val(check_out);
		
		if(status == "ACTIVE"){
			$('#active_edit').attr('checked', 'checked');
		}else{
			$('#inactive_edit').attr('checked', 'checked');
		}
		
			
		},
		error:function(){
			console.log('error edit apartment');
		}
	});
	

	$('form#edit_apart_form').submit(function(event) {
		event.preventDefault();
		
		let name = $('#name_apart_edit').val();
		let room_number = $('#nmb_rooms_edit').val();
		let guest_number = $('#nmb_guests_edit').val();
		let start_date = $('#start_date_edit').val();
		let end_date = $('#end_date_edit').val();
		let price = $('#price_edit').val();
		let check_in = $('#checkin_edit').val();
		let check_out = $('#checkout_edit').val();
		let checked_amenities=[];
					
		let city = "null";
		
		if(address){
			
			let split_address = address.split(",");
			let whole_street_one = split_address[0];
			let whole_street = whole_street_one.split(" ");
			let number_one = whole_street[whole_street.length - 1];
			let street_one="";
			for(let i =0; i<whole_street.length -1; i++){
				street_one += whole_street[i] + " ";
			}
			
			let street_number = split_address[0].split(" ");
		
			let zipCode = split_address[1];
			let country = split_address[split_address.length - 1];
			
			let city1 = split_address[split_address.length - 3];
			city = city1.substring(1, city1.length);
			
			loc.latitude = lat_select;
			loc.longitude = lng_select;
			loc.address.country = country;
			loc.address.city = city;
			loc.address.zipCode = zipCode;
			loc.address.street = street_one;
			loc.address.number = number_one;
			
		}
		
		for(let a of all_amenities){
			if($('#' + a).is(":checked")){
				checked_amenities.push(a);
			}
		}
		
		let type = 0;
		
		if($('#room_edit').is(":checked")){
			type = 1;
		}
		
		let status = true;
		if($('#inactive_edit').is(":checked")){
			status = false;
		}
		
		
		if (!name) {
			$('#edit_error_name').text('Unos naziva apartamana je obavezan');
			$('#edit_error_name').attr("hidden",false);
			return;
		}
		
		if(!room_number){ 						
			$('#edit_error_name').attr("hidden",true);
			$('#error_rooms_edit').text('Unos broja soba je obavezan');
			$("#error_rooms_edit").attr("hidden",false);
			return;
		}
		
		if(!guest_number){ 		
			$('#error_rooms_edit').attr("hidden",true);
			$('#error_guests_edit').text('Unos broja gostiju je obavezan');
			$("#error_guests_edit").attr("hidden",false);
			return;
		}
		
		if(!start_date || !end_date){
			$('#error_guests_edit').attr("hidden",true);
			$('#error_dates_edit').text('Unos početnog i krajnjeg datuma je obavezan');
			$('#error_dates_edit').attr("hidden",false);
			return;
		}
		
		var from_start = $("#start_date_edit").val().split("-");
		var from_end = $("#end_date_edit").val().split("-");
		var ds = new Date(from_start[0], from_start[1] - 1, from_start[2]);
		var de = new Date(from_end[0], from_end[1] - 1, from_end[2]);
		var today = new Date();

		if(ds < today){
			$('#error_guests_edit').attr("hidden",true);
			$('#error_dates_edit').text('Početni datum ne smije biti manji od današnjeg');
			$('#error_dates_edit').attr("hidden",false);
			return;
		}
		
		if(de < today){
			$('#error_guests_edit').attr("hidden",true);
			$('#error_dates_edit').text('Krajnji datum ne smije biti manji od današnjeg');
			$('#error_dates_edit').attr("hidden",false);
			return;
		}
		
		if(de < ds){
			$('#error_guests_edit').attr("hidden",true);
			$('#error_dates_edit').text('Krajnji datum ne smije biti manji od početnog');
			$('#error_dates_edit').attr("hidden",false);
			return;
		}
		
		if(!price){
			$('#error_dates_edit').attr("hidden",true);
			$('#error_price_edit').text('Unos cijene je obavezan');
			$('#error_price_edit').attr("hidden",false);
			return;
		}
		
		if(!check_in){
			$('#error_price_edit').attr("hidden",true);
			$('#error_checkin_edit').text('Unos vremena za prijavu je obavezan');
			$('#error_checkin_edit').attr("hidden",false);
			return;
		}
		
		if(!check_out){
			$('#error_checkin_edit').attr("hidden",true);
			$('#error_checkout_edit').text('Unos vremena za odjavu je obavezan');
			$('#error_checkout_edit').attr("hidden",false);
			return;
		}
		
		$('#error_price_edit').attr("hidden",true);
		$('#error_checkout_edit').attr("hidden",true);
		
		var date_s = new Date(start_date);
		var date_e = new Date(end_date);
		
		var rent_days = [];
		
		rent_days.push(date_s.getFullYear() + "-" + ("0" + (date_s.getMonth() + 1)).slice(-2) + "-" + ("0" + date_s.getDate()).slice(-2));

		while(true){		
			date_s.setDate(date_s.getDate() + 1)
			
			if(date_s.getDate() > date_e.getDate()){
				break;
			}
			else{
				rent_days.push(date_s.getFullYear() + "-" + ("0" + (date_s.getMonth() + 1)).slice(-2) + "-" + ("0" + date_s.getDate()).slice(-2));	
			}
		}
		
	
	
		$.ajax({
			type: "POST",
			url: "rest/apartments/edit",
			data: JSON.stringify({ 
				id : id,
				name: name, 
				type: type, 
				numberOfRooms: room_number,
				numberOfGuests: guest_number,
				location: loc,
				rentingDates: rent_days,
				availableDates: available_days,
				hostUsername: host_name,
				comments: comments,
				pictures: [],
				pricePerNight: price,
				checkInTime: check_in,
				checkOutTime : check_out,
				amenities: checked_amenities,
				reservations: reservations,
				active: status,
				deleted: is_deleted}),
			contentType: "application/json",
			success:function(data){
				
				toastr["success"]("Uspješno ste izmijenili apartman.");
			},
			error: function(response){
				toastr["error"]("Došlo je do greške!");
			}
		});	
	});
	
});

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
				if(user.typeOfUser == "HOST"){
					$('#host_navbar').attr("hidden",false);
					$('#admin_navbar').attr("hidden",true);
				}
				else{
					$('#admin_navbar').attr("hidden",false);
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