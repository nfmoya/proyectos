
-----------------------------------------------------------------------------
-- DDL for Table 'CFACSERV.dbo.INV_menu'
-----------------------------------------------------------------------------
print '<<<<< CREATING Table - "CFACSERV.dbo.INV_menu" >>>>>'
go

use CFACSERV
go 

setuser 'dbo'
go 

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'INV_menu' AND u.name = 'dbo' AND o.type = 'U')
	drop table INV_menu

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING table 'CFACSERV.dbo.INV_menu'"
	SELECT syb_quit()
END
go

create table INV_menu (
	id_menu                         numeric(18,0)                    identity  ,
	d_menu                          varchar(40)                      not null  ,
	x_url_menu                      varchar(250)                     not null  ,
	id_padre                        numeric(18,0)                        null  ,
	n_orden                         numeric(18,0)                        null  ,
	c_usua_alta                     numeric(18,0)                    not null  ,
	f_alta                          smalldatetime                   DEFAULT  getDate() 
      null  ,
	c_usua_actuac                   numeric(18,0)                        null  ,
	f_actuac                        smalldatetime                        null  ,
	n_nivel                         numeric(1,0)                     not null  ,
	f_baja                          smalldatetime                        null   
)
lock allpages
 on 'default'
go 


setuser
go 

