<h2>Categorias</h2>
<?php
    echo "<ul>"; 
    if($conn) {
        $categories = getCategories($conn);
        $categoryIndex = isset($categoryIndex) ? $categoryIndex : ""; 

        if(count($categories))
            echo "<li><a ".($categoryIndex == "all" ? "class='selected'": "")." href='" . BASE_ROUTE . "/views/index.php?category=all'>Ver todas</a></li>";

        foreach ($categories as $category) {
            $class = ($category['id'] == $categoryIndex) ? "class='selected'" : "" ;

            echo "<li><a ".$class." href='" . BASE_ROUTE . "/views/index.php?category=".$category['id']."'>".$category['categoria']."</a></li>";
        }

    }
    echo "</ul>";
?>
