
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_Consulta_ServPrestDetalle;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_Consulta_ServPrestDetalle;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_Consulta_ServPrestDetalle' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_Consulta_ServPrestDetalle

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_Consulta_ServPrestDetalle;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_Consulta_ServPrestDetalle ( 
  @pi_CD_PROVEEDOR       varchar(6) = ''         ,
  @pi_CD_SECTOR          varchar(15) = ''        ,
  @pi_CD_GRUPOPRODUCTO   varchar(6) = ''         ,
  @pi_CD_PRODUCTO        varchar(12) = ''        ,
  @pi_FH_REMITO          varchar(10) = ''        ,
  @pi_FH_FIN_SERV        varchar(10) = ''        ,
  @pi_NM_REMITO_DESDE    varchar(13) = ''        ,
  @pi_NM_REMITO_HASTA    varchar(13) = ''        ,
  @pi_NM_LOTE            int = 0                 ,
  @pi_ST_LOTEDET         varchar(1) = ''         ,
  @po_c_error            typ_c_error output      ,
  @po_d_error            typ_d_error output
) AS 
  set @po_c_error = 0  
  set @po_d_error = null 
  DECLARE @query    varchar(16000)

set @query = ''
set @query = @query + 'SELECT TCF010.CD_SECTOR, TCF005.CD_GRUPOPRODUCTO, TCF010.CD_PRODUCTO, TCF005.NB_PRODUCTO AS NB_DESCRIPCION, '
set @query = @query + 'TCF010.CD_LOTESERV, TCF010.CD_REMITO, CONVERT(varchar(10),TCF010.FH_REMITO,103) as FH_REMITO, CONVERT(varchar(10),TCF010.FH_FIN_SERV,103) as FH_FIN_SERV, '
set @query = @query + 'TCF010.CT_SERVPREST, TCF010.IM_PRECIOTOTAL, TCF010.NB_OBSERVACIONES, '
set @query = @query + 'TCF010.ST_LOTEDET, TCF010.CD_CONCILIACION '
set @query = @query + 'FROM TCF010_SERVPRESDET TCF010 '
set @query = @query + 'LEFT OUTER JOIN TCF005_PRODUCTO TCF005 ON (TCF005.CD_PRODUCTO = TCF010.CD_PRODUCTO) '
set @query = @query + 'WHERE '

if( @pi_NM_LOTE <> null and @pi_NM_LOTE <> 0)
begin
	set @query = @query + ' TCF010.CD_LOTESERV = ' || CONVERT(VARCHAR(10),@pi_NM_LOTE) || ' '
	if( @pi_CD_PROVEEDOR <> null and @pi_CD_PROVEEDOR <> '' AND @pi_CD_PROVEEDOR <> '0' )
	begin
		set @query = @query +   ' and '
	end
end

if( @pi_CD_PROVEEDOR <> null and @pi_CD_PROVEEDOR <> '' and @pi_CD_PROVEEDOR <> '0')
begin
	 set @query = @query + 'TCF005.CD_PROVEEDOR = ''' || @pi_CD_PROVEEDOR || ''' '
end

if ((@pi_CD_GRUPOPRODUCTO <> null) and (@pi_CD_GRUPOPRODUCTO <> '') and (@pi_CD_GRUPOPRODUCTO <> '0'))
begin
	set @query = @query + 'AND TCF005.CD_GRUPOPRODUCTO = ''' || @pi_CD_GRUPOPRODUCTO || ''' '
end

if ((@pi_CD_PRODUCTO <> null) and (@pi_CD_PRODUCTO <> '') and (@pi_CD_PRODUCTO <> '0'))
begin
	set @query = @query + 'AND TCF005.CD_PRODUCTO = ''' || @pi_CD_PRODUCTO || ''' '
end

if ((@pi_CD_SECTOR <> null) and (@pi_CD_SECTOR <> '') and (@pi_CD_SECTOR <> '0'))
begin
	set @query = @query + 'AND TCF010.CD_SECTOR = ''' || @pi_CD_SECTOR || ''' '
end

if ((@pi_FH_REMITO <> null) and (@pi_FH_REMITO <> ''))
begin
	set @query = @query + 'AND TCF010.FH_REMITO >= (convert(date, ''' || @pi_FH_REMITO || ''', 103)) '
end

if ((@pi_FH_FIN_SERV <> null) and (@pi_FH_FIN_SERV <> ''))
begin
	set @query = @query + 'AND TCF010.FH_REMITO <= (convert(date, ''' || @pi_FH_FIN_SERV || ''', 103)) '
end

if ((@pi_NM_REMITO_DESDE <> null) and (@pi_NM_REMITO_DESDE <> ''))
begin
	set @query = @query + 'AND TCF010.CD_REMITO >= ''' || @pi_NM_REMITO_DESDE || ''' '
end

if ((@pi_NM_REMITO_HASTA <> null) and (@pi_NM_REMITO_HASTA <> ''))
begin
	set @query = @query + 'AND TCF010.CD_REMITO <= ''' || @pi_NM_REMITO_HASTA || ''' '
end

set @query = @query + 'AND TCF010.ST_LOTEDET = ''' || @pi_ST_LOTEDET || ''' '
declare @printo varchar(100)
set @printo = substring(@query,1,100)
print @printo
set @printo = substring(@query,100,100)
print @printo
set @printo = substring(@query,200,100)
print @printo
set @printo = substring(@query,300,100)
print @printo
set @printo = substring(@query,400,100)
print @printo
set @printo = substring(@query,500,100)
print @printo
set @printo = substring(@query,600,100)
print @printo
set @printo = substring(@query,700,100)
print @printo

exec(@query)
go 

sp_procxmode 'sp_CFS_Consulta_ServPrestDetalle', unchained
go 

setuser
go 

