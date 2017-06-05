
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_Consulta_ServPrestDetalleVW_2;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_Consulta_ServPrestDetalleVW_2;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_Consulta_ServPrestDetalleVW_2' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_Consulta_ServPrestDetalleVW_2

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_Consulta_ServPrestDetalleVW_2;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_Consulta_ServPrestDetalleVW_2 ( 
  @pi_CD_PROVEEDOR       varchar(6) = ''         ,
  @pi_CD_SECTOR          varchar(15) = ''        ,
  @pi_CD_GRUPOPRODUCTO   varchar(6) = ''         ,
  @pi_CD_PRODUCTO        varchar(12) = ''        ,
  @pi_DESDE              varchar(10) = ''        ,
  @pi_HASTA              varchar(10) = ''        ,
  @pi_NM_REMITO_DESDE    varchar(13) = ''        ,
  @pi_NM_REMITO_HASTA    varchar(13) = ''        ,
  @pi_NM_LOTE            int = 0                 ,
  @pi_ST_LOTEDET         varchar(1) = ''         ,
  @po_c_error            typ_c_error output      ,
  @po_d_error            typ_d_error output
) AS 
  set @po_c_error = 0  
  set @po_d_error = null 

  SELECT vista.CD_SECTOR, 
         vista.CD_GRUPOPRODUCTO, 
         vista.CD_PRODUCTO, 
         vista.NB_PRODUCTO AS NB_DESCRIPCION, 
         vista.CD_LOTESERV, 
         vista.CD_REMITO, 
         CONVERT(varchar(10), vista.FH_REMITO,103) as FH_REMITO, 
         CONVERT(varchar(10), vista.FH_FIN_SERV,103) as FH_FIN_SERV, 
         vista.CT_SERVPREST, 
         vista.IM_PRECIOTOTAL, 
         vista.NB_OBSERVACIONES, 
         vista.ST_LOTEDET, 
         vista.CD_CONCILIACION,
         vista.NB_ATRIBUTOREF1,
         vista.NB_ATRIBUTOREF2,
         vista.NB_TIPVAL
  FROM VCF001_SERVPRESTVIEW vista
  WHERE 1 = 1
    AND (ISNULL(@pi_NM_LOTE,0) = 0 OR (vista.CD_LOTESERV = @pi_NM_LOTE))
    AND (ISNULL(@pi_CD_PROVEEDOR,'0') = '0' OR (vista.CD_PROVEEDOR = @pi_CD_PROVEEDOR))
    AND (ISNULL(@pi_CD_GRUPOPRODUCTO,'0') = '0' OR (vista.CD_GRUPOPRODUCTO = @pi_CD_GRUPOPRODUCTO))
    AND (ISNULL(@pi_CD_PRODUCTO,'0') = '0' OR (vista.CD_PRODUCTO = @pi_CD_PRODUCTO))
    AND (ISNULL(@pi_CD_SECTOR,'0') = '0' OR (vista.CD_SECTOR = @pi_CD_SECTOR))
    AND (ISNULL(@pi_DESDE,'') = '' OR (vista.FH_REMITO >= convert(date, @pi_DESDE, 103)))
    AND (ISNULL(@pi_HASTA,'') = '' OR (vista.FH_REMITO <= convert(date, @pi_HASTA, 103)))
    AND (ISNULL(@pi_NM_REMITO_DESDE,'') = '' OR (vista.CD_REMITO >= @pi_NM_REMITO_DESDE))
    AND (ISNULL(@pi_NM_REMITO_HASTA,'') = '' OR (vista.CD_REMITO <= @pi_NM_REMITO_HASTA))
    AND (vista.ST_LOTEDET = @pi_ST_LOTEDET)
  ORDER BY vista.CD_SECTOR, vista.CD_GRUPOPRODUCTO, vista.CD_PRODUCTO
go 

sp_procxmode 'sp_CFS_Consulta_ServPrestDetalleVW_2', anymode
go 

setuser
go 

