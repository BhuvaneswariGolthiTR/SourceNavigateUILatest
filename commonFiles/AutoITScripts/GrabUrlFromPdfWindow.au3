WinWaitActive("[CLASS:IEFrame]", "", 10)
$title = WinGetTitle("[CLASS:IEFrame]")

$text = WinGetText($title)
$strings = StringSplit($text, @CRLF)

For $i=1 To $strings[0]
    If StringRegExp($strings[$i],"^http.*artifactContent.*$") Then
        ConsoleWrite($strings[$i])
    EndIf
Next

WinClose($title)