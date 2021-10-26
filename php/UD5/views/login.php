<?php require_once "./header.php";


    //checkings here
    $loginButton = isset($_POST['login']) ? $_POST['login'] : false;

    $errorUser = false;
    $errorPassword = false;
    $login = false;
    $username = isset($_REQUEST['user']) ? $_REQUEST['user'] : false;


    if($loginButton){
        $password =  isset($_POST['pass1']) ? $_POST['pass1']: false;
    
        if(!$username) $errorUser = "Introduzca un usuario";
        else if(!$password) $errorPassword = "Introduzca una contraseña";
        else {
            $login = login($conn,$username,$password);
            if($login){
                $_SESSION['user'] = $username;
                $_SESSION['userId'] = getUser($conn,$username)['id'];
                header("location:".$_SERVER['host'].$_POST['last']);
            }
        }
    }

?>

<h1>Login</h1>
<form action="<?php echo $_SERVER['PHP_SELF'] ?>" method="post">
    <table class='login'>
        <tr>
            <td><label for="user">Usuario</label></td>
            <td><input type="text" name="user" id="user"  value="<?php echo $username ? $username : ''?>"></td>
            <?php if($errorUser) echo "<td class='error'>".$errorUser."</td>";?>
        </tr>
        <tr>
            <td><label for="pass1">Password</label></td>
            <td><input type="password" name="pass1" id="pass1"></td>
            <?php if($errorPassword) echo "<td class='error'>".$errorPassword."</td>";?>
        </tr>
        <tr>
            <td><input type="hidden" name="last" value="<?php echo $_SESSION['last'] ?>"></td>
            <td><input type="submit" id="login" name="login" value='Login'></td>
        </tr>
    </table>
</form>
<p>¿No tienes una cuenta? <b><a href="register.php">Regístrate.</a></b></p> 
<?php require_once "./footer.php" ?> 