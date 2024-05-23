#cs -----
  Check the commandline arguements (remember that the first is the number of arguements). So check if there
  are two. If so, then we are dealing with a letter and X amount of keys. If it's just one arguement then, its just
  X amount keys.
#ce -----
Global $hasLetter = False;
Global $keyString;
Const $EMPTY_STRING = "";
Global $combination ;

If $CmdLine[0] = 2 Then
   Global $keyString = $CmdLine[1];
   Global $letter = $CmdLine[2];
   Global $hasLetter = True;
Else
   Global $keyString = $CmdLine[1];
EndIf

Global $keyArray = StringSplit($keyString, " ");

For $i = 1 To $keyArray[0] ; Loop through the array returned by StringSplit to display the individual values.
   $combination &= $keyArray[$i];
   Next

If $hasLetter = True Then
   $combination &= "{"&$letter&"}";
EndIf

ConsoleWrite($combination);
Send($combination);
;ControlSend($windowTitle, $EMPTY_STRING, "[INSTANCE:1]", $combination)

Exit(1);