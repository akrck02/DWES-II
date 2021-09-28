<?php 

    define("CHAT","../data/chat.txt");
    define("SEPARATOR","&#!");
    define("BANNED_WORDS_FILE","../data/banned.txt");
    define("EMOJI_TEMPLATE", "<img style='height:25px' src='?' alt='*'>");

    define("EMOJIS", [
        ":)",
        ":("
    ]);

    define("EMOJI_IMG", [
        str_replace(["?","*"],["../images/smile.png",":)"],EMOJI_TEMPLATE),
        str_replace(["?","*"],["../images/smile.png",":)"],EMOJI_TEMPLATE)            
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
    <script type="text/javascript">
        window.onload = function() {
                window.scrollTo(0, document.body.scrollHeight);
        }
    </script>

</head>
<body>
    <?php 
        foreach ($chat as $message) {
            echo $message["user"].": ".$message["message"]."<br>";
        }
    ?>
</body>
</html>