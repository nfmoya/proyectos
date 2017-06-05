
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_Usuarios;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_ABMC_Usuarios;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_ABMC_Usuarios' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_ABMC_Usuarios

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_Usuarios;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE sp_CFS_ABMC_Usuarios ( 
	@opcion         char(1),
	@id_usuario     numeric(20,0) = 0,
	@d_usuario      varchar(40) = '',
	@id_perfil      numeric(10,0) = 0,
	@d_nombre       varchar(40) = '',
	@d_apellido     varchar(40) = '',
    @cd_sector      char(6) = '',
    @clave_defecto  varchar(40),   
	@usu_modi       numeric(10,0) = 0,
    @po_c_error     typ_c_error output,  
    @po_d_error     typ_d_error output  
) AS 
  declare @d_perfil   varchar(40),
          @d_usu_modi char(8)
  set @po_c_error = 0  
  set @po_d_error = null   
  
/* Opcion Alta */
if (@opcion = '1') 
    if exists (select id_usuario 
               from INV_usuarios 
               where d_user = @d_usuario) 
       begin     
          set @po_d_error = 'No puede realizar el alta del usuario '+@d_usuario+ ', está siendo usado ese identificador de usuario.'     
          set @po_c_error = 2                           
          return           
       end     
    else
       begin 
   
		  insert into INV_usuarios(d_user, e_usuario, d_nombre, d_apellido, c_usua_alta, f_alta)
		  values (@d_usuario, 'A', @d_nombre, @d_apellido, @usu_modi, GetDate())
		  
		  set @id_usuario = @@identity 

		  insert into INV_usuarios_perfiles(id_perfil, id_usuario, e_usu_perfil, c_usua_alta, f_alta)
		  values (@id_perfil, @id_usuario, 'A', @usu_modi, GetDate())
		  
          execute sp_GuardarUsuario   
                  @pi_usu_d_user         = @d_usuario,     
                  @pi_usu_habilitado     = 'S',     
                  @pi_usu_estado	     = 'N',     
                  @pi_usu_clave1	     = '',     
                  @pi_usu_clave2	     = '',                                      
                  @pi_usu_clave3	     = '',     
                  @pi_usu_clave4	     = '',
                  @pi_usu_int_fallidos   = 0,
                  @pi_usu_clave	         = @clave_defecto,      
                  @po_c_error            = @po_c_error output,          
                  @po_d_error            = @po_d_error output

          set @d_perfil   = (select d_perfil from INV_perfiles where id_perfil = @id_perfil)
          set @d_usu_modi = (select d_user   from INV_usuarios where id_usuario = @usu_modi)

		  insert into TCF016_USUARIO(CD_USUARIO, NB_USUARIO, CD_SECTOR, NB_PERFIL, ST_HABILITADO, FH_ALTA, NB_USUARIOALTA, NB_CORREO)
		  values (@d_usuario, rtrim(@d_nombre) + ' '+ rtrim(@d_apellido),  @cd_sector, @d_perfil, 'S', GetDate(), @d_usu_modi, '')
				  
	   end
	   
/* Opcion modificacion */
if (@opcion = '2') 
	begin
		if @id_usuario = 0
			begin
				return -2
			end
		else
			begin	
				update INV_usuarios 
--				set d_user        = @d_usuario,
				set	d_nombre      = @d_nombre,
					d_apellido    = @d_apellido,
					c_usua_actuac = @usu_modi,
					f_actuac      = GetDate()
				where id_usuario = @id_usuario

				update INV_usuarios_perfiles 
				set id_perfil     = @id_perfil,
					c_usua_actuac = @usu_modi,
					f_actuac      = GetDate()
				where id_usuario = @id_usuario

                set @d_perfil   = (select d_perfil from INV_perfiles where id_perfil  = @id_perfil)
                set @d_usu_modi = (select d_user   from INV_usuarios where id_usuario = @usu_modi)

   		        update TCF016_USUARIO
                set NB_USUARIO = rtrim(@d_nombre) + ' '+ rtrim(@d_apellido),
                    CD_SECTOR  = @cd_sector,
                    NB_PERFIL  = @d_perfil,
                    NB_USUARIOMODIF = @d_usu_modi,
                    FH_MODIFICACION = GetDate()
				where CD_USUARIO = @d_usuario
			end
	end
