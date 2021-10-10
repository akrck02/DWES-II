<?php
    require_once "./Ejercicio1/constants.php";
    session_start();
    define("INPUT_ROWS", 8);

    $send = isset($_POST['send']) ? $_POST['send'] : false;
    $categories = isset($_SESSION['categories']) ? $_SESSION['categories'] : [];
    
    $quantity_error = false;
    $equal_error = false;

    $valid_categories = array_keys(QUESTIONS);
    $cache = [];
    $clone = isset($_SESSION['clone'])? $_SESSION['clone'] : [];

    if($send){
        for ($i=1; $i <= INPUT_ROWS; $i++) { 
        
            $category =  isset($_POST["category_$i"]) ? $_POST["category_$i"] : "";
            $category = strtoupper($category);

            $quantity =  isset($_POST["quantity_$i"]) ? $_POST["quantity_$i"] : 0;
            $save = isset($_POST["save_$i"]) ? true : false;

            if($category != ""){
                if(in_array($category,$cache)){
                    $equal_error = "Debe haber al menos 3 tipos distintos";
                    break;
                }
                
                $cache[] = $category;
                if(in_array($category,$valid_categories)) {
                    $categories[$category] = [
                        "quantity" => $quantity,
                        "save_errors" => $save,
                        "success" => 0,
                        "question_index" => 0,
                    ];
                    $clone[$i] = $category;
                }
                else { 
                    if(isset($categories["OTRAS"])){
                        $categories["OTRAS"]["quantity"] += $quantity;
                        $categories["OTRAS"]["save_errors"] = $save;
                        $categories["OTRAS"]["success"] = 0;
                        $categories["OTRAS"]["question_index"] = 0;
                    } else {
                        $categories["OTRAS"] = [
                            "quantity" => $quantity,
                            "save_errors" =>$save,
                            "success" => 0,
                            "queston_index" => 0
                        ];
                        $clone[$i] = "OTRAS";
                    }

                }

            }
        }

        if(count($categories) < 3)
            $quantity_error = "Configura un mínimo de 3 tipos de preguntas."; 

        if(!$equal_error and !$quantity_error){
            $_SESSION['categories'] = $categories;
            $_SESSION['clone'] = $clone;
            header("location:./Ejercicio1/jugar.php");
        }
    }
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

                $category = "";
                $data = false;
                $quantity = false;
                $check = false;

                if(isset($clone[$i])){
                    
                    $category = $clone[$i];

                    if(isset($categories[$clone[$i]]))
                        $data = $categories[$clone[$i]];

                    if($data and is_array($data) and count($data) >= 2){
                        $quantity = $data[0];
                        $check = $data[1];
                    }
                }


                echo "<tr>";
                echo "<td>$i <input type='text' name='category_$i' value='$category'></td>";
                echo "<td>";
                    echo "<select name='quantity_$i'>";
                        for ($j = 1; $j <= 10; $j++) {
                            if($quantity == $j) 
                                echo "<option selected>$j</option>";
                            else 
                                echo "<option>$j</option>";
                        }
                    echo "</select>";
                 echo "</td>";
                 echo "<td><label><input type='checkbox' ".($check ? "checked" : "")." name='save_$i'> Grabar fallos </label></td>";
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