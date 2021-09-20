<?php

    function body(){
        return "
            body {
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                height: 100vh;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }
        ";
    }

    function select(){
        return "
            select {
                padding: 5px;
                border-radius: 3px;
                border: 2px solid #e1e1e1;
                margin: 3px;
                min-width: 75px;
            }
        ";
    }

    function submit() {
        return "  
            input[type=submit] {
                background: #f1f1f1;
                padding: 10px;
                border: none;
                margin: 5px;
                box-shadow: 0px 2px 4px rgba(0, 0, 0, .15);
                border-radius: 6px;
            }
        ";
    }

    function fixed_navbar(){
        return "
            #navbar {
                position: fixed;
                display: flex;
                justify-content: space-between;
                align-items: center;
                box-sizing: border-box;
        
                width: 100%;
                height: 60px;
                
                bottom : 10px;
                left: 0;
                padding: 10px;
            }
        
            #navbar a {
                display: flex;
                justify-content: center;
                align-items: center;
                background: #f1f1f1;
        
                border-radius: 100%;
                width: 30px;
                height: 30px;
                padding: 10px;
                text-decoration: none;
                color : #202020;
                font-weight: bold;
            }
        ";
    }

    function label() {
        return "
            label{
                display:block;
                width: 100%;
                margin : 0;
            }
        ";
    }

    function table_data(){
        return "
            td { 
                padding: 20px
            }
        ";
    }

    function input_text() { 
        return "
            input[type=text]{
                padding: 5px;
                border-radius: 3px;
                border: 2px solid #e1e1e1;
            }
        ";

    }

    function error() {
        return "
            #error {
                display:block;
                color:red;
                text-align: center;
                width: 100%;
            }
        ";
    }

    function result() {
        return "
            #result {
                display:block;
                color:dodgerblue;
                text-align: center;
                width: 100%;
            }
        ";
    }