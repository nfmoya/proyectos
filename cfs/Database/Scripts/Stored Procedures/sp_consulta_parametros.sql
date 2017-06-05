
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_consulta_parametros;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_consulta_parametros;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_consulta_parametros' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_consulta_parametros

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_consulta_parametros;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_consulta_parametros ( 
@pi_id_tabla 	varchar(3), 
@pi_filtro_1    char(50), 
@pi_filtro_2    char(50), 
@po_c_error typ_c_error output, 
@po_d_error typ_d_error output 
)  as
--Objetivo: obtener todos los códigos y descripciones de una tabla de parÃ¡metros 
begin 
  if (@pi_id_tabla is null or @pi_id_tabla = '') 
    begin 
      set @po_c_error = 3 
      set @po_d_error = 'No se recibió id de tabla ' 
      return        
  end 
  declare @cant_filas int 
  set @po_c_error = 0 
  set @po_d_error = null 

  declare @pos_final int,
          @separador char(1),
          @cd_proveedor char(6),
          @cd_producto char(6),
          @cd_sector char(6),
          @cd_periodoFact char(6),
          @st_conciliacion char(6)
  set @separador = ';'

  /* PROVEEDORES */
  if (@pi_id_tabla = '001')
     begin
        select distinct 
               CD_PROVEEDOR as codigo, 
               CD_PROVEEDOR + ' - ' + NB_PROVEEDOR as valor1
        from TCF002_PROVEEDOR
        where ST_HABILITADO = 'S'
        order by NB_PROVEEDOR
     end
  else
  /* PROVEEDORES - PERIODOS */
  if (@pi_id_tabla = '002')
     begin
        select distinct 
               CD_PERIODOFACT as codigo, 
               CD_PERIODOFACT + ' - ' + NB_PERIODOFACT as valor1
        from TCF003_PERIODOFACT
        where CD_PROVEEDOR = @pi_filtro_1 or @pi_filtro_1 is null
