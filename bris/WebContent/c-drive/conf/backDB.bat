
set dt=%date:~7,2%-%date:~4,2%-%date:~10,4%_%time:~0,2%_%time:~3,2%_%time:~6,2%
set det=%date:~7,2%-%date:~4,2%-%date:~10,4%
echo Backup database.....
echo %dt%
C:
cd C:\Program Files\MySQL\MySQL Server 5.7\bin 

echo Creating dir if not exist
if not exist "C:\bls\databasebackup" mkdir C:\bls\databasebackup

setlocal
set LogPath=C:\bls\log\
set LogFileExt=.log
set LogFileName=Daily Backup%LogFileExt%
::use set MyLogFile=%date:~4% instead to remove the day of the week
::[COLOR="DarkRed"]set MyLogFile=%date%
::set MyLogFile=%MyLogFile:/=-%[/COLOR]
set MyLogFile=%MyLogFile%
set MyLogFile=%LogPath%%MyLogFile%%LogFileName%
::Note that the quotes are REQUIRED around %MyLogFIle% in case it contains a space
IF NOT Exist "%LogPath%" mkdir %LogPath%
If NOT Exist "%MyLogFile%" goto:noseparator
Echo.>>"%MyLogFile%"
Echo.========================================================================ITALIAWorks========================================>>"%MyLogFile%"
:noseparator
::echo.%Date% >>"%MyLogFile%"
::echo.%Time% >>"%MyLogFile%"
echo.%Date% %Time% Preparing for backup... >>"%MyLogFile%"
echo.%Date% %Time% starting backup... >>"%MyLogFile%"


mysqldump.exe -e -uroot -poctober181986* -hlocalhost bls > C:\bris\databasebackup\bls_today.sql

echo.%Date% %Time% Preparing to save the backup >>"%MyLogFile%"
echo.%Date% %Time% Location: C:\bls\databasebackup >>"%MyLogFile%"
echo.%Date% %Time% Backup has been successfully proccessed with the file name of 'bls_%det%.sql' >>"%MyLogFile%"
echo.%Date% %Time% Location: C:\bris\databasebackup\bls_%det%.sql >>"%MyLogFile%"


echo.%Date% %Time% Ended... >>"%MyLogFile%"