<?php
  define("COMPATIBLE_FILES",["png","jpg","webp","gif"]);
  define("IMAGE_DIR","./images/");

  # Get the image count
  function getImages($url){
      $files = scandir($url);
      $images = [];
 
      foreach ($files as $file) {
          foreach (COMPATIBLE_FILES as $extension) {
              if(strpos($file,".$extension") !== false) {
                 $images[] = $file;
                 break;
              }
          }
      }

      return $images;
  }

  #Get random imagess
  function getRandomImages($url,$n){
      $images = getImages($url);

      if($n >= count($images))
        $n = count($images) - 1;

      $keys = array_rand($images,$n);
      print_r($keys);
      $new = [];
      
      foreach ($keys as $key) 
          $new[] = $images[$key];
      

      return $new;
  }

?>