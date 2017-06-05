
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF003_PERIODOFACT'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF003_PERIODOFACT" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF003_PERIODOFACT' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF003_PERIODOFACT

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF003_PERIODOFACT'"
	SELECT syb_quit()
END
go

create table TCF003_PERIODOFACT (
	CD_PROVEEDOR                    char(6)                          not null  ,
	CD_PERIODOFACT                  char(6)                          not null  ,
	NB_PERIODOFACT                  varchar(20)                      not null  ,
	CD_PERFACTALT                   varchar(20)                          null  ,
	FH_DESDE                        date                             not null  ,
	FH_HASTA                        date                             not null  ,
	ST_ESTADO                       char(3)                         DEFAULT  "PEN" 
  not null  ,
	FH_ALTA                         datetime                        DEFAULT  GETDATE() 
  not null  ,
	NB_USUARIOALTA                  char(8)                          not null  ,
	FH_MODIFICACION                 datetime                             null  ,
	NB_USUARIOMODIF                 char(8)                              null  ,
		CONSTRAINT ICF003P1_PERIODOFACT PRIMARY KEY CLUSTERED ( CD_PROVEEDOR, CD_PERIODOFACT )  on 'default' 
)
lock allpages
 on 'default'
go 


setuser
go 

-----------------------------------------------------------------------------
-- Dependent DDL for Object(s)
-----------------------------------------------------------------------------
alter table CFACSERV.dbo.TCF003_PERIODOFACT
add constraint R0020031 FOREIGN KEY (CD_PROVEEDOR) REFERENCES CFACSERV.dbo.TCF002_PROVEEDOR(CD_PROVEEDOR)
go


