
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF019_PERFILES'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF019_PERFILES" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF019_PERFILES' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF019_PERFILES

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF019_PERFILES'"
	SELECT syb_quit()
END
go

create table TCF019_PERFILES (
	ID_PERFIL                       numeric(18,0)                    identity  ,
	D_PERFIL                        varchar(40)                      not null  ,
	N_NIVEL_MENSAJE                 int                              not null  ,
	C_USUA_ALTA                     numeric(18,0)                    not null  ,
	F_ALTA                          smalldatetime                   DEFAULT  getDate() 
      null  ,
	C_USUA_ACTUAC                   numeric(18,0)                        null  ,
	F_ACTUAC                        smalldatetime                        null   
)
lock allpages
with identity_gap = 1 on 'default'
go 


setuser
go 