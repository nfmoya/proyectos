CREATE FUNCTION periodoDesdeFecha( @fecha date )
RETURNS CHAR(11)
AS
BEGIN
    DECLARE @periodo CHAR(11), @xmes int, @xanio char(4), @fullanio char(4)
    set @xmes = CONVERT( int , DATEPART( MONTH, @fecha ) )
    set @xanio = SUBSTRING ( CONVERT( CHAR( 4 ), DATEPART( YEAR, @fecha ), 4 ), 3, 2 )
	set @fullanio = CONVERT( CHAR( 4 ), DATEPART( YEAR, @fecha ), 4 )
	set @periodo = rtrim(ltrim(@fullanio)) 
    if ( @xmes = 1) 
        set @periodo = rtrim(ltrim(@periodo)) + '01ENE'
    if ( @xmes = 2) 
        set @periodo = rtrim(ltrim(@periodo)) + '02FEB'
    if ( @xmes = 3) 
        set @periodo = rtrim(ltrim(@periodo)) + '03MAR'
    if ( @xmes = 4) 
        set @periodo = rtrim(ltrim(@periodo)) + '04ABR'
    if ( @xmes = 5) 
        set @periodo = rtrim(ltrim(@periodo)) + '05MAY'
    if ( @xmes = 6) 
        set @periodo = rtrim(ltrim(@periodo)) + '06JUN'
    if ( @xmes = 7) 
        set @periodo = rtrim(ltrim(@periodo)) + '07JUL'
    if ( @xmes = 8) 
        set @periodo = rtrim(ltrim(@periodo)) + '08AGO'
    if ( @xmes = 9) 
        set @periodo = rtrim(ltrim(@periodo)) + '09SEP'
    if ( @xmes = 10) 
        set @periodo = rtrim(ltrim(@periodo)) + '10OCT'
    if ( @xmes = 11) 
        set @periodo = rtrim(ltrim(@periodo)) + '11NOV'
    if ( @xmes = 12) 
        set @periodo = rtrim(ltrim(@periodo)) + '12DIC'
  set @periodo = rtrim(ltrim(@periodo))+rtrim(ltrim(@xanio)) 
  RETURN (@periodo)
END
