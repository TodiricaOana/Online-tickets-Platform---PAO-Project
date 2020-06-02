$(document).ready(function() {
    $("#login-form").submit(function( event ) {
        event.preventDefault();

        let firstName = $("#first-name"),
            lastName = $("#last-name"),
            email = $("#email"),
            type = $("#type"),
            password = $("#password");

        $.ajax({
            url: "client?case=" + "1" + "&firstName=" + firstName.val() + "&lastName=" + lastName.val() + "&email=" + email.val() + "&type=" + type.val() + "&password=" + password.val(),
            type: 'POST',
            success: function(response) {
                const responseMessage = JSON.parse(response);

                if(responseMessage.hasOwnProperty('success')) {
                    window.location.replace('login.jsp');
                    alert(responseMessage.success);
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
