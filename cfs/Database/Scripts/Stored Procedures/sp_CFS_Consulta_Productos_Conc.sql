-----------------------------------------------------------------------------
-- DDL for Stored Procedure 'CFACSERV.dbo.sp_CFS_Consulta_Productos_Conc;1'
-----------------------------------------------------------------------------

print '<<<<< CREATING Stored Procedure - "CFACSERV.dbo.sp_CFS_Consulta_Productos_Conc;1" >>>>>'
go 

use CFACSERV
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_Consulta_Productos_Conc' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure sp_CFS_Consulta_Productos_Conc

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'CFACSERV.dbo.sp_CFS_Consulta_Productos_Conc;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

CREATE PROCEDURE dbo.sp_CFS_Consulta_Productos_Conc ( 
	@CD_PROVEEDOR        char(6) = ''        ,
	@CD_SECTOR           char(6) = ''        ,
	@CD_PRODUCTO         varchar(12) = ''    ,
    @po_c_error          typ_c_error output  ,
    @po_d_error          typ_d_error output  
) AS 
  set @po_c_error = 0  
  set @po_d_error = null 
begin
         select p.CD_PROVEEDOR       , 
                p.CD_PRODUCTO        ,
                p.NB_PRODUCTO        ,
                p.CD_UNIVAL          ,
                p.ST_CONCILSINVAL    ,
                p.ST_SERVSINCONCIL   ,
                p.ST_FACTSINCONCIL   ,
                p.NU_DIAEMIFDESDE    ,
                p.NU_DIAEMIFHASTA    ,
                p.NU_DIACIERREFDESDE ,
                p.NU_DIACIERREFHASTA ,
                p.ST_HABILITADO      ,
                p.CD_GRUPOPRODUCTO   ,
                isnull(s.ST_HABILITADO, 'N') AS ST_RELACIONSECTOR
         from TCF005_PRODUCTO p
         left join TCF008_PRODSECTOR s on p.CD_PROVEEDOR = s.CD_PROVEEDOR and p.CD_PRODUCTO = s.CD_PRODUCTO and s.CD_SECTOR = @CD_SECTOR
         where p.CD_PROVEEDOR      = @CD_PROVEEDOR
           and p.CD_PRODUCTO       = @CD_PRODUCTO 
           and p.CD_GRUPOPRODUCTO  != 'NO_CON'
end
go 


sp_procxmode 'sp_CFS_Consulta_Productos_Conc', unchained
go 

setuser
go 

