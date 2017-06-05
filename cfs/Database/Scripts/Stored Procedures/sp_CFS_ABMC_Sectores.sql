
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_Sectores;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_ABMC_Sectores;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_ABMC_Sectores' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_ABMC_Sectores

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_Sectores;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_ABMC_Sectores ( 
	@opcion             char(1) = ''        ,
	@CD_SECTOR          varchar(15) = ''    ,
	@NB_SECTOR          varchar(50) = ''    ,
	@NB_SECTORABREV     varchar(10) = ''    ,
	@CD_SECTOR_ALT      varchar(15) = ''    ,
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
                 from TCF007_SECTOR  
                 where CD_SECTOR = @CD_SECTOR) 
      begin   
         insert into TCF007_SECTOR(
                 CD_SECTOR, 
                 NB_SECTOR, 
                 NB_SECTORABREV, 
                 CD_SECTOR_ALT, 
                 ST_HABILITADO, 
                 FH_ALTA, 
                 NB_USUARIOALTA)
         values (@CD_SECTOR, 
                 @NB_SECTOR, 
                 @NB_SECTORABREV, 
                 @CD_SECTOR_ALT, 
                 @ST_HABILITADO, 
                 getdate(), 
                 @USU_MODI)
      end
   else
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Codigo de sector duplicado'
         return 
      end
end
/* Opcion modificacion */
if (@opcion = '2') 
begin
   if not exists (select * 
                 from TCF007_SECTOR  
                 where CD_SECTOR = @CD_SECTOR) 
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Codigo de sector inexistente'
         return 
      end
   else
      begin	
         update TCF007_SECTOR 
         set NB_SECTOR         = @NB_SECTOR        , 
             NB_SECTORABREV    = @NB_SECTORABREV   , 
             CD_SECTOR_ALT     = @CD_SECTOR_ALT    ,
             ST_HABILITADO     = @ST_HABILITADO    , 
             FH_MODIFICACION   = getdate()         , 
             NB_USUARIOMODIF   = @USU_MODI   
         where CD_SECTOR = @CD_SECTOR
	  end
end
/* Opcion consulta: se consulta una pieza en particular */
if (@opcion = '3') 
begin
         select CD_SECTOR, 
                NB_SECTOR, 
                NB_SECTORABREV, 
                CD_SECTOR_ALT, 
                ST_HABILITADO
         from TCF007_SECTOR  
         where CD_SECTOR = @CD_SECTOR or @CD_SECTOR is null
         order by CD_SECTOR
end
/* Opcion eliminacion */
if (@opcion = '4') 
begin
   if not exists (select * 
                 from TCF007_SECTOR  
                 where CD_SECTOR = @CD_SECTOR) 
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Codigo de sector inexistente'
         return 
      end
   else
      begin	
         delete from TCF007_SECTOR  
         where CD_SECTOR = @CD_SECTOR
	  end
end
go 


sp_procxmode 'sp_CFS_ABMC_Sectores', unchained
go 

setuser
go 

