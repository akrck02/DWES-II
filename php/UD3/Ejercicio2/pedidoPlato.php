<?php  
    session_start();
    define("CATEGORIES",
    [
        "FIRST" => 1,
        "SECOND" => 2,
        "DESSERT" => 3,
        "DRINK" => 4,

    ]);
  
   function get_dishes($category){
        $dishes = [];
        $file = fopen("../data/platos.txt", "r");
        
        while(!feof($file)){
            $line = fgets($file);
            $dish = explode(" ", $line);
            if($dish[1] == $category)
                $dishes[] = $dish;
        }
        
        fclose($file);
        return $dishes;
    }

    function get_category($category = ""){
        switch ($category) {
            case CATEGORIES['FIRST']:
                return "primero";
            case CATEGORIES['SECOND']:
                return "segundo";
            case CATEGORIES['DESSERT']:
                return "postre";
            case CATEGORIES['DRINK']:
                return "bebida";
            default: break;
        }
    }

    $categoria = isset($_POST['categoria']);
    $category = isset($_REQUEST['category']) ? $_REQUEST['category'] : false;

    if($category)
        $dishes = get_dishes($category);
    else
        header("location: ./pedido.php");
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pedido</title>
</head>
<style><?php include_once "../styles/Ejercicio2.css";?></style>
<body>
    <p>Elija un <?php echo get_category($category) ?></p>
    <form action="./pedido.php" method="post">
        <select name="dish">
            <?php
                foreach ($dishes as $dish) {
                    echo "<option value='$dish[0]'>$dish[0]</option>";
                }
            ?>
        </select>
        <input type="hidden" name="category" value="<?php echo $category?>">
        <input type="submit" name="add" value="Elegir <?php echo get_category($category) ?>">
    </form>
</body>
</html>