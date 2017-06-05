
-----------------------------------------------------------------------------
-- DDL for UserDefinedDatatype 'INVHWR.typ_lista'
-----------------------------------------------------------------------------

print '<<<<< CREATING UserDefinedDatatype - "INVHWR.typ_lista" >>>>>'
go 

use INVHWR
go

IF EXISTS (SELECT 1 FROM INVHWR.dbo.systypes WHERE name = 'typ_lista')
BEGIN
	setuser 'dbo'
	exec sp_droptype 'typ_lista'

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING UserDefinedDatatype 'INVHWR.typ_lista'"
	SELECT syb_quit()
END
go

SETUSER 'dbo'
go

exec  sp_addtype 'typ_lista' , 'varchar(1500)' , nonull
go 

SETUSER
go


