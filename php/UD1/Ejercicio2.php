<!DOCTYPE html>
<html lang="en">
<?php

    $temps = [35.9, 36.7, 37.8, 25.4, 78.9, 40.34];
    $avg = array_sum($temps) / count($temps);

    sort($temps);
    $min_temps = array_slice($temps, 0, 5);

    rsort($temps);
    $max_temps = array_slice($temps, 0, 5);

?>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ejercicio 2</title>
</head>
<style>
    body {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    p {
        background: #f1f1f1;
        padding: 10px;
        max-width: 500px;
        border-radius: 6px;
        margin: 5px 5px;
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
    <h1>TEMPERATURE VISOR v1.0</h1>

    <p>
        <?php 
            echo "Avg temperature (rounded): " . round($avg) . "<br>";
            echo "Avg temperature (truncated): " . floor($avg);
        ?>
    </p>

    <p> 
        <?php
            echo  "Min temperatures: [" . join(' , ', $min_temps) . "]<br>";
            echo  "Max temperatures: [" . join(' , ', $max_temps) . "]"
        ?>
    </p>

    <div id="navbar">
        <a href="./Ejercicio1.php" class="btn">1</a>
        <b>2</b>
        <a href="./Ejercicio3.php" class="btn">3</a>
    </div>
</body>

</html>