cd C:\Program Files\MySQL\MySQL Server 5.7\bin 

echo Creating dir if not exist
if not exist "C:\bls\databasebackup" mkdir C:\bls\databasebackup

mysqldump.exe -e -uroot -poctober181986* -hlocalhost bls > C:\bris\databasebackup\bls.sql