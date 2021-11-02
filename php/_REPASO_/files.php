<?php
    // If file exists deleted
    if(file_exists("fino.php"))          
        unlink("fino.php");               

    // Create directory
    mkdir("fino");   

    // if dir exists remove
    if(is_dir("fino"));                 
        rmdir("fino");                 

    
    /**
     * add a line to file
     * @param string $url The directory
     * @param string $content The line to add
     * @return if the line was added
     */
    function addLineToFile($url = "./", ...$content)
    {

        if (!is_dir(dirname($url)))
            mkdir(dirname($url), 0777, true);

        $file = fopen($url, "a+");
        if ($file != false) {
            foreach ($content as $line) {
                if(is_array($line))
                    fwrite($file, implode(" ", $line) . " ");
                else
                    fwrite($file, $line . " ");
            }
            fwrite($file, "\n");
            fclose($file);
        } else return false;

        return true;
    }

    // read files 
    if(file_exists("./fino.txt"))
    {
        $f = fopen("./fino.txt", "r");
        while(!feof($f)) 
            $linea = fgets($f); 
        fclose($f);
    }

?>