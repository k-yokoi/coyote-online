$(document).ready(function() {
    $.ajax({
        url: "/greeting"
    }).then(function(data) {
        $('.greeting-id').append(data.id);
        $('.greeting-content').append(data.content);
    });
});

function sendName() {
    const hostname = window.location.host;
    $.ajax({
        url: "/create",
        data: { name: $("#name").val()}
    }).then(function(data) {
        console.log(data)
        sessionStorage.setItem('coyote_token', data.users[0].token);
        console.log(sessionStorage.getItem('coyote_token'))
        const joinUrl = hostname + '/game/' + data.roomId;
        $('a').attr('href', joinUrl);
        location.href='/game/' + data.roomId;
    });
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});