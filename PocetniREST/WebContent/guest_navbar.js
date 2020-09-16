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
	        <a href="javascript:signOut();" class="btn  btn-outline-danger">Da</a>
	        <a type="button" class="btn  btn-danger waves-effect" data-dismiss="modal">Ne</a>
	      </div>
	    </div>
	    <!--/.Content-->
	  </div>
	</div>
<!--Modal: modalSignOut-->
			
  <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
  <h4 class="navbar-brand" id="title" style="margin-right: 50px;">DSTOURS.COM</h4>

  <!-- Links -->
  <ul class="navbar-nav">
    <li class="nav-item">
      <a class="nav-link" href="guest_new-reservation.html">Rezervišite smještaj</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="guest_my-reservations.html">Moje rezervacije</a>
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
          <a class="dropdown-item" id="edit_profile" href="" data-toggle="modal" data-target="#modalEditProfile">Izmijeni profil</a>
          <a class="dropdown-item" id="sign_out" href="" data-toggle="modal" data-target="#modalSignOut">Odjavi se</a>
        </div>
      </li>
    </ul>
</nav>	
		
`);