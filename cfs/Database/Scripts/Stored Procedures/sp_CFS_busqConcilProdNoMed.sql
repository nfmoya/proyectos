
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_busqConcilProdNoMed;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_busqConcilProdNoMed;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_busqConcilProdNoMed' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_busqConcilProdNoMed

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_busqConcilProdNoMed;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE sp_CFS_busqConcilProdNoMed(
@pi_cdProveedor     varchar(12),
@pi_cdSector        varchar(12),
@pi_fhDesde      varchar(12),
@pi_fhHasta       varchar(12),
@cdProducto    varchar(12),
@estadoDif    varchar(12),
@stDiferencia   varchar(12),
@cd_conciliacion  int,
@po_c_error       typ_c_error output,
@po_d_error       typ_d_error output )
as
begin  
    set @po_c_error = 0 
    set @po_d_error = null
    DECLARE @dif varchar(20),

            @sdif varchar(20),

            @fhInicio date,

            @fhFin date

    SET @dif = 'CON DIFERENCIAS', 
        @sdif = 'SIN DIFERENCIAS'
    /**

        ** Obtengo las fechas de los periodos en caso de se haya seleccionado una
        ** Sino las seteo en NULL
        */

        if(@pi_fhDesde is not null or @pi_fhDesde <> '' and @pi_fhHasta is not null or @pi_fhHasta <> '')

        begin
            SELECT  @fhInicio = t03.FH_DESDE FROM TCF003_PERIODOFACT t03 WHERE t03.CD_PERIODOFACT = @pi_fhDesde and t03.CD_PROVEEDOR = @pi_cdProveedor
            SELECT @fhFin = t03.FH_HASTA FROM TCF003_PERIODOFACT t03 WHERE t03.CD_PERIODOFACT = @pi_fhHasta  and t03.CD_PROVEEDOR = @pi_cdProveedor
        end
        --validacion de los periodos
        if(@fhInicio > @fhFin)
        begin
            set @po_c_error = 1
            set @po_d_error = ' Periodo Hasta no puede ser menor al Periodo Desde '
            return
        end
             SELECT  distinct c.CD_CONCILIACION,
                c.CD_SECTOR,
                c.CD_PERIODOFACT,
                t03.ST_ESTADO,
                '' as CD_PRODUCTO,
                '' as descripcion,
                c.ST_CONCILIACION,
                '' as ST_IGNORAVAL,
                '' as estadoDif,
                '' as NB_OBSERVACIONES,
                (CONVERT(VARCHAR,c.FH_CONCILIACION,103)) FH_CONCILIACION,
                c.NB_USUARIOCONCIL
                FROM TCF029_NOMEDIBLES c
                    INNER JOIN TCF003_PERIODOFACT t03 ON c.CD_PROVEEDOR = t03.CD_PROVEEDOR AND c.CD_PERIODOFACT = t03.CD_PERIODOFACT
                   -- LEFT JOIN TCF030_NOMEDIBLES_PROD df on df.CD_CONCILIACION = c.CD_CONCILIACION 
                    --INNER JOIN TCF005_PRODUCTO t05 ON df.CD_PRODUCTO = t05.CD_PRODUCTO AND c.CD_PROVEEDOR = t05.CD_PROVEEDOR 
                WHERE 
                    c.CD_PROVEEDOR =  (case when @pi_cdProveedor is null or @pi_cdProveedor='' then c.CD_PROVEEDOR else @pi_cdProveedor end)
                    and c.CD_CONCILIACION =  (case when @cd_conciliacion = -1 then c.CD_CONCILIACION else  @cd_conciliacion end)
                    and c.CD_SECTOR =  (case when @pi_cdSector is null or @pi_cdSector='' then c.CD_SECTOR else @pi_cdSector end)
                   -- and df.CD_PRODUCTO =  (case when @cdProducto is null or @cdProducto='' then df.CD_PRODUCTO else @cdProducto end)
                    and c.ST_CONCILIACION =  (case when @estadoDif is null or @estadoDif='' then c.ST_CONCILIACION else @estadoDif end)
                    and t03.FH_DESDE >= (case when @pi_fhDesde is null or @pi_fhDesde='' then t03.FH_DESDE else @fhInicio end) 
                    and t03.FH_HASTA <= (case when @pi_fhHasta is null or @pi_fhHasta='' then t03.FH_HASTA else @fhFin end)
                order by c.CD_CONCILIACION asc
                   -- and ((@pi_fhDesde is null or @pi_fhDesde = '') or c.CD_PERIODOFACT between @fhInicio and @fhFin )
                   --  (@id_cliente is null       or ei.id_cliente     = @id_cliente)

end
go 


sp_procxmode 'sp_CFS_busqConcilProdNoMed', anymode
go 

setuser
go 