--        and ST_HABILITADO = 'S'
       order by FH_DESDE DESC
        -- order by CD_PERIODOFACT DESC
     end
  else
  /* PRODUCTOS - PERIODOS - UNIVAL */
  if (@pi_id_tabla = '003')
     begin
        select distinct 
               CD_UNIVAL as codigo, 
               CD_UNIVAL as valor1
        from TCF004_VALORUNIVAL
        where CD_PROVEEDOR   = @pi_filtro_1
          and CD_PERIODOFACT = @pi_filtro_2
        order by CD_UNIVAL
     end
  else
  /* PRODUCTOS */
  if (@pi_id_tabla = '004')
     begin
     	select distinct 
       		CD_PRODUCTO as codigo, 
            CD_PRODUCTO + ' - ' + NB_PRODUCTO as valor1
            	from TCF005_PRODUCTO
            		where CD_PROVEEDOR = @pi_filtro_1 or @pi_filtro_1 is null
                order by NB_PRODUCTO
       
     end
  else
  /* PRODUCTOS PARA CONSULTA CONCILIACIONES NO MEDIBLES*/
  if (@pi_id_tabla = '044')
     begin
	      select distinct 
               CD_PRODUCTO as codigo, 
               CD_PRODUCTO + ' - ' + NB_PRODUCTO as valor1
                from TCF005_PRODUCTO
                where (CD_PROVEEDOR = @pi_filtro_1 or @pi_filtro_1 is null) 
                    and CD_GRUPOPRODUCTO != 'NO_CON'
                order by NB_PRODUCTO
     end
  else
  /* PRECIO PRODUCTOS */
  if (@pi_id_tabla = '005')	
     begin
        select distinct 
               FH_DESDE as codigo, 
               FH_DESDE as valor1
        from TCF006_PRECIOPROD
        where CD_PROVEEDOR = @pi_filtro_1
          and CD_PRODUCTO  = @pi_filtro_2
        order by FH_DESDE
     end
  else
  /* PRODUCTOS SECTORES */
  if (@pi_id_tabla = '006')
     begin
        select distinct 
		       CD_SECTOR as codigo, 
               CD_SECTOR as valor1
        from TCF008_PRODSECTOR
        where CD_PROVEEDOR = @pi_filtro_1
          and CD_PRODUCTO  = @pi_filtro_2
        order by CD_SECTOR
     end
  else
  /* SECTORES */
  if (@pi_id_tabla = '007')
     begin
        --if(@pi_filtro_1 = '') begin
    
            select distinct 
               CD_SECTOR as codigo, 
               CD_SECTOR + ' - ' + NB_SECTOR as valor1
        	    from TCF007_SECTOR 
               where ST_HABILITADO = 'S'
                order by NB_SECTOR
    
       /*  end
       else begin

            select distinct 
               s.CD_SECTOR as codigo, 
               s.CD_SECTOR + ' - ' + s.NB_SECTOR as valor1
        	    from TCF007_SECTOR s
                    inner join INV_usuarios u on u.id_usuario = convert(numeric,@pi_filtro_1)
                    inner join TCF016_USUARIO us on us.CD_USUARIO = u.d_user
                where s.ST_HABILITADO = 'S' and (us.CD_USUARIO s.CD_SECTOR = us.CD_SECTOR)
                order by s.NB_SECTOR

        end
        */
     end
  else
  /* TABLAS BASICAS - DESTAB */
  if (@pi_id_tabla = '008')
     begin
        select distinct 
               CD_CODTABLA as codigo, 
               NB_CODTABLA as valor1
        from TCF001_GENERAL
        where CD_TABLA = 'DESTAB'
        order by NB_CODTABLA
     end
  else
  /* TABLAS BASICAS - GRUPOS PRODUCTOS */
  if (@pi_id_tabla = '009')
     begin
        select distinct 
               CD_CODTABLA as codigo, 
               NB_CODTABLA as valor1
        from TCF001_GENERAL
        where CD_TABLA = 'GRUPRO'
          and ST_HABILITADO = 'S'
        order by NB_CODTABLA
     end
  else
  /* TABLAS BASICAS - UNIDAD VALORACION */
  if (@pi_id_tabla = '010')
     begin
        select distinct 
               CD_CODTABLA as codigo, 
               NB_CODTABLA as valor1
        from TCF001_GENERAL
        where CD_TABLA = 'UNIVAL'
          and ST_HABILITADO = 'S'
        order by NB_CODTABLA
     end
  else
  /* TABLAS BASICAS - TIPOS COMPROBANTES */
  if (@pi_id_tabla = '011')
     begin
        select distinct 
               CD_CODTABLA as codigo, 
               NB_CODTABLA as valor1
        from TCF001_GENERAL
        where CD_TABLA = 'TIPCOM'
         and ST_HABILITADO = 'S'
        order by NB_CODTABLA
     end
  else
  /* TABLAS BASICAS - PARAMETROS */
  if (@pi_id_tabla = '012')
     begin
        select distinct 
               CD_CODTABLA as codigo, 
               NB_CODTABLA as valor1
        from TCF001_GENERAL
        where CD_TABLA = 'PARAME'
         and ST_HABILITADO = 'S'
        order by NB_CODTABLA
     end
  else
  /* CONCILIACION - PROVEEDORES PERIODOS ESTADO = 'ABI' o 'CER' */
  if (@pi_id_tabla = '013')
     begin
        select distinct 
               CD_PERIODOFACT as codigo, 
               CD_PERIODOFACT + ' - ' + NB_PERIODOFACT + ' - ' + ST_ESTADO as valor1
        from TCF003_PERIODOFACT
        where (CD_PROVEEDOR = @pi_filtro_1 or @pi_filtro_1 is null)
          and ST_ESTADO IN('ABI', 'CER')
        order by FH_DESDE DESC
     end
  else
  /* CONCILIACION - PROVEEDORES SECTORES PRODUCTOS */
  if (@pi_id_tabla = '014')
     if @pi_filtro_2 is null
        begin
           select distinct 
                  PRD.CD_PRODUCTO as codigo, 
                  PRD.CD_PRODUCTO + ' - ' + PRD.NB_PRODUCTO as valor1
           from TCF005_PRODUCTO PRD
           where (PRD.CD_PROVEEDOR = @pi_filtro_1 or @pi_filtro_1 is null)
		     and PRD.CD_GRUPOPRODUCTO != 'NO_CON'
