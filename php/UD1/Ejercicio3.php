<!DOCTYPE html>
<html lang="en">
<?php
    $cities =  array_unique([
        "Barcelona",
        "London",
        "Tokio",
        "New York",
        "Tokio",
        "Paris",
        "Barcelona",
        "Dublin",
        "Zurich",
        "Amsterdam"
    ]);

?>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ejercicio 3</title>
</head>
<style>
    body {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    h1 {
        padding: 10px;
    }

    ul {
        display: flex;
        flex-wrap: wrap;
    }

    li {
        list-style-type: none;
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
    <h1>TOP CITIES IN THE WORLD</h1>
    <ul>
        <?php
        foreach ($cities as $city)
            echo "<li>${city}</li>";
        ?>
    </ul>

    <div id="navbar">
        <a href="./Ejercicio2.php" class="btn">2</a>
        <b>3</b>
        <a href="./Ejercicio4.php" class="btn">4</a>
    </div>
</body>

</html>