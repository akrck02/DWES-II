<?php
    session_start();
    define("PRODUCT_VALUES", [
        "Play station 5" => 599.99,
        "Xbox series X" => 499.99,
        "Xbox series S" => 299.99,
        "Nintendo switch oled" => 359.99,
        "Nintendo switch" => 299.99,
        "Nintendo switch lite" => 199.99,
        "Steam deck" => 499.99
    ]);

    $cart = isset($_SESSION['cart']) ? $_SESSION['cart'] : [];
    $add = isset($_POST['add']) ? $_POST['add'] : false;
    $finish = isset($_POST['finish']) ? $_POST['finish'] : false;
    $clear = isset($_REQUEST['clear']) ? $_REQUEST['clear'] : false;

    $error = false;

    if ($add) {
        
        $products = isset($_POST['product']) ? $_POST['product'] : [];

        if(count($products) == 0)
            $error = 'Seleccione un producto.';
        

        foreach ($products as $product) {
           $product_name = $product;
           $product = str_replace(" ","_",$product);


           $uds = isset($_POST['select_'.$product])? floatval($_POST['select_'.$product]) : false;

           if($uds){
                if(isset($cart[$product]))
                    $cart[$product] += $uds;
                else
                    $cart[$product] = $uds;
           }else if(!$error)
             $error = "No es posible añadir 0 unidades de " . $product_name;
            else $error .= " , " . $product_name;
        }

        $_SESSION['cart'] = $cart;
    }

    if ($finish) {
        $_SESSION['cart'] = [];
    }

    if ($clear) 
        unset($_SESSION['cart']);
    

?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrito de la compra</title>
</head>
<style><?php include_once "./styles/Ejercicio3.css";?></style>
<body>
    <form action="<?php echo $_SERVER['PHP_SELF'] ?>" method="post">
    <table>
        <tr>
            <th>PRODUCTO</th>
            <th>PRECIO</th>
            <th>ELIJA CANTIDAD</th>
            <th>PEDIDO ACTUAL</th>
        </tr>
        <?php
            foreach (PRODUCT_VALUES as $product => $value) {
                $product_parsed = str_replace(" ","_",$product);
                $uds = isset($cart[$product_parsed]) ? $cart[$product_parsed] : 0;
                echo "<tr>";
                    echo "<td><label><input type='checkbox' name='product[]' value='$product'>&nbsp; $product</label></td>";
                    echo "<td>$value € <input type='hidden' name='price_$product' value='$value'></td>";
                    echo "<td>"; 
                        echo "<select name='select_$product'>";
                            for ($i=0; $i <= 10; $i++) 
                                echo "<option value='$i'>$i</option>";
                        echo "</select>"; 
                    echo "</td>";
                    echo "<td>$uds Unidades pedidas</td>";
                echo "</tr>";
            }
           
        ?>
    </table>
    <p class="error">
        <?php
            if($error)
                echo $error;
        ?>
    </p>
    <input type="submit" name="add" value="AÑADIR UNIDADES">
    <input type="submit" name="finish" value="FORMALIZAR COMPRA">
    <input type="submit" name="clear" value="VACIAR CARRO">
    </form>
    <?php 
        if($finish){

            echo "<table>";
            echo "<tr><th>Producto</th><th>Cantidad</th><th>Precio</th></tr>";

            $total = 0;
            foreach ($cart as $product => $uds) {
                $product_name = str_replace("_"," ",$product);
                $value = PRODUCT_VALUES[$product_name];
                $price = $uds * $value;
                $total += $price;
                
                echo "<tr>";
                    echo "<td><b>$product_name :</b></td>";
                    echo "<td>$uds</td>";
                    echo "<td>$price €</td>";
                echo "</tr>";
}
            echo "<tr><td colspan='3'><h3>Total: $total €</h3></td></tr>";
            echo "</table>";
            echo "<a href='".$_SERVER['PHP_SELF']."?clear=true' id='new'> Nueva compra</a>";
           
        }

    ?>
</body>
</html>