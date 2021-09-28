<?php
    require_once "./styles/components.php"; 
    require_once "./lib/data.php"; 
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tanda 2 - Ejercicio 2</title>
</head>
<style>
    <?php 
        include_once "./styles/Ej2.css";
        include_once "./styles/styles.css";
    ?>
</style>

<body>
    <?php echo navbar(1,2,3,"./Ejercicio1.php","./Ejercicio3.php"); ?>
    <h1>Imagenes.com</h1>
    <form action="./views/evaluate.php" method="get">
        <label> Cuantas imágenes quieres ver?
            <?php
            $images = getImages(IMAGE_DIR);
            $count = count($images);

            if ($count <= 0)
                echo "<p style='color:red'>No hay imagenes en la capeta.</p>";
            else {
                echo '<select name="images" id="images">';
                for ($i = 0; $i <= $count; $i++)
                    echo "<option value='".($i + 1)."'>".($i + 1)."</option>";
                echo '</select>';
            }

            ?>
        </label>
        <?php echo '<br><input type="submit" name="next" value="Siguiente">';?>
    </form>
</body>

</html>