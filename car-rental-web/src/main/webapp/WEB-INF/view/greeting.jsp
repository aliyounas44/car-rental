<!DOCTYPE html>
<html>
    <head>
        <script>
            function loadDoc() {
                var xhttp =  new XMLHttpRequest();
                xhttp.onreadystatechange = function() {
                    if (xhttp.readyState == 4 && xhttp.status == 200) {
                        document.getElementById("demo").innerHTML = xhttp.responseText;
                    }
                };
                xhttp.open("POST", "http://localhost:8080/car-rental-web/serverInfo");
                xhttp.setRequestHeader("Content-Type", "application/json");
                xhttp.send(JSON.stringify({handShake:"John Rambo"}));
            }
        </script>
    </head>

    <body>
        <button type="button" onclick="loadDoc()">Test Connection</button>
        <div id="demo"></div>
    </body>
</html>
