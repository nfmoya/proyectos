
-----------------------------------------------------------------------------
-- DDL for View 'CFACSERV.dbo.VCF001_SERVPRESTGEN'
-----------------------------------------------------------------------------

print '<<<<< CREATING View - "CFACSERV.dbo.VCF001_SERVPRESTGEN" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'VCF001_SERVPRESTGEN' AND u.name = 'dbo' AND o.type = 'V')
BEGIN
  setuser 'dbo'
  drop view VCF001_SERVPRESTGEN

END
go 

IF (@@error != 0)
BEGIN
  PRINT "Error CREATING View 'CFACSERV.dbo.VCF001_SERVPRESTGEN'"
  SELECT syb_quit()
END
go

setuser 'dbo'
go 

create view dbo.VCF001_SERVPRESTGEN

as 

SELECT t012.CD_SECTORCONCIL as CD_SECTORCONCIL
       ,t007.NB_SECTOR as NB_SECTOR 
       ,P.CD_GRUPOPRODUCTO as CD_GRUPOPRODUCTO
       ,t001.NB_CODTABLA as NB_GRUPOPRODUCTO
       ,t012.CD_PRODUCTO as CD_PRODUCTO
       ,P.NB_PRODUCTO as NB_PRODUCTO
       ,t012.CD_PERIODOFACT  as CD_PERIODOFACT      
       ,t012.CT_SERVFACT as CT_SERVFACT
       ,t012.IM_PRECIOTOTAL as IM_PRECIOTOTAL
       ,t012.CD_REMITO as CD_REMITO
       ,t012.CD_CONCILIACION as CD_CONCILIACION
       ,t013.ST_CONCILIACION as ST_CONCILIACION
       ,t012.CD_LOTEFACT as CD_LOTEFACT
       ,t012.ST_LOTEDET as ST_LOTEDET
       ,t012.NU_COMPROBANTE as NU_COMPROBANTE
       ,t012.TP_COMPROBANTE as TP_COMPROBANTE
       ,t011.ST_LOTECAB as ST_LOTECAB
       ,t011.CD_PROVEEDOR as CD_PROVEEDOR
       ,t012.NB_OBSERVACIONES as NB_OBSERVACIONES
      ,(t012.IM_PRECIOTOTAL * (select t004.NU_VALNETOUNIVAL from TCF004_VALORUNIVAL t004 where t004.CD_UNIVAL     = P.CD_UNIVAL 
                                       and t004.CD_PROVEEDOR   = t011.CD_PROVEEDOR
                                      and t004.CD_PERIODOFACT = t012.CD_PERIODOFACT  ))  as CT_NETO  
      ,(t012.IM_PRECIOTOTAL * (select t004.NU_VALNETOUNIVAL from TCF004_VALORUNIVAL t004 where t004.CD_UNIVAL     = P.CD_UNIVAL 
                                        and t004.CD_PROVEEDOR   = t011.CD_PROVEEDOR
                                        and t004.CD_PERIODOFACT = t012.CD_PERIODOFACT  ) ) as CT_BRUTO
FROM TCF012_SERVFACDET t012 
left JOIN TCF011_SERVFACCAB t011 ON t011.CD_LOTEFACT = t012.CD_LOTEFACT 
left JOIN TCF005_PRODUCTO P ON P.CD_PRODUCTO = t012.CD_PRODUCTO and P.CD_PROVEEDOR = t011.CD_PROVEEDOR 
left JOIN TCF007_SECTOR t007 ON t007.CD_SECTOR = t012.CD_SECTORCONCIL 
left JOIN TCF001_GENERAL t001 ON t001.CD_CODTABLA = P.CD_GRUPOPRODUCTO and t001.CD_TABLA = 'GRUPRO'
       left join TCF004_VALORUNIVAL t004 on t004.CD_UNIVAL     = P.CD_UNIVAL 
                                        and t004.CD_PROVEEDOR   = t011.CD_PROVEEDOR
                                        and t004.CD_PERIODOFACT = t012.CD_PERIODOFACT
left join TCF013_CONCILIA t013 
on t012.CD_CONCILIACION = t013.CD_CONCILIACION
go 

setuser
go 
