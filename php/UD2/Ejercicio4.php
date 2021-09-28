<?php 
    require_once "./styles/components.php";
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
</head>
<style>
    <?php
        include_once "./styles/styles.css";
    ?>
</style>
<body>
<?php echo navbar(3, 4, 5, "./Ejercicio3.php", "./Ejercicio5.php") ?>    

    <form action="./views/validate.php" method="post">
    <?php 
        if(isset($_GET['error']) and isset($_GET['user']))
            echo "<h1> CONTRASEÑA ERRONEA PARA ".strtoupper($_GET['user'])."</h1>";
    
    ?>
        <table>
            <tr>
                <td>Nombre de usuario</td>
                <td>
                    <input type="text" name="user" value=" <?php echo isset($_GET['user'])? $_GET['user'] : "";?>" id="">
                </td>
            </tr>
            <tr>
                <td>Contraseña</td>
                <td><input type="password" name="password" id=""></td>
            </tr>
            <tr>
                <td><input type="submit" name="login" value="login"></td>
            </tr>
        </table>
    </form>

  
</body>
</html>