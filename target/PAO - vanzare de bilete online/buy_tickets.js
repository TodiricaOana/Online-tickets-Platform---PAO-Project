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
        url: "events",
        type: 'GET',
        success: function(response) {
            const responseMessage = JSON.parse(response);

            let container = $(".archive");

            responseMessage.forEach(function(item) {
                let article = $('<div></div>');
                article.attr("class", "article");

                let h3 = $('<h3></h3>').text(item.eventName);
                h3.css("text-align", "center");

                let paragraphPrice = $('<p></p>').text( "$" + item.price);
                paragraphPrice.attr('class', 'price');
                paragraphPrice.css("text-align", "center");

                let h4 = $('<h4></h4>').text(item.location);
                h4.css("text-align", "center");
                h4.css("color", "#4a588d");

                let h5 = $('<h5></h5>').text(item.date);
                h5.css("text-align", "center");
                h5.css("color", "#6276ba");

                let paragraphSeats = $('<p></p>').text( item.numberAvailableSeats + " available seats");
                paragraphSeats.css("text-align", "center");
                paragraphSeats.css("color", "#7d91db");

                let paragraphCategory = $('<p></p>').text(item.category);
                paragraphCategory.css("text-align", "center");
                paragraphCategory.css("font-size", "smaller");

                let button = $('<button></button>').text("Buy");
                button.attr('class', 'buy');
                button.attr('id', item.eventDetailsID);

                let i = $('<i></i>');
                i.attr('class', 'fa fa-shopping-cart');
                i.css('color','white');

                button.append(i);

                article.append(h3);
                article.append(paragraphPrice);
                article.append(h4);
                article.append(h5);
                article.append(paragraphSeats);
                article.append(paragraphCategory);
                article.append(button);

                container.append(article);

                button.click(function () {

                    let p = $("#mymessage");

                    if(!$("#mymessage").length) {
                        p = $('<p></p>');
                    }

                    p.css('display', 'block');
                    p.css('text-align', 'center');
                    p.css('color', 'black');

                    $.ajax({
                        url: "events?email=" + email + "&eventDetailsID=" + button.attr('id'),
                        type: 'POST',
                        success: function(response) {
                            const responseMessage = JSON.parse(response);

                            if(responseMessage.hasOwnProperty("success")) {
                                p.text(responseMessage.success);
                            }
                            else {
                                p.text(responseMessage.failure);
                            }
                        }
                    });

                    article.append(p);

                    setTimeout(function() {
                        p.remove();
                    }, 2000);
                });
            });
        }
    });
});

