
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF029_NOMEDIBLES'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF029_NOMEDIBLES" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

create table TCF029_NOMEDIBLES (
	CD_CONCILIACION                 int                              not null  ,
	CD_PROVEEDOR                    char(6)                          not null  ,
	CD_SECTOR                       varchar(15)                      not null  ,
	CD_PERIODOFACT                  char(6)                          not null  ,
	ST_CONCILIACION                 char(3)                          not null  ,
	FH_CONCILIACION                 datetime                        DEFAULT     GETDATE() 
  not null  ,
	NB_USUARIOCONCIL                char(8)                          not null   
)
lock allpages
 on 'default'
go 


setuser
go 

