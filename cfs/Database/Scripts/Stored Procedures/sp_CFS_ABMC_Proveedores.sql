
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_Proveedores;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_ABMC_Proveedores;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_ABMC_Proveedores' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_ABMC_Proveedores

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_Proveedores;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_ABMC_Proveedores ( 
  @opcion             char(1) = ''        ,
  @CD_PROVEEDOR       char(6)  = ''       ,
  @NB_PROVEEDOR       varchar(30) = ''    ,
  @NB_PROVEEDORCORTO  varchar(12) = ''    ,
  @NU_CUIT            numeric(10,0) = 0   ,
  @NB_ATRIBUTOPROV1   varchar(30) = ''    ,
  @NB_ATRIBUTOPROV2   varchar(30) = ''    ,
  @NB_ATRIBUTOPROV3   varchar(30) = ''    ,
  @NB_ATRIBUTOPROV4   varchar(30) = ''    ,
  @NB_ATRIBUTOPROV5   varchar(30) = ''    ,
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
                 from TCF002_PROVEEDOR  
                 where CD_PROVEEDOR = @CD_PROVEEDOR) 
      begin   
         insert into TCF002_PROVEEDOR(
                 CD_PROVEEDOR, 
                 NB_PROVEEDOR, 
                 NB_PROVEEDORCORTO, 
                 NU_CUIT, 
                 NB_ATRIBUTOPROV1, 
                 NB_ATRIBUTOPROV2,
                 NB_ATRIBUTOPROV3, 
                 NB_ATRIBUTOPROV4, 
                 NB_ATRIBUTOPROV5, 
                 ST_HABILITADO, 
                 FH_ALTA, 
                 NB_USUARIOALTA)
         values (@CD_PROVEEDOR, 
                 @NB_PROVEEDOR, 
                 @NB_PROVEEDORCORTO, 
                 @NU_CUIT, 
                 @NB_ATRIBUTOPROV1, 
                 @NB_ATRIBUTOPROV2,
                 @NB_ATRIBUTOPROV3, 
                 @NB_ATRIBUTOPROV4, 
                 @NB_ATRIBUTOPROV5, 
                 @ST_HABILITADO, 
                 getdate(), 
                 @USU_MODI)
      end
   else
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Codigo de proveedor duplicado'
         return
      end
end
/* Opcion modificacion */
if (@opcion = '2') 
begin
   if not exists (select * 
                  from TCF002_PROVEEDOR  
                  where CD_PROVEEDOR = @CD_PROVEEDOR) 
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Codigo de proveedor inexistente'
         return
      end
   else
      begin 
         update TCF002_PROVEEDOR 
         set NB_PROVEEDOR      = @NB_PROVEEDOR     , 
             NB_PROVEEDORCORTO = @NB_PROVEEDORCORTO, 
             NU_CUIT           = @NU_CUIT          , 
             NB_ATRIBUTOPROV1  = @NB_ATRIBUTOPROV1 , 
             NB_ATRIBUTOPROV2  = @NB_ATRIBUTOPROV2 ,
             NB_ATRIBUTOPROV3  = @NB_ATRIBUTOPROV3 , 
             NB_ATRIBUTOPROV4  = @NB_ATRIBUTOPROV4 , 
             NB_ATRIBUTOPROV5  = @NB_ATRIBUTOPROV5 , 
             ST_HABILITADO     = @ST_HABILITADO    , 
             FH_MODIFICACION   = getdate()         , 
             NB_USUARIOMODIF   = @USU_MODI   
         where CD_PROVEEDOR = @CD_PROVEEDOR
    end
end
/* Opcion consulta: se consulta una pieza en particular */
if (@opcion = '3') 
begin
         select CD_PROVEEDOR, 
                NB_PROVEEDOR, 
                NB_PROVEEDORCORTO, 
                NU_CUIT, 
                NB_ATRIBUTOPROV1, 
                NB_ATRIBUTOPROV2,
                NB_ATRIBUTOPROV3, 
                NB_ATRIBUTOPROV4, 
                NB_ATRIBUTOPROV5, 
                ST_HABILITADO
         from TCF002_PROVEEDOR
         where CD_PROVEEDOR = @CD_PROVEEDOR or @CD_PROVEEDOR is null
         order by CD_PROVEEDOR
end
/* Opcion eliminacion */
if (@opcion = '4') 
begin
   if not exists (select * 
                  from TCF002_PROVEEDOR  
                  where CD_PROVEEDOR = @CD_PROVEEDOR) 
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Codigo de proveedor inexistente'
         return 
      end
   else
      begin 
         delete from TCF002_PROVEEDOR 
         where CD_PROVEEDOR = @CD_PROVEEDOR
    end
end
go 


sp_procxmode 'sp_CFS_ABMC_Proveedores', unchained
go 

setuser
go 

