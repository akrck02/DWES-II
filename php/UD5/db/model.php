<?php
require_once "config.php";

/**
 * Connect to the database
 * @return mysqli The database connection
 */
function connectDB()
{
    $conn = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB);
    mysqli_set_charset($conn, "UTF8");
    return $conn;
}

/**
 * Get all the categories 
 * @param mysqli $conn  the database connection  
 * @return array        The categories
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
 * @param mysqli $conn      The database connection
 * @param int $category     The category id to search for
 * @return array            The items from the category
 */
function getItemsFromCategory($conn, $category)
{

    $items = [];
    $sql = "SELECT * FROM ITEM";

    if ($category != "all") {
        $sql = "SELECT * FROM ITEM WHERE id_cat=?";
        $statement = $conn->prepare($sql);

        if($statement === false)
            return $items;

        $prepared = $statement->bind_param("i", $category);
    } else {
        $statement = $conn->prepare($sql);

        if($statement === false)
            return $items;

        $prepared = true;
    }

    $executed = $statement->execute();

    if ($prepared and $executed) {

        $result = $statement->get_result();
        while ($item = $result->fetch_assoc()) {
            $items[] = $item;
        }
    }

    $statement->close();
    return $items;
}

/**
 * Get expired items
 * @param mysqli $conn  The database connection
 * @return array        The expired items
 */
function  getExpiredItems($conn){
    $items = [];
    $sql = "select * from item where TIMESTAMPDIFF(SECOND, fechafin, now()) > 1";
    
    $statement = $conn->prepare($sql);

    if($statement === false)
        return $items;

    $prepared = true;
    $executed = $statement->execute();

    if ($prepared and $executed) {

        $result = $statement->get_result();
        while ($item = $result->fetch_assoc()) {
            $items[] = $item;
        }
    }

    $statement->close();
    return $items;
}

/**
 * Remove an item
 * @param mysqli $conn  The database connection
 * @param int $item     The item id to remove
 * @return boolean      if item was remove
 */
function removeItem($conn,$item){
    $sql = "DELETE FROM item WHERE id=?";
    $sql2 = "DELETE FROM imagen WHERE id_item=?";
    $sql3 = "DELETE FROM puja WHERE id_item=?";
   
    $statement = $conn->prepare($sql);

    if($statement === false)
        return false;

    $prepared = $statement->bind_param("i", intval($item));
    $executed = $statement->execute();

    if($prepared and $executed){
        $statement = $conn->prepare($sql2);

        if($statement === false)
            return false;

        $prepared = $statement->bind_param("i", intval($item));
        $executed = $statement->execute();

        if($prepared and $executed){
            $statement = $conn->prepare($sql3);

            if($statement === false)
                return false;

            $prepared = $statement->bind_param("i", intval($item));
            $executed = $statement->execute();

            return $prepared and $executed;
        }

    } 

    return false;
}


/**
 * Update item
 * @param mysqli $conn      The database connection
 * @param int $id           The item id
 * @param double $price     The new item price 
 * @param string $date      The new expiration date
 * @return boolean          If the item was updated
 */
function updateItem($conn,$id,$price,$date){
    $sql = "UPDATE item SET precio_partida=?, fechafin=? WHERE id=?";
   
    $statement = $conn->prepare($sql);

    if($statement === false)
        return false;

    $prepared = $statement->bind_param("dsi", floatval($price),$date,intval($id));
    $executed = $statement->execute();

    return $prepared and $executed;
}


/** 
 * Insert image in the database 
 * @param mysqli $conn  The database connection
 * @param int $item     The image owner 
 * @param int $image    The image id 
 * @return boolean      If the image was upload
 **/
function newImage($conn,$item,$image)
{
    $sql = "INSERT INTO imagen(id_item,imagen) VALUES(?,?)";

    $statement = $conn->prepare($sql);
  
    if($statement === false)
        return false;

    $prepared = $statement->bind_param("is", intval($item), $image);
    $executed = $statement->execute();
    $statement->close();


    return $prepared and $executed;
}

/**
 * Remove image
 * @param mysqli $conn      The database connection
 * @param int $image        The image id to remove
 * @return boolean          If the image was removed
 */
function removeImage($conn, $image){
    $sql = "DELETE FROM imagen WHERE id=?";
   
    $statement = $conn->prepare($sql);

    if($statement === false)
        return false;

    $prepared = $statement->bind_param("i", intval($image));
    $executed = $statement->execute();

    return $prepared and $executed;
}

/**
 * Get an image for the item 
 * @param mysqli $conn      The database connection
 * @param int $item         The item id to get the image
 * @return array|boolean    The image if found, else false
 */
function getImageFromItem($conn, $item)
{
    $images = getImagesFromItem($conn, $item);

    if (count($images) > 0)
        return $images[0];

    return false;
}


/**
 * Get an image for the item
 * @param mysqli $conn  The database connection
 * @param int $item     The item id to get the images 
 * @return array        The images of the item
 */
