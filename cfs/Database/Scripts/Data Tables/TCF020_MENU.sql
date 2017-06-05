
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF020_MENU'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF020_MENU" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF020_MENU' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF020_MENU

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF020_MENU'"
	SELECT syb_quit()
END
go

create table TCF020_MENU (
	ID_MENU                         numeric(18,0)                    identity  ,
	D_MENU                          varchar(40)                      not null  ,
	X_URL_MENU                      varchar(250)                     not null  ,
	ID_PADRE                        numeric(18,0)                        null  ,
	N_ORDEN                         numeric(18,0)                        null  ,
	C_USUA_ALTA                     numeric(18,0)                    not null  ,
	F_ALTA                          smalldatetime                   DEFAULT  getDate() 
      null  ,
	C_USUA_ACTUAC                   numeric(18,0)                        null  ,
	F_ACTUAC                        smalldatetime                        null  ,
	N_NIVEL                         numeric(1,0)                     not null  ,
	F_BAJA                          smalldatetime                        null   
)
lock allpages
with identity_gap = 1 on 'default'
go 


setuser
go 
