<?php require_once "../db/config.php";
    session_start();
    session_unset();
    header("location:".BASE_ROUTE);
?>