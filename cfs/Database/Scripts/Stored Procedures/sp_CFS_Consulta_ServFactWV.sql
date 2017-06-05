
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_Consulta_ServFactWV;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_Consulta_ServFactWV;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_Consulta_ServFactWV' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_Consulta_ServFactWV

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_Consulta_ServFactWV;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_Consulta_ServFactWV ( 

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

  @pi_REDUCIDA           int = 0        ,

  @po_c_error         typ_c_error output  ,

  @po_d_error         typ_d_error output   

) AS 

  set @po_c_error = 0  

  set @po_d_error = null 

  DECLARE @query    varchar(16000)

  declare @cdperido varchar(6),

          @mes  int,

          @f_desde  varchar(11),

          @f_hasta  varchar(11)





if( @pi_NM_LOTE <> 0 and @pi_NM_LOTE <> null)

BEGIN

    print 'fechas de lote'

    select @pi_CD_PROVEEDOR = CD_PROVEEDOR from TCF011_SERVFACCAB where  CD_LOTEFACT = @pi_NM_LOTE 

    select @f_desde = convert(varchar,min(t03.FH_DESDE),106) ,  

           @f_hasta = convert(varchar,MAX(t03.FH_HASTA),106) 

    from TCF003_PERIODOFACT t03

    inner join TCF012_SERVFACDET t12 on  t03.CD_PERIODOFACT = t12.CD_PERIODOFACT

    where  CD_LOTEFACT = @pi_NM_LOTE  and   CD_PROVEEDOR  = @pi_CD_PROVEEDOR

    print  @f_desde

    print  @f_hasta

end 

if( @pi_CD_PERIODO_DESDE <> '' and @pi_CD_PERIODO_DESDE <> null and @pi_CD_PERIODO_DESDE <> '0' )

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







set @query = ' SELECT (CD_SECTORCONCIL + '' - '' + NB_SECTOR) as CD_SECTORCONCIL,'

 +'  (CD_GRUPOPRODUCTO + '' - '' + NB_GRUPOPRODUCTO) as CD_GRUPOPRODUCTO ' 

if( @pi_REDUCIDA = 0) 

begin

	set @query = @query + ' ,(vista.CD_PRODUCTO + '' - '' + NB_PRODUCTO) as CD_PRODUCTO '

end

else 

begin

	set @query = @query + ' , '''' as CD_PRODUCTO '

end

set @query = @query + ',sum(vista.CT_SERVFACT) as CANTFACT , sum(IM_PRECIOTOTAL) as SERVFACT , count(1) as CANTREM, count(case when CD_CONCILIACION = null then 1

  else  (case when ST_CONCILIACION = ''GRA'' then 1 else null  end )  end) as CANTREMCON , sum(CT_NETO) AS CT_NETO, sum(CT_BRUTO)  as CT_BRUTO '

+', vista.CD_PERIODOFACT '





 +'  from VCF001_SERVPRESTGEN vista '

 +' inner join TCF003_PERIODOFACT t003  on vista.CD_PERIODOFACT = t003.CD_PERIODOFACT and vista.CD_PROVEEDOR = t003.CD_PROVEEDOR where '





if( @pi_NM_LOTE <> null and @pi_NM_LOTE <> 0)

begin

	set @query = @query +   ' vista.CD_LOTEFACT = ' || CONVERT(VARCHAR(10),@pi_NM_LOTE)

	if( @pi_CD_PROVEEDOR <> null and @pi_CD_PROVEEDOR <> '' )

	begin

		set @query = @query +   ' and '

	end

end



if( @pi_CD_PROVEEDOR <> null and  @pi_CD_PROVEEDOR <> '')

begin

	 set @query = @query +   ' vista.CD_PROVEEDOR = ''' || @pi_CD_PROVEEDOR ||  '''' 

end



if(@pi_CD_PRODUCTO <> ''

  and @pi_CD_PRODUCTO <> null

  and @pi_CD_PRODUCTO <> '0'  )

  begin

      set @query = @query +   ' and vista.CD_PRODUCTO = ''' ||  @pi_CD_PRODUCTO  ||  '''' 

  end

if(@pi_CD_SECTOR <> ''

  and @pi_CD_SECTOR <> null 

  and @pi_CD_SECTOR <> '0')

begin

      set @query = @query +   ' and vista.CD_SECTORCONCIL = ''' ||  @pi_CD_SECTOR  ||  '''' 

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

  if(@pi_CD_PERIODO_DESDE <> ''

      and @pi_CD_PERIODO_DESDE <> null

        and @pi_CD_PERIODO_DESDE <> '0' )

     begin

         set @query = @query +   ' and t003.FH_DESDE >=  (convert(date,  ''' ||@f_desde ||  ''' ,106))'  

      end



     if(@pi_CD_PERIODO_HASTA <> ''

      and @pi_CD_PERIODO_HASTA <> null 

        and @pi_CD_PERIODO_HASTA <> '0' )

     BEGIN

         set @query = @query +   ' and t003.FH_HASTA <=  (convert(date,  ''' ||@f_hasta ||  ''' ,106))'  

     END    



set @query = @query + ' GROUP BY vista.CD_SECTORCONCIL, vista.CD_GRUPOPRODUCTO, vista.NB_SECTOR,vista.NB_GRUPOPRODUCTO, NB_PRODUCTO '

if( @pi_REDUCIDA = 0) 

begin

	set @query = @query + ' , CD_PRODUCTO '

end

set @query = @query + ' ,vista.CD_PERIODOFACT ORDER BY vista.CD_SECTORCONCIL, vista.CD_GRUPOPRODUCTO, CD_PRODUCTO'



declare @printo varchar(100)

set @printo = substring(@query,1,100)

print @printo

set @printo = substring(@query,100,100)

print @printo

set @printo = substring(@query,200,100)

print @printo

set @printo = substring(@query,300,100)

print @printo

set @printo = substring(@query,400,100)

print @printo

set @printo = substring(@query,500,100)

print @printo

set @printo = substring(@query,600,100)

print @printo

set @printo = substring(@query,700,100)

print @printo

set @printo = substring(@query,800,100)

print @printo

set @printo = substring(@query,900,100)

print @printo

set @printo = substring(@query,1000,100)

print @printo

set @printo = substring(@query,1100,100)

print @printo

set @printo = substring(@query,1200,100)

print @printo

set @printo = substring(@query,1300,100)

print @printo

set @printo = substring(@query,1400,100)

print @printo

set @printo = substring(@query,1500,100)

print @printo

set @printo = substring(@query,1600,100)

print @printo

set @printo = substring(@query,1700,100)

print @printo

set @printo = substring(@query,1800,100)

print @printo

set @printo = substring(@query,1900,100)

print @printo

set @printo = substring(@query,2000,100)

print @printo

set @printo = substring(@query,2100,100)

print @printo

set @printo = substring(@query,2200,100)

print @printo

 exec(@query)
go 


sp_procxmode 'sp_CFS_Consulta_ServFactWV', unchained
go 

setuser
go 

