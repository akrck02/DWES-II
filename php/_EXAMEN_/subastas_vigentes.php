<?php 
    require_once "header.php";
    $items = getNotExpiredItems($conn);
    $error = false;
    $success = false;

    if(!isset($_SESSION['bids']))
    $_SESSION['bids'] = [];

    if(isset($_POST['newBid'])){
       $value = $_POST['bid'];
       $item = $_POST['item'];

       if(is_numeric($value)){

            $lastBid = getItemWinnerBid($conn,$item);

            if(isset($_SESSION['bids'][$item]) and $lastBid['cantidad'] < $_SESSION['bids'][$item])
                $lastBid = $_SESSION['bids'][$item];
            else 
                $lastBid = $lastBid['cantidad'];
            
            if( $lastBid < $value){
                $_SESSION['bids'][$item] = $value; 
            }else $error = "La cantidad pujada debe ser mayor a la de la última puja";
       }else $error = "Valor incorrecto para la puja";
    }

    if(isset($_POST['saveBids'])){

     if(isset($_POST['save'])){
        foreach ($_SESSION['bids'] as $key => $value) {
            if(in_array($key,$_POST['save'])){
               insertBid($conn,$_POST['save'],$value);
            }
        }
        session_unset();
        $_SESSION['bids'] = [];

        $success = "Grabadas en la base de datos";
     } else $error = "seleccione alguna puja para guardar";
    }
    
?>

<h1>Ultimas subastas vencidas</h1>
<p class="error"><?php if($error !== false) echo $error ?></p>
<p style="color:forestgreen"><?php if($success !== false) echo $success ?></p>
<table>
    <tr>
        <th>ITEM</th>
        <th>ULTIMA PUJA</th>
        <th>QUEDAN</th>
        <th>PUJA FICTICIA</th>
    </tr>

    <?php 
        foreach ($items as $item) {

            $lastBid = getItemWinnerBid($conn,$item['id']);
            $lastBidExists = ($lastBid !== false);
            if($lastBidExists)
                $lastBid = $lastBid['cantidad'].CURRENCY;
            else $lastBid = "Sin pujas";


            //comparing dates

            $today = new DateTime();
            $other = new DateTime($item['fechafin']);
            $diff = $today -> diff($other); 

            $yearDiff = $diff -> y;
            $monthDiff = $diff -> m;
            $dayDiff =  $diff -> d;

            $diffText = "";
            if($yearDiff > 0)
                $diffText .= $yearDiff.(($yearDiff == 1) ? " Año " : " Años ");
            
            if($monthDiff > 0)
                $diffText .= $monthDiff.(($monthDiff == 1) ? " Mes " : " Meses ");
            
            if($dayDiff > 0)
                $diffText .= $dayDiff.(($dayDiff == 1) ? " Dia " : " Dias ");
                
            echo  "<tr>";
            
            echo "<td>".$item['nombre']."</td>";            
            echo "<td>".$lastBid."</td>";            
            echo "<td>".$diffText."</td>";            
            
            echo "<td>";
                if($lastBidExists){
                    echo "<form action='".$_SERVER['PHP_SELF']."' method='post'>";
                    echo "<input type='hidden' name='item' value='".$item['id']."'>";

                    
                    if(isset($_SESSION['bids'][$item['id']]))
                        echo "<input type='text' name='bid' value='".$_SESSION['bids'][$item['id']]."'> &nbsp;";
                    else
                        echo "<input type='text' name='bid'> &nbsp;";

                    echo "<input type='submit' name='newBid' value='Nueva puja'>";
                    echo "</form>";
                }
            echo "</td>";

            echo "</tr>";
        }    
    ?>
  
</table>

<hr>
<form action="<?=$_SERVER['PHP_SELF']?>" method="post">
<ul>
    <?php 
        foreach ($_SESSION['bids'] as $item => $value) {
            $itemName = getItem($conn,$item)['nombre'];
          echo "<li>$itemName, $value".CURRENCY." <input type='checkbox' name='save[]' value='$item'></li>";
        }
    ?>
</ul>
<input type="submit" name='saveBids' value="Grabar pujas marcadas" >
</form>


<?php require_once "footer.php"; ?>
