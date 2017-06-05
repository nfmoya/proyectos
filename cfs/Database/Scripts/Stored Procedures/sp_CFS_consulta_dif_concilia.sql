
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_consulta_dif_concilia;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_consulta_dif_concilia;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_consulta_dif_concilia' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	drop procedure sp_CFS_consulta_dif_concilia
END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_consulta_dif_concilia;1'"
	SELECT syb_quit()
END
go

CREATE PROCEDURE dbo.sp_CFS_consulta_dif_concilia ( 
    @pi_cd_proveedor         varchar(6),
    @pi_cd_sector            varchar(15),
    @pi_pf_desde             varchar(5),
    @pi_pf_hasta             varchar(5),
    @pi_cd_producto          varchar(12),
    @pi_st_diferencia        varchar(3),
    @pi_tp_solucion          varchar(6),-- SOLUCION
    @pi_cd_conciliacion      numeric(6,0),   
    @po_c_error              typ_c_error output,  
    @po_d_error              typ_d_error output  
) AS 
   begin
    declare 
        @fhInicio date,
        @fhFin date
        
	--Seteo de variables
    set @po_c_error = 0  
    set @po_d_error = null   

    /**
    ** Obtengo las fechas de los periodos en caso de se haya seleccionado una
    ** Sino las seteo en NULL
    */

    if(@pi_pf_desde is not null and @pi_pf_desde <> '' and @pi_pf_hasta is not null and @pi_pf_hasta <> '')
    begin
        --print 'HOLA'
      SELECT  @fhInicio = FH_DESDE FROM TCF003_PERIODOFACT WHERE CD_PERIODOFACT = @pi_pf_desde AND CD_PROVEEDOR = @pi_cd_proveedor
      SELECT @fhFin = FH_HASTA FROM TCF003_PERIODOFACT WHERE CD_PERIODOFACT = @pi_pf_hasta AND CD_PROVEEDOR = @pi_cd_proveedor

	  --validacion de los periodos
		if(@fhInicio > @fhFin)
		begin
		set @po_c_error = 1
		set @po_d_error = ' Periodo Hasta no puede ser menor al Periodo Desde '
		return
		end
    end

    set @po_c_error = @@error

    if(@po_c_error <> 0)
    begin
        set @po_c_error = 1
        set @po_d_error = ' Error en validacion de periodos '
        return
    end

    set @po_c_error = 0 
    --print  @fhInicio
    --print @fhFin
    if (@pi_cd_conciliacion = 0 or @pi_cd_conciliacion = null )
       begin
          set @pi_cd_conciliacion = -1
       end

    SELECT  t14.CD_CONCILIACION
           , t14.CD_ORDEN
           , t14.CD_REMITO
           , t13.CD_SECTOR
           , t13.CD_PERIODOFACT
           , t03.ST_ESTADO as 'ST_PERIODOFACT'
           , t13.CD_PRODUCTO
           , t05.NB_PRODUCTO 
           , t14.NB_PIEZADESDE
           , t14.NB_PIEZAHASTA
           , t14.CT_DIFERENCIA
           , t14.CD_UNIVAL 
           , t14.IM_PRECIOTOTAL
		   , t05.CD_TIPVAL
           , t14.ST_DIFERENCIA
           , CONVERT(varchar(10),t14.FH_ALTA,103) as 'FH_ALTA'
           , t14.NB_USUARIOALTA 
           , t14.NB_OBSERVACIONES 
           , t14.TP_SOLUCION
    FROM TCF014_DIFCONCILIA t14
    INNER JOIN TCF013_CONCILIA t13
      ON t14.CD_CONCILIACION = t13.CD_CONCILIACION
    INNER JOIN TCF005_PRODUCTO t05
      ON t13.CD_PRODUCTO = t05.CD_PRODUCTO 
      AND t13.CD_PROVEEDOR = t05.CD_PROVEEDOR 
    INNER JOIN TCF003_PERIODOFACT t03 
      ON t13.CD_PROVEEDOR = t03.CD_PROVEEDOR 
      AND t13.CD_PERIODOFACT = t03.CD_PERIODOFACT
    WHERE
          t13.CD_PROVEEDOR =  (case when @pi_cd_proveedor is null or @pi_cd_proveedor='0' then t13.CD_PROVEEDOR else @pi_cd_proveedor end)   
         and t14.CD_CONCILIACION =  (case when @pi_cd_conciliacion = -1 then t14.CD_CONCILIACION else  @pi_cd_conciliacion end)
          and t13.CD_SECTOR =  (case when @pi_cd_sector is null or @pi_cd_sector='0' then t13.CD_SECTOR else @pi_cd_sector end)
          and t13.CD_PRODUCTO =  (case when @pi_cd_producto is null or @pi_cd_producto='0' then t13.CD_PRODUCTO else @pi_cd_producto end)
          and t14.ST_DIFERENCIA =  (case when @pi_st_diferencia is null or @pi_st_diferencia='0' then t14.ST_DIFERENCIA else @pi_st_diferencia end)
          and t14.TP_SOLUCION =  (case when @pi_tp_solucion is null or @pi_tp_solucion='0' then t14.TP_SOLUCION else @pi_tp_solucion  end)
          and t03.FH_DESDE >= (case when @pi_pf_desde is null or @pi_pf_desde='0' then t03.FH_DESDE else @fhInicio end) 
          and t03.FH_HASTA <= (case when @pi_pf_hasta is null or @pi_pf_hasta='0' then t03.FH_HASTA else @fhFin end)
  end
    set @po_c_error = @@error
    if(@po_c_error <> 0)
    begin
        set @po_c_error = 1
        set @po_d_error = ' Error en Consulta de Diferencias de Conciliaciones '
        return
    end
go 

Grant Execute on dbo.sp_CFS_consulta_dif_concilia to RolTrnCFACSERV 
go

sp_procxmode 'sp_CFS_consulta_dif_concilia', anymode
go 

