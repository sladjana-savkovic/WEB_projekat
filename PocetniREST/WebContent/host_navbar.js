document.write(`

  <!--Modal: modalSignOut-->
	<div class="modal fade" id="modalSignOut" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
	  aria-hidden="true">
	  <div class="modal-dialog modal-sm modal-notify modal-danger" role="document">
	    <!--Content-->
	    <div class="modal-content text-center">
	      <!--Header-->
	      <div class="modal-header d-flex justify-content-center">
	        <p class="heading">Da li ste sigurni?</p>
	      </div>
	
	      <!--Body-->
	      <div class="modal-body">
	        <i class="fas fa-times fa-4x animated rotateIn"></i>
	      </div>
	
	      <!--Footer-->
	      <div class="modal-footer flex-center">
	        <a href="javascript:signOut();" class="btn  btn-outline-danger" id="sign_out_yes">Da</a>
	        <a type="button" class="btn  btn-danger waves-effect" data-dismiss="modal" id="sign_out_no">Ne</a>
	      </div>
	    </div>
	    <!--/.Content-->
	  </div>
	</div>
  <!--Modal: modalSignOut-->

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
	    
	    <label for="psw" style="margin-top:30px;"><b>Lozinka</b></label>
	    <input type="password" placeholder="Unesite lozinku" name="psw" id="psw" class="register" style="margin-bottom:0px;">
	    <p style="color:red; float:right;" id="error_psw" hidden="true"></p>
	    
	    <label for="psw-repeat" style="margin-top:30px;"><b>Potvrda lozinke</b></label>
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
		
  <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
  <h4 class="navbar-brand" id="title" style="margin-right: 50px;">DSTOURS.COM</h4>

  <!-- Links -->
  <ul class="navbar-nav">
    <li class="nav-item">
      <a class="nav-link" href="host_guests-review.html">Pregled gostiju</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="host_reservations.html">Pregled rezervacija</a>
    </li>
	<li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink-333" data-toggle="dropdown"
          aria-haspopup="true" aria-expanded="false">Moji apartmani
        </a>
        <div class="dropdown-menu dropdown-default" aria-labelledby="navbarDropdownMenuLink-333">
          <a class="dropdown-item" href="host_active-apart.html">Pregled apartmana</a>
          <a class="dropdown-item" href="host_add-apart.html">Dodaj novi</a>
        </div>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="host_comments.html">Komentari</a>
    </li>
  </ul>
  <ul class="navbar-nav ml-auto nav-flex-icons" style="margin-right: 50px;">
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink-333" data-toggle="dropdown"
          aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-user"></i>
        </a>
        <div class="dropdown-menu dropdown-menu-right dropdown-default"
          aria-labelledby="navbarDropdownMenuLink-333">
          <p style="margin-left:10px; font-size:15px;"><i>Korisnik: <b id="user_navbar"></b></i><p>
          <a class="dropdown-item" id="edit_profile" href="" data-toggle="modal" data-target="#modalEditProfile">Izmijeni profil</a>
          <a class="dropdown-item" id="sign_out" href="" data-toggle="modal" data-target="#modalSignOut">Odjavi se</a>
        </div>
      </li>
    </ul>
</nav>	
		
`);