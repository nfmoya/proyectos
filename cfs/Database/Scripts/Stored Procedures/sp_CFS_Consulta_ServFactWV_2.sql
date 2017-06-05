
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_Consulta_ServFactWV_2;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_Consulta_ServFactWV_2;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_Consulta_ServFactWV_2' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_Consulta_ServFactWV_2

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_Consulta_ServFactWV_2;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_Consulta_ServFactWV_2 ( 
  @pi_CD_PROVEEDOR       varchar(6) = ''   ,
  @pi_CD_SECTOR          varchar(15) = ''  ,
  @pi_CD_PRODUCTO        varchar(12) = ''  ,
  @pi_CD_GRUPO           varchar(6) = ''   ,
  @pi_CD_HABILITADO      varchar(3) = ''   ,
  @pi_CD_PERIODO_DESDE   varchar(6) = ''   ,
  @pi_CD_PERIODO_HASTA   varchar(6) = ''   ,
  @pi_CD_TIPCOMP         varchar(8) = ''   ,
  @pi_NM_COMPROBANTE     varchar(13) = ''  ,
  @pi_NM_REMITO_DESDE    varchar(13) = ''  ,
  @pi_NM_REMITO_HASTA    varchar(13) = ''  ,
  @pi_NM_LOTE            int = 0           ,
  @pi_REDUCIDA           int = 0           ,
  @po_c_error         typ_c_error output   ,
  @po_d_error         typ_d_error output   
) AS 
  set @po_c_error = 0  
  set @po_d_error = null 
  declare @cdperido varchar(6),
          @mes  int,
          @f_desde  varchar(11),
          @f_hasta  varchar(11)

  if( @pi_NM_LOTE <> 0 and @pi_NM_LOTE <> null)
  BEGIN
    print 'fechas de lote'
    select @pi_CD_PROVEEDOR = CD_PROVEEDOR from TCF011_SERVFACCAB where  CD_LOTEFACT = @pi_NM_LOTE 
    select @f_desde = convert(varchar,min(t03.FH_DESDE),106) ,  
           @f_hasta = convert(varchar,MAX(t03.FH_HASTA),106) 
    from TCF003_PERIODOFACT t03
    inner join TCF012_SERVFACDET t12 on  t03.CD_PERIODOFACT = t12.CD_PERIODOFACT
    where  CD_LOTEFACT = @pi_NM_LOTE  and   CD_PROVEEDOR  = @pi_CD_PROVEEDOR

    print  @f_desde
    print  @f_hasta
  end 
  
  if( @pi_CD_PERIODO_DESDE <> '' and @pi_CD_PERIODO_DESDE <> null and @pi_CD_PERIODO_DESDE <> '0' )
  begin
    print 'fechas sin lote'
    select  @f_desde =   convert(varchar,FH_DESDE,106)   from TCF003_PERIODOFACT
    where CD_PERIODOFACT = @pi_CD_PERIODO_DESDE
    and   CD_PROVEEDOR  = @pi_CD_PROVEEDOR
  end
  if( @pi_CD_PERIODO_HASTA <> '' and @pi_CD_PERIODO_HASTA <> null and @pi_CD_PERIODO_HASTA <> '0' )
  begin
    select @f_hasta =  convert(varchar,FH_HASTA,106)   from TCF003_PERIODOFACT
    where CD_PERIODOFACT =  @pi_CD_PERIODO_HASTA
    and   CD_PROVEEDOR = @pi_CD_PROVEEDOR
  END

  SELECT (CD_SECTORCONCIL + ' - ' + NB_SECTOR) as CD_SECTORCONCIL,
         (CD_GRUPOPRODUCTO + ' - ' + NB_GRUPOPRODUCTO) as CD_GRUPOPRODUCTO,
		 (CASE WHEN @pi_REDUCIDA = 0 THEN vista.CD_PRODUCTO + ' - ' + NB_PRODUCTO ELSE '' END) as CD_PRODUCTO,
		 sum(vista.CT_SERVFACT) as CANTFACT , 
		 sum(IM_PRECIOTOTAL) as SERVFACT , 
		 count(1) as CANTREM, 
		 count(case when CD_CONCILIACION = null then 1 else (case when ST_CONCILIACION = 'GRA' then 1 else null  end)  end) as CANTREMCON, 
		 sum(CT_NETO) AS CT_NETO, 
		 sum(CT_BRUTO) as CT_BRUTO,
         vista.CD_PERIODOFACT,
         vista.NU_PORCVARMAX
  from VCF001_SERVFACTVIEW vista
  inner join TCF003_PERIODOFACT t003 on vista.CD_PERIODOFACT = t003.CD_PERIODOFACT and vista.CD_PROVEEDOR = t003.CD_PROVEEDOR 
  WHERE 1 = 1
    AND (ISNULL(@pi_NM_LOTE,0) = 0 OR (vista.CD_LOTEFACT = @pi_NM_LOTE))
	AND (ISNULL(@pi_CD_PROVEEDOR,'0') = '0' OR (vista.CD_PROVEEDOR = @pi_CD_PROVEEDOR))
	AND (ISNULL(@pi_CD_PRODUCTO,'0') = '0' OR (vista.CD_PRODUCTO = @pi_CD_PRODUCTO))
	AND (ISNULL(@pi_CD_SECTOR,'0') = '0' OR (vista.CD_SECTORCONCIL = @pi_CD_SECTOR))
	AND (ISNULL(@pi_NM_COMPROBANTE,'') = '' OR (vista.NU_COMPROBANTE = @pi_NM_COMPROBANTE))
	AND (ISNULL(@pi_CD_TIPCOMP,'') = '' OR (vista.TP_COMPROBANTE = @pi_CD_TIPCOMP))
	AND ((@pi_CD_HABILITADO = 'N' AND vista.ST_LOTECAB <> 'ACT') OR (@pi_CD_HABILITADO <> 'N' AND vista.ST_LOTECAB = 'ACT'))
	AND (ISNULL(@pi_CD_GRUPO,'0') = '0' OR (vista.CD_GRUPOPRODUCTO = @pi_CD_GRUPO))
	AND (ISNULL(@pi_NM_REMITO_DESDE,'') = '' OR (vista.CD_REMITO >= @pi_NM_REMITO_DESDE))
	AND (ISNULL(@pi_NM_REMITO_HASTA,'') = '' OR (vista.CD_REMITO <= @pi_NM_REMITO_HASTA))
	AND (ISNULL(@pi_CD_PERIODO_DESDE,'0') = '0' OR (t003.FH_DESDE >= (convert(date, @f_desde, 106))))
	AND (ISNULL(@pi_CD_PERIODO_HASTA,'0') = '0' OR (t003.FH_HASTA <= (convert(date, @f_hasta, 106))))
  GROUP BY vista.CD_SECTORCONCIL, vista.CD_GRUPOPRODUCTO, vista.NB_SECTOR, vista.NB_GRUPOPRODUCTO, NB_PRODUCTO, vista.CD_PRODUCTO, vista.CD_PERIODOFACT 
  ORDER BY vista.CD_SECTORCONCIL, vista.CD_GRUPOPRODUCTO, vista.CD_PRODUCTO
go 


sp_procxmode 'sp_CFS_Consulta_ServFactWV_2', anymode
go 

setuser
go 

