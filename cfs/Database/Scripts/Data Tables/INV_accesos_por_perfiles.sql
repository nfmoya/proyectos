
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.INV_accesos_por_perfiles'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.INV_accesos_por_perfiles" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'INV_accesos_por_perfiles' AND u.name = 'dbo' AND o.type = 'U')
	drop table INV_accesos_por_perfiles

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.INV_accesos_por_perfiles'"
	SELECT syb_quit()
END
go

create table INV_accesos_por_perfiles (
	id_acceso                       varchar(20)                      not null  ,
	id_perfil                       numeric(18,0)                    not null  ,
	f_alta                          smalldatetime                   DEFAULT  getDate() 
      null  ,
 PRIMARY KEY CLUSTERED ( id_acceso, id_perfil )  on 'default' 
)
lock allpages
 on 'default'
go 


setuser
go 

-----------------------------------------------------------------------------
-- Dependent DDL for Object(s)
-----------------------------------------------------------------------------
alter table CFACSERV.dbo.INV_accesos_por_perfiles
add constraint INV_acces_id_acc_1495009376 FOREIGN KEY (id_acceso) REFERENCES CFACSERV.dbo.INV_accesos(id_acceso)
go


