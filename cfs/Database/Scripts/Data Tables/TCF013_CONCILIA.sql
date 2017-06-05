
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF013_CONCILIA'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF013_CONCILIA" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF013_CONCILIA' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF013_CONCILIA

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF013_CONCILIA'"
	SELECT syb_quit()
END
go

create table TCF013_CONCILIA (
	CD_CONCILIACION                 int                              not null  ,
	CD_PROVEEDOR                    char(6)                          not null  ,
	CD_PRODUCTO                     varchar(12)                      not null  ,
	CD_SECTOR                       varchar(15)                      not null  ,
	CD_PERIODOFACT                  char(6)                          not null  ,
	ST_IGNORAVAL                    char(1)                         DEFAULT  "N" 
  not null  ,
	NB_OBSERVACIONES                varchar(180)                         null  ,
	ST_CONCILIACION                 char(3)                          not null  ,
	FH_CONCILIACION                 datetime                        DEFAULT  GETDATE() 
  not null  ,
	NB_USUARIOCONCIL                char(8)                          not null  ,
		CONSTRAINT ICF013P1_CONCILIA PRIMARY KEY CLUSTERED ( CD_CONCILIACION )  on 'default' 
)
lock allpages
 on 'default'
go 


setuser
go 

-----------------------------------------------------------------------------
-- Dependent DDL for Object(s)
-----------------------------------------------------------------------------
alter table CFACSERV.dbo.TCF013_CONCILIA
add constraint R0070132 FOREIGN KEY (CD_SECTOR) REFERENCES CFACSERV.dbo.TCF007_SECTOR(CD_SECTOR)
go

alter table CFACSERV.dbo.TCF013_CONCILIA
add constraint R0050133 FOREIGN KEY (CD_PROVEEDOR,CD_PRODUCTO) REFERENCES CFACSERV.dbo.TCF005_PRODUCTO(CD_PROVEEDOR,CD_PRODUCTO)
go

alter table CFACSERV.dbo.TCF013_CONCILIA
add constraint R0020131 FOREIGN KEY (CD_PROVEEDOR) REFERENCES CFACSERV.dbo.TCF002_PROVEEDOR(CD_PROVEEDOR)
go


