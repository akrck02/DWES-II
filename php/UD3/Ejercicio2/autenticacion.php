<?php 
    include_once "./libmenu.php";
    session_start();


    # main
    $error = false;

    if(isset($_POST['partner'])){
        
        $user = isset($_POST['user']) ? $_POST['user'] : false;
        $password = isset($_POST['password']) ? $_POST['password'] : false;

        if($user and $password){
            $login = login($user,$password); 
            if($login){
                $_SESSION['user'] = $user;
                header("location:pedido.php");
            }else $error = "Usuario o contraseña incorrectos";
        } else $error = "Introduzca todos los campos";


        if($error) 
            header('location:../Ejercicio2.php?error='.$error); 
    } else 
        if(isset($_POST['guest'])) {
            $_SESSION['user'] = "invitado";
            header("location:pedido.php");
        }



?>