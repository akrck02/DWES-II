<?php require_once "./header.php";

    if(!isset($_SESSION['user']))
        header('location:./login.php');


    # If new item button is pressed, try to insert item
    $newItemButton = isset($_POST['newItem']) ? $_POST['newItem'] : false;
    $error = false;

    if($newItemButton){

        $name = isset($_POST['name']) ? $_POST['name'] : false;
        $description = isset($_POST['description']) ? $_POST['description'] : false;
        $price = isset($_POST['price']) ? $_POST['price'] : false;

        $day = isset($_POST['day']) ? $_POST['day'] : false;
        $month = isset($_POST['month']) ? $_POST['month'] : false;
        $year = isset($_POST['year']) ? $_POST['year'] : false;
        $hour = isset($_POST['hour']) ? $_POST['hour'] : false;
        $minutes = isset($_POST['minutes']) ? $_POST['minutes'] : false;

       
       
        if(!$price) $error = "Introduzca un precio";
        if(!$description) $error = "Introduzca una descripción";
        if(!$name) $error = "Introduzca un nombre de producto";

        # If no errors are presented insert item to database
        if(!$error){

            $date = "$year-$month-$day $hour:$minutes:00";
            newitem($conn,$category['id'],$_SESSION['userId'],$name,$price,$description,$date);
            
            # Get item ID and redirect to edit item
            $item = getItemByProperties($conn,$category['id'],$_SESSION['userId'],$name,$description,$date);
            header("location:./edititem.php?item=".$item['id']);
                  
        }

    }


    $categories = getCategories($conn);
    $now = new DateTime();
?>

<div class="new-item">
    <form action="" method="post">
    <table>
        <tr>
            <td><label for="category">Categoría</label></td>
            <td>
                <select name="category" id="category">
                    <?php 
                        foreach ($categories as $category) {
                            echo "<option value='".$category['id']."'>".$category['categoria']."</option>";
                        }
                    ?>
                </select>
            </td>
        </tr>
        <tr>
            <td><label for="name">Nombre</label></td>
            <td><input type="text" name="name" id="name"></td>
        </tr>
        <tr>
            <td><label for="description">Descripcion</label></td>
            <td><textarea name="description" id="description" cols="30" rows="10"></textarea></td>
        </tr>
        <tr>
            <td><label for="day">Fecha fin para pujas</label></td>
            <td>
                <table>
                    <tr>
                        <th>Día</th>
                        <th>Mes</th>
                        <th>Año</th>
                        <th>Hora</th>
                        <th>Minutos</th>
                    </tr>
                    <tr>
                        <td>
                            <select name="day" id="day">
                            <?php 
                                for ($i=1; $i <= 31; $i++) 
                                   echo "<option>$i</option>";
                            ?>
                            </select>
                        </td>
                        <td>
                            <select name="month" id="month">
                            <?php 
                                for ($i=1; $i <= 12; $i++) 
                                     echo "<option>$i</option>";
                            ?>
                            </select>
                        </td>
                        <td>
                            <select name="year" id="year">
                            <?php 
                                for ($i=$now->format('Y'); $i <= $now->format('Y') + 5; $i++) 
                                    echo "<option>$i</option>";
                            ?>
                            </select>
                        </td>
                        <td>
                            <select name="hour" id="hour">
                            <?php 
                                for ($i=0; $i < 24; $i++) 
                                    echo "<option>$i</option>";  
                            ?>
                            </select>
                        </td>
                        <td>
                            <select name="minutes" id="minutes">
                            <?php 
                                for ($i=0; $i < 60; $i++) 
                                     echo "<option>$i</option>";    
                            ?>
                            </select>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td><label for="price">Precio</label></td>
            <td><input type="number" name="price" id="price" step="0.01" min="0"> <?php echo CURRENCY?></td>
        </tr>
        <tr>
            <td><input type="submit" name="newItem" value="Enviar!"></td>
        </tr>

    </table>
    </form>
    <p class="error">
        <?php if($error) echo $error ?>
    </p>
</div>
<?php require_once "./footer.php" ?> 