
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF005_PRODUCTO'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF005_PRODUCTO" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF005_PRODUCTO' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF005_PRODUCTO

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF005_PRODUCTO'"
	SELECT syb_quit()
END
go

create table TCF005_PRODUCTO (
	CD_PROVEEDOR                    char(6)                          not null  ,
	CD_PRODUCTO                     varchar(12)                      not null  ,
	NB_PRODUCTO                     varchar(40)                      not null  ,
	NB_PRODUCTOCORTO                varchar(15)                      not null  ,
	CD_GRUPOPRODUCTO                char(6)                          not null  ,
	CD_UNIVAL                       char(6)                          not null  ,
	CD_SECSOLSERV                   varchar(15)                          null  ,
	CD_SECCONSERV                   varchar(15)                      not null  ,
	CD_SECCONFACT                   varchar(15)                          null  ,
	ST_PRODIMPORTPREST              char(1)                          not null  ,
	ST_PRODIMPORTFACT               char(1)                          not null  ,
	ST_REMSERVOBLIG                 char(1)                          not null  ,
	ST_REMFACTOBLIG                 char(1)                          not null  ,
	ST_ADMITEREMSERV                char(1)                          not null  ,
	ST_ADMITEREMFACT                char(1)                          not null  ,
	NB_ATRIBUTOREF1                 varchar(30)                          null  ,
	NB_ATRIBUTOREF2                 varchar(10)                          null  ,
	ST_CONCILSINVAL                 char(1)                          not null  ,
	ST_SERVSINCONCIL                char(1)                          not null  ,
	ST_FACTSINCONCIL                char(1)                          not null  ,
	NU_DIAEMIFDESDE                 int                              not null  ,
	NU_DIAEMIFHASTA                 int                              not null  ,
	NU_DIACIERREFDESDE              int                              not null  ,
	NU_DIACIERREFHASTA              int                              not null  ,
	NB_ATRIBUTOADIC1                varchar(30)                          null  ,
	NB_ATRIBUTOADIC2                varchar(30)                          null  ,
	NB_ATRIBUTOADIC3                varchar(30)                          null  ,
	NB_ATRIBUTOADIC4                varchar(30)                          null  ,
	NB_ATRIBUTOADIC5                varchar(30)                          null  ,
	ST_HABILITADO                   char(1)                          not null  ,
	FH_ALTA                         datetime                        DEFAULT  GETDATE() 
  not null  ,
	NB_USUARIOALTA                  char(8)                          not null  ,
	FH_MODIFICACION                 datetime                             null  ,
	NB_USUARIOMODIF                 char(8)                              null  ,
	CD_TIPVAL						char(3)							 not null  ,
	CD_MONEDA						char(3)							 not null  ,
	NU_PORCVARMAX					int								 not null  ,
		CONSTRAINT ICF005P1_PRODUCTO PRIMARY KEY CLUSTERED ( CD_PROVEEDOR, CD_PRODUCTO )  on 'default' 
)
lock allpages
 on 'default'
go 


setuser
go 

-----------------------------------------------------------------------------
-- Dependent DDL for Object(s)
-----------------------------------------------------------------------------
alter table CFACSERV.dbo.TCF005_PRODUCTO
add constraint R0020051 FOREIGN KEY (CD_PROVEEDOR) REFERENCES CFACSERV.dbo.TCF002_PROVEEDOR(CD_PROVEEDOR)
go


