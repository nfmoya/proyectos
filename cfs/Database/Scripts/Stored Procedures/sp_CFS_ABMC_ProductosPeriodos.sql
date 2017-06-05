
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_ProductosPeriodos;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_ABMC_ProductosPeriodos;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_ABMC_ProductosPeriodos' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
  setuser 'dbo'
  drop procedure sp_CFS_ABMC_ProductosPeriodos

END
go 

IF (@@error != 0)
BEGIN
  PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_ProductosPeriodos;1'"
  SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE sp_CFS_ABMC_ProductosPeriodos ( 
    @opcion             char(1) = ''        ,
    @CD_PROVEEDOR       char(6) = ''        ,
    @CD_PRODUCTO        varchar(12) = ''    ,
    @FH_DESDE           varchar(20)         ,
    @FH_HASTA           varchar(20)         ,
    @ST_HABILITADO      char(1) = 'S'       ,
    @USU_MODI           char(8) = ''        ,
    @po_c_error         typ_c_error output  ,
    @po_d_error         typ_d_error output  
) AS 
  declare 
  @f_desde  smalldatetime,
  @f_hasta  smalldatetime
  set @po_c_error = 0  
  set @po_d_error = null 
  if @FH_DESDE is not null    
     begin  
        execute sp_convierte_char_en_fecha @pi_fecha_char     = @FH_DESDE,  
                                           @po_fecha_datetime = @f_desde    output,  
                                           @po_c_error        = @po_c_error output,  
                                           @po_d_error        = @po_d_error output  
        if (@po_c_error  <> 0)  
           begin  
              set @po_d_error = 'Error llamando a sp_convierte_char_en_fecha : ' + @po_d_error     
              return         
           end
     end  
  if @FH_HASTA is not null    
     begin  
        execute sp_convierte_char_en_fecha @pi_fecha_char     = @FH_HASTA,  
                                           @po_fecha_datetime = @f_hasta    output,  
                                           @po_c_error        = @po_c_error output,  
                                           @po_d_error        = @po_d_error output  
        if (@po_c_error  <> 0)  
           begin  
              set @po_d_error = 'Error llamando a sp_convierte_char_en_fecha : ' + @po_d_error     
              return         
           end
     end  

/* Opcion Alta */
if (@opcion = '1') 
begin
   if not exists (select * 
                 from TCF026_PRODPERIODO  
                 where CD_PROVEEDOR = @CD_PROVEEDOR
                   and CD_PRODUCTO  = @CD_PRODUCTO
                   and FH_DESDE     = @f_desde
                   and ST_HABILITADO = 'S') 

   begin   

   if exists(  SELECT 1 FROM TCF026_PRODPERIODO 
                     WHERE CD_PROVEEDOR = @CD_PROVEEDOR AND
                           CD_PRODUCTO = @CD_PRODUCTO   AND
                           ST_HABILITADO = 'S'          AND
                    ((   convert(date, @FH_DESDE, 111) >= FH_DESDE AND convert(date, @FH_DESDE, 111) <= FH_HASTA) OR  
                    (convert(date, @FH_HASTA, 111) >= FH_DESDE AND convert(date, @FH_HASTA, 111)<= FH_HASTA) OR    
                    (convert(date, @FH_DESDE, 111) <= FH_DESDE AND convert(date, @FH_HASTA, 111) >= FH_HASTA)))   
    
    
            begin
               set @po_c_error = 1
               set @po_d_error = 'Fecha desde/hasta coincide con algun periodo'
               return  
            end 
         else
            begin
               insert into TCF026_PRODPERIODO(
                      CD_PROVEEDOR, 
                      CD_PRODUCTO, 
                      FH_DESDE, 
                      FH_HASTA, 
                      ST_HABILITADO, 
                      FH_ALTA, 
                      NB_USUARIOALTA)
               values (@CD_PROVEEDOR, 
                      @CD_PRODUCTO, 
                      @f_desde, 
                      @f_hasta,
                      @ST_HABILITADO, 
                      getdate(), 
                      @USU_MODI)
            end
      end
   else
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Periodo de producto duplicado'
         return 
      end
end
/* Opcion modificacion */
if (@opcion = '2') 
begin
   if not exists (select * 
                 from TCF026_PRODPERIODO  
                 where CD_PROVEEDOR = @CD_PROVEEDOR
                   and CD_PRODUCTO  = @CD_PRODUCTO
                   and FH_DESDE     = @f_desde) 
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Periodo de producto inexistente'
         return 
      end
   else
      begin 
         update TCF026_PRODPERIODO
         set FH_HASTA          = @f_hasta          ,
             ST_HABILITADO     = @ST_HABILITADO    , 
             FH_MODIFICACION   = getdate()         , 
             NB_USUARIOMODIF   = @USU_MODI   
         where CD_PROVEEDOR = @CD_PROVEEDOR
           and CD_PRODUCTO  = @CD_PRODUCTO     
           and FH_DESDE     = @f_desde
    end
end
/* Opcion consulta: se consulta una pieza en particular */
if (@opcion = '3') 
begin
         select CD_PROVEEDOR, 
                CD_PRODUCTO, 
                FH_DESDE, 
                FH_HASTA, 
                ST_HABILITADO
         from TCF026_PRODPERIODO
         where (CD_PROVEEDOR = @CD_PROVEEDOR or @CD_PROVEEDOR is null)
           and (CD_PRODUCTO  = @CD_PRODUCTO  or @CD_PRODUCTO is null)    
           and (FH_DESDE     = @FH_DESDE     or @FH_DESDE is null)
end
/* Opcion eliminacion */
if (@opcion = '4') 
begin
   if not exists (select * 
                 from TCF026_PRODPERIODO  
                 where CD_PROVEEDOR = @CD_PROVEEDOR
                   and CD_PRODUCTO  = @CD_PRODUCTO
                   and FH_DESDE     = @f_desde) 
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Periodo de producto inexistente'
         return 
      end
   else
      begin 
         delete from TCF026_PRODPERIODO 
         where CD_PROVEEDOR = @CD_PROVEEDOR
           and CD_PRODUCTO  = @CD_PRODUCTO
           and FH_DESDE     = @f_desde
    end
end
go 

sp_procxmode 'sp_CFS_ABMC_ProductosPeriodos', anymode
go 

setuser
go 

