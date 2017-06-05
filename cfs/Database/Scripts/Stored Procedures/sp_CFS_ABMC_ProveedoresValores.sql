
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_ProveedoresValores;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_ABMC_ProveedoresValores;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_ABMC_ProveedoresValores' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_ABMC_ProveedoresValores

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_ProveedoresValores;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_ABMC_ProveedoresValores( 
	@opcion             char(1) = ''        ,
	@CD_PROVEEDOR       char(6) = ''        ,
	@CD_PERIODOFACT     char(6) = ''        ,
	@CD_UNIVAL          char(6) = ''        ,
	@NU_VALBRUTOUNIVAL  varchar(20)         ,
	@NU_VALNETOUNIVAL   varchar(20)         ,
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
                 from TCF004_VALORUNIVAL  
                 where CD_PROVEEDOR   = @CD_PROVEEDOR
                   and CD_PERIODOFACT = @CD_PERIODOFACT
                   and CD_UNIVAL      = @CD_UNIVAL) 
      begin   
         insert into TCF004_VALORUNIVAL(
                 CD_PROVEEDOR, 
                 CD_PERIODOFACT, 
                 CD_UNIVAL, 
                 NU_VALBRUTOUNIVAL, 
                 NU_VALNETOUNIVAL, 
                 ST_HABILITADO,
                 FH_ALTA, 
                 NB_USUARIOALTA)
         values (@CD_PROVEEDOR, 
                 @CD_PERIODOFACT, 
                 @CD_UNIVAL, 
                 convert(decimal(15,4), @NU_VALBRUTOUNIVAL), 
                 convert(decimal(15,4), @NU_VALNETOUNIVAL), 
                 @ST_HABILITADO,
                 getdate(), 
                 @USU_MODI)
      end
   else
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Valoracion existente para ese proveedor y periodo'
         return 
      end
end
/* Opcion modificacion */
if (@opcion = '2') 
begin
   if not exists (select * 
                 from TCF004_VALORUNIVAL  
                 where CD_PROVEEDOR   = @CD_PROVEEDOR
                   and CD_PERIODOFACT = @CD_PERIODOFACT
                   and CD_UNIVAL      = @CD_UNIVAL) 
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Valoracion inexistente'
         return 
      end
   else
      begin	
         update TCF004_VALORUNIVAL
         set NU_VALBRUTOUNIVAL  = convert(decimal(15,4), @NU_VALBRUTOUNIVAL) , 
             NU_VALNETOUNIVAL   = convert(decimal(15,4), @NU_VALNETOUNIVAL)  , 
             ST_HABILITADO      = @ST_HABILITADO     ,
             FH_MODIFICACION    = getdate()          , 
             NB_USUARIOMODIF    = @USU_MODI   
         where CD_PROVEEDOR   = @CD_PROVEEDOR
           and CD_PERIODOFACT = @CD_PERIODOFACT
           and CD_UNIVAL      = @CD_UNIVAL
	  end
end
/* Opcion consulta: se consulta una pieza en particular */
if (@opcion = '3') 
begin
         select CD_PROVEEDOR, 
                CD_PERIODOFACT, 
                CD_UNIVAL, 
                NU_VALBRUTOUNIVAL, 
                NU_VALNETOUNIVAL, 
                ST_HABILITADO
         from TCF004_VALORUNIVAL
         where (CD_PROVEEDOR   = @CD_PROVEEDOR   or @CD_PROVEEDOR is null)
           and (CD_PERIODOFACT = @CD_PERIODOFACT or @CD_PERIODOFACT is null)
           and (CD_UNIVAL      = @CD_UNIVAL      or @CD_UNIVAL is null)
         order by CD_PROVEEDOR, CD_PERIODOFACT, CD_UNIVAL
end
/* Opcion eliminacion */
if (@opcion = '4') 
begin
   if not exists (select * 
                 from TCF004_VALORUNIVAL
                 where CD_PROVEEDOR   = @CD_PROVEEDOR
                   and CD_PERIODOFACT = @CD_PERIODOFACT
                   and CD_UNIVAL      = @CD_UNIVAL) 
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Valoracion inexistente'
         return 
      end
   else
      begin	
         delete from TCF004_VALORUNIVAL
         where CD_PROVEEDOR   = @CD_PROVEEDOR
           and CD_PERIODOFACT = @CD_PERIODOFACT
           and CD_UNIVAL      = @CD_UNIVAL
	  end
end
go 


sp_procxmode 'sp_CFS_ABMC_ProveedoresValores', unchained
go 

setuser
go 

