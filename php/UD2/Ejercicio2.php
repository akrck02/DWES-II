<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tanda 2 - Ejercicio 2</title>
</head>
<?php
    require_once "./lib/images.php";
?>

<body>
    <form action="./lib/eval_imag.php" method="get">
        <p> Cuantas imágenes quieres ver?</p>

        <?php
        $images = getImages(IMAGE_DIR);
        $count = count($images);

        if ($count <= 0)
            echo "<p style='color:red'>No hay imagenes en la capeta.</p>";
        else {
            echo '<select name="images" id="images">';
            for ($i = 1; $i <= $count; $i++)
                echo "<option value='$i'>$i</option>";

            echo '</select>';
            echo '<br><input type="submit" name="next" value="Siguiente">';
        }
        ?>


    </form>

</body>

</html>