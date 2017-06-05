
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_ProductosSectores;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_ABMC_ProductosSectores;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_ABMC_ProductosSectores' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
  setuser 'dbo'
  drop procedure sp_CFS_ABMC_ProductosSectores

END
go 

IF (@@error != 0)
BEGIN
  PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_ProductosSectores;1'"
  SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_ABMC_ProductosSectores ( 

  @opcion             char(1) = ''        ,

  @CD_PROVEEDOR       char(6) = ''        ,

  @CD_PRODUCTO        varchar(12) = ''    ,

  @CD_SECTOR          varchar(15) = ''    ,

  @ST_HABILITADO      char(1) = 'S'       ,

  @USU_MODI           char(8) = ''        ,

  @CD_SECTOR_OLD      varchar(15) = ''    ,

    @po_c_error         typ_c_error output  ,

    @po_d_error         typ_d_error output  

) AS 

  set @po_c_error = 0  

  set @po_d_error = null 

/* Opcion Alta */

if (@opcion = '1') 

begin

 

   if not exists (select * 

                 from TCF008_PRODSECTOR  

                 where CD_PROVEEDOR = @CD_PROVEEDOR 

                   and CD_PRODUCTO  = @CD_PRODUCTO

                   and CD_SECTOR    = @CD_SECTOR) 

      begin   

         insert into TCF008_PRODSECTOR(

                 CD_PROVEEDOR, 

                 CD_PRODUCTO, 

                 CD_SECTOR, 

                 ST_HABILITADO, 

                 FH_ALTA, 

                 NB_USUARIOALTA)

         values (@CD_PROVEEDOR, 

                 @CD_PRODUCTO, 

                 @CD_SECTOR, 

                 @ST_HABILITADO, 

                 getdate(), 

                 @USU_MODI)

      end

   else

      begin

         set @po_c_error   = 1

         set @po_d_error   = 'Sector de producto duplicado'

         return 

      end

end

/* Opcion modificacion */

if (@opcion = '2') 

begin

 

   if not exists (select * 

                 from TCF008_PRODSECTOR  

                 where CD_PROVEEDOR = @CD_PROVEEDOR 

                   and CD_PRODUCTO  = @CD_PRODUCTO

                   and CD_SECTOR    = @CD_SECTOR_OLD) 

      begin

         set @po_c_error   = 1

         set @po_d_error   = 'Sector de producto inexistente'

         return 

      end

   else

      begin 

         if not exists (select * 

                       from TCF008_PRODSECTOR  

                       where CD_PROVEEDOR = @CD_PROVEEDOR 

                         and CD_PRODUCTO  = @CD_PRODUCTO

                         and CD_SECTOR    = @CD_SECTOR

                         and @CD_SECTOR    <> @CD_SECTOR_OLD

                         ) 
            begin 
               update TCF008_PRODSECTOR 

               set CD_SECTOR         = @CD_SECTOR        ,

                   ST_HABILITADO     = @ST_HABILITADO    , 

                   FH_MODIFICACION   = getdate()         , 

                   NB_USUARIOMODIF   = @USU_MODI   

               where CD_SECTOR = @CD_SECTOR_OLD and CD_PROVEEDOR =  @CD_PROVEEDOR and CD_PRODUCTO = @CD_PRODUCTO

            end

         else

               set @po_c_error   = 1

               set @po_d_error   = 'Sector de producto duplicado'

               return 

          end

end

/* Opcion consulta: se consulta una pieza en particular */

if (@opcion = '3') 

begin

         select CD_PROVEEDOR, 

                CD_PRODUCTO, 

                TCF008_PRODSECTOR.CD_SECTOR, 

                TCF007_SECTOR.NB_SECTOR,

                TCF008_PRODSECTOR.ST_HABILITADO

         from TCF008_PRODSECTOR  

         inner join TCF007_SECTOR ON TCF008_PRODSECTOR.CD_SECTOR = TCF007_SECTOR.CD_SECTOR

         where (CD_PROVEEDOR = @CD_PROVEEDOR or @CD_PROVEEDOR is null)

           and (CD_PRODUCTO  = @CD_PRODUCTO  or @CD_PRODUCTO is null)

           and (TCF008_PRODSECTOR.CD_SECTOR = @CD_SECTOR    or @CD_SECTOR is null)

         order by CD_PROVEEDOR, CD_PRODUCTO, TCF008_PRODSECTOR.CD_SECTOR

end

/* Opcion eliminacion */

if (@opcion = '4') 

begin

   if not exists (select * 

                 from TCF008_PRODSECTOR  

                 where CD_PROVEEDOR = @CD_PROVEEDOR 

                   and CD_PRODUCTO  = @CD_PRODUCTO

                   and CD_SECTOR    = @CD_SECTOR) 

      begin

         set @po_c_error   = 1

         set @po_d_error   = 'Sector de producto inexistente'

         return 

      end

   else

      begin 

         delete from TCF008_PRODSECTOR  

         where CD_PROVEEDOR = @CD_PROVEEDOR 

           and CD_PRODUCTO  = @CD_PRODUCTO

           and CD_SECTOR    = @CD_SECTOR

    end

end
go 


sp_procxmode 'sp_CFS_ABMC_ProductosSectores', unchained
go 

setuser
go 

