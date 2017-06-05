
-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_Productos;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_ABMC_Productos;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_ABMC_Productos' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_ABMC_Productos

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_ABMC_Productos;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_ABMC_Productos ( 
	@opcion              char(1) = ''        ,
	@CD_PROVEEDOR        char(6) = ''        ,
	@CD_PRODUCTO         varchar(12) = ''    ,
	@NB_PRODUCTO         varchar(40) = ''    ,
	@NB_PRODUCTOCORTO    varchar(15) = ''    ,
	@CD_GRUPOPRODUCTO    char(12) = ''        ,
	@CD_UNIVAL           char(6) = ''        ,
	@CD_SECSOLSERV       varchar(15) = ''    ,
	@CD_SECCONSERV       varchar(15) = ''    ,
	@CD_SECCONFACT       varchar(15) = ''    ,
	@ST_PRODIMPORTPREST  char(1) = ''        ,
	@ST_PRODIMPORTFACT   char(1) = ''        ,
	@ST_REMSERVOBLIG     char(1) = ''        ,
	@ST_REMFACTOBLIG     char(1) = ''        ,
	@ST_ADMITEREMSERV    char(1) = ''        ,
	@ST_ADMITEREMFACT    char(1)             ,
	@NB_ATRIBUTOREF1     varchar(30) = ''    ,
	@NB_ATRIBUTOREF2     varchar(10) = ''    ,
	@ST_CONCILSINVAL     char(1) = ''        ,
	@ST_SERVSINCONCIL    char(1) = ''        ,
	@ST_FACTSINCONCIL    char(1) = ''        ,
	@NU_DIAEMIFDESDE     numeric(10,0) = 0   ,
	@NU_DIAEMIFHASTA     numeric(10,0) = 0   ,
	@NU_DIACIERREFDESDE  numeric(10,0) = 0   ,
	@NU_DIACIERREFHASTA  numeric(10,0) = 0   ,
	@NB_ATRIBUTOADIC1    varchar(30) = ''    ,
	@NB_ATRIBUTOADIC2    varchar(30) = ''    ,
	@NB_ATRIBUTOADIC3    varchar(30) = ''    ,
	@NB_ATRIBUTOADIC4    varchar(30) = ''    ,
	@NB_ATRIBUTOADIC5    varchar(30) = ''    ,
	@ST_HABILITADO       char(1) = ''        ,
	@CD_TIPVAL           char(3) = ''        ,
	@CD_MONEDA           char(3) = ''        ,
	@NU_PORCVARMAX       numeric(10,0) = 0   ,
	@USU_MODI            char(8) = ''        ,
    @po_c_error          typ_c_error output  ,
    @po_d_error          typ_d_error output  
) AS 
  set @po_c_error = 0  
  set @po_d_error = null 
