<?php

function showImages($routes)
{

    $routes = array_unique($routes);

    for ($i = 1; $i <=  count($routes); $i++) {
        $route = $routes[$i - 1];
        echo "<a href='${route}'>
                <img style=' width: 150px; height: 150px; object-fit: cover' src='${route}'/>
              </a>";

        if ($i % 3 == 0) echo "<br>";
    }
}

$base_url = "./images/";
$photos = [];

for ($i = 1; $i <= 7; $i++) {
    $photos[] = $base_url . $i . ".jpg";
}

?>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ejercicio 4</title>
</head>
<style>
    body {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    img {
        transition: .25s;
        border-radius: 3px;
        margin: 10px;
        filter: grayscale(100);
    }

    img:hover {
        transform: scale(1.1);
        box-shadow: 0px 2px 4px rgba(0,0,0,.15);
        filter: grayscale(0);
    }

    #navbar {
        position: fixed;
        display: flex;
        justify-content: space-between;
        align-items: center;
        box-sizing: border-box;

        width: 100%;
        height: 60px;
        
        bottom : 10px;
        left: 0;
        padding: 10px;
    }

    #navbar a {
        display: flex;
        justify-content: center;
        align-items: center;
        background: #f1f1f1;

        border-radius: 100%;
        width: 30px;
        height: 30px;
        padding: 10px;
        text-decoration: none;
        color : #202020;
        font-weight: bold;
    }
</style>

<body>
    <h1>Album de fotos:</h1>
    <div> <?php showImages($photos); ?> </div>
    
    <div id="navbar">
        <a href="./Ejercicio3.php" class="btn">3</a>
        <a href="./Ejercicio5.php" class="btn">5</a>
    </div>
</body>
</html>