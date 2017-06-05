
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.INV_usuarios'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.INV_usuarios" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'INV_usuarios' AND u.name = 'dbo' AND o.type = 'U')
	drop table INV_usuarios

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.INV_usuarios'"
	SELECT syb_quit()
END
go

create table INV_usuarios (
	id_usuario                      numeric(18,0)                    identity  ,
	d_user                          varchar(40)                      not null  ,
	e_usuario                       char(1)                              null  ,
	d_nombre                        varchar(40)                      not null  ,
	d_apellido                      varchar(40)                      not null  ,
	c_usua_alta                     numeric(18,0)                    not null  ,
	f_alta                          smalldatetime                   DEFAULT  getDate() 
      null  ,
	c_usua_actuac                   numeric(18,0)                        null  ,
	f_actuac                        smalldatetime                        null  ,
 PRIMARY KEY CLUSTERED ( id_usuario )  on 'default',
		CONSTRAINT INV_usu_uk_d_user UNIQUE NONCLUSTERED ( d_user )  on 'default',
CONSTRAINT INV_usua_check CHECK     (e_usuario in ('A', 'B', 'U')))
lock allpages
 on 'default'
go 


setuser
go 

