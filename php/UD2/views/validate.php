<?php

define("USER_DB", "../data/usuarios.txt");
define(
    "USER_SEARCH_OUTPUT",
    [
        "LOGIN_CORRECT" => 0,
        "LOGIN_INCORRECT" => 1,
        "UNKNOWN_USER" => 2,
    ]
);

/**
 * Get if login is correct
 */
function login($url, $username, $password)
{

    $file = fopen($url, "r");
    $line = "";
    while (!feof($file)) {
        $line = fgets($file);
        $line = explode(";", $line);

        if (count($line) >= 2) {
            $user = $line[0];
            $pass = $line[1];

            if (trim($user) == trim($username)) {
                fclose($file);
                if (trim($pass) == trim($password))
                    return USER_SEARCH_OUTPUT['LOGIN_CORRECT'];
                else
                    return USER_SEARCH_OUTPUT['LOGIN_INCORRECT'];
            }
        }
    }
    fclose($file);
    return USER_SEARCH_OUTPUT['UNKNOWN_USER'];
}


/**
 * Handle calls
 */
if (isset($_POST['login']) and isset($_POST['user']) and isset($_POST['password'])) {

    $user = $_POST['user'];
    $password = $_POST['password'];

    $status = login(USER_DB, $user, $password);
    echo "STATUS: ".$status."";

    switch ($status) {
        case USER_SEARCH_OUTPUT['LOGIN_CORRECT']:
            header("location: ./charla.php?user=".$user);
            break;
        case USER_SEARCH_OUTPUT['LOGIN_INCORRECT']:
            header("location: ../Ejercicio4.php?error=login&user=".$user);
            break;
        case USER_SEARCH_OUTPUT['UNKNOWN_USER']:
            header("location: ./alta.php?user=".$user);
            break;
        default:
            echo "500 : Internal server error";
            break;
    }
} else  echo "NOPE";
