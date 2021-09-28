<?php
define("COMPATIBLE_FILES", ["png", "jpg", "webp", "gif"]);
define("IMAGE_DIR", "./images/");
define("ABC",  $abc = ["a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"]);

/**
 * Cifrate replacing the characters with the 
 * corresponding alphabetic character adding $despl to
 * the actual index. 
 * 
 * @param $text - The text to cifrate
 * @param $despl - The number of index to move
 */
function cesarCifrate($text = "", $despl = 0)
{

    $new = "";

    for ($i = 0; $i < strlen($text); $i++) {
        $letter_index =  array_search(strtolower($text[$i]), ABC);
        $new_index = $letter_index + $despl;

        if ($new_index >= count(ABC))
            $new_index = $new_index - count(ABC);

        $new .= ABC[$new_index];
    }

    return strtoupper($new);
}

/**
 * Cifrate a text replacing alphabetic numbers with 
 * the corresponding character in the file
 * 
 * @param $text - The text to cifrate
 * @param $filename - The file to use
 */
function substCifrate($text = "", $filename = "")
{
    if($filename == "")
        return $text;

    $file = fopen($filename, "r+");
    $abc = fread($file, filesize($filename));

    fclose($file);
    $new = "";

    for ($i = 0; $i < strlen($text); $i++) {
        $index = array_search(strtolower($text[$i]), ABC);
        if ($index === false) $new .= $text[$i];
        else $new .= $abc[$index];
    }

    return $new;
}

/**
 * Get the image count
 * @param $url - The directory 
 */
function getImages($url = "./")
{
    $files = scandir($url);
    $images = [];

    foreach ($files as $file) {
        foreach (COMPATIBLE_FILES as $extension) {
            if (strrpos($file, ".$extension") !== false) {
                $images[] = $file;
                break;
            }
        }
    }

    return $images;
}

/**
 * Get a random image array 
 * @param $url - The array
 * @param $n - The number of images
 */
function getRandomImages($url = "./", $n = 0)
{
    $images = getImages($url);

    if (count($images) == 0)
        return [];

    if ($n > count($images))
        $n = count($images);

    $keys = array_keys($images);
    shuffle($keys);

    $new = [];
    for ($i = 0; $i < $n; $i++) 
        $new[] = $images[$keys[$i]];

    return $new;
}

#  IMPORTANT INFORMATION !!!
# ------------------------------------------------------
# I haven't used array_rand() but I think it's not a good idea
# because it's not a random array without repetitions
# ANYWAY the code would be the following
function getRandomImageWithArrayRand($url = "./", $n = 0){
    $images = getImages($url);

    if (count($images) == 0)
        return [];

    if ($n >= count($images))
        $n = count($images);


    $keys = array_rand($images, $n);
    $new = [];

    foreach ($keys as $key)
        $new[] = $url . $images[$key];

    return $new;

}

/**
 * add a line to file
 * @param $url - The directory
 * @param $content - The line to add
 * @return if the line was added
 */
function addLineToFile($url = "./", ...$content)
{

    if (!is_dir(dirname($url))) {
        mkdir(dirname($url), 0777, true);
    }

    $file = fopen($url, "a+");
    if ($file != false) {
        foreach ($content as $line) {
            if(is_array($line))
                fwrite($file, implode(" ", $line) . " ");
            else
                fwrite($file, $line . " ");
        }
        fwrite($file, "\n");
        fclose($file);
        return true;
    } else return false;

    return true;
}
