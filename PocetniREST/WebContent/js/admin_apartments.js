$(document).ready(function() {
	
	//checkLoggedUser();
	
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
					 				+ '<tr><td><a href="apartment-review.html">'
				 					+ '<img class="img_apartment" src="https://apartmanialexandria.rs/wp-content/uploads/2015/03/Apartman-1-02.jpg" alt="thumbnail" class="img-thumbnail"/>'
				 					+ '</a></td>'
				 					+ '<td><table style="height: 220px; margin-left: 40px; width: 350px;">'
				 					+ '<tr><td colspan="2"><a href="apartment-review.html"><h5>Apartman ' + a.name + '</h5></a></td></tr>'
				 					+ '<tr><th style="width: 35%;">Tip apartmana:</th><td>' + type + '</td></tr>'
				 					+ '<tr><th>Status:</th>' + td_status + '</tr>'
				 					+ '<tr><th>Adresa:</th><td>' + address + '</td></tr>'
				 					+ '<tr><th>Cijena po noći:</th><td> ' + a.pricePerNight + ' rsd</td></tr>'
				 					+ '<tr><th>Domaćin:</th><td>' + host + '</td></tr>'
				 					+ '<tr><th>Sadržaj:</th><td>' + amenities.substring(0, amenities.length-2) + '</td></tr>'
				 					+ '</table></td>'
				 					+ '<td style="vertical-align: bottom;"><button class="btn btn-orange edit_delete" type="submit" id="' + a.id +'" onclick="editApartment(this.id)">Izmijeni</button>'
				 					+ '<button class="btn btn-red edit_delete" type="submit"  data-toggle="modal" data-target="#modalConfirmDelete" id="' + a.id +'" onclick="deleteApartment(this.id)">Obriši</button>'
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

function deleteApartment(id){
	
	$('a#yes_delete').click(function(event){
		
		event.preventDefault();
		
		$.ajax({
			type: "DELETE",
			url: "rest/apartments/delete",
			contentType: "application/json",
			data:id,
			success: function(){
				$('a#no_delete').click();
				toastr["success"]("Uspješno ste dodali obrisali apartman");
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
		url: "rest/verification/admin",
		error:  function(jqXHR, textStatus, errorThrown)  {
			$('#admin_amenities').hide(function() {
				alert(jqXHR.responseText);
				window.history.back();
			});
		}
	});
};

