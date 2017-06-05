
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_get_NuevoCodigoTarifa;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_get_NuevoCodigoTarifa;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_get_NuevoCodigoTarifa' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_get_NuevoCodigoTarifa

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_get_NuevoCodigoTarifa;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE sp_CFS_get_NuevoCodigoTarifa ( 
    @CD_PROVEEDOR       char(6) = ''        ,
    @CD_PRODUCTO        varchar(12) = ''    ,
    @FH_DESDE           varchar(20)         ,
    @NU_CANTDESDE_NUEVO integer     output  ,
    @po_c_error         typ_c_error output  ,
    @po_d_error         typ_d_error output   
) AS 
BEGIN
    set @po_c_error = 0  
    set @po_d_error = null 
    SET @NU_CANTDESDE_NUEVO = (select ISNULL(MAX(NU_CANTHASTA),0) + 1
				 		       from TCF027_PRODPERTARIFA
					           where CD_PROVEEDOR = @CD_PROVEEDOR
						         and CD_PRODUCTO  = @CD_PRODUCTO    
						         and FH_DESDE     = @FH_DESDE)

    select @NU_CANTDESDE_NUEVO

END
go 


sp_procxmode 'sp_CFS_get_NuevoCodigoTarifa', anymode
go 

setuser
go 

