$title = "Open"
WinWaitActive($title, "", 10)

$text = $CmdLine[1]

While (ControlCommand($title, "", "Edit1", "IsVisible")  == false) Or (ControlCommand($title, "", "Edit1", "IsEnabled", "") == false)
    Sleep(5000)
WEnd
ControlClick($title, "", "Edit1")
ControlSetText($title, "", "Edit1", $text)
ControlClick($title, "", "Button1")

WinWaitClose($title, "", 10)