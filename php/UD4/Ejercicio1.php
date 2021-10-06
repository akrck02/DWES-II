<?php
    session_start();
    define("INPUT_ROWS", 8);

    $send = isset($_POST['send']) ? $_POST['send'] : false;
    $categories = isset($_SESSION['categories']) ? $_SESSION['categories'] : [];
    
    $quantity_error = false;
    $equal_error = false;

    $cache = [];

    if($send){
        for ($i=1; $i <= INPUT_ROWS; $i++) { 
        
            $category =  isset($_POST["category_$i"]) ? $_POST["category_$i"] : "";
            $quantity =  isset($_POST["quantity_$i"]) ? $_POST["quantity_$i"] : 0;
            $save = isset($_POST["save_$i"]) ? true : false;

            if($category != ""){
                if(in_array($category,$cache)){
                    $equal_error = "Debe haber al menos 3 tipos distintos";
                    break;
                }
                
                $cache[] = $category;
                $categories[$i] = [$category,$quantity,$save];
            }
        }

       // print_r($cache);

        if(count($categories) < 3)
            $quantity_error = "Configura un mínimo de 3 tipos de preguntas."; 

        if(!$equal_error and !$quantity_error){
            $_SESSION['categories'] = $categories;
            header("location:./Ejercicio1/jugar.php");
        }
    }

    //print_r($_POST);

?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Preguntas.com - Elegir preguntar</title>
</head>
<style><?php include_once "./styles/Ejercicio1.css";?></style> 
<body>
    <form action="<?php echo $_SERVER['PHP_SELF'] ?>" method="post">
    <table>
        <tr>
            <th>TIPO DE PREGUNTA</th>
            <th>CANTIDAD</th>
            <th>GRABAR FALLOS</th>
        </tr>
        <?php 
            for ($i = 1; $i <= INPUT_ROWS; $i++) { 

                $value = isset($categories[$i]) ? $categories[$i][0] : "";
                echo "<tr>";
                echo "<td>$i <input type='text' name='category_$i' value='$value'></td>";
                echo "<td>";
                    echo "<select name='quantity_$i'>";
                        for ($j = 1; $j <= 10; $j++) {
                            echo "<option>$j</option>";
                        }
                    echo "</select>";
                 echo "</td>";
                 echo "<td><label><input type='checkbox' name='save_$i'> Grabar fallos </label></td>";
                echo "</tr>";
            }
        ?>
    </table>
    <input type="submit" value="ENVIAR TIPOS" name="send">
    </form>
    <?php 
        if($quantity_error)
            echo "<p class='error'>$quantity_error</p>";
        
        if($equal_error)
            echo "<p  class='error'>$equal_error</p>";
    ?>
</body>
</html>