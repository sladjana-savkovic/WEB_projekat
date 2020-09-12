$(document).ready(function() {
	
	var all_amenities = [];
	
	$('#start_date').prop("min",new Date().toISOString().split("T")[0]);
	$('#end_date').prop("min",new Date().toISOString().split("T")[0]);

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
				$('div#div_amenities').append(a_div);
				all_amenities.push(a.id);
			}
			
		},
		error:function(){
			console.log('error getting amenities');
		}
	});
	
	//initialization
	$('#name_apart').val("");
	$('#whole_apart').prop("checked",true);
	$('#room').prop("checked",false);
	$('#nmb_rooms').val("");
	$('#nmb_guests').val("");
	$('#start_date').val("");
	$('#end_date').val("");
	$('#price').val("");
	$('#checkin').val("14:00");
	$('#checkout').val("10:00");
	$('#inactive').prop("checked",true);
	$('#active').prop("checked",false);
	for(let a of all_amenities){
		$('#' + a).prop("checked",false);
	}
	$('#files').val('');
	
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
	  
  //choose images
  var chosen_images = [];
  if (window.File && window.FileList && window.FileReader) {
	  
	    $("#add_images").on("change", function(e) {
	    	
	      chosen_images = [];	
	      
	      var files = e.target.files,
	        filesLength = files.length;
	      
	      for (var i = 0; i < filesLength; i++) {
	        var f = files[i];
	        chosen_images.push(f);
	        var fileReader = new FileReader();
	        fileReader.onload = (function(e) {
	          var file = e.target;
	          var column = $("<div  class=\"col-md-3\"><span class=\"pip\">" +
	            "<img class=\"imageThumb\" style='width:100%; height:90%; margin-top:10px;' src=\"" + e.target.result + "\" title=\"" + f.name + "\"/>" 
	            + "</span></div>");
	          $('#div_row').append(column);
	          
	          
	        });
	        fileReader.readAsDataURL(f);
	      }
	    });
	  } else {
	    alert("Your browser doesn't support to File API")
	  }
	  
	  $("input#files").click(function() {
		  $('#files').val('');
		  $('#div_row').empty();
	  });


	$('#add_apart').submit(function(event){
		
		event.preventDefault();
				
		let name = $('#name_apart').val();
		let type = "WHOLE_APARTMENT";
		let rooms = $('#nmb_rooms').val();
		let guests = $('#nmb_guests').val();
		let start_date = $('#start_date').val();
		let end_date = $('#end_date').val();
		let price = $('#price').val();
		let checkin = $('#checkin').val();
		let checkout = $('#checkout').val();
		let active = false;
		let checked_amenities = [];
		
		if($('#room').is(":checked")){
			type="ROOM";
		}		
		
		if (!name) {
			$('#ap_error_name').text('Unos naziva apartmana je obavezan');
			$('#ap_error_name').attr("hidden",false);
			return;
		}
		
		if(!rooms){
			$('#ap_error_name').attr("hidden",true);
			$('#ap_error_rooms').text('Unos broja soba je obavezan');
			$('#ap_error_rooms').attr("hidden",false);
			return;
		}
		if(!guests){
			$('#ap_error_rooms').attr("hidden",true);
			$('#ap_error_guests').text('Unos broja gostiju je obavezan');
			$('#ap_error_guests').attr("hidden",false);
			return;
		}
		
		if(!marker){
			$('#ap_error_guests').attr("hidden",true);
			$('#ap_error_map').text('Odabir lokacije obavezan');
			$('#ap_error_map').attr("hidden",false);
			return;
		}
		
		if(!start_date || !end_date){
			$('#ap_error_map').attr("hidden",true);
			$('#ap_error_dates').text('Unos početnog i krajnjeg datuma je obavezan');
			$('#ap_error_dates').attr("hidden",false);
			return;
		}
		
		var from_start = $("#start_date").val().split("-");
		var from_end = $("#end_date").val().split("-");
		var ds = new Date(from_start[0], from_start[1] - 1, from_start[2]);
		var de = new Date(from_end[0], from_end[1] - 1, from_end[2]);
		
		let split_address = address.split(", ");
		let street_number = split_address[0];
		let zipCode = split_address[1];
		let city = split_address[split_address.length - 3];
		let country = split_address[split_address.length - 1];
		
		let curPos = marker.getLatLng();
		let lat = curPos.lat;
		let lng = curPos.lng;

		if(de < ds){
			$('#ap_error_map').attr("hidden",true);
			$('#ap_error_dates').text('Krajnji datum ne smije biti manji od početnog');
			$('#ap_error_dates').attr("hidden",false);
			return;
		}
		
		if(!price){
			$('#ap_error_dates').attr("hidden",true);
			$('#ap_error_price').text('Unos cijene je obavezan');
			$('#ap_error_price').attr("hidden",false);
			return;
		}
		
		if(!checkin){
			$('#ap_error_price').attr("hidden",true);
			$('#ap_error_checkin').text('Unos vremena za prijavu je obavezan');
			$('#ap_error_checkin').attr("hidden",false);
			return;
		}
		
		if(!checkout){
			$('#ap_error_checkin').attr("hidden",true);
			$('#ap_error_checkout').text('Unos vremena za odjavu je obavezan');
			$('#ap_error_checkout').attr("hidden",false);
			return;
		}
		
		$('#ap_error_price').attr("hidden",true);
		$('#ap_error_checkout').attr("hidden",true);
		
		if($('#active').is(":checked")){
			active = true;
		}
		
		for(let a of all_amenities){
			if($('#' + a).is(":checked")){
				checked_amenities.push(a);
			}
		}
		
		var date_s = new Date(start_date);
		var date_e = new Date(end_date);
		
		var rent_days = [];
		
		rent_days.push(date_s.getFullYear() + "-" + ("0" + (date_s.getMonth() + 1)).slice(-2) + "-" + ("0" + date_s.getDate()).slice(-2));

		while(true){		
			date_s.setDate(date_s.getDate() + 1)
			
			if(date_s > date_e){
				break;
			}
			else{
				rent_days.push(date_s.getFullYear() + "-" + ("0" + (date_s.getMonth() + 1)).slice(-2) + "-" + ("0" + date_s.getDate()).slice(-2));	
			}
		}
		
		$.ajax({
			type:"GET", 
			url: "rest/apartments/new_id",
			contentType: "application/json",
			success:function(id){
				new_id = id;
				//ako je uspjesno dobavljanje id-a, slijedi dodavanje apartmana
				$.ajax({
					type: "POST",
					url: "rest/apartments/add",
					data: JSON.stringify({ 
						id:new_id,
						name:name,
						type:type,
						numberOfRooms:parseInt(rooms),
						numberOfGuests:parseInt(guests),
						location: {
							latitude : lat,
						    longitude : lng,
						    address : {
						      country : country,
						      city : city,
						      zipCode : zipCode,
						      streetAndNumber : street_number,
						    }
						},
						rentingDates: rent_days,
						availableDates: rent_days,
						comments: [],
						pictures: [],
						pricePerNight:price,
						checkInTime:checkin,
						checkOutTime:checkout,
						active:active,
						amenities:checked_amenities,
						reservations: [],
						deleted:false}),
					contentType: "application/json",
					success:function(){
						if(chosen_images.length > 0){
							for(var file of chosen_images){	
								alert(file.name);
					            var extension = file.name.split(".").pop();
					            var type = "";
					            
					            if (extension === "jpg" || extension === "jpeg" ||
					                extension === "JPG" || extension === "JPEG") {
					                type = "image/jpeg";
					            } else if (extension === "png" || extension === "PNG") {
					                type = "image/png";
					            } else {
					                alert("Invalid file type");
					                return;
					            }  
					            
					            var request = new XMLHttpRequest();
					            request.open("POST", "rest/apartments/" + new_id +"/image");
					            request.setRequestHeader("Content-Type", type);
					            request.setRequestHeader("Image-Name", name);
					            request.send(file);
							}
						}
						toastr["success"]("Uspješno ste se dodali apartman");
						window.location.href = 'host_apartments.html';
					},
					error: function(response){
						console.log("greska prilikom dodavanja apartmana");
					}
				});
			},
			error:function(){
				console.log('error getting last id');
			}
		});
		
	});
	
});

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
