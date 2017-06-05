-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_grabar_nomedibles_cab;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_grabar_nomedibles_cab;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_grabar_nomedibles_cab' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_grabar_nomedibles_cab

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_grabar_nomedibles_cab;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_grabar_nomedibles_cab(  
-- drop procedure sp_CFS_grabar_nomedibles_cab
@cd_conciliacion  int         ,
@cd_proveedor     varchar(6)  ,
@cd_sector        varchar(6)  ,
@cd_periodo       varchar(6)  ,
@st_conciliacion  char(3)     ,
@nb_usuario       char(8)     ,
@po_conciliacion  int output,  
@po_c_error       typ_c_error output,  
@po_d_error       typ_d_error output  
)  
as  
begin
  declare @sep             varchar(1)   ,  
          @subSep          varchar(1)   ,  
          @v_lista         typ_lista    ,  
          @v_sublista      typ_lista    ,   
          @lote            int          ,
          @orden           int          ,
          @logconcil       varchar(10)  ,
          @logcondif       varchar(10)  ,
          @max_orden       numeric      ,
          @nrolog          int          
  set @po_c_error = 0  
  set @po_d_error = null  

  -- SI EL NUMERO DE CONCILIACION = 0 (PORQUE INGRESO DESDE LOS FILTROS) VALIDO SI EXISTE UNA CONCILIACION DE ACUERDO A ESOS MISMOS FILTROS
  if (@cd_conciliacion = 0)
     begin
        select @cd_conciliacion = CD_CONCILIACION
        from TCF029_NOMEDIBLES
        where CD_PROVEEDOR    = @cd_proveedor
          and CD_SECTOR       = @cd_sector
          and CD_PERIODOFACT  = @cd_periodo
          and ST_CONCILIACION = 'GRA'
     end 

  -- VUELVO A VERIFICAR EL NUMERO DE CONCILIACION DE ACUERDO A SI SE INGRESO O EXISTE POR LOS FILTROS INGRESADOS
  if (@cd_conciliacion = 0)
     begin
        set @cd_conciliacion = (isnull((select max(CD_CONCILIACION)+1 from TCF029_NOMEDIBLES),1))

        insert into TCF029_NOMEDIBLES (CD_CONCILIACION,CD_PROVEEDOR,CD_SECTOR,CD_PERIODOFACT,ST_CONCILIACION,FH_CONCILIACION,NB_USUARIOCONCIL) 
        select @cd_conciliacion, @cd_proveedor, @cd_sector, @cd_periodo, @st_conciliacion, getdate(), @nb_usuario

        set @logconcil = 'INSNOMEDI'
     end
  else
     begin
        update TCF029_NOMEDIBLES 
        set ST_CONCILIACION = @st_conciliacion
        where CD_CONCILIACION = @cd_conciliacion

        set @logconcil = 'UPDNOMEDI'
     end

  insert into TCF015_LOG( CD_USUARIO, FH_LOG, CD_EVENTO, NB_LOG)
  values( @nb_usuario, getdate(), @logconcil, 'Conc nomedibles: '  + convert(varchar(6), @cd_conciliacion) + 
                                              ', Proveedor: '      + @cd_proveedor + 
                                              ', Sector: '         + @cd_sector + 
                                              ', Periodo Fact: '   + @cd_periodo + 
                                              ', Estado: '         + @st_conciliacion)

  set @po_conciliacion = @cd_conciliacion
end
go 


sp_procxmode 'sp_CFS_grabar_nomedibles_cab', anymode
go 

setuser
go 

