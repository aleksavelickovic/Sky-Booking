$(document).ready(function(){
    console.log('Skript za listu zelja je ucitana');
    $('.listazeljalabel').hide();
    $('.listazeljagreskalabel').hide();



    const $listazeljabtn = $('.listazeljabtn');

    $listazeljabtn.on('click', function() {
        console.log('Dugme za listu zelja je kliknuto!!!');
        const letId = $(this).data('let-id');
        // const datumVazenjaAkcije = $('#datumvazenjaakcije' + letId).val()
        $.ajax({
            url: '/PrviMavenVebProjekat/letovi/dodajnalistuzelja',
            type: 'POST',
            data: {letId: letId},
            dataType: 'json',
            success: function(response) {
            // response contains the returned data from the server
            console.log('Povratni podaci:', response);

            if (response == "neuspeh"){
                console.log("Neuspesno dodavanje na listu zelja!")
                $('#listazeljagreskalabel' + letId).show(300);
                setTimeout(function() {
                    $('#listazeljagreskalabel' + letId).hide(300);
                }, 1650);
            }
            else if (response == "uspeh"){
                console.log("Uspeh!!!");
                $('#listazeljalabel' + letId).show(300);
                setTimeout(function() {
                    $('#listazeljalabel' + letId).hide(300);
                }, 1650);
            }
            else{
                console.log("Neka druga greska!!!");
            }








            // var responseObj = JSON.parse(response);
            // console.log(responseObj)

            // if (response.oznaka == "") { 
            //     console.log('Nije moguće definisati akciju za ovaj let');
            //     $('#letjevecnaakciji').show(200);
            //     setTimeout(function() {
            //         $('#letjevecnaakciji').hide(200);
            //     }, 3000);
			// 	// console.log("TEST")
            // }
            // else if (response.oznaka == "P"){
            //     console.log('Polje je prazno!')
            //     $('#popustjeprazan').show(200);
            //     setTimeout(function() {
            //         $('#popustjeprazan').hide(200);
            //     }, 3000);
            // }
            // else{
            //     console.log('Akcija uspešno definisana!');

            //     //  // Osvježi stranicu ili izvrši neku drugu akciju
            //     // location.reload();

            //     $('#naAkciji' + letId).show(300);
            //     // $('#oznaka' + let.id).text(response.oznaka);
            //     // $('#polaziste' + let.id).text(response.polaziste.oznaka);
            //     // $('#odrediste' + let.id).text(response.odrediste.oznaka);
            //     // $('#avion' + let.id).text(response.avion.naziv);
            //     // $('#terminPolaska' + let.id).text(response.terminPolaska);
            //     // $('#trajanje' + let.id).text(response.trajanjeLeta);
            //     $('#cena' + letId).text(response.cena);

            //     $('#akcijauspeh').show(200);
            //     setTimeout(function() {
            //         $('#akcijauspeh').hide(200);
            //     }, 3000);
               
            // }
        },
        complete: function(xhr) {
            console.log('Status kod dodavanje u listu zelja:', xhr.status);
            }
        });
    });

});