document.write(`

    <div class="modal fade" id="modalLoginForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	  aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content" >
	      <div class="modal-header text-center">
	        <h4 class="modal-title w-100 font-weight-bold">Prijava</h4>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      
	      <form>
			  <div class="input-container">
			    <i class="fa fa-user icon"></i>
			    <input class="input-field sign" type="text" placeholder="Korisničko ime" name="username">
			  </div>
			  
			  <div class="input-container" style="margin-top:25px;">
			    <i class="fa fa-key icon"></i>
			    <input class="input-field sign" type="password" placeholder="Lozinka" name="password">
			  </div>	
			  
		</form>
	 
	      <div class="modal-footer d-flex justify-content-center">
	        <button class="btn btn-primary mb-2 sign">Prijavi se</button>
	      </div>
	    </div>
	  </div>
</div>

`);