:: locations
@echo on
@SET HH=%time:~0,2%
@if "%HH:~0,1%"==" " SET HH=0%HH:~1,1%
@SET TODAYS_DATETIME=%date:~10,4%-%date:~4,2%-%date:~7,2%-%HH%%time:~3,2%
@SET TEST_SOURCE_DIR=\\Cobalttesttools\cobalttesttools$\QED\Codes-CodesBench
@SET TEST_RESULTS_DIR=\\Cobalttesttools\cobalttesttools$\QED\Codes-CodesBench\TEX_Results\%TODAYS_DATETIME%\Codes-CodesBench
@SET TEST_EXECUTION_DIR=D:\Temp\Cobalt QED Testing\Codes-CodesBench
@SET QRT_CONSOLE_DIR=\\cobalttesttools\cobalttesttools$\QED\QRTConsole

:: configuration and build files to be used
@SET QED_TEST_CONFIG=QedTestConfig.xml
@SET ANT_BUILD_FILE=RegressionTestAntScript.xml

:: delete the temp test execution location
rmdir /S /Q "%TEST_EXECUTION_DIR%"

:: make the temp location
if not exist "%TEST_EXECUTION_DIR%"\ mkdir "%TEST_EXECUTION_DIR%"

:: copy regression tests, ant builds, external libraries, and resources locally
xcopy /y "%TEST_SOURCE_DIR%"\* "%TEST_EXECUTION_DIR%" /s /i

:: execute the tests
cd /d "%TEST_EXECUTION_DIR%"
call ant -buildfile "%TEST_EXECUTION_DIR%"\ant\\"%ANT_BUILD_FILE%" -Dlog.file= "RegressionTestResults.xml"


:: create test results datetime directory for results
if not exist "%TEST_RESULTS_DIR%"\ mkdir "%TEST_RESULTS_DIR%"

:: copy test results and configuration to QED NAS
xcopy /y "%TEST_EXECUTION_DIR%"\testResults\* "%TEST_RESULTS_DIR%" /s /i
copy /y "%TEST_SOURCE_DIR%"\commonFiles\properties\QedTestConfig.xml "%TEST_RESULTS_DIR%"

:: copy down the qrtconsole.exe
copy /y "%QRT_CONSOLE_DIR%\*.*" "%TEST_EXECUTION_DIR%\"

:: execute the test results load into QRT
"%TEST_EXECUTION_DIR%"\QRTConsole.exe "%TEST_RESULTS_DIR%"\RegressionTestResults.xml "%TEST_RESULTS_DIR%"\QedTestConfig.xml 2j
