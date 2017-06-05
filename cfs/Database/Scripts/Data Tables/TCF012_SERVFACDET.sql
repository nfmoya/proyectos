
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF012_SERVFACDET'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF012_SERVFACDET" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF012_SERVFACDET' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF012_SERVFACDET

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF012_SERVFACDET'"
	SELECT syb_quit()
END
go

create table TCF012_SERVFACDET (
	CD_LOTEFACT                     int                              not null  ,
	CD_ORDEN                        int                              not null  ,
	CD_PERIODOFACT                  char(6)                          not null  ,
	CD_SECTORCONCIL                 varchar(15)                      not null  ,
	TP_COMPROBANTE                  char(6)                          not null  ,
	NU_COMPROBANTE                  varchar(13)                      not null  ,
	CD_REMITO                       varchar(13)                          null  ,
	CD_PRODUCTO                     varchar(12)                      not null  ,
	CT_SERVFACT                     decimal(7,2)                     not null  ,
	CD_UNIVAL                       char(6)                          not null  ,
	IM_PRECIOUNIT                   decimal(5,4)                     not null  ,
	IM_PRECIOTOTAL                  decimal(9,2)                     not null  ,
	NB_OBSERVACIONES                varchar(50)                          null  ,
	ST_LOTEDET                      char(1)                         DEFAULT  "A" 
  not null  ,
	CD_CONCILIACION                 int                                  null  ,
	FH_MODIFICACION                 datetime                        DEFAULT  GETDATE() 
  not null  ,
	NB_USUARIOMODIF                 char(8)                          not null  ,
		CONSTRAINT ICF012P1_SERVFACDET PRIMARY KEY CLUSTERED ( CD_LOTEFACT, CD_ORDEN )  on 'default' 
)
lock allpages
 on 'default'
go 


setuser
go 

-----------------------------------------------------------------------------
-- Dependent DDL for Object(s)
-----------------------------------------------------------------------------
alter table CFACSERV.dbo.TCF012_SERVFACDET
add constraint R0110121 FOREIGN KEY (CD_LOTEFACT) REFERENCES CFACSERV.dbo.TCF011_SERVFACCAB(CD_LOTEFACT)
go

alter table CFACSERV.dbo.TCF012_SERVFACDET
add constraint R0070122 FOREIGN KEY (CD_SECTORCONCIL) REFERENCES CFACSERV.dbo.TCF007_SECTOR(CD_SECTOR)
go


