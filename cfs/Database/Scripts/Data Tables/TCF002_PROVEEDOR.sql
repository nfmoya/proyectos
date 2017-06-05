
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF002_PROVEEDOR'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF002_PROVEEDOR" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF002_PROVEEDOR' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF002_PROVEEDOR

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF002_PROVEEDOR'"
	SELECT syb_quit()
END
go

create table TCF002_PROVEEDOR (
	CD_PROVEEDOR                    char(6)                          not null  ,
	NB_PROVEEDOR                    varchar(30)                      not null  ,
	NB_PROVEEDORCORTO               varchar(12)                          null  ,
	NU_CUIT                         int                              not null  ,
	NB_ATRIBUTOPROV1                varchar(30)                          null  ,
	NB_ATRIBUTOPROV2                varchar(30)                          null  ,
	NB_ATRIBUTOPROV3                varchar(30)                          null  ,
	NB_ATRIBUTOPROV4                varchar(30)                          null  ,
	NB_ATRIBUTOPROV5                varchar(30)                          null  ,
	ST_HABILITADO                   char(1)                         DEFAULT  "S" 
  not null  ,
	FH_ALTA                         datetime                        DEFAULT  GETDATE() 
  not null  ,
	NB_USUARIOALTA                  char(8)                          not null  ,
	FH_MODIFICACION                 datetime                             null  ,
	NB_USUARIOMODIF                 char(8)                              null  ,
		CONSTRAINT ICF002P1_PROVEEDOR PRIMARY KEY CLUSTERED ( CD_PROVEEDOR )  on 'default' 
)
lock allpages
 on 'default'
go 


setuser
go 

