
<?php
    require_once "bd/bd.php";
    session_start();
    $conn = connectDB();

    if(!$conn) die("<h1>Ha ocurrido un error al conectar, intentelo más tarde</h1>");
?>
<html>
    <head>
        <title><?php echo "subastas ciudad jardin"; ?></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/master.css" type="text/css" />
    </head>
    <body>
        <div id="header"> <h1>SUBASTAS CIUDAD JARDIN</h1> </div>
        <div id="menu">
            <a href="home.php">Home</a>
       </div>
       <div id="container">
       <div id="bar"> <?php require("bar.php"); ?> </div>
       <div id="main">