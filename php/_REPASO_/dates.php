<?php 

    /**
     * Set timezone my espanita (up)
     */
    date_default_timezone_set('Europe/Madrid');

    /**
     * php Datetime format chars
     */
    define("FORMATS", [
        "AM_PM"         => "A",
        "MILLISECONDS"  => "v",
        "SECONDS"       => "s",
        "MINUTES"       => "i",
        "HOUR_12"       => "h",
        "HOUR_24"       => "H",
        "DAY"           => "d",
        "DAY_TEXT"      => "l",
        "MONTH"         => "m",
        "MONTH_TEXT"    => "F",
        "YEAR"          => "Y"
    ]);

    /**
     * Get formated date
     * @param string $datestr   The  date string
     * @param string $format    The format to apply
     * @return string           The formated date
     */
    function getFormatedDate($datestr,$format){
        $date = new Datetime($datestr);
        return $date->format($format);
    }

    /**
     * Get difference between two dates
     * @param Datetime $date a date
     * @param Datetime $other other date
     */
    function getDiff($date , $other) {
        $diff = $date -> diff($other); 

        $yearDiff = $diff -> y;
        $monthDiff = $diff -> m;
        $DaysDiff =  $diff -> d;
        $hourDiff = $diff -> h;
    }
    

    /**
     * Comparing dates
     */
     $dateOne = new DateTime();
     $dateOther = new DateTime();

     $bigger = $dateOne > $dateOther;



?>