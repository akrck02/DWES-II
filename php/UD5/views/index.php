<?php 
    $categoryIndex = isset($_GET['category'])? $_GET['category'] : "all";

    //require header
    require_once "./header.php";

    $items = getItemsFromCategory($conn,$categoryIndex);
?>

<h1>Items disponibles</h1>
<table>
    <tr>
        <th>Imagen</th>
        <th>Item</th>
        <th>Pujas</th>
        <th>Precio</th>
        <th>Pujas hasta</th>
    </tr>

 <?php 
    foreach ($items as $item) {


        $image = getImageFromItem($conn, $item['id']);
        $date = $date = new DateTime($item['fechafin']);

        echo "<tr>"; 
            if($image)      
                echo "<td><img src='".IMAGES_ROUTE.$image."'></td>";
            else
                echo "<td> <p class='no-image'>NO IMAGEN</p></td>";

            echo "<td>".$item['nombre']."</td>";
            echo "<td></td>";
            echo "<td>".$item['precio_partida'].CURRENCY."</td>";
            echo "<td>".$date->format('d/m/Y h:i')."</td>";
        echo "</tr>";
    }   
   // print_r($items);
 ?>
</table>
<?php require_once "./footer.php" ?>