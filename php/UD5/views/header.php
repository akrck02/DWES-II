
<?php
    require_once "../db/model.php";
    session_start();
    $conn = connectDB();
?>
<html>
    <head>
        <title><?php echo FORUM_TITLE; ?></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../styles/master.css" type="text/css" />
    </head>
    <body>
        <div id="header"> <h1>SUBASTAS CIUDAD JARDIN</h1> </div>
        <div id="menu">
            <a href="index.php">Home</a>
            <?php echo (isset($_SESSION['USERNAME'])) ? "<a href='logout.php'>Logout</a>" : "<a href='login.php'>Login</a>"; ?>
            <a href="newitem.php">New Item</a>
       </div>
       <div id="container">
       <div id="bar"> <?php require("bar.php"); ?> </div>
       <div id="main">