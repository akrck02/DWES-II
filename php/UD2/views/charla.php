<?php 

    define("CHAT","../data/chat.txt");
    define("SEPARATOR","&#!");

    function addLineToFile($url = "./", $line = "")
    {

        if (!is_dir(dirname($url))) {
            mkdir(dirname($url), 0777, true);
        }

        $file = fopen($url, "a");
        if ($file != false) {
            fwrite($file, $line . " ");
            fwrite($file, "\n");
            fclose($file);
            return true;
        } else return false;

        return true;
    }


    $user = isset($_REQUEST['user']) ? $_REQUEST['user'] : null;
    $message = isset($_REQUEST['message']) ? $_REQUEST['message'] : null;

    if ($user != null && $message != null) {
        addLineToFile(CHAT, $user.SEPARATOR.$message);
    }

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chat</title>
</head>
<body>
    <iframe style='width:90%; height:500px;'src="./contenido_charla.php"></iframe>
    <form action="" method="post">
        <table>
            <tr>
                <td>Usuario: </td>
                <td><b><?php echo $user; ?></b></td>
            </tr>
            <tr>
                <td>Mensaje: </td>
                <td><input type="text" name="message" id=""></td>
            </tr>
            <tr>
                <td><input type="submit" value="Enviar" name="send"></td>
            </tr>
        </table>
    </form>
</body>
</html>