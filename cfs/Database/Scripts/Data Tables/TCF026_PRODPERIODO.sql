
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF026_PRODPERIODO'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF026_PRODPERIODO" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF026_PRODPERIODO' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF026_PRODPERIODO

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF026_PRODPERIODO'"
	SELECT syb_quit()
END
go

create table TCF026_PRODPERIODO (
	CD_PROVEEDOR                    char(6)                          not null  ,
	CD_PRODUCTO                     varchar(12)                      not null  ,
	FH_DESDE                        date                             not null  ,
	FH_HASTA                        date                             not null  ,
	ST_HABILITADO                   char(1)                          not null  ,
	FH_ALTA                         datetime                        DEFAULT  GETDATE() not null  ,
	NB_USUARIOALTA                  char(8)                          not null  ,
	FH_MODIFICACION                 datetime                             null  ,
	NB_USUARIOMODIF                 char(8)                              null  ,
		CONSTRAINT ICF022P1_PRECIOPROD PRIMARY KEY CLUSTERED ( CD_PROVEEDOR, CD_PRODUCTO, FH_DESDE )  on 'default' 
)
lock allpages
 on 'default'
go 


setuser
go 

-----------------------------------------------------------------------------
-- Dependent DDL for Object(s)
-----------------------------------------------------------------------------
alter table CFACSERV.dbo.TCF026_PRODPERIODO
add constraint R0220062 FOREIGN KEY (CD_PROVEEDOR,CD_PRODUCTO) REFERENCES CFACSERV.dbo.TCF005_PRODUCTO(CD_PROVEEDOR,CD_PRODUCTO)
go


