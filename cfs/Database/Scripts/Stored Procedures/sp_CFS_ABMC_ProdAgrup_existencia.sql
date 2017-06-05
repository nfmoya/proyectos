
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_ProdAgrup_existencia;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_ABMC_ProdAgrup_existencia;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_ABMC_ProdAgrup_existencia' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_ABMC_ProdAgrup_existencia

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_ProdAgrup_existencia;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_ABMC_ProdAgrup_existencia
    @cd_proveedor        varchar(6),
    @cd_producto         varchar(12),
    @nb_atributoadic1    varchar(1),
    @po_c_error          typ_c_error output,
    @po_d_error          typ_d_error output  
AS
BEGIN
    DECLARE @cantidad int
    set @cantidad = 0
    set @po_c_error = 0  
    set @po_d_error = null 

	IF @nb_atributoadic1 = 'S'
        BEGIN
            set @cantidad = (SELECT Count(*) 
                            FROM TCF028_PRODUCT_AGRUP A
                            INNER JOIN TCF005_PRODUCTO B ON A.CD_PRODUCTO = B.CD_PRODUCTO 
                            WHERE B.CD_PROVEEDOR = @cd_proveedor
                              AND B.CD_PRODUCTO  = @cd_producto
                              AND A.FECHA_BAJA IS NULL)
        END
    ELSE
        BEGIN
            set @cantidad = (SELECT Count(*)
                            FROM TCF028_PRODUCT_AGRUP A
                            INNER JOIN TCF005_PRODUCTO B ON A.CD_PRODUCTO_ORIG = B.CD_PRODUCTO 
                            WHERE B.CD_PROVEEDOR = @cd_proveedor
                              AND B.CD_PRODUCTO  = @cd_producto
                              AND A.FECHA_BAJA IS NULL)
        END

    IF @cantidad > 0
        BEGIN
            set @po_c_error   = 1
            set @po_d_error   = 'No se puede INHABILITAR este producto ya que tiene otros productos agrupados'
            return 
        END

END
go 


sp_procxmode 'sp_CFS_ABMC_ProdAgrup_existencia', anymode
go 

setuser
go 

