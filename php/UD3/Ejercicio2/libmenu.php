<?php 
    define("USERS",get_users());
    define("DISHES",get_dishes());
    define("CATEGORIES",
    [
        "FIRST" => 1,
        "SECOND" => 2,
        "DESSERT" => 3,
        "DRINK" => 4,

    ]);

    /**
     * Get the user data from the file
     */
    function get_users(){
        $file = fopen("../data/socios.txt", "r");
        $users = [];

        while(!feof($file)){
            $line = fgets($file);
            $user_data = explode(" ", $line);
            $users[$user_data[0]] = $user_data;
        }
        fclose($file);
        return $users;
    }

    /**
     * Get the dish data from the file
     */
   function get_dishes($category = ""){
        $dishes = [];
        $file = fopen("../data/platos.txt", "r");
        
        while(!feof($file)){
            $line = fgets($file);
            $dish = explode(" ", $line);
            if($category =="" or $dish[1] == $category)
                $dishes[$dish[0]] = $dish;
        }
        
        fclose($file);
        return $dishes;
    }

    /**
     * Get the discount 
     */
    function get_discount($user) {
        return isset(USERS[$user]) ? floatval(USERS[$user][2]) : 0;
    }

    /**
     * Get price of a product
     */
    function get_price($product){
        return isset(DISHES[$product]) ? floatval(DISHES[$product][2]) : 0;
    }