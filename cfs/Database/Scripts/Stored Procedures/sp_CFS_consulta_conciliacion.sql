
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_consulta_conciliacion;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_consulta_conciliacion;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_consulta_conciliacion' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_consulta_conciliacion

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_consulta_conciliacion;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE sp_CFS_consulta_conciliacion(
@cd_conciliacion  int,
@cd_proveedor     varchar(6),
@cd_sector        varchar(6),
@cd_producto      varchar(6),
@cd_periodo       varchar(6),
@po_c_error       typ_c_error output,
@po_d_error       typ_d_error output )
as

begin  
	set @po_c_error = 0  
	set @po_d_error = null

    if (@cd_conciliacion != 0)
       begin
          select CD_CONCILIACION , CD_PROVEEDOR   , CD_SECTOR      , CD_PRODUCTO    , CD_PERIODOFACT   , ST_IGNORAVAL, 
                 NB_OBSERVACIONES, ST_CONCILIACION, FH_REMITO_DESDE, FH_REMITO_HASTA, FH_FIN_SERV_DESDE, FH_FIN_SERV_HASTA  
          from TCF013_CONCILIA 
          where CD_CONCILIACION  = @cd_conciliacion
             and CD_SECTOR =  (case when @cd_sector = '0' then CD_SECTOR else @cd_sector end)
       end
    else
       begin
          select CD_CONCILIACION , CD_PROVEEDOR   , CD_SECTOR      , CD_PRODUCTO    , CD_PERIODOFACT   , ST_IGNORAVAL, 
                 NB_OBSERVACIONES, ST_CONCILIACION, FH_REMITO_DESDE, FH_REMITO_HASTA, FH_FIN_SERV_DESDE, FH_FIN_SERV_HASTA  
          from TCF013_CONCILIA 
          where CD_PROVEEDOR    = @cd_proveedor
            and CD_SECTOR       = @cd_sector
            and CD_PRODUCTO     = @cd_producto
            and CD_PERIODOFACT  = @cd_periodo
            and ST_CONCILIACION = 'GRA'
       end

end
go 


sp_procxmode 'sp_CFS_consulta_conciliacion', unchained
go 

setuser
go 

