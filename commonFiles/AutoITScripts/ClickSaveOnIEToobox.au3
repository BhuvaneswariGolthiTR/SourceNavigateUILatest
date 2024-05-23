$title = "Toolbox - Internet Explorer"
WinWaitActive($title, "", 10)

Sleep(1000)

Send("{F6}")
Sleep(250)
Send("{TAB}")
Sleep(250)
Send("{ENTER}")

WinWaitClose($title, "", 10)