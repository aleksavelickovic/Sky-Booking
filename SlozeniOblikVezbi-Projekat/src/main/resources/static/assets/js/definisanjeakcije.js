$(document).ready(function(){
    console.log('Skript za definisanje akcije je učitana');
     $('.akcijaform').hide();
     $('#letjevecnaakciji').hide();
     $('#popustjeprazan').hide();
     $('#akcijauspeh').hide();
     $('.naakcijilabela').hide();
     $('#popustveciodsto').hide();

    var formaPrikazana = false;

    function prikaziFormu() {
        if (formaPrikazana) {
            $('.akcijaform').hide(300);
            formaPrikazana = false;
            return;
        }
        if (formaPrikazana == false) {
            $('.akcijaform').show(300);
            formaPrikazana = true;
        }
        console.log(formaPrikazana);
    }

    $('.definisiakcijubtn').on('click', prikaziFormu);

    const $definisipopustbtn = $('.definisipopustbtn');

    $definisipopustbtn.on('click', function() {
        console.log('Dugme definisipopustbtn je kliknuto');
        const letId = $(this).data('let-id');
        const datumVazenjaAkcije = $('#datumvazenjaakcije' + letId).val()
        $.ajax({
            url: '/PrviMavenVebProjekat/letovi/definisiakciju',
            type: 'POST',
            data: {procenatpopusta: $('#popustzalet' + letId).val(), letId: letId, datumVazenjaAkcije: datumVazenjaAkcije},
            dataType: 'json',
            success: function(response) {
            // response contains the returned data from the server
            console.log('Povratni podaci:', response);
            // var responseObj = JSON.parse(response);
            // console.log(responseObj)

            if (response.oznaka == "") { 
                console.log('Nije moguće definisati akciju za ovaj let');
                $('#letjevecnaakciji').show(200);
                setTimeout(function() {
                    $('#letjevecnaakciji').hide(200);
                }, 3000);
				// console.log("TEST")
            }
            else if (response.oznaka == "P"){
                console.log('Polje je prazno!')
                $('#popustjeprazan').show(200);
                setTimeout(function() {
                    $('#popustjeprazan').hide(200);
                }, 3000);
            }
            else if (response == "V"){
                console.log("Popust je VECI od 99%!")
                $('#popustveciodsto').show(200);
                setTimeout(function(){
                    $('#popustveciodsto').hide(200);
                }, 3000)
            }
            else{
                console.log('Akcija uspešno definisana!');

                //  // Osvježi stranicu ili izvrši neku drugu akciju
                // location.reload();

                $('#naAkciji' + letId).show(300);
                // $('#oznaka' + let.id).text(response.oznaka);
                // $('#polaziste' + let.id).text(response.polaziste.oznaka);
                // $('#odrediste' + let.id).text(response.odrediste.oznaka);
                // $('#avion' + let.id).text(response.avion.naziv);
                // $('#terminPolaska' + let.id).text(response.terminPolaska);
                // $('#trajanje' + let.id).text(response.trajanjeLeta);
                $('#cena' + letId).text(response.cena);

                $('#akcijauspeh').show(200);
                setTimeout(function() {
                    $('#akcijauspeh').hide(200);
                }, 3000);
               
            }
        },
        complete: function(xhr) {
            console.log('Status kod:', xhr.status);
            }
        });
    });

});