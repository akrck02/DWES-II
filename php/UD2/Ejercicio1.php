<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tanda 2 - Ejercicio 1</title>
</head>
<?php

define("DESPL_ARRAY", [3, 5, 10]);
define("DIR", "./secure/");
define("ABC",  $abc = ["a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"]);

function cesarCifrate($text, $despl)
{

    $new = "";

    for ($i = 0; $i < strlen($text); $i++) {
        $letter_index =  array_search(strtolower($text[$i]), ABC);
        $new_index = $letter_index + $despl;

        if ($new_index >= count(ABC))
            $new_index = $new_index - count(ABC);

        $new .= ABC[$new_index];
    }

    return strtoupper($new);
}

function substCifrate($text, $filename)
{
    $file = fopen($filename, "r");
    $abc = fread($file, filesize($filename));

    fclose($file);
    $new = "";

    for ($i = 0; $i < strlen($text); $i++) {
        $index = array_search(strtolower($text[$i]), ABC);
        $new .= $abc[$index];
    }

    return $new;
}

/**
 * MAIN
 */

$text_error = false;
$radio_error = false;
$file_error = false;

$cesar = $_GET['cesar'];
$sub = $_GET['sub'];
$despl = $_GET['despl'];
$text = $_GET['text'];
$filename = $_GET['file'];
$result = "";

#Cifrado cesar
if (isset($cesar)) {
    if (isset($despl)) {
        if ($text != "") {
            $result = cesarCifrate($text, $despl);
        } else $text_error = true;
    } else $radio_error = true;
}

#cifrado sustitución
if (isset($sub)) {
    if ($text != "") {
        if (isset($filename)) {
            $result = substCifrate($text, $filename);
        } else $file_error = true;
    } else $text_error = true;
}

?>

<body>
    <form action="./Ejercicio1.php" method="get">
        <table>
            <tr>
                <td>Texto a cifrar</td>
                <td><input type="text" name="text" id="text" value="<?php echo $text?>"></td>
            </tr>
            <tr>
                <td>Desplazamiento</td>
                <td>
                    <?php
                    foreach (DESPL_ARRAY as $d) {
                        echo "<input type='radio' ". ($despl == $d ? "checked":"")." name='despl' value='$d'>$d<br>";
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

                                echo "<option ".(($filename == DIR.$file) ? 'selected': '')." value='".DIR."$file'>$file</option>";
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
                echo "<br><b style='color:red'>Escriba un texto</b>";
            if ($radio_error)
                echo "<br><b style='color:red'>Elija un desplazamiento</b>";
            if ($result != "")
                echo "<br><b> RESULTADO : $result</b>"

            ?>
        </div>
    </form>
</body>

</html>