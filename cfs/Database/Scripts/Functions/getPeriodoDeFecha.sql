CREATE FUNCTION getPeriodoDeFecha( @fecha date, @prov varchar(20))
RETURNS CHAR(11)
AS
BEGIN
    DECLARE @periodo CHAR(11)
 	select @periodo = CD_PERIODOFACT from TCF003_PERIODOFACT s
	where  @fecha >= s.FH_DESDE
	and    @fecha <= s.FH_HASTA
    and    s.CD_PROVEEDOR = @prov
  RETURN (@periodo)
END
go

grant Execute on getPeriodoDeFecha to RolTrnCFACSERV
go