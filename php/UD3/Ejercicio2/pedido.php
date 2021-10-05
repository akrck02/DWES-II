<?php 
    require_once "./libmenu.php";
    session_start();

    $_SESSION['menu'] = isset($_SESSION['menu']) ? $_SESSION['menu'] : [];
  
    if(!isset($_SESSION['user']))
        header("location:../Ejercicio2.php");

    if(isset($_POST['add'])){
        $category = isset($_POST['category']) ? $_POST['category'] : false;
        $dish = isset($_POST['dish']) ? $_POST['dish'] : false;

        if($category and $dish)
            $_SESSION['menu'][$category] = $dish;
    }  

?>
<!DOCTYPE html> 
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pedidos</title>
</head>
<style><?php include_once "../styles/Ejercicio2.css";?></style>
<body>
    <div class="column">
        <h1>MENU DEL DIA</h1>
        <a class="dish_category" href="./pedidoPlato.php?category=<?php echo CATEGORIES['FIRST']?>">PRIMER PLATO</a>
        <a class="dish_category" href="./pedidoPlato.php?category=<?php echo CATEGORIES['SECOND']?>">SEGUNDO PLATO</a>
        <a class="dish_category" href="./pedidoPlato.php?category=<?php echo CATEGORIES['DESSERT']?>">POSTRE</a>
        <a class="dish_category" href="./pedidoPlato.php?category=<?php echo CATEGORIES['DRINK']?>">BEBIDA</a>
    </div>
</body>
<?php 
        if (count($_SESSION['menu']) > 0) {
            echo "<h2>Menú:</h2>";

            $primero = isset($_SESSION['menu'][CATEGORIES['FIRST']]) ? $_SESSION['menu'][CATEGORIES['FIRST']] : false;
            $segundo = isset($_SESSION['menu'][CATEGORIES['SECOND']]) ? $_SESSION['menu'][CATEGORIES['SECOND']] : false;
            $postre = isset($_SESSION['menu'][CATEGORIES['DESSERT']]) ? $_SESSION['menu'][CATEGORIES['DESSERT']] : false;
            $bebida = isset($_SESSION['menu'][CATEGORIES['DRINK']]) ? $_SESSION['menu'][CATEGORIES['DRINK']] : false;

            if($primero) 
                echo "<p><b>Primer plato:</b> $primero</p>";
            if($segundo) 
                echo "<p><b>Segundo plato:</b> $segundo</p>";
            if($postre)
                echo "<p><b>Postre:</b> $postre</p>";
            if($bebida)
                echo "<p><b>Bebida:</b> $bebida</p>";

            echo 
            "<form action='./finpedido.php' method='post'>
                <input type='submit' name='end' value='' id='end'>
            </form>";
        } else echo "<p>No hay nada seleccionado.</p>";    
    ?>

</html>