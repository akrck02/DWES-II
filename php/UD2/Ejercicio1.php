<?php
require_once "./styles/components.php";
require_once "./lib/data.php";

define("DESPL_ARRAY", [3, 5, 10]);
define("DIR", "./secure/");

/**
 * MAIN
 */

$text_error = false;
$radio_error = false;
$file_error = false;

$cesar = isset($_GET['cesar']) ? $_GET['cesar'] : null;
$sub = isset($_GET['sub']) ? $_GET['sub'] : null;
$despl = isset($_GET['despl']) ? $_GET['despl'] : null;
$text = isset($_GET['text']) ? $_GET['text'] : null;
$filename = isset($_GET['file']) ? $_GET['file'] : null;
$result = "";

#Cifrado cesar
if (isset($cesar)) {
    if (isset($despl)) {
        if ($text != "") {
            $result = cesarCifrate(urldecode($text), $despl);
        } else $text_error = true;
    } else $radio_error = true;
}

#cifrado sustitución
if (isset($sub)) {
    if ($text != "") {
        if (isset($filename)) {
            $result = substCifrate(urldecode($text), $filename);
        } else $file_error = true;
    } else $text_error = true;
}

?>


<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tanda 2 - Ejercicio 1</title>
</head>
<style>
    <?php include_once "./styles/Ej1.css"; ?>
</style>

<body>
    <?php echo navbar("..", 1, 2, "./index.php", "./Ejercicio2.php"); ?>
    <h1>Cifrador M4x1m0 v0.34</h1>
    <form action="./Ejercicio1.php" method="get">
        <table>
            <tr>
                <td>Texto a cifrar</td>
                <td><input type="text" name="text" id="text" value="<?php echo $text ?>"></td>
            </tr>
            <tr>
                <td>Desplazamiento</td>

                <td>
                    <?php
                    foreach (DESPL_ARRAY as $d) {
                        echo "<label><input type='radio' " . ($despl == $d ? "checked" : "") . " name='despl' value='$d'>$d</label><br>";
                    }
                    ?>
                </td>
                <td>
                    <input type="submit" name="cesar" value="Cifrado cesar">
                </td>
            </tr>
            <tr>
                <td>
                    Fichero de clave
                </td>
                <td>
                    <select name="file" id="file">
                        <?php
                        $files = scandir(DIR);
                        foreach ($files as $file) {
                            if ($file != "." && $file != "..") {
                                echo "<option " . (($filename == DIR . $file) ? 'selected' : '') . " value='" . DIR . "$file'>$file</option>";
                            }
                        }
                        ?>
                    </select>
                </td>
                <td>
                    <input type="submit" name="sub" value="Cifrado sustitución">
                </td>
            </tr>
        </table>
        <div style="font-size: 1.2em">
            <?php
            if ($text_error)
                echo "<br><b id='error'>Escriba un texto</b>";
            if ($radio_error)
                echo "<br><b id='error'>Elija un desplazamiento</b>";
            if ($result != "")
                echo "<br><b id='result'> RESULTADO : $result</b>"
            ?>
        </div>
    </form>
</body>

</html>