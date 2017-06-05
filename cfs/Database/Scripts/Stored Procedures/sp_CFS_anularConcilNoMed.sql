
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_anularConcilNoMed;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_anularConcilNoMed;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_anularConcilNoMed' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_anularConcilNoMed

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_anularConcilNoMed;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE sp_CFS_anularConcilNoMed(
@cdConciliacion  int,
@observaciones  varchar(180),
@userAnulacion  varchar(8),
@po_c_error       typ_c_error output,
@po_d_error       typ_d_error output )
as
begin  
    set @po_c_error = 0  
    set @po_d_error = null
    -- validacion para que las conciliaciones tengas estado GRA o APR
    if ((select ST_CONCILIACION from TCF029_NOMEDIBLES WHERE CD_CONCILIACION = @cdConciliacion) = 'ANU') begin
        set @po_c_error = 2
        set @po_d_error = ' La conciliacion ya está anulada '
        return
    end
    begin tran anulacion
        -- Se cambia observaciones a las diferencias asociadas de la conciliacion.
        if ((select COUNT(CD_CONCILIACION) from TCF030_NOMEDIBLES_PROD WHERE CD_CONCILIACION = @cdConciliacion) > 0) begin
            update TCF030_NOMEDIBLES_PROD set NB_OBSERVACIONES = @observaciones WHERE CD_CONCILIACION = @cdConciliacion
            set @po_c_error = @@error          
            if (@po_c_error  <> 0) begin     
                set @po_d_error =  'Error al intentar anular la diferencia de conciliacion. '     
                rollback tran anulacion    
                return    
            end  
        end 
        -- Cambio estado de la conciliacion
        update TCF029_NOMEDIBLES set ST_CONCILIACION = 'ANU' WHERE CD_CONCILIACION = @cdConciliacion
        set @po_c_error = @@error          
        if (@po_c_error  <> 0) begin     
            set @po_d_error =  'Error al intentar anular la conciliacion. '     
            rollback tran anulacion    
            return    
        end
        -- Se sacan las conciliaciones asociadas a los servicios prestados y facturados.
    /*    update TCF010_SERVPRESDET set CD_CONCILIACION = null, FH_MODIFICACION = getdate(), NB_USUARIOMODIF = @userAnulacion WHERE CD_CONCILIACION = @cdConciliacion
        set @po_c_error = @@error          
        if (@po_c_error  <> 0) begin     
            set @po_d_error =  'Error al intentar sacar las conciliaciones asociadas en servicios prestados. '     
            rollback tran anulacion    
            return    
        end    */
        update TCF012_SERVFACDET set CD_CONC_NO_MEDIBLE = null, FH_MODIFICACION = getdate(), NB_USUARIOMODIF = @userAnulacion WHERE CD_CONC_NO_MEDIBLE = @cdConciliacion
        set @po_c_error = @@error          
        if (@po_c_error  <> 0) begin     
            set @po_d_error =  'Error al intentar sacar las conciliaciones asociadas en servicios facturados. '     
            rollback tran anulacion    
            return    
        end  
        commit tran anulacion
        -- Se realiza el log.
        declare @descripcion varchar(160),
                @logCdConciliacion int,
                @logCdProveedor varchar(25), 
              --  @logCdProducto varchar(15), 
                @logSector varchar(20), 
                @logPeriodo varchar(12), 
             --   @logIgnoraVal   varchar(20),
                @logEstado  varchar(10)
        select  @logCdConciliacion = CD_CONCILIACION, 
                @logCdProveedor = CD_PROVEEDOR,
              --  @logCdProducto = CD_PRODUCTO, 
                @logSector = CD_SECTOR, 
                @logPeriodo = CD_PERIODOFACT, 
             --   @logIgnoraVal = ST_IGNORAVAL,
                @logEstado  = ST_CONCILIACION
            from TCF029_NOMEDIBLES WHERE CD_CONCILIACION = @cdConciliacion
        SET @descripcion = 'Nro conciliacion: '+convert(varchar,@logCdConciliacion)+
                            ' , Proveedor: '+@logCdProveedor+
                            --' , Producto: '+@logCdProducto+
                            ' , Sector: '+@logSector+
                            ' , Periodo Fact: '+@logPeriodo+
                            --' , Ignora Val: '+@logIgnoraVal+
                            ' , Estado: '+@logEstado
        exec sp_CFS_IASF_GrabarLog @userAnulacion, 'UPDCONCILANU', @descripcion, @po_c_error output, @po_d_error output
end
go 

sp_procxmode 'sp_CFS_anularConcilNoMed', anymode
go 

setuser
go 
