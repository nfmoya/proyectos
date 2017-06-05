
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF004_VALORUNIVAL'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF004_VALORUNIVAL" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF004_VALORUNIVAL' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF004_VALORUNIVAL

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF004_VALORUNIVAL'"
	SELECT syb_quit()
END
go

create table TCF004_VALORUNIVAL (
	CD_PROVEEDOR                    char(6)                          not null  ,
	CD_UNIVAL                       char(6)                          not null  ,
	CD_PERIODOFACT                  char(6)                          not null  ,
	NU_VALBRUTOUNIVAL               decimal(15,4)                    not null  ,
	NU_VALNETOUNIVAL                decimal(15,4)                    not null  ,
	ST_HABILITADO                   char(1)                         DEFAULT  "S" 
  not null  ,
	FH_ALTA                         datetime                        DEFAULT  GETDATE() 
  not null  ,
	NB_USUARIOALTA                  char(8)                          not null  ,
	FH_MODIFICACION                 datetime                             null  ,
	NB_USUARIOMODIF                 char(8)                              null  ,
		CONSTRAINT ICF004P1_VALORUNIVAL PRIMARY KEY CLUSTERED ( CD_PROVEEDOR, CD_UNIVAL, CD_PERIODOFACT )  on 'default' 
)
lock allpages
 on 'default'
go 


setuser
go 

-----------------------------------------------------------------------------
-- Dependent DDL for Object(s)
-----------------------------------------------------------------------------
alter table CFACSERV.dbo.TCF004_VALORUNIVAL
add constraint R0030042 FOREIGN KEY (CD_PROVEEDOR,CD_PERIODOFACT) REFERENCES CFACSERV.dbo.TCF003_PERIODOFACT(CD_PROVEEDOR,CD_PERIODOFACT)
go

alter table CFACSERV.dbo.TCF004_VALORUNIVAL
add constraint R0020041 FOREIGN KEY (CD_PROVEEDOR) REFERENCES CFACSERV.dbo.TCF002_PROVEEDOR(CD_PROVEEDOR)
go


