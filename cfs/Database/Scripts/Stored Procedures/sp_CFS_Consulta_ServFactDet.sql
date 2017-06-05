
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


-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_Consulta_ServFactDet;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_Consulta_ServFactDet;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_Consulta_ServFactDet' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_Consulta_ServFactDet

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_Consulta_ServFactDet;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_Consulta_ServFactDet ( 

  @pi_CD_PROVEEDOR       varchar(6) = ''        ,

  @pi_CD_SECTOR          varchar(15) = ''        ,

  @pi_CD_PRODUCTO        varchar(12) = ''        ,

  @pi_CD_GRUPO           varchar(6) = ''        ,

  @pi_CD_HABILITADO      varchar(3) = ''        ,

  @pi_CD_PERIODO_DESDE   varchar(6) = ''        ,

  @pi_CD_PERIODO_HASTA   varchar(6) = ''        ,

  @pi_CD_TIPCOMP         varchar(8) = ''        ,

  @pi_NM_COMPROBANTE     varchar(13) = ''        ,

  @pi_NM_REMITO_DESDE    varchar(13) = ''        ,

  @pi_NM_REMITO_HASTA    varchar(13) = ''        ,

  @pi_NM_LOTE            int = 0        ,

  @po_c_error         typ_c_error output  ,

  @po_d_error         typ_d_error output  

 -- @po_d_query    varchar(3000)    output   

) AS 

  set @po_c_error = 0  

  set @po_d_error = null 

  DECLARE @query    varchar(16000)

  declare @f_desde  varchar(11),

          @f_hasta  varchar(11)





if( @pi_NM_LOTE <> 0

and @pi_NM_LOTE <> null)

BEGIN

    print 'fechas de lote'

    select @pi_CD_PROVEEDOR = CD_PROVEEDOR from TCF011_SERVFACCAB where  CD_LOTEFACT = @pi_NM_LOTE 

    select @f_desde = convert(varchar,min(t03.FH_DESDE),106) ,  

           @f_hasta = convert(varchar,MAX(t03.FH_HASTA),106) 

    from TCF003_PERIODOFACT t03

    inner join TCF012_SERVFACDET t12 on  t03.CD_PERIODOFACT = t12.CD_PERIODOFACT

    where  CD_LOTEFACT = @pi_NM_LOTE 

    and   CD_PROVEEDOR  = @pi_CD_PROVEEDOR

    print  @f_desde

    print  @f_hasta

end 

if( @pi_CD_PERIODO_DESDE <> ''

and @pi_CD_PERIODO_DESDE <> null

and @pi_CD_PERIODO_DESDE <> '0' )

begin

    print 'fechas sin lote'

  select  @f_desde =   convert(varchar,FH_DESDE,106)   from TCF003_PERIODOFACT

            where CD_PERIODOFACT = @pi_CD_PERIODO_DESDE

            and   CD_PROVEEDOR  = @pi_CD_PROVEEDOR

end

if( @pi_CD_PERIODO_HASTA <> ''

and @pi_CD_PERIODO_HASTA <> null

and @pi_CD_PERIODO_HASTA <> '0' )

begin

  select @f_hasta =  convert(varchar,FH_HASTA,106)   from TCF003_PERIODOFACT

            where CD_PERIODOFACT =  @pi_CD_PERIODO_HASTA

            and     CD_PROVEEDOR = @pi_CD_PROVEEDOR

END

 





set @query = ' SELECT vista.CD_SECTORCONCIL ,  ' +

'            vista.CD_GRUPOPRODUCTO , ' +

'            vista.CD_PRODUCTO, vista.NB_PRODUCTO, vista.CD_LOTEFACT,vista.CD_PERIODOFACT, vista.TP_COMPROBANTE,vista.NU_COMPROBANTE,vista.CD_REMITO ' +

'            ,CONVERT(varchar(10),t010.FH_REMITO,103) as FH_REMITO, CONVERT(varchar(10),t010.FH_FIN_SERV,103) as FH_FIN_SERV, vista.CT_SERVFACT, vista.IM_PRECIOTOTAL,vista.NB_OBSERVACIONES,vista.ST_LOTEDET,vista.CD_CONCILIACION ' +

' FROM  VCF001_SERVPRESTGEN vista' +

' left join TCF013_CONCILIA t013  ' +

' on vista.CD_CONCILIACION = t013.CD_CONCILIACION inner join TCF003_PERIODOFACT t003 ' +

' on vista.CD_PERIODOFACT = t003.CD_PERIODOFACT and vista.CD_PROVEEDOR = t003.CD_PROVEEDOR  ' +

'    left JOIN (SELECT distinct x.CD_SECTOR, x.CD_PRODUCTO, x.CD_REMITO, min(x.FH_REMITO) as FH_REMITO, max(x.FH_FIN_SERV) as FH_FIN_SERV, t009.CD_PROVEEDOR ' +

