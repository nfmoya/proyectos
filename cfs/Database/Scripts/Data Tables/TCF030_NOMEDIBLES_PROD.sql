
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


-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF030_NOMEDIBLES_PROD'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF030_NOMEDIBLES_PROD" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

create table TCF030_NOMEDIBLES_PROD (
	CD_CONCILIACION                 int                                  null  ,
	CD_PRODUCTO                     char(12)                             null  ,
	CD_PERIODO_ANT                  char(6)                              null  ,
	CT_SERVFACT_ANT                 decimal(14,4)                        null  ,
	CD_UNIVAL_ANT                   char(6)                              null  ,
	IM_PRECIOTOTAL_ANT              decimal(18,2)                        null  ,
	CD_CONCILIACION_ANT             int                                  null  ,
	CD_PERIODO_ACT                  char(6)                              null  ,
	CT_SERVFACT_ACT                 decimal(14,4)                        null  ,
	CD_UNIVAL_ACT                   char(6)                              null  ,
	IM_PRECIOTOTAL_ACT              decimal(18,2)                        null  ,
	NU_PORCVARMAX                   decimal(9,2)                         null  ,
	NU_PORCVARVAL                   decimal(9,2)                         null  ,
	CD_CONCILIACION_ACT             int                                  null  ,
	IM_DIFERENCIA                   decimal(18,2)                        null  ,
	NB_OBSERVACIONES                char(180)                            null   
)
lock allpages
 on 'default'
go 


setuser
go 

