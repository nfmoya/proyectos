
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_IASF_modifAgrupProd;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_IASF_modifAgrupProd;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_IASF_modifAgrupProd' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_IASF_modifAgrupProd

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_IASF_modifAgrupProd;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

create procedure dbo.sp_CFS_IASF_modifAgrupProd
/* [ (@param_name datatype [= default] [output] ), ... ] */
@producto varchar(14),
@nroComprobante varchar(8),
@cantidad   numeric(18,0),
@importe    numeric(18,2)
as
begin
    declare @cantAnt numeric(18,0), @importeAnt numeric(18,2)
    
    select @cantAnt = NU_CANTIDAD, @importeAnt = NU_IMPORTE from TCF028_PRODUCT_AGRUP where  CD_PRODUCTO_ORIG = @producto

    if ((select NU_NROCPTE from TCF028_PRODUCT_AGRUP where CD_PRODUCTO_ORIG = @producto) = '0') begin
        update TCF028_PRODUCT_AGRUP set NU_NROCPTE = @nroComprobante where CD_PRODUCTO = (select CD_PRODUCTO from TCF028_PRODUCT_AGRUP where CD_PRODUCTO_ORIG = @producto)
    end
    update TCF028_PRODUCT_AGRUP set NU_CANTIDAD = (@cantAnt + @cantidad), NU_IMPORTE = (@importeAnt + @importe) where  CD_PRODUCTO_ORIG = @producto


end

go 


sp_procxmode 'sp_CFS_IASF_modifAgrupProd', anymode
go 

setuser
go 