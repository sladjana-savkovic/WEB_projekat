$(document).ready(function() {
	
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
	
});

function addAmenitiesTr(a) {
	let tr = $('<tr style="height:50px;"></tr>');
	let tdName = $('<td>' + a.name + '</td>');
	let tdEdit = $('<td><button class="btn edit_amen" id="' + a.id +'"><i class="fas fa-pencil-alt"></i></button></td>');
	let tdDelete = $('<td><button class="btn del_amen" id="' + a.id +'"><i class="fas fa-times"></i></button></td>');

	tr.append(tdName).append(tdEdit).append(tdDelete);
	
	$('#amenities tbody').append(tr);
}