
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_get_consActLotes;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_get_consActLotes;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_get_consActLotes' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_get_consActLotes

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_get_consActLotes;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_get_consActLotes (
	@pi_tp_lote		         varchar(2),
    @pi_cd_proveedor         varchar(6),
    @pi_fa_desde             varchar(20),
    @pi_fa_hasta             varchar(20),
    @pi_st_lote        		 varchar(3),
    @pi_cd_nro_lote      	 numeric(10,0),
    @po_c_error              typ_c_error output,  
    @po_d_error              typ_d_error output  
) AS 
    declare 
	@fa_desde  smalldatetime,
    @fa_hasta  smalldatetime
    
	--Seteo de variables
    set @po_c_error = 0  
    set @po_d_error = null   
	
    if (@pi_fa_desde is not null)
       begin
			set @fa_desde = convert(date, @pi_fa_desde, 103)
			if (@@error  <> 0)
              begin  
                 set @po_d_error = 'Error convirtiendo fecha desde'
                 return         
              end
       end
    if (@pi_fa_hasta is not null)
       begin  
          set @fa_hasta = convert(date, @pi_fa_hasta, 103)
			if (@@error  <> 0)
              begin  
                 set @po_d_error = 'Error convirtiendo fecha hasta'
                 return         
              end
       end

    begin
		-- Servicios Prestados
		if (@pi_tp_lote = 'SP')
			begin
				SELECT CD_LOTESERV as CD_LOTE
					  ,TP_INTERFAZ
					  ,NB_ARCHIVO
					  ,CD_PROVEEDOR
					  ,ST_LOTECAB
					  ,NB_OBSERVACIONES
					  ,FH_ALTA
					  ,NB_USUARIOALTA
					  ,CASE WHEN ST_LOTECAB = 'ACT'
							AND NOT EXISTS(SELECT 1
									   FROM TCF010_SERVPRESDET T010
									   WHERE T010.CD_LOTESERV = T009.CD_LOTESERV
										  AND T010.CD_CONCILIACION is not null)
							THEN 1
							ELSE 0
						END as ANULAR
				FROM TCF009_SERVPRESCAB T009
				WHERE (@pi_cd_proveedor is null 			or CD_PROVEEDOR = @pi_cd_proveedor)
				  AND (@pi_st_lote is null 					or ST_LOTECAB = @pi_st_lote)
				  AND (@pi_cd_nro_lote = 0 					or CD_LOTESERV = @pi_cd_nro_lote)
				  AND (@pi_fa_desde is null 				or convert(date,FH_ALTA) >= @fa_desde)
				  AND (@pi_fa_hasta is null 				or convert(date,FH_ALTA) <= @fa_hasta)
			end
		else
			-- Servicios Facturados
			begin
				SELECT CD_LOTEFACT as CD_LOTE
					  ,TP_INTERFAZ
					  ,NB_ARCHIVO
					  ,CD_PROVEEDOR
					  ,ST_LOTECAB
					  ,NB_OBSERVACIONES
					  ,FH_ALTA
					  ,NB_USUARIOALTA
					  ,CASE WHEN ST_LOTECAB = 'ACT'
							 AND NOT EXISTS(SELECT 1
										FROM TCF012_SERVFACDET T012
										WHERE T012.CD_LOTEFACT = T011.CD_LOTEFACT
										  AND T012.CD_CONCILIACION is not null)
							THEN 1
							ELSE 0
						END as ANULAR
				FROM TCF011_SERVFACCAB T011
				WHERE (@pi_cd_proveedor is null 			or CD_PROVEEDOR = @pi_cd_proveedor)
				  AND (@pi_st_lote is null 					or ST_LOTECAB = @pi_st_lote)
				  AND (@pi_cd_nro_lote = 0 					or CD_LOTEFACT = @pi_cd_nro_lote)
				  AND (@pi_fa_desde is null 				or convert(date,FH_ALTA) >= @fa_desde)
				  AND (@pi_fa_hasta is null 				or convert(date,FH_ALTA) <= @fa_hasta)
			end
end
go 


sp_procxmode 'sp_CFS_get_consActLotes', unchained
go 

setuser
go 

