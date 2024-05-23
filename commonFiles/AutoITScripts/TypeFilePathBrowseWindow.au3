$title = "Choose File to Upload"
WinWaitActive($title, "", 10)

$text = $CmdLine[1]

ControlSend($title, "", "", $text & "{ENTER}")