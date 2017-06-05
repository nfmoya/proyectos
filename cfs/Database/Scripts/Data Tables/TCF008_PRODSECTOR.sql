
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF008_PRODSECTOR'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF008_PRODSECTOR" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF008_PRODSECTOR' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF008_PRODSECTOR

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF008_PRODSECTOR'"
	SELECT syb_quit()
END
go

create table TCF008_PRODSECTOR (
	CD_PROVEEDOR                    char(6)                          not null  ,
	CD_PRODUCTO                     varchar(12)                      not null  ,
	CD_SECTOR                       varchar(15)                      not null  ,
	ST_HABILITADO                   char(1)                         DEFAULT  "S" 
  not null  ,
	FH_ALTA                         datetime                        DEFAULT  GETDATE() 
  not null  ,
	NB_USUARIOALTA                  char(8)                          not null  ,
	FH_MODIFICACION                 datetime                             null  ,
	NB_USUARIOMODIF                 char(8)                              null  ,
		CONSTRAINT ICF008P1_PRODSECTOR PRIMARY KEY CLUSTERED ( CD_PROVEEDOR, CD_PRODUCTO, CD_SECTOR )  on 'default' 
)
lock allpages
 on 'default'
go 


setuser
go 

-----------------------------------------------------------------------------
-- Dependent DDL for Object(s)
-----------------------------------------------------------------------------
alter table CFACSERV.dbo.TCF008_PRODSECTOR
add constraint R0070082 FOREIGN KEY (CD_SECTOR) REFERENCES CFACSERV.dbo.TCF007_SECTOR(CD_SECTOR)
go

alter table CFACSERV.dbo.TCF008_PRODSECTOR
add constraint R0050083 FOREIGN KEY (CD_PROVEEDOR,CD_PRODUCTO) REFERENCES CFACSERV.dbo.TCF005_PRODUCTO(CD_PROVEEDOR,CD_PRODUCTO)
go

alter table CFACSERV.dbo.TCF008_PRODSECTOR
add constraint R0020081 FOREIGN KEY (CD_PROVEEDOR) REFERENCES CFACSERV.dbo.TCF002_PROVEEDOR(CD_PROVEEDOR)
go


