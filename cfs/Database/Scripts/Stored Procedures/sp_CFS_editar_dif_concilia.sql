
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_editar_dif_concilia;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_editar_dif_concilia;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_editar_dif_concilia' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_editar_dif_concilia

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_editar_dif_concilia;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_editar_dif_concilia ( 
@pi_cd_conciliacion   numeric(6,0), 
@pi_cd_orden          numeric(18,0), 
@pi_cd_remito         varchar(13), 
@pi_ct_diferencia     numeric(18,4), 
@pi_im_precioTotal    numeric(18,4), 
@pi_tp_solucion       char(6), 
@pi_st_diferencia     char(3),
@pi_observacion       varchar(180), 
@pi_cod_event         varchar(15), 
@pi_id_usuario        varchar(8),
@po_c_error           typ_c_error output, 
@po_d_error           typ_d_error output 
)  as
/*
** Objetivo: Actualiza los datos de la diferecia de conciliacion 
** y registrar en la tabla de logs el registro
*/

begin  
  declare @cant_filas int ,
          @datos varchar(1000)

  set @po_c_error = 0 
  set @po_d_error = null 
  set @datos = null

  if (@pi_cd_conciliacion is null or @pi_cd_conciliacion < 0)
  begin
    set @po_c_error = 1 
    set @po_d_error = 'No se recibio codigo de conciliacion' 

    return
  end

  if (@pi_id_usuario is null or @pi_id_usuario = '')
  begin
    set @po_c_error = 1 
    set @po_d_error = 'No se recibio usuario' 

    return
  end

  --  Actualizo la tabla de conciliacion con los valores nuevos
  UPDATE TCF014_DIFCONCILIA 
  SET TP_SOLUCION = @pi_tp_solucion,
      NB_OBSERVACIONES = @pi_observacion
  WHERE CD_CONCILIACION = @pi_cd_conciliacion


  set @po_c_error = @@error
  if(@po_c_error <> 0)
  begin
    set @po_d_error = 'Error al actualizar registro de diferecia de conciliacion'
    return
  end

  --Inserto los demas registros en la tabla de log

  set @datos = 'Concilia: ' + convert(varchar(6),@pi_cd_conciliacion) +' -Orden: '+ convert(varchar(18),@pi_cd_orden) +' -Cantidad '+ convert(varchar(18),@pi_ct_diferencia)
                +' -Precio Total: '+ convert(varchar(18),@pi_im_precioTotal) +' -Solucion: '+ @pi_tp_solucion +' -Estado: '+ convert(varchar(18),@pi_st_diferencia )

  INSERT INTO TCF015_LOG (CD_USUARIO,FH_LOG, CD_EVENTO, NB_LOG)
  VALUES (
          @pi_id_usuario
          
          , getdate()
          
          , @pi_cod_event

          , @datos

          ) 

  set @po_c_error = @@error
  if(@po_c_error <> 0)
  begin
    set @po_d_error = 'Error al insertar registro tabla de log'
    return
  end
end
go 


sp_procxmode 'sp_CFS_editar_dif_concilia', unchained
go 

setuser
go 

