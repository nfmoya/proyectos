
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF023_INTERFAZBI'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF023_INTERFAZBI" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF023_INTERFAZBI' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF023_INTERFAZBI

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF023_INTERFAZBI'"
	SELECT syb_quit()
END
go

create table TCF023_INTERFAZBI (
	CD_INTERFAZ                     char(3)                          not null  ,
	CD_ORDEN                        int                              not null  ,
	FH_FECHA                        datetime                         not null  ,
	NB_DATO                         text                             not null  ,
 PRIMARY KEY CLUSTERED ( CD_INTERFAZ, CD_ORDEN )  on 'default' 
)
lock allpages
 on 'default'
go 

sp_placeobject 'default', 'dbo.TCF023_INTERFAZBI.tTCF023_INTERFAZBI'
go 


setuser
go 

