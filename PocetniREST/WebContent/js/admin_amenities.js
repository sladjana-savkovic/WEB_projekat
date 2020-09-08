$(document).ready(function() {
	
	//checkLoggedUser();
	
	$.ajax({
		type:"GET", 
		url: "rest/amenities",
		contentType: "application/json",
		success:function(amenities){
			for (let a of amenities) {
				addAmenitiesTr(a);
			}
		},
		error:function(){
			console.log('error getting amenities');
		}
	});
	
	$('form#add_amen').submit(function(event) {
		event.preventDefault();
		
		let name = $('#name_amen').val();
		
		if(!name){
		    $('#name_amen').css("border","1px solid red");
		    toastr["error"]("Unesite naziv sadržaja!");
			return;
		}
		
		var new_id;
		
		$.ajax({
			type:"GET", 
			url: "rest/amenities/new_id",
			contentType: "application/json",
			success:function(id){
				new_id = id;
				//ako je uspjesno dobavljanje id-a, slijedi dodavanje
				$.ajax({
					type:"POST", 
					url: "rest/amenities/add",
					data: JSON.stringify({ 
						id: new_id, 
						name: name, 
						deleted: false}),
					contentType: "application/json",
					success:function(){
						toastr["success"]("Uspješno ste dodali novi sadržaj");
						setTimeout(function(){
					           location.reload(); 
					      }, 50); 
					},
					error:function(){
						toastr["error"]("Došlo je do greške prilikom dodavanja sadržaja!");
					}
				});
			},
			error:function(){
				console.log('error getting last id');
			}
		});
	});
	
});

function addAmenitiesTr(a) {
	let tr = $('<tr style="height:50px;" id="' + a.id + '"></tr>');
	let tdName = $('<td>' + a.name + '</td>');
	let tdEdit = $('<td><button class="btn edit_amen" id="' + a.id +'"  onclick="editAmenities(this.id)"><i class="fas fa-pencil-alt"></i></button></td>');
	let tdDelete = $('<td><button class="btn del_amen" id="' + a.id +'"  onclick="deleteAmenities(this.id)"><i class="fas fa-times"></i></button></td>');

	tr.append(tdName).append(tdEdit).append(tdDelete);
	
	$('#amenities tbody').append(tr);
}

function deleteAmenities(id){

	$.ajax({
		type:"DELETE",
		url: "rest/amenities/delete",
		contentType: "application/json",
		data: id,
		success: function(){
			setTimeout(function(){
		           location.reload(); 
		      }, 50); 
		},
		error:function(){
			toastr["error"]("Došlo je do greške prilikom brisanja sadržaja!");
		}
	})
}

function editAmenities(id){
	
	$('tr#' + id).css("background","lightgray");

	$.ajax({
		type:"GET",
		url: "rest/amenities/" + id,
		contentType: "application/json",
		success: function(name){
			$('#edit_name').val(name);
			$('#edit_name').attr("disabled",false);
		},
		error:function(){
			toastr["error"]("Došlo je do greške prilikom učitavanja sadržaja!");
		}
	});
	
	$('form#edit_amen').submit(function(event) {
		event.preventDefault();
		
		let name = $('#edit_name').val();
		
		if(!name){
		    $('#edit_name').css("border","1px solid red");
		    toastr["error"]("Unesite naziv sadržaja!");
			return;
		}
		
		$.ajax({
			type:"PUT",
			url: "rest/amenities/edit",
			data: JSON.stringify({ 
				id: id, 
				name: name, 
				deleted: false}),
			contentType: "application/json",
			success: function(){
				toastr["success"]("Uspješno ste izmijenili sadržaj");
				setTimeout(function(){
					location.reload(); }, 1000); 
			},
			error:function(){
				toastr["error"]("Došlo je do greške prilikom učitavanja sadržaja!");
			}
		});
		
	});
}

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
}