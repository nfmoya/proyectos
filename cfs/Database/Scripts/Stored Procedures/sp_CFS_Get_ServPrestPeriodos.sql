
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_Get_ServPrestPeriodos;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_Get_ServPrestPeriodos;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_Get_ServPrestPeriodos' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_Get_ServPrestPeriodos

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_Get_ServPrestPeriodos;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_Get_ServPrestPeriodos ( 

  @pi_CD_PROVEEDOR       varchar(6) = ''        ,

  @pi_CD_SECTOR          varchar(15) = ''        ,

  @pi_CD_PRODUCTO        varchar(12) = ''        ,

  @pi_CD_GRUPO           varchar(6) = ''        ,

  @pi_DESDE              varchar(100) = ''        ,

  @pi_HASTA              varchar(100) = ''        ,

  @pi_NM_COMPROBANTE     varchar(13) = ''        ,

  @pi_NM_REMITO_DESDE    varchar(13) = ''        ,

  @pi_NM_REMITO_HASTA    varchar(13) = ''        ,
  
  @pi_ST_LOTEDET         varchar(1) = ''         ,

  @pi_NM_LOTE            int = 0        ,

  @po_c_error         typ_c_error output  ,

  @po_d_error         typ_d_error output  

) AS 

set @po_c_error = 0  

set @po_d_error = null 




SELECT 

DISTINCT top 12 dbo.periodoDesdeFecha(TCF010.FH_REMITO) AS PERIODO 

FROM 

TCF010_SERVPRESDET TCF010 

LEFT OUTER JOIN TCF005_PRODUCTO TCF005 ON (TCF005.CD_PRODUCTO = TCF010.CD_PRODUCTO) 

WHERE 1 = 1
  AND (ISNULL(@pi_NM_LOTE,0) = 0 OR (TCF010.CD_LOTESERV = @pi_NM_LOTE))
  AND (ISNULL(@pi_CD_PROVEEDOR,'0') = '0' OR (TCF005.CD_PROVEEDOR = @pi_CD_PROVEEDOR))
  AND (ISNULL(@pi_CD_GRUPO,'0') = '0' OR (TCF005.CD_GRUPOPRODUCTO = @pi_CD_GRUPO))
  AND (ISNULL(@pi_CD_PRODUCTO,'0') = '0' OR (TCF005.CD_PRODUCTO = @pi_CD_PRODUCTO))
  AND (ISNULL(@pi_CD_SECTOR,'0') = '0' OR (TCF010.CD_SECTOR = @pi_CD_SECTOR))

  AND (ISNULL(@pi_DESDE,'') = '' OR (TCF010.FH_REMITO >= convert(date, @pi_DESDE, 103)))
  AND (ISNULL(@pi_HASTA,'') = '' OR (TCF010.FH_REMITO <= convert(date, @pi_HASTA, 103)))
  AND (ISNULL(@pi_NM_REMITO_DESDE,'') = '' OR (TCF010.CD_REMITO >= @pi_NM_REMITO_DESDE))
  AND (ISNULL(@pi_NM_REMITO_HASTA,'') = '' OR (TCF010.CD_REMITO <= @pi_NM_REMITO_HASTA))
 AND TCF010.ST_LOTEDET =    @pi_ST_LOTEDET 

 ORDER BY PERIODO
go 

Grant Execute on dbo.sp_CFS_Get_ServPrestPeriodos to RolTrnCFACSERV 
go

sp_procxmode 'sp_CFS_Get_ServPrestPeriodos', unchained
go 

setuser
go 

