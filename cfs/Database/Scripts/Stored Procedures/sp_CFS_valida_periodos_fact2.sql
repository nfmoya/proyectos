
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_valida_periodos_fact2;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_valida_periodos_fact2;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_valida_periodos_fact2' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_valida_periodos_fact2

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_valida_periodos_fact2;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_valida_periodos_fact2 ( 
	@pi_pf_desde             varchar(5),
    @pi_pf_hasta             varchar(5),
	@pi_cd_proveedor		varchar(6),
    @po_c_error              typ_c_error output,  
    @po_d_error              typ_d_error output  
)AS
    declare 
    	@fhInicio date,
        @fhFin date,
        @diasDiff int
	--Seteo de variables
    set @po_c_error = 0  
    set @po_d_error = null   
    /**
    ** Obtengo las fechas de los periodos en caso de se haya seleccionado una
    ** Sino las seteo en NULL
    */
    if(@pi_pf_desde is not null or @pi_pf_desde <> '' and @pi_pf_hasta is not null or @pi_pf_hasta <> '')
    begin
      SELECT  @fhInicio = FH_DESDE FROM TCF003_PERIODOFACT WHERE CD_PERIODOFACT = @pi_pf_desde AND CD_PROVEEDOR = @pi_cd_proveedor
      SELECT @fhFin = FH_HASTA FROM TCF003_PERIODOFACT WHERE CD_PERIODOFACT = @pi_pf_hasta AND CD_PROVEEDOR = @pi_cd_proveedor
    end
    else
    begin
		set @po_c_error = 1
    	set @po_d_error = ' No se recibio Fechas de Periodos de Facturacion '
    	return
    end
	--validacion de los periodos
	if(@fhInicio > @fhFin)
	begin
    	set @po_c_error = 2
    	set @po_d_error = ' Periodo Hasta no puede ser menor al Periodo Desde '
    	return
	end
	else
	begin
        -- Verifica que el periodo esté en el rango de un año (12 meses)
        select @diasDiff = datediff(dd,(SELECT FH_HASTA FROM TCF003_PERIODOFACT WHERE CD_PERIODOFACT = @pi_pf_desde AND CD_PROVEEDOR = @pi_cd_proveedor),
            (SELECT FH_HASTA FROM TCF003_PERIODOFACT WHERE CD_PERIODOFACT = @pi_pf_hasta AND CD_PROVEEDOR = @pi_cd_proveedor))        
        if(@diasDiff > 365) begin
            set @po_c_error = 2
    	    set @po_d_error = ' El Periodo supera los 12 meses del rango para consulta'
    	    return
	    end 
        else 
        begin
    		set @po_c_error = 0  
        	set @po_d_error = null   
        	return
        end
	end
go 

sp_procxmode 'sp_CFS_valida_periodos_fact2', anymode
go 

setuser
go 
