
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_Consulta_ServFactReduc;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_Consulta_ServFactReduc;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_Consulta_ServFactReduc' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_Consulta_ServFactReduc

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_Consulta_ServFactReduc;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_Consulta_ServFactReduc ( 
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
) AS 
  set @po_c_error = 0  
  set @po_d_error = null 
  DECLARE @query    varchar(16000)
  declare @cdperido varchar(6),
          @mes  int,
          @f_desde  varchar(11),
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
    where  CD_LOTEFACT = @pi_NM_LOTE  and   CD_PROVEEDOR  = @pi_CD_PROVEEDOR
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

print 'cursor'
declare curs cursor for 
            select 
                   CD_PERIODOFACT
            from TCF003_PERIODOFACT
            where CD_PROVEEDOR   = @pi_CD_PROVEEDOR 
              and FH_DESDE >= @f_desde
              and FH_HASTA <= @f_hasta
            order by FH_DESDE asc
    for READ ONLY
set @query = 'SELECT (t012.CD_SECTORCONCIL + '' - '' + t007.NB_SECTOR) as CD_SECTORCONCIL,  '+
            '(P.CD_GRUPOPRODUCTO + '' - '' + t001.NB_CODTABLA) as CD_GRUPOPRODUCTO '
             --+ '(t012.CD_PRODUCTO + '' - '' + P.NB_PRODUCTO) as CD_PRODUCTO '       
/* open the cursor */
open curs

create table #tempvalores
    (
      CD_PROVEEDOR     varchar(16) null,
      CD_GRUPOPRODUCTO varchar(6) null,
      CD_SECTORCONCIL  varchar(6) null,
      NU_PORCVARMAX    int null,
      VA_BRUTO         numeric(14,2) null,
      VA_NETO          numeric(14,2) null
    )

/* fetch the first row */

fetch curs into @cdperido

/* now loop, processing all the rows
** @@sqlstatus = 0 means successful fetch
** @@sqlstatus = 1 means error on previous fetch
** @@sqlstatus = 2 means end of result set reached
*/

if(    @pi_NM_LOTE <> null
   AND @pi_NM_LOTE <> 0)
BEGIN
    insert into  #tempvalores --values ( 
    select prov, grup, sect, varmax, sum (bruto), sum(neto) from (
                    SELECT 
                            t011.CD_PROVEEDOR as prov
                          , P.CD_GRUPOPRODUCTO as grup,t012.CD_SECTORCONCIL  as sect
                          , (t012.IM_PRECIOTOTAL * t004.NU_VALNETOUNIVAL)  as neto                      
                          , (t012.IM_PRECIOTOTAL * t004.NU_VALBRUTOUNIVAL) as bruto
                          , P.NU_PORCVARMAX as varmax
                    FROM TCF012_SERVFACDET t012                    
                    INNER JOIN TCF011_SERVFACCAB t011 ON t011.CD_LOTEFACT = t012.CD_LOTEFACT 
                    INNER JOIN TCF005_PRODUCTO P ON  t012.CD_PRODUCTO = P.CD_PRODUCTO and  t011.CD_PROVEEDOR = P.CD_PROVEEDOR
                    inner join TCF004_VALORUNIVAL t004 on t004.CD_UNIVAL     = P.CD_UNIVAL 
                                                      and t004.CD_PROVEEDOR   = t011.CD_PROVEEDOR
                                                      and t004.CD_PERIODOFACT = t012.CD_PERIODOFACT  
                    where  t011.CD_LOTEFACT = @pi_NM_LOTE ) a  group by  prov ,grup,sect
END
else
BEGIN
    print 'insert tempvalores'
    insert into  #tempvalores 
    select prov, grup, sect, varmax, sum(neto), sum (bruto) from (
                    SELECT                     
                            t011.CD_PROVEEDOR as prov
                          , P.CD_GRUPOPRODUCTO as grup,t012.CD_SECTORCONCIL  as sect                    
                          , (t012.IM_PRECIOTOTAL * t004.NU_VALNETOUNIVAL)  as neto  
                          , (t012.IM_PRECIOTOTAL * t004.NU_VALBRUTOUNIVAL) as bruto
                          , P.NU_PORCVARMAX as varmax
                    FROM TCF012_SERVFACDET t012                      
                    INNER JOIN TCF011_SERVFACCAB t011 ON t011.CD_LOTEFACT = t012.CD_LOTEFACT 
                    INNER JOIN TCF005_PRODUCTO P ON  t012.CD_PRODUCTO = P.CD_PRODUCTO and  t011.CD_PROVEEDOR = P.CD_PROVEEDOR                    
                    inner join TCF004_VALORUNIVAL t004 on t004.CD_UNIVAL     = P.CD_UNIVAL 
                                                      and t004.CD_PROVEEDOR   = t011.CD_PROVEEDOR
                                                      and t004.CD_PERIODOFACT = t012.CD_PERIODOFACT  
                    inner join TCF003_PERIODOFACT t003 on t012.CD_PERIODOFACT = t003.CD_PERIODOFACT
                    where P.CD_PROVEEDOR = @pi_CD_PROVEEDOR
                      and t003.FH_DESDE >=  (convert(date, @f_desde    ,106))
                      and t003.FH_HASTA <=  (convert(date,@f_hasta ,106)) ) a  group by  prov ,grup,sect
