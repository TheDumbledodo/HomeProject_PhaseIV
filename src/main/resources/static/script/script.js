// noinspection JSUnresolvedFunction

function countdown(elementName, minutes) {
    let endTime, hours, mins, msLeft, time;

    function updateTimer() {
        msLeft = endTime - (+new Date);
        if (msLeft < 1000) {
            window.location.replace("../error.html");

        } else {
            time = new Date(msLeft);
            hours = time.getUTCHours();
            mins = time.getUTCMinutes();
            setTimeout(updateTimer, time.getUTCMilliseconds() + 500);
        }
    }

    endTime = (+new Date) + 1000 * (60 * minutes) + 500;
    updateTimer();
}

countdown("ten-countdown", 10);

function validateAndGetCaptchaResponse() {
    const response = grecaptcha.getResponse();
    return response.length === 0 ? null : response;
}

$(document).ready(function () {
    $("#button").click(function () {

        let captchaResponse = validateAndGetCaptchaResponse();
        if (captchaResponse) {
            let cardNumber = document.getElementById("card-number").value;
            let expiryYear = document.getElementById("expiry-year").value;
            let expiryMonth = document.getElementById("expiry-month").value;
            let cvc = document.getElementById("card-cvc").value;

            let request = {
                'cardNumber': cardNumber,
                'expiryYear': expiryYear,
                'expiryMonth': expiryMonth,
                'cvc': cvc,
                'captchaResponse': captchaResponse
            };

            $.ajax({
                type: "POST",
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify(request),
                async: false,
                url: "http://localhost:8081/api/v1/request/152/online-payment",
                success: function () {
                    alert("Your payment has been confirmed!");
                },
                error: function () {
                    window.location.replace("../error.html");
                }
            });
        } else {
            $("#captcha-error").html("You haven't completed the captcha!");
        }
    });
});