'    				FROM TCF009_SERVPRESCAB t009 ' +

'    				LEFT JOIN TCF010_SERVPRESDET x ON t009.CD_LOTESERV = x.CD_LOTESERV ' +

'    				group by x.CD_SECTOR, x.CD_PRODUCTO, x.CD_REMITO ) t010 ' +

'	  ON ( vista.CD_SECTORCONCIL = t010.CD_SECTOR and vista.CD_PRODUCTO = t010.CD_PRODUCTO ' +

'        and  vista.CD_REMITO = t010.CD_REMITO and vista.CD_PERIODOFACT = substring(dbo.periodoDesdeFecha(t010.FH_REMITO),7,11 ) AND vista.CD_PROVEEDOR = t010.CD_PROVEEDOR) ' +

' WHERE '



if( @pi_NM_LOTE <> null and @pi_NM_LOTE <> 0)

begin

	set @query = @query +   '    vista.CD_LOTEFACT = ' || CONVERT(VARCHAR(10),@pi_NM_LOTE)

	if(@pi_CD_PROVEEDOR <> '' and @pi_CD_PROVEEDOR <> null  and @pi_CD_PROVEEDOR <> '0'  )

	begin

		set @query = @query +    ' and ' 

	end

end



if(@pi_CD_PROVEEDOR <> '' and @pi_CD_PROVEEDOR <> null  and @pi_CD_PROVEEDOR <> '0'  )

begin

	set @query = @query +   '   vista.CD_PROVEEDOR = ''' || @pi_CD_PROVEEDOR ||  '''' 

end



if(@pi_CD_PERIODO_DESDE <> '' and @pi_CD_PERIODO_DESDE <> null  and @pi_CD_PERIODO_DESDE <> '0' )

begin

	 set @query = @query +   ' and t003.FH_DESDE >=  (convert(date,  ''' ||@f_desde ||  ''' ,106))'  

end



if(@pi_CD_PERIODO_HASTA <> '' and @pi_CD_PERIODO_HASTA <> null and @pi_CD_PERIODO_HASTA <> '0' )

 BEGIN

	 set @query = @query +   ' and t003.FH_HASTA <=  (convert(date,  ''' ||@f_hasta ||  ''' ,106))'  

 END



 if(@pi_CD_PRODUCTO <> '' and @pi_CD_PRODUCTO <> null and @pi_CD_PRODUCTO <> '0'  )

  begin

      set @query = @query +   ' and vista.CD_PRODUCTO = ''' ||  @pi_CD_PRODUCTO  ||  '''' 

  end

if(@pi_CD_SECTOR <> '' and @pi_CD_SECTOR <> null and @pi_CD_SECTOR <> '0')

begin

      set @query = @query +   ' and vista.CD_SECTORCONCIL = ''' ||  @pi_CD_SECTOR  ||  '''' 

end 



if(@pi_CD_GRUPO <> ''

and @pi_CD_GRUPO <> null 

and @pi_CD_GRUPO <> '0')

begin

    set @query = @query +   ' and vista.CD_GRUPOPRODUCTO = ''' ||  @pi_CD_GRUPO  ||  '''' 

end 

if(@pi_NM_REMITO_DESDE <> ''

and @pi_NM_REMITO_DESDE <> null )

begin

    set @query = @query +   ' and (vista.CD_REMITO >=  ''' ||  @pi_NM_REMITO_DESDE  ||  ''' or vista.CD_REMITO is null) ' 

end

if(@pi_NM_REMITO_HASTA <> ''

and @pi_NM_REMITO_HASTA <> null )

begin

    set @query = @query +   ' and (vista.CD_REMITO <=  ''' ||  @pi_NM_REMITO_HASTA  ||   ''' or vista.CD_REMITO is null) ' 

end  

if(@pi_NM_COMPROBANTE <> ''

and @pi_NM_COMPROBANTE <> null )

begin

    set @query = @query +   ' and vista.NU_COMPROBANTE =  ''' ||  @pi_NM_COMPROBANTE  ||  '''' 

end

if(@pi_CD_TIPCOMP <> ''

and @pi_CD_TIPCOMP <> null )

begin

    set @query = @query +   ' and vista.TP_COMPROBANTE =  ''' ||  @pi_CD_TIPCOMP  ||  '''' 

end

if(@pi_CD_HABILITADO =  'N' )

begin

    set @query = @query +   ' and vista.ST_LOTECAB <> ''ACT  '' ' 

end

else

begin

    set @query = @query +   ' and vista.ST_LOTECAB = ''ACT  '' ' 

end

    



set @query = @query + ' ORDER BY vista.CD_SECTORCONCIL, vista.CD_GRUPOPRODUCTO, vista.CD_PRODUCTO'









exec(@query)
go 


sp_procxmode 'sp_CFS_Consulta_ServFactDet', unchained
go 

setuser
go 

