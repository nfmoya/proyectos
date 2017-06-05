
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_ProveedoresTipoCambio;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_ABMC_ProveedoresTipoCambio;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_ABMC_ProveedoresTipoCambio' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_ABMC_ProveedoresTipoCambio

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_ProveedoresTipoCambio;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_ABMC_ProveedoresTipoCambio( 
	@opcion             char(1) = ''        ,
	@CD_PROVEEDOR       char(6) = ''        ,
	@CD_PERIODOFACT     char(6) = ''        ,
	@CD_MONEDA          char(3) = ''        ,
	@NU_DIAS            varchar(20)         ,
	@CD_COTIZACION		varchar(5)          ,
	@ST_HABILITADO      char(1) = 'S'       ,
	@USU_MODI           char(8) = ''        ,
    @po_c_error         typ_c_error output  ,
    @po_d_error         typ_d_error output  
) AS 
  set @po_c_error = 0  
  set @po_d_error = null 
/* Opcion Alta */
if (@opcion = '1') 
begin
   if not exists (select * 
                 from TCF025_PERIODOTC  
                 where CD_PROVEEDOR   = @CD_PROVEEDOR
                   and CD_PERIODOFACT = @CD_PERIODOFACT
                   and CD_MONEDA      = @CD_MONEDA) 
      begin   
         insert into TCF025_PERIODOTC(
                 CD_PROVEEDOR, 
                 CD_PERIODOFACT, 
                 CD_MONEDA, 
                 NU_DIAS, 
                 CD_COTIZACION,
                 ST_HABILITADO,
                 FH_ALTA, 
                 NB_USUARIOALTA)
         values (@CD_PROVEEDOR, 
                 @CD_PERIODOFACT, 
                 @CD_MONEDA, 
                 convert(int, @NU_DIAS),
                 @CD_COTIZACION,
                 @ST_HABILITADO,
                 getdate(), 
                 @USU_MODI)
      end
   else
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Tipo de Cambio existente para ese proveedor y periodo'
         return 
      end
end
/* Opcion modificacion */
if (@opcion = '2') 
begin
   if not exists (select * 
                 from TCF025_PERIODOTC  
                 where CD_PROVEEDOR   = @CD_PROVEEDOR
                   and CD_PERIODOFACT = @CD_PERIODOFACT
                   and CD_MONEDA      = @CD_MONEDA) 
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Tipo de Cambio inexistente'
         return 
      end
   else
      begin	
         update TCF025_PERIODOTC 
         set NU_DIAS         = convert(int, @NU_DIAS) ,
         	 CD_COTIZACION   = @CD_COTIZACION         ,
             ST_HABILITADO   = @ST_HABILITADO         , 
             FH_MODIFICACION = getdate()              , 
             NB_USUARIOMODIF = @USU_MODI   
         where CD_PROVEEDOR   = @CD_PROVEEDOR
           and CD_PERIODOFACT = @CD_PERIODOFACT
           and CD_MONEDA      = @CD_MONEDA
	  end
end
/* Opcion consulta: se consulta una pieza en particular */
if (@opcion = '3') 
begin
         select CD_PROVEEDOR, 
                CD_PERIODOFACT, 
                CD_MONEDA, 
                NU_DIAS,
                CD_COTIZACION,
                ST_HABILITADO
         from TCF025_PERIODOTC
         where (CD_PROVEEDOR   = @CD_PROVEEDOR   or @CD_PROVEEDOR   is null)
           and (CD_PERIODOFACT = @CD_PERIODOFACT or @CD_PERIODOFACT is null)
           and (CD_MONEDA      = @CD_MONEDA      or @CD_MONEDA      is null)
         order by CD_PROVEEDOR, CD_PERIODOFACT, CD_MONEDA
end
/* Opcion eliminacion */
if (@opcion = '4') 
begin
   if not exists (select * 
                 from TCF025_PERIODOTC  
                 where CD_PROVEEDOR   = @CD_PROVEEDOR
                   and CD_PERIODOFACT = @CD_PERIODOFACT
                   and CD_MONEDA      = @CD_MONEDA) 
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Tipo de Cambio inexistente'
         return 
      end
   else
      begin	
         delete from TCF025_PERIODOTC 
         where CD_PROVEEDOR   = @CD_PROVEEDOR
           and CD_PERIODOFACT = @CD_PERIODOFACT
           and CD_MONEDA      = @CD_MONEDA
	  end
end
go 


sp_procxmode 'sp_CFS_ABMC_ProveedoresTipoCambio', anymode
go 

setuser
go 

