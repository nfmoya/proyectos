
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF007_SECTOR'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF007_SECTOR" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF007_SECTOR' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF007_SECTOR

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF007_SECTOR'"
	SELECT syb_quit()
END
go

create table TCF007_SECTOR (
	CD_SECTOR                       varchar(15)                      not null  ,
	NB_SECTOR                       varchar(50)                      not null  ,
	NB_SECTORABREV                  varchar(10)                          null  ,
	CD_SECTOR_ALT                   varchar(15)                          null  ,
	ST_HABILITADO                   char(1)                         DEFAULT  "S" 
  not null  ,
	FH_ALTA                         datetime                        DEFAULT  GETDATE() 
  not null  ,
	NB_USUARIOALTA                  char(8)                          not null  ,
	FH_MODIFICACION                 datetime                             null  ,
	NB_USUARIOMODIF                 char(8)                              null  ,
		CONSTRAINT ICF007P1_SECTOR PRIMARY KEY CLUSTERED ( CD_SECTOR )  on 'default' 
)
lock allpages
 on 'default'
go 


setuser
go 

