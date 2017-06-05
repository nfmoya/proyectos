
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF022_USUARIO_BATCH'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF022_USUARIO_BATCH" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF022_USUARIO_BATCH' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF022_USUARIO_BATCH

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF022_USUARIO_BATCH'"
	SELECT syb_quit()
END
go

create table TCF022_USUARIO_BATCH (
	CD_USUARIO                      char(8)                          not null  ,
	DS_NOMBRE                       varchar(50)                      not null  ,
	CD_SECTOR                       varchar(15)                      not null  ,
	ID_PERFIL                       int                              not null  ,
	ST_HABILITADO                   char(1)                         DEFAULT   "S" 
  
  not null  ,
	NB_CORREO                       varchar(60)                          null  ,
	NB_SECTOR                       varchar(50)                          null  ,
	DS_APELLIDO                     varchar(50)                      not null   
)
lock allpages
 on 'default'
go 


setuser
go 

