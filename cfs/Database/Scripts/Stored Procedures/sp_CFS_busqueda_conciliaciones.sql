
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_busqueda_conciliaciones;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_busqueda_conciliaciones;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_busqueda_conciliaciones' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_busqueda_conciliaciones

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_busqueda_conciliaciones;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE sp_CFS_busqueda_conciliaciones(



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

    /*

    if(@cd_conciliacion != (-1)) begin

        if((select count(c.CD_CONCILIACION) from TCF014_DIFCONCILIA c where c.CD_CONCILIACION = @cd_conciliacion) > 0)
            SET @sdif = 'CON DIFERENCIAS'
        
        select 
            c.CD_CONCILIACION,

            c.CD_SECTOR,

            c.CD_PERIODOFACT,

            t03.ST_ESTADO as 'ST_PERIODOFACT',

            c.CD_PRODUCTO,            

            prod.NB_PRODUCTO descripcion,

            c.ST_CONCILIACION,

            c.ST_IGNORAVAL,

            @sdif estadoDif,

            c.NB_OBSERVACIONES,

            (CONVERT(VARCHAR,c.FH_CONCILIACION,103)) FH_CONCILIACION,

            c.NB_USUARIOCONCIL

            from TCF013_CONCILIA c 

                inner join TCF005_PRODUCTO prod on prod.CD_PRODUCTO = c.CD_PRODUCTO

                INNER JOIN TCF003_PERIODOFACT t03 ON c.CD_PROVEEDOR = t03.CD_PROVEEDOR AND c.CD_PERIODOFACT = t03.CD_PERIODOFACT

            where c.CD_CONCILIACION = @cd_conciliacion

    end



    else begin */

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

        if(@stDiferencia = 'true') begin

            SET @dif = 'CON DIFERENCIAS'

            SELECT  c.CD_CONCILIACION,

                c.CD_SECTOR,

                c.CD_PERIODOFACT,

                c.CD_PRODUCTO,

                t03.ST_ESTADO,

                t05.NB_PRODUCTO descripcion,

                c.ST_CONCILIACION,

                c.ST_IGNORAVAL,

                @dif estadoDif,

                c.NB_OBSERVACIONES,

                (CONVERT(VARCHAR,c.FH_CONCILIACION,103)) FH_CONCILIACION,

                c.NB_USUARIOCONCIL

            FROM TCF013_CONCILIA c

                INNER JOIN TCF005_PRODUCTO t05 ON c.CD_PRODUCTO = t05.CD_PRODUCTO AND c.CD_PROVEEDOR = t05.CD_PROVEEDOR 

                INNER JOIN TCF003_PERIODOFACT t03 ON c.CD_PROVEEDOR = t03.CD_PROVEEDOR AND c.CD_PERIODOFACT = t03.CD_PERIODOFACT

                INNER JOIN TCF014_DIFCONCILIA df ON @stDiferencia != '' and @stDiferencia = 'true' and df.CD_CONCILIACION = c.CD_CONCILIACION

            WHERE

                c.CD_PROVEEDOR =  (case when @pi_cdProveedor is null or @pi_cdProveedor='' then c.CD_PROVEEDOR else @pi_cdProveedor end)

                and c.CD_CONCILIACION =  (case when @cd_conciliacion = -1 then c.CD_CONCILIACION else  @cd_conciliacion end)

                and c.CD_SECTOR =  (case when @pi_cdSector is null or @pi_cdSector='' then c.CD_SECTOR else @pi_cdSector end)

                and c.CD_PRODUCTO =  (case when @cdProducto is null or @cdProducto='' then c.CD_PRODUCTO else @cdProducto end)

                and c.ST_CONCILIACION =  (case when @estadoDif is null or @estadoDif='' then c.ST_CONCILIACION else @estadoDif end)

                and t03.FH_DESDE >= (case when @pi_fhDesde is null or @pi_fhDesde='' then t03.FH_DESDE else @fhInicio end) 

                and t03.FH_HASTA <= (case when @pi_fhHasta is null or @pi_fhHasta='' then t03.FH_HASTA else @fhFin end)
                order by c.CD_CONCILIACION asc
        end

        else if (@stDiferencia = 'false') begin

            SET  @dif = 'SIN DIFERENCIAS'

            SELECT  c.CD_CONCILIACION,

                c.CD_SECTOR,

                c.CD_PERIODOFACT,

                t03.ST_ESTADO,

                c.CD_PRODUCTO,

                t05.NB_PRODUCTO descripcion,

                c.ST_CONCILIACION,

                c.ST_IGNORAVAL,

                @dif estadoDif,

                c.NB_OBSERVACIONES,

                (CONVERT(VARCHAR,c.FH_CONCILIACION,103)) FH_CONCILIACION,

                c.NB_USUARIOCONCIL

            FROM TCF013_CONCILIA c

                INNER JOIN TCF005_PRODUCTO t05 ON c.CD_PRODUCTO = t05.CD_PRODUCTO AND c.CD_PROVEEDOR = t05.CD_PROVEEDOR 

                INNER JOIN TCF003_PERIODOFACT t03 ON c.CD_PROVEEDOR = t03.CD_PROVEEDOR AND c.CD_PERIODOFACT = t03.CD_PERIODOFACT

            WHERE

                c.CD_PROVEEDOR =  (case when @pi_cdProveedor is null or @pi_cdProveedor='' then c.CD_PROVEEDOR else @pi_cdProveedor end)

                and c.CD_CONCILIACION =  (case when @cd_conciliacion = -1 then c.CD_CONCILIACION else  @cd_conciliacion end)

                and c.CD_SECTOR =  (case when @pi_cdSector is null or @pi_cdSector='' then c.CD_SECTOR else @pi_cdSector end)

                and c.CD_PRODUCTO =  (case when @cdProducto is null or @cdProducto='' then c.CD_PRODUCTO else @cdProducto end)

                and c.ST_CONCILIACION =  (case when @estadoDif is null or @estadoDif='' then c.ST_CONCILIACION else @estadoDif end)

                and t03.FH_DESDE >= (case when @pi_fhDesde is null or @pi_fhDesde='' then t03.FH_DESDE else @fhInicio end) 

                and t03.FH_HASTA <= (case when @pi_fhHasta is null or @pi_fhHasta='' then t03.FH_HASTA else @fhFin end)
                order by c.CD_CONCILIACION asc
        end

        else begin

            SELECT  distinct c.CD_CONCILIACION,

                c.CD_SECTOR,

                c.CD_PERIODOFACT,

                t03.ST_ESTADO,

                c.CD_PRODUCTO,

                t05.NB_PRODUCTO descripcion,

                c.ST_CONCILIACION,

                c.ST_IGNORAVAL,

                (case when df.CD_CONCILIACION is null then 'SIN DIFERENCIAS' else 'CON DIFERENCIAS' end) estadoDif,

                c.NB_OBSERVACIONES,

                (CONVERT(VARCHAR,c.FH_CONCILIACION,103)) FH_CONCILIACION,

                c.NB_USUARIOCONCIL

   

                FROM TCF013_CONCILIA c



                    INNER JOIN TCF005_PRODUCTO t05 ON c.CD_PRODUCTO = t05.CD_PRODUCTO AND c.CD_PROVEEDOR = t05.CD_PROVEEDOR 

                    INNER JOIN TCF003_PERIODOFACT t03 ON c.CD_PROVEEDOR = t03.CD_PROVEEDOR AND c.CD_PERIODOFACT = t03.CD_PERIODOFACT

                    LEFT JOIN TCF014_DIFCONCILIA df on df.CD_CONCILIACION = c.CD_CONCILIACION 



                WHERE 

                    c.CD_PROVEEDOR =  (case when @pi_cdProveedor is null or @pi_cdProveedor='' then c.CD_PROVEEDOR else @pi_cdProveedor end)

                    and c.CD_CONCILIACION =  (case when @cd_conciliacion = -1 then c.CD_CONCILIACION else  @cd_conciliacion end)

                    and c.CD_SECTOR =  (case when @pi_cdSector is null or @pi_cdSector='' then c.CD_SECTOR else @pi_cdSector end)

                    and c.CD_PRODUCTO =  (case when @cdProducto is null or @cdProducto='' then c.CD_PRODUCTO else @cdProducto end)

                    and c.ST_CONCILIACION =  (case when @estadoDif is null or @estadoDif='' then c.ST_CONCILIACION else @estadoDif end)

                    and t03.FH_DESDE >= (case when @pi_fhDesde is null or @pi_fhDesde='' then t03.FH_DESDE else @fhInicio end) 

                    and t03.FH_HASTA <= (case when @pi_fhHasta is null or @pi_fhHasta='' then t03.FH_HASTA else @fhFin end)
        
                order by c.CD_CONCILIACION asc
                   -- and ((@pi_fhDesde is null or @pi_fhDesde = '') or c.CD_PERIODOFACT between @fhInicio and @fhFin )

                   --  (@id_cliente is null       or ei.id_cliente     = @id_cliente)
        end
    -- end
end
go 

sp_procxmode 'sp_CFS_busqueda_conciliaciones', unchained
go 

setuser
go 

