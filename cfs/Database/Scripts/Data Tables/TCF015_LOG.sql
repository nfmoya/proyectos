
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF015_LOG'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF015_LOG" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF015_LOG' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF015_LOG

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF015_LOG'"
	SELECT syb_quit()
END
go

create table TCF015_LOG (
	CD_USUARIO                      char(8)                          not null  ,
	FH_LOG                          datetime                        DEFAULT  GETDATE() 
  not null  ,
	CD_EVENTO                       varchar(12)                      not null  ,
	NB_LOG                          varchar(80)                          null  ,
		CONSTRAINT ICF015P1_LOG PRIMARY KEY CLUSTERED ( CD_USUARIO, FH_LOG )  on 'default' 
)
lock allpages
 on 'default'
go 


setuser
go 

-----------------------------------------------------------------------------
-- Dependent DDL for Object(s)
-----------------------------------------------------------------------------
alter table CFACSERV.dbo.TCF015_LOG
add constraint R0160151 FOREIGN KEY (CD_USUARIO) REFERENCES CFACSERV.dbo.TFC016_USUARIO(CD_USUARIO)
go


