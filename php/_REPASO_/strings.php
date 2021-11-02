<?php
    $input = "fino";
    
    $length = strlen($input);
    $replace = str_replace(["A","B"],["c","d"],$input);
    $repeat = str_repeat("x",10);
    $uppercase = strtoupper($input);
    $trimmed = trim($input);

    //CAST
    $int = intval($input);
    $double = doubleval($input);
    $numeric = is_numeric($input);
    $empty = empty($input);
    
    // JOIN AND DRAG
    $exploded = explode(";",$input);
    $joined = join(",",[]);

    // ASCII
    $ord = ord("a");    // int value form "a"
    $ord = chr(65);     // char for 65
?>