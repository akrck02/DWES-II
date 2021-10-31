<?php require_once "./header.php";
 
    $items = getExpiredItems($conn);
    $id = isset($_GET['item']) ? $_GET['item'] : false;

    $selected = isset($_POST['selected']) ? $_POST['selected'] : false;
    $delete = isset($_POST['delete']) ? $_POST['delete'] : false;

    if($selected !== false and $delete !== false){
        foreach ($selected as $removable) {
            removeItem($conn,$removable); 
        }
               
    }

?>
<div class="overdue">
    <form action="<?php echo $_SERVER['PHP_SELF'] ?>" method="POST">
    <table>
        <tr>
            <th></th>
            <th>Item</th>
            <th>Precio final</th>
            <th>Ganador</th>
        </tr>
        <?php
        
            foreach ($items as $item) {

                $bid = getItemsBids($conn,$item['id']);
                $bid = isset($bid[0]) ? $bid[0] : false;
                $winner = $bid ? getUsername($conn,$bid['id_user']) : "-";
                $quantity = $bid ? $bid['cantidad'] : $item['precio_partida'];

                echo "<tr>";    
                echo "<td><input type='checkbox' name='selected[]' value='".$item['id']."'></td>";
                echo "<td>";
                echo "<a href='".$_SERVER['PHP_SELF']."?item=".$item['id']."'>".$item['nombre']."</a>";
                if($id !== false and $id == $item['id']) 
                    echo "<p style='color:var(--shutdown-black)'>".$item['descripcion']."</p>";
                echo "</td>";

                echo "<td>".$quantity.CURRENCY."</td>";                
                echo "<td>".$winner."</td>";                
                echo "</tr>";
            }
            echo '<tr><td colspan="4"><input id="delete" type="submit" value="Borrar" name="delete"></td></tr>';        
        
        ?>
    </table>
   
    </form>
</div>
<?php require_once "./footer.php" ?> 