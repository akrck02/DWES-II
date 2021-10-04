<?php
require_once "./styles/components.php";
require_once "./lib/data.php";

define("UPLOAD_DIR", "./data/");
define("DB", UPLOAD_DIR . "articulos.txt");

/**
 * Get articles from the file
 */
function get_items($url)
{
    $items = [];
    $html = file_get_contents($url);

    $temp = explode("\n", $html);
    foreach ($temp as $line) {
        $item = explode(";", $line);
        if (count($item) >= 2)
            $items[$item[0]] = $item;
    }

    return $items;
}

/**
 * Get the line of an article or  
 */
function get_item_line($url, $item)
{

    $file = fopen($url, "r");
    $line = "";
    $i = 0;
    while (!feof($file)) {
        $line = fgets($file);
        $i++;
        if (strpos($line, $item) !== false) {
            fclose($file);
            return $i;
            break;
        }
    }
    fclose($file);
    return false;
}

/**
 * Replaces an item on the file
 */
function replace_item($url, $item, $line)
{
    $html = file_get_contents($url);
    $temp = explode("\n", $html);

    if (count($item) >= 3)
        $temp[$line - 1] = $item[0] . ";" . $item[1] . ";" . $item[2];
    else if (count($item) == 2)
        $temp[$line - 1] = $item[0] . ";" . $item[1];

    $html = implode("\n", $temp);
    file_put_contents($url, $html);
}


/**
 * Adds a file if it isn't on the file
 * or replace it if so,
 */
function add_or_replace_article($url, $item)
{

    if(count($item) == 0)
        return false;

    $article_line = get_item_line($url, $item[0]);

    if ($article_line !== false)
        replace_item($url, $item, $article_line);
    else
        addLineToFile($url, $item[0] . ";" . $item[1]);
}


/**
 *  Checkingg parameters
 */
$total = isset($_GET["total"]) ? $_GET["total"] : 0;
$product_price = isset($_POST["product_price"]) ? $_POST["product_price"] : null;
$product_name = isset($_POST["product_name"]) ? $_POST["product_name"] : null;
$product_file = isset($_FILES["product_file"]) ? $_FILES["product_file"] : null;

if ($product_name != null and $product_price !== null) {
    $article_line = get_item_line(DB, $product_name);

    if ($product_file !== null) {
        if(isset($product_file['size']) and $product_file['size'] > 0){
            if (move_uploaded_file($_FILES['product_file']['tmp_name'], UPLOAD_DIR.$product_name.".txt")) 
                add_or_replace_article( DB, [$product_name, $product_price, UPLOAD_DIR.$product_name.".txt"]);
            else 
                echo "<p style='color:crimson'>ERROR: El archivo no se puede subir, intentélo de nuevo.</p>";
        } else 
            add_or_replace_article(DB, [$product_name,$product_price]);
    } else 
        add_or_replace_article(DB, [$product_name,$product_price]);
}

$articles = get_items(DB);
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
    <?php echo navbar(2, 3, 4, "./Ejercicio2.php", "./Ejercicio4.php") ?>

    <h1>ELIGE TU PEDIDO</h1>
    <table>
        <?php
        foreach ($articles as $article) {
            $article[2] = isset($article[2]) ? "<a href='" . $article[2] . "'>Archivo</a>" : "";
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
</body>
</html>