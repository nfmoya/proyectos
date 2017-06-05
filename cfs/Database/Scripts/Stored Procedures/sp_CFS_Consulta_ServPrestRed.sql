
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_Consulta_ServPrestRed;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_Consulta_ServPrestRed;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_Consulta_ServPrestRed' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_Consulta_ServPrestRed

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_Consulta_ServPrestRed;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_Consulta_ServPrestRed( 
  @pi_CD_PROVEEDOR       varchar(6) = ''        ,
  @pi_CD_SECTOR          varchar(15) = ''        ,
  @pi_CD_PRODUCTO        varchar(12) = ''        ,
  @pi_CD_GRUPO           varchar(6) = ''        ,
  @pi_DESDE              varchar(100) = ''        ,
  @pi_HASTA              varchar(100) = ''        ,
  @pi_NM_COMPROBANTE     varchar(13) = ''        ,
  @pi_NM_REMITO_DESDE    varchar(13) = ''        ,
  @pi_NM_REMITO_HASTA    varchar(13) = ''        ,
  @pi_ST_LOTEDET         varchar(1) = ''         ,
  @pi_NM_LOTE            int = 0        ,
  @po_c_error         typ_c_error output  ,
  @po_d_error         typ_d_error output  
) AS 
  set @po_c_error = 0  
  set @po_d_error = null 
  DECLARE @query    varchar(16000)
  declare @cdperido varchar(11),
          @mes  int,
          @f_desde  varchar(100),
          @f_hasta  varchar(100)

if(@pi_DESDE =null and @pi_CD_SECTOR =null) 
begin 
    select @pi_DESDE = convert(varchar,min(FH_REMITO),103)   from TCF009_SERVPRESCAB b left join TCF010_SERVPRESDET a  on (b.CD_LOTESERV = a.CD_LOTESERV )
            where CD_PROVEEDOR  = @pi_CD_PROVEEDOR 
end
else 
begin
	if(@pi_DESDE =null)
	begin
		select @pi_DESDE = convert(varchar,min(FH_REMITO),103)   from TCF009_SERVPRESCAB b left join TCF010_SERVPRESDET a  on (b.CD_LOTESERV = a.CD_LOTESERV )
			where CD_PROVEEDOR  = @pi_CD_PROVEEDOR and CD_SECTOR = @pi_CD_SECTOR
	end
end


if(@pi_HASTA =null and @pi_CD_SECTOR =null) 
begin 
    select @pi_HASTA = convert(varchar,max(FH_REMITO),103)   from TCF009_SERVPRESCAB b left join TCF010_SERVPRESDET a  on (b.CD_LOTESERV = a.CD_LOTESERV )
            where CD_PROVEEDOR  = @pi_CD_PROVEEDOR 
end
else 
begin
	if(@pi_HASTA =null)
	begin
		select @pi_HASTA = convert(varchar,max(FH_REMITO),103)   from TCF009_SERVPRESCAB b left join TCF010_SERVPRESDET a  on (b.CD_LOTESERV = a.CD_LOTESERV )
			where CD_PROVEEDOR  = @pi_CD_PROVEEDOR and CD_SECTOR = @pi_CD_SECTOR
	end
end

print 'lote'
if(@pi_NM_LOTE = null or @pi_NM_LOTE = 0)
begin
    print 'fechas sin lote'
  select top 1 @f_desde = convert(varchar,FH_REMITO,106)   from TCF010_SERVPRESDET a left join TCF009_SERVPRESCAB b on (a.CD_LOTESERV = b.CD_LOTESERV )
            where FH_REMITO >= convert(date, @pi_DESDE, 103)
            and   CD_PROVEEDOR  = @pi_CD_PROVEEDOR 

  select top 1 @f_hasta = convert(varchar,FH_REMITO,106)   from TCF010_SERVPRESDET a left join TCF009_SERVPRESCAB b on (a.CD_LOTESERV = b.CD_LOTESERV )
            where FH_REMITO <=   convert(date, @pi_HASTA, 103)
            and     CD_PROVEEDOR = @pi_CD_PROVEEDOR
            order by  FH_REMITO desc
    print  @f_desde
    print  @f_hasta
