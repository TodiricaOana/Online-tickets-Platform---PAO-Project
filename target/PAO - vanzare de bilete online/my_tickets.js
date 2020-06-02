$(document).ready(function() {
    let email = localStorage.getItem('email');

    $("#logout").click(function() {
        localStorage.setItem('logged', '0');
        localStorage.removeItem('email');
        location.replace("login.jsp");
    });

    $("#changePassword").click(function() {
        location.replace("change_password.jsp");
    });

    $.ajax({
        url: "tickets?email=" + email,
        type: 'GET',
        success: function(response) {
            const responseMessage = JSON.parse(response);

            let container = $(".archive");

            responseMessage.forEach(function(item) {
                let article = $('<div></div>');
                article.attr("class", "article");

                let h3 = $('<h3></h3>').text(item.eventName);
                h3.css("text-align", "center");

                let paragraphOldPrice = $('<p></p>').text("Old price: $" + item.oldPrice);
                paragraphOldPrice.css("text-align", "center");

                let paragraphPrice = $('<p></p>').text("New price: $" + item.newPrice + " (" + item.type + " discount" + ")");
                paragraphPrice.css("color", "#209935");
                paragraphPrice.css("text-align", "center");

                let h4 = $('<h4></h4>').text(item.location);
                h4.css("text-align", "center");
                h4.css("color", "#4a588d");

                let h5 = $('<h5></h5>').text(item.date);
                h5.css("text-align", "center");
                h5.css("color", "#6276ba");

                let paragraphCategory = $('<p></p>').text(item.category);
                paragraphCategory.css("text-align", "center");
                paragraphCategory.css("font-size", "smaller");

                let button = $('<button></button>').text("Remove ticket");
                button.attr('class', 'buy');
                button.attr('id', item.ticketID);

                article.append(h3);
                article.append(paragraphOldPrice);
                article.append(paragraphPrice);
                article.append(h4);
                article.append(h5);
                article.append(paragraphCategory);
                article.append(button);

                container.append(article);

                button.click(function () {

                    console.log(button.attr('id'));
                    if (confirm('Are you sure you want to delete this ticket？')) {
                        $.ajax({
                            url: "tickets?ticketId=" + button.attr('id'),
                            type: 'DELETE',
                            success: function(response) {
                                const responseMessage = JSON.parse(response);

                                if(responseMessage.hasOwnProperty("success")) {
                                    article.remove();
                                }
                                else {
                                    alert("This ticket couldn't be removed");
                                }
                            }
                        });
                    }

                });
            });
        }
    });
});
