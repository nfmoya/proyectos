
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_busqueda_conciliacion;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_busqueda_conciliacion;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_busqueda_conciliacion' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_busqueda_conciliacion

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_busqueda_conciliacion;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_busqueda_conciliacion(
@cd_proveedor     varchar(6),
@cd_sector        varchar(6),
@cd_producto      varchar(12),
@cd_periodo       varchar(6),
@fhRemitoDesde    varchar(20),
@fhRemitoHasta    varchar(20),
@fhFinServDesde   varchar(20),
@fhFinServHasta   varchar(20),
@st_conciliacion  varchar(3),
@cd_conciliacion  int,
@nu_billetes      int,
@po_c_error       typ_c_error output,
@po_d_error       typ_d_error output )
as

begin  
    SET ARITHABORT NUMERIC_TRUNCATION OFF
    declare 
    @fr_desde  smalldatetime,
    @fr_hasta  smalldatetime,
    @fs_desde  smalldatetime,
    @fs_hasta  smalldatetime,
    @st_servsinconcil char(1),
    @st_factsinconcil char(1),
    @im_cotiza decimal(12,4),
	@cd_grupoproducto char(12)

--  select @st_servsinconcil = ST_SERVSINCONCIL, @st_factsinconcil = ST_FACTSINCONCIL
--  from TCF005_PRODUCTO where CD_PROVEEDOR = @cd_proveedor and CD_PRODUCTO = @cd_producto and ST_HABILITADO = 'S'

    set @st_servsinconcil = (select ST_SERVSINCONCIL from TCF005_PRODUCTO where CD_PROVEEDOR = @cd_proveedor and CD_PRODUCTO = @cd_producto) -- and ST_HABILITADO = 'S')

    set @st_factsinconcil = (select ST_FACTSINCONCIL from TCF005_PRODUCTO where CD_PROVEEDOR = @cd_proveedor and CD_PRODUCTO = @cd_producto) -- and ST_HABILITADO = 'S')

    set @cd_grupoproducto = (select CD_GRUPOPRODUCTO from TCF005_PRODUCTO where CD_PROVEEDOR = @cd_proveedor and CD_PRODUCTO = @cd_producto) -- and ST_HABILITADO = 'S')

    set @im_cotiza = 1

	set @po_c_error = 0  
	set @po_d_error = null
    if @cd_conciliacion = 0
       begin
          set @cd_conciliacion = null
       end

    if @fhRemitoDesde is not null and @fhRemitoDesde != ''
       begin  
          execute sp_convierte_char_en_fecha @pi_fecha_char     = @fhRemitoDesde,  
                                             @po_fecha_datetime = @fr_desde   output,  
                                             @po_c_error        = @po_c_error output,  
                                             @po_d_error        = @po_d_error output  
          if (@po_c_error  <> 0)  
              begin  
                 set @po_d_error = 'Error llamando a sp_convierte_char_en_fecha : ' + @po_d_error	  
                 return         
              end
       end
    if @fhRemitoHasta is not null and @fhRemitoHasta != ''   
       begin  
          execute sp_convierte_char_en_fecha @pi_fecha_char     = @fhRemitoHasta,  
                                             @po_fecha_datetime = @fr_hasta   output,  
                                             @po_c_error        = @po_c_error output,  
                                             @po_d_error        = @po_d_error output  
          if (@po_c_error  <> 0)  
              begin  
                 set @po_d_error = 'Error llamando a sp_convierte_char_en_fecha : ' + @po_d_error	  
                 return         
              end
       end
    if @fhFinServDesde is not null and @fhFinServDesde != ''
       begin  
          execute sp_convierte_char_en_fecha @pi_fecha_char     = @fhFinServDesde,  
                                             @po_fecha_datetime = @fs_desde   output,  
                                             @po_c_error        = @po_c_error output,  
                                             @po_d_error        = @po_d_error output  
          if (@po_c_error  <> 0)  
              begin  
                 set @po_d_error = 'Error llamando a sp_convierte_char_en_fecha : ' + @po_d_error	  
                 return         
              end
       end
    if @fhFinServHasta is not null and @fhFinServHasta != ''
       begin  
          execute sp_convierte_char_en_fecha @pi_fecha_char     = @fhFinServHasta,  
                                             @po_fecha_datetime = @fs_hasta   output,  
                                             @po_c_error        = @po_c_error output,  
                                             @po_d_error        = @po_d_error output  
          if (@po_c_error  <> 0)  
              begin  
                 set @po_d_error = 'Error llamando a sp_convierte_char_en_fecha : ' + @po_d_error	  
                 return         
              end
       end
	  
    create table #APAREO_CONCILIACION
    (CD_REMITO_PRES        varchar(13)    null, 
     FH_REMITO             date           null, 
     FH_FIN_SERV           date           null, 
     CT_SERVPREST          decimal(16,4)  null, 
     IM_PRECIOTOTAL_PRES   decimal(18,4)  null, 
	 CD_TIPVAL             char(3)        null,
	 CD_MONEDA             char(3)        null,
     CD_CONCILIACION_PRES  int            null,
     CHK_PRES              char(1)        null, 
     CD_REMITO_FACT        varchar(13)    null, 
     TP_COMPROBANTE        char(6)        null, 
     NU_COMPROBANTE        varchar(13)    null, 
     CT_SERVFACT           decimal(16,4)  null, 
     IM_PRECIOTOTAL_FACT   decimal(18,4)  null, 
     CD_CONCILIACION_FACT  int            null, 
     CHK_FACT              char(1)        null, 
     CT_SERVFACT_DIFE      decimal(16,4)  null, 
     IM_SERVFACT_DIFE      decimal(18,4)  null, 
     CD_ORDEN_PRES         bigint         null,
     CD_LOTESERV           int            null,
     CD_ORDENSERV          int            null, 
     CD_SECTORSOL          varchar(15)    null,
     CD_SECTORCONTROL      varchar(15)    null,
     CD_PRODUCTOPRES       varchar(12)    null,
     CD_UNIVAL             varchar(6)     null,
     IM_PRECIOUNITPRES     decimal(12,4)  null, 
     NB_PIEZADESDEPRES     varchar(25)    null, 
     NB_PIEZAHASTAPRES     varchar(25)    null, 
     CD_REMITOPADRE        varchar(13)    null, 
     NB_ATRIBUTOREF1       varchar(30)    null, 
     NB_ATRIBUTOREF2       varchar(10)    null, 
     NB_OBSERVACIONES      varchar(50)    null, 
     ST_LOTEDET            char(1)        null, 
     FH_MODIFICACION       date           null,
     NB_USUARIOMODIF       varchar(8)     null,
     CD_LOTEFACT           int            null,
     CD_ORDENFACT          int            null,
     CD_PROVEEDOR          varchar(6)         ,
     CD_SECTOR             varchar(6)         ,
     CD_PRODUCTO           varchar(12)        ,
     CD_PERIODO            varchar(6)	      ,
     IM_COTIZA             decimal(12,4)  null,
     FH_COTIZA             date           null
    )

    if (@cd_conciliacion is not null) 
       begin
          set @cd_proveedor = '0'
          --set @cd_sector    = '0'
          set @cd_producto  = '0'
          set @cd_periodo   = '0'
          set @fr_desde     = null
          set @fr_hasta     = null
          set @fs_desde     = null
          set @fs_hasta     = null

          select @cd_proveedor    = CD_PROVEEDOR, 
                -- @cd_sector       = CD_SECTOR, 
                 @cd_producto     = CD_PRODUCTO, 
                 @cd_periodo      = CD_PERIODOFACT, 
                 @st_conciliacion = ST_CONCILIACION,
                 @fr_desde        = FH_REMITO_DESDE, 
                 @fr_hasta        = FH_REMITO_HASTA, 
                 @fs_desde        = FH_FIN_SERV_DESDE, 
                 @fs_hasta        = FH_FIN_SERV_HASTA
          from TCF013_CONCILIA 
          where CD_CONCILIACION = @cd_conciliacion 
          --Agregado David
            and CD_SECTOR =  (case when @cd_sector = '0' then CD_SECTOR else @cd_sector end)
       end

    -- OBTENGO LOS SERVICIOS PRESTADOS --
    if (@cd_conciliacion is not null)
       begin
            SELECT temp1.CD_REMITO_PRES, temp1.FH_REMITO, temp1.FH_FIN_SERV, temp1.CT_SERVPREST, temp1.IM_PRECIOTOTAL_PRES, temp1.CD_TIPVAL, temp1.CD_MONEDA,
                   temp1.CD_CONCILIACION_PRES, temp1.CD_ORDEN_PRES, temp1.CD_LOTESERV, temp1.CD_ORDENSERV, temp1.CD_SECTORSOL, temp1.CD_SECTORCONTROL, temp1.CD_PRODUCTOPRES, temp1.CD_UNIVAL,
                   temp1.IM_PRECIOUNITPRES, temp1.NB_PIEZADESDEPRES, temp1.NB_PIEZAHASTAPRES, temp1.CD_REMITOPADRE, temp1.NB_ATRIBUTOREF1, temp1.NB_ATRIBUTOREF2, temp1.NB_OBSERVACIONES,
                   temp1.ST_LOTEDET, temp1.FH_MODIFICACION, temp1.NB_USUARIOMODIF
            INTO #TEMP_PRES1 FROM (
				-- TIPO VAL 1
				select D.CD_REMITO AS CD_REMITO_PRES, D.FH_REMITO, D.FH_FIN_SERV, D.CT_SERVPREST, 				
					  case when @st_conciliacion != 'APR' and @cd_grupoproducto != 'CON_MONTO_TO' then 
							(isnull(D.CT_SERVPREST,0) * isnull(P.NU_PRECIOUNIVAL,0)) 
						else 
							D.IM_PRECIOTOTAL 
					  end AS IM_PRECIOTOTAL_PRES,
					  T.CD_TIPVAL, T.CD_MONEDA, D.CD_CONCILIACION AS CD_CONCILIACION_PRES, D.CD_ORDEN AS CD_ORDEN_PRES,
					  D.CD_LOTESERV, D.CD_ORDEN AS CD_ORDENSERV, D.CD_SECTORSOL, D.CD_SECTOR AS CD_SECTORCONTROL,
					  D.CD_PRODUCTO AS CD_PRODUCTOPRES, D.CD_UNIVAL, 
					  case 
						when @st_conciliacion != 'APR' and @cd_grupoproducto != 'CON_MONTO_TO' then 
							isnull(P.NU_PRECIOUNIVAL,0) 
						else 
							D.IM_PRECIOUNIT 
					  end AS IM_PRECIOUNITPRES, 
					  D.NB_PIEZADESDE AS NB_PIEZADESDEPRES, D.NB_PIEZAHASTA AS NB_PIEZAHASTAPRES,
					  D.CD_REMITOPADRE, D.NB_ATRIBUTOREF1, D.NB_ATRIBUTOREF2, D.NB_OBSERVACIONES,
					  D.ST_LOTEDET, D.FH_MODIFICACION, D.NB_USUARIOMODIF
				from TCF009_SERVPRESCAB C
				inner join TCF010_SERVPRESDET D on C.CD_LOTESERV = D.CD_LOTESERV
				left join TCF013_CONCILIA L on D.CD_CONCILIACION = L.CD_CONCILIACION
				left join TCF006_PRECIOPROD P on P.CD_PROVEEDOR = C.CD_PROVEEDOR and P.CD_PRODUCTO = D.CD_PRODUCTO 
						 and (case when @fhFinServHasta is not null then D.FH_FIN_SERV else D.FH_REMITO end) between P.FH_DESDE and P.FH_HASTA and P.ST_HABILITADO = 'S'
				left join TCF005_PRODUCTO T on T.CD_PROVEEDOR = C.CD_PROVEEDOR and T.CD_PRODUCTO = D.CD_PRODUCTO and T.ST_HABILITADO = 'S'
				where D.CD_CONCILIACION = @cd_conciliacion  and @cd_conciliacion is not null
				 --Agregado David
				 and D.CD_SECTORSOL  = (case when @cd_sector = '0' then D.CD_SECTORSOL else @cd_sector end)
				 and T.CD_TIPVAL     = '1'
				or (
						isnull(L.ST_CONCILIACION,'PEN') = 'PEN'
					and @st_conciliacion   != 'APR'
					and C.CD_PROVEEDOR     = @cd_proveedor
					and C.ST_LOTECAB       = 'ACT'
					and D.CD_SECTORSOL     = @cd_sector
					and D.CD_PRODUCTO      = @cd_producto
					and (D.FH_REMITO       >= @fr_desde or @fr_desde is null)
					and (D.FH_REMITO       <= @fr_hasta or @fr_hasta is null)
					and (D.FH_FIN_SERV     >= @fs_desde or @fs_desde is null)
					and (D.FH_FIN_SERV     <= @fs_hasta or @fs_hasta is null)
					and D.ST_LOTEDET       = 'C'
					and T.CD_GRUPOPRODUCTO != 'NO_CON'
					and T.CD_TIPVAL        = '1'
				)
				UNION ALL
				-- TIPO VAL 2
				select D.CD_REMITO AS CD_REMITO_PRES, D.FH_REMITO, D.FH_FIN_SERV, D.CT_SERVPREST, 
					  case 
						when @st_conciliacion != 'APR' and @cd_grupoproducto != 'CON_MONTO_TO' then 
							case when R.ST_PRECIOFIJO = 'N' then 
								(isnull(D.CT_SERVPREST,0) * isnull(R.NU_PRECIOUNIVAL,0))
                            else
								isnull(R.NU_PRECIOUNIVAL,0)
                            end
						else 
							D.IM_PRECIOTOTAL 
					  end AS IM_PRECIOTOTAL_PRES,
					  T.CD_TIPVAL, T.CD_MONEDA, D.CD_CONCILIACION AS CD_CONCILIACION_PRES, D.CD_ORDEN AS CD_ORDEN_PRES,
					  D.CD_LOTESERV, D.CD_ORDEN AS CD_ORDENSERV, D.CD_SECTORSOL, D.CD_SECTOR AS CD_SECTORCONTROL,
					  D.CD_PRODUCTO AS CD_PRODUCTOPRES, D.CD_UNIVAL, 
					  case 
						when @st_conciliacion != 'APR' and @cd_grupoproducto != 'CON_MONTO_TO' then 
							isnull(R.NU_PRECIOUNIVAL,0) 
						else 
							D.IM_PRECIOUNIT 
					  end AS IM_PRECIOUNITPRES, 
					  D.NB_PIEZADESDE AS NB_PIEZADESDEPRES, D.NB_PIEZAHASTA AS NB_PIEZAHASTAPRES,
					  D.CD_REMITOPADRE, D.NB_ATRIBUTOREF1, D.NB_ATRIBUTOREF2, D.NB_OBSERVACIONES,
					  D.ST_LOTEDET, D.FH_MODIFICACION, D.NB_USUARIOMODIF
				from TCF009_SERVPRESCAB C
				inner join TCF010_SERVPRESDET D on C.CD_LOTESERV = D.CD_LOTESERV
				left join TCF013_CONCILIA L on D.CD_CONCILIACION = L.CD_CONCILIACION
				left join TCF026_PRODPERIODO P on P.CD_PROVEEDOR = C.CD_PROVEEDOR and P.CD_PRODUCTO = D.CD_PRODUCTO 
						 and (case when @fhFinServHasta is not null then D.FH_FIN_SERV else D.FH_REMITO end) between P.FH_DESDE and P.FH_HASTA and P.ST_HABILITADO = 'S'
				left join TCF027_PRODPERTARIFA R on R.CD_PROVEEDOR = P.CD_PROVEEDOR and R.CD_PRODUCTO = P.CD_PRODUCTO and R.FH_DESDE = P.FH_DESDE 
				                                                                   and isnull(D.CT_SERVPREST,0) between R.NU_CANTDESDE and R.NU_CANTHASTA and R.ST_HABILITADO = 'S'
				left join TCF005_PRODUCTO T on T.CD_PROVEEDOR = C.CD_PROVEEDOR and T.CD_PRODUCTO = D.CD_PRODUCTO and T.ST_HABILITADO = 'S'
				where D.CD_CONCILIACION = @cd_conciliacion  and @cd_conciliacion is not null
				 --Agregado David
				 and D.CD_SECTORSOL  = (case when @cd_sector = '0' then D.CD_SECTORSOL else @cd_sector end)
				 and T.CD_TIPVAL     = '2'
				or (
						isnull(L.ST_CONCILIACION,'PEN') = 'PEN'
					and @st_conciliacion   != 'APR'
					and C.CD_PROVEEDOR     = @cd_proveedor
					and C.ST_LOTECAB       = 'ACT'
					and D.CD_SECTORSOL     = @cd_sector
					and D.CD_PRODUCTO      = @cd_producto
					and (D.FH_REMITO       >= @fr_desde or @fr_desde is null)
					and (D.FH_REMITO       <= @fr_hasta or @fr_hasta is null)
					and (D.FH_FIN_SERV     >= @fs_desde or @fs_desde is null)
					and (D.FH_FIN_SERV     <= @fs_hasta or @fs_hasta is null)
					and D.ST_LOTEDET       = 'C'
					and T.CD_GRUPOPRODUCTO != 'NO_CON'
					and T.CD_TIPVAL        = '2'
				)
				UNION ALL
				-- TIPO VAL 3
				select D.CD_REMITO AS CD_REMITO_PRES, D.FH_REMITO, D.FH_FIN_SERV, D.CT_SERVPREST, 
					  case 
						when @st_conciliacion != 'APR' and @cd_grupoproducto != 'CON_MONTO_TO' then 
							(isnull(D.CT_SERVPREST,0) * (isnull(R.NU_PRECIOUNIVAL,0)/100)) 
						else 
							D.IM_PRECIOTOTAL 
					  end AS IM_PRECIOTOTAL_PRES, 
					  T.CD_TIPVAL, T.CD_MONEDA, D.CD_CONCILIACION AS CD_CONCILIACION_PRES, D.CD_ORDEN AS CD_ORDEN_PRES,
					  D.CD_LOTESERV, D.CD_ORDEN AS CD_ORDENSERV, D.CD_SECTORSOL, D.CD_SECTOR AS CD_SECTORCONTROL,
					  D.CD_PRODUCTO AS CD_PRODUCTOPRES, D.CD_UNIVAL, 
					  case 
						when @st_conciliacion != 'APR' and @cd_grupoproducto != 'CON_MONTO_TO' then 
							isnull(R.NU_PRECIOUNIVAL,0) 
						else 
							D.IM_PRECIOUNIT 
					  end AS IM_PRECIOUNITPRES, 
					  D.NB_PIEZADESDE AS NB_PIEZADESDEPRES, D.NB_PIEZAHASTA AS NB_PIEZAHASTAPRES,
					  D.CD_REMITOPADRE, D.NB_ATRIBUTOREF1, D.NB_ATRIBUTOREF2, D.NB_OBSERVACIONES,
					  D.ST_LOTEDET, D.FH_MODIFICACION, D.NB_USUARIOMODIF
				from TCF009_SERVPRESCAB C
				inner join TCF010_SERVPRESDET D on C.CD_LOTESERV = D.CD_LOTESERV
				left join TCF013_CONCILIA L on D.CD_CONCILIACION = L.CD_CONCILIACION
				left join TCF026_PRODPERIODO P on P.CD_PROVEEDOR = C.CD_PROVEEDOR and P.CD_PRODUCTO = D.CD_PRODUCTO 
						 and (case when @fhFinServHasta is not null then D.FH_FIN_SERV else D.FH_REMITO end) between P.FH_DESDE and P.FH_HASTA and P.ST_HABILITADO = 'S'
				left join TCF027_PRODPERTARIFA R on R.CD_PROVEEDOR = P.CD_PROVEEDOR and R.CD_PRODUCTO = P.CD_PRODUCTO and R.FH_DESDE = P.FH_DESDE 
				                                                                   and isnull(D.CT_SERVPREST,0) between R.NU_CANTDESDE and R.NU_CANTHASTA and R.ST_HABILITADO = 'S'
				left join TCF005_PRODUCTO T on T.CD_PROVEEDOR = C.CD_PROVEEDOR and T.CD_PRODUCTO = D.CD_PRODUCTO and T.ST_HABILITADO = 'S'
				where D.CD_CONCILIACION = @cd_conciliacion  and @cd_conciliacion is not null
				 --Agregado David
				 and D.CD_SECTORSOL  = (case when @cd_sector = '0' then D.CD_SECTORSOL else @cd_sector end)
				 and T.CD_TIPVAL     = '3'
				or (
						isnull(L.ST_CONCILIACION,'PEN') = 'PEN'
					and @st_conciliacion   != 'APR'
					and C.CD_PROVEEDOR     = @cd_proveedor
					and C.ST_LOTECAB       = 'ACT'
					and D.CD_SECTORSOL     = @cd_sector
					and D.CD_PRODUCTO      = @cd_producto
					and (D.FH_REMITO       >= @fr_desde or @fr_desde is null)
					and (D.FH_REMITO       <= @fr_hasta or @fr_hasta is null)
					and (D.FH_FIN_SERV     >= @fs_desde or @fs_desde is null)
					and (D.FH_FIN_SERV     <= @fs_hasta or @fs_hasta is null)
					and D.ST_LOTEDET       = 'C'
					and T.CD_GRUPOPRODUCTO != 'NO_CON'
					and T.CD_TIPVAL        = '3'
				)
			) as temp1 

            INSERT INTO #APAREO_CONCILIACION
            select P.CD_REMITO_PRES, P.FH_REMITO, P.FH_FIN_SERV, P.CT_SERVPREST, P.IM_PRECIOTOTAL_PRES, P.CD_TIPVAL, P.CD_MONEDA, P.CD_CONCILIACION_PRES, '0' AS CHKPRES, 
                   '' AS CD_REMITO_FACT, '' AS TP_COMPROBANTE, '' AS NU_COMPROBANTE, 0 AS CT_SERVFACT, 0 AS IM_PRECIOTOTAL_FACT, null AS CD_CONCILIACION_FACT, '0' AS CHK_FACT, 
                   0 AS CT_SERVFACT_DIFE, 0 AS IM_SERVFACT_DIFE, P.CD_ORDEN_PRES, P.CD_LOTESERV, P.CD_ORDENSERV, P.CD_SECTORSOL, P.CD_SECTORCONTROL, P.CD_PRODUCTOPRES, 
                   P.CD_UNIVAL, P.IM_PRECIOUNITPRES, P.NB_PIEZADESDEPRES, P.NB_PIEZAHASTAPRES, P.CD_REMITOPADRE, P.NB_ATRIBUTOREF1, P.NB_ATRIBUTOREF2, P.NB_OBSERVACIONES, 
                   P.ST_LOTEDET, P.FH_MODIFICACION, P.NB_USUARIOMODIF, 0 AS CD_LOTEFACT, 0 AS CD_ORDENFACT, @cd_proveedor, @cd_sector, @cd_producto, @cd_periodo, @im_cotiza, null
            FROM #TEMP_PRES1 P
			order by P.CD_REMITO_PRES, P.FH_REMITO, P.CT_SERVPREST desc
       end
    else 
       begin
			SELECT temp2.CD_REMITO_PRES, temp2.FH_REMITO, temp2.FH_FIN_SERV, temp2.CT_SERVPREST, temp2.IM_PRECIOTOTAL_PRES, temp2.CD_TIPVAL, temp2.CD_MONEDA,
                   temp2.CD_CONCILIACION_PRES, temp2.CD_ORDEN_PRES, temp2.CD_LOTESERV, temp2.CD_ORDENSERV, temp2.CD_SECTORSOL, temp2.CD_SECTORCONTROL, temp2.CD_PRODUCTOPRES, temp2.CD_UNIVAL,
                   temp2.IM_PRECIOUNITPRES, temp2.NB_PIEZADESDEPRES, temp2.NB_PIEZAHASTAPRES, temp2.CD_REMITOPADRE, temp2.NB_ATRIBUTOREF1, temp2.NB_ATRIBUTOREF2, temp2.NB_OBSERVACIONES,
                   temp2.ST_LOTEDET, temp2.FH_MODIFICACION, temp2.NB_USUARIOMODIF      
            INTO #TEMP_PRES2 FROM (
				-- TIPO VAL 1
				select D.CD_REMITO AS CD_REMITO_PRES, D.FH_REMITO, D.FH_FIN_SERV, D.CT_SERVPREST, 				
					  case when @st_conciliacion != 'APR' and @cd_grupoproducto != 'CON_MONTO_TO' then 
							(isnull(D.CT_SERVPREST,0) * isnull(P.NU_PRECIOUNIVAL,0)) 
						else 
							D.IM_PRECIOTOTAL 
					  end AS IM_PRECIOTOTAL_PRES, 
					  T.CD_TIPVAL, T.CD_MONEDA, D.CD_CONCILIACION AS CD_CONCILIACION_PRES, D.CD_ORDEN AS CD_ORDEN_PRES,
					  D.CD_LOTESERV, D.CD_ORDEN AS CD_ORDENSERV, D.CD_SECTORSOL, D.CD_SECTOR AS CD_SECTORCONTROL,
					  D.CD_PRODUCTO AS CD_PRODUCTOPRES, D.CD_UNIVAL, case when @st_conciliacion != 'APR' then isnull(P.NU_PRECIOUNIVAL,0) else D.IM_PRECIOUNIT end AS IM_PRECIOUNITPRES, 
					  D.NB_PIEZADESDE AS NB_PIEZADESDEPRES, D.NB_PIEZAHASTA AS NB_PIEZAHASTAPRES,
					  D.CD_REMITOPADRE, D.NB_ATRIBUTOREF1, D.NB_ATRIBUTOREF2, D.NB_OBSERVACIONES,
					  D.ST_LOTEDET, D.FH_MODIFICACION, D.NB_USUARIOMODIF
				from TCF009_SERVPRESCAB C
				inner join TCF010_SERVPRESDET D on C.CD_LOTESERV = D.CD_LOTESERV
				left join TCF013_CONCILIA L on D.CD_CONCILIACION = L.CD_CONCILIACION
				left join TCF006_PRECIOPROD P on P.CD_PROVEEDOR = C.CD_PROVEEDOR and P.CD_PRODUCTO = D.CD_PRODUCTO
						 and (case when @fhFinServHasta is not null then D.FH_FIN_SERV else D.FH_REMITO end) between P.FH_DESDE and P.FH_HASTA and P.ST_HABILITADO = 'S'
				left join TCF005_PRODUCTO T on T.CD_PROVEEDOR = C.CD_PROVEEDOR and T.CD_PRODUCTO = D.CD_PRODUCTO and T.ST_HABILITADO = 'S'
				where  (isnull(L.ST_CONCILIACION,'PEN') != 'APR')
					and C.CD_PROVEEDOR    = @cd_proveedor
					and C.ST_LOTECAB      = 'ACT'
					and D.CD_SECTORSOL    = @cd_sector
					and D.CD_PRODUCTO     = @cd_producto
					and (D.FH_REMITO       >= @fr_desde or @fr_desde is null)
					and (D.FH_REMITO       <= @fr_hasta or @fr_hasta is null)
					and (D.FH_FIN_SERV     >= @fs_desde or @fs_desde is null)
					and (D.FH_FIN_SERV     <= @fs_hasta or @fs_hasta is null)
					and D.ST_LOTEDET      = 'C'
					and T.CD_GRUPOPRODUCTO != 'NO_CON'
					and T.CD_TIPVAL        = '1'
			UNION ALL
				-- TIPO VAL 2
				select D.CD_REMITO AS CD_REMITO_PRES, D.FH_REMITO, D.FH_FIN_SERV, D.CT_SERVPREST, 
					  case when @st_conciliacion != 'APR' then 
							case when R.ST_PRECIOFIJO = 'N' then 
								(isnull(D.CT_SERVPREST,0) * isnull(R.NU_PRECIOUNIVAL,0))
							else
								isnull(R.NU_PRECIOUNIVAL,0)
							end
					  else D.IM_PRECIOTOTAL end AS IM_PRECIOTOTAL_PRES, 
					  T.CD_TIPVAL, T.CD_MONEDA, D.CD_CONCILIACION AS CD_CONCILIACION_PRES, D.CD_ORDEN AS CD_ORDEN_PRES,
					  D.CD_LOTESERV, D.CD_ORDEN AS CD_ORDENSERV, D.CD_SECTORSOL, D.CD_SECTOR AS CD_SECTORCONTROL,
					  D.CD_PRODUCTO AS CD_PRODUCTOPRES, D.CD_UNIVAL, case when @st_conciliacion != 'APR' then isnull(R.NU_PRECIOUNIVAL,0) else D.IM_PRECIOUNIT end AS IM_PRECIOUNITPRES, 
					  D.NB_PIEZADESDE AS NB_PIEZADESDEPRES, D.NB_PIEZAHASTA AS NB_PIEZAHASTAPRES,
					  D.CD_REMITOPADRE, D.NB_ATRIBUTOREF1, D.NB_ATRIBUTOREF2, D.NB_OBSERVACIONES,
					  D.ST_LOTEDET, D.FH_MODIFICACION, D.NB_USUARIOMODIF
				from TCF009_SERVPRESCAB C
				inner join TCF010_SERVPRESDET D on C.CD_LOTESERV = D.CD_LOTESERV
				left join TCF013_CONCILIA L on D.CD_CONCILIACION = L.CD_CONCILIACION
				left join TCF026_PRODPERIODO P on P.CD_PROVEEDOR = C.CD_PROVEEDOR and P.CD_PRODUCTO = D.CD_PRODUCTO 
						 and (case when @fhFinServHasta is not null then D.FH_FIN_SERV else D.FH_REMITO end) between P.FH_DESDE and P.FH_HASTA and P.ST_HABILITADO = 'S'
				left join TCF027_PRODPERTARIFA R on R.CD_PROVEEDOR = P.CD_PROVEEDOR and R.CD_PRODUCTO = P.CD_PRODUCTO and R.FH_DESDE = P.FH_DESDE 
				                                                                   and isnull(D.CT_SERVPREST,0) between R.NU_CANTDESDE and R.NU_CANTHASTA and R.ST_HABILITADO = 'S'
				left join TCF005_PRODUCTO T on T.CD_PROVEEDOR = C.CD_PROVEEDOR and T.CD_PRODUCTO = D.CD_PRODUCTO and T.ST_HABILITADO = 'S'
				where  (isnull(L.ST_CONCILIACION,'PEN') != 'APR')
					and C.CD_PROVEEDOR    = @cd_proveedor
					and C.ST_LOTECAB      = 'ACT'
					and D.CD_SECTORSOL    = @cd_sector
					and D.CD_PRODUCTO     = @cd_producto
					and (D.FH_REMITO       >= @fr_desde or @fr_desde is null)
					and (D.FH_REMITO       <= @fr_hasta or @fr_hasta is null)
					and (D.FH_FIN_SERV     >= @fs_desde or @fs_desde is null)
					and (D.FH_FIN_SERV     <= @fs_hasta or @fs_hasta is null)
					and D.ST_LOTEDET      = 'C'
					and T.CD_GRUPOPRODUCTO != 'NO_CON'
					and T.CD_TIPVAL        = '2'
				UNION ALL
				-- TIPO VAL 3
				select D.CD_REMITO AS CD_REMITO_PRES, D.FH_REMITO, D.FH_FIN_SERV, D.CT_SERVPREST, 
					  case 
						when @st_conciliacion != 'APR' and @cd_grupoproducto != 'CON_MONTO_TO' then 
							(isnull(D.CT_SERVPREST,0) * (isnull(R.NU_PRECIOUNIVAL,0)/100)) 
						else 
							D.IM_PRECIOTOTAL 
					  end AS IM_PRECIOTOTAL_PRES, 
					  T.CD_TIPVAL, T.CD_MONEDA, D.CD_CONCILIACION AS CD_CONCILIACION_PRES, D.CD_ORDEN AS CD_ORDEN_PRES,
					  D.CD_LOTESERV, D.CD_ORDEN AS CD_ORDENSERV, D.CD_SECTORSOL, D.CD_SECTOR AS CD_SECTORCONTROL,
					  D.CD_PRODUCTO AS CD_PRODUCTOPRES, D.CD_UNIVAL, case when @st_conciliacion != 'APR' then isnull(R.NU_PRECIOUNIVAL,0) else D.IM_PRECIOUNIT end AS IM_PRECIOUNITPRES, 
					  D.NB_PIEZADESDE AS NB_PIEZADESDEPRES, D.NB_PIEZAHASTA AS NB_PIEZAHASTAPRES,
					  D.CD_REMITOPADRE, D.NB_ATRIBUTOREF1, D.NB_ATRIBUTOREF2, D.NB_OBSERVACIONES,
					  D.ST_LOTEDET, D.FH_MODIFICACION, D.NB_USUARIOMODIF
				from TCF009_SERVPRESCAB C
				inner join TCF010_SERVPRESDET D on C.CD_LOTESERV = D.CD_LOTESERV
				left join TCF013_CONCILIA L on D.CD_CONCILIACION = L.CD_CONCILIACION
				left join TCF026_PRODPERIODO P on P.CD_PROVEEDOR = C.CD_PROVEEDOR and P.CD_PRODUCTO = D.CD_PRODUCTO
						 and (case when @fhFinServHasta is not null then D.FH_FIN_SERV else D.FH_REMITO end) between P.FH_DESDE and P.FH_HASTA and P.ST_HABILITADO = 'S'
				left join TCF027_PRODPERTARIFA R on R.CD_PROVEEDOR = P.CD_PROVEEDOR and R.CD_PRODUCTO = P.CD_PRODUCTO and R.FH_DESDE = P.FH_DESDE 
				                                                                   and isnull(D.CT_SERVPREST,0) between R.NU_CANTDESDE and R.NU_CANTHASTA and R.ST_HABILITADO = 'S'
				left join TCF005_PRODUCTO T on T.CD_PROVEEDOR = C.CD_PROVEEDOR and T.CD_PRODUCTO = D.CD_PRODUCTO and T.ST_HABILITADO = 'S'
				where  (isnull(L.ST_CONCILIACION,'PEN') != 'APR')
					and C.CD_PROVEEDOR    = @cd_proveedor
					and C.ST_LOTECAB      = 'ACT'
					and D.CD_SECTORSOL    = @cd_sector
					and D.CD_PRODUCTO     = @cd_producto
					and (D.FH_REMITO       >= @fr_desde or @fr_desde is null)
					and (D.FH_REMITO       <= @fr_hasta or @fr_hasta is null)
					and (D.FH_FIN_SERV     >= @fs_desde or @fs_desde is null)
					and (D.FH_FIN_SERV     <= @fs_hasta or @fs_hasta is null)
					and D.ST_LOTEDET      = 'C'
					and T.CD_GRUPOPRODUCTO != 'NO_CON'
					and T.CD_TIPVAL        = '3'
			) as temp2

            INSERT INTO #APAREO_CONCILIACION
            select P.CD_REMITO_PRES, P.FH_REMITO, P.FH_FIN_SERV, P.CT_SERVPREST, P.IM_PRECIOTOTAL_PRES, P.CD_TIPVAL, P.CD_MONEDA, P.CD_CONCILIACION_PRES, '0' AS CHKPRES, 
                   '' AS CD_REMITO_FACT, '' AS TP_COMPROBANTE, '' AS NU_COMPROBANTE, 0 AS CT_SERVFACT, 0 AS IM_PRECIOTOTAL_FACT, null AS CD_CONCILIACION_FACT, '0' AS CHK_FACT, 
                   0 AS CT_SERVFACT_DIFE, 0 AS IM_SERVFACT_DIFE, 0 AS CD_ORDEN_PRES, P.CD_LOTESERV, P.CD_ORDENSERV, P.CD_SECTORSOL, P.CD_SECTORCONTROL, P.CD_PRODUCTOPRES, 
                   P.CD_UNIVAL, P.IM_PRECIOUNITPRES, P.NB_PIEZADESDEPRES, P.NB_PIEZAHASTAPRES, P.CD_REMITOPADRE, P.NB_ATRIBUTOREF1, P.NB_ATRIBUTOREF2, P.NB_OBSERVACIONES, 
                   P.ST_LOTEDET, P.FH_MODIFICACION, P.NB_USUARIOMODIF, 0 AS CD_LOTEFACT, 0 AS CD_ORDENFACT, @cd_proveedor, @cd_sector, @cd_producto, @cd_periodo, @im_cotiza, null
            FROM #TEMP_PRES2 P
			order by P.CD_REMITO_PRES, P.FH_REMITO, P.CT_SERVPREST desc
       end

    -- OBTENGO LOS SERVICIOS FACTURADOS --
    if (@cd_conciliacion is not null)
       begin
          declare cur_fact cursor for 
               select D.CD_REMITO AS CD_REMITO_FACT, D.TP_COMPROBANTE, D.NU_COMPROBANTE, 
                      D.CT_SERVFACT    * CASE WHEN G.NB_ATRIBUTOTABLA1 = '-' THEN -1 ELSE 1 END AS CT_SERVFACT, 
                      D.IM_PRECIOTOTAL * CASE WHEN G.NB_ATRIBUTOTABLA1 = '-' THEN -1 ELSE 1 END AS IM_PRECIOTOTAL_FACT, 
                      D.CD_CONCILIACION AS CD_CONCILIACION_FACT, D.CD_LOTEFACT, D.CD_ORDEN AS CD_ORDENFACT,
                      (case when PROD.CD_MONEDA = 'ARS' then 1 else (case when P.CD_COTIZACION = 'MEDIO' then T.NU_CAMBFIX when P.CD_COTIZACION = 'ALTO' then T.NU_CAMBALTO else T.NU_CAMBBAJO end)/@nu_billetes end) AS IM_COTIZA,
                      case when PROD.CD_MONEDA = 'ARS' then null else T.FH_CAMBIO end AS FH_COTIZA
               from TCF011_SERVFACCAB C
               inner join TCF012_SERVFACDET D on C.CD_LOTEFACT = D.CD_LOTEFACT
               inner join TCF001_GENERAL G on G.CD_TABLA = 'TIPCOM' AND G.CD_CODTABLA = D.TP_COMPROBANTE
               inner join TCF005_PRODUCTO PROD on PROD.CD_PROVEEDOR = C.CD_PROVEEDOR and PROD.CD_PRODUCTO = D.CD_PRODUCTO and PROD.ST_HABILITADO = 'S'
               left join TCF013_CONCILIA L on D.CD_CONCILIACION = L.CD_CONCILIACION
			   left join TCF025_PERIODOTC P on P.CD_PROVEEDOR = C.CD_PROVEEDOR and P.CD_PERIODOFACT = D.CD_PERIODOFACT and P.ST_HABILITADO = 'S'
               left join TCF024_TIPOCAMBIO T on P.CD_MONEDA = T.CD_DIVISS and T.FH_CAMBIO = dateadd(DAY, P.NU_DIAS, D.FH_NROCTE)
               where D.CD_CONCILIACION = @cd_conciliacion 
               --Agregado David
                  and D.CD_SECTORCONCIL = (case when @cd_sector = '0' then D.CD_SECTORCONCIL else @cd_sector end)
               
                or (
                       isnull(L.ST_CONCILIACION,'PEN') = 'PEN'
                   and @st_conciliacion  != 'APR'
                   and D.CD_CONCILIACION = null
                   and C.CD_PROVEEDOR    = @cd_proveedor
                   and C.ST_LOTECAB      = 'ACT'
                   and D.CD_SECTORCONCIL = @cd_sector
                   and D.CD_PRODUCTO     = @cd_producto
                   and D.CD_PERIODOFACT  = @cd_periodo
                   and D.ST_LOTEDET      = 'A'
                )
               order by CD_REMITO, CT_SERVFACT desc
       end
    else
       begin
          declare cur_fact cursor for 
               select D.CD_REMITO AS CD_REMITO_FACT, D.TP_COMPROBANTE, D.NU_COMPROBANTE, 
                      D.CT_SERVFACT    * CASE WHEN G.NB_ATRIBUTOTABLA1 = '-' THEN -1 ELSE 1 END AS CT_SERVFACT, 
                      D.IM_PRECIOTOTAL * CASE WHEN G.NB_ATRIBUTOTABLA1 = '-' THEN -1 ELSE 1 END AS IM_PRECIOTOTAL_FACT,
                      D.CD_CONCILIACION AS CD_CONCILIACION_FACT, D.CD_LOTEFACT, D.CD_ORDEN AS CD_ORDENFACT,
                      (case when PROD.CD_MONEDA = 'ARS' then 1 else (case when P.CD_COTIZACION = 'MEDIO' then T.NU_CAMBFIX when P.CD_COTIZACION = 'ALTO' then T.NU_CAMBALTO else T.NU_CAMBBAJO end)/@nu_billetes end) AS IM_COTIZA,
                      case when PROD.CD_MONEDA = 'ARS' then null else T.FH_CAMBIO end AS FH_COTIZA
               from TCF011_SERVFACCAB C
               inner join TCF012_SERVFACDET D on C.CD_LOTEFACT = D.CD_LOTEFACT
               inner join TCF001_GENERAL G on G.CD_TABLA = 'TIPCOM' AND G.CD_CODTABLA = D.TP_COMPROBANTE
               inner join TCF005_PRODUCTO PROD on PROD.CD_PROVEEDOR = C.CD_PROVEEDOR and PROD.CD_PRODUCTO = D.CD_PRODUCTO and PROD.ST_HABILITADO = 'S'
               left join TCF013_CONCILIA L on D.CD_CONCILIACION = L.CD_CONCILIACION
			   left join TCF025_PERIODOTC P on P.CD_PROVEEDOR = C.CD_PROVEEDOR and P.CD_PERIODOFACT = D.CD_PERIODOFACT and P.ST_HABILITADO = 'S'
               left join TCF024_TIPOCAMBIO T on P.CD_MONEDA = T.CD_DIVISS and T.FH_CAMBIO = dateadd(DAY, P.NU_DIAS, D.FH_NROCTE)
               where 
                      (isnull(L.ST_CONCILIACION,'PEN') != 'APR')
                   and C.CD_PROVEEDOR    = @cd_proveedor
                   and C.ST_LOTECAB      = 'ACT'
                   and D.CD_SECTORCONCIL = @cd_sector
                   and D.CD_PRODUCTO     = @cd_producto
                   and D.CD_PERIODOFACT  = @cd_periodo
                   and D.ST_LOTEDET      = 'A'
               order by CD_REMITO, CT_SERVFACT desc
       end

    declare 
     @CD_REMITO_PRES        varchar(13)  , 
     @CD_ORDEN_PRES         int          , 
     @CD_REMITO_FACT        varchar(13)  , 
     @TP_COMPROBANTE        char(6)      , 
     @NU_COMPROBANTE        varchar(13)  , 
     @CT_SERVFACT           decimal(11,2), 
     @IM_PRECIOTOTAL_FACT   decimal(18,2), 
     @CD_CONCILIACION_FACT  int          ,
     @CD_LOTEFACT           int          ,
     @CD_ORDENFACT          int          ,
     @IM_COTIZA_FACT        decimal(12,4),
     @FH_COTIZA_FACT        date
    open cur_fact
    fetch cur_fact into @CD_REMITO_FACT, @TP_COMPROBANTE, @NU_COMPROBANTE, @CT_SERVFACT, @IM_PRECIOTOTAL_FACT, @CD_CONCILIACION_FACT, @CD_LOTEFACT, @CD_ORDENFACT, @IM_COTIZA_FACT, @FH_COTIZA_FACT 

    if @IM_COTIZA_FACT != 0
        set @im_cotiza = @IM_COTIZA_FACT 

    while (@@sqlstatus != 2)
    begin

       if not exists(select CD_REMITO_PRES
                 from #APAREO_CONCILIACION
                 where CD_REMITO_PRES = @CD_REMITO_FACT AND CD_REMITO_FACT = ''
                 group by CD_REMITO_PRES)
          begin
             INSERT INTO #APAREO_CONCILIACION
             select '' AS CD_REMITO_PRES, null AS FH_REMITO, null AS FH_FIN_SERV, 0 AS CT_SERVPREST, 0 AS IM_PRECIOTOTAL_PRES, '' AS CD_TIPVAL, '' AS CD_MONEDA, null AS CD_CONCILIACION_PRES, 
             '0' AS CHKPRES, @CD_REMITO_FACT, @TP_COMPROBANTE, @NU_COMPROBANTE, @CT_SERVFACT, @IM_PRECIOTOTAL_FACT, @CD_CONCILIACION_FACT, '0' AS CHKFACT, 
             0 AS CT_SERVFACT_DIFE, 0 AS NU_SERVFACT_DIFE, 0 AS CD_ORDEN_PRES, 0 AS CD_LOTESERV, 0 AS CD_ORDEN, '' AS CD_SECTORSOL, '' AS CD_SECTORCONTROL, 
             '' AS CD_PRODUCTOPRES, '' AS CD_UNIVAL, 0 AS IM_PRECIOUNITPRES, '' AS NB_PIEZADESDEPRES, '' AS NB_PIEZAHASTAPRES, '' AS CD_REMITOPADRE, 
             '' AS NB_ATRIBUTOREF1, '' AS NB_ATRIBUTOREF2, '' AS NB_OBSERVACIONES, '' AS ST_LOTEDET, null AS FH_MODIFICACION, '' AS NB_USUARIOMODIF, 
             @CD_LOTEFACT, @CD_ORDENFACT, @cd_proveedor, @cd_sector, @cd_producto, @cd_periodo, @IM_COTIZA_FACT, @FH_COTIZA_FACT
          end
       else
          begin
             select @CD_REMITO_PRES = CD_REMITO_PRES, @CD_ORDEN_PRES = MIN(CD_ORDEN_PRES)
             from #APAREO_CONCILIACION
             where CD_REMITO_PRES = @CD_REMITO_FACT AND CD_REMITO_FACT = ''
             group by CD_REMITO_PRES

             UPDATE #APAREO_CONCILIACION
             SET CD_REMITO_FACT = @CD_REMITO_FACT, TP_COMPROBANTE = @TP_COMPROBANTE, NU_COMPROBANTE = @NU_COMPROBANTE, 
                 CT_SERVFACT = @CT_SERVFACT, IM_PRECIOTOTAL_FACT = @IM_PRECIOTOTAL_FACT, CD_CONCILIACION_FACT = @CD_CONCILIACION_FACT,
                 CD_LOTEFACT = @CD_LOTEFACT, CD_ORDENFACT = @CD_ORDENFACT, CHK_PRES = '1', CHK_FACT = '1', IM_COTIZA = @IM_COTIZA_FACT, 
                 FH_COTIZA = @FH_COTIZA_FACT
             WHERE CD_REMITO_PRES = @CD_REMITO_PRES AND CD_ORDEN_PRES = @CD_ORDEN_PRES
          end
       fetch cur_fact into @CD_REMITO_FACT, @TP_COMPROBANTE, @NU_COMPROBANTE, @CT_SERVFACT, @IM_PRECIOTOTAL_FACT, @CD_CONCILIACION_FACT, @CD_LOTEFACT, @CD_ORDENFACT, @IM_COTIZA_FACT, @FH_COTIZA_FACT

    end
    close cur_fact

    UPDATE #APAREO_CONCILIACION SET CHK_PRES = '0', CHK_FACT = '0' where CD_REMITO_PRES = '' AND CD_REMITO_FACT = ''

    UPDATE #APAREO_CONCILIACION SET CHK_PRES = '1' where CD_CONCILIACION_PRES is not null
    UPDATE #APAREO_CONCILIACION SET CHK_FACT = '1' where CD_CONCILIACION_FACT is not null

    UPDATE #APAREO_CONCILIACION
    SET CHK_PRES = (CASE WHEN @st_servsinconcil = 'N' THEN '1' ELSE CHK_PRES END),
        CHK_FACT = (CASE WHEN @st_factsinconcil = 'N' THEN '1' ELSE CHK_FACT END)

    UPDATE #APAREO_CONCILIACION SET CHK_PRES = '0' where CT_SERVPREST = 0
    UPDATE #APAREO_CONCILIACION SET CHK_FACT = '0' where CT_SERVFACT  = 0
    
    IF @st_conciliacion != 'APR'
    BEGIN
        UPDATE #APAREO_CONCILIACION 
        SET IM_PRECIOTOTAL_PRES = IM_PRECIOTOTAL_PRES * @IM_COTIZA_FACT
    END

    UPDATE #APAREO_CONCILIACION 
    SET NB_ATRIBUTOREF1 = CONVERT(VARCHAR(15), @IM_COTIZA_FACT),
        NB_ATRIBUTOREF2 = CONVERT(VARCHAR(10), @FH_COTIZA_FACT, 103)
    
    UPDATE #APAREO_CONCILIACION 
    SET CT_SERVFACT_DIFE = CT_SERVFACT - CT_SERVPREST, 
        IM_SERVFACT_DIFE = IM_PRECIOTOTAL_FACT - IM_PRECIOTOTAL_PRES
    WHERE CHK_PRES = '1' or CHK_FACT = '1'

    SET ARITHABORT NUMERIC_TRUNCATION ON

    SELECT CD_REMITO_PRES, FH_REMITO, FH_FIN_SERV, CT_SERVPREST, IM_PRECIOTOTAL_PRES, IM_PRECIOTOTAL_PRES, CD_TIPVAL, CD_MONEDA, CD_CONCILIACION_PRES, CHK_PRES, CD_REMITO_FACT, 
           TP_COMPROBANTE, NU_COMPROBANTE, CT_SERVFACT, IM_PRECIOTOTAL_FACT, CD_CONCILIACION_FACT, CHK_FACT, CT_SERVFACT_DIFE, IM_SERVFACT_DIFE, 
           CD_LOTESERV, CD_ORDENSERV, CD_SECTORSOL, CD_SECTORCONTROL, CD_PRODUCTOPRES, CD_UNIVAL, IM_PRECIOUNITPRES, NB_PIEZADESDEPRES, NB_PIEZAHASTAPRES, 
           CD_REMITOPADRE, NB_ATRIBUTOREF1, NB_ATRIBUTOREF2, NB_OBSERVACIONES, ST_LOTEDET, FH_MODIFICACION, NB_USUARIOMODIF, CD_LOTEFACT, CD_ORDENFACT
    FROM #APAREO_CONCILIACION
    order by CD_ORDEN_PRES, CT_SERVPREST DESC, CT_SERVFACT DESC

end
go 


sp_procxmode 'sp_CFS_busqueda_conciliacion', anymode
go 

setuser
go 