/* Opcion Alta */
if (@opcion = '1') 
begin
   if not exists (select * 
                 from TCF005_PRODUCTO  
                 where CD_PROVEEDOR = @CD_PROVEEDOR 
                   and CD_PRODUCTO  = @CD_PRODUCTO)
      begin   
         insert into TCF005_PRODUCTO(
                 CD_PROVEEDOR       ,
                 CD_PRODUCTO        ,
                 NB_PRODUCTO        ,
                 NB_PRODUCTOCORTO   ,
                 CD_GRUPOPRODUCTO   ,
                 CD_UNIVAL          ,
                 CD_SECSOLSERV      ,
                 CD_SECCONSERV      ,
                 CD_SECCONFACT      ,
                 ST_PRODIMPORTPREST ,
                 ST_PRODIMPORTFACT  ,
                 ST_REMSERVOBLIG    ,
                 ST_REMFACTOBLIG    ,
                 ST_ADMITEREMSERV   ,
                 ST_ADMITEREMFACT   ,
                 NB_ATRIBUTOREF1    ,
                 NB_ATRIBUTOREF2    ,
                 ST_CONCILSINVAL    ,
                 ST_SERVSINCONCIL   ,
                 ST_FACTSINCONCIL   ,
                 NU_DIAEMIFDESDE    ,
                 NU_DIAEMIFHASTA    ,
                 NU_DIACIERREFDESDE ,
                 NU_DIACIERREFHASTA ,
                 NB_ATRIBUTOADIC1   ,
                 NB_ATRIBUTOADIC2   ,
                 NB_ATRIBUTOADIC3   ,
                 NB_ATRIBUTOADIC4   ,
                 NB_ATRIBUTOADIC5   ,
                 ST_HABILITADO      ,
                 CD_TIPVAL          ,
            	 CD_MONEDA          ,
	             NU_PORCVARMAX      ,
                 FH_ALTA            , 
                 NB_USUARIOALTA)
         values (@CD_PROVEEDOR       ,
                 @CD_PRODUCTO        ,
                 @NB_PRODUCTO        ,
                 @NB_PRODUCTOCORTO   ,
                 @CD_GRUPOPRODUCTO   ,
                 @CD_UNIVAL          ,
                 @CD_SECSOLSERV      ,
                 @CD_SECCONSERV      ,
                 @CD_SECCONFACT      ,
                 @ST_PRODIMPORTPREST ,
                 @ST_PRODIMPORTFACT  ,
                 @ST_REMSERVOBLIG    ,
                 @ST_REMFACTOBLIG    ,
                 @ST_ADMITEREMSERV   ,
                 @ST_ADMITEREMFACT   ,
                 @NB_ATRIBUTOREF1    ,
                 @NB_ATRIBUTOREF2    ,
                 @ST_CONCILSINVAL    ,
                 @ST_SERVSINCONCIL   ,
                 @ST_FACTSINCONCIL   ,
                 @NU_DIAEMIFDESDE    ,
                 @NU_DIAEMIFHASTA    ,
                 @NU_DIACIERREFDESDE ,
                 @NU_DIACIERREFHASTA ,
                 @NB_ATRIBUTOADIC1   ,
                 @NB_ATRIBUTOADIC2   ,
                 @NB_ATRIBUTOADIC3   ,
                 @NB_ATRIBUTOADIC4   ,
                 @NB_ATRIBUTOADIC5   ,
                 @ST_HABILITADO      ,
                 @CD_TIPVAL          ,
            	 @CD_MONEDA          ,
	             @NU_PORCVARMAX      ,
                 getdate()           , 
                 @USU_MODI)
      end
   else
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Codigo de Producto duplicado'
         return 
      end
end
/* Opcion modificacion */
if (@opcion = '2') 
begin
   if not exists (select * 
                  from TCF005_PRODUCTO  
                  where CD_PROVEEDOR = @CD_PROVEEDOR 
                    and CD_PRODUCTO  = @CD_PRODUCTO) 
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Codigo de Producto inexistente'
         return 
      end
   else
      begin	
         update TCF005_PRODUCTO 
         set NB_PRODUCTO        = @NB_PRODUCTO        ,
             NB_PRODUCTOCORTO   = @NB_PRODUCTOCORTO   ,
             CD_GRUPOPRODUCTO   = @CD_GRUPOPRODUCTO   ,
             CD_UNIVAL          = @CD_UNIVAL          ,
             CD_SECSOLSERV      = @CD_SECSOLSERV      ,
             CD_SECCONSERV      = @CD_SECCONSERV      ,
             CD_SECCONFACT      = @CD_SECCONFACT      ,
             ST_PRODIMPORTPREST = @ST_PRODIMPORTPREST ,
             ST_PRODIMPORTFACT  = @ST_PRODIMPORTFACT  ,
             ST_REMSERVOBLIG    = @ST_REMSERVOBLIG    ,
             ST_REMFACTOBLIG    = @ST_REMFACTOBLIG    ,
             ST_ADMITEREMSERV   = @ST_ADMITEREMSERV   ,
             ST_ADMITEREMFACT   = @ST_ADMITEREMFACT   ,
             NB_ATRIBUTOREF1    = @NB_ATRIBUTOREF1    ,
             NB_ATRIBUTOREF2    = @NB_ATRIBUTOREF2    ,
             ST_CONCILSINVAL    = @ST_CONCILSINVAL    ,
             ST_SERVSINCONCIL   = @ST_SERVSINCONCIL   ,
             ST_FACTSINCONCIL   = @ST_FACTSINCONCIL   ,
             NU_DIAEMIFDESDE    = @NU_DIAEMIFDESDE    ,
             NU_DIAEMIFHASTA    = @NU_DIAEMIFHASTA    ,
             NU_DIACIERREFDESDE = @NU_DIACIERREFDESDE ,
             NU_DIACIERREFHASTA = @NU_DIACIERREFHASTA ,
             NB_ATRIBUTOADIC1   = @NB_ATRIBUTOADIC1   ,
             NB_ATRIBUTOADIC2   = @NB_ATRIBUTOADIC2   ,
             NB_ATRIBUTOADIC3   = @NB_ATRIBUTOADIC3   ,
             NB_ATRIBUTOADIC4   = @NB_ATRIBUTOADIC4   ,
             NB_ATRIBUTOADIC5   = @NB_ATRIBUTOADIC5   ,
             ST_HABILITADO      = @ST_HABILITADO      ,
             CD_TIPVAL          = @CD_TIPVAL          ,
             CD_MONEDA          = @CD_MONEDA          ,
             NU_PORCVARMAX      = @NU_PORCVARMAX      ,
             FH_MODIFICACION    = getdate()           , 
             NB_USUARIOMODIF    = @USU_MODI
         where CD_PROVEEDOR = @CD_PROVEEDOR
           and CD_PRODUCTO  = @CD_PRODUCTO 
	  end
