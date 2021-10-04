<?php 
    $categoria = isset($_POST['categoria']);


?>
<!DOCTYPE html>
<html lang="en">
<?php
    $category = isset($_REQUEST['category']) ? $_REQUEST['category'] : false;
     
    function get_dishes($category){

        $dishes = [];
        // open dishes file 




        return $dishes;

    }


    if($category)
    {
        $dishes = get_dishes($category);
    } else header("location: ./pedido.php");


?>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <p>Elija un $a</p>
    <select>
        <?php
            foreach ($dishes as $dish) {
                echo "<option value='$dish'>$dish</option>";
            }
        ?>
    </select>
    <input type="submit" value="Añadir">
</body>
</html>