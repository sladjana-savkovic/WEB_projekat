document.write(`

    <div class="modal fade" id="modalLoginForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	  aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header text-center">
	        <h4 class="modal-title w-100 font-weight-bold">Prijavite se</h4>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="signin_close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <form id="sign_in">
	      <div class="modal-body mx-3">
	        <div class="md-form mb-5">
	          <i class="fas fa-envelope prefix grey-text"></i>
	          <input type="text" id="signin_username" class="form-control validate" style="margin-bottom:0px;">
	          <label data-error="wrong" data-success="right" for="signin_username" style="margin-bottom:0px;">Korisničko ime</label>
	          <p style="color:red; float:right;" id="error_uname" hidden="true"></p>
	        </div>
	
	        <div class="md-form mb-4">
	          <i class="fas fa-lock prefix grey-text"></i>
	          <input type="password" id="signin_password" class="form-control validate" style="margin-bottom:0px;">
	          <label data-error="wrong" data-success="right" for="signin_password" style="margin-bottom:0px;">Lozinka</label>
	          <p style="color:red; float:right;" id="error_pas" hidden="true"></p>
	        </div>
	
	      </div>
	      <div class="modal-footer d-flex justify-content-center">
	        <input type="submit" class="btn btn-primary mb-2 register" value="Prijavi se" style="width:150px;">
	      </div>
	    </form>
	    </div>
	  </div>
</div>


`);