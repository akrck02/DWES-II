
<?php
    require_once "../db/model.php";
    session_start();
    $conn = connectDB();

    if(!$conn) header("location:./error.php")
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
            <?php 
                $selected = -1;
                $currentPage = $_SERVER['REQUEST_URI'];
                $user = isset($_SESSION['user']) ? $_SESSION['user'] : false;

                if(strpos($currentPage,"index.php")) $selected = 0;
                if(strpos($currentPage,"newitem.php")) $selected = 1;
                if(strpos($currentPage,"overdueitems.php")) $selected = 2;
                if(strpos($currentPage,"publi.php")) $selected = 3;
            ?>
            <a href="index.php" <?php echo ($selected == 0)? "class='selected'": ""; ?> >Home</a>
            <?php echo ($user !== false) ? "<a href='logout.php'>Logout</a>" : "<a href='login.php'>Login</a>"; ?>
            <a href="newitem.php" <?php echo ($selected == 1)? "class='selected'": ""; ?>>New Item</a>
            <?php
               if($user == "admin"){
                    echo "<a href='./overdueitems.php' ".($selected == 2 ? "class='selected'": "")." >Subastas vencidas</a>";
                    echo "<a href='./publi.php' ".($selected == 3 ? "class='selected'": "").">Anunciantes</a>";
               }
            ?>
       </div>
       <div id="container">
       <div id="bar"> <?php require("bar.php"); ?> </div>
       <div id="main">