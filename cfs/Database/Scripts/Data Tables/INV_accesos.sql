
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.INV_accesos'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.INV_accesos" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'INV_accesos' AND u.name = 'dbo' AND o.type = 'U')
	drop table INV_accesos

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.INV_accesos'"
	SELECT syb_quit()
END
go

create table INV_accesos (
	id_acceso                       varchar(20)                      not null  ,
	d_acceso                        varchar(40)                      not null  ,
	id_menu                         numeric(18,0)                        null  ,
 PRIMARY KEY CLUSTERED ( id_acceso )  on 'default' 
)
lock allpages
 on 'default'
go 


setuser
go 

