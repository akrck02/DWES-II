<?php 
    //Database info
    date_default_timezone_set("Europe/Andorra");
    define("DB_HOST" , "localhost");
    define("DB_USER" , "root");
    define("DB_PASSWORD" , "");
    define("DB" , "subastas");

    //Routes 
    define("BASE_ROUTE", "http://" . $_SERVER['SERVER_NAME']."/DWES_II/php/UD5/");
    define("IMAGES_ROUTE",BASE_ROUTE."images/");

    //Mail info
    define("MAIL_HOST","smtp.gmail.com");
    define("MAIL_PORT",465);
    define("MAIL_USERNAME","phpdewspro@gmail.com");
    define("MAIL_PASSWORD","\$php1234");

    //mix
    define("FORUM_TITLE","Subastas.com");
    define("CURRENCY","€");