function getImagesFromItem($conn, $item)
{
    $sql = "SELECT * FROM IMAGEN WHERE id_item=?";
    $images = [];

    $statement = $conn->prepare($sql);

    if($statement === false)
        return $images;

    $prepared = $statement->bind_param("i", $item);
    $executed = $statement->execute();

   
    if ($prepared and $executed) {

        $result = $statement->get_result();
        while ($image = $result->fetch_assoc())
            $images[] = $image;
    }

    $statement->close();
    return $images;
}

/**
 * get an item from ID
 * @param mysqli $conn      The database connection
 * @param int $id           The item id
 * @return array|boolean    The item if found, else false
 */
function getItem($conn, $id)
{
    $sql = "SELECT * FROM item WHERE id=?";

    $statement = $conn->prepare($sql);

    if($statement === false)
        return false;

    $prepared = $statement->bind_param("i", $id);
    $executed = $statement->execute();

    if ($prepared and $executed) {

        $result = $statement->get_result();
        if ($result->num_rows > 0) {

            $result = $result->fetch_assoc();
            if (!$result) {
                $statement->close();
                return false;
            }
            $item = $result;
            return $item;
        }
    }

    $statement->close();
    return false;
}

/**
 * Get item by properties
 * @param mysqli $conn          The database connection
 * @param int $category         The category id 
 * @param string $user          The username
 * @param string $name          The item name
 * @param string $description   The item description
 * @param string $date          The item expiration date
 * @return array|boolean        The item if found, else false
 */
function getItemByProperties($conn,$category,$user,$name,$description,$date){
    $sql = "SELECT * FROM item WHERE id_cat=? AND id_user=? AND nombre=? AND descripcion=? AND fechafin=?";

    $statement = $conn->prepare($sql);

    if($statement === false)
        return false;

    $prepared = $statement->bind_param("iisss", $category, $user, $name, $description, $date);
    $executed = $statement->execute();

    if ($prepared and $executed) {

        $result = $statement->get_result();
    
        if ($result->num_rows > 0) {

            $result = $result->fetch_assoc();
            if (!$result) {
                $statement->close();
                return false;
            }
            $item = $result;

            return $item;
        }
    }

    $statement->close();
    return false;
}

/**
 * Get if the user if owner of thee item
 * @param mysqli $conn  The database connection
 * @param int $user     The user id
 * @param int $item     The item id
 * @return boolean      If user is owner
 */
function isItemOwner($conn, $user, $item){
    $sql = "SELECT * FROM item WHERE id_user=? AND id=?";
   
    $statement = $conn->prepare($sql);

    if($statement === false)
        return false;

    $prepared = $statement->bind_param("ii", intval($user), intval($item));
    $executed = $statement->execute();

    $owner = false;

    if ($prepared and $executed) {
        $owner = $statement->get_result()->num_rows > 0;
    }

    $statement->close();
    return $owner;

}


/** 
 * Insert intem in the database
 * @param mysqli $conn          The database connection
 * @param int $category         The category id 
 * @param string $user          The username
 * @param string $name          The item name
 * @param string $description   The item description
 * @param string $date          The item expiration date
 * @return boolean              If the new item was inserted
 **/
function newitem($conn,$category,$user,$name,$price,$description,$date)
{
    $sql = "INSERT INTO item(id_cat,id_user,nombre,precio_partida,descripcion,fechafin) VALUES(?,?,?,?,?,?)";
   

    $statement = $conn->prepare($sql);
    
    if($statement === false)
        return false;

    $prepared = $statement->bind_param("iisdss", $category, $user, $name, $price, $description, $date);
    $executed = $statement->execute();
    $statement->close();


    return $prepared and $executed;
}

/**
 * Get bids of an item
 * @param mysqli $conn  The database connection
 * @param int $item     The id item 
 * @return array        The bids
 */
function getItemsBids($conn, $item)
{
    $sql = "SELECT * FROM PUJA WHERE id_item=? ORDER BY cantidad DESC ";
    $bids = [];

    $statement = $conn->prepare($sql);

    if($statement === false)
        return $bids;

    $prepared = $statement->bind_param("i", $item);
    $executed = $statement->execute();

    if ($prepared and $executed) {

        $result = $statement->get_result();
        while ($item = $result->fetch_assoc()) {
            $bids[] = $item;
        }
    }

    $statement->close();
    return $bids;
}

/**
 * Create a new bid
 * @param mysqli $conn  The database connection
 * @param int $item     The item id
 * @param int $item     The user id
 * @param double $item  The bid quantity
 * @return boolean      If the bid was inserted
 */
function newBid($conn, $item, $user, $quantity)
{
    
    $date = date("Y-m-d");
    $max = count(getDayUserBids($conn,$item,$user,$date)) >= 3;
  
    if($max) 
        return false;

    $sql = "INSERT INTO puja(id_item,id_user,cantidad,fecha) VALUES(?,?,?,?)";

    $statement = $conn->prepare($sql);

    if($statement === false)
        return false;

    $prepared = $statement->bind_param("ssds", $item, $user, floatval($quantity), $date);
    $executed = $statement->execute();
    $statement->close();
    


    return ($prepared and $executed);
}

/**
 * Get the user bids of the day
 * @param mysqli $conn  The database connection
 * @param int $item     The item id
 * @param int $user     The user id 
 * @param string $date  The item expiration date
 * @return array        The user bids of the day
 */
