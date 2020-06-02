$(document).ready(function() {
    let email = localStorage.getItem('email');

    $("#logout").click(function() {
        localStorage.setItem('logged', '0');
        localStorage.removeItem('email');
        location.replace("login.jsp");
    });

    $("#login-form").submit( function(event){
        event.preventDefault();
        let oldPassword = $("#oldPassword"),
            newPassword = $("#newPassword");

        $.ajax({
            url: "client?case=" + "3" + "&email=" + email + "&oldPassword=" + oldPassword.val() + "&newPassword=" + newPassword.val(),
            type: 'POST',
            success: function(response) {
                const responseMessage = JSON.parse(response);

                if(responseMessage.hasOwnProperty("success")) {
                    localStorage.setItem('logged', '0');
                    window.location.replace('login.jsp');
                    alert(responseMessage.success);
                }
                else {
                    if(!$("#message").length) {
                        let message = $('<h4></h4>').text(responseMessage['failure']);
                        message.css("margin-top", "10px");
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

