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
function cesarCifrate($text, $despl)
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
function substCifrate($text, $filename)
{
    $file = fopen($filename, "r");
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

/**
 * Get a random image array 
 * @param $url - The array
 * @param $n - The number of images
 */
function getRandomImages($url, $n)
{
    $images = getImages($url);

    if (count($images) == 0)
        return [];

    if ($n >= count($images))
        $n = count($images);

    $keys = [];
    for ($i = 0; $i < $n; $i++) {
        $rand = rand(0, count($images) - 1);

        while (in_array($rand, $keys)) {
            $rand = rand(0, count($images) - 1);
        }

        $keys[] = $rand;
    }


    $new = [];

    foreach ($keys as $key)
        $new[] = $url . $images[$key];

    return $new;
}

/**
 * add a line to file
 * @param $url - The directory
 * @param $content - The line to add
 */
function addLineToFile($url, $content)
{

    $file = fopen("./data.txt", "w");
    echo $file ? "true":"false";

    fwrite($file, $content . "\n");
    fclose($file);

}
