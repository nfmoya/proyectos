-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_GetUsuario;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_GetUsuario;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_GetUsuario' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_GetUsuario

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_GetUsuario;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_GetUsuario (   
@pi_usu_d_user      varchar (40),   
@po_c_error         typ_c_error output,    
@po_d_error         typ_d_error output )   
as    
----------------------------------------------------------------------
--Objetivo: Obtener los datos de Login de un usuario   
----------------------------------------------------------------------
begin    
  set @po_c_error = 0    
  set @po_d_error = null    
  If @pi_usu_d_user is null    
    begin    
      set @po_c_error = 3     
 
      set @po_d_error = 'No se informó el usuario'    
      return          
  end
  select 
        CD_USUARIO as USU_D_USER,
        ST_HABILITADO as USU_HABILITADO,
         ''as USU_ESTADO,
         ''as USU_ULT_ACCESO,   
         ''as USU_HORA_ACCESO,   
         'USU_CLAVE1'as USU_CLAVE1,   
         'USU_CLAVE2'as USU_CLAVE2,   
         'USU_CLAVE3'as USU_CLAVE3,   
         'USU_CLAVE4'as USU_CLAVE4,   
         0 as USU_INT_FALLIDOS,   
         '' as USU_CLAVE   
  from TCF016_USUARIO    
  where CD_USUARIO = @pi_usu_d_user  
/*
         USU_D_USER,   
         USU_HABILITADO,   
         USU_ESTADO,   
         USU_ULT_ACCESO,   
         USU_HORA_ACCESO,   
         USU_CLAVE1,   
         USU_CLAVE2,   
         USU_CLAVE3,   
         USU_CLAVE4,   
         USU_INT_FALLIDOS,   
         USU_CLAVE   
  from TCF022_LOGIN_USUARIOS    
  where USU_D_USER = @pi_usu_d_user  
*/ 
  set @po_c_error = @@error   
  if (@po_c_error  <> 0)    
    begin      
    set @po_d_error = 'Error al consultar los datos del login'    
    end    
 end
go 


sp_procxmode 'sp_GetUsuario', unchained
go 

setuser
go 

