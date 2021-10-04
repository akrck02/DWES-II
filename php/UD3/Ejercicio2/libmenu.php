<?php 


    function get_users(){
        $file = fopen("../data/users.txt", "r");
        $users = array();
        while(!feof($file)){
            $line = fgets($file);
            $user_data = explode(";", $line);
            $users[] = $user_data;
        }
        fclose($file);
        return $users;
    }

    function login($user , $password){

        $users = get_users();
        if(array_key_exists($user, $users)) {
            if($users[$user] == $password){
                return true;
            }
        }
        
        return false;
    }

    

?>