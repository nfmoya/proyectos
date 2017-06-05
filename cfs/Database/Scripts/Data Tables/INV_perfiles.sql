
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.INV_perfiles'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.INV_perfiles" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'INV_perfiles' AND u.name = 'dbo' AND o.type = 'U')
	drop table INV_perfiles

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.INV_perfiles'"
	SELECT syb_quit()
END
go

create table INV_perfiles (
	id_perfil                       numeric(18,0)                    identity  ,
	d_perfil                        varchar(40)                      not null  ,
	n_nivel_mensaje                 int                              not null  ,
	c_usua_alta                     numeric(18,0)                    not null  ,
	f_alta                          smalldatetime                   DEFAULT  getDate() 
      null  ,
	c_usua_actuac                   numeric(18,0)                        null  ,
	f_actuac                        smalldatetime                        null   
)
lock allpages
 on 'default'
go 


setuser
go 

