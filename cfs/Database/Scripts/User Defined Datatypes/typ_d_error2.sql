
-----------------------------------------------------------------------------
-- DDL for UserDefinedDatatype 'INVHWR.typ_d_error2'
-----------------------------------------------------------------------------

print '<<<<< CREATING UserDefinedDatatype - "INVHWR.typ_d_error2" >>>>>'
go 

use INVHWR
go

IF EXISTS (SELECT 1 FROM INVHWR.dbo.systypes WHERE name = 'typ_d_error2')
BEGIN
	setuser 'dbo'
	exec sp_droptype 'typ_d_error2'

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING UserDefinedDatatype 'INVHWR.typ_d_error2'"
	SELECT syb_quit()
END
go

SETUSER 'dbo'
go

exec  sp_addtype 'typ_d_error2' , 'char(250)' , nonull
go 

SETUSER
go


