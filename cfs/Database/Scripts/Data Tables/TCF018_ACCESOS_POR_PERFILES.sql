
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF018_ACCESOS_POR_PERFILES'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF018_ACCESOS_POR_PERFILES" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF018_ACCESOS_POR_PERFILES' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF018_ACCESOS_POR_PERFILES

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF018_ACCESOS_POR_PERFILES'"
	SELECT syb_quit()
END
go

create table TCF018_ACCESOS_POR_PERFILES (
	ID_ACCESO                       varchar(20)                      not null  ,
	ID_PERFIL                       numeric(18,0)                    not null  ,
	F_ALTA                          smalldatetime                   DEFAULT  getDate() 
      null  ,
 PRIMARY KEY CLUSTERED ( ID_ACCESO, ID_PERFIL )  on 'default' 
)
lock allpages
with identity_gap = 1 on 'default'
go 


setuser
go 
