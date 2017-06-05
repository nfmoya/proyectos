
-----------------------------------------------------------------------------
-- DDL for View 'CFACSERV.dbo.VCF001_SERVPRESTVIEW'
-----------------------------------------------------------------------------

print '<<<<< CREATING View - "CFACSERV.dbo.VCF001_SERVPRESTVIEW" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'VCF001_SERVPRESTVIEW' AND u.name = 'dbo' AND o.type = 'V')
BEGIN
	setuser 'dbo'
	drop view VCF001_SERVPRESTVIEW

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING View 'CFACSERV.dbo.VCF001_SERVPRESTVIEW'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

create view dbo.VCF001_SERVPRESTVIEW

as 

SELECT t010.CD_SECTOR as CD_SECTOR 
   , t007.NB_SECTOR as NB_SECTOR
   , P.CD_GRUPOPRODUCTO as CD_GRUPOPRODUCTO 
   , t001.NB_CODTABLA as NB_GRUPOPRODUCTO
   , t010.CD_PRODUCTO as CD_PRODUCTO 
   , P.NB_PRODUCTO as NB_PRODUCTO      
   , t010.FH_REMITO as  FH_REMITO
   , t010.FH_FIN_SERV as  FH_FIN_SERV
   , dbo.getPeriodoDeFecha(t010.FH_REMITO , t009.CD_PROVEEDOR) as CD_PERIODOFACT
   , t010.CT_SERVPREST as CT_SERVPREST
   , t010.IM_PRECIOTOTAL as IM_PRECIOTOTAL
   , t010.CD_REMITO as CD_REMITO
     ,t010.CD_CONCILIACION as CD_CONCILIACION
     ,t013.ST_CONCILIACION as ST_CONCILIACION
     , t010.CD_LOTESERV  as CD_LOTESERV
     , t010.ST_LOTEDET as ST_LOTEDET
     ,t009.ST_LOTECAB as ST_LOTECAB
     , P.CD_PROVEEDOR as CD_PROVEEDOR
     , t010.NB_OBSERVACIONES as NB_OBSERVACIONES
     , t001.NB_CODTABLA as NB_CODTABLA
     , (t010.IM_PRECIOTOTAL * (select t004.NU_VALNETOUNIVAL from TCF004_VALORUNIVAL t004 where t004.CD_UNIVAL     = P.CD_UNIVAL 
                                      and t004.CD_PROVEEDOR   = t009.CD_PROVEEDOR
                                      and t004.CD_PERIODOFACT = dbo.getPeriodoDeFecha(t010.FH_REMITO , t009.CD_PROVEEDOR)  ) )  as CT_NETO  
     , (t010.IM_PRECIOTOTAL * (select t004.NU_VALBRUTOUNIVAL from TCF004_VALORUNIVAL t004 where t004.CD_UNIVAL     = P.CD_UNIVAL 
                                        and t004.CD_PROVEEDOR   = t009.CD_PROVEEDOR
                                        and t004.CD_PERIODOFACT = dbo.getPeriodoDeFecha(t010.FH_REMITO , t009.CD_PROVEEDOR)  ) ) as CT_BRUTO
    , t010.NB_ATRIBUTOREF1
    , t010.NB_ATRIBUTOREF2
    , P.CD_TIPVAL + ' - ' + tipval.NB_CODTABLA as NB_TIPVAL
 from TCF010_SERVPRESDET t010 
  INNER JOIN TCF009_SERVPRESCAB t009 ON (t009.CD_LOTESERV = t010.CD_LOTESERV ) 
  INNER JOIN TCF005_PRODUCTO P ON (P.CD_PRODUCTO = t010.CD_PRODUCTO and P.CD_PROVEEDOR = t009.CD_PROVEEDOR ) 
  INNER JOIN TCF007_SECTOR t007 ON (t007.CD_SECTOR = t010.CD_SECTOR) 
  INNER JOIN TCF001_GENERAL t001 ON t001.CD_CODTABLA = P.CD_GRUPOPRODUCTO and t001.CD_TABLA = 'GRUPRO'
  LEFT JOIN TCF001_GENERAL tipval ON tipval.CD_CODTABLA = P.CD_TIPVAL and tipval.CD_TABLA = 'TIPVAL'
  left JOIN TCF013_CONCILIA t013 on t010.CD_CONCILIACION = t013.CD_CONCILIACION 
  left join TCF004_VALORUNIVAL t004 on (P.CD_UNIVAL = t004.CD_UNIVAL and t009.CD_PROVEEDOR =t004.CD_PROVEEDOR  
  and dbo.getPeriodoDeFecha(t010.FH_REMITO, t009.CD_PROVEEDOR) = ltrim(rtrim(t004.CD_PERIODOFACT)) )
go 

setuser
go 

