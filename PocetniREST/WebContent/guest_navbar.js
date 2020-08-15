document.write(`
		
			
  <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
  <h4 class="navbar-brand" id="title" style="margin-right: 50px; color: white; margin-bottom: 40px;">DSTOURS.COM</h4>

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
          <p style="margin-left:10px; font-size:15px;"><i>Korisnik: <b>marko88</b></i><p>
          <a class="dropdown-item" id="edit_profile" href="" data-toggle="modal" data-target="#modalEditProfile">Izmijeni profil</a>
          <a class="dropdown-item" id="sign_out" href="" data-toggle="modal" data-target="#modalSignOut">Odjavi se</a>
        </div>
      </li>
    </ul>
</nav>	
		
`);