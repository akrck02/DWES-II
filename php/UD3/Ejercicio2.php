<?php 
    session_start(); 
    $error = isset($_REQUEST['error'])  ? $_REQUEST['error'] : false;

?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ejercicio 2</title>
</head>
<style><?php include_once "./styles/Ejercicio2.css";?></style>
<body>
    <h2>Acceso con registro: </h2>
    <p>Si eres socio introduce tu usuario y password</p>
    <form action="./Ejercicio2/autenticacion.php" method="post">
        <div class="column">
            <label for="">
                Usuario:
                <input type="text" name="user" value="<?php echo isset($_COOKIE['last_user']) ? $_COOKIE['last_user'] : "" ?>">
            </label>
            <label for="">
                Contraseña:
                <input type="password" name="password">
            </label><br>
            <input type="submit" name="partner" value="Acceso socio">
        </div>
    </form>
    <?php
        echo "<br><hr><b>Sesión activa: </b>";
        print_r($_SESSION);  

        echo "<br><b>Cookies activas: </b>";
        print_r($_COOKIE); 
    ?>
    <?php 
        if($error)
            echo "<p class='error'>$error</p>";
    ?>
    <hr>

    <h3>Acceso sin registro:</h3>
    <p>Si no dispones de usuario y password, entra aquí.</p>
    <form action="./Ejercicio2/autenticacion.php" method="post">
        <input type="submit" name="guest" value="Acceso invitado">
    </form>
</body> 
</html>