
<?php 
require_once("class.phpmailer.php");
require_once("../db/config.php");

/**
 * Send an email
 */
function sendMail($receiver,$subject,$body)
{
    $mail = new PHPMailer();
    $mail->IsSMTP();
    $mail->SMTPAuth = true;
    $mail->Host = MAIL_HOST;
    $mail->Port = MAIL_PORT;
    $mail->SMTPSecure = 'tls';
    $mail->Username = MAIL_USERNAME;
    $mail->Password = MAIL_PASSWORD;
    $mail->From = MAIL_USERNAME;
    $mail->FromName = FORUM_TITLE;
    $mail->AddAddress($receiver, "client");
    $mail->Subject = $subject;
    $mail->IsHTML(true);
    $mail->msgHTML($body);
    print_r($mail);
}

?>