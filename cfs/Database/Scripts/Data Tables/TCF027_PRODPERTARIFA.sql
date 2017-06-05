
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF027_PRODPERTARIFA'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF027_PRODPERTARIFA" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF027_PRODPERTARIFA' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF027_PRODPERTARIFA

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF027_PRODPERTARIFA'"
	SELECT syb_quit()
END
go

create table TCF027_PRODPERTARIFA (
	CD_PROVEEDOR                    char(6)                          not null  ,
	CD_PRODUCTO                     varchar(12)                      not null  ,
	FH_DESDE                        date                             not null  ,
	NU_CANTDESDE                    int                              not null  ,
	NU_CANTHASTA                    int                              not null  ,
	NU_PRECIOUNIVAL                 decimal(15,4)                    not null  ,
	NU_PORCTARIFA                   decimal(10,2)                    not null  ,
	ST_HABILITADO                   char(1)                          not null  ,
	ST_PRECIOFIJO                   char(1)                              null  ,
	FH_ALTA                         datetime                        DEFAULT    GETDATE() not null  ,
	NB_USUARIOALTA                  char(8)                          not null  ,
	FH_MODIFICACION                 datetime                             null  ,
	NB_USUARIOMODIF                 char(8)                              null  ,
		CONSTRAINT ICF023P1_PRODPERIODO PRIMARY KEY CLUSTERED ( CD_PROVEEDOR, CD_PRODUCTO, FH_DESDE, NU_CANTDESDE )  on 'default' 
)
lock allpages
 on 'default'
go 


setuser
go 

-----------------------------------------------------------------------------
-- Dependent DDL for Object(s)
-----------------------------------------------------------------------------
alter table CFACSERV.dbo.TCF027_PRODPERTARIFA
add constraint R0230064 FOREIGN KEY (CD_PROVEEDOR,CD_PRODUCTO,FH_DESDE) REFERENCES CFACSERV.dbo.TCF026_PRODPERIODO(CD_PROVEEDOR,CD_PRODUCTO,FH_DESDE)
go


