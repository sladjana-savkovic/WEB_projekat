document.write(`
	
		<div class="modal fade" id="modalEditProfile" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
  aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content" >
      <div class="modal-header text-center">
        <h4 class="modal-title w-100 font-weight-bold">Izmjena profila</h4>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="close_btn">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      
      <form id="edit">
	  <div class="container_edit">
	    <label for="name"><b>Ime</b></label>
	    <input type="text" class="register" placeholder="Unesite ime" name="name" id="name" style="margin-bottom:0px;">
	    <p style="color:red; float:right;" id="error_name" hidden="true"></p>
	    
	    <label for="surname" style="margin-top:30px;"><b>Prezime</b></label>
	    <input type="text" placeholder="Unesite prezime" name="surname" id="surname" class="register" style="margin-bottom:0px;">
	    <p style="color:red; float:right;" id="error_surname" hidden="true"></p>
	    
	    <label for="gender" style="margin-top:30px;"><b>Pol</b></label>
		<div class="custom-control custom-radio">
		  <input type="radio" class="custom-control-input register" id="male" name="groupOfDefaultRadios" checked>
		  <label class="custom-control-label" for="male">Muški</label>
		</div>
		<div class="custom-control custom-radio">
		  <input type="radio" class="custom-control-input register" id="female" name="groupOfDefaultRadios" >
		  <label class="custom-control-label" for="female">Ženski</label>
		</div>
		
		<label for="old_psw" style="margin-top:30px;"><b>Stara lozinka</b></label>
	    <input type="password" placeholder="Unesite lozinku" name="old_psw" id="old_psw" class="register" style="margin-bottom:0px;">
	    <p style="color:red; float:right;" id="error_old_psw" hidden="true"></p>
	    
	    <label for="psw" style="margin-top:30px;"><b>Nova lozinka</b></label>
	    <input type="password" placeholder="Unesite lozinku" name="psw" id="psw" class="register" style="margin-bottom:0px;">
	    <p style="color:red; float:right;" id="error_psw" hidden="true"></p>
	    
	    <label for="psw-repeat" style="margin-top:30px;"><b>Potvrda nove lozinke</b></label>
   		<input type="password" placeholder="Ponovo unesite lozinku" name="psw-repeat" id="psw-repeat" style="margin-bottom:0px;" class="register">
		<p style="color:red; float:right;" id="error_psw-repeat" hidden="true"></p>
	  
	  </div>
	  
	  <div class="modal-footer d-flex justify-content-center">
        <input type="submit" class="btn btn-primary mb-2 register" value="Izmijeni" style="width:150px;" id="send">
      </div>
      
	</form>
    </div>
  </div>
</div>
		
`);