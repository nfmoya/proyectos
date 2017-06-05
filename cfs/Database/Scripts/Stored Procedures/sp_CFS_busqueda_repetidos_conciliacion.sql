

-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_busqueda_repetidos_conciliacion;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_busqueda_repetidos_conciliacion;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_busqueda_repetidos_conciliacion' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_busqueda_repetidos_conciliacion

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_busqueda_repetidos_conciliacion;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE sp_CFS_busqueda_repetidos_conciliacion(  
@cd_proveedor     varchar(6)  ,
@cd_producto      varchar(6)  ,
@cd_periodo       varchar(6)  ,
@cd_sector        varchar(6)  ,
@cd_conciliacion  int         ,  
@po_c_error       typ_c_error output,  
@po_d_error       typ_d_error output  
)  
as  
begin

  set @po_c_error = 0
  set @po_d_error = ''
  if @cd_conciliacion = 0
     begin
        set @cd_conciliacion = null
     end
	 
  create table #REMITOS_REPETIDOS
    (CD_REMITO varchar(13)) 

  -- OBTENGO LOS SERVICIOS FACTURADOS --
  if (@cd_conciliacion is not null)
     begin
        insert into #REMITOS_REPETIDOS
        select distinct D.CD_REMITO
        from TCF011_SERVFACCAB C
        inner join TCF012_SERVFACDET D on C.CD_LOTEFACT = D.CD_LOTEFACT
        where D.CD_CONCILIACION = @cd_conciliacion
        order by CD_REMITO
     end
  else
     begin
        insert into #REMITOS_REPETIDOS
        select distinct D.CD_REMITO
        from TCF011_SERVFACCAB C
        inner join TCF012_SERVFACDET D on C.CD_LOTEFACT = D.CD_LOTEFACT
        left join TCF013_CONCILIA L on D.CD_CONCILIACION = L.CD_CONCILIACION
        where 
              (isnull(L.ST_CONCILIACION,'PEN') != 'APR')
          and C.CD_PROVEEDOR    = @cd_proveedor
          and C.ST_LOTECAB      = 'ACT'
          and D.CD_SECTORCONCIL = @cd_sector
          and D.CD_PRODUCTO     = @cd_producto
          and D.CD_PERIODOFACT  = @cd_periodo
          and D.ST_LOTEDET      = 'A'
        order by CD_REMITO
     end

  select D.CD_REMITO, D.CD_PERIODOFACT, D.TP_COMPROBANTE, D.NU_COMPROBANTE, D.CT_SERVFACT, D.IM_PRECIOUNIT, D.IM_PRECIOTOTAL
  from TCF012_SERVFACDET D
  inner join TCF011_SERVFACCAB C on C.CD_LOTEFACT = D.CD_LOTEFACT
  where C.ST_LOTECAB      = 'ACT'
    and D.ST_LOTEDET      = 'A'
    and C.CD_PROVEEDOR    = @cd_proveedor
    and D.CD_PRODUCTO     = @cd_producto
    and (D.CD_REMITO != '' and D.CD_REMITO is not null)
    and D.CD_PERIODOFACT != @cd_periodo
    and D.CD_REMITO IN( SELECT R.CD_REMITO FROM #REMITOS_REPETIDOS R WHERE R.CD_REMITO = D.CD_REMITO)
end
go 


sp_procxmode 'sp_CFS_busqueda_repetidos_conciliacion', unchained
go 

setuser
go 

