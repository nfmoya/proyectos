
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_convierte_char_en_fecha;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_convierte_char_en_fecha;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_convierte_char_en_fecha' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_convierte_char_en_fecha

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_convierte_char_en_fecha;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_convierte_char_en_fecha (
@pi_fecha_char      varchar(19),
@po_fecha_datetime  datetime output,
@po_c_error    typ_c_error output,
@po_d_error    typ_d_error output
)
as
begin 

 set @po_c_error = 0
 set @po_d_error = null 

 --formato in 'dd/mm/yyyy hh:mm:ss' 
 set @po_fecha_datetime = convert(datetime, @pi_fecha_char, 111) 

 set @po_c_error = @@error   
  
 if (@po_c_error  <> 0)
 begin
    set @po_d_error = 'Error al convertir char en date: ' + @pi_fecha_char + '. '
    set @po_c_error = 3                 
    return             
 end

end
go 


sp_procxmode 'sp_convierte_char_en_fecha', anymode
go 

setuser
go 

