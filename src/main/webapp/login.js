$(document).ready(function() {
    $("#login-form").submit(function( event ) {
        event.preventDefault();

        let email = $("#email"),
            password = $("#password");

        $.ajax({
            url: "client?case=" + "2" + "&email=" + email.val() + "&password=" + password.val(),
            type: 'POST',
            success: function(response) {
                const responseMessage = JSON.parse(response);

                if(responseMessage.hasOwnProperty('success')) {
                    localStorage.setItem('logged', '1');
                    localStorage.setItem('email', email.val());
                    window.location.replace('buy_tickets.jsp');
                }
                else {
                    if(!$("#message").length) {
                        let message = $('<h4></h4>').text(responseMessage['failure']);
                        message.css("margin-top", "10px")
                        message.attr("id", "message");

                        $("#mess").append(message);
                    }
                    else
                        $("#message").text(responseMessage['failure']);
                }
            }
        });
    });
});