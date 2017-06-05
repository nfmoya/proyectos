
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF014_DIFCONCILIA'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF014_DIFCONCILIA" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF014_DIFCONCILIA' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF014_DIFCONCILIA

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF014_DIFCONCILIA'"
	SELECT syb_quit()
END
go

create table TCF014_DIFCONCILIA (
	CD_CONCILIACION                 int                              not null  ,
	CD_ORDEN                        int                              not null  ,
	CD_REMITO                       varchar(13)                          null  ,
	CT_DIFERENCIA                   decimal(7,2)                         null  ,
	NB_PIEZADESDE                   varchar(25)                          null  ,
	NB_PIEZAHASTA                   varchar(25)                          null  ,
	NB_OBSERVACIONES                varchar(180)                         null  ,
	CD_UNIVAL                       char(6)                              null  ,
	IM_PRECIOTOTAL                  decimal(9,2)                         null  ,
	TP_SOLUCION                     char(6)                          not null  ,
	ST_DIFERENCIA                   char(3)                         DEFAULT  "ACT" 
  not null  ,
	FH_ALTA                         datetime                        DEFAULT  GETDATE() 
  not null  ,
	NB_USUARIOALTA                  char(8)                          not null  ,
		CONSTRAINT ICF014P1_DIFCONCILIA PRIMARY KEY CLUSTERED ( CD_CONCILIACION, CD_ORDEN )  on 'default' 
)
lock allpages
 on 'default'
go 


setuser
go 

-----------------------------------------------------------------------------
-- Dependent DDL for Object(s)
-----------------------------------------------------------------------------
alter table CFACSERV.dbo.TCF014_DIFCONCILIA
add constraint R0130141 FOREIGN KEY (CD_CONCILIACION) REFERENCES CFACSERV.dbo.TCF013_CONCILIA(CD_CONCILIACION)
go


