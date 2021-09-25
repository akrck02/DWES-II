<?php
    require_once "../lib/data.php";

    define("ORIGIN", "../Ejercicio2.php");
    define("DIRIMG", "../images/");

    $next = isset($_GET['next']) ? $_GET['next'] : null;
    $images = isset($_GET['images']) ? $_GET['images'] : null;
    $completed = $next == "Valorar";

    if (!$completed) {
        if (!isset($next) or !isset($images))
            header("location:" . ORIGIN);

        $photos = getRandomImages(DIRIMG, $images);
    }else {
        $photos = isset($_GET['photo']) ? $_GET['photo'] : null;
        if($photos != null)
            $added = addLineToFile("../data/evaluation.txt", $_SERVER['REMOTE_ADDR'].":", $photos);
    }
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
        include_once "../styles/components.php";
        include_once "../styles/Ej2.css";
    ?>
</style>

<body>
    <?php 
    if (!$completed)
        echo "<h1>". count($photos) ." Imagenes aleatorias</h1>";
    ?>
    <section>
        <form action="<?php echo $_SERVER['PHP_SELF'] ?>">
         <table>
            <?php
            if ($completed) {
                if($photos == null){
                    echo "<tr><td><h1>Sentimos que no te haya gustado ninguna :(</h1></td></tr>";
                } else {
                    echo "<tr><td><h1>Gracias por valorar</h1></td></tr>";

                    if(!$added)
                        echo("<tr><td>No se pudo añadir tu valoración a la base de datos</td></tr>");
                }
                echo "<tr><td><a href='../Ejercicio2.php'>Volver a la página principal</a></td></tr>";
            } else {
                foreach ($photos as $photo) {
                    echo "<tr>";
                    echo "<td><img src='".DIRIMG."$photo'></td>";
                    echo "<td><label style='margin: 50px' ><input type='checkbox' name='photo[]' value='$photo'>&nbsp;Me gusta</label></td>";
                    echo "</tr>";
                }
                echo '<tr><td colspan="2"><input type="submit" name="next" value="Valorar"></td><tr>';
            }
            ?>
            </table>


            <input type="hidden" name="images" value="<?php echo $_GET['images'] ?>">
        </form>
    </section>
</body>
</html>