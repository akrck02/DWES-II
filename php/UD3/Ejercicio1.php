<?php
session_start();

function validate_name($text)
{
    $text = str_replace(" ","",$text);
    return ctype_alpha($text);
}

if (isset($_GET['delete'])) {
    session_unset();
    session_destroy();
}

$names = isset($_SESSION['names']) ? $_SESSION['names'] : [];
$name = isset($_GET['name']) ? $_GET['name'] : '';
$error = false;

if (isset($_GET['add'])) {
    if (validate_name($name)) {
        $names[$name] = $name;
        $_SESSION['names'] = $names;
    } else $error = "No has escrito el nombre unicamente con letras y espacios";
}

?>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ejercicio 1</title>
</head>
<style>
    .table{
        width: 500px;
    }

    .row {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .row input {
        height: 25px;
    }

    .reverse {
        flex-direction: row-reverse;
        justify-content: flex-start;
    }

    .error {
        color: crimson;
    }
</style>
<body>
    <?php
        if($error) 
            echo "<p class='error'>$error</p>";
    ?>
    <form action="" method="get">
        <div class="table">
            <div class="row">
                <h3>Escriba algun nombre</h3> &nbsp;
                <input type="text" name="name" id="">
            </div>
            <div class="row reverse">
                <input type="submit" name="add" value="Añadir">
                <input type="submit" name="remove" value="Borrar">
            </div>
        </div>
    </form>
    <br>
    <div>
        <?php
        if (count($names) > 0) {
            echo "<p>Datos introducidos: </p>";
            echo "<ul>";
            foreach ($names as $n) {
                echo "<li>$n</li>";
            }
            echo "</ul>";

        } else echo '<p>Todavia no se han introducido nombres</p>';
        ?>
        <a href="<?php echo $_SERVER['PHP_SELF'] ?>?delete=true">Cerrar sesion (Se perderán los datos almacenados)</a>
    </div>
</body>

</html>