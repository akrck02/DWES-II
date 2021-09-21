<?php

function body()
{
    return "
        body {
            display: flex;
    
            flex-direction: column;
            justify-content: center;
            align-items: center;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        ";
}

function section()
{
    return "
        section {
            width: 100%;
            display: flex;
    
            flex-direction: column;
            justify-content: center;
            align-items: center;
            flex-wrap: wrap;
        }
        ";
}

function img()
{
    return "        
        img {
            transition: .25s;
            border-radius: 6px;
            margin: 10px;
            width: 200px;
            max-height: 200px;
            object-fit: cover;
            filter: grayscale(100);
        }


        tr:hover img {
            box-shadow: 0px 2px 4px rgba(0, 0, 0, .15);
            transform: scale(1.1);
            filter: grayscale(0);
        }
    ";
}

function div()
{
    return "
        div {
            display: flex;
            justify-content: center;
            align-items: center;
            max-width: 500px; 
            padding: 15px;
        }
    ";
}


function label()
{
    return "
        label {
            cursor: pointer;
        }
    ";
}

function submit() {
    return "  
        form{
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }

        input[type=submit] {
            border: 2px solid dodgerblue;
            background : #fff;
            color: dodgerblue;
            padding: 10px;
            margin: 5px;
            width: 150px;
            font-size: 1.5em;
            transition: .25s;
            cursor: pointer;
            border-radius: 6px;
        }

        input[type=submit]:hover {
            background: dodgerblue;
            color: #fff;

        }
    ";
}