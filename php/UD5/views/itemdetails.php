<?php require_once "./header.php";
    $id = isset($_REQUEST['item'])? $_REQUEST['item'] : false;
    $bidButton = isset($_REQUEST['bid'])? $_REQUEST['bid'] : false;
    $error = false;

    # If no item is selected return to index.php
    if(!$id)
        header("location:./index.php");

    $item = getItem($conn,$id);

    # If the item exists, get item data, else return to index.php 
    if($item){
        $date = new DateTime($item['fechafin']);
        $date = $date->format('d/m/Y h:iA');
    
        $images = getImagesFromItem($conn,$id);
        $bids = getItemsBids($conn,$id);
        $price = floatval($item['precio_partida']);

        # If there are bids, takes the biggest one and compares price
        if(count($bids) > 0){
            $bid = $bids[0];
            $quantity = floatval($bid['cantidad']);       

            if($quantity > $price)
                $price = $quantity;     
        }

        # If a new bid is made, check it and if it is correct insrt into database
        if($bidButton){
            $quantity = isset($_REQUEST['quantity']) ? $_REQUEST['quantity'] : false;
            if($quantity){
                
                #If th bid is higher than previus ones, insert into database and update view data
                if($quantity > $price){
                    $created = newBid($conn,$id,$_SESSION['userId'],$quantity);
                    if($created) {  
                        $bids = getItemsBids($conn,$id);
                        $price = $quantity;
                    }    
                    else  $error = "Límite 3 pujas por dia.";
                       
                } else $error = "Puja muy baja!";   
            } else $error = "Seleccione una cantidad.";
        }

    } else header("location:./index.php");

?>

<div class="item-details">
    <h1><?php echo $item['nombre'] ?></h1>
    <?php 
        # Display item info
        echo "<p class='mininfo'><b>Numero de pujas: </b> ".count($bids)." - <b>Precio actual: </b> $price".CURRENCY." - <b>Fecha fin para jugar: </b> $date </p>";

        # Display the item images
        foreach ($images as $image)
            echo "<img src='".IMAGES_ROUTE.$image['imagen']."'>"
    ?>
    <p><?php echo $item['descripcion']?></p>

    <h1>Puja por este item</h1>
    <?php if(isset($_SESSION['user'])){ ?>
       <p>Añade tu puja en el cuadro inferior.</p>
        <form action="<?php echo $_SERVER['PHP_SELF']?>" method="POST">
            <table class='new-bid'>
                <tr>
                    <td><input type='number' name='quantity' step="0.01" min="0"></td>
                    <td><input type='submit' name='bid' value='Puja'></td>
                </tr>
            </table>
            <input type="hidden" name="item" value ="<?php echo $id?>">
        </form>
        <p class="error"><?php echo $error ? $error : "" ?></p>
    <?php } else echo "<p>Para pujar debes autenticarte <b><a href='./Login.php'>aquí.</a></b></p>"; ?>
    <h3>Historial de la puja: </h3>
    <?php 
        # Display each bid
       foreach ($bids as $bid) 
           echo "<li><b>".getUsername($conn,$bid['id_user'])."</b> - ".$bid['cantidad'].CURRENCY."</li>";
    ?>
</div>
<?php require_once "./footer.php" ?> 