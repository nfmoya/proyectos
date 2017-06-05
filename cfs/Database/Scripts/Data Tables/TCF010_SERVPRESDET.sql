
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF010_SERVPRESDET'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF010_SERVPRESDET" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF010_SERVPRESDET' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF010_SERVPRESDET

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF010_SERVPRESDET'"
	SELECT syb_quit()
END
go

create table TCF010_SERVPRESDET (
	CD_LOTESERV                     int                              not null  ,
	CD_ORDEN                        int                              not null  ,
	CD_SECTORSOL                    varchar(15)                      not null  ,
	CD_SECTOR                       varchar(15)                      not null  ,
	FH_REMITO                       date                             not null  ,
	FH_FIN_SERV                     date                             not null  ,
	CD_PRODUCTO                     varchar(12)                      not null  ,
	CT_SERVPREST                    decimal(7,2)                     not null  ,
	CD_UNIVAL                       char(6)                          not null  ,
	IM_PRECIOUNIT                   decimal(5,4)                     not null  ,
	IM_PRECIOTOTAL                  decimal(9,2)                     not null  ,
	NB_PIEZADESDE                   varchar(25)                          null  ,
	NB_PIEZAHASTA                   varchar(25)                          null  ,
	CD_REMITOPADRE                  varchar(13)                          null  ,
	NB_ATRIBUTOREF1                 varchar(30)                          null  ,
	NB_ATRIBUTOREF2                 varchar(10)                          null  ,
	NB_OBSERVACIONES                varchar(50)                          null  ,
	ST_LOTEDET                      char(1)                         DEFAULT  "A" 
  not null  ,
	CD_CONCILIACION                 int                                  null  ,
	FH_MODIFICACION                 datetime                        DEFAULT  GETDATE() 
  not null  ,
	NB_USUARIOMODIF                 char(8)                          not null  ,
	CD_REMITO                       varchar(13)                          null  ,
		CONSTRAINT ICF010P1_SERVPRESDET PRIMARY KEY CLUSTERED ( CD_LOTESERV, CD_ORDEN )  on 'default' 
)
lock allpages
 on 'default'
go 


setuser
go 

-----------------------------------------------------------------------------
-- Dependent DDL for Object(s)
-----------------------------------------------------------------------------
alter table CFACSERV.dbo.TCF010_SERVPRESDET
add constraint R0090101 FOREIGN KEY (CD_LOTESERV) REFERENCES CFACSERV.dbo.TCF009_SERVPRESCAB(CD_LOTESERV)
go

alter table CFACSERV.dbo.TCF010_SERVPRESDET
add constraint R0070102 FOREIGN KEY (CD_SECTOR) REFERENCES CFACSERV.dbo.TCF007_SECTOR(CD_SECTOR)
go


