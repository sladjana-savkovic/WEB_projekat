document.write(`

	<!--Modal: modalConfirmDelete-->
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
	        <a href="index.html" class="btn  btn-outline-danger">Da</a>
	        <a type="button" class="btn  btn-danger waves-effect" data-dismiss="modal">Ne</a>
	      </div>
	    </div>
	    <!--/.Content-->
	  </div>
	</div>
<!--Modal: modalConfirmDelete-->

  <div class="modal fade" id="modalEditProfile" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
  aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content" >
      <div class="modal-header text-center">
        <h4 class="modal-title w-100 font-weight-bold">Izmjena profila</h4>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      
      <form>
	  <div class="container">
	    
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
	
	    <label for="psw"><b>Lozinka</b></label>
	    <input type="password" placeholder="Unesite lozinku" name="psw" id="psw" class="register" required>
	    
	    <label for="psw-repeat"><b>Potvrda lozinke</b></label>
   		<input type="password" placeholder="Ponovo unesite lozinku" name="psw-repeat" id="psw-repeat" class="register" required>
	  </div>
	</form>
 
      <div class="modal-footer d-flex justify-content-center">
        <button class="btn btn-primary mb-2 register">Izmijeni</button>
      </div>
 
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
          <a class="dropdown-item" href="host_active-apart.html">Vidi aktivne</a>
          <a class="dropdown-item" href="host_inactive-apart.html">Vidi neakivne</a>
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
          <p style="margin-left:10px; font-size:15px;"><i>Korisnik: <b>marko88</b></i><p>
          <a class="dropdown-item" id="edit_profile" href="" data-toggle="modal" data-target="#modalEditProfile">Izmijeni profil</a>
          <a class="dropdown-item" id="sign_out" href="" data-toggle="modal" data-target="#modalSignOut">Odjavi se</a>
        </div>
      </li>
    </ul>
</nav>	
		
`);