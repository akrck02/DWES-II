<?php require_once "./header.php";

    $errorUser = false;
    $errorPassword = false;
    $errorName = false;
    $errorMail = false;
    $existingUserError = false;

    $register = isset($_POST['register']) ? $_POST['register'] : false;
    if($register){

        $username = isset($_POST['user']) ? $_POST['user'] : false;
        $password = isset($_POST['pass1']) ? $_POST['pass1'] : false;
        $name = isset($_POST['name']) ? $_POST['name'] : false;
        $mail = isset($_POST['email']) ? $_POST['email'] : false;

        if($username and $password and $mail and $name){
            if(!userExists($conn,$username)){
                $registered = register($conn,$username,$password,$name,$mail);
                if($registered){
                    $user = getUser($conn, $username);
                    
                    //redirect to login
                    header("location:./login.php?user=".$user['username']);
                }
            } else $existingUserError = "Este usuario ya está registrado";
        } 
        else if(!$username) $errorUser = "Introduzca nombre de usuario";
        else if(!$password) $errorUser = "Introduzca una contraseña";
        else if(!$name) $errorName = "Introduzca un nombre";
        else if(!$mail) $errorName = "Introduzca un correo electrónico";


    }



?>

<h1>Registro</h1>
<p style='padding-left:14px'>Para registrate en <?php echo FORUM_TITLE ?>, rellena el siguiente formulario</p>

<form action="<?php echo $_SERVER['PHP_SELF'] ?>" method="post">
    <table class='register'>
        <tr>
            <td><label for="user">Usuario</label></td>
            <td><input type="text" name="user" id="user"></td>
        </tr>
        <tr>
            <td><label for="name">Nombre completo</label></td>
            <td><input type="text" name="name" id="name"></td>
        </tr>
        <tr>
            <td><label for="pass1">Password</label></td>
            <td><input type="password" name="pass1" id="pass1"></td>
        </tr>
        <tr>
            <td><label for="pass2">Password (Repetir)</label></td>
            <td><input type="password" name="pass2" id="pass2"></td>
        </tr>
        <tr>
            <td><label for="email">Email</label></td>
            <td><input type="text" name='email' id="email"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" id="register" name="register" value='Regístrate' disabled></td>
        </tr>
    </table>
    <p class="error">
        <?php 
            if($existingUserError)
                echo $existingUserError."<br>";

            if($errorUser) 
                echo $errorUser."<br>";

            if($errorPassword)
                echo $errorPassword."<br>";

            if($errorMail)
                echo $errorMail."<br>";
        ?>
    </p>

    <script>
        window.onload = () =>{

            const passwordElement = document.getElementById("pass1");
            const password2Element = document.getElementById("pass2");
            const register = document.getElementById("register");

            const checkPasswords = () =>{
                const password = passwordElement.value;
                const password2 = password2Element.value;

                let disabled = true;
                
                if(password != "" && password2 != "") 
                    disabled = (password != password2);
                 
                if (disabled){
                    password2Element.classList.add("error");
                } else {
                    password2Element.classList.remove("error");
                }


                register.disabled = disabled;
            }

            password2Element.oninput = checkPasswords;
            passwordElement.oninput = checkPasswords;
        }
    </script>

</form>

<?php require_once "./footer.php" ?>