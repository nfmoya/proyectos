
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_IASP_GrabarDetalle;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_IASP_GrabarDetalle;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_IASP_GrabarDetalle' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_IASP_GrabarDetalle

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_IASP_GrabarDetalle;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_IASP_GrabarDetalle (
    @pi_cd_loteserv         int,
    @pi_cd_orden            int,
    @pi_cd_sectorSolic      varchar (15),
    @pi_cd_sectorCtrl       varchar (15), 
    @pi_cd_remito           varchar (13),
    @pi_cd_producto         varchar (12),
    @pi_ct_servPrest        decimal(14,2),
    @pi_fh_fechaRemito      date,
    @pi_fh_fechaFinServ     date,
    @pi_st_estado           char(1),
    @pi_cd_piezaDesde       varchar (25),
    @pi_cd_piezaHasta       varchar (25),
    @pi_cd_remitoPadre      varchar (13),
    @pi_cd_referencia1      varchar (30),
    @pi_cd_referencia2      varchar (10),
    @pi_nb_observaciones    varchar (50),
    @pi_nb_usuario          varchar (8),
    @pi_cd_proveedor        varchar (6),
    @po_c_error             typ_c_error output,
    @po_d_error             typ_d_error output )
as

begin  
    declare @cd_grupoproducto varchar(12), @im_precio_total decimal(18,2)
    set @cd_grupoproducto = ''
    set @im_precio_total = 0
    set @po_c_error = 0  
    set @po_d_error = null

    set @cd_grupoproducto = (select CD_GRUPOPRODUCTO from TCF005_PRODUCTO WHERE CD_PROVEEDOR = @pi_cd_proveedor AND CD_PRODUCTO = @pi_cd_producto)

    if (@cd_grupoproducto = 'CON_MONT_TOT')
    begin
        set @im_precio_total = convert(decimal(18,2), @pi_cd_referencia1)
    end
    
    insert into TCF010_SERVPRESDET (
       CD_LOTESERV
      ,CD_ORDEN
      ,CD_SECTORSOL
      ,CD_SECTOR
      ,CD_REMITO
      ,CD_PRODUCTO
      ,CT_SERVPREST
      ,FH_REMITO
      ,FH_FIN_SERV
      ,ST_LOTEDET
      ,NB_PIEZADESDE
      ,NB_PIEZAHASTA
      ,CD_REMITOPADRE
      ,NB_ATRIBUTOREF1
      ,NB_ATRIBUTOREF2
      ,NB_OBSERVACIONES
      ,NB_USUARIOMODIF
      )
    values (
       @pi_cd_loteserv
      ,@pi_cd_orden
      ,@pi_cd_sectorSolic
      ,@pi_cd_sectorCtrl
      ,@pi_cd_remito
      ,@pi_cd_producto
      ,@pi_ct_servPrest
      ,@pi_fh_fechaRemito
      ,@pi_fh_fechaFinServ
      ,@pi_st_estado
      ,@pi_cd_piezaDesde
      ,@pi_cd_piezaHasta
      ,@pi_cd_remitoPadre
      ,@pi_cd_referencia1
      ,@pi_cd_referencia2
      ,@pi_nb_observaciones
      ,@pi_nb_usuario
      ) 

    set @po_c_error = @@error 
    if (@po_c_error  <> 0)  
      begin    
        set @po_d_error = 'Error al insertar Registro de Detalle: '
        return
      end
end
go 


sp_procxmode 'sp_CFS_IASP_GrabarDetalle', anymode
go 

setuser
go 

