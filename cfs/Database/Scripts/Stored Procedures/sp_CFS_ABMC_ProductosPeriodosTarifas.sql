
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_ProductosPeriodosTarifas;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_ABMC_ProductosPeriodosTarifas;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_ABMC_ProductosPeriodosTarifas' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_ABMC_ProductosPeriodosTarifas

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_ProductosPeriodosTarifas;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE sp_CFS_ABMC_ProductosPeriodosTarifas ( 

    @opcion             char(1) = ''        ,

    @CD_PROVEEDOR       char(6) = ''        ,

    @CD_PRODUCTO        varchar(12) = ''    ,

    @FH_DESDE           varchar(20)         ,

    @NU_CANTDESDE       varchar(10)         ,

    @NU_CANTHASTA       varchar(10)         ,

    @NU_PRECIOUNIVAL    varchar(20)         ,

    @NU_PORCTARIFA      varchar(20)         ,

    @ST_HABILITADO      char(1) = 'S'       ,

    @ST_PRECIOFIJO      char(1) = 'N'       ,

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



/* Opcion Alta */

if (@opcion = '1') 

begin

   if not exists (select * 

                 from TCF027_PRODPERTARIFA  

                 where CD_PROVEEDOR  = @CD_PROVEEDOR

                   and CD_PRODUCTO   = @CD_PRODUCTO

                   and FH_DESDE      = @f_desde

                   and NU_CANTDESDE  = convert(integer, @NU_CANTDESDE)

                   and ST_HABILITADO = 'S') 



   begin   



   if exists(  SELECT 1 FROM TCF027_PRODPERTARIFA 

                     WHERE CD_PROVEEDOR  = @CD_PROVEEDOR AND

                           CD_PRODUCTO   = @CD_PRODUCTO  AND

                           FH_DESDE      = @f_desde      AND

                           ST_HABILITADO = 'S'           AND

                    ((convert(integer, @NU_CANTDESDE) >= NU_CANTDESDE AND convert(integer, @NU_CANTDESDE) <= NU_CANTHASTA) OR  

                    ( convert(integer, @NU_CANTHASTA) >= NU_CANTDESDE AND convert(integer, @NU_CANTHASTA) <= NU_CANTHASTA) OR    

                    ( convert(integer, @NU_CANTDESDE) <= NU_CANTDESDE AND convert(integer, @NU_CANTHASTA) >= NU_CANTHASTA)))   



            begin

               set @po_c_error = 1

               set @po_d_error = 'Fecha desde/hasta coincide con algun periodo'

               return  

            end 

         else

            begin

               insert into TCF027_PRODPERTARIFA(

                      CD_PROVEEDOR, 

                      CD_PRODUCTO, 

                      FH_DESDE, 

                      NU_CANTDESDE, 

                      NU_CANTHASTA, 

                      NU_PRECIOUNIVAL, 

                      NU_PORCTARIFA, 

                      ST_HABILITADO, 

                      ST_PRECIOFIJO, 

                      FH_ALTA, 

                      NB_USUARIOALTA)

               values (@CD_PROVEEDOR, 

                      @CD_PRODUCTO, 

                      @f_desde, 

                      convert(integer, @NU_CANTDESDE), 

                      convert(integer, @NU_CANTHASTA), 

                      convert(decimal(15,4), @NU_PRECIOUNIVAL), 

                      convert(decimal(10,2), @NU_PORCTARIFA), 

                      @ST_HABILITADO, 

                      @ST_PRECIOFIJO, 

                      getdate(), 

                      @USU_MODI)

            end

      end

   else

      begin

         set @po_c_error   = 1

         set @po_d_error   = 'Tarifa de Periodo Producto duplicado'

         return 

      end

end

/* Opcion modificacion */

if (@opcion = '2') 

begin

   if not exists (select * 

                 from TCF027_PRODPERTARIFA  

                 where CD_PROVEEDOR = @CD_PROVEEDOR

                   and CD_PRODUCTO  = @CD_PRODUCTO

                   and FH_DESDE     = @f_desde

                   and NU_CANTDESDE = convert(integer, @NU_CANTDESDE)) 

      begin

         set @po_c_error   = 1

         set @po_d_error   = 'Tarifa de Periodo Producto inexistente'

         return 

      end

   else

      begin 

         update TCF027_PRODPERTARIFA 

--       set FH_HASTA          = @f_hasta          ,

         set NU_PRECIOUNIVAL   = convert(decimal(15,4), @NU_PRECIOUNIVAL) , 

             NU_PORCTARIFA     = convert(decimal(10,2), @NU_PORCTARIFA)   ,

             NU_CANTHASTA      = convert(integer, @NU_CANTHASTA)          ,

             ST_HABILITADO     = @ST_HABILITADO    , 

             ST_PRECIOFIJO     = @ST_PRECIOFIJO    , 

             FH_MODIFICACION   = getdate()         , 

             NB_USUARIOMODIF   = @USU_MODI   

         where CD_PROVEEDOR = @CD_PROVEEDOR

           and CD_PRODUCTO  = @CD_PRODUCTO     

           and FH_DESDE     = @f_desde

           and NU_CANTDESDE = convert(integer, @NU_CANTDESDE)

    end

end

/* Opcion consulta: se consulta una pieza en particular */

if (@opcion = '3') 

begin

         select ppt.CD_PROVEEDOR, 

                ppt.CD_PRODUCTO, 

                ppt.FH_DESDE, 

                pp.FH_HASTA,

                ppt.NU_CANTDESDE, 

                ppt.NU_CANTHASTA, 

                ppt.NU_PRECIOUNIVAL, 

                ppt.NU_PORCTARIFA,

                ppt.ST_HABILITADO,

                ppt.ST_PRECIOFIJO 

         from TCF027_PRODPERTARIFA ppt

            inner join TCF026_PRODPERIODO pp on pp.CD_PROVEEDOR = ppt.CD_PROVEEDOR and pp.CD_PRODUCTO = ppt.CD_PRODUCTO and pp.FH_DESDE = ppt.FH_DESDE 

         where (ppt.CD_PROVEEDOR = @CD_PROVEEDOR or @CD_PROVEEDOR is null)

           and (ppt.CD_PRODUCTO  = @CD_PRODUCTO  or @CD_PRODUCTO is null)    

           and (ppt.FH_DESDE     = @FH_DESDE     or @FH_DESDE is null)

           and (ppt.NU_CANTDESDE = convert(integer, @NU_CANTDESDE) or @NU_CANTDESDE is null)

end

/* Opcion eliminacion */

if (@opcion = '4') 

begin

   if not exists (select * 

                 from TCF027_PRODPERTARIFA  

                 where CD_PROVEEDOR = @CD_PROVEEDOR

                   and CD_PRODUCTO  = @CD_PRODUCTO

                   and FH_DESDE     = @f_desde

                   and NU_CANTDESDE = convert(integer, @NU_CANTDESDE)) 

      begin

         set @po_c_error   = 1

         set @po_d_error   = 'Tarifa de Periodo Producto inexistente'

         return 

      end

   else

      begin 

         delete from TCF027_PRODPERTARIFA

         where CD_PROVEEDOR = @CD_PROVEEDOR

           and CD_PRODUCTO  = @CD_PRODUCTO

           and FH_DESDE     = @f_desde

           and NU_CANTDESDE = convert(integer, @NU_CANTDESDE)

    end

end



/* Opcion eliminar todos */

if (@opcion = '5') 

begin

   if not exists (select * 

                 from TCF027_PRODPERTARIFA  

                 where CD_PROVEEDOR = @CD_PROVEEDOR

                   and CD_PRODUCTO  = @CD_PRODUCTO

                   and FH_DESDE     = @f_desde) 

      begin

         set @po_c_error   = 1

         set @po_d_error   = 'Tarifa de Periodo Producto inexistente'

         return 

      end

   else

      begin 

         delete from TCF027_PRODPERTARIFA

         where CD_PROVEEDOR = @CD_PROVEEDOR

           and CD_PRODUCTO  = @CD_PRODUCTO

           and FH_DESDE     = @f_desde

    end

end
go 


sp_procxmode 'sp_CFS_ABMC_ProductosPeriodosTarifas', anymode
go 

setuser
go 

