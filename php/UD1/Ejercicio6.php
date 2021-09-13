<?php

function showTimetable($dayArray, $startTime, $endTime, $interval)
{

    # Convert the start and end hour to minutes
    $start = explode(":", $startTime);
    $start = $start[0] * 60 + $start[1];

    $end = explode(":", $endTime);
    $end = $end[0] * 60 + $end[1];

    $interval = (int) $interval;

    # Draw the table
    echo "<table>";

    # Create the table header
    echo "<tr>";
    echo "<th>Hora</th>";
    foreach ($dayArray as $day) {
        echo "<th>$day</th>";
    }
    echo "</tr>";

    # Create the table body
    for ($i = $start; $i < $end; $i += $interval) {

        # Format the time
        $hour = floor($i / 60);
        if ($hour < 10)
            $hour = "0" . $hour;

        $minutes = $i % 60;

        if ($minutes < 10)
            $minutes = "0" . $minutes;


        # Draw the row
        echo "<tr>";
        echo "<td>" . $hour . ":" . $minutes . "</td>";
        foreach ($dayArray as $day)
            echo "<td></td>";

        echo "</tr>";
    }

    echo "</table>";
}

?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ejercicio 6</title>
</head>
<style>
    body {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    th,td {
        padding: 10px;
    }

    th {
        
        background: #202020;
        color: #fafafa;
    }

    td {
        background : #fafafa;
        border: 2px solid #f1f1f1;
    }
    #navbar {
        position: fixed;
        display: flex;
        justify-content: space-between;
        align-items: center;
        box-sizing: border-box;

        width: 100%;
        height: 60px;

        bottom: 10px;
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
        color: #202020;
        font-weight: bold;
    }
</style>

<body>
    <h1>CALENDAR APP</h1>

    <?php
    # Create the days array
    $days = array("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo");

    # Show the timetable
    showTimetable($days, "8:10", "10:15", 24);
    ?>

    <div id="navbar">
        <a href="./Ejercicio5.php" class="btn">5</a>
        <a href="./Ejercicio7.php" class="btn">7</a>
    </div>


</body>

</html>