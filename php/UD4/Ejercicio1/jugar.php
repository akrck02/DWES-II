<?php
require_once "./constants.php";
define("ERROR_FILE", "../data/errors.txt");
session_start();

function add_line_to_file($url, $content)
{

    if (!is_dir(dirname($url))) {
        mkdir(dirname($url), 0777, true);
    }

    $file = fopen($url, "a+");
    if ($file != false) {
        fwrite($file, $content . " ");
        fwrite($file, "\n");
        fclose($file);
        return true;
    } else return false;

    return true;
}


// Checking possible inputs
$_SESSION['QUESTIONS'] = QUESTIONS;
$categories = isset($_SESSION['categories']) ? $_SESSION['categories'] : [];
$check = isset($_POST['check']) ? $_POST['check'] : false;

if ($check) {
    $needle = "ENVIAR RESPUESTA";
    $category = trim(substr($check, strrpos($check, $needle) + strlen($needle)));
    $question = isset($_POST['question_' . $category]) ? $_POST['question_' . $category] : false;
    $response = isset($_POST['response_' . $category]) ? $_POST['response_' . $category] : false;
    $valid_response = QUESTIONS[$category][$question][1];
    $save = $categories[$category]['save_errors'];

    if($question !== false and $response !== false){
        if ($response == $valid_response) {
            $categories[$category]['success']++;
            //print_r($categories[$category]);
        } else {
            if ($save) {
                $question_predicate = QUESTIONS[$category][$question][0];
                add_line_to_file(ERROR_FILE, $question_predicate . " => ERROR: " . $response);
            }
        }
        $categories[$category]['question_index']++;
    }
}

?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<style>
    <?php include_once "../styles/Ejercicio1.css"; ?>th,
    td {
        padding: 10px;
    }
</style>

<body>
    <form action="<?php echo $_SERVER['PHP_SELF'] ?>" method="post">
        <table>
            <?php
            $unfinished = 0;

            echo "  
            <tr>
                <th>TIPO</th>
                <th>Nº PREGUNTA</th>
                <th>PREGUNTA</th>
                <th>RESPUESTAS</th>
                <th></th>
            <th></th>
            </tr>
            ";

            foreach ($categories as $category => $data) {
                $possible_questions = QUESTIONS[$category];
                $max = count($possible_questions) - 1 < $data['question_index'] ? count($possible_questions) - 1 : $data['question_index'];

                if (!isset($data['answers'])) {
                    shuffle($possible_questions);
                    $categories[$category]['answers'] = $possible_questions;
                }

                $question_index = $max;
                if ($data['question_index'] <= $question_index) {
                    $current_question = $possible_questions[$question_index];
                    $question = $current_question[0];
                    $valid_response = $current_question[1];

                    $current_question = array_slice($current_question, 1);
                    shuffle($current_question);

                    echo "<tr>";
                    echo "<td>$category<input type='hidden' name='question_$category' value='$question_index'></td>";
                    echo "<td>" . ($question_index + 1) . "</td>";
                    echo "<td>" . $question . "</td>";
                    echo "<td>";
                    foreach ($current_question as $response) {
                        echo "<label><input type='radio' value='$response' name='response_$category'>$response</label><br>";
                    }
                    echo "</td>";
                    echo "<td><input type='submit' name='check' value='ENVIAR RESPUESTA $category'></td>";
                    echo "<td><p>" . $data['success'] . " Aciertos</p></td>";

                    echo "</tr>";
                    $unfinished++;
                }
            }
            $_SESSION['categories'] = $categories;
            ?>
        </table>
    </form>
    <?php 
        if ($unfinished == 0) {

            echo "<h1>Enhorabuena, has terminado el juego!!!</h1>";
            echo "<a href='".ERROR_FILE."'>Ver tus fallos</a>";
        }
    ?>
</body>
</html>