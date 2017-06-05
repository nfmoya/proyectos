
-----------------------------------------------------------------------------
-- DDL for UserDefinedDatatype 'INVHWR.typ_d_error'
-----------------------------------------------------------------------------

print '<<<<< CREATING UserDefinedDatatype - "INVHWR.typ_d_error" >>>>>'
go 

use INVHWR
go

IF EXISTS (SELECT 1 FROM INVHWR.dbo.systypes WHERE name = 'typ_d_error')
BEGIN
	setuser 'dbo'
	exec sp_droptype 'typ_d_error'

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING UserDefinedDatatype 'INVHWR.typ_d_error'"
	SELECT syb_quit()
END
go

SETUSER 'dbo'
go

exec  sp_addtype 'typ_d_error' , 'char(1000)' , nonull
go 

SETUSER
go


