; finish running PingID process
ProcessClose("PingId.exe")

; start new PingId process
Run("PingId.exe")
; wait for the PingID window to become active
$pingidwindow = WinWaitActive("[CLASS:GlassWndClass-GlassWindowClass-2]", "", 5)
; wait for 5 seconds to display the PingID window
Sleep(5000)

; move to pin code input:
Send("{TAB}{TAB}")
; enter pin to unblock PingId:
$pingIdUnblockPin = $CmdLine[1]
Sleep(1000)
Send($pingIdUnblockPin)
Send("{ENTER}")
; wait for pass code refreshed:
Sleep(3000)
; move to "Copy" button and click it:
Send("{TAB}{ENTER}")
Sleep(1000)
WinSetState($pingidwindow, "", @SW_MINIMIZE)

; now we've got PingID passcode copied to clipboard!