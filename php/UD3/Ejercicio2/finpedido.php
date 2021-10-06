<?php
    require_once "./libmenu.php";
    session_start();

    /**
     * Checking ilegal access
     */
    if(!isset($_SESSION['user']))
        header("location:../Ejercicio2.php");

    $menu  = isset($_SESSION['menu']) ? $_SESSION['menu'] : [];

    $first = isset($menu[CATEGORIES['FIRST']]) ? $menu[CATEGORIES['FIRST']] : false;
    $first_total = $first ?  get_price($first) : 0;
    
    $second = isset($menu[CATEGORIES['SECOND']]) ? $menu[CATEGORIES['SECOND']] : false;
    $second_total = $second ?  get_price($second) : 0;

    $dessert = isset($menu[CATEGORIES['DESSERT']]) ? $menu[CATEGORIES['DESSERT']] : false;
    $dessert_total = $dessert ? get_price($dessert) : 0;

    $drink = isset($menu[CATEGORIES['DRINK']]) ? $menu[CATEGORIES['DRINK']] : false;
    $drink_total = $drink ? get_price($drink) : 0;

    $discount =  isset($_SESSION['discount']) ? $_SESSION['discount'] : 0;
    $total =  $first_total + $second_total + $dessert_total + $drink_total;
    
    if($discount != 0)
        $total *= $discount / floatval(100);
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menu</title>
</head>
<style><?php include_once "../styles/Ejercicio2.css";?></style>
<body>
    <h1>Tu pedido</h1>    
    <?php
        if($first)
            echo "<p><b>$first:</b> $first_total €</p>";
        if($second) 
            echo "<p><b>$second:</b> $second_total €</p>";
        if($dessert)
            echo "<p><b>$dessert:</b> $dessert_total €</p>";
        if($drink)
            echo "<p><b>$drink:</b> $drink_total €</p>";
    ?>
    <div class="result-box"> 
        <p><b>Descuento de</b> <?php echo $discount?>%</p>
        <p><b>Total:</b> <?php echo $total ?> €</p>
    </div>
    <a href="./pedido.php" class="back">Realizar otro pedido</a>
</body>
</html>