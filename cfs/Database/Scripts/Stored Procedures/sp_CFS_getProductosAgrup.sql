-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_getProductosAgrup;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_getProductosAgrup;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_getProductosAgrup' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_getProductosAgrup

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_getProductosAgrup;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

create procedure dbo.sp_CFS_getProductosAgrup
as
begin
    select 	TCF028_PRODUCT_AGRUP.CD_PRODUCTO_ORIG, TCF028_PRODUCT_AGRUP.CD_PRODUCTO, TCF028_PRODUCT_AGRUP.FECHA_BAJA, TCF028_PRODUCT_AGRUP.FECHA_ALTA, 
			TCF028_PRODUCT_AGRUP.U_ALTA, TCF028_PRODUCT_AGRUP.FECHA_MOD, TCF028_PRODUCT_AGRUP.U_MOD  
	from TCF028_PRODUCT_AGRUP

end
go 


sp_procxmode 'sp_CFS_getProductosAgrup', anymode
go 

setuser
go 

