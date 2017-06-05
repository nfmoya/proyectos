
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_IASF_GrabarDetalle;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_IASF_GrabarDetalle;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_IASF_GrabarDetalle' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_IASF_GrabarDetalle

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_IASF_GrabarDetalle;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_IASF_GrabarDetalle(
@pi_cd_lotefact   	 int,
@pi_cd_orden	   	 int,
@pi_cd_periodofact	 varchar (6),
@pi_cd_sectorconcil	 varchar (15),
@pi_tp_comprobante   varchar (6),
@pi_nu_comprobante	 varchar (13),
@pi_cd_remito	  	 varchar (13),
@pi_cd_producto 	 varchar (12),
@pi_ct_servfact		 decimal(18,2),
@pi_cd_unival		 varchar (6),
@pi_im_preciounit	 decimal(9,4),
@pi_im_preciototal	 decimal(18,2),
@pi_observaciones	 varchar (50),
@pi_usuario			 varchar (8),
@pi_fechaComp	varchar(8),
@po_c_error          typ_c_error output,
@po_d_error          typ_d_error output )
as

begin  
	set @po_c_error = 0  
	set @po_d_error = null
	
	insert into TCF012_SERVFACDET
	  (
	   CD_LOTEFACT
	  ,CD_ORDEN
	  ,CD_PERIODOFACT
	  ,CD_SECTORCONCIL
	  ,TP_COMPROBANTE
	  ,NU_COMPROBANTE
	  ,CD_REMITO
	  ,CD_PRODUCTO
	  ,CT_SERVFACT
	  ,CD_UNIVAL
	  ,IM_PRECIOUNIT
	  ,IM_PRECIOTOTAL
	  ,NB_OBSERVACIONES
	  ,NB_USUARIOMODIF
      ,FH_NROCTE
	  )
	values
	  (
	   @pi_cd_lotefact
	  ,@pi_cd_orden
	  ,@pi_cd_periodofact
	  ,@pi_cd_sectorconcil
	  ,@pi_tp_comprobante
	  ,@pi_nu_comprobante
	  ,CASE WHEN @pi_cd_remito = '' THEN NULL ELSE @pi_cd_remito END
	  ,@pi_cd_producto
	  ,@pi_ct_servfact
	  ,@pi_cd_unival
	  ,@pi_im_preciounit
	  ,@pi_im_preciototal
	  ,@pi_observaciones
	  ,@pi_usuario
	  ,(select convert(date,@pi_fechaComp,105))
	) 
	set @po_c_error = @@error 
	if (@po_c_error  <> 0)  
	  begin    
		set @po_d_error = 'Error al insertar registro de detalle: '
		return
	  end   
end
go 

sp_procxmode 'sp_CFS_IASF_GrabarDetalle', anymode
go 

setuser
go 

