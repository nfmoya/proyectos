
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_grabar_nomedibles_det;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_grabar_nomedibles_det;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_grabar_nomedibles_det' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_grabar_nomedibles_det

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_grabar_nomedibles_det;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_grabar_nomedibles_det(  
-- drop procedure sp_CFS_grabar_nomedibles_det
@cd_conciliacion  int         ,
@cd_periodo       char(6)     ,
@pi_l_detalle     typ_lista   ,  
@po_c_error       typ_c_error output,  
@po_d_error       typ_d_error output  
)  
as  
begin
print 'comienzo'
print @cd_periodo
	declare @sep               varchar(1)    ,  
			@subSep            varchar(1)    ,  
			@v_lista           typ_lista     ,  
			@v_sublista        typ_lista     ,
			@cdProducto        char(12)      ,
			@nbObservaciones   char(180)     ,
            @tpSolucion        char(6)
  
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
	if (@pi_l_detalle != '' and @pi_l_detalle is not null)
		begin
			set @v_lista = @pi_l_detalle + @sep  
			while (@v_lista is not null)  
				begin  
					--limpio las variables   
					set @cdProducto        = ''
					set @nbObservaciones   = ''

                    --obtengo un elemento de la lista  
                    set @v_sublista = substring(@v_lista, 1,charindex(@sep,@v_lista)-1)+ @subSep
        
                    --obtengo el resto de la lista
                    set @v_lista    = substring(@v_lista, charindex(@sep,@v_lista)+1, char_length(@v_lista))
                           
                    --separo los subelementos        
                    set @cdProducto        = rtrim(substring(@v_sublista,1,charindex(@subSep,@v_sublista)-1))
--print @cdProducto       
                    set @v_sublista        = substring(@v_sublista, charindex(@subSep,@v_sublista)+1, char_length(@v_sublista))   

                    set @nbObservaciones   = rtrim(substring(@v_sublista,1,charindex(@subSep,@v_sublista)-1))
--print @nbObservaciones       
                    set @v_sublista        = substring(@v_sublista, charindex(@subSep,@v_sublista)+1, char_length(@v_sublista))   
                       
                    set @tpSolucion        = substring(@v_sublista,1,charindex(@subSep,@v_sublista)-1)
--print @tpSolucion       

					UPDATE TCF030_NOMEDIBLES_PROD 
						SET CD_CONCILIACION = @cd_conciliacion, CD_CONCILIACION_ACT = @cd_conciliacion, NB_OBSERVACIONES = @nbObservaciones, TP_SOLUCION = @tpSolucion
					where CD_PRODUCTO    = @cdProducto
					  and CD_PERIODO_ACT = @cd_periodo
					  
					update TCF012_SERVFACDET
					set CD_CONC_NO_MEDIBLE = @cd_conciliacion 
					where CD_PRODUCTO    = @cdProducto
					  and CD_PERIODOFACT = @cd_periodo
				   
				end
		end

end
go 


sp_procxmode 'sp_CFS_grabar_nomedibles_det', anymode
go 

setuser
go 


