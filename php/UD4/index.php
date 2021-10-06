<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ejercicios tanda 1</title>
</head>
<style>
    body {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    .menu{
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        justify-content: center;
        align-items: center;
    }

    .menu a {
        background: #f1f1f1;
        padding: 10px;

        max-width: 500px;
        border-radius: 6px;
        margin: 5px 5px;
        text-decoration: none;
        color: dodgerblue;
    }

    .menu a:visited {
        color: #a000ff
    }
 
</style>
<body>
    <h1>Ejercicios tanda 1</h1>
    <div class="menu"> 
    <?php

        $dir = "./";
        $files = scandir($dir);

        foreach ($files as $file) {
            if (is_file($dir . $file) and strpos($file,".php") !== false and $file != "index.php") {
                echo "<a href='$dir$file'>$file</a><br>";
            }
        }
    ?>
    </div>
</body>
</html>