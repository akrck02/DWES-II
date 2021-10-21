<?php
require_once "config.php";

/**
 * Connect to the database
 */
function connectDB()
{
    $conn = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB);
    mysqli_set_charset($conn, "UTF8");
    return $conn;
}

/**
 * Get all the categories 
 */
function getCategories($conn)
{

    $sql = "SELECT * FROM CATEGORIA";
    $query = mysqli_query($conn, $sql);
    $categories = [];

    if (!$query) return $categories;

    $result = [];
    while ($category = mysqli_fetch_assoc($query)) {
        $result[] = $category;
    }

    return $result;
}

/**
 * Get items from an specific categories
 */
function getItemsFromCategory($conn, $category)
{

    $items = [];
    $sql = "SELECT * FROM ITEM";

    if ($category != "all") {
        $sql = "SELECT * FROM ITEM WHERE id_cat=?";
        $statement = $conn->prepare($sql);
        $prepared = $statement->bind_param("i", $category);
    }else {
        $statement = $conn->prepare($sql);
        $prepared = true;
    }

    $executed = $statement->execute();

    if ($prepared and $executed) {

        $result = $statement->get_result();
        while($item = $result->fetch_assoc()){
           $items[] = $item;
        }
    }

    $statement->close();
    return $items;
}

/**
 * Get an image for the item
 */
function getImageFromItem($conn, $item)
{
    $sql = "SELECT * FROM IMAGEN WHERE id_item=?";

    $statement = $conn->prepare($sql);
    $prepared = $statement->bind_param("i", $item);
    $executed = $statement->execute();

    if ($prepared and $executed) {

        $result = $statement->get_result();
        if ($result->num_rows > 0) {

            $result = $result->fetch_assoc();
            if (!$result) {
                $statement->close();
                return false;
            }
            $image = $result;
            return $image['imagen'];
        }
    }

    $statement->close();
    return false;
}

/**
 * Get items bid
 */
function getItemsBids($conn, $item){
    $sql = "SELECT * FROM PUJA WHERE id_item=? ORDER BY cantidad";
    $bids = [];

    $statement = $conn->prepare($sql);
    $prepared = $statement->bind_param("i", $item);
    $executed = $statement->execute();

    if ($prepared and $executed) {

        $result = $statement->get_result();
        while($item = $result->fetch_assoc()){
           $bids[] = $item;
        }
    }

    $statement->close();
    return $bids;
}


function generateVerificationString(){
    $max = 16;
    $str = "";

    for ($i=0; $i < $max; $i++) { 
        $char = chr(random_int(ord('A'),ord('z')));
        $str .= $char;
    }

    return $str;
}

/**
 * Register a user 
 */
function register($conn, $username, $password, $name, $email) {


    $verification = generateVerificationString();
    $active = false;
    $false = false;

    $sql = "INSERT INTO usuario(username,nombre,password,email,cadenaverificacion,activo,falso) VALUES(?,?,?,?,?,?,?)";
    $password = md5($password);    

    $statement = $conn->prepare($sql);
    $prepared = $statement->bind_param("sssssii", $username, $name, $password, $email, $verification, intval($active),intval($false));
    $executed = $statement->execute();
    $statement->close();

    return ($prepared and $executed);

}


/**
 * Login into the app
 */
function login($conn, $username, $password) {

    $sql = "SELECT * FROM usuario WHERE activo=1 AND username=? AND password=?";
    $password = md5($password);    

    $statement = $conn->prepare($sql);
    $prepared = $statement->bind_param("ss", $username, $password);
    $executed = $statement->execute();

    $login = false;

    if ($prepared and $executed){
       $login = $statement->get_result()->num_rows > 0;
    }

    $statement->close();
    return $login;
}



/**
 * Check if the username exists
 */
function userExists($conn, $username) {

    $sql = "SELECT * FROM usuario WHERE username=?";

    $statement = $conn->prepare($sql);
    $prepared = $statement->bind_param("s", $username);
    $executed = $statement->execute();

    $exists = false;

    if ($prepared and $executed){
       $exists = $statement->get_result()->num_rows > 0;
    }

    $statement->close();
    return $exists;
}