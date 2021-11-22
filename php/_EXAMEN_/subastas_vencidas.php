<?php 
    require_once "header.php";
    $expiredItems = getExpiredItems($conn);
?>

<h1>Ultimas subastas vencidas</h1>
<table>
    <tr>
        <th>FINALIZO EL</th>
        <th>CATEGORIA</th>
        <th>ITEM</th>
        <th>GANADOR</th>
    </tr>

    <?php 
        foreach ($expiredItems as $item) {

            $endDate = new DateTime($item['fechafin']);
            $endDate = $endDate->format('d-M-y H:i');

            $category = getCategory($conn, $item['id_cat']);
            if($category !== false)
                $category = $category['categoria'];

            $winnerBid = getItemWinnerBid($conn, $item['id']);
            $selected = (isset($_GET['id']) and $item['id'] == $_GET['id']);


            if($selected)
                echo "<tr style='background: #e8e8e8'>"; 
            else 
                echo "<tr>";

            echo "<td>$endDate</td>";
            echo "<td>".$category."</td>";
            echo "<td>".$item['nombre']."</td>";
           
            if($winnerBid !== false){
                $winnerUser = getUser($conn,$winnerBid['id_user'])['nombre'];
                echo "<td>";
                echo "<a href='".$_SERVER['PHP_SELF']."?id=".$item['id']."'>".$winnerUser."</a>";
                if($selected){

                    $percentage = ($winnerBid['cantidad'] - $item['preciopartida']) / $item['preciopartida'];
                    $percentage = round($percentage,2);

                    echo "<p>Ganado por ".$winnerBid['cantidad'].CURRENCY."</p>";
                    echo "<p>".$percentage."% superior al  precio de partida(".$item['preciopartida']." ".CURRENCY.")</p>";
                }
                echo "</td>";
            }
            else echo "<td>Sin pujas</td>";
           
            echo "</tr>";
        }
    
    ?>

</table>


<?php require_once "footer.php"; ?>