END
else
BEGIN
    print 'fechas de lote'
    select @pi_CD_PROVEEDOR = CD_PROVEEDOR from TCF009_SERVPRESCAB where  CD_LOTESERV = @pi_NM_LOTE 
    select @f_desde = convert(varchar,min(t03.FH_REMITO),106) ,  
           @f_hasta = convert(varchar,MAX(t03.FH_REMITO),106) 
    from TCF010_SERVPRESDET t03
    where  CD_LOTESERV = @pi_NM_LOTE 
    print  @f_desde
    print  @f_hasta
end 

--#prod, descripcion, prov, grup ,sect, count(CD_REMITO) as cantrem, sum(cantfact) as cantfact, sum(valorprest) as valorprest, sum(bruto) as bruto, sum(neto) as neto
create table #tempServPrest (
    CD_PROVEEDOR varchar(16) null,
    CD_SECTOR varchar(6) null,
    CD_GRUPOPRODUCTO varchar(6) null,
    PERIODO varchar(11) null  )

if(@pi_NM_LOTE <> null AND @pi_NM_LOTE <> 0)
    BEGIN
          insert into  #tempServPrest 
          SELECT DISTINCT 
              t009.CD_PROVEEDOR as prov ,
              t010.CD_SECTOR as sect, 
              P.CD_GRUPOPRODUCTO as grup,
              dbo.periodoDesdeFecha(t010.FH_REMITO) as periodo
               FROM  TCF009_SERVPRESCAB t009 
               LEFT join TCF010_SERVPRESDET t010 on t009.CD_LOTESERV = t010.CD_LOTESERV
               LEFT JOIN TCF005_PRODUCTO P ON  t010.CD_PRODUCTO = P.CD_PRODUCTO and  t009.CD_PROVEEDOR = P.CD_PROVEEDOR
               where  t009.CD_LOTESERV = @pi_NM_LOTE 
                    AND t010.ST_LOTEDET = @pi_ST_LOTEDET
    END
else
    begin 
		if(@pi_CD_SECTOR = null) 
		begin 
          insert into  #tempServPrest 
          SELECT DISTINCT 
              t009.CD_PROVEEDOR as prov ,
              t010.CD_SECTOR as sect, 
              P.CD_GRUPOPRODUCTO as grup,
              dbo.periodoDesdeFecha(t010.FH_REMITO) as periodo
               FROM  TCF009_SERVPRESCAB t009 
               LEFT join TCF010_SERVPRESDET t010 on t009.CD_LOTESERV = t010.CD_LOTESERV
               LEFT JOIN TCF005_PRODUCTO P ON  t010.CD_PRODUCTO = P.CD_PRODUCTO and  t009.CD_PROVEEDOR = P.CD_PROVEEDOR
                where t009.CD_PROVEEDOR = @pi_CD_PROVEEDOR
               and t010.FH_REMITO >=  (convert(date, @pi_DESDE ,103))  --'03 Jan 2011'
               and t010.FH_REMITO <=  (convert(date, @pi_HASTA ,103)) --'03 Jan 2015'
                AND t010.ST_LOTEDET = @pi_ST_LOTEDET 
		end
		else 
		begin
			insert into  #tempServPrest 
			  SELECT DISTINCT 
				  t009.CD_PROVEEDOR as prov ,
				  t010.CD_SECTOR as sect, 
				  P.CD_GRUPOPRODUCTO as grup,
				  dbo.periodoDesdeFecha(t010.FH_REMITO) as periodo
				   FROM  TCF009_SERVPRESCAB t009 
				   LEFT join TCF010_SERVPRESDET t010 on t009.CD_LOTESERV = t010.CD_LOTESERV
				   LEFT JOIN TCF005_PRODUCTO P ON  t010.CD_PRODUCTO = P.CD_PRODUCTO and  t009.CD_PROVEEDOR = P.CD_PROVEEDOR
					where t009.CD_PROVEEDOR = @pi_CD_PROVEEDOR
						and t010.CD_SECTOR = @pi_CD_SECTOR
				   and t010.FH_REMITO >=  (convert(date, @pi_DESDE ,103))  --'03 Jan 2011'
				   and t010.FH_REMITO <=  (convert(date, @pi_HASTA ,103)) --'03 Jan 2015'
					AND t010.ST_LOTEDET = @pi_ST_LOTEDET 
		end
    end

