<?php

function getUrlsFromFile($file)
{
    $urls = [];

    $handle = fopen($file, "r");
    while (!feof($handle)) {
        $map = explode(',', fgets($handle));
        if (count($map) == 2)
            $urls[] = $map;
    }
    fclose($handle);
    return $urls;
}

$file = 'urls.txt';
$urls = getUrlsFromFile($file);
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ejercicio 7</title>
</head>
<style>
    body {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    a {
        text-decoration: none;
    }

    li {
        list-style-type: none;
        padding : 7px;
        transition: .25s;
    }

    li:hover {
        transform: translateX(10px);
    }

    ul {
        width: 400px;
    }

    #navbar {
        position: fixed;
        display: flex;
        justify-content: space-between;
        align-items: center;
        box-sizing: border-box;

        width: 100%;
        height: 60px;

        bottom: 10px;
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
        color: #202020;
        font-weight: bold;
    }
</style>

<body>
    <h1>WEB INDEX</h1>

    <ul>
    <?php
        #Draw link list
        foreach ($urls as $url) {
            echo '<li><a href="' . $url[0] . '">' . $url[1] . '</a></li>';
        }
    ?>
</ul>

    <div id="navbar">
        <a href="./Ejercicio6.php" class="btn">6</a>
        <b>7</b>
        <a href="./index.php" class="btn">..</a>
    </div>


</body>

</html>