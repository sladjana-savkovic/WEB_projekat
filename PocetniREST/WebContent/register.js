document.write(`

<div class="modal fade" id="modalRegisterForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
  aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content" >
      <div class="modal-header text-center">
        <h4 class="modal-title w-100 font-weight-bold">Registracija</h4>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      
      <form>
	  <div class="container">
	    <p>Popunite sledeću formu da biste napravili nalog.</p>
	    <hr>
	    
	    <label for="name"><b>Ime</b></label>
	    <input type="text" placeholder="Unesite ime" name="name" id="name" class="register" required>
	    
	    <label for="surname"><b>Prezime</b></label>
	    <input type="text" placeholder="Unesite prezime" name="surname" id="surname" class="register" required>
	    
	    <label for="gender"><b>Pol</b></label>
		<div class="custom-control custom-radio">
		  <input type="radio" class="custom-control-input register" id="male" name="groupOfDefaultRadios" checked>
		  <label class="custom-control-label" for="male">Muški</label>
		</div>
		<div class="custom-control custom-radio">
		  <input type="radio" class="custom-control-input register" id="female" name="groupOfDefaultRadios" >
		  <label class="custom-control-label" for="female">Ženski</label>
		</div>
		<hr>
			
	    <label for="username"><b>Korisničko ime</b></label>
	    <input type="text" placeholder="Unesite korisničko ime" name="email" id="email" class="register" required>
	
	    <label for="psw"><b>Lozinka</b></label>
	    <input type="password" placeholder="Unesite lozinku" name="psw" id="psw" class="register" required>
	    
	    <label for="psw-repeat"><b>Potvrda lozinke</b></label>
   		<input type="password" placeholder="Ponovo unesite lozinku" name="psw-repeat" id="psw-repeat" class="register" required>
	  </div>
	</form>
 
      <div class="modal-footer d-flex justify-content-center">
        <button class="btn btn-primary mb-2 register">Registruj se</button>
      </div>
      
      <div class="container signin">
	    <p>Već imate nalog? <a href="" data-toggle="modal" data-target="#modalLoginForm">Prijavi se</a>.</p>
	  </div>
    </div>
  </div>
</div>


`);
