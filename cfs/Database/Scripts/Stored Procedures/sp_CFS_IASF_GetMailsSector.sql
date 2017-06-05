
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_IASF_GetMailsSector;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_IASF_GetMailsSector;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_IASF_GetMailsSector' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_IASF_GetMailsSector

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_IASF_GetMailsSector;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

create procedure sp_CFS_IASF_GetMailsSector
@CD_SECTOR       	char(15)  = ''
as


begin

select NB_CORREO
from TCF016_USUARIO a 
--inner join TCF018_USUARIOS_PERFILES b on a.ID_USUARIO = b.ID_USUARIO
--inner join TCF019_PERFILES C on b.ID_PERFIL = C.ID_PERFIL

where CD_SECTOR = @CD_SECTOR
  and 
ID_PERFIL = 2
  and ST_HABILITADO = 'S'

end
go 


sp_procxmode 'sp_CFS_IASF_GetMailsSector', anymode
go 

setuser
go 

