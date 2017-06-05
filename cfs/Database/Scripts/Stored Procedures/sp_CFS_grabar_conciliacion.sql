
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_grabar_conciliacion;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_grabar_conciliacion;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_grabar_conciliacion' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_grabar_conciliacion

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_grabar_conciliacion;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE sp_CFS_grabar_conciliacion(  
-- drop procedure sp_CFS_grabar_conciliacion
@cd_conciliacion  int         ,
@cd_proveedor     varchar(6)  ,
@cd_sector        varchar(6)  ,
@cd_producto      varchar(6)  ,
@cd_periodo       varchar(6)  ,
@st_ignoraval     char(1)     ,
@nb_observaciones varchar(180),
@st_conciliacion  char(3)     ,
@pi_l_servicios   typ_lista   ,  
@pi_l_facturas    typ_lista   ,  
@pi_l_diferencias typ_lista   ,  
@nb_usuario       char(8)     ,
@po_conciliacion  int output,  
@po_c_error       typ_c_error output,  
@po_d_error       typ_d_error output  
)  
as  
begin
  declare @sep             varchar(1)  ,  
          @subSep          varchar(1)  ,  
          @v_lista         typ_lista   ,  
          @v_sublista      typ_lista   ,   
          @lote            int         ,
          @orden           int         ,
          @logconcil       varchar(10) ,
          @logcondif       varchar(10) ,
          @max_orden       numeric     ,
          @im_precio       numeric     ,
          @im_total        numeric     ,
          @nrolog          int         ,
          @precio          varchar(50) ,
          @total           varchar(50)
  
  execute sp_separador_registros  
             @po_separador_registro    = @sep output,  
             @po_separador_campo       = @subSep output,  
             @po_c_error               = @po_c_error output,  
             @po_d_error               = @po_d_error output                     

  if (@po_c_error  <> 0)  
    begin  
	  set @po_d_error = 'Error llamando a sp_separador_registros : ' + @po_d_error 
      return      
  end    
  set @po_c_error = 0  
  set @po_d_error = null  

  -- SI EL NUMERO DE CONCILIACION = 0 (PORQUE INGRESO DESDE LOS FILTROS) VALIDO SI EXISTE UNA CONCILIACION DE ACUERDO A ESOS MISMOS FILTROS
  if (@cd_conciliacion = 0)
     begin
        select @cd_conciliacion = CD_CONCILIACION
        from TCF013_CONCILIA
        where CD_PROVEEDOR    = @cd_proveedor
          and CD_PRODUCTO     = @cd_producto
          and CD_SECTOR       = @cd_sector
          and CD_PERIODOFACT  = @cd_periodo
          and ST_CONCILIACION = 'GRA'
     end 

  -- VUELVO A VERIFICAR EL NUMERO DE CONCILIACION DE ACUERDO A SI SE INGRESO O EXISTE POR LOS FILTROS INGRESADOS
  if (@cd_conciliacion = 0)
     begin
        set @cd_conciliacion = (isnull((select max(CD_CONCILIACION)+1 from TCF013_CONCILIA),1))

        insert into TCF013_CONCILIA (CD_CONCILIACION,CD_PROVEEDOR,CD_PRODUCTO,CD_SECTOR,CD_PERIODOFACT,ST_IGNORAVAL,NB_OBSERVACIONES,ST_CONCILIACION,FH_CONCILIACION,NB_USUARIOCONCIL) 
        select @cd_conciliacion, @cd_proveedor, @cd_producto, @cd_sector, @cd_periodo, @st_ignoraval, @nb_observaciones, @st_conciliacion, getdate(), @nb_usuario

        set @logconcil = 'INSCONCIL'
     end
  else
     begin
        update TCF013_CONCILIA 
        set ST_IGNORAVAL = @st_ignoraval, NB_OBSERVACIONES = @nb_observaciones, ST_CONCILIACION = @st_conciliacion
        where CD_CONCILIACION = @cd_conciliacion

        set @logconcil = 'UPDCONCIL'
     end

  insert into TCF015_LOG( CD_USUARIO, FH_LOG, CD_EVENTO, NB_LOG)
  values( @nb_usuario, getdate(), @logconcil, 'Conciliacion: '     + convert(varchar(6), @cd_conciliacion) + 
                                              ', Proveedor: '      + @cd_proveedor + 
                                              ', Producto: '       + @cd_producto + 
                                              ', Sector: '         + @cd_sector + 
                                              ', Periodo Fact: '   + @cd_periodo + 
                                              ', Ignora Valores: ' + @st_ignoraval + 
                                              ', Estado: '         + @st_conciliacion)


  -- SERVICIOS PRESTADOS --
  -- Blanqueo los numeros de Conciliacion en los remitos
  update TCF010_SERVPRESDET set CD_CONCILIACION = null where CD_CONCILIACION = @cd_conciliacion and @cd_conciliacion is not null

  -- Actualizo los numeros de Conciliacion
  if (@pi_l_servicios != '' and @pi_l_servicios is not null)
     begin
        set @v_lista = @pi_l_servicios + @sep  
        while (@v_lista is not null)  
           begin  
              --limpio las variables   
              set @lote   = 0
              set @orden  = 0 
              set @precio = '' 
              set @total  = ''
              set @im_precio = 0 
              set @im_total  = 0 

              declare @linea varchar(100)          
              --obtengo un elemento de la lista  
              set @v_sublista = substring(@v_lista, 1,charindex(@sep,@v_lista)-1)+ @subSep

              --obtengo el resto de la lista
              set @v_lista    = substring(@v_lista, charindex(@sep,@v_lista)+1, char_length(@v_lista))

              --separo los subelementos
              set @lote       = convert(numeric,substring(@v_sublista,1,charindex(':',@v_sublista)-1))
              set @v_sublista = substring(@v_sublista, charindex(':',@v_sublista)+1, char_length(@v_sublista))   
               
              set @orden      = convert(numeric,substring(@v_sublista,1,charindex(':',@v_sublista)-1))
              set @v_sublista = substring(@v_sublista, charindex(':',@v_sublista)+1, char_length(@v_sublista))   

              set @precio     = substring(@v_sublista,1,charindex(@subSep,@v_sublista)-1)
              set @v_sublista = substring(@v_sublista, charindex(':',@v_sublista)+1, char_length(@v_sublista))

              set @total      = substring(@v_sublista,1,charindex(':',@v_sublista)-1)
              set @v_sublista = ''

              update TCF010_SERVPRESDET
              set CD_CONCILIACION = @cd_conciliacion 
              where CD_LOTESERV = @lote
                and CD_ORDEN    = @orden

              update TCF010_SERVPRESDET
              set IM_PRECIOUNIT   = convert(numeric(9,4) , @precio), 
                  IM_PRECIOTOTAL  = convert(numeric(11,2), @total) 
              where CD_LOTESERV = @lote
                and CD_ORDEN    = @orden
                and @st_conciliacion = 'APR'

           end
     end

  -- SERVICIOS FACTURADOS --
  -- Blanqueo los numeros de Conciliacion en los remitos
  update TCF012_SERVFACDET set CD_CONCILIACION = null where CD_CONCILIACION = @cd_conciliacion and @cd_conciliacion is not null

  -- Actualizo los numeros de Conciliacion
  if (@pi_l_facturas != '' and @pi_l_facturas is not null)
     begin
        set @v_lista = @pi_l_facturas + @sep  
        while (@v_lista is not null)  
           begin  
              --limpio las variables   
              set @lote  = 0
              set @orden = 0 
          
              --obtengo un elemento de la lista  
              set @v_sublista = substring(@v_lista, 1,charindex(@sep,@v_lista)-1)+ @subSep

              --obtengo el resto de la lista
              set @v_lista    = substring(@v_lista, charindex(@sep,@v_lista)+1, char_length(@v_lista))
                   
              --separo los subelementos        
              set @lote   = convert(numeric,substring(@v_sublista,1,charindex(@subSep,@v_sublista)-1))

              set @v_sublista = substring(@v_sublista, charindex(@subSep,@v_sublista)+1, char_length(@v_sublista))   
               
              set @orden  = convert(numeric,substring(@v_sublista,1,charindex(@subSep,@v_sublista)-1))

              update TCF012_SERVFACDET
              set CD_CONCILIACION = @cd_conciliacion 
              where CD_LOTEFACT = @lote
                and CD_ORDEN    = @orden
           end
     end

  -- DIFERENCIAS --
  -- Verifico la existencia de diferencias cargadas previamente y tomo el ùltimo nùmero para poner como update en el log
  set @max_orden = (select max(CD_ORDEN) from TCF014_DIFCONCILIA where CD_CONCILIACION = @cd_conciliacion)

  -- Ingreso las diferencias
  if (@pi_l_diferencias != '' and @pi_l_diferencias is not null)
     begin
        declare @cd_orden           int         ,
                @cd_remito          varchar(13) , 
                @ct_diferencia      decimal(7,2), 
                @nb_piezadesde      varchar(25) , 
                @nb_piezahasta      varchar(25) , 
                @nb_observa_dife    varchar(180), 
                @cd_univalpres      char(6)     , 
                @im_preciototaldife decimal(9,2), 
                @tp_solucion        char(6)     , 
                @st_diferencia      char(3)

        set @cd_orden = 0
        set @v_lista = @pi_l_diferencias + @sep  
        while (@v_lista is not null)  
           begin  
              --limpio las variables   
              set @cd_remito          = '', 
                  @ct_diferencia      = 0, 
                  @nb_piezadesde      = '', 
                  @nb_piezahasta      = '', 
                  @nb_observa_dife    = '', 
                  @cd_univalpres      = '', 
                  @im_preciototaldife = 0, 
                  @tp_solucion        = '', 
                  @st_diferencia      = ''

              --obtengo un elemento de la lista  
              set @v_sublista = substring(@v_lista, 1,charindex(@sep,@v_lista)-1)+ @subSep

              --obtengo el resto de la lista
              set @v_lista    = substring(@v_lista, charindex(@sep,@v_lista)+1, char_length(@v_lista))
                   
              --separo los subelementos        
              set @cd_orden           = convert(numeric,substring(@v_sublista,1,charindex(@subSep,@v_sublista)-1))
              set @v_sublista         = substring(@v_sublista, charindex(@subSep,@v_sublista)+1, char_length(@v_sublista))   

              set @cd_remito          = substring(@v_sublista,1,charindex(@subSep,@v_sublista)-1)
              set @v_sublista         = substring(@v_sublista, charindex(@subSep,@v_sublista)+1, char_length(@v_sublista))   
               
              set @ct_diferencia      = convert(numeric(9,2),substring(@v_sublista,1,charindex(@subSep,@v_sublista)-1))
              set @v_sublista         = substring(@v_sublista, charindex(@subSep,@v_sublista)+1, char_length(@v_sublista))   

              set @nb_piezadesde      = substring(@v_sublista,1,charindex(@subSep,@v_sublista)-1)
              set @v_sublista         = substring(@v_sublista, charindex(@subSep,@v_sublista)+1, char_length(@v_sublista))   

              set @nb_piezahasta      = substring(@v_sublista,1,charindex(@subSep,@v_sublista)-1)
              set @v_sublista         = substring(@v_sublista, charindex(@subSep,@v_sublista)+1, char_length(@v_sublista))   

              set @nb_observa_dife    = substring(@v_sublista,1,charindex(@subSep,@v_sublista)-1)
              set @v_sublista         = substring(@v_sublista, charindex(@subSep,@v_sublista)+1, char_length(@v_sublista))   

              set @cd_univalpres      = substring(@v_sublista,1,charindex(@subSep,@v_sublista)-1)
              set @v_sublista         = substring(@v_sublista, charindex(@subSep,@v_sublista)+1, char_length(@v_sublista))   

              set @im_preciototaldife = convert(numeric(11,2),substring(@v_sublista,1,charindex(@subSep,@v_sublista)-1))
              set @v_sublista         = substring(@v_sublista, charindex(@subSep,@v_sublista)+1, char_length(@v_sublista))   

              set @tp_solucion        = substring(@v_sublista,1,charindex(@subSep,@v_sublista)-1)
              set @v_sublista         = substring(@v_sublista, charindex(@subSep,@v_sublista)+1, char_length(@v_sublista))   

              set @st_diferencia      = substring(@v_sublista,1,charindex(@subSep,@v_sublista)-1)
              set @v_sublista         = substring(@v_sublista, charindex(@subSep,@v_sublista)+1, char_length(@v_sublista))   

              if @cd_orden <= @max_orden
                 -- update
                 begin
                    update TCF014_DIFCONCILIA 
                    set CD_REMITO = @cd_remito, CT_DIFERENCIA = @ct_diferencia, NB_PIEZADESDE = @nb_piezadesde, NB_PIEZAHASTA = @nb_piezahasta, 
                        NB_OBSERVACIONES = @nb_observa_dife, IM_PRECIOTOTAL = @im_preciototaldife, TP_SOLUCION = @tp_solucion, ST_DIFERENCIA = @st_diferencia 
                    where CD_CONCILIACION = @cd_conciliacion and CD_ORDEN = @cd_orden

                    set @logcondif = 'UPDDIFCONCIL'
                 end
              else
                 begin
                    insert into TCF014_DIFCONCILIA (CD_CONCILIACION,CD_ORDEN,CD_REMITO,CT_DIFERENCIA,NB_PIEZADESDE,NB_PIEZAHASTA,NB_OBSERVACIONES,CD_UNIVAL,IM_PRECIOTOTAL,TP_SOLUCION,ST_DIFERENCIA,FH_ALTA,NB_USUARIOALTA) 
                    select @cd_conciliacion, @cd_orden, @cd_remito, @ct_diferencia, @nb_piezadesde, @nb_piezahasta, @nb_observa_dife, @cd_univalpres, @im_preciototaldife, @tp_solucion, @st_diferencia, getdate(), @nb_usuario

                    set @logcondif = 'INSDIFCONCIL'
                 end

              set @nrolog = (select 1 from TCF015_LOG where CD_USUARIO = @nb_usuario and FH_LOG = getdate())

              insert into TCF015_LOG( CD_USUARIO, FH_LOG, CD_EVENTO, NB_LOG)
              values( @nb_usuario, getdate(), @logcondif, 'Conciliacion: '  + convert(varchar(6), @cd_conciliacion) + 
                                                          ', Orden: '       + convert(varchar(6), @cd_orden) + 
                                                          ', Remito: '      + @cd_remito + 
                                                          ', Cantidad: '    + convert(varchar(10), @ct_diferencia) + 
                                                          ', Precio: '      + convert(varchar(10), @im_preciototaldife) + 
                                                          ', Solucion: '    + @tp_solucion + 
                                                          ', Estado: '      + @st_diferencia)
           end
     end
  set @po_conciliacion = @cd_conciliacion

end
go 


sp_procxmode 'sp_CFS_grabar_conciliacion', unchained
go 

setuser
go 