
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_IASF_GrabarCabecera;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_IASF_GrabarCabecera;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_IASF_GrabarCabecera' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_IASF_GrabarCabecera

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_IASF_GrabarCabecera;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_IASF_GrabarCabecera(

@pi_cd_lotefact   	 int,

@pi_tp_interfaz   	 varchar (3),

@pi_nb_archivo	     varchar (30),

@pi_cd_proveedor	 varchar (6),

@pi_nb_observaciones varchar (50),

@pi_nb_usuarioalta	 varchar (8),

@po_cd_lotefact      int output, -- Retorno el numero de cabecera

@po_c_error          typ_c_error output,

@po_d_error          typ_d_error output )

as



begin  

	set @po_c_error = 0  

	set @po_d_error = null





	--Obtengo el numero maximo de id obetenido

    SELECT @po_cd_lotefact = max(CD_LOTEFACT) + 1 FROM TCF011_SERVFACCAB

    if (@po_cd_lotefact = null )

        set @po_cd_lotefact = 1

	

	insert into TCF011_SERVFACCAB

	  (

	   CD_LOTEFACT

	  ,TP_INTERFAZ

	  ,NB_ARCHIVO

	  ,CD_PROVEEDOR

	  ,NB_OBSERVACIONES

	  ,NB_USUARIOALTA

	  )

	values

	  (

	   --@pi_cd_lotefact

	   @po_cd_lotefact

	  ,@pi_tp_interfaz

	  ,@pi_nb_archivo

	  ,@pi_cd_proveedor

	  ,@pi_nb_observaciones

	  ,@pi_nb_usuarioalta

	  ) 



	set @po_c_error = @@error 

	if (@po_c_error  <> 0)  

	  begin    

		set @po_d_error = 'Error al insertar registro de cabecera'

		return

	  end   

end
go 


sp_procxmode 'sp_CFS_IASF_GrabarCabecera', anymode
go 

setuser
go 

grant execute on  sp_CFS_IASF_GrabarCabecera to RolTrnCFACSERV 
go