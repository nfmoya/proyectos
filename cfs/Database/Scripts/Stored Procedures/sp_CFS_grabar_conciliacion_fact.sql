
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_grabar_conciliacion_fact;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_grabar_conciliacion_fact;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_grabar_conciliacion_fact' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_grabar_conciliacion_fact

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_grabar_conciliacion_fact;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE sp_CFS_grabar_conciliacion_fact(  
-- drop procedure sp_CFS_grabar_conciliacion_fact
@cd_conciliacion  int         ,
@pi_l_facturas    typ_lista   ,  
@po_c_error       typ_c_error output,  
@po_d_error       typ_d_error output  
)  
as  
begin
  declare @sep             varchar(1)  ,  
          @subSep          varchar(1)  ,  
          @v_lista         typ_lista   ,  
          @v_sublista      typ_lista   ,   
          @lote            bigint      ,
          @orden           int
  
  execute sp_separador_registros  
             @po_separador_registro    = @sep output,  
             @po_separador_campo       = @subSep output,  
             @po_c_error               = @po_c_error output,  
             @po_d_error               = @po_d_error output                     

  if (@po_c_error  <> 0)  
    begin  
	  set @po_d_error = 'Error llamando a sp_separador_registros : ' + @po_d_error 
      return      
  end    
  set @po_c_error = 0  
  set @po_d_error = null  

  -- Actualizo los numeros de Conciliacion
  if (@pi_l_facturas != '' and @pi_l_facturas is not null)
     begin
        set @v_lista = @pi_l_facturas + @sep  
        while (@v_lista is not null)  
           begin  
              --limpio las variables   
              set @lote  = 0
              set @orden = 0 
          
              --obtengo un elemento de la lista  
              set @v_sublista = substring(@v_lista, 1,charindex(@sep,@v_lista)-1)+ @subSep

              --obtengo el resto de la lista
              set @v_lista    = substring(@v_lista, charindex(@sep,@v_lista)+1, char_length(@v_lista))
                   
              --separo los subelementos        
              set @lote   = convert(numeric,substring(@v_sublista,1,charindex(@subSep,@v_sublista)-1))

              set @v_sublista = substring(@v_sublista, charindex(@subSep,@v_sublista)+1, char_length(@v_sublista))   
               
              set @orden  = convert(numeric,substring(@v_sublista,1,charindex(@subSep,@v_sublista)-1))

              update TCF012_SERVFACDET
              set CD_CONCILIACION = @cd_conciliacion 
              where CD_LOTEFACT = @lote
                and CD_ORDEN    = @orden
           end
     end

end
go 


sp_procxmode 'sp_CFS_grabar_conciliacion_fact', unchained
go 

setuser
go 

