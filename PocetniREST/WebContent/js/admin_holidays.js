$(document).ready(function() {
	
	checkLoggedUser();
	
	$('#name_holiday').val('');
	
	//getting holidays
	$.ajax({
		type:"GET", 
		url: "rest/holidays",
		contentType: "application/json",
		success:function(holiday){
			for (let h of holiday.holidays) {
				addHolidayRow(h);
			}
		},
		error:function(){
			console.log('error getting holidays');
		}
	});
	
	$('form#add_holiday').submit(function(event) {
		event.preventDefault();
		
		var name = $('#name_holiday').val();
		
		if(!name){
		    $('#name_holiday').css("border","1px solid red");
		    toastr["error"]("Unesite datum!");
			return;
		}
		
		
		$.ajax({
			type:"GET", 
			url: "rest/holidays/contains/" + name,
			success:function(result){

				if(result == '0'){
					$.ajax({
						type:"POST", 
						url: "rest/holidays/add",
						data: name,
						success:function(){
							toastr["success"]("Uspješno ste dodali novi datum");
							setTimeout(function(){
						           location.reload(); 
						      }, 500); 
						},
						error:function(){
							toastr["error"]("Došlo je do greške prilikom dodavanja datuma!");
						}
					});
				}else{
					toastr["error"]("Uneseni datum već postoji!");
					return;
				}
			},
			error:function(){
				console.log('error checking date');
			}
		});
	});
	
});

function addHolidayRow(h){
	let tr = $('<tr></tr>');
	let tdDate = $('<td>' + h + '</td>');
	let tdDelete = $('<td><button class="btn del_amen" id="' + h + '" onclick="deleteHoliday(this.id)"><i class="fas fa-times" style="color: red;"></i></button></td>');
	
	tr.append(tdDate).append(tdDelete);
	$('#holidays tbody').append(tr);
};

function deleteHoliday(h){
	
	$.ajax({
		url: "rest/holidays/delete",
		type:"DELETE",
		data: h,
		success: function(){
			setTimeout(function(){
		           location.reload(); 
		      }, 50); 
		},
		error:function(){
			console.log('error');
		}
	})
};

function checkLoggedUser(){
	
	$.ajax({
		type: "GET",
		url: "rest/verification/admin",
		error:  function(jqXHR, textStatus, errorThrown)  {
			$('#holidays').hide(function() {
				alert(jqXHR.responseText);
				window.history.back();
			});
		}
	})
};