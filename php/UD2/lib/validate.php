<?php

    define("USERS_FILE","./data/users.txt");

    function get_users() {
        $users = [];
        $file = fopen(USERS_FILE, "r");
        while (!feof($file)) {
            $line = fgets($file);
            $user = explode(";", $line);
            $users[$user[0]] = $user[1];
        }
        fclose($file);
        return $users;
    }

    function validate_user($user,$password) {

        $users = get_users();
        if(isset($users[$user])){
            if($users[$user] == $password){    
               
            } else {
                //redirect to login
                $error = "contraseña incorrecta para $user";
                header("Location: ../Ejercicio4.php?user=$user&error=$error");
            }
        } else {
            //redirect to register
            header("Location: ../views/register.php?user=$user");
        }
        
    
    }
?>