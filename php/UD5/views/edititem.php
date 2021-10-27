<?php require_once "./header.php";
 
    $id = isset($_REQUEST['item'])? $_REQUEST['item'] : false;

    if(!$id)
        header('location:./index.php');

    $item = getItem($conn,$id);

    if(!$item)
        header('location:./index.php');
    
    # Getting possible inputs
    $remove = isset($_REQUEST['remove']) ? $_REQUEST['remove'] : false;
    $up = isset($_REQUEST['up']) ? $_REQUEST['up'] : false;
    $down = isset($_REQUEST['down']) ? $_REQUEST['down'] : false;
    $quantity = isset($_REQUEST['quantity']) ? floatval($_REQUEST['quantity']) : false;

    $addHour = isset($_REQUEST['addHour']) ? $_REQUEST['addHour'] : false;
    $addDay = isset($_REQUEST['addDay']) ? $_REQUEST['addDay'] : false;

    $upload = isset($_POST['upload']) ? $_POST['upload'] : false;

    # Remove an image
    if($remove !== false){
        removeImage($conn,$remove);
    }

    # Add quantity to the price 
    if($up !== false and $quantity !== false){
        $item['precio_partida'] = floatval($item['precio_partida']) + $quantity;
        $price = $item['precio_partida'];
        $date = $item['fechafin'];
        updateItem($conn,$id,$price,$date);
    }

    # Substract quantity to the price
    if($down !== false and $quantity !== false){
        $item['precio_partida'] = floatval($item['precio_partida']) - $quantity;
        if($item['precio_partida'] < 0)
            $item['precio_partida'] = 0;

        $price = $item['precio_partida'];
        $date = $item['fechafin'];
        updateItem($conn,$id,$price,$date);
    }

    # Add one hour
    if($addHour !== false){
        $datestr = $item['fechafin'];
        $date = date_create_from_format("Y-m-d H:i:s", $datestr);
        $date->add(new DateInterval('PT1H'));
        
        $item['fechafin'] = $date->format("Y-m-d H:i:s");
        $price = $item['precio_partida'];
        $date = $item['fechafin'];
        updateItem($conn,$id,$price,$date);
    }

    # Add a day
    if($addDay !== false){
        $datestr = $item['fechafin'];
        $date = date_create_from_format("Y-m-d H:i:s", $datestr);
        $date->add(new DateInterval('PT24H'));
        
        $item['fechafin'] = $date->format("Y-m-d H:i:s");
        $price = $item['precio_partida'];
        $date = $item['fechafin'];
        updateItem($conn,$id,$price,$date);
    }

    # Upload an image
    if($upload !== false){
        $file = isset($_FILES['image']) ? $_FILES['image'] : false;
        if($file !== false){
           print_r($file);

            if($file['size'] > 0){
                if (move_uploaded_file($file['tmp_name'], "../images/".$file['name'])) {
                    newImage($conn,$id,$file['name']);

                }else $uploadError = "No se pudo subir la imagen";
            }


        }else   echo "file does not exist";
    }

    $images = getImagesFromItem($conn,$id);
    $bids = getItemsBids($conn, $id);


?>
<div class="edit-item">
    <h1><?php echo $item['nombre'] ?></h1>
    <form action="" method="post">
        <table class="item-info">
            <tr>
                <td><b>Precio de salida: </b> <?php echo $item['precio_partida'].CURRENCY ?> </td>
                <td>
                    <?php 
                        if(count($bids) == 0){ 
                            echo '<input type="number" name="quantity" id="">';
                            echo '<input type="submit" name="down" value="BAJAR">';
                            echo '<input type="submit" name="up" value="SUBIR">';
                        }
                    ?>
                </td>
            </tr>
            <tr>
                <?php 
                    $datestring = $item['fechafin'];
                    $date = date_create_from_format("Y-m-d H:i:s", $datestring);
                ?>
                <td><b>Fecha fin para pujar: </b> <?php echo $date->format("Y/m/d H.iA") ?></td>
                <td>
                    <input type="submit" name="addHour" value="POSPONER 1 HORA">
                    <input type="submit" name="addDay" value="POSPONER 1 DIA">
                </td>
            </tr>
        </table>
        <input type="hidden" name="item" value="<?php echo $id ?>">
    </form>
    <h1>Imágenes actuales</h1>
    <?php 
        if(count($images) == 0)
            echo "<p>No hay imágenes disponibles</p>";        
    ?>

    <table class="item-images">
    <?php
        # Display the item images
        foreach ($images as $image){
            
            echo "<tr>"; 
            echo "<td><img src='".IMAGES_ROUTE.$image['imagen']."'> </td>";
            echo "<td><b><a href='".$_SERVER['PHP_SELF']."?item=$id&remove=".$image['id']."'>[BORRAR]</a></b></td>";
            echo "</tr>";
        }
           
    ?>
    </table>
    <hr>
    <form action="" enctype="multipart/form-data" method="post">
        <table>
            <tr>
                <td><b>Imagen a subir</b></td>
                <td><input type="file" name="image" id="image"></td>
            </tr>
            <tr>
                <td><input type="submit" name="upload" value="Subir"></td>
            </tr>
        </table>
    </form>
    <p class="error">
        <?php if(isset( $uploadError)) echo $uploadError; ?>
    </p>
</div>
<?php require_once "./footer.php" ?> 