function getDayUserBids($conn, $item, $user, $date)
{
    $bids = [];
    $sql = "SELECT * FROM puja WHERE id_item=? AND id_user=? AND fecha=?";

    $statement = $conn->prepare($sql);

    if($statement === false)
        return $bids;

    $prepared = $statement->bind_param("iis",$item, $user, $date);
    $executed = $statement->execute();

    if ($prepared and $executed) {
        $result = $statement->get_result();
        while ($bid = $result->fetch_assoc())
            $bids[] = $bid;
    }

    $statement->close();
    return $bids;
}

/**
 * Generate a verification string
 * This string is generated adding random 
 * alphabetic characters to itself while 
 * max character limit is not reached
 * @return string The verfication string 
 */
function generateVerificationString()
{
    $max = 16;
    $str = "";

    for ($i = 0; $i < $max; $i++) {
        $char = chr(random_int(ord('A'), ord('z')));
        $str .= $char;
    }

    return $str;
}

/**
 * Register a user 
 * @param mysqli $conn      The database connection
 * @param string $username  The username
 * @param string $password  The password
 * @param string $name      The user full name
 * @param string $email     The user email
 * @return boolean          If the register was successful
 */
function register($conn, $username, $password, $name, $email)
{

    $verification = generateVerificationString();
    $active = false;
    $false = false;

    $sql = "INSERT INTO usuario(username,nombre,password,email,cadenaverificacion,activo,falso) VALUES(?,?,?,?,?,?,?)";
    $password = md5($password);

    $statement = $conn->prepare($sql);

    if($statement === false)
        return false;

    $prepared = $statement->bind_param("sssssii", $username, $name, $password, $email, $verification, intval($active), intval($false));
    $executed = $statement->execute();
    $statement->close();

    return ($prepared and $executed);
}


/**
 * Login into the app
 * @param mysqli $conn          The database connection
 * @param string $username      The username
 * @param string $password      The password
 * @return boolean              If login is correct
 */
function login($conn, $username, $password)
{

    $sql = "SELECT * FROM usuario WHERE activo=1 AND username=? AND password=?";
    $password = md5($password);

    $statement = $conn->prepare($sql);

    if($statement === false)
        return false;

    $prepared = $statement->bind_param("ss", $username, $password);
    $executed = $statement->execute();

    $login = false;

    if ($prepared and $executed) {
        $login = $statement->get_result()->num_rows > 0;
    }

    $statement->close();
    return $login;
}

/**
 * Check if the username exists
 * @param mysqli $conn          The database connection
 * @param string $username      The username
 * @return boolean              If user exists
 */
function userExists($conn, $username)
{

    $sql = "SELECT * FROM usuario WHERE username=?";

    $statement = $conn->prepare($sql);

    if($statement === false)
        return false;

    $prepared = $statement->bind_param("s", $username);
    $executed = $statement->execute();

    $exists = false;

    if ($prepared and $executed) {
        $exists = $statement->get_result()->num_rows > 0;
    }

    $statement->close();
    return $exists;
}


/**
 * Get user from database 
 * @param mysqli $conn          The database connection
 * @param string $username      The username
 * @return array|false          The user if found, elsw false
 */
function getUser($conn, $username)
{
    $sql = "SELECT * FROM usuario WHERE username=?";

    $statement = $conn->prepare($sql);

    if($statement === false)
        return false;

    $prepared = $statement->bind_param("s", $username);
    $executed = $statement->execute();

    $user = false;

    if ($prepared and $executed) {
        $user = $statement->get_result()->fetch_assoc();
    }

    $statement->close();
    return $user;
}

/**
 * Get username by id
 * @param mysqli $conn      The database connection
 * @param int $id           The user id
 * @return array|false      The username if found, else false
 */
function getUsername($conn, $id)
{
    $sql = "SELECT * FROM usuario WHERE id=?";

    $statement = $conn->prepare($sql);

    if($statement === false)
        return false;

    $prepared = $statement->bind_param("i", $id);
    $executed = $statement->execute();

    $user = false;

    if ($prepared and $executed) {
        $user = $statement->get_result()->fetch_assoc()['username'];
    }

    $statement->close();
    return $user;
}


/**
 * Get almost expired items
 * @param mysqli $conn The database connection
 * @return array The expired item
 */
function getAlmostExpiredItems($conn)
{
    $resultItem=[];
    $queryItem="
    SELECT * 
    FROM item 
    WHERE TIMESTAMPDIFF(DAY, fechafin, now()) < 3
    AND NOT EXISTS
    (
        SELECT *
        FROM puja
        WHERE puja.id_item = item.id
    )
    OR precio_partida * 1.1 <
    (
        SELECT max(puja.cantidad)
        FROM puja
        WHERE puja.id_item = item.id
    );";

    $st=$conn -> prepare($queryItem);
    $stExecuted=$st -> execute();
    if($stExecuted) 
    {
        $stResult=$st -> get_result();
        while($item=$stResult -> fetch_assoc()) 
            $resultItem[]=$item;
    }
    $st -> close();
    return $resultItem;
}