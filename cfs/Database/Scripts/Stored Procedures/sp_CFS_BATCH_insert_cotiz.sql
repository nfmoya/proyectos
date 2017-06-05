IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'sp_CFS_BATCH_insert_cotiz' AND u.name = 'dbo' AND o.type = 'P')
BEGIN
	setuser 'dbo'
	drop procedure dbo.sp_CFS_BATCH_insert_cotiz

END
go 

IF (@@error != 0)
BEGIN
	PRINT "Error CREATING Stored Procedure 'dbo.sp_CFS_BATCH_insert_cotiz;1'"
	SELECT syb_quit()
END
go

setuser 'dbo'
go 

create procedure sp_CFS_BATCH_insert_cotiz
    @FH_CAMBIO   DATE,
    @CD_DIVISS   VARCHAR(3),
    @NU_CAMBFIX  DECIMAL(15,10),
    @NU_CAMBBAJO DECIMAL(15,10),
    @NU_CAMBALTO DECIMAL(15,10),
    @FH_ALTA     DATETIME
as
begin
    IF EXISTS(
            SELECT 1 FROM TCF024_TIPOCAMBIO 
            WHERE FH_CAMBIO = @FH_CAMBIO
            AND CD_DIVISS = @CD_DIVISS
            AND FH_ALTA = @FH_ALTA)
        UPDATE TCF024_TIPOCAMBIO
        SET NU_CAMBFIX = @NU_CAMBFIX,
            NU_CAMBBAJO = @NU_CAMBBAJO,
            NU_CAMBALTO = @NU_CAMBALTO
    ELSE
        INSERT INTO TCF024_TIPOCAMBIO
        (FH_CAMBIO, CD_DIVISS, NU_CAMBFIX, NU_CAMBBAJO, NU_CAMBALTO, FH_ALTA)
        VALUES
        (@FH_CAMBIO, @CD_DIVISS, @NU_CAMBFIX, @NU_CAMBBAJO, @NU_CAMBALTO, @FH_ALTA)
end
GO

grant EXECUTE on sp_CFS_BATCH_insert_cotiz to RolTrnCFACSERV
go

sp_procxmode 'sp_CFS_BATCH_insert_cotiz', anymode
go 

setuser
go 