
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'INVHWR.dbo.sp_UpdClaveUsuario;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "INVHWR.dbo.sp_UpdClaveUsuario;1" >>>>>'
go 

use INVHWR
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_UpdClaveUsuario' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_UpdClaveUsuario

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'INVHWR.dbo.sp_UpdClaveUsuario;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE sp_UpdClaveUsuario   (   
@pi_usu_d_user	     varchar (40),   
@pi_usu_clave1	     varchar (50),   
@pi_usu_clave2	     varchar (50),   
@pi_usu_clave3	     varchar (50),   
@pi_usu_clave4	     varchar (50),   
@pi_usu_clave	     varchar (50),   
@po_c_error     typ_c_error output,    
@po_d_error     typ_d_error output )   
as    
----------------------------------------------------------------------
--Objetivo: Actualiza los datos del usuario   
----------------------------------------------------------------------
begin
  set @po_c_error = 0    
  set @po_d_error = null    
  If (@pi_usu_d_user is null)  
    begin    
      set @po_c_error = 3   
      set @po_d_error = 'No se informó el usuario'    
      return          
  end    
  update INV_login_usuarios    
     set usu_clave1       = @pi_usu_clave1,   
         usu_clave2       = @pi_usu_clave2,   
         usu_clave3       = @pi_usu_clave3,   
         usu_clave4       = @pi_usu_clave4,   
	     usu_ult_acceso	  = Convert (char(8), GetDate(), 112),  
	     usu_hora_acceso  = Convert (char(8), GetDate(), 108),
         usu_int_fallidos = 0,   
         usu_estado       = 'N',   
         usu_clave        = @pi_usu_clave   
  where usu_d_user = @pi_usu_d_user   

  set @po_c_error = @@error   
  if (@po_c_error  <> 0)    
    begin      
      set @po_d_error = 'Error al consultar los datos del login'    
	  return  
  end    
end
go 


sp_procxmode 'sp_UpdClaveUsuario', unchained
go 

setuser
go 