/* Opcion consulta: se consulta una pieza en particular */
if (@opcion = '3') 
	begin
		if (@id_usuario=0) 
			begin
				select INV_usuarios.id_usuario,
			           d_user,
                       id_perfil,
                       case when id_perfil = 2 then 'CONCILIADOR' 
                            when id_perfil = 3 then 'CARGAR LOTES'
                            else 'ADMINISTRADOR' 
                       end as perfil_name, 
			           d_nombre,
			           d_apellido,
                       CD_SECTOR
				from INV_usuarios
                inner join INV_usuarios_perfiles on INV_usuarios.id_usuario = INV_usuarios_perfiles.id_usuario
                inner join TCF016_USUARIO on CD_USUARIO = d_user
                where e_usuario != 'B'
			end
		else
			begin
				select INV_usuarios.id_usuario,
			           d_user,
                       id_perfil,
                       case when id_perfil = 2 then 'CONCILIADOR' 
                            when id_perfil = 3 then 'CARGAR LOTES'
                            else 'ADMINISTRADOR' 
                       end as perfil_name, 
			           d_nombre,
			           d_apellido,
                       CD_SECTOR
				from INV_usuarios
                inner join INV_usuarios_perfiles on INV_usuarios.id_usuario = INV_usuarios_perfiles.id_usuario
                inner join TCF016_USUARIO on CD_USUARIO = d_user
                where e_usuario != 'B'
				  and INV_usuarios.id_usuario = @id_usuario
			end
	end
if (@opcion = '4') 
	begin
		if @id_usuario = 0
			begin
				return -2
			end
		else
			begin	
				update INV_usuarios 
				set e_usuario     = 'B',
					c_usua_actuac = @usu_modi,
					f_actuac      = GetDate()
				where id_usuario = @id_usuario

                set @d_usu_modi = (select d_user   from INV_usuarios where id_usuario = @id_usuario)

                update TCF016_USUARIO
                set ST_HABILITADO = 'N'
				where CD_USUARIO = @d_usu_modi
			end
	end
go 


sp_procxmode 'sp_CFS_ABMC_Usuarios', unchained
go 

setuser
go 


-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_ProveedoresPeriodos;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_ABMC_ProveedoresPeriodos;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_ABMC_ProveedoresPeriodos' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_ABMC_ProveedoresPeriodos

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_ProveedoresPeriodos;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE sp_CFS_ABMC_ProveedoresPeriodos( 
  @opcion             char(1) = ''        ,
  @CD_PROVEEDOR       char(6)  = ''       ,
  @CD_PERIODOFACT     char(6) = ''        ,
  @NB_PERIODOFACT     varchar(20) = ''    ,
  @CD_PERFACTALT      varchar(20) = ''    ,
  @FH_DESDE           varchar(20)         ,
  @FH_HASTA           varchar(20)         ,
  @ST_ESTADO          char(3) = 'PEN'     ,
  @USU_MODI           char(8) = ''        ,
    @po_c_error         typ_c_error output  ,
    @po_d_error         typ_d_error output  
) AS 
  declare 
  @f_desde  smalldatetime,
  @f_hasta  smalldatetime
  set @po_c_error = 0  
  set @po_d_error = null 
  if @FH_DESDE is not null    
     begin  
        execute sp_convierte_char_en_fecha @pi_fecha_char     = @FH_DESDE,  
                                           @po_fecha_datetime = @f_desde    output,  
                                           @po_c_error        = @po_c_error output,  
                                           @po_d_error        = @po_d_error output  
        if (@po_c_error  <> 0)  
           begin  
              set @po_d_error = 'Error llamando a sp_convierte_char_en_fecha : ' + @po_d_error     
              return         
           end
     end  
  if @FH_HASTA is not null    
     begin  
        execute sp_convierte_char_en_fecha @pi_fecha_char     = @FH_HASTA,  
                                           @po_fecha_datetime = @f_hasta    output,  
                                           @po_c_error        = @po_c_error output,  
                                           @po_d_error        = @po_d_error output  
        if (@po_c_error  <> 0)  
           begin  
              set @po_d_error = 'Error llamando a sp_convierte_char_en_fecha : ' + @po_d_error     
              return         
           end
     end  

