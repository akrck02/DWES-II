<?php
require_once "./styles/components.php";
require_once "./lib/data.php";

define("UPLOAD_DIR","./data/");

function get_articles($url)
{
    $articles = [];
    $html = file_get_contents($url);

    $temp = explode("\n", $html);
    foreach ($temp as $line) {
        $article = explode(";", $line);
        if (count($article) >= 2)
            $articles[] = $article;
    }

    return $articles;
}

$total = isset($_GET["total"]) ? $_GET["total"] : 0;
$product_price = isset($_POST["product_price"]) ? $_POST["product_price"] : null;
$product_name = isset($_POST["product_name"]) ? $_POST["product_name"] : null;
$product_file = isset($_FILES["product_file"]) ? $_FILES["product_file"] : null;

$articles = get_articles("./data/articulos.txt");
if ($product_name != null and $product_price !== null) {
  
    if($product_file !== null) {
        if (move_uploaded_file($_FILES['product_file']['tmp_name'],  UPLOAD_DIR . basename($product_name . ".txt") )) {
            addLineToFile("./data/articulos.txt", $product_name . ";" . UPLOAD_DIR . basename($product_name . ".txt"));
            $articles[] = [$product_name, $product_price, UPLOAD_DIR . basename($product_name . ".txt")];
        } else {
            $articles[] = [$product_name, $product_price];
            echo "<p style='color:crimson'>ERROR: El archivo no se puedo subir, intentélo de nuevo.</p>";
        } 
           
    } else {
        addLineToFile("./data/articulos.txt", $product_name . ";" . $product_price);
    }
}

?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <style>
        <?php 
            include_once "./styles/styles.css"; 
            include_once "./styles/Ej3.css"; 
        ?>
    </style>
</head>

<body>
    <h1>ELIGE TU PEDIDO</h1>
    <table>
        <?php
        foreach ($articles as $article) {
            $article[2] = isset($article[2]) ? "<a href='".$article[2]."'>Archivo</a>" : "";
            echo "<tr>
                    <td>$article[0]</td>
                    <td>$article[1]€</td>
                    <td>$article[2]</td>
                    <td><a href='?total=" . ($total + floatval($article[1])) . "'>Añadir unidad</a></td>
            </tr>";
        }
        ?>
    </table>
    <h2>TOTAL <?php echo $total; ?> €</h2>

    <h2>AÑADE ARTICULO</h2>
    <form action="<?php echo $_SERVER['PHP_SELF'] ?>" method="post" enctype="multipart/form-data">
    
        <table>
            <tr>
                <th>Nombre:</th>
                <th>Precio(€):</th>
            </tr>
            <tr>
                <td><input type="text" name="product_name"></td>
                <td><input type="text" name="product_price"></td>
                <td><input type="submit" name="product_register"></td>

            </tr>
            <tr>
                <td colspan="2"><input type="file" name="product_file"></td>
            </tr>
        </table>
    </form>

    <?php echo navbar(2, 3, 4, "./Ejercicio2.php", "./Ejercicio4.php") ?>
</body>

</html>