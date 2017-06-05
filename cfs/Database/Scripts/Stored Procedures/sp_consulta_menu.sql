-- Tablas a reemplazar 
--    - INV_accesos 7 referencias
--    - INV_usuarios_perfiles 4 referencias
--    - INV_menu 8 referencias
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_consulta_menu;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_consulta_menu;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_consulta_menu' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
  setuser 'dbo'
  drop procedure sp_consulta_menu

END
go 

IF (@@error != 0)
BEGIN
  PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_consulta_menu;1'"
  SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_consulta_menu (  
@pi_id_usuario  numeric(18,0),  
@po_c_error     typ_c_error output,  
@po_d_error     typ_d_error output  
)  
as  
-------------------------------------------------------------------  
--Objetivo: obtener las entradas de menú para los roles   
-- de un usuario del sistema  
--Parámetros de entrada: pi_usuario (varchar2)  
--Parámetros de salida:   
--      cursor con los valores del menú: XXXX,   
--po_c_error y po_d_error  
-------------------------------------------------------------------  
begin     
  if (@pi_id_usuario is null or @pi_id_usuario = 0) 
    begin  
      set @po_c_error = 3 
      set @po_d_error = 'No se recibió usuario. '  
      return  
  end  
  declare @cant_filas int       
  set @po_c_error = 0  
  set @po_d_error = null  
    
--agregamos la opción de menú Home en duro, ya que ninguna entrada de menú lo   
--tiene como padre  
  select m.ID_MENU,                   
         m.D_MENU,   
         m.X_URL_MENU,  
         m.ID_PADRE,  
         m.N_NIVEL,  
         m.N_ORDEN    
  from TCF020_MENU m  
  where ID_MENU in (1,32) -- home y cambio de clave  
    and F_BAJA is null
  union    
  select m.ID_MENU,                   
         m.D_MENU,   
         m.X_URL_MENU,  
         m.ID_PADRE,  
         m.N_NIVEL,  
         m.N_ORDEN   
  from TCF020_MENU m  
  where exists  (  
                  select *  
                  from   TCF017_ACCESOS a,                   --id_acceso, id_menu  
                         TCF019_ACCESOS_POR_PERFILES ap,     --id_perfil  
                         TCF016_USUARIO up         --id_perfil, id_usuario  
                  WHERE  a.ID_ACCESO = ap.ID_ACCESO    
                    AND  ap.ID_PERFIL = up.ID_PERFIL  
                    AND  up.ID_USUARIO =  @pi_id_usuario    
                    and  (a.ID_MENU = m.ID_MENU)  
                )  
    and m.F_BAJA is null
  union  
  select m1.ID_MENU,                   
         m1.D_MENU,   
         m1.X_URL_MENU,  
         m1.ID_PADRE,  
         m1.N_NIVEL,  
         m1.N_ORDEN    
  from   TCF017_ACCESOS a,                   --id_acceso, id_menu  
         TCF019_ACCESOS_POR_PERFILES ap,     --id_perfil  
         TCF016_USUARIO up,  --id_perfil, id_usuario  
         TCF020_MENU m,  
         TCF020_MENU m1  
  WHERE  a.ID_ACCESO = ap.ID_ACCESO    
    AND  ap.ID_PERFIL = up.ID_PERFIL  
    AND  up.ID_USUARIO =  @pi_id_usuario    
    AND  a.ID_MENU = m.ID_MENU  
    AND  m.ID_PADRE = m1.ID_MENU  
    AND  m.F_BAJA IS NULL
    AND  m1.F_BAJA IS NULL
  union
  select  m2.ID_MENU,                   
         m2.D_MENU,   
         m2.X_URL_MENU,  
         m2.ID_PADRE,  
         m2.N_ORDEN,           
         m2.N_NIVEL  
           from   TCF017_ACCESOS a,                   --id_acceso, id_menu  
         TCF019_ACCESOS_POR_PERFILES ap,     --id_perfil  
         TCF016_USUARIO up,  --id_perfil, id_usuario  
         TCF020_MENU m,  
         TCF020_MENU m1 , 
         TCF020_MENU m2                           
  WHERE  a.ID_ACCESO = ap.ID_ACCESO    
    AND  ap.ID_PERFIL = up.ID_PERFIL  
    AND  up.ID_USUARIO =  @pi_id_usuario    
    AND  a.ID_MENU = m.ID_MENU  
    AND  m.ID_PADRE = m1.ID_MENU  
    AND  m1.ID_PADRE = m2.ID_MENU                      
    AND  m.F_BAJA IS NULL
    AND  m1.F_BAJA IS NULL
    AND  m2.F_BAJA IS NULL
  ORDER BY 5,6  
  
  set @po_c_error = @@error,  
      @cant_filas = @@rowcount    
  
  if (@po_c_error  <> 0)  
    begin  
      set @po_d_error = convert(varchar,@po_c_error) + ' - Error al consultar menu'  
    end  
  
  if (@cant_filas = 0)  
    begin  
      set @po_c_error = 1  
      set @po_d_error = 'No se encontraron datos del menú. '  
      return        
    end  
   
end
go 


sp_procxmode 'sp_consulta_menu', unchained
go 

setuser
go 

