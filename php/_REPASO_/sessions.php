<?php 

    //start session
    session_start();

    // erase one session variable
    unset($_SESSION['properties']);

    //delete session all variables
    session_unset();

    //destroy session
    session_destroy();

    //Cookie
    setcookie($name,$value,$expire);
?>