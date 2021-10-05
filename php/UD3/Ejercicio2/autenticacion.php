<?php 
    include_once "./libmenu.php";
    session_start();

    /**
     * Return if is logged
     */
    function login($user , $password){
       $users = USERS;    
       $user = trim($user);
       $password = trim($password);

       $user_exists = in_array($user, array_keys($users));
       $success = ($user_exists and $users[$user][1] == $password); 
       return $success;
    }

    # main
    $error = false;
    if(isset($_POST['partner'])){
        
        $user = isset($_POST['user']) ? $_POST['user'] : false;
        $password = isset($_POST['password']) ? $_POST['password'] : false;

        if($user and $password){
            $login = login($user,$password);
       
            if($login){
                session_unset();
                $_SESSION['user'] = $user;
                $_SESSION['discount'] = get_discount($user);
                setcookie('last_user', $user);
                header("location:pedido.php");
            }else $error = "Usuario o contraseña incorrectos";
        } else $error = "Introduzca todos los campos";


        if($error) 
            header('location:../Ejercicio2.php?error='.$error); 
    } else 
        if(isset($_POST['guest'])) {
            session_unset();
            $_SESSION['user'] = "invitado";
            $_SESSION['discount'] = 0;
            header("location:pedido.php");
        }
