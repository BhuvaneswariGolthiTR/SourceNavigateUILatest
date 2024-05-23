#cs -----
   include additional scripts as necessary
#ce -----
#include <StringConstants.au3>

#cs -----
   declare constants used for button text and alert title
#ce -----
Const $OK_BUTTON_TEXT = "OK"
Const $CANCEL_BUTTON_TEXT = "Cancel"
Const $EMPTY_STRING = ""
Const $TITLE = "Message from webpage"
Const $OK_BUTTON = "[CLASS:Button; TEXT:OK]"
Const $CANCEL_BUTTON = "[CLASS:Button; TEXT:Cancel]"
Const $TRUE_STRING = "true"
Const $SECONDS_TO_WAIT = 15

#cs -----
   grab the parameters alertExpected and alertAppeared
   and place them into their corresponding variables

   alertExpected is a string "true" or "false"
   alertExpectedMessage is a string expected message in the alert
#ce -----
If $CmdLine[0] = 2 Then
   Global $alertExpected = $CmdLine[1]
   Global $alertExpectedMessage = $CmdLine[2]
Else
   Global $alertExpected = $CmdLine[1]
EndIf

#cs -----
   we wait for a maximum of 5 seconds for the alert to appear
   can change later if needed
#ce -----
Global $alertAppeared = WinWaitActive($TITLE, $EMPTY_STRING, $SECONDS_TO_WAIT)

#cs -----
   if we expect the alert and it appears
	  grab the text of the alert and remove any text we don't need
	  verify it matches the text we expect
	  return a boolean alertIsValid that checks if the alertMessage is equal to the alertExpectedMessage

   if we don't expect the alert and it appears
	  return a boolean alertIsValid that is false

   if we expect the alert and it doesn't appear
	  return a boolean alertIsValid that is false

   if we don't expect the alert and it doesn't appear
	  return a boolean alertIsValid that is true
#ce -----
Global $alertIsValid = False

If $alertExpected = $TRUE_STRING Then
   If $alertAppeared = True Then
	  Global $alertMessage = WinGetText($TITLE)
	  If StringInStr($alertMessage, $OK_BUTTON_TEXT) Then
		 $alertMessage = StringReplace($alertMessage, $OK_BUTTON_TEXT, $EMPTY_STRING)
	  EndIf
	  If StringInStr($alertMessage, $CANCEL_BUTTON_TEXT) Then
		 $alertMessage = StringReplace($alertMessage, $CANCEL_BUTTON_TEXT, $EMPTY_STRING)
	  EndIf
	  $alertMessage = StringStripWS($alertMessage, $STR_STRIPLEADING + $STR_STRIPTRAILING)
	  $alertExpectedMessage = StringStripWS($alertExpectedMessage, $STR_STRIPLEADING + $STR_STRIPTRAILING)
	  $alertIsValid = $alertMessage == $alertExpectedMessage
	  ConsoleWrite($alertMessage)
	  ConsoleWrite($alertExpectedMessage)
	  ConsoleWrite($alertIsValid)
	  ControlClick($TITLE, $EMPTY_STRING, $CANCEL_BUTTON)
   EndIf
Else
   If $alertAppeared = False Then
	  $alertIsValid = True
   Else
	  Global $alertMessage = WinGetText($TITLE)
	  ConsoleWrite($alertMessage)
	  ControlClick($TITLE, $EMPTY_STRING, $CANCEL_BUTTON)
   EndIf
EndIf

Exit($alertIsValid)