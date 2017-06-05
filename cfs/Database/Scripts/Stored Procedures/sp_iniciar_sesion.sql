
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_iniciar_sesion;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_iniciar_sesion;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_iniciar_sesion' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_iniciar_sesion

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_iniciar_sesion;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_iniciar_sesion(   
@d_user            varchar(40),   
@po_usuario_id     numeric(18,0) output,   
@po_sep_listas     varchar(1)    output,   
@po_sep_sublistas  varchar(1)    output,   
@po_full_name      varchar(120)  output,   
@po_sector         varchar(6)    output,   
@po_c_error        typ_c_error   output,   
@po_d_error        typ_d_error   output   
)   
as   
-------------------------------------------------------------------------------   
--objetivo: se llamará al entrar a la aplicación. El po_usuario_id será el q se   
--maneje a lo largo de la aplicación por el resto de los servicios.   
---- Parametros de entrada:    
---- d_user: usuario de loggeo del sistema    
---- Parametros de salida: separadores de listas, para utilizar   
---- en la aplicación y en la base.   
---- Cursor de Accesos de acuerdo a los perfiles del usuario   
-------------------------------------------------------------------------------   
begin   
  declare @dummy numeric(18,0)  
  set @po_c_error = 0   
  set @po_d_error = null   
  set @po_sector = '0'
     --obtener el id de usuario   
  select @po_usuario_id = usu.ID_USUARIO,   
         @po_full_name  = usu.DS_APELLIDO   +', '+ usu.DS_NOMBRE
  from TCF016_USUARIO usu 
  where usu.CD_USUARIO = @d_user   
   -- and usu.ST_HABILITADO in( 'A', 'U')   
    and usu.ST_HABILITADO in( 'S')

  --obtener el SECTOR de usuario
  select @po_sector = CD_SECTOR
  from TCF016_USUARIO
  where CD_USUARIO = @d_user AND ID_PERFIL != 1
 
  set @po_c_error = @@error       
  if (@po_c_error  <> 0)   
  begin    
      set @po_d_error =  convert(varchar,@po_c_error) + ' - Error al obtener id del usuario. '    
      return   
  end   
     
  --obtener el cursor con los permisos   
  select distinct ac.ID_ACCESO, ac.D_ACCESO   
    from TCF016_USUARIO up,   
         TCF018_ACCESOS_POR_PERFILES ap,   
         TCF017_ACCESOS ac   
   where up.ID_USUARIO = @po_usuario_id   
    and  ap.ID_PERFIL = up.ID_PERFIL     
    and  ac.ID_ACCESO = ap.ID_ACCESO     
    --and E_USU_PERFIL in( 'A', 'U')
   
  set @po_c_error = @@error       
  if (@po_c_error  <> 0)   
  begin    
      set @po_d_error =  convert(varchar,@po_c_error) + ' - Error al obtener los permisos del usuario. '    
   
      return   
  end     
    
  --obtengo los separadores de registros y de campos  
  execute sp_separador_registros   
                              @po_separador_registro = @po_sep_listas output,   
                              @po_separador_campo    = @po_sep_sublistas output,   
                              @po_c_error            = @po_c_error output,   
                              @po_d_error            = @po_d_error output   
   
    if (@po_c_error  <> 0)   
    begin   
	  set @po_d_error = 'Error llamando a sp_separador_registros : ' + @po_d_error 
      return          
    end                                       
end
go 


sp_procxmode 'sp_iniciar_sesion', unchained
go 

setuser
go 