--           and PRD.ST_HABILITADO = 'S'
           order by PRD.NB_PRODUCTO
        end
     else
        begin
           select distinct 
                  PRD.CD_PRODUCTO as codigo, 
                  PRD.CD_PRODUCTO + ' - ' + PRD.NB_PRODUCTO as valor1
           from TCF005_PRODUCTO PRD
           inner join TCF008_PRODSECTOR SEC ON PRD.CD_PROVEEDOR = SEC.CD_PROVEEDOR AND PRD.CD_PRODUCTO = SEC.CD_PRODUCTO
           where PRD.CD_PROVEEDOR = @pi_filtro_1
		     and PRD.CD_GRUPOPRODUCTO != 'NO_CON'
             and SEC.CD_SECTOR    = @pi_filtro_2
--           and PRD.ST_HABILITADO = 'S'
           order by PRD.NB_PRODUCTO
        end
  /* CONCILIACION - CONCILIACIONES */
  if (@pi_id_tabla = '015')
     begin
        set @pos_final       = charindex(@separador, @pi_filtro_1)
        set @cd_proveedor    = substring(@pi_filtro_1, 1, @pos_final-1) 

        set @pi_filtro_1     = substring(@pi_filtro_1, @pos_final+1,100)
        set @pos_final       = charindex(@separador, @pi_filtro_1)
        set @cd_producto     = substring(@pi_filtro_1, 1, @pos_final-1)

        set @pi_filtro_1     = substring(@pi_filtro_1, @pos_final+1,100)
        set @pos_final       = charindex(@separador, @pi_filtro_1)
        set @cd_sector       = substring(@pi_filtro_1, 1, @pos_final-1)

        set @pi_filtro_1     = substring(@pi_filtro_1, @pos_final+1,100)
        set @pos_final       = charindex(@separador, @pi_filtro_1)
        set @cd_periodoFact  = substring(@pi_filtro_1, 1, @pos_final-1)

        set @pi_filtro_1     = substring(@pi_filtro_1, @pos_final+1,100)
        set @pos_final       = charindex(@separador, @pi_filtro_1)
        set @st_conciliacion = substring(@pi_filtro_1, 1, @pos_final-1)

        select CD_CONCILIACION as codigo,
	           'Conc: ' + convert(varchar, CD_CONCILIACION) + ' - Prv: ' + CD_PROVEEDOR + ', Prd: ' + CD_PRODUCTO + ', Sec: ' + CD_SECTOR + ', Per: ' + CD_PERIODOFACT as valor1
        from TCF013_CONCILIA
	    where rtrim(CD_PROVEEDOR)    = rtrim(@cd_proveedor)
          and (rtrim(CD_PRODUCTO)     = rtrim(@cd_producto)      or rtrim(@cd_producto) = '0')
          and (rtrim(CD_SECTOR)       = rtrim(@cd_sector)        or rtrim(@cd_sector) = '0')
          and (rtrim(CD_PERIODOFACT)  = rtrim(@cd_periodoFact)   or rtrim(@cd_periodoFact) = '0')
	      and rtrim(ST_CONCILIACION) = rtrim(@st_conciliacion)
	    order by FH_CONCILIACION desc
     end
  /* CONCILIACION - CONCILIACIONES */
  if (@pi_id_tabla = '016')
     begin
        set @pos_final       = charindex(@separador, @pi_filtro_1)
        set @cd_proveedor    = substring(@pi_filtro_1, 1, @pos_final-1) 

        set @pi_filtro_1     = substring(@pi_filtro_1, @pos_final+1,100)
        set @pos_final       = charindex(@separador, @pi_filtro_1)
        set @cd_sector       = substring(@pi_filtro_1, 1, @pos_final-1)

        set @pi_filtro_1     = substring(@pi_filtro_1, @pos_final+1,100)
        set @pos_final       = charindex(@separador, @pi_filtro_1)
        set @cd_periodoFact  = substring(@pi_filtro_1, 1, @pos_final-1)

        set @pi_filtro_1     = substring(@pi_filtro_1, @pos_final+1,100)
        set @pos_final       = charindex(@separador, @pi_filtro_1)
        set @st_conciliacion = substring(@pi_filtro_1, 1, @pos_final-1)

        select CD_CONCILIACION as codigo,
	           'Conc: ' + convert(varchar, CD_CONCILIACION) + ' - Prv: ' + CD_PROVEEDOR + ', Sec: ' + CD_SECTOR + ', Per: ' + CD_PERIODOFACT as valor1
        from TCF029_NOMEDIBLES
	    where rtrim(CD_PROVEEDOR)    = rtrim(@cd_proveedor)
          and (rtrim(CD_SECTOR)       = rtrim(@cd_sector)        or rtrim(@cd_sector) = '0')
          and (rtrim(CD_PERIODOFACT)  = rtrim(@cd_periodoFact)   or rtrim(@cd_periodoFact) = '0')
	      and rtrim(ST_CONCILIACION) = rtrim(@st_conciliacion)
	    order by FH_CONCILIACION desc
     end

	 /* PRODUCTOS AGRUPADOS ORIG */
	if (@pi_id_tabla = '017')
    begin
		select CD_PRODUCTO_ORIG as codigo, 
			CD_PRODUCTO_ORIG +' - '+DES_ITEM as valor1 
				from TCF028_PRODUCT_AGRUP
    end 
	/* PRODUCTOS AGRUPADOS REL CON 05 */
	if (@pi_id_tabla = '018')
    begin
		select p.CD_PRODUCTO AS codigo, 
			p.CD_PRODUCTO +' - '+p.NB_PRODUCTO AS valor1 
			from TCF005_PRODUCTO p 
        		inner join TCF028_PRODUCT_AGRUP ag ON ag.CD_PRODUCTO = p.CD_PRODUCTO
       				GROUP BY ag.CD_PRODUCTO, p.CD_PRODUCTO
    end
    /* PRODUCTO_AGRUP_ORIG_ALTA PARA ALTA AGRUPADOS*/
    if (@pi_id_tabla = '019')
    begin
		select p.CD_PRODUCTO as codigo,
		    p.CD_PRODUCTO +' - '+ p.NB_PRODUCTO 
		        from TCF005_PRODUCTO p
		    WHERE p.CD_PROVEEDOR = @pi_filtro_1 and p.NB_ATRIBUTOADIC1 = 'N' 
		        and p.ST_HABILITADO = 'S'
    end 
    /* PRODUCTO_AGRUP_ALTA PARA ALTA AGRUPADOS QUE TENGAN MARCA DE GRUPO.*/
    if (@pi_id_tabla = '020')
    begin
		
		select p.CD_PRODUCTO as codigo,
		    p.CD_PRODUCTO +' - '+ p.NB_PRODUCTO 
		        from TCF005_PRODUCTO p
		    WHERE p.CD_PROVEEDOR = @pi_filtro_1 and p.NB_ATRIBUTOADIC1 = 'S' 
		        and p.ST_HABILITADO = 'S'
    end 
  
    set @po_c_error = @@error
    
    if (@po_c_error  <> 0) 
      begin  
        set @po_d_error =  convert(varchar,@po_c_error)  
                           + ' - Error en sp_consulta_parametros: '+  
                          convert(varchar(7),@pi_id_tabla) 
        return 
      end

end
go 


sp_procxmode 'sp_consulta_parametros', anymode
go 

setuser
go 

Grant Execute on dbo.sp_consulta_parametros to RolTrnCFACSERV 
go
