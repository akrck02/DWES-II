<?php 
    require_once "config.php";
    
    /**
     * Connect to the database
     */
    function connectDB(){
        $conn = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB);
        mysqli_set_charset($conn,"UTF8");
        return $conn;
    }

    /**
     * Get all the categories 
     */
    function getCategories($conn){

        $sql = "SELECT * FROM CATEGORIA";
        $query = mysqli_query($conn,$sql);
        $categories = [];

        if(!$query) return $categories;

        $result = [];
        while($category = mysqli_fetch_assoc($query)){
            $result[] = $category;
        }

        return $result ;
    }

    /**
     * Get items from an specific categories
     */
    function getItemsFromCategory($conn, $category) {
        $sql = "SELECT * FROM ITEM";

        if($category != "all")
            $sql = "SELECT * FROM ITEM WHERE id_cat=$category";

        $query = mysqli_query($conn,$sql);
        $categories = [];

        if(!$query) return $categories;

        $result = [];
        while($category = mysqli_fetch_assoc($query)){
            $result[] = $category;
        }

        return $result ;
    }

    /**
     * Get an image for the item
     */
    function getImageFromItem($conn, $item) {

        $sql = "SELECT * FROM IMAGEN WHERE id_item=$item";
        $query = mysqli_query($conn,$sql);

        if(!$query) return false;
        $image = mysqli_fetch_assoc($query);
        return $image['imagen'];
    }

    /**
     * Get items bid
     */
    function getItemsBid($conn, $item) {
       
    }

 