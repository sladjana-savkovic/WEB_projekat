$(document).ready(function() {
	

	$('form#search_reservation').submit(function(event) {
		event.preventDefault();
		
		let username = $('#guest_name').val(); 
		
			$.ajax({
				type:"GET", 
				url: "rest/hosts_reservations/search/" + username,
				contentType: "application/json",
				success: function(response){
					
					let reservations = response;
					if(reservations.length != 0){
						alert("Postoji rezervacija koja ima to korisnicko ime.");
						
					}
					if(reservations.length == 0){
						alert("Nema rezervacije sa tim korisnockim imenom.");
					}
					for(let reservation of reservations){
						alert(reservation.guestUsername);
					}
				},
				error:function(){
					console.log('error search reservations');
				}
			});
		
	});
	
});