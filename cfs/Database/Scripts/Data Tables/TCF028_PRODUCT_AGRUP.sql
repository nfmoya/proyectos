
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.TCF028_PRODUCT_AGRUP'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.TCF028_PRODUCT_AGRUP" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'TCF028_PRODUCT_AGRUP' AND u.name = 'dbo' AND o.type = 'U')
	drop table TCF028_PRODUCT_AGRUP

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.TCF028_PRODUCT_AGRUP'"
	SELECT syb_quit()
END
go

CREATE TABLE dbo.TCF028_PRODUCT_AGRUP  ( 
	CD_PRODUCTO_ORIG                varchar(14)                      not null  ,
	CD_PRODUCTO                     varchar(14)                      not null  ,
	DES_ITEM                        varchar(50)                      not null  ,
	DES_GRUPO                       varchar(50)                      not null  ,
	NU_NROCPTE                      varchar(8)                       not null  ,
	NU_CANTIDAD                     numeric(18,0)                   DEFAULT  0 not null  ,
	NU_IMPORTE                      numeric(18,2)                   DEFAULT  0 not null  ,
	FECHA_BAJA                      datetime                             null  ,
	FECHA_ALTA                      datetime                         not null  ,
	U_ALTA                          varchar(10)                      not null  ,
	FECHA_MOD                       datetime                             null  ,
	U_MOD                           varchar(10)                      not null   
 PRIMARY KEY CLUSTERED ( CD_PRODUCTO, CD_PRODUCTO_ORIG )  on 'default' 
)
lock allpages
 on 'default'
go 


setuser
go 
