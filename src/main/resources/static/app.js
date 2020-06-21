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
        data: { name: $("#name").val() },
        error: function(e) {
            console.log(e);
            $("#message1").text("Name Error")
            $("#message1").addClass("alert")
            $("#message1").addClass("alert-danger")
        }
    }).then(function(data) {
        if (data) {
            console.log(data)
            sessionStorage.setItem('coyote_token', data.users[0].token);
            sessionStorage.setItem('coyote_name', $("#name").val());
            console.log(sessionStorage.getItem('coyote_token'))
            const joinUrl = hostname + '/game/' + data.roomId;
            $('a').attr('href', joinUrl);
            location.href = '/game/' + data.roomId;
        } else {
            console.log("You can't join.");
            $("#message1").text("Name Error")
            $("#message1").addClass("alert")
            $("#message1").addClass("alert-danger")
        }
    });
}

function join() {
    const hostname = window.location.host;
    $.ajax({
        url: "/join",
        data: {
            name: $("#enter_name").val(),
            roomId: $("#room_id").val()
        },
        error: function(e) {
            console.log(e);
            $("#message2").text("Room ID or Name Error")
            $("#message2").addClass("alert")
            $("#message2").addClass("alert-danger")
        }
    }).then(function(data) {
        if (data) {
            console.log(data);
            sessionStorage.setItem('coyote_token', data)
            sessionStorage.setItem('coyote_name', $("#enter_name").val());
            console.log(sessionStorage.getItem('coyote_token'))
            const joinUrl = hostname + '/game/' + $("#room_id").val();
            $('a').attr('href', joinUrl);
            location.href = '/game/' + $("#room_id").val();
        } else {
            console.log("You can't join.");
            $("#message2").text("Room ID or Name Error")
            $("#message2").addClass("alert")
            $("#message2").addClass("alert-danger")
        }
    });
}

$('#exampleModal1').on('hidden.bs.modal', function(e) {
    $("#message1").text("")
    $("#message1").removeClass("alert")
    $("#message1").removeClass("alert-danger")
})

$('#exampleModal2').on('hidden.bs.modal', function(e) {
    $("#message2").text("")
    $("#message2").removeClass("alert")
    $("#message2").removeClass("alert-danger")
})

$(function() {
    $("form").on('submit', function(e) {
        e.preventDefault();
    });
    $("#connect").click(function() { connect(); });
    $("#disconnect").click(function() { disconnect(); });
    $("#send").click(function() { sendName(); });
    $("#join").click(function() { join(); });
});