end

set @mes = 1
while (@@sqlstatus != 2)
begin    
    print 'vuelta'
    set @query = @query + 
                    -- columnas de mes a repetir
                    ',sum(case when t012.CD_PERIODOFACT = '''+ @cdperido +  '''' 
                  + ' then t012.CT_SERVFACT else 0 end) as MES'+CONVERT(VARCHAR(2), @mes)
                  + 'CANTFACT ,sum(case when t012.CD_PERIODOFACT = '''+ @cdperido+  ''''  +  
                    ' then t012.IM_PRECIOTOTAL else 0 end) as MES'+CONVERT(VARCHAR(2), @mes)
                  + 'SERVFACT ,count(distinct(case when t012.CD_PERIODOFACT = '''+ @cdperido +  '''' 
                  + ' then t012.CD_REMITO else null end)) as MES'+CONVERT(VARCHAR(2), @mes)
                  + 'REM ,count(distinct(case when t012.CD_PERIODOFACT = '''+ @cdperido +  '''' 
                  + ' then (case when t012.CD_CONCILIACION = null then CD_REMITO ' +
                    ' else  (case when t013.ST_CONCILIACION = null then CD_REMITO else null  end )  end)  else null end)) as MES'+CONVERT(VARCHAR(2), @mes)+ 'REMCON '

set @mes = @mes+1
    /* fetch the next row */
    fetch curs into @cdperido
end

/* close the cursor and return */
close curs

WHILE @mes <= 12 
begin
    print 'vuelta mala'
    set @query = @query +
                     ',(0.0) as MES'+CONVERT(VARCHAR(2), @mes)
                    + 'CANTFACT,(0.0) as MES'+CONVERT(VARCHAR(2), @mes)
                    + 'SERVFACT ,(0) as MES'+CONVERT(VARCHAR(2), @mes)
                    + 'REM,(0) as MES'+CONVERT(VARCHAR(2), @mes)+ 'REMCON '
    set @mes = @mes+1
END 

set @query = @query + ',sum(t012.CT_SERVFACT) as SERVFACT '
          +' ,sum(t012.IM_PRECIOTOTAL) as VALORFACT '
        --  +' -- falta valor facturado bruto '
          +' ,sum(ttemp.VA_BRUTO) as VALORFACTBRUTO '
          --+' ,(0.0) as VALORFACTBRUTO '
         -- +' -- falta valor facturado neto '
          +' ,sum(ttemp.VA_NETO) as VALORFACTNETO '
          --+' ,(0.0) as VALORFACTNETO '
          +' ,count(distinct(t012.CD_REMITO)) as TOTALREMITOS '
          +' ,count(distinct((case when t012.CD_CONCILIACION = null then CD_REMITO  else  '
          +' (case when t013.ST_CONCILIACION = ''GRA'' then CD_REMITO else null end ) '
          +'   end))) as TOTALREMITOSACON '

set @query = @query +   ' FROM TCF012_SERVFACDET t012 ' 
         +'INNER JOIN TCF011_SERVFACCAB t011 ON t011.CD_LOTEFACT = t012.CD_LOTEFACT '
          +'INNER JOIN TCF005_PRODUCTO P ON P.CD_PRODUCTO = t012.CD_PRODUCTO and P.CD_PROVEEDOR = t011.CD_PROVEEDOR '
          +'INNER JOIN TCF007_SECTOR t007 ON t007.CD_SECTOR = t012.CD_SECTORCONCIL '
          +'INNER JOIN TCF001_GENERAL t001 ON t001.CD_CODTABLA = P.CD_GRUPOPRODUCTO and t001.CD_TABLA = ''GRUPRO'' '
        +'left join TCF013_CONCILIA t013 ' 
        +'on t012.CD_CONCILIACION = t013.CD_CONCILIACION inner join TCF003_PERIODOFACT t003 '
        +'on t012.CD_PERIODOFACT = t003.CD_PERIODOFACT and t011.CD_PROVEEDOR = t003.CD_PROVEEDOR  '
        + 'left join #tempvalores ttemp on t011.CD_PROVEEDOR = ttemp.CD_PROVEEDOR '--P.CD_PRODUCTO = ttemp.CD_PRODUCTO and 
        + ' and P.CD_GRUPOPRODUCTO = ttemp.CD_GRUPOPRODUCTO and t012.CD_SECTORCONCIL = ttemp.CD_SECTORCONCIL '

if(   @pi_NM_LOTE = null
   or @pi_NM_LOTE = 0)
begin
    --CONVERT(VARCHAR(8), tab.date_col
    set @query = @query +   ' where   t011.CD_PROVEEDOR = ''' || @pi_CD_PROVEEDOR ||  '''' 
end
else
begin
    set @query = @query +   ' where   t012.CD_LOTEFACT = ' || CONVERT(VARCHAR(10),@pi_NM_LOTE)
    if (   @pi_CD_PROVEEDOR <> ''
        and @pi_CD_PROVEEDOR <> null
        and @pi_CD_PROVEEDOR <> '0'  )
       begin
            set @query = @query +    ' and   t011.CD_PROVEEDOR = ''' || @pi_CD_PROVEEDOR ||  '''' 
        end
end
if(    @pi_CD_PRODUCTO <> ''
   and @pi_CD_PRODUCTO <> null 
   and @pi_CD_PRODUCTO <> '0'  )
begin
      set @query = @query +   ' and t012.CD_PRODUCTO = ''' ||  @pi_CD_PRODUCTO  ||  '''' 
end

if(    @pi_CD_SECTOR <> ''
   and @pi_CD_SECTOR <> null 
   and @pi_CD_SECTOR <> '0')
begin
      set @query = @query +   ' and t012.CD_SECTORCONCIL = ''' ||  @pi_CD_SECTOR  ||  '''' 
end 

if(    @pi_NM_COMPROBANTE <> ''
   and @pi_NM_COMPROBANTE <> null )
begin
    set @query = @query +   ' and t012.NU_COMPROBANTE =  ''' ||  @pi_NM_COMPROBANTE  ||  '''' 
end

if(    @pi_CD_TIPCOMP <> ''
   and @pi_CD_TIPCOMP <> null )
begin
    set @query = @query +   ' and t012.TP_COMPROBANTE =  ''' ||  @pi_CD_TIPCOMP  ||  '''' 
end

if(@pi_CD_HABILITADO =  'N' )
begin
    set @query = @query +   ' and t011.ST_LOTECAB <> ''ACT  '' ' 
end
else
begin
    set @query = @query +   ' and t011.ST_LOTECAB = ''ACT  '' ' 
end

if(    @pi_CD_GRUPO <> ''
   and @pi_CD_GRUPO <> null 
   and @pi_CD_GRUPO <> '0')
begin
    set @query = @query +   ' and P.CD_GRUPOPRODUCTO = ''' ||  @pi_CD_GRUPO  ||  '''' 
end 

if(    @pi_NM_REMITO_DESDE <> ''
   and @pi_NM_REMITO_DESDE <> null )
begin
    set @query = @query +   ' and (t012.CD_REMITO >=  ''' ||  @pi_NM_REMITO_DESDE  ||  ''' or t012.CD_REMITO is null) ' 
end

if(    @pi_NM_REMITO_HASTA <> ''
   and @pi_NM_REMITO_HASTA <> null )
begin
    set @query = @query +   ' and (t012.CD_REMITO <=  ''' ||  @pi_NM_REMITO_HASTA  ||   ''' or t012.CD_REMITO is null) ' 
end  

if(    @pi_CD_PERIODO_DESDE <> ''
   and @pi_CD_PERIODO_DESDE <> null
   and @pi_CD_PERIODO_DESDE <> '0' )
begin
    set @query = @query +   ' and t003.FH_DESDE >=  (convert(date,  ''' ||@f_desde ||  ''' ,106))'  
end

if(    @pi_CD_PERIODO_HASTA <> ''
   and @pi_CD_PERIODO_HASTA <> null 
   and @pi_CD_PERIODO_HASTA <> '0' )
BEGIN
    set @query = @query +   ' and t003.FH_HASTA <=  (convert(date,  ''' ||@f_hasta ||  ''' ,106))'  
END    

set @query = @query + ' GROUP BY t012.CD_SECTORCONCIL, P.CD_GRUPOPRODUCTO, t007.NB_SECTOR,t001.NB_CODTABLA, P.NU_PORCVARMAX '+
                      ' ORDER BY t012.CD_SECTORCONCIL, P.CD_GRUPOPRODUCTO'

exec(@query)
go 


sp_procxmode 'sp_CFS_Consulta_ServFactReduc', anymode
go 

setuser
go 

