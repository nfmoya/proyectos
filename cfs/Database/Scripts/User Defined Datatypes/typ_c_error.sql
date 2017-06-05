
-----------------------------------------------------------------------------
-- DDL for UserDefinedDatatype 'INVHWR.typ_c_error'
-----------------------------------------------------------------------------

print '<<<<< CREATING UserDefinedDatatype - "INVHWR.typ_c_error" >>>>>'
go 

use INVHWR
go

IF EXISTS (SELECT 1 FROM INVHWR.dbo.systypes WHERE name = 'typ_c_error')
BEGIN
	setuser 'dbo'
	exec sp_droptype 'typ_c_error'

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING UserDefinedDatatype 'INVHWR.typ_c_error'"
	SELECT syb_quit()
END
go

SETUSER 'dbo'
go

exec  sp_addtype 'typ_c_error' , 'int' , nonull
go 

SETUSER
go



-----------------------------------------------------------------------------
-- DDL for UserDefinedDatatype 'INVHWR.typ_c_error'
-----------------------------------------------------------------------------

print '<<<<< CREATING UserDefinedDatatype - "INVHWR.typ_c_error" >>>>>'
go 

use INVHWR
go

IF EXISTS (SELECT 1 FROM INVHWR.dbo.systypes WHERE name = 'typ_c_error')
BEGIN
	setuser 'dbo'
	exec sp_droptype 'typ_c_error'

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING UserDefinedDatatype 'INVHWR.typ_c_error'"
	SELECT syb_quit()
END
go

SETUSER 'dbo'
go

exec  sp_addtype 'typ_c_error' , 'int' , nonull
go 

SETUSER
go