/* fetch the first row */
print 'cursor'
declare curs cursor for 
	select distinct top 12 PERIODO
        from #tempServPrest
        order by PERIODO
    for READ ONLY
/* open the cursor */
open curs

fetch curs into @cdperido
/* now loop, processing all the rows
** @@sqlstatus = 0 means successful fetch
** @@sqlstatus = 1 means error on previous fetch
** @@sqlstatus = 2 means end of result set reached
*/
	

set @mes = 1
set @query = 'SELECT (t010.CD_SECTOR + '' - '' + t007.NB_SECTOR) as CD_SECTOR,  '
	+'(P.CD_GRUPOPRODUCTO + '' - '' + t001.NB_CODTABLA) as CD_GRUPOPRODUCTO '
while (@@sqlstatus != 2)
begin    
    print 'vuelta'
    set @query = @query + 
--    ', ''' + @cdperido +  ''' as MES'+CONVERT(VARCHAR(2), @mes)+ 'SERVPREST '
-- columnas de mes a repetir
 ',sum(case when dbo.periodoDesdeFecha(t010.FH_REMITO) =  ''' + @cdperido +  ''' then t010.CT_SERVPREST  else 0 end) as MES'+CONVERT(VARCHAR(2), @mes)+ 'SERVPREST '
 +',sum(case when dbo.periodoDesdeFecha(t010.FH_REMITO) =  '''+ @cdperido +  ''' then t010.IM_PRECIOTOTAL  else 0 end) as MES'+CONVERT(VARCHAR(2), @mes)+ 'VALORPREST ' 
  +',count(case when dbo.periodoDesdeFecha(t010.FH_REMITO) = '''+ @cdperido +  ''' then 1 else null end) as MES'+CONVERT(VARCHAR(2), @mes) + 'REM '
 +',count(case when dbo.periodoDesdeFecha(t010.FH_REMITO) ='''+ @cdperido +  ''' then (case when t010.CD_CONCILIACION = null then 1 else (case when ST_CONCILIACION = ''GRA'' then 1 else null end ) end) end) as MES'+CONVERT(VARCHAR(2), @mes)+ 'REMCON '
 set @mes = @mes+1

    /* fetch the next row */
    fetch curs into @cdperido
 
END 
     
/* close the cursor and return */
close curs
   
WHILE @mes <= 12 
begin
    print 'vuelta mala'
  set @query = @query +
   ',(0.0) as MES'+CONVERT(VARCHAR(2), @mes) + 'SERVPREST '
  +',(0.0) as MES'+CONVERT(VARCHAR(2), @mes) + 'VALORPREST '
  +',(0) as MES'+CONVERT(VARCHAR(2), @mes) + 'REM '
  +',(0) as MES'+CONVERT(VARCHAR(2), @mes)+ 'REMCON '
   
  set @mes = @mes+1
END 

set @query = @query + ', sum(t010.CT_SERVPREST )as SERVPREST   '
  +' , sum(t010.IM_PRECIOTOTAL ) as VALORPREST  '
  +' ,sum(t010.IM_PRECIOTOTAL * t004.NU_VALBRUTOUNIVAL ) as VALORPRESTBRUTO  '
  +' ,sum(t010.IM_PRECIOTOTAL * t004.NU_VALNETOUNIVAL ) as VALORPRESTNETO  '
  +' ,count(1) as TOTALREMITOS  '
  +' , count(case when t010.CD_CONCILIACION = null then 1  else (case when ST_CONCILIACION = ''GRA'' then 1 else null end ) end ) as TOTALREMITOSACON  '

set @query = @query +   ' FROM TCF010_SERVPRESDET t010 ' 
  + 'INNER JOIN TCF009_SERVPRESCAB t009 ON (t009.CD_LOTESERV = t010.CD_LOTESERV ) '
  + 'INNER JOIN TCF005_PRODUCTO P ON (P.CD_PRODUCTO = t010.CD_PRODUCTO and P.CD_PROVEEDOR = t009.CD_PROVEEDOR ) '
  + 'INNER JOIN TCF007_SECTOR t007 ON (t007.CD_SECTOR = t010.CD_SECTOR) '
  + 'INNER JOIN TCF001_GENERAL t001 ON t001.CD_CODTABLA = P.CD_GRUPOPRODUCTO and t001.CD_TABLA = ''GRUPRO'' '
  + 'left JOIN TCF013_CONCILIA t013 on t010.CD_CONCILIACION = t013.CD_CONCILIACION '
  + 'left join TCF004_VALORUNIVAL t004 on (P.CD_UNIVAL = t004.CD_UNIVAL and t009.CD_PROVEEDOR =t004.CD_PROVEEDOR  '
  + ' and substring(dbo.periodoDesdeFecha(t010.FH_REMITO),7,11 ) = ltrim(rtrim(t004.CD_PERIODOFACT)) ) where '

if( @pi_NM_LOTE <> null and @pi_NM_LOTE <> 0)
begin
	set @query = @query +   ' t010.CD_LOTESERV = ' || CONVERT(VARCHAR(10),@pi_NM_LOTE)
	if( @pi_CD_PROVEEDOR <> null and @pi_CD_PROVEEDOR <> '' )
	begin
		set @query = @query +   ' and '
	end
end

if( @pi_CD_PROVEEDOR <> null and  @pi_CD_PROVEEDOR <> '')
begin
	 set @query = @query +   ' P.CD_PROVEEDOR = ''' || @pi_CD_PROVEEDOR ||  '''' 
end

if( @pi_DESDE <> null)
	begin
		set @query = @query +' and t010.FH_REMITO >=  (convert(date,  ''' || @pi_DESDE ||  ''' ,103))' 
end
if( @pi_HASTA <> null)
begin
    set @query = @query +' and t010.FH_REMITO <=  (convert(date,  ''' ||@pi_HASTA ||  ''' ,103))'   
end


if(@pi_CD_SECTOR <> '' and @pi_CD_SECTOR <> null and @pi_CD_SECTOR <> '0')
begin
  set @query = @query +   ' and t010.CD_SECTOR = ''' ||  @pi_CD_SECTOR  ||  '''' 
end 
if(@pi_CD_GRUPO <> '' and @pi_CD_GRUPO <> null  and @pi_CD_GRUPO <> '0')
begin
  set @query = @query +   ' and P.CD_GRUPOPRODUCTO = ''' ||  @pi_CD_GRUPO  ||  '''' 
end 
if(@pi_NM_REMITO_DESDE <> '' and @pi_NM_REMITO_DESDE <> null )
begin
  set @query = @query +   ' and (t010.CD_REMITO >=  ''' ||  @pi_NM_REMITO_DESDE  ||  ''' or t010.CD_REMITO is null) ' 
end
if(@pi_NM_REMITO_HASTA <> '' and @pi_NM_REMITO_HASTA <> null )
begin
  set @query = @query +   ' and (t010.CD_REMITO <=  ''' ||  @pi_NM_REMITO_HASTA  ||   ''' or t010.CD_REMITO is null) ' 
end  

    


set @query = @query + ' AND t010.ST_LOTEDET = ''' ||  @pi_ST_LOTEDET || ''' '
    

set @query = @query + ' GROUP BY t010.CD_SECTOR, P.CD_GRUPOPRODUCTO, t007.NB_SECTOR, t001.NB_CODTABLA '+
'ORDER BY t010.CD_SECTOR, P.CD_GRUPOPRODUCTO'

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
set @printo = substring(@query,2300,100)
print @printo
set @printo = substring(@query,2400,100)
print @printo
set @printo = substring(@query,2500,100)
print @printo
set @printo = substring(@query,2600,100)
print @printo
set @printo = substring(@query,2700,100)
print @printo
set @printo = substring(@query,2800,100)
print @printo
set @printo = substring(@query,2900,100)
print @printo
set @printo = substring(@query,3000,100)
print @printo
set @printo = substring(@query,3100,100)
print @printo
set @printo = substring(@query,3200,100)
print @printo
set @printo = substring(@query,3300,100)
print @printo
set @printo = substring(@query,3400,100)
print @printo
set @printo = substring(@query,3500,100)
print @printo
set @printo = substring(@query,3600,100)
print @printo
set @printo = substring(@query,3700,100)
print @printo
set @printo = substring(@query,3800,100)
print @printo
set @printo = substring(@query,3900,100)
print @printo
set @printo = substring(@query,4000,100)
print @printo
--set @po_d_query = @query

exec(@query)
go 


sp_procxmode 'sp_CFS_Consulta_ServPrestRed', unchained
go 

setuser
go 

