
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_General;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_ABMC_General;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_ABMC_General' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_ABMC_General

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_General;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_ABMC_General ( 
  @opcion             char(1) = ''        ,
  @CD_TABLA           char(6) = ''        ,
  @CD_CODTABLA        char(12) = ''        ,
  @NB_CODTABLA        varchar(30) = ''    ,
  @NB_CODTABLACORTO   varchar(12) = ''    ,
  @NB_ATRIBUTOTABLA1  varchar(30) = ''    ,
  @NB_ATRIBUTOTABLA2  varchar(30) = ''    ,
  @NB_ATRIBUTOTABLA3  varchar(30) = ''    ,
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
                 from TCF001_GENERAL  
                 where CD_TABLA    = @CD_TABLA
                   and CD_CODTABLA = @CD_CODTABLA) 
      begin   
         insert into TCF001_GENERAL(
                 CD_TABLA, 
                 CD_CODTABLA, 
                 NB_CODTABLA, 
                 NB_CODTABLACORTO, 
                 NB_ATRIBUTOTABLA1, 
                 NB_ATRIBUTOTABLA2, 
                 NB_ATRIBUTOTABLA3, 
                 ST_HABILITADO, 
                 FH_ALTA, 
                 NB_USUARIOALTA)
         values (@CD_TABLA, 
                 @CD_CODTABLA, 
                 @NB_CODTABLA, 
                 @NB_CODTABLACORTO, 
                 @NB_ATRIBUTOTABLA1, 
                 @NB_ATRIBUTOTABLA2, 
                 @NB_ATRIBUTOTABLA3, 
                 @ST_HABILITADO, 
                 getdate(), 
                 @USU_MODI)
      end
   else
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Codigo duplicado'
         return 
      end
end
/* Opcion modificacion */
if (@opcion = '2') 
begin
   if not exists (select * 
                 from TCF001_GENERAL  
                 where CD_TABLA    = @CD_TABLA
                   and CD_CODTABLA = @CD_CODTABLA) 
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Codigo inexistente'
         return 
      end
   else
      begin 
         update TCF001_GENERAL 
         set CD_TABLA          = @CD_TABLA          , 
             CD_CODTABLA       = @CD_CODTABLA       , 
             NB_CODTABLA       = @NB_CODTABLA       ,
             NB_CODTABLACORTO  = @NB_CODTABLACORTO  ,
             NB_ATRIBUTOTABLA1 = @NB_ATRIBUTOTABLA1 ,
             NB_ATRIBUTOTABLA2 = @NB_ATRIBUTOTABLA2 ,
             NB_ATRIBUTOTABLA3 = @NB_ATRIBUTOTABLA3 ,
             ST_HABILITADO     = @ST_HABILITADO     , 
             FH_MODIFICACION   = getdate()          , 
             NB_USUARIOMODIF   = @USU_MODI
         where CD_TABLA    = @CD_TABLA
           and CD_CODTABLA = @CD_CODTABLA
    end
end
/* Opcion consulta: se consulta una pieza en particular */
if (@opcion = '3') 
begin
         select CD_TABLA, 
                CD_CODTABLA, 
                NB_CODTABLA, 
                NB_CODTABLACORTO, 
                NB_ATRIBUTOTABLA1, 
                NB_ATRIBUTOTABLA2, 
                NB_ATRIBUTOTABLA3, 
                ST_HABILITADO
         from TCF001_GENERAL  
         where (CD_TABLA    = @CD_TABLA    or @CD_TABLA is null)
           and (CD_CODTABLA = @CD_CODTABLA or @CD_CODTABLA is null)
         order by CD_TABLA, CD_CODTABLA
end
/* Opcion eliminacion */
if (@opcion = '4') 
begin
   if not exists (select * 
                 from TCF001_GENERAL  
                 where CD_TABLA    = @CD_TABLA
                   and CD_CODTABLA = @CD_CODTABLA) 
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Codigo inexistente'
         return 
      end
   else
      begin 
         delete from TCF001_GENERAL  
         where CD_TABLA    = @CD_TABLA
           and CD_CODTABLA = @CD_CODTABLA
    end
end
go 


sp_procxmode 'sp_CFS_ABMC_General', unchained
go 

setuser
go 

