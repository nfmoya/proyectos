
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_ProductosAgrup;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_ABMC_ProductosAgrup;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_ABMC_ProductosAgrup' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_ABMC_ProductosAgrup

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_ProductosAgrup;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_ABMC_ProductosAgrup ( 
	@opcion              char(1) = ''        ,
    @CD_PROVEEDOR        char(6) = ''        ,
	@CD_PRODUCTO_ORIG	 varchar(12) = ''    ,
	@CD_PRODUCTO     	 varchar(12) = ''    ,
    @DES_ITEM            varchar(50) = ''    ,
    @DES_GRUPO           varchar(50) = ''    ,
	@ST_HABILITADO       char(1) = ''        ,
	@USU_MODI            char(8) = ''        ,
    @po_c_error          typ_c_error output  ,
    @po_d_error          typ_d_error output  
) AS 
  set @po_c_error = 0  
  set @po_d_error = null 
/* Opcion Alta */
if (@opcion = '1') 
begin
   if not exists (select * 
                 from TCF028_PRODUCT_AGRUP  
                 where CD_PROVEEDOR     = @CD_PROVEEDOR
                   and CD_PRODUCTO_ORIG = @CD_PRODUCTO_ORIG)
      begin   
         insert into TCF028_PRODUCT_AGRUP(
                 CD_PROVEEDOR       ,
                 CD_PRODUCTO_ORIG   ,
                 CD_PRODUCTO        ,
                 DES_ITEM           ,
                 DES_GRUPO          ,
                 ST_HABILITADO      ,
                 FECHA_ALTA         , 
                 U_ALTA)
         values (@CD_PROVEEDOR       ,
                 @CD_PRODUCTO_ORIG   ,
                 @CD_PRODUCTO        ,
                 @DES_ITEM           ,
                 @DES_GRUPO          ,
                 @ST_HABILITADO      ,
                 getdate()           , 
                 @USU_MODI)
      end
   else
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Codigo de Producto duplicado'
         return 
      end
end
/* Opcion modificacion */
if (@opcion = '2') 
begin
   if not exists (select * 
                  from TCF028_PRODUCT_AGRUP  
		          where CD_PROVEEDOR     = @CD_PROVEEDOR
                    and CD_PRODUCTO_ORIG = @CD_PRODUCTO_ORIG)
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Codigo de Producto inexistente'
         return 
      end
   else
      begin	
         update TCF028_PRODUCT_AGRUP 
         set CD_PRODUCTO        = @CD_PRODUCTO    ,
             DES_ITEM           = @DES_ITEM       ,
             DES_GRUPO          = @DES_GRUPO      ,
             ST_HABILITADO      = @ST_HABILITADO  ,
             FECHA_MOD          = getdate()       , 
             U_MOD              = @USU_MODI
         where CD_PROVEEDOR     = @CD_PROVEEDOR
           and CD_PRODUCTO_ORIG = @CD_PRODUCTO_ORIG
	  end
end
/* Opcion consulta: se consulta una pieza en particular */
if (@opcion = '3') 
begin
         select CD_PROVEEDOR       , 
                CD_PRODUCTO_ORIG   ,
                CD_PRODUCTO        ,
                DES_ITEM           ,
                DES_GRUPO          ,
                ST_HABILITADO   
         from TCF028_PRODUCT_AGRUP
         where (CD_PROVEEDOR      = @CD_PROVEEDOR      or @CD_PROVEEDOR is null)
           and (CD_PRODUCTO_ORIG  = @CD_PRODUCTO_ORIG  or @CD_PRODUCTO_ORIG is null)
           and (CD_PRODUCTO       = @CD_PRODUCTO       or @CD_PRODUCTO is null)
           and (ST_HABILITADO     = @ST_HABILITADO     or @ST_HABILITADO is null)
         order by  CD_PROVEEDOR, CD_PRODUCTO_ORIG
end
/* Opcion eliminacion */
if (@opcion = '4') 
begin
   if not exists (select * 
                  from TCF028_PRODUCT_AGRUP  
                  where CD_PROVEEDOR     = @CD_PROVEEDOR 
                    and CD_PRODUCTO_ORIG = @CD_PRODUCTO_ORIG) 
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Codigo de Producto inexistente'
         return 
      end
   else
      begin	
         update TCF028_PRODUCT_AGRUP 
         set FECHA_BAJA = getdate()
         where CD_PROVEEDOR     = @CD_PROVEEDOR
           and CD_PRODUCTO_ORIG = @CD_PRODUCTO_ORIG
	  end
end
go 


sp_procxmode 'sp_CFS_ABMC_ProductosAgrup', anymode
go 

setuser
go 

