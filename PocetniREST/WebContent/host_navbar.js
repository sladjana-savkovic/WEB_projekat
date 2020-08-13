document.write(`
		
	<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
  <h4 class="navbar-brand" id="title" style="margin-right: 50px;">DSTOURS.COM</h4>

  <!-- Links -->
  <ul class="navbar-nav">
    <li class="nav-item">
      <a class="nav-link" href="#">Pregled gostiju</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="#">Pregled rezervacija</a>
    </li>
	<li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink-333" data-toggle="dropdown"
          aria-haspopup="true" aria-expanded="false">Moji apartmani
        </a>
        <div class="dropdown-menu dropdown-default" aria-labelledby="navbarDropdownMenuLink-333">
          <a class="dropdown-item" href="#">Vidi aktivne</a>
          <a class="dropdown-item" href="#">Vidi neakivne</a>
          <a class="dropdown-item" href="#">Dodaj novi</a>
        </div>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="#">Komentari</a>
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
          <a class="dropdown-item" href="#">Izmijeni profil</a>
          <a class="dropdown-item" href="#">Odjavi se</a>
        </div>
      </li>
    </ul>
</nav>	
		
`);