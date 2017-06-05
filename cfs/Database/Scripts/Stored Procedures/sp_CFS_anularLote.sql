
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_anularLote;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_anularLote;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_anularLote' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_anularLote

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_anularLote;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_anularLote (
@pi_tp_lote		      varchar(2),
@pi_cd_lote	   		  numeric(10,0),
@pi_id_usuario        varchar(10),
@po_c_error           typ_c_error output,
@po_d_error           typ_d_error output
) AS

begin
  declare 
	  @nb_archivo		varchar(30)
	 ,@cd_proveedor		char(6)
	 ,@st_lotecab		char(3)
	 ,@cd_evento		varchar(12)
	 ,@datos 			varchar(80)
 
  set @po_c_error = 0 
  set @po_d_error = null

  if (@pi_tp_lote is null or @pi_tp_lote = '')
  begin
    set @po_c_error = 1 
    set @po_d_error = 'No se recibi&oacute; tipo de lote' 
    return
  end
  
  if (@pi_cd_lote is null)
  begin
    set @po_c_error = 1 
    set @po_d_error = 'No se recibi&oacute; nro de lote' 
    return
  end
  
  if (@pi_id_usuario is null or @pi_id_usuario = '')
  begin
    set @po_c_error = 1 
    set @po_d_error = 'No se recibi&oacute; id de usuario' 
    return
  end

  -- Servicios Prestados
  if (@pi_tp_lote = 'SP')
	begin
	  set @cd_evento = 'UPDLOTPREANU'
	  
	  -- Actualiza cabecera
	  UPDATE TCF009_SERVPRESCAB
	  SET ST_LOTECAB = 'ANU'
		 ,FH_MODIFICACION = GETDATE()
		 ,NB_USUARIOMODIF = @pi_id_usuario
	  WHERE CD_LOTESERV = @pi_cd_lote
	  
	  -- Actualiza detalle
	  UPDATE TCF010_SERVPRESDET
	  SET ST_LOTEDET = 'N'
		 ,FH_MODIFICACION = GETDATE()
		 ,NB_USUARIOMODIF = @pi_id_usuario
	  WHERE CD_LOTESERV = @pi_cd_lote
	  
	  -- Datos para grabar log
	  SELECT @nb_archivo 	= NB_ARCHIVO
			,@cd_proveedor 	= CD_PROVEEDOR
			,@st_lotecab 	= ST_LOTECAB
	  FROM TCF009_SERVPRESCAB
	  WHERE CD_LOTESERV = @pi_cd_lote
	end
  else
	-- Servicios Facturados
	begin
	  set @cd_evento = 'UPDLOTFACANU'
	  
	  -- Actualiza cabecera
	  UPDATE TCF011_SERVFACCAB
	  SET ST_LOTECAB = 'ANU'
		 ,FH_MODIFICACION = GETDATE()
		 ,NB_USUARIOMODIF = @pi_id_usuario
	  WHERE CD_LOTEFACT = @pi_cd_lote
	  
	  -- Actualiza detalle
	  UPDATE TCF012_SERVFACDET
	  SET ST_LOTEDET = 'N'
		 ,FH_MODIFICACION = GETDATE()
		 ,NB_USUARIOMODIF = @pi_id_usuario
	  WHERE CD_LOTEFACT = @pi_cd_lote
	  
	  -- Datos para grabar log
	  SELECT @nb_archivo 	= NB_ARCHIVO
			,@cd_proveedor 	= CD_PROVEEDOR
			,@st_lotecab 	= ST_LOTECAB
	  FROM TCF011_SERVFACCAB
	  WHERE CD_LOTEFACT = @pi_cd_lote
	end

  set @po_c_error = @@error
  if(@po_c_error <> 0)
  begin
    set @po_d_error = 'Error al anular lote'
    return
  end

  --Inserta log
  
  -- No entra en la columna (long max 80)
  /*set @datos = 'Nro Lote: ' + convert(varchar(10),@pi_cd_lote) 
			 + ', Tipo Lote: ' + @pi_tp_lote
			 + ', Nb arch: ' + @nb_archivo
             + ', Prov: ' + @cd_proveedor
			 + ', Est Lote: ' + @st_lotecab
			 + ', F. mod.: ' + convert(varchar(10),GETDATE())
			 + ', Usu. mod.: ' + @pi_id_usuario*/
	
  set @datos = convert(varchar(10),@pi_cd_lote) 
			 + ', ' + @pi_tp_lote
			 + ', ' + @nb_archivo
             + ', ' + rtrim(@cd_proveedor)
			 + ', ' + @st_lotecab

  INSERT INTO TCF015_LOG (CD_USUARIO, FH_LOG, CD_EVENTO, NB_LOG)
  VALUES (
          @pi_id_usuario
         ,GETDATE()
         ,@cd_evento
         ,@datos
         ) 

  set @po_c_error = @@error
  if(@po_c_error <> 0)
  begin
    set @po_d_error = 'Error al insertar registro tabla de log'
    return
  end
  
end
go 


sp_procxmode 'sp_CFS_anularLote', anymode
go 

setuser
go 

