
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF011_SERVFACCAB'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF011_SERVFACCAB" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF011_SERVFACCAB' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF011_SERVFACCAB

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF011_SERVFACCAB'"
	SELECT syb_quit()
END
go

create table TCF011_SERVFACCAB (
	CD_LOTEFACT                     int                              not null  ,
	TP_INTERFAZ                     char(3)                          not null  ,
	NB_ARCHIVO                      varchar(30)                      not null  ,
	CD_PROVEEDOR                    char(6)                          not null  ,
	NB_OBSERVACIONES                varchar(50)                          null  ,
	ST_LOTECAB                      char(3)                         DEFAULT  "ACT" 
  not null  ,
	FH_ALTA                         datetime                        DEFAULT  GETDATE() 
  not null  ,
	NB_USUARIOALTA                  char(8)                          not null  ,
	FH_MODIFICACION                 datetime                             null  ,
	NB_USUARIOMODIF                 char(8)                              null  ,
		CONSTRAINT ICF011P1_SERVFACCAB PRIMARY KEY CLUSTERED ( CD_LOTEFACT )  on 'default' 
)
lock allpages
 on 'default'
go 


setuser
go 

-----------------------------------------------------------------------------
-- Dependent DDL for Object(s)
-----------------------------------------------------------------------------
alter table CFACSERV.dbo.TCF011_SERVFACCAB
add constraint R0020111 FOREIGN KEY (CD_PROVEEDOR) REFERENCES CFACSERV.dbo.TCF002_PROVEEDOR(CD_PROVEEDOR)
go


