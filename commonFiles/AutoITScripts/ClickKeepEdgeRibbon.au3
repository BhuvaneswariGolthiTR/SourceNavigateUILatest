$title = "Toolbox - Work - Microsoft Edge"

WinWaitActive($title, "", 10)

Sleep(1000)
Send("^j")
Sleep(1000)
Send("{TAB}")
Sleep(1000)
Send("{ENTER}")