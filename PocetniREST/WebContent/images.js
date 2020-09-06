function myFunction(imgs) {
  // Get the expanded image
  var expandImg = document.getElementById("expandedImg");
  // Get the image text
  var imgText = document.getElementById("imgtext");
  // Use the same src in the expanded image as the image being clicked on from the grid
  expandImg.src = imgs.src;
  // Use the value of the alt attribute of the clickable image as text inside the expanded image
  imgText.innerHTML = imgs.alt;
  // Show the container element (hidden with CSS)
  expandImg.parentElement.style.display = "block";
  expandImg.style.width = "800px";
  expandImg.style.height = "500px";
  
}

$(document).ready(function() {
		
	 $('#files').val('');
	
	  if (window.File && window.FileList && window.FileReader) {
	    $("#files").on("change", function(e) {
	      var files = e.target.files,
	        filesLength = files.length;
	      
	      for (var i = 0; i < filesLength; i++) {
	        var f = files[i]
	        var fileReader = new FileReader();
	        fileReader.onload = (function(e) {
	          var file = e.target;
	          var column = $("<div  class=\"col-md-3\"><span class=\"pip\">" +
	            "<img class=\"imageThumb\" style='width:100%; height:90%; margin-top:10px;' src=\"" + e.target.result + "\" title=\"" + file.name + "\"/>" 
	            + "</span></div>");
	          $('#div_row').append(column);
	          
	        });
	        fileReader.readAsDataURL(f);
	      }
	    });
	  } else {
	    alert("Your browser doesn't support to File API")
	  }
});
