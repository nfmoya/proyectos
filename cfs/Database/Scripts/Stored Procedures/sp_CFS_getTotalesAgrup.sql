
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_getTotalesAgrup;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_getTotalesAgrup;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_getTotalesAgrup' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_getTotalesAgrup

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_getTotalesAgrup;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

create procedure dbo.sp_CFS_getTotalesAgrup

/* [ (@param_name datatype [= default] [output] ), ... ] */

@producto   varchar(14)

as

begin

    select DISTINCT(DES_GRUPO), NU_NROCPTE, SUM(NU_CANTIDAD) as CANTIDAD, SUM(NU_IMPORTE) as IMPORTE,(CONVERT(NUMERIC(18,2),(SUM(NU_IMPORTE) / SUM(NU_CANTIDAD)))) as PRECIO_UNIT

        from TCF028_PRODUCT_AGRUP WHERE CD_PRODUCTO =@producto group by CD_PRODUCTO, DES_GRUPO

end
go 


sp_procxmode 'sp_CFS_getTotalesAgrup', anymode
go 

setuser
go 