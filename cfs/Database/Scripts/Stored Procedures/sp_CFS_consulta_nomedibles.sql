
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_consulta_nomedibles;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_consulta_nomedibles;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_consulta_nomedibles' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_consulta_nomedibles

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_consulta_nomedibles;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_consulta_nomedibles(
@cd_conciliacion  int,
@cd_proveedor     varchar(6),
@cd_sector        varchar(6),
@cd_periodo       varchar(6),
@po_c_error       typ_c_error output,
@po_d_error       typ_d_error output )
as

begin  
	set @po_c_error = 0  
	set @po_d_error = null

    if (@cd_conciliacion != 0)
       begin
          select CD_CONCILIACION, CD_PROVEEDOR, CD_SECTOR, CD_PERIODOFACT, ST_CONCILIACION
          from TCF029_NOMEDIBLES
          where CD_CONCILIACION  = @cd_conciliacion
             and CD_SECTOR =  (case when @cd_sector = '0' then CD_SECTOR else @cd_sector end)
       end
    else
       begin
          select CD_CONCILIACION, CD_PROVEEDOR, CD_SECTOR, CD_PERIODOFACT, ST_CONCILIACION
          from TCF029_NOMEDIBLES
          where CD_PROVEEDOR    = @cd_proveedor
            and CD_SECTOR       = @cd_sector
            and CD_PERIODOFACT  = @cd_periodo
            and ST_CONCILIACION = 'GRA'
       end

end
go 


sp_procxmode 'sp_CFS_consulta_nomedibles', anymode
go 

setuser
go 

