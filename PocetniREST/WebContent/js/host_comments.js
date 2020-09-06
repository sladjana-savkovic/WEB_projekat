$(document).ready(function() {
	
	//checkLoggedUser();
	
	$.ajax({
		type:"GET", 
		url: "rest/comments",
		contentType: "application/json",
		success:function(comments){
			for (let c of comments) {
				addCommentTable(c);
			}
		},
		error:function(){
			console.log('error getting comments');
		}
	});
	
});

function addCommentTable(c) {
	
	$.ajax({
		type:"GET", 
		url: "rest/apartments/" + c.apartmentId,
		contentType: "application/json",
		success:function(apartment){
			
			let type = "Soba";
			if(apartment.type == "WHOLE_APARTMENT"){
				type="Apartman";
			}
			
			let comment = $('<div class="border_comment">' 
					   + '<table class="table_comment">'
					   + '<tr><th style="width:350px;">Naziv apartmana:</th><td>' + apartment.name +'</td></tr>'
					   + '<tr><th>Tip apartmana:</th><td>' + type +'</td></tr>'
					   + '<tr><th>Gost koji je ostavio komentar:</th><td>' + c.guestUsername + '</td></tr>'
					   + '<tr><th>Tekst komentara:</th><td>' + c.description + '</td></tr>'
					   + '<tr><th>Ocjena:</th><td>' + c.rating + '</td></tr>'
					   + '<tr><td style="width:250px;"><span></span></td><td style="width:250px;"><span></span></td>'
					   + '<td><button class="btn yes" id="' + c.id +'" onclick="approveComment(this.id)"><i class="fas fa-check"></i></button>'
					   + '<button class="btn no" id="' + c.id + '" onclick="disapproveComment(this.id)"><i class="fas fa-times"></i></button></td>'
					   + '</tr></table></div>');
		
		$('div#div_comments').append(comment);
		},
		error:function(){
			console.log('error getting apartment');
		}
	});
		
}

function approveComment(id){
	$.ajax({
		type:"PUT", 
		url: "rest/comments/approve",
		data:id,
		contentType: "application/json",
		success:function(){
			toastr["success"]("Uspješno ste dodali odobrili komentar");
			setTimeout(function(){
				location.reload(); }, 500); 
		},
		error:function(){
			toastr["error"]("Došlo je do greške. Pokušajte ponovo.");
		}
	});
}

function disapproveComment(id){
	$.ajax({
		type:"PUT", 
		url: "rest/comments/disapprove",
		data:id,
		contentType: "application/json",
		success:function(){
			toastr["success"]("Uspješno ste dodali odbili komentar");
			setTimeout(function(){
				location.reload(); }, 500); 
		},
		error:function(){
			toastr["error"]("Došlo je do greške. Pokušajte ponovo.");
		}
	});
}

function checkLoggedUser(){
		
	$.ajax({
		type: "GET",
		url: "rest/verification/host",
		error:  function(jqXHR, textStatus, errorThrown)  {
			$('body#comments').hide(function() {
				alert(jqXHR.responseText);
				window.history.back();
			});
		}
	});
};
