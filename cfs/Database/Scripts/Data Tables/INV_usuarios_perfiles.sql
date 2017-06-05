
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.INV_usuarios_perfiles'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.INV_usuarios_perfiles" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'INV_usuarios_perfiles' AND u.name = 'dbo' AND o.type = 'U')
	drop table INV_usuarios_perfiles

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.INV_usuarios_perfiles'"
	SELECT syb_quit()
END
go

create table INV_usuarios_perfiles (
	id_perfil                       numeric(18,0)                    not null  ,
	id_usuario                      numeric(18,0)                    not null  ,
	e_usu_perfil                    varchar(1)                       not null  ,
	c_usua_alta                     numeric(18,0)                    not null  ,
	f_alta                          smalldatetime                   DEFAULT  getDate() 
      null  ,
	c_usua_actuac                   numeric(18,0)                        null  ,
	f_actuac                        smalldatetime                        null  ,
 PRIMARY KEY CLUSTERED ( id_perfil, id_usuario )  on 'default' 
)
lock allpages
 on 'default'
go 


setuser
go 

-----------------------------------------------------------------------------
-- Dependent DDL for Object(s)
-----------------------------------------------------------------------------
alter table CFACSERV.dbo.INV_usuarios_perfiles
add constraint INV_usuar_id_usu_2115535589 FOREIGN KEY (id_usuario) REFERENCES CFACSERV.dbo.INV_usuarios(id_usuario)
go


