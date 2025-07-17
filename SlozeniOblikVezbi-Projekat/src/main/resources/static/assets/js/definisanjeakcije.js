$(document).ready(function(){
    console.log('Definisanje akcije script loaded');
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
        console.log('Definisanje popusta button clicked');
        const letId = $(this).data('let-id');
        $.post('/PrviMavenVebProjekat/letovi/definisiakciju', {teststring: 'TEST STRING, id leta: ' +  letId + ' ', drugiteststring: ' DRUGI TEST STRING'}, 'json');
    });

});