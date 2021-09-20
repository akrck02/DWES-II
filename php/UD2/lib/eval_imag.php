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
define("ORIGIN", "../Ejercicio2.php");

$next = $_GET['next'];
$images = $_GET['images'];

if (!isset($next) or !isset($images))
    header("location:" . ORIGIN);

$photos = getRandomImages("../images/", $images);
?>
<style>
    body {
        display: flex;

        flex-direction: column;
        justify-content: center;
        align-items: center;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    section {
        width: 100%;
        display: flex;

        flex-direction: column;
        justify-content: center;
        align-items: center;
        flex-wrap: wrap;
    }

    img {
        transition: .25s;
        border-radius: 6px;
        margin: 10px;
        width: 200px;
        max-height: 200px;
        object-fit: cover;
        filter: grayscale(100);
    }

    div {
        display: flex;
        justify-content: center;
        align-items: center;
        max-width: 500px; 
        padding: 15px;
    }

    label {
        cursor: pointer;
    }

    div:hover img {
        box-shadow: 0px 2px 4px rgba(0, 0, 0, .15);
        transform: scale(1.1);
        filter: grayscale(0);
    }
</style>

<body>
    <h1> <?php echo count($photos); ?> Imagenes aleatorias</h1>
    <section>
        <?php
        foreach ($photos as $photo) {
            echo "<div>";
            echo "<img src='$photo'>";
            echo "<label style='margin: 50px' ><input type='checkbox' name='photo[]' value='$photo'>&nbsp;Me gusta</label>";
            echo "</div>";
        }
        ?>
    </section>
</body>

</html>