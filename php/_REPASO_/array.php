<?php 
    // Getting array random keys    
    $array = [];
    $max = 10;
    $randomKeys = array_rand($array,$max);

    // Getting array values
    $values = array_values($array);

    // Getting array keys 
    $keys = array_keys($array);

    // Shuffle array
    shuffle($array);

    // If key exists
    $existKey = array_key_exists("cosa", ["cosa" => 0]);

    // slice 
    $slice = array_slice([],$min,$max);

?>