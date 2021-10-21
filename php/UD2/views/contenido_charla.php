<?php 

    define("CHAT","../data/chat.txt");
    define("SEPARATOR","&#!");
    define("BANNED_WORDS_FILE","../data/banned.txt");
    define("EMOJI_TEMPLATE", "<img style='height:25px' src='?'>");

    define("EMOJIS", [
        ":)",
        ":("
    ]);

    define("EMOJI_IMG", [
        str_replace("?","../images/smile.png",EMOJI_TEMPLATE),
        str_replace("?","../images/sad.png",EMOJI_TEMPLATE)            
    ]);

    function get_banned_words(){
        
        $banned_words = [];
        $file = fopen(BANNED_WORDS_FILE, "r");

        while (!feof($file)) {
            $line = fgets($file);
            $banned_words[] = trim($line);
        }
            
        fclose($file);
        return $banned_words;
    }

    function get_formatted_chat($url) {

        $banned_words = get_banned_words();
        $file = fopen($url, "r");
        $line = "";
        $chat = [];

        while (!feof($file)) {
    
            $line = fgets($file);
            $line = explode(SEPARATOR, $line);

            if(count($line) == 2) {
                $now = [
                    "user" => "<b>".$line[0]."</b>",
                    "message" => $line[1]
                ];

                $now["message"] = str_replace(EMOJIS,EMOJI_IMG, $now["message"]);
                foreach ($banned_words as $word) 
                    $now["message"] = str_replace($word, str_repeat("*",strlen($word)), $now["message"]);
                
                $chat[] = $now;
            }
        }
        fclose($file);
        return $chat;
    }

    $chat = get_formatted_chat(CHAT);
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="refresh" content="2">
    <title>Document</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .message {
            background: #fafafa;
            padding: 10px;
            border: 1px solid #f9f9f9;
            box-shadow: 0px 2px 4px rgba(0,0,0,.15);
            max-width: 300px;
            width:max-content;
            display: flex;
            border-radius: min(20px);
        }

            
    ::-webkit-scrollbar {
        width: 5px;
    }
    
    ::-webkit-scrollbar-thumb { 
        background: dodgerblue; 
        border-radius: 10px;
    }

    ::-webkit-scrollbar-thumb:hover {
        background: #f1f1f1; 
    }
    </style>
    <script type="text/javascript">
        window.onload = function() {
                window.scrollTo(0, document.body.scrollHeight);
        }
    </script>

</head>
<body>
    <?php 
        foreach ($chat as $message) 
            echo "<p class='message'>".$message["user"].": ".$message["message"]."</p>";
    ?>
</body>
</html>