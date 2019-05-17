echo off
sqlcmd -U AutomationAdmin -P TestSavvyAdmin!4 -S timesheetcgi.database.windows.net -d timesheetdb -Q "SELECT SUM(Id) FROM dbo.Role" -o output.txt
set /p delExit=Press the ENTER key to exit...:
SELECT employeeid, PAY FROM (
//(SELECT salary FROM (left joint(left joint queue + timesheet) + employee)*
//(SELECT value FROM (left joint(left joint (left join queue + timesheet) + timesheetRow) + hour_project))
//AS PAY)