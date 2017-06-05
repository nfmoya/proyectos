
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
    declare @usu_mod_d char(8)
  select @usu_mod_d = CD_USUARIO from TCF016_USUARIO where ID_USUARIO = @usu_modi
  
/* Opcion Alta */
if (@opcion = '1') 
    if exists (select CD_USUARIO 
               from TCF016_USUARIO 
               where CD_USUARIO = @d_usuario) 
       begin     
          set @po_d_error = 'No puede realizar el alta del usuario '+@d_usuario+ ', está siendo usado ese identificador de usuario.'     
          set @po_c_error = 2                           
          return           
       end     
    else
       begin 
   
		  insert into TCF016_USUARIO(CD_USUARIO, DS_NOMBRE, DS_APELLIDO, CD_SECTOR, ST_HABILITADO, FH_ALTA, NB_USUARIOALTA, NB_CORREO , ID_PERFIL)
		  values (@d_usuario, rtrim(@d_nombre) , rtrim(@d_apellido),  @cd_sector, 'S', GetDate(), @usu_mod_d, '' , @id_perfil)
		--  insert into TCF016_USUARIO(d_user, e_usuario, d_nombre, d_apellido, c_usua_alta, f_alta)
		--  values (@d_usuario, 'A', @d_nombre, @d_apellido, @usu_modi, GetDate())
		  
	/*	  set @id_usuario = @@identity 

		  insert into TCF018_USUARIOS_PERFILES(ID_PERFIL, ID_USUARIO, E_USU_PERFIL, C_USUA_ALTA, F_ALTA)
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
                  */

  --       set @d_perfil   = (select d_perfil from INV_perfiles where id_perfil = @id_perfil)
  --        set @d_usu_modi = (select d_user   from TCF016_USUARIO where id_usuario = @usu_modi)

	--	  insert into TCF016_USUARIO(CD_USUARIO, NB_USUARIO, CD_SECTOR, NB_PERFIL, ST_HABILITADO, FH_ALTA, NB_USUARIOALTA, NB_CORREO)
--		  values (@d_usuario, rtrim(@d_nombre) + ' '+ rtrim(@d_apellido),  @cd_sector, @d_perfil, 'S', GetDate(), @d_usu_modi, '')
				  
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
			    update TCF016_USUARIO
                set DS_NOMBRE = rtrim(@d_nombre),
                    DS_APELLIDO  = rtrim(@d_apellido),
                    CD_SECTOR  = @cd_sector,
                --    NB_PERFIL  = @d_perfil,
                    NB_USUARIOMODIF = @usu_mod_d,
                    ID_PERFIL     = @id_perfil,
                    FH_MODIFICACION = GetDate()
				where CD_USUARIO = @d_usuario
/*
				update TCF018_USUARIOS_PERFILES 
				set ID_PERFIL     = @id_perfil,
					C_USUA_ACTUAC = @usu_modi,
					F_ACTUAC      = GetDate()
				where ID_USUARIO = @id_usuario
*/
			end
	end
/* Opcion consulta: se consulta una pieza en particular */

/* Opcion consulta: se consulta una pieza en particular */
if (@opcion = '3') 
	begin
		if (@id_usuario=0) 
			begin
				select u.ID_USUARIO,
					   CD_USUARIO,
                       p.ID_PERFIL,
                       p.D_PERFIL, 
                       DS_NOMBRE,
                       DS_APELLIDO,
                       u.CD_SECTOR,
                        CASE WHEN t007.NB_SECTOR IS NULL THEN '' ELSE t007.NB_SECTOR END as 'NB_SECTOR',
                       NB_CORREO
				from TCF016_USUARIO u
               -- inner join TCF018_USUARIOS_PERFILES up on u.ID_USUARIO = u.ID_USUARIO
                inner join TCF019_PERFILES p on p.ID_PERFIL = u.ID_PERFIL
                left join TCF007_SECTOR t007 on t007.CD_SECTOR = u.CD_SECTOR
                where u.ST_HABILITADO = 'S'
                order by LOWER(CD_USUARIO) asc
			end
		else
			begin
				select u.ID_USUARIO,
					   CD_USUARIO,
                       p.ID_PERFIL,
                       p.D_PERFIL, 
                       DS_NOMBRE,
                       DS_APELLIDO,
                       u.CD_SECTOR,
                       CASE WHEN t007.NB_SECTOR IS NULL THEN '' ELSE t007.NB_SECTOR END as 'NB_SECTOR',
                       NB_CORREO
				from TCF016_USUARIO u
          --      inner join TCF018_USUARIOS_PERFILES up on u.ID_USUARIO = u.ID_USUARIO
                inner join TCF019_PERFILES p on p.ID_PERFIL = u.ID_PERFIL
                left join TCF007_SECTOR t007 on t007.CD_SECTOR = u.CD_SECTOR
                 where u.ST_HABILITADO = 'S'
				  and  u.ID_USUARIO = @id_usuario
				order by LOWER(CD_USUARIO) asc
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

				update TCF016_USUARIO 
				set ST_HABILITADO = 'N',
					NB_USUARIOMODIF = @usu_mod_d,
					FH_MODIFICACION      = GetDate()
				where ID_USUARIO = @id_usuario

			end
	end
go 


sp_procxmode 'sp_CFS_ABMC_Usuarios', unchained
go 

setuser
go 