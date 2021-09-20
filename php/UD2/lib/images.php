<?php
define("COMPATIBLE_FILES", ["png", "jpg", "webp", "gif"]);
define("IMAGE_DIR", "./images/");

# Get the image count
function getImages($url)
{
    $files = scandir($url);
    $images = [];

    foreach ($files as $file) {
        foreach (COMPATIBLE_FILES as $extension) {
            if (strpos($file, ".$extension") !== false) {
                $images[] = $file;
                break;
            }
        }
    }

    return $images;
}

#Get random imagess
function getRandomImages($url, $n)
{
    $images = getImages($url);

    if(count($images) == 0)
        return [];

    if ($n >= count($images))
        $n = count($images);

    $keys = array_rand($images, $n);




    $new = [];

    if (is_array($keys))
        foreach ($keys as $key)
            $new[] = $url.$images[$key];
    else { 
        $new[] = $url.$images[$keys];
    }
    return $new;
}
