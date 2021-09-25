<?php
    function navbar($start,$current,$end,$start_url,$end_url){
        $html = '<div id="navbar" ><a href="'.$start_url.'" class="btn">'.$start.'</a><b>'.$current.'</b><a href="'.$end_url.'" class="btn">'.$end.'</a></div>';
        return $html;
    }
?>
