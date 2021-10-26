<?php 
    $categoryIndex = isset($_GET['category'])? $_GET['category'] : "all";

    //require header
    require_once "./header.php";

    $items = getItemsFromCategory($conn,$categoryIndex);
?>

<h1>Items disponibles</h1>
<table class='items'>
    <tr>
        <th>Imagen</th>
        <th>Item</th>
        <th>Pujas</th>
        <th>Precio</th>
        <th>Pujas hasta</th>
    </tr>

 <?php 
    foreach ($items as $item) {
        $id = $item['id'];
        $image = getImageFromItem($conn, $id);
        $date = $date = new DateTime($item['fechafin']);
        $bids = getItemsBids($conn,$id);
        $bidCount = count($bids);
        $price = $item['precio_partida'];
        

        if($bidCount > 0) 
            $price = $bids[0]['cantidad'];

        echo "<tr>"; 
            if($image)      
                echo "<td><img src='".IMAGES_ROUTE.$image."'></td>";
            else
                echo "<td> <p class='no-image'>NO IMAGEN</p></td>";

            echo "<td>";
            echo "<a href='itemdetails.php?item=$id'>".$item['nombre']."</a>"; 

            $owner = false;

            if($owner)
               echo "&nbsp;<b><a href='edititems.php?item=$id'>[Editar]</a></b>"; 

            echo "</td>";

            echo "<td>$bidCount</td>";
            echo "<td>$price".CURRENCY."</td>";
            echo "<td>".$date->format('d/m/Y h:iA')."</td>";
        echo "</tr>";
    }   

 ?>
</table>
<?php require_once "./footer.php" ?>