end
/* Opcion consulta: se consulta una pieza en particular */
if (@opcion = '3') 
begin
         select CD_PROVEEDOR       , 
                CD_PRODUCTO        ,
                NB_PRODUCTO        ,
                NB_PRODUCTOCORTO   ,
                CD_GRUPOPRODUCTO   ,
                CD_UNIVAL          ,
                CD_SECSOLSERV      ,
                CD_SECCONSERV      ,
                CD_SECCONFACT      ,
                ST_PRODIMPORTPREST ,
                ST_PRODIMPORTFACT  ,
                ST_REMSERVOBLIG    ,
                ST_REMFACTOBLIG    ,
                ST_ADMITEREMSERV   ,
                ST_ADMITEREMFACT   ,
                NB_ATRIBUTOREF1    ,
                NB_ATRIBUTOREF2    ,
                ST_CONCILSINVAL    ,
                ST_SERVSINCONCIL   ,
                ST_FACTSINCONCIL   ,
                NU_DIAEMIFDESDE    ,
                NU_DIAEMIFHASTA    ,
                NU_DIACIERREFDESDE ,
                NU_DIACIERREFHASTA ,
                NB_ATRIBUTOADIC1   ,
                NB_ATRIBUTOADIC2   ,
                NB_ATRIBUTOADIC3   ,
                NB_ATRIBUTOADIC4   ,
                NB_ATRIBUTOADIC5   ,
                ST_HABILITADO      ,
                CD_TIPVAL          ,
                CD_MONEDA          ,
                NU_PORCVARMAX
         from TCF005_PRODUCTO
         where (CD_PROVEEDOR      = @CD_PROVEEDOR or @CD_PROVEEDOR is null)
           and (CD_PRODUCTO       = @CD_PRODUCTO  or @CD_PRODUCTO is null)
           and (CD_GRUPOPRODUCTO  = @CD_GRUPOPRODUCTO  or @CD_GRUPOPRODUCTO is null)
           and (ST_HABILITADO     = @ST_HABILITADO  or @ST_HABILITADO is null)
         order by  CD_PROVEEDOR, CD_PRODUCTO
end
/* Opcion eliminacion */
if (@opcion = '4') 
begin
   if not exists (select * 
                  from TCF005_PRODUCTO  
                  where CD_PROVEEDOR = @CD_PROVEEDOR 
                    and CD_PRODUCTO  = @CD_PRODUCTO) 
      begin
         set @po_c_error   = 1
         set @po_d_error   = 'Codigo de Producto inexistente'
         return 
      end
   else
      begin	
         delete from TCF005_PRODUCTO 
         where CD_PROVEEDOR = @CD_PROVEEDOR
           and CD_PRODUCTO  = @CD_PRODUCTO 
	  end
end
go 


sp_procxmode 'sp_CFS_ABMC_Productos', anymode
go 

setuser
go 

