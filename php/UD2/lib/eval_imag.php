<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tanda 2 - Ejercicio 2</title>
</head>
<?php 
    require_once "../lib/images.php";
    define("ORIGIN","../Ejercicio2.php");

    $next = $_GET['next'];
    $images = $_GET['images'];
    
    if(!isset($next) or !isset($images))
        header("location:".ORIGIN);

    $photos = getRandomImages("../images/",$images);

    print_r($photos);
?>
<body>
    <table>


    </table>
</body>
</html>