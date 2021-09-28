<?php
define("USER_DB", "../data/usuarios.txt");

function user_exists($url, $username)
{
    echo file_exists($url) ? ' file exists' : 'file does not exist', "\n";
    echo is_readable($url) ? ' is readable' : ' is NOT readable', "\n";
    echo is_writable($url) ? ' is writable' : ' is NOT writable', "\n";

    $file = fopen($url, "r");
    $line = "";

    while (!feof($file)) {

        $line = fgets($file);
        $line = explode(";", $line);

        if (count($line) >= 2) {
            $user = $line[0];
            if (trim($user) == trim($username)) {
                fclose($file);
                return true;
            }
        }
    }
    fclose($file);
    return false;
}

function register_user($url, $username, $password)
{
    echo substr(decoct(fileperms($url)), 2);
    $file = fopen($url, "a");

    if ($file != false) {
        fwrite($file, $username . ";" . $password . "\n");
        fclose($file);
        return true;
    } else {
        return false;
    }
    return true;
}



$password_error = false;
$exist_error = false;
$registered = false;

$user = isset($_POST['user']) ? $_POST['user'] : "";
$password = isset($_POST['password']) ? $_POST['password'] : "";

if ($user !== "") {
    if ($password !== "") {
        $exists = user_exists(USER_DB, $user);

        if ($exists == true) {
            $exist_error = true;
        } else {
            $registered = register_user(USER_DB, trim($user), $password);
        }
    } else $password_error = true;
}


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
    include_once "../styles/styles.css";
    ?>
</style>

<body>
    <h2>REGÍSTRATE</h2>
    <form action="<?php echo $_SERVER['PHP_SELF']; ?>" method="post">
        <table>
            <tr>
                <td>Nombre de usuario</td>
                <td>
                    <input type="text" name="user" value="<?php echo isset($_REQUEST['user']) ? $_REQUEST['user'] : ""; ?>" id="">
                </td>
                <td rowspan="2">
                    <img src="../images/register.png" alt="">
                </td>
            </tr>
            <tr>
                <td>Contraseña</td>
                <td><input type="password" name="password" id=""></td>
            </tr>
            <tr>
                <td><input type="submit" name="register" value="register"></td>
            </tr>

        </table>
        <?php
        if ($password_error)
            echo "<p style='color:crimson'>Escriba una contraseña</p>";

        if ($exist_error)
            echo "<p style='color:crimson'>El usuario ya $user existe</p>";

        if ($registered) {
            echo "<p>El usuario $user se ha dado de alta con éxito.</p>";
            echo "<a href='../Ejercicio4.php?user=$user'>ENTRAR AL CHAT</a>";
        }
        ?>

    </form>

</body>

</html>