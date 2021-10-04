<?php 
    define("CATEGORIES",
    [
        "FIRST" => 0,
        "SECOND" => 1,
        "DESSERT" => 2,
        "DRINK" => 3,

    ]);

?>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pedidos</title>
</head>
<style>
    .column{
        display: flex;
        flex-direction: column;
    }
</style>
<body>
    <div class="column">
        <h1>MENU DEL DIA</h1>
        <a href="./pedidoPlato.php?category=<?php echo CATEGORIES['FIRST']?>">PRIMER PLATO</a>
        <a href="./pedidoPlato.php?category=<?php echo CATEGORIES['SECOND']?>">SEGUNDO PLATO</a>
        <a href="./pedidoPlato.php?category=<?php echo CATEGORIES['DESSERT']?>">POSTRE</a>
        <a href="./pedidoPlato.php?category=<?php echo CATEGORIES['DRINK']?>">BEBIDA</a>
    </div>
</body>

</html>