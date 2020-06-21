if (sessionStorage.getItem('coyote_token') == null) {
    $('#staticBackdrop').modal('show')
}

$(function() {
    var POLLLING_INVERVAL_TIME_IN_MILLIS = 1000; //10s
    (function polling() {
        getCountUp();
        window.setTimeout(polling, POLLLING_INVERVAL_TIME_IN_MILLIS);
    }());

    function getCountUp() {
        $.ajax({
            type: "GET ",
            url: "/gameinfo ",
            content: "application/json ",
            data: {
                roomId: roomId,
                token: sessionStorage.getItem('coyote_token')
            },
            dataType: "json ",
        }).done(function(data) {
            console.log(data)
            if (data.playing) {
                $('#is_playing').text("Playing ")
                $("#start ").prop("disabled ", true);
                $("#exit ").prop("disabled ", true);
                $("#raise_value ").prop("disabled ", false);
                $("#raise ").prop("disabled ", false);
                $("#coyote ").prop("disabled ", false);
            } else {
                $('#is_playing').text("Waiting ")
                $("#start ").prop("disabled ", false);
                $("#exit ").prop("disabled ", false);
                $("#raise_value ").prop("disabled ", true);
                $("#raise ").prop("disabled ", true);
                $("#coyote ").prop("disabled ", true);
            }
            $('#players').children().remove();
            $.each(data.players, function(i, val) {
                $("#players ").append('<tr><th scope="row ">' + h(val.name) + '</th><td>' + val.card + '</td></tr>')
            })
            var declared_value = parseInt(data.value, 10)
            $("#declared_value ").text(data.value); //html要素変更する
            $("#message ").text(data.message); //html要素変更する
        }).fail(function(jqXHR, textStatus) {
            $("dd ").text("error occured "); //html要素変更する
        });
    }
});

function h(str) {
    return String(str).replace(/&/g, "&amp; ")
        .replace(/"/g, "&quot;").replace(/</g, "&lt;").replace(/>/g, "&gt;")
}

function join() {
    $("#join").prop("disabled", true);
    $.ajax({
        url: "/join",
        data: {
            name: $("#name").val(),
            roomId: roomId
        }
    }).then(function(data) {
        if (data) {
            console.log(data);
            sessionStorage.setItem('coyote_token', data) $('#staticBackdrop').modal('hide');
        } else {
            console.log("You can't join.");
            $("#join").prop("disabled", false);
        }
    });
}

function exit() {
    sessionStorage.removeItem('coyote_token')
    location.href = '/'
}

function start() {
    $.ajax({
        url: "/start",
        data: {
            token: sessionStorage.getItem('coyote_token'),
            roomId: roomId
        }
    }).then(function(data) {
        console.log(data);
    });
}

function raise() {
    $.ajax({
        url: "/raise",
        data: {
            token: sessionStorage.getItem('coyote_token'),
            value: $("#raise_value").val(),
            roomId: roomId
        }
    }).then(function(data) {
        console.log(data);
        $("#raise_value").val(1);
        $("#formControlRange").val(1);
    });
    $('#exampleModal').modal('hide');
}

function coyote() {
    $.ajax({
        url: "/coyote",
        data: {
            token: sessionStorage.getItem('coyote_token'),
            roomId: roomId
        }
    }).then(function(data) {
        console.log(data);
    });
}
$(function() {
    $("form").on('submit', function(e) {
        e.preventDefault();
    });
    $("#join").click(function() {
        join();
    });
    $("#start").click(function() {
        start();
    });
    $("#exit").click(function() {
        exit();
    });
    $("#close").click(function() {
        exit();
    });
    $("#raise_execute").click(function() {
        raise();
    });
    $("#coyote").click(function() {
        coyote();
    });
});
var elem = document.getElementById('formControlRange');
var target = document.getElementById('raise_value');
var rangeValue = function(elem, target) {
    return function(evt) {
        target.value = elem.value;
    }
}
elem.addEventListener('input', rangeValue(elem, target));
target.addEventListener('input', rangeValue(target, elem));