<?php

define("DB","./data/conversion.txt");
define("FACTOR", get_conversion());

function get_conversion(){
    $file = fopen(DB, "r");
    $factor =  fscanf($file, "%f");
    fclose($file);

    return isset($factor[0]) ? $factor[0] : false;
}

function euro_to_dolar($euro){

    return FACTOR === false ? "No se ha encontrado el factor de conversión" : $euro * FACTOR;
}

function dolar_to_euro($dolar){
    return FACTOR === false ? "No se ha encontrado el factor de conversión" : $dolar / FACTOR;
}

$empty_error = false;
$no_numeric_error = false;
$result = false;

$convertir = isset($_POST['convertir']) ? $_POST['convertir'] : null;
$cantidad = isset($_POST['cantidad']) ? $_POST['cantidad'] : false;
$conversion = isset($_POST['conversion']) ? $_POST['conversion'] : "eur-dol";

if ($convertir !== false) {
    if ($cantidad !== false) {

        if ($cantidad !== "") {
            if (is_numeric($cantidad)) {
                if ($conversion === "dol-eur") {
                        $result = dolar_to_euro($cantidad);
                }
                if($conversion === "eur-dol"){
                        $result = euro_to_dolar($cantidad);
                }
            } else $no_numeric_error = true;
        } else $empty_error = true;
    }
}
?>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Euros - Dólares</title>
</head>

<body>

    <form action="<?php echo $_SERVER['PHP_SELF'] ?>" method="post">
        <table>
            <tr>
                <td>Cantidad:</td>
                <td>
                    <input type="text" name="cantidad">
                    <?php  
                        if ($empty_error) {
                            echo '<span style="color:red">No puede estar vacío</span>';
                        }
                        if ($no_numeric_error) {
                            echo '<span style="color:red">Debe ser un número</span>';
                        }
                    ?>
                </td>
                <?php 
                    if ($result !== false) 
                        echo '<td>' . $result . '</td>';
  
                ?>
                <td>
                    <label><input type="radio" name="conversion" <?php if($conversion == "eur-dol") echo "checked" ?>  value="eur-dol" id=""> Euros a dólares</label>
                    <label><input type="radio" name="conversion" <?php if($conversion == "dol-eur") echo "checked" ?>  value="dol-eur" id=""> Dólares a euros</label>
                </td>
            </tr>
            <tr>
                <td><input type="submit" name="convertir" value="Convertir"></td>
            </tr>
        </table>
    </form>

</body>

</html>