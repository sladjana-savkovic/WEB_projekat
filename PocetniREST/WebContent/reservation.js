document.write(`

<div class="modal fade right" id="modalReservationForm" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
  aria-hidden="true" data-backdrop="false">
  <div class="modal-dialog modal-full-height modal-right modal-notify modal-info" role="document">
    <div class="modal-content">
      <!--Header-->
      <div class="modal-header" style="background: green;">
        <p class="heading lead">Raspoloživost
        </p>

        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true" class="white-text">×</span>
        </button>
      </div>

      <!--Body-->
      <div class="modal-body">
        <div class="text-center">
          
          <p>
            Provjerite i dodajte podatke potrebne za rezervaciju smještaja!
          </p>
          <p><strong>Apartmani Sunce</strong></p>
          
        </div>

        <hr>

        
        <p class="text-left">
          <strong>Datum prijave:</strong>
        </p>
        <div >
  		<input type="date" class="form-control" style="width: 180px" value="2020-08-26"/>
  		</div>
  		
  		 <p class="text-left">
          <strong>Datum odjave:</strong>
        </p>
        <div>
        <input type="date" class="form-control" style="width: 180px" value="2020-08-28" min="2020-08-18" max="2020-09-08"/>
        </div>
        
        <p class="text-left">
          <strong>Broj osoba:</strong>
        </p>
        <div>
        <input type="number" placeholder="Unesite broj osoba" class="form-control" value="2" min="1" style="width: 180px"/>
        </div>
        
        <p class="text-left">
          <strong>Ukupna cijena (RSD):</strong>
        </p>
        <div>
        <input type="number" placeholder="Unesite broj osoba" class="form-control" value="4000" min="1" style="width: 180px" readonly/>
        </div>

        <p class="text-left">
          <strong>Vaša poruka domaćinu:</strong>
        </p>
        <!--Basic textarea-->
        <div class="md-form">
  <i class="fas fa-pencil-alt prefix"></i>
  <textarea id="form10" class="md-textarea form-control" rows="3"></textarea>
  <label for="form10">Unesite poruku</label>
</div>

      </div>

      <!--Footer-->
      <div class="modal-footer justify-content-center">
        <button class="btn btn-green" type="submit" id="first_highest" style="margin-top: 30px;">Rezervišite</button> 
      </div>
    </div>
  </div>
</div>	

`);