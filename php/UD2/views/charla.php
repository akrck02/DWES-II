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
<style>
    body {
        position: absolute;
        margin: 0;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        width:100%;
        height:100%;
    }

    iframe {
        position: relative;
        border:none;
        width:100%;
        height:calc(100% - 140px);
    }

    form{
        display: block;
    }

    table {
        box-shadow: 0px -1px 4px rgba(0,0,0,.15);
        width:100%;
        padding:10px;
        height:80px;
    }

    input[type=text]{
        padding:7px;
        border-radius: 100px;
        border:2px solid #f1f1f1;
        background:#f1f1f1;
        max-width: 400px;
        width:100%;
    }

    input[type=submit]{
        background: dodgerblue;
        padding: 10px;
        color: white;
        border: none;
        border-radius: 60px;
        box-shadow: 0px 2px 4px rgba(0,0,0,.15);
        transform: scale(1.2);
        cursor: pointer;
    }

    header {
        position: relative;
        height: 20px;
        padding: 10px;
        font-weight: bold;
        background: #fafafa;
        box-shadow: 0px 2px 4px rgba(0,0,0,.15);
        z-index: 9;
    }
</style>
<body>
    <header>CHARLAPP</header>
    <iframe src="./contenido_charla.php"></iframe>
    <div>
    <form action="" method="post">
        <table>
            <tr>
                <td>Usuario: </td>
                <td><b><?php echo $user; ?></b></td>
            </tr>
            <tr>
                <td>Mensaje: </td>
                <td><input type="text" name="message" id=""></td>
                <td><input type="submit" value="Enviar" name="send"></td>
            </tr>
        </table>
    </form>
    </div>
</body>
</html>