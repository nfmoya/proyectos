
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_busqueda_nomedibles;1'
-----------------------------------------------------------------------------

print ' CREATING Stored Procedure - CFACSERV.dbo.sp_CFS_busqueda_nomedibles;1 '
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_busqueda_nomedibles' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_busqueda_nomedibles

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_busqueda_nomedibles;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_busqueda_nomedibles(
@cd_proveedor     varchar(6),
@cd_sector        varchar(6),
@cd_periodo       varchar(6),
@st_conciliacion  varchar(3),
@cd_conciliacion  int,
@po_c_error       typ_c_error output,
@po_d_error       typ_d_error output )
as

begin  
	declare @periodo char(6), 
            @CD_PERIODO_ACT char(6)

	set @po_c_error = 0  
	set @po_d_error = null

    if @cd_conciliacion = 0
		begin
			set @cd_conciliacion = null

			create table #APAREO_CONCILIACION (
			CD_PRODUCTO            char(12)        null  ,
			CD_PERIODO_ANT         char(6)         null  ,
			CT_SERVFACT_ANT        decimal(14,4)   null  ,
			CD_UNIVAL_ANT          char(6)         null  ,
			IM_PRECIOTOTAL_ANT     decimal(18,2)   null  ,
			CD_CONCILIACION_ANT    int             null  ,
			CD_PERIODO_ACT         char(6)         null  ,
			CT_SERVFACT_ACT        decimal(14,4)   null  ,
			CD_UNIVAL_ACT          char(6)         null  ,
			IM_PRECIOTOTAL_ACT     decimal(18,2)   null  ,
			NU_PORCVARMAX          decimal(9,2)    null  ,
			NU_PORCVARVAL          decimal(9,2)    null  ,
			CD_CONCILIACION_ACT    int             null  ,
			IM_DIFERENCIA          decimal(18,2)   null  ,
			NB_OBSERVACIONES       char(180)       null  ,
            TP_SOLUCION            char(6)         null)

			declare cur_conc cursor for
				select sfd.CD_PRODUCTO, sum(sfd.CT_SERVFACT) as CT_SERVFACT_ACT, sfd.CD_UNIVAL AS CD_UNIVAL_ACT, sum(sfd.IM_PRECIOTOTAL) as IM_PRECIOTOTAL_ACT,
				pro.NU_PORCVARMAX, 0 as NU_PORCVARVAL
				from TCF012_SERVFACDET sfd
				inner join TCF011_SERVFACCAB sfc ON sfc.CD_LOTEFACT = sfd.CD_LOTEFACT
				inner join TCF005_PRODUCTO pro ON pro.CD_PRODUCTO = sfd.CD_PRODUCTO
				where pro.CD_GRUPOPRODUCTO = 'NO_CON'
				  and sfc.CD_PROVEEDOR     = @cd_proveedor
				  and sfd.CD_PERIODOFACT   = @cd_periodo
				  and sfd.CD_SECTORCONCIL  = @cd_sector
                  and sfd.CD_CONC_NO_MEDIBLE is null
				group by sfd.CD_PERIODOFACT, sfd.CD_SECTORCONCIL, sfd.CD_PRODUCTO, sfd.CD_UNIVAL, pro.NU_PORCVARMAX

			declare 
				@CD_PRODUCTO          char(12)      , 
				@CT_SERVFACT_ACT      decimal(14,4) ,
				@CD_UNIVAL_ACT        char(6)       ,
				@IM_PRECIOTOTAL_ACT   decimal(18,2) ,
				@NU_PORCVARMAX        decimal(9,2)  ,
				@NU_PORCVARVAL        decimal(9,2)
			open cur_conc
			fetch cur_conc into @CD_PRODUCTO, @CT_SERVFACT_ACT, @CD_UNIVAL_ACT, @IM_PRECIOTOTAL_ACT, @NU_PORCVARMAX, @NU_PORCVARVAL

			while (@@sqlstatus != 2)
			begin
				declare @CD_PERIODO_ANT      char(6)      ,
						@CT_SERVFACT_ANT     decimal(14,4),
						@CD_UNIVAL_ANT       char(6)      ,
						@IM_PRECIOTOTAL_ANT  decimal(18,2),
						@CD_CONCILIACION_ANT int

				set     @CD_PERIODO_ANT      = '',
						@CT_SERVFACT_ANT     = 0,
						@CD_UNIVAL_ANT       = '',
						@IM_PRECIOTOTAL_ANT  = 0,
						@CD_CONCILIACION_ANT = 0               

				set @periodo = (SELECT MAX(pf2.CD_PERIODOFACT) FROM TCF003_PERIODOFACT pf2 WHERE pf2.CD_PERIODOFACT = (  SELECT MAX(pf.CD_PERIODOFACT) FROM TCF003_PERIODOFACT pf 
                                                                            										INNER JOIN TCF011_SERVFACCAB fc ON fc.CD_PROVEEDOR = pf.CD_PROVEEDOR
                                                                            										INNER JOIN TCF012_SERVFACDET fd ON fc.CD_LOTEFACT = fd.CD_LOTEFACT 
                                                                            										WHERE fc.CD_PROVEEDOR = @cd_proveedor
                                                                                                                      AND pf.FH_HASTA < (select pf2.FH_HASTA FROM TCF003_PERIODOFACT pf2 WHERE pf2.CD_PERIODOFACT=@cd_periodo) 
                                                                                                                      AND fd.CD_SECTORCONCIL = @cd_sector
                                                                                                                      AND fd.CD_PRODUCTO = @CD_PRODUCTO))


				if @periodo != null
					begin
