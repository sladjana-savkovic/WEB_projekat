$(document).ready(function() {
	
	checkLoggedUser();
	
	$.ajax({
		type:"GET", 
		url: "rest/hosts_guests",
		contentType: "application/json",
		success:function(hosts_guests){
			for (let g of hosts_guests) {
				adduserTr(g);
			}
		},
		error:function(){
			console.log('error getting guests');
		}
	});
	

	$('form#search_guests').submit(function(event) {
		event.preventDefault();
		
		let username = $('#name_for_search').val();
		let gender_type = $('#gender_for_search option:selected').val();
		
		if(!username){
			username = " ";
		}
		
			$.ajax({
				type:"GET", 
				url: "rest/hosts_guests/search/" + username + "/" + gender_type,
				contentType: "application/json",
				success:function(hosts_guests){
					$('#guests_of_host tbody').empty();
					for (let g of hosts_guests) {
						adduserTr(g);
					}
				},
				error:function(){
					console.log('error search guests');
				}
			});
		
	});
	
});

function adduserTr(user) {
	let tr = $('<tr style="height:50px;"></tr>');
	let tdName = $('<td>' + user.name + '</td>');
	let tdSurname = $('<td>' + user.surname + '</td>');
	let tdGender = $('<td>' + 'Muški' + '</td>');
	
	if(user.gender == "FEMALE")
		tdGender = $('<td>' + 'Ženski' + '</td>');
    
	let tdUsername = $('<td>' + user.username + '</td>');
	
	tr.append(tdName).append(tdSurname).append(tdGender).append(tdUsername);
	
	$('#guests_of_host tbody').append(tr);
}

function checkLoggedUser(){
	
	$.ajax({
		type: "GET",
		url: "rest/verification/host",
		error:  function(jqXHR, textStatus, errorThrown)  {
			$('body#guests-review').hide(function() {
				alert(jqXHR.responseText);
				window.history.back();
			});
		}
	});
};
