
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF001_GENERAL'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF001_GENERAL" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF001_GENERAL' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF001_GENERAL

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF001_GENERAL'"
	SELECT syb_quit()
END
go

create table TCF001_GENERAL (
	CD_TABLA                        char(6)                          not null  ,
	CD_CODTABLA                     char(6)                          not null  ,
	NB_CODTABLA                     varchar(30)                      not null  ,
	NB_CODTABLACORTO                varchar(12)                          null  ,
	NB_ATRIBUTOTABLA1               varchar(30)                          null  ,
	NB_ATRIBUTOTABLA2               varchar(30)                          null  ,
	NB_ATRIBUTOTABLA3               varchar(30)                          null  ,
	ST_HABILITADO                   char(1)                         DEFAULT  "S" 
  not null  ,
	FH_ALTA                         datetime                        DEFAULT  GETDATE() 
  not null  ,
	NB_USUARIOALTA                  char(8)                          not null  ,
	FH_MODIFICACION                 datetime                             null  ,
	NB_USUARIOMODIF                 char(8)                              null  ,
		CONSTRAINT ICF001P1_GENERAL PRIMARY KEY CLUSTERED ( CD_TABLA, CD_CODTABLA )  on 'default' 
)
lock allpages
 on 'default'
go 


setuser
go 

