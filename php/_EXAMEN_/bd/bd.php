<?php require_once "config.php";

function connectDB()
{
    $conn = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB);
    mysqli_set_charset($conn, "UTF8");
    return $conn;
}

function  getExpiredItems($conn)
{
    $items = [];
    $sql = "select * from items where TIMESTAMPDIFF(SECOND, fechafin, now()) > 1";

    $statement = $conn->prepare($sql);

    if ($statement === false)
        return $items;

    $prepared = true;
    $executed = $statement->execute();

    if ($prepared and $executed) {

        $result = $statement->get_result();
        while ($item = $result->fetch_assoc()) {
            $items[] = $item;
        }
    }

    $statement->close();
    return $items;
}

function  getNotExpiredItems($conn)
{
    $items = [];
    $sql = "select * from items where TIMESTAMPDIFF(SECOND, fechafin, now()) > 1";

    $statement = $conn->prepare($sql);

    if ($statement === false)
        return $items;

    $prepared = true;
    $executed = $statement->execute();

    if ($prepared and $executed) {

        $result = $statement->get_result();
        while ($item = $result->fetch_assoc()) {
            $items[] = $item;
        }
    }

    $statement->close();
    return $items;
}

function getCategory($conn, $id)
{
    $sql = "SELECT * FROM CATEGORIAS WHERE id=? ";
    $category = false;

    $statement = $conn->prepare($sql);

    if ($statement === false)
        return false;

    $prepared = $statement->bind_param("i", $id);
    $executed = $statement->execute();

    if ($prepared and $executed) {

        $result = $statement->get_result();
        if ($item = $result->fetch_assoc()) {
            $category = $item;
        }
    }

    $statement->close();
    return $category;
}

function getItemWinnerBid($conn, $item)
{
    $sql = "SELECT * FROM PUJAS WHERE id_item=? ORDER BY cantidad DESC ";
    $bids = false;

    $statement = $conn->prepare($sql);

    if ($statement === false)
        return false;

    $prepared = $statement->bind_param("i", $item);
    $executed = $statement->execute();

    if ($prepared and $executed) {

        $result = $statement->get_result();
        if ($item = $result->fetch_assoc()) {
            $bids = $item;
        }
    }

    $statement->close();
    return $bids;
}


function getUser($conn, $id)
{
    $sql = "SELECT * FROM usuarios WHERE id=?";

    $statement = $conn->prepare($sql);

    if ($statement === false)
        return false;

    $prepared = $statement->bind_param("i", $id);
    $executed = $statement->execute();

    $user = false;

    if ($prepared and $executed) {
        $user = $statement->get_result()->fetch_assoc();
    }

    $statement->close();
    return $user;
}


function getItem($conn, $id)
{
    $sql = "SELECT * FROM ITEMS WHERE id=?";

    $statement = $conn->prepare($sql);

    if ($statement === false)
        return false;

    $prepared = $statement->bind_param("i", $id);
    $executed = $statement->execute();

    $item = false;

    if ($prepared and $executed) {
        $item = $statement->get_result()->fetch_assoc();
    }

    $statement->close();
    return $item;
}

function insertBid($conn, $item, $value)
{

    $user = getRandomFalseUser($conn);
    $sql = "INSERT INTO pujas(id_item,id_user,cantidad,fecha) VALUES(?,?,?,now())";

    $statement = $conn->prepare($sql);

    if ($statement === false)
        return false;

    $prepared = $statement->bind_param("iid", intval($item), intval($user), doubleval($value));
    $executed = $statement->execute();
    $statement->close();


    return $prepared and $executed;
}


function getRandomFalseUser($conn){
    $sql = "SELECT * FROM USUARIOS WHERE falso=1";

    $users = [];
    $statement = $conn->prepare($sql);

    if ($statement === false)
        return false;

    $executed = $statement->execute();

    if ($executed) {
        $result = $statement->get_result();
        while($user = $result->fetch_assoc()){
            $users[] = $user['id'];
        }
    }

    $user = $users[array_rand($users,1)];
    $statement->close();
    return $user;

}
