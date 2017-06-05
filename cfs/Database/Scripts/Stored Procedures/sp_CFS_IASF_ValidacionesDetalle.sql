
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_IASF_ValidacionesDetalle;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_IASF_ValidacionesDetalle;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_IASF_ValidacionesDetalle' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_IASF_ValidacionesDetalle

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_IASF_ValidacionesDetalle;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

create procedure sp_CFS_IASF_ValidacionesDetalle
@CD_PRODUCTO        char(12)  = ''       ,
@CD_PROVEEDOR       char(6)  = ''       ,
@CD_SECTOR          char(15) = ''       ,
@CD_PERIODOFACT		char(6)  = ''		,
@CD_CODTABLA		char(6)  = ''		,
@CD_REMITO			char(13)  = ''		,
@CD_UNIVAL			char(6)  = ''		,
@po_c_error         typ_c_error output  ,
@po_d_error         varchar(1000) output,
@po_sector          varchar(15) output  ,
@po_remitoRepetido  char(1) output
as
declare @importar char(1)
       ,@habilitado char(1)
	   ,@estado char(3)
	   ,@sectorDefault char(15)
	   ,@remitoObligatorio char(1)
	   ,@uniVal char(6)
set @po_c_error = 0  
set @po_d_error = null
set @po_sector = null
set @importar = null
set @habilitado = null
set @estado = null
set @sectorDefault = null
set @remitoObligatorio = null
set @po_remitoRepetido = null
set @uniVal = null
begin
    select @importar     		= ST_PRODIMPORTFACT
	      ,@habilitado   		= ST_HABILITADO
		  ,@sectorDefault 		= CD_SECCONFACT
		  ,@remitoObligatorio 	= ST_REMFACTOBLIG
		  ,@po_remitoRepetido	= ST_ADMITEREMFACT
		  ,@uniVal				= CD_UNIVAL
	from TCF005_PRODUCTO
	where CD_PRODUCTO = @CD_PRODUCTO
	  and CD_PROVEEDOR = @CD_PROVEEDOR
	-- Si hay que importar o no existe hacer validaciones
	if (@importar is null or @importar = 'S')
		begin
			-- Validacion id producto existente
			if (@importar is null)
				begin
					set @po_c_error   = 1
					set @po_d_error   = ' | Id producto inexistente en tabla TCF005_PRODUCTO'
				end
			-- Validacion id periodo de facturacion
			set @estado = (select ST_ESTADO
						   from TCF003_PERIODOFACT
						   where CD_PROVEEDOR = @CD_PROVEEDOR
						     and CD_PERIODOFACT = @CD_PERIODOFACT)
			if (@estado is null)
				begin
					set @po_c_error   = 1
					set @po_d_error   = @po_d_error + ' | Periodo de facturacion inexistente'
				end
			else if (@estado != 'ABI')
				begin
					set @po_c_error   = 1
					set @po_d_error   = @po_d_error + ' | Estado distinto de ABI'
				end
			-- Validacion id producto habilitado
			if (@habilitado = 'N')
				begin
					set @po_c_error   = 1
					set @po_d_error   = @po_d_error + ' | Id producto inhabilitado'
				end
			-- Validacion sector
			-- Si no se informo id sector se obtiene de la tabla TCF005_PRODUCTO
			if (@CD_SECTOR = '')
				begin
					if (@importar = 'S' and (@sectorDefault is null or @sectorDefault = '0'))
						begin
							set @po_c_error   = 1
							set @po_d_error   = @po_d_error + ' | Sector no informado en archivo y producto no tiene sector por defecto'
						end
					else
						begin
							set @habilitado = null
							select @habilitado = T008.ST_HABILITADO
							from TCF008_PRODSECTOR T008
							inner join TCF007_SECTOR T007
							on T008.CD_SECTOR = T007.CD_SECTOR
							where T008.CD_PRODUCTO  = @CD_PRODUCTO
							  and T008.CD_PROVEEDOR = @CD_PROVEEDOR
							  and T008.CD_SECTOR    = @sectorDefault
							  and T008.ST_HABILITADO = 'S'
							  and T007.ST_HABILITADO = 'S'
							if (@habilitado is null or @habilitado = 'N')
								begin
									set @po_c_error   = 9
									set @po_d_error   = @po_d_error + ' | Sector inexistente o inhabilitado '
								end
							else
								begin
									set @po_sector   = @sectorDefault
								end
						end
				end
			else
				begin
					set @habilitado = null
					select @habilitado = T008.ST_HABILITADO
					from TCF008_PRODSECTOR T008
					inner join TCF007_SECTOR T007
					on T008.CD_SECTOR = T007.CD_SECTOR
					where T008.CD_PRODUCTO  = @CD_PRODUCTO
					  and T008.CD_PROVEEDOR = @CD_PROVEEDOR
					  and T008.CD_SECTOR    = @CD_SECTOR
					  and T008.ST_HABILITADO = 'S'
					  and T007.ST_HABILITADO = 'S'
					if (@habilitado is null or @habilitado = 'N')
						begin
							set @po_c_error   = 1
							set @po_d_error   = @po_d_error + ' | Sector informado no es valido para el producto'
						end
				end
			-- Validacion tipo comprobante
			set @habilitado = null
			select @habilitado = ST_HABILITADO
					from TCF001_GENERAL 
					where CD_TABLA =  'TIPCOM'
					  and CD_CODTABLA = @CD_CODTABLA
			if (@habilitado is null or @habilitado = 'N')
				begin
					set @po_c_error   = 1
					set @po_d_error   = @po_d_error + ' | Tipo comprobante inexistente o inhabilitado'
				end
			-- Validacion remito
			if (@po_remitoRepetido = 'N')
				begin
					if exists(select 1
							  from TCF012_SERVFACDET T012
							  inner join TCF011_SERVFACCAB T011
							  on T012.CD_LOTEFACT = T011.CD_LOTEFACT
							  where T011.ST_LOTECAB =  'ACT'
							    and CD_REMITO = @CD_REMITO)
						begin
							set @po_c_error   = 1
							set @po_d_error   = @po_d_error + ' | Remito duplicado'
						end
			/*		if (@remitoObligatorio = 'S' and @CD_REMITO = '')
						begin
							set @po_c_error   = 1
							set @po_d_error   = @po_d_error + ' | Remito obligatorio'
						end*/
				end
			if (@remitoObligatorio = 'S' and @CD_REMITO = '')
						begin
							set @po_c_error   = 1
							set @po_d_error   = @po_d_error + ' | Remito obligatorio'
			end	
			-- Validacion unidad de valoracion
			if not exists(select  CD_UNIVAL
					from TCF004_VALORUNIVAL where CD_UNIVAL = @CD_UNIVAL and CD_PROVEEDOR = @CD_PROVEEDOR)
			begin
					set @po_c_error   = 1
					set @po_d_error   = @po_d_error + ' | Unidad de valoracion incorrecta'
			end
			else
					begin
						if not exists(select  CD_UNIVAL
								from TCF004_VALORUNIVAL where CD_UNIVAL = @CD_UNIVAL and CD_PROVEEDOR = @CD_PROVEEDOR and ST_HABILITADO = 'S')
						begin
								set @po_c_error   = 1
								set @po_d_error   = @po_d_error + ' | Unidad de valoración inhabilitada'
						end
						else
									begin
											if not exists(select  CD_UNIVAL	from TCF004_VALORUNIVAL where CD_UNIVAL = @CD_UNIVAL and CD_PROVEEDOR = @CD_PROVEEDOR and ST_HABILITADO = 'S' and CD_PERIODOFACT = @CD_PERIODOFACT)
												begin
														set @po_c_error   = 1
														set @po_d_error   = @po_d_error + ' | Unidad de valoración no corresponde al periodo informado en archivo'
												end
									end
					end
		end
	else
		-- No importar
		begin
			set @po_c_error   = 0
			set @po_d_error   = 'N'
		end
	--	print @po_d_error 
end
go 


sp_procxmode 'sp_CFS_IASF_ValidacionesDetalle', anymode
go 

setuser
go 