if (@opcion = '1')
--or  @opcion = '2') 
begin 
  -- Valida que la fecha desde no este incluida en algun periodo
  if exists ( SELECT 1
    FROM TCF003_PERIODOFACT
    where 
    -- valido que las fechas ingresadas no contengan el fin de un periodo
    (@f_desde   between FH_DESDE and FH_HASTA 
    or @f_hasta between FH_DESDE and FH_HASTA)
    and ST_ESTADO <> 'ANU'
    and CD_PROVEEDOR = @CD_PROVEEDOR)
  begin
     set @po_c_error = 1
     set @po_d_error = 'Fecha desde/hasta coincide con algun periodo'
              return  
  end 
end
-- fin Validacion

/* Opcion Alta */
if (@opcion = '1') 
begin
   if not exists (select * 
                 from TCF003_PERIODOFACT  
                 where CD_PROVEEDOR   = @CD_PROVEEDOR
                   and CD_PERIODOFACT = @CD_PERIODOFACT) 
      begin
         insert into TCF003_PERIODOFACT(
                 CD_PROVEEDOR, 
                 CD_PERIODOFACT, 
                 NB_PERIODOFACT, 
                 CD_PERFACTALT, 
                 FH_DESDE, 
                 FH_HASTA,
                 ST_ESTADO, 
                 FH_ALTA, 
                 NB_USUARIOALTA)
         values (@CD_PROVEEDOR, 
                 @CD_PERIODOFACT, 
                 @NB_PERIODOFACT, 
                 @CD_PERFACTALT, 
                 @f_desde, 
                 @f_hasta,
                 @ST_ESTADO, 
                 getdate(), 
                 @USU_MODI)
      end
   else
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Periodo Facturacion duplicado'
         return 
      end
end
/* Opcion modificacion */
if (@opcion = '2') 
begin
   if not exists (select * 
                 from TCF003_PERIODOFACT  
                 where CD_PROVEEDOR   = @CD_PROVEEDOR
                   and CD_PERIODOFACT = @CD_PERIODOFACT) 
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Periodo Facturacion inexistente'
         return 
      end
   else
      begin 
         update TCF003_PERIODOFACT 
         set NB_PERIODOFACT  = @NB_PERIODOFACT , 
             CD_PERFACTALT   = @CD_PERFACTALT  , 
--             FH_DESDE        = @f_desde        , 
--             FH_HASTA        = @f_hasta        ,
             ST_ESTADO       = @ST_ESTADO      , 
             FH_MODIFICACION = getdate()       , 
             NB_USUARIOMODIF = @USU_MODI   
         where CD_PROVEEDOR   = @CD_PROVEEDOR
           and CD_PERIODOFACT = @CD_PERIODOFACT
    end
end
/* Opcion consulta: se consulta una pieza en particular */
if (@opcion = '3') 
begin
            select CD_PROVEEDOR, 
                   CD_PERIODOFACT, 
                   NB_PERIODOFACT, 
                   CD_PERFACTALT, 
                   FH_DESDE, 
                   FH_HASTA,
                   ST_ESTADO 
            from TCF003_PERIODOFACT
            where (CD_PROVEEDOR   = @CD_PROVEEDOR   or @CD_PROVEEDOR is null)
              and (CD_PERIODOFACT = @CD_PERIODOFACT or @CD_PERIODOFACT is null)
            order by CD_PROVEEDOR, CD_PERIODOFACT
end
/* Opcion eliminacion */
if (@opcion = '4') 
begin
   if not exists (select * 
                 from TCF003_PERIODOFACT  
                 where CD_PROVEEDOR   = @CD_PROVEEDOR
                   and CD_PERIODOFACT = @CD_PERIODOFACT) 
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Periodo Facturacion inexsitente'
         return 
      end
   else
      begin 
         delete from TCF003_PERIODOFACT 
         where CD_PROVEEDOR   = @CD_PROVEEDOR
           and CD_PERIODOFACT = @CD_PERIODOFACT
    end
end
go 


sp_procxmode 'sp_CFS_ABMC_ProveedoresPeriodos', unchained
go 

setuser
go 

