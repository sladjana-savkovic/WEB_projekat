function signOut() {
		
	$.ajax({
		type: "GET",
		url: "rest/signout",
		success: function() {
			location.href = "index.html";
		}
	});
	
}