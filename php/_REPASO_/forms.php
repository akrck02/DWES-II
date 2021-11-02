<?php

//  $select = $_POST['select'];
//  $check = $_POST['check'];
//  $radio = $_POST['radio'];


    # Upload an image
    if(isset($_POST['submit'])){
            if(isset($_FILES['file'])){
                $file = $_FILES['file'];
                if($file['size'] > 0){
                    if (move_uploaded_file($file['tmp_name'], "./".$file['name'])) {
                       echo "<h1 style='color:green'>Done.</h1>";
                    }
                }else $uploadError = "No se pudo subir la imagen";
            }
    } else $uploadError = "file does not exist";
    
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>

<body>
    <form action="<?= $_SERVER['PHP_SELF'] ?>" enctype="multipart/form-data" method="post">

        <select name="select" id="">
            <option value="">1</option>
            <option value="">2</option>
        </select>

        <input type="checkbox" name="check[]" value="0" id="">
        <input type="checkbox" name="check[]" value="1" id="">

        <input type="radio" name="radio" id="">
        <input type="radio" name="radio" id="">

        <input type="file" name="file" id="">
        <input type="submit" value="Click" name='submit'>
    </form>
</body>

</html>