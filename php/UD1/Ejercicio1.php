<?php

/** Get today's date formatted */
function GetFormattedDate()
{

    $day = (int) date("d");
    $suffix = ["\s\\t", "\\n\d", "\\r\d", "\\t\h"];
    $suffix = $suffix[$day] ?? "\\t\h";

    return  date("d{$suffix} F Y \, l");
}

/** Get if a year is leap or not */
function isLeapYear($year)
{
    return (0 == $year % 4) & (0 != $year % 100) | (0 == $year % 400);
}

/** Calculate days left in a year (Without substracting dates) */
function daysLeftInYear($day, $month, $year)
{
    $monthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

    if (isLeapYear($year)) {
        $monthDays[1] = 29;
    }

    $daysLeft = 0;
    $daysLeft += $monthDays[$month] - $day;

    for ($i = $month + 1; $i < 12; $i++) {
        $daysLeft += $monthDays[$i];
    }

    return $daysLeft;
}

/** Create a phrase by array element concatenation */
function phraseFromArray($words)
{
    $phrase = "";
    foreach ($words as $world) {
        $phrase .= $world . " ";
    }
    return $phrase;
}

/** Create an array with n random numbers between two given numbers */
function randomArray($n, $min, $max)
{

    #if the minimum value is lower swift them 
    if ($min > $max) {
        $aux = $max;
        $min = $aux;
        $max = $min;
    }

    $array = [];
    for ($i = 0; $i < $n; $i++) {
        $array[$i] = rand($min, $max);
    }
    return $array;
}

/** Cifrate a text by replacement */
function cifrate($text)
{
    define('cifrator', array("A" => "20", "H" => "9R", "M" => "abcd"));
    return str_replace(array_keys(cifrator), array_values(cifrator), $text);
}

?>


<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ejercicio 1</title>
</head>
<style>
    body {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    p {
        background: #f1f1f1;
        padding: 10px;
        max-width: 500px;
        border-radius: 6px;
        margin: 5px 5px;
    }

    #navbar {
        position: fixed;
        display: flex;
        justify-content: space-between;
        align-items: center;
        box-sizing: border-box;

        width: 100%;
        height: 60px;
        
        bottom : 10px;
        left: 0;
        padding: 10px;
    }

    #navbar a {
        display: flex;
        justify-content: center;
        align-items: center;
        background: #f1f1f1;

        border-radius: 100%;
        width: 30px;
        height: 30px;
        padding: 10px;
        text-decoration: none;
        color : #202020;
        font-weight: bold;
    }
</style>

<body>
    <h1>RESULTADOS</h1>
    <p><?php echo GetFormattedDate() ?></p>

    <p>
        <?php
        //create a date and get days left in year
        $date = date("d/m/Y");
        $date = explode("/", $date);

        echo daysLeftInYear(+$date[0], +$date[1] - 1, +$date[2]) . " Days left.";
        ?>
    </p>
    <p><?php echo "Phrase : " . phraseFromArray(["Hola ", "que ", "tal ", "estas ", "majo", "?"]) ?></p>
    <p><?php echo str_replace('ñ', 'gn', "la ñiña se baña en la playa mientras come piña")  ?></p>
    <p><?php echo "Random array: [" . join(' , ', randomArray(5, 1, 10))."]" ?></p>
    <p><?php echo "Cifrated 'HOLA AMO' : " . cifrate("HOLA AMO")?></p>
</body>

</html>