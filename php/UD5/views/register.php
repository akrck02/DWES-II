<?php require_once "./header.php"; ?>

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
            <td><input type="password" name="password" id="pass1"></td>
        </tr>
        <tr>
            <td><label for="pass2">Password (Repetir)</label></td>
            <td><input type="password" name="password" id="pass2"></td>
        </tr>
        <tr>
            <td><label for="email">Email</label></td>
            <td><input type="text" name='email' id="email"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" id="register" value='Regístrate' disabled></td>
        </tr>
    </table>

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
                
                register.disabled = disabled;
            }

            password2Element.oninput = checkPasswords;
            passwordElement.oninput = checkPasswords;
        }
    </script>

</form>

<?php require_once "./footer.php" ?>