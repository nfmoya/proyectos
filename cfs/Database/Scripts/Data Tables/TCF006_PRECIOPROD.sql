
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF006_PRECIOPROD'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF006_PRECIOPROD" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF006_PRECIOPROD' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF006_PRECIOPROD

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF006_PRECIOPROD'"
	SELECT syb_quit()
END
go

create table TCF006_PRECIOPROD (
	CD_PROVEEDOR                    char(6)                          not null  ,
	CD_PRODUCTO                     varchar(12)                      not null  ,
	FH_DESDE                        date                             not null  ,
	FH_HASTA                        date                             not null  ,
	NU_PRECIOUNIVAL                 decimal(15,4)                    not null  ,
	ST_HABILITADO                   char(1)                          not null  ,
	FH_ALTA                         datetime                        DEFAULT  GETDATE() 
  not null  ,
	NB_USUARIOALTA                  char(8)                          not null  ,
	FH_MODIFICACION                 datetime                             null  ,
	NB_USUARIOMODIF                 char(8)                              null  ,
		CONSTRAINT ICF006P1_PRECIOPROD PRIMARY KEY CLUSTERED ( CD_PROVEEDOR, CD_PRODUCTO, FH_DESDE )  on 'default' 
)
lock allpages
 on 'default'
go 


setuser
go 

-----------------------------------------------------------------------------
-- Dependent DDL for Object(s)
-----------------------------------------------------------------------------
alter table CFACSERV.dbo.TCF006_PRECIOPROD
add constraint R0050062 FOREIGN KEY (CD_PROVEEDOR,CD_PRODUCTO) REFERENCES CFACSERV.dbo.TCF005_PRODUCTO(CD_PROVEEDOR,CD_PRODUCTO)
go


