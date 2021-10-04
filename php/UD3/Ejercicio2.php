<?php



?>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ejercicio 2</title>
</head>
<style>
    .column {
        display: flex;
        flex-direction: column;
    }

    form {
        display: inherit;
    }

    input[type=submit] {
        
        max-width: 105px;

    }
</style>

<body>
    <h2>Acceso con registro: </h2>
    <p>Si eres socio introduce tu usuario y password</p>
    <form action="./Ejercicio1/autenticacion.php" method="post">
        <div class="column">
            <label for="">
                Usuario:
                <input type="text">
            </label>
            <label for="">
                Contraseña:
                <input type="password">
            </label>
            <input type="submit" name="partner" value="Acceso socio">
        </div>
    </form>
    <hr>

    <h3>Acceso sin registro:</h3>
    <p>Si no dispones de usuario y password, entra aquí.</p>
    <form action="./Ejercicio1/autenticacion.php" method="post">
        <input type="submit" name="guest" value="Acceso invitado">
    </form>

</body>

</html>