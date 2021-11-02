<?php 

    //Database info
    date_default_timezone_set("Europe/Madrid");
    define("DB_HOST" , "localhost");
    define("DB_USER" , "root");
    define("DB_PASSWORD" , "");
    define("DB" , "subastas");

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

?>