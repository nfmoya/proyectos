
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF021_LOGS_BATCH'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF021_LOGS_BATCH" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF021_LOGS_BATCH' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF021_LOGS_BATCH

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF021_LOGS_BATCH'"
	SELECT syb_quit()
END
go

create table TCF021_LOGS_BATCH (
	descripcion                     varchar(600)                     not null  ,
	fecha                           datetime                         not null  ,
	origen                          varchar(100)                     not null  ,
	archivo                         varchar(100)                     not null   
)
lock allpages
 on 'default'
go 


setuser
go 

