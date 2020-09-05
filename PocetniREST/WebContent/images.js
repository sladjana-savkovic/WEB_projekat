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
	  if (window.File && window.FileList && window.FileReader) {
	    $("#files").on("change", function(e) {
	      var files = e.target.files,
	        filesLength = files.length;
	      for (var i = 0; i < filesLength; i++) {
	        var f = files[i]
	        var fileReader = new FileReader();
	        fileReader.onload = (function(e) {
	          var file = e.target;
	          $("<span class=\"pip\">" +
	            "<img class=\"imageThumb\" style='width:20%; margin-top:10px;' src=\"" + e.target.result + "\" title=\"" + file.name + "\"/>" +
	            "<br/><span  style='color:blue;' class=\"remove\"><a>Ukloni sliku</a></span>" +
	            "</span>").insertAfter("#files");
	          $(".remove").click(function(){
	            $(this).parent(".pip").remove();
	          });
	          
	          // Old code here
	          /*$("<img></img>", {
	            class: "imageThumb",
	            src: e.target.result,
	            title: file.name + " | Click to remove"
	          }).insertAfter("#files").click(function(){$(this).remove();});*/
	          
	        });
	        fileReader.readAsDataURL(f);
	      }
	    });
	  } else {
	    alert("Your browser doesn't support to File API")
	  }
});
