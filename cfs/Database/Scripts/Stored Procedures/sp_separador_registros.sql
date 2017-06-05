
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'INVHWR.dbo.sp_separador_registros;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "INVHWR.dbo.sp_separador_registros;1" >>>>>'
go 

use INVHWR
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_separador_registros' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_separador_registros

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'INVHWR.dbo.sp_separador_registros;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE sp_separador_registros ( 
-- drop procedure sp_separador_registros 
@po_separador_registro    varchar(1) output, 
@po_separador_campo       varchar(1) output, 
@po_c_error               typ_c_error output, 
@po_d_error               typ_d_error output 
) 
as 
-- Objetivo: Deolver los separadores de registro y campo 
-- Parametros de entrada: No posee 
-- 
--  Ejemplo: 
--     campo1:campo2#campo1:campo2#campo1:campo2 
-- 
--     : separador de campo 
--     # separador de Registro 
 
begin 
 
 
  set @po_separador_registro = '|' 
  set @po_separador_campo    = ':' 
 
  set @po_c_error = @@error    
  if (@po_c_error  <> 0) 
  begin 
    set @po_d_error = convert(varchar,@po_c_error) +  
                      ' - Error al establecer los separadores' 
	return 
  end 
 
end
go 


sp_procxmode 'sp_separador_registros', unchained
go 

setuser
go 

