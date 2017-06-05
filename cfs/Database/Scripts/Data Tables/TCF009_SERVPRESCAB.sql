
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF009_SERVPRESCAB'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF009_SERVPRESCAB" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF009_SERVPRESCAB' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF009_SERVPRESCAB

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF009_SERVPRESCAB'"
	SELECT syb_quit()
END
go

create table TCF009_SERVPRESCAB (
	CD_LOTESERV                     int                              not null  ,
	TP_INTERFAZ                     char(3)                          not null  ,
	NB_ARCHIVO                      varchar(30)                      not null  ,
	CD_PROVEEDOR                    char(6)                          not null  ,
	NB_OBSERVACIONES                varchar(50)                      not null  ,
	ST_LOTECAB                      char(3)                         DEFAULT  "ACT" 
  not null  ,
	FH_ALTA                         datetime                        DEFAULT  GETDATE() 
  not null  ,
	NB_USUARIOALTA                  char(8)                          not null  ,
	FH_MODIFICACION                 datetime                         not null  ,
	NB_USUARIOMODIF                 char(8)                          not null  ,
		CONSTRAINT ICF009P1_SERVPRESCAB PRIMARY KEY CLUSTERED ( CD_LOTESERV )  on 'default' 
)
lock allpages
 on 'default'
go 


setuser
go 

-----------------------------------------------------------------------------
-- Dependent DDL for Object(s)
-----------------------------------------------------------------------------
alter table CFACSERV.dbo.TCF009_SERVPRESCAB
add constraint R0020091 FOREIGN KEY (CD_PROVEEDOR) REFERENCES CFACSERV.dbo.TCF002_PROVEEDOR(CD_PROVEEDOR)
go


