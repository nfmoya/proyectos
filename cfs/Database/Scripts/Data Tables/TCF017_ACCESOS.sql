
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF017_ACCESOS'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF017_ACCESOS" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF017_ACCESOS' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF017_ACCESOS

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF017_ACCESOS'"
	SELECT syb_quit()
END
go

create table TCF017_ACCESOS (
	ID_ACCESO                       varchar(20)                      not null  ,
	D_ACCESO                        varchar(40)                      not null  ,
	ID_MENU                         numeric(18,0)                        null  ,
 PRIMARY KEY CLUSTERED ( ID_ACCESO )  on 'default' 
)
lock allpages
with identity_gap = 1 on 'default'
go 


setuser
go 
