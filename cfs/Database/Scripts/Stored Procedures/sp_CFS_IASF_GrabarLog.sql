
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_IASF_GrabarLog;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_IASF_GrabarLog;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_IASF_GrabarLog' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_IASF_GrabarLog

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_IASF_GrabarLog;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_IASF_GrabarLog(

@pi_cd_usuario		 varchar (8),

@pi_cd_evento   	 varchar (12),

@pi_nb_log		     varchar (160),

@po_c_error          typ_c_error output,

@po_d_error          typ_d_error output )

as



begin  

	set @po_c_error = 0

	set @po_d_error = null

	

	insert into TCF015_LOG

	  (

	   CD_USUARIO

	  ,CD_EVENTO

	  ,NB_LOG

	  )

	values

	  (

	   @pi_cd_usuario

	  ,@pi_cd_evento

	  ,@pi_nb_log

	  ) 



	set @po_c_error = @@error

	if (@po_c_error  <> 0)

	  begin

		set @po_d_error = 'Error al insertar registro de log'

		return

	  end

end
go 


sp_procxmode 'sp_CFS_IASF_GrabarLog', anymode
go 

setuser
go 

