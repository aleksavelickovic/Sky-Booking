$(document).ready(function(){
    console.log('Skript za definisanje akcije je učitana');
     $('.akcijaform').hide();
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
        $.ajax({
            url: '/PrviMavenVebProjekat/letovi/definisiakciju',
            type: 'POST',
            data: {teststring: 'TEST STRING, id leta: ' + letId + ' ', drugiteststring: ' DRUGI TEST STRING'},
            dataType: 'json',
            success: function(response) {
            // response contains the returned data from the server
            console.log('Povratni podaci:', response);
            },
            complete: function(xhr) {
            console.log('Status kod:', xhr.status);
            }
        });
    });

});