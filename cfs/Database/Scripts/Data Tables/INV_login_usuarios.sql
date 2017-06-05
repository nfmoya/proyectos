
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.INV_login_usuarios'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.INV_login_usuarios" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'INV_login_usuarios' AND u.name = 'dbo' AND o.type = 'U')
	drop table INV_login_usuarios

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.INV_login_usuarios'"
	SELECT syb_quit()
END
go

create table INV_login_usuarios (
	usu_d_user                      varchar(40)                      not null  ,
	usu_habilitado                  varchar(1)                           null  ,
	usu_estado                      varchar(1)                           null  ,
	usu_ult_acceso                  varchar(12)                          null  ,
	usu_hora_acceso                 varchar(8)                           null  ,
	usu_clave1                      varchar(50)                          null  ,
	usu_clave2                      varchar(50)                          null  ,
	usu_clave3                      varchar(50)                          null  ,
	usu_clave4                      varchar(50)                          null  ,
	usu_int_fallidos                numeric(4,0)                     not null  ,
	usu_clave                       varchar(50)                          null   
)
lock allpages
 on 'default'
go 


setuser
go 

