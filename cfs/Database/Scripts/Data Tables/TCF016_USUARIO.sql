
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF016_USUARIO'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF016_USUARIO" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

create table TCF016_USUARIO (
	CD_USUARIO                      char(8)                          not null  ,
	NB_USUARIO                      varchar(50)                      not null  ,
	CD_SECTOR                       varchar(15)                      not null  ,
	NB_PERFIL                       varchar(20)                      not null  ,
	ST_HABILITADO                   char(1)                         DEFAULT  "S" 
  not null  ,
	CD_USUARIORPL                   char(8)                              null  ,
	FH_DESDE                        date                                 null  ,
	FH_HASTA                        date                                 null  ,
	FH_ALTA                         datetime                        DEFAULT  GETDATE() 
  not null  ,
	NB_USUARIOALTA                  char(8)                          not null  ,
	FH_MODIFICACION                 datetime                             null  ,
	NB_USUARIOMODIF                 char(8)                              null  ,
	NB_CORREO                       varchar(60)                          null   
)
lock allpages
 on 'default'
go 


setuser
go 