--        				select @CD_PERIODO_ANT=sfd.CD_PERIODOFACT, CT_SERVFACT_ANT=sum(sfd.CT_SERVFACT), @CD_UNIVAL_ANT=sfd.CD_UNIVAL, @IM_PRECIOTOTAL_ANT=sum(sfd.IM_PRECIOTOTAL),
--        				@CD_CONCILIACION_ANT=CD_CONC_NO_MEDIBLE
        				select @CD_PERIODO_ANT=sfd.CD_PERIODOFACT, @CT_SERVFACT_ANT=sum(sfd.CT_SERVFACT), @CD_UNIVAL_ANT=sfd.CD_UNIVAL, @IM_PRECIOTOTAL_ANT=sum(sfd.IM_PRECIOTOTAL),
        				@CD_CONCILIACION_ANT=CD_CONC_NO_MEDIBLE
        				from TCF012_SERVFACDET sfd
        				inner join TCF011_SERVFACCAB sfc ON sfc.CD_LOTEFACT = sfd.CD_LOTEFACT
        				inner join TCF005_PRODUCTO pro ON pro.CD_PRODUCTO = sfd.CD_PRODUCTO
        				where pro.CD_GRUPOPRODUCTO = 'NO_CON'
        				  and sfc.CD_PROVEEDOR     = @cd_proveedor
        				  and sfd.CD_PRODUCTO      = @CD_PRODUCTO
        				  and sfd.CD_PERIODOFACT   = @periodo
        				  and sfd.CD_SECTORCONCIL  = @cd_sector
        				group by sfd.CD_PERIODOFACT, sfd.CD_UNIVAL
					end
				
				INSERT INTO #APAREO_CONCILIACION
				select @CD_PRODUCTO, @CD_PERIODO_ANT, @CT_SERVFACT_ANT, @CD_UNIVAL_ANT, @IM_PRECIOTOTAL_ANT, @CD_CONCILIACION_ANT, @cd_periodo,
					   @CT_SERVFACT_ACT, @CD_UNIVAL_ACT, @IM_PRECIOTOTAL_ACT, @NU_PORCVARMAX, @NU_PORCVARVAL, 0 as CD_CONCILIACION_ACT, 0 as IM_DIFERENCIA, '' as NB_OBSERVACIONES, '' as TP_SOLUCION 


				fetch cur_conc into @CD_PRODUCTO, @CT_SERVFACT_ACT, @CD_UNIVAL_ACT, @IM_PRECIOTOTAL_ACT, @NU_PORCVARMAX, @NU_PORCVARVAL
			end
			close cur_conc

            UPDATE #APAREO_CONCILIACION SET IM_DIFERENCIA=IM_PRECIOTOTAL_ACT-IM_PRECIOTOTAL_ANT, 
                    NU_PORCVARVAL=(CONVERT(decimal(10,2),CASE WHEN IM_PRECIOTOTAL_ANT=0 THEN 100 WHEN IM_PRECIOTOTAL_ACT=0 THEN 0 ELSE (((IM_PRECIOTOTAL_ACT/IM_PRECIOTOTAL_ANT)-1)*100) END))
        
            UPDATE #APAREO_CONCILIACION SET NU_PORCVARVAL=NU_PORCVARVAL*-1
            WHERE NU_PORCVARVAL<0

            DELETE TCF030_NOMEDIBLES_PROD WHERE CD_CONCILIACION IS NULL AND CD_PERIODO_ACT = @cd_periodo

			INSERT INTO TCF030_NOMEDIBLES_PROD(	CD_PRODUCTO, CD_PERIODO_ANT, CT_SERVFACT_ANT, CD_UNIVAL_ANT, IM_PRECIOTOTAL_ANT, CD_CONCILIACION_ANT, 
					CD_PERIODO_ACT, CT_SERVFACT_ACT, CD_UNIVAL_ACT, IM_PRECIOTOTAL_ACT, NU_PORCVARMAX, NU_PORCVARVAL, CD_CONCILIACION_ACT, 
					IM_DIFERENCIA, NB_OBSERVACIONES, TP_SOLUCION)
			SELECT 	CD_PRODUCTO,CD_PERIODO_ANT,CT_SERVFACT_ANT,CD_UNIVAL_ANT,IM_PRECIOTOTAL_ANT,CD_CONCILIACION_ANT,
					CD_PERIODO_ACT,CT_SERVFACT_ACT,CD_UNIVAL_ACT,IM_PRECIOTOTAL_ACT,NU_PORCVARMAX,NU_PORCVARVAL,CD_CONCILIACION_ACT,
					IM_DIFERENCIA,NB_OBSERVACIONES, TP_SOLUCION
			FROM #APAREO_CONCILIACION

			SELECT 	CD_PRODUCTO,CD_PERIODO_ANT,CT_SERVFACT_ANT,CD_UNIVAL_ANT,IM_PRECIOTOTAL_ANT,CD_CONCILIACION_ANT,
					CD_PERIODO_ACT,CT_SERVFACT_ACT,CD_UNIVAL_ACT,IM_PRECIOTOTAL_ACT,NU_PORCVARMAX,NU_PORCVARVAL,CD_CONCILIACION_ACT,
					IM_DIFERENCIA,NB_OBSERVACIONES, TP_SOLUCION
			FROM #APAREO_CONCILIACION

		end
	else
		begin
			SELECT 	CD_PRODUCTO,CD_PERIODO_ANT,CT_SERVFACT_ANT,CD_UNIVAL_ANT,IM_PRECIOTOTAL_ANT,CD_CONCILIACION_ANT,
					CD_PERIODO_ACT,CT_SERVFACT_ACT,CD_UNIVAL_ACT,IM_PRECIOTOTAL_ACT,NU_PORCVARMAX,NU_PORCVARVAL,CD_CONCILIACION_ACT,
					IM_DIFERENCIA,NB_OBSERVACIONES, TP_SOLUCION
			FROM TCF030_NOMEDIBLES_PROD
			WHERE 	CD_CONCILIACION = @cd_conciliacion		
		end
end
go 


sp_procxmode 'sp_CFS_busqueda_nomedibles', anymode
go 

setuser
go 

Grant Execute on dbo.sp_CFS_busqueda_nomedibles to RolTrnCFACSERV 
go