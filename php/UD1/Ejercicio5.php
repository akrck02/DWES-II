<?php

/** Get the database of favorite movies */
function createDatabase()
{
    return array(
        "Mikel" => ["The lightning", "Your name", "The lord of the rings"],
        "Jhon" => ["The lord of the rings", "Harry Potter"],
        "Mary" => ["Harry Potter", "Pokemon 2000", "Titanic"],
        "Jackson" => ["Pokemon 2000", "Titanic", "The lord of the rings"],
        "Claire" => ["The lightning", "Avengers", "Iron man"],
        "Nathalie" => ["Mad max"]
    );
}

/** Return how many people has a movie as favorite */
function howManyHasFavorite($movie)
{
    $database = createDatabase();
    $counter = 0;

    foreach ($database as $movies) {
        foreach ($movies as $temp) {
            if ($temp == $movie) {
                $counter++;
                break;
            }
        }
    }

    return $counter;
}

/** Show two random favorite movies per persons */
function showTwoRandomMoviesPerPerson()
{
    $database = createDatabase();
    foreach ($database as $key => $movies) {

        $max = 2;
        if ($max > count($movies))
            $max = count($movies);

        $written = [];

        for ($i = 0; $i < $max; $i++) {

            $random = rand(0, count($movies) - 1);

            while (isset($written[$movies[$random]])) {
                $random = rand(0, count($movies) - 1);
            }

            $written[$movies[$random]] = $movies[$random];
        }
        echo "<b>${key}:</b> " . join(', ', $written) . "<br>";
    }
}
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ejercicio 5</title>
</head>
<style>
    body {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
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
    <h1>FAVORITE MOVIES</h1>
    <div>
        <b>
        <?php                   
            $favoriteMovie = "The lord of the rings";

            if(isset($_GET['peli']))
                $favoriteMovie = $_GET['peli'];

            echo "People who love '${favoriteMovie}' :";        
        ?> 
        </b>
        <?php echo howManyHasFavorite($favoriteMovie) ?><br><br>
    </div>

    
    <div><?php showTwoRandomMoviesPerPerson(); ?></div>
    <div id="navbar">
        <a href="./Ejercicio4.php" class="btn">4</a>
        <b>5</b>
        <a href="./Ejercicio6.php" class="btn">6</a>
    </div>

   
</body>
</html>