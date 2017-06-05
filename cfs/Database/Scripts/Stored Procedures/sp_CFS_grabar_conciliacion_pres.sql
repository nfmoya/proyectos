
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_grabar_conciliacion_pres;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_grabar_conciliacion_pres;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_grabar_conciliacion_pres' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_grabar_conciliacion_pres

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_grabar_conciliacion_pres;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE sp_CFS_grabar_conciliacion_pres(  
-- drop procedure sp_CFS_grabar_conciliacion_pres
@cd_conciliacion  int         ,
@st_conciliacion  char(3)     ,
@pi_l_servicios   typ_lista   ,  
@po_c_error       typ_c_error output,  
@po_d_error       typ_d_error output  
)  
as  
begin
  declare @sep             varchar(1)  ,  
          @subSep          varchar(1)  ,  
          @v_lista         typ_lista   ,  
          @v_sublista      typ_lista   ,   
          @lote            int         ,
          @orden           int         ,
          @im_precio       numeric     ,
          @im_total        numeric     ,
          @precio          varchar(50) ,
          @total           varchar(50) ,
          @im_cotiza       varchar(15) ,
          @fh_cotiza       varchar(10)
  
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
  if (@pi_l_servicios != '' and @pi_l_servicios is not null)
     begin
        set @v_lista = @pi_l_servicios + @sep  
        while (@v_lista is not null)  
           begin  
              --limpio las variables   
              set @lote   = 0
              set @orden  = 0 
              set @precio = '' 
              set @total  = ''
              set @im_precio = 0 
              set @im_total  = 0 

              declare @linea varchar(100)          
              --obtengo un elemento de la lista  
              set @v_sublista = substring(@v_lista, 1,charindex(@sep,@v_lista)-1)+ @subSep

              --obtengo el resto de la lista
              set @v_lista    = substring(@v_lista, charindex(@sep,@v_lista)+1, char_length(@v_lista))

              --separo los subelementos
              set @lote       = convert(numeric,substring(@v_sublista,1,charindex(':',@v_sublista)-1))
              set @v_sublista = substring(@v_sublista, charindex(':',@v_sublista)+1, char_length(@v_sublista))   
--set @linea = convert(varchar(10),@lote)
--print @linea

              set @orden      = convert(numeric,substring(@v_sublista,1,charindex(':',@v_sublista)-1))
              set @v_sublista = substring(@v_sublista, charindex(':',@v_sublista)+1, char_length(@v_sublista))   
--set @linea = convert(varchar(10),@orden)
--print @linea

              set @precio     = ltrim(substring(@v_sublista,1,charindex(@subSep,@v_sublista)-1))
              set @v_sublista = substring(@v_sublista, charindex(':',@v_sublista)+1, char_length(@v_sublista))
--print @precio

              set @total      = substring(@v_sublista,1,charindex(':',@v_sublista)-1)
              set @v_sublista = substring(@v_sublista, charindex(':',@v_sublista)+1, char_length(@v_sublista))
--print @total

              set @im_cotiza  = substring(@v_sublista,1,charindex(':',@v_sublista)-1)
              set @v_sublista = substring(@v_sublista, charindex(':',@v_sublista)+1, char_length(@v_sublista))
--print @im_cotiza              
              
              set @fh_cotiza  = substring(@v_sublista,1,charindex(':',@v_sublista)-1)
              set @v_sublista = ''
--print @fh_cotiza

              update TCF010_SERVPRESDET
              set CD_CONCILIACION = @cd_conciliacion 
              where CD_LOTESERV = @lote
                and CD_ORDEN    = @orden

              update TCF010_SERVPRESDET
              set IM_PRECIOUNIT   = convert(numeric(9,4) , @precio), 
                  IM_PRECIOTOTAL  = convert(numeric(11,2), @total),
                  NB_ATRIBUTOREF1 = @im_cotiza,
                  NB_ATRIBUTOREF2 = @fh_cotiza
              where CD_LOTESERV = @lote
                and CD_ORDEN    = @orden
                and @st_conciliacion = 'APR'

           end
     end

end
go 


sp_procxmode 'sp_CFS_grabar_conciliacion_pres', unchained
go 

setuser
go 

