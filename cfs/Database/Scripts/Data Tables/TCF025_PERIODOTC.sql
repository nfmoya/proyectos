
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF025_PERIODOTC'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF025_PERIODOTC" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF025_PERIODOTC' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF025_PERIODOTC

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF025_PERIODOTC'"
	SELECT syb_quit()
END
go

create table TCF025_PERIODOTC (
	CD_PROVEEDOR                    char(6)                          not null  ,
	CD_PERIODOFACT                  char(6)                          not null  ,	
	CD_MONEDA	                    char(3)                          not null  ,
	NU_DIAS                         int                              not null  ,
	ST_HABILITADO                   char(1)                          DEFAULT  "S",
	FH_ALTA                         datetime                         DEFAULT    GETDATE()  not null  ,
	NB_USUARIOALTA                  char(8)                          not null  ,
	FH_MODIFICACION                 datetime                             null  ,
	NB_USUARIOMODIF                 char(8)                              null  ,
		CONSTRAINT ICF021P1_PERIODOTC PRIMARY KEY CLUSTERED (CD_PROVEEDOR, CD_PERIODOFACT, CD_MONEDA)  on 'default' 
)
lock allpages
 on 'default'
go 


setuser
go 

-----------------------------------------------------------------------------
-- Dependent DDL for Object(s)
-----------------------------------------------------------------------------
alter table CFACSERV.dbo.TCF025_PERIODOTC
add constraint R0210041 FOREIGN KEY (CD_PROVEEDOR,CD_PERIODOFACT) REFERENCES CFACSERV.dbo.TCF003_PERIODOFACT(CD_PROVEEDOR,CD_PERIODOFACT)
go
