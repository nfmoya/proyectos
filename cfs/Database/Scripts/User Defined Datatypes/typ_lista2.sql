
-----------------------------------------------------------------------------
-- DDL for UserDefinedDatatype 'INVHWR.typ_lista2'
-----------------------------------------------------------------------------

print '<<<<< CREATING UserDefinedDatatype - "INVHWR.typ_lista2" >>>>>'
go 

use INVHWR
go

IF EXISTS (SELECT 1 FROM INVHWR.dbo.systypes WHERE name = 'typ_lista2')
BEGIN
	setuser 'dbo'
	exec sp_droptype 'typ_lista2'

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING UserDefinedDatatype 'INVHWR.typ_lista2'"
	SELECT syb_quit()
END
go

SETUSER 'dbo'
go

exec  sp_addtype 'typ_lista2' , 'char(1000)' , nonull
go 

SETUSER
go


