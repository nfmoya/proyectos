
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF024_TIPOCAMBIO'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF024_TIPOCAMBIO" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF024_TIPOCAMBIO' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF024_TIPOCAMBIO

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF024_TIPOCAMBIO'"
	SELECT syb_quit()
END
go

create table TCF024_TIPOCAMBIO (
	FH_CAMBIO                       date                             not null  ,
	CD_DIVISS                       varchar(3)                       not null  ,
	NU_CAMBFIX                      decimal(15,10)                   not null  ,
	NU_CAMBBAJO                     decimal(15,10)                   not null  ,
	NU_CAMBALTO                     decimal(15,10)                   not null  ,
	FH_ALTA                         datetime                         not null  ,
		CONSTRAINT TCF024_TIP_16445298611 PRIMARY KEY CLUSTERED ( FH_CAMBIO, CD_DIVISS )  on 'default' 
)
lock allpages
 on 'default'
go 


setuser
go 

