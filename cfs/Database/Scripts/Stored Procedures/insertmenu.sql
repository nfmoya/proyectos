insert into INV_menu (d_menu,x_url_menu,id_padre,n_orden,c_usua_alta,f_alta,n_nivel) 
values('Servicios Prestados','javascript:loadContentDiv(''ConsServPrest.action'')',4,20,1,'2014-02-03 12:30:00.0',2);
insert into INV_menu (d_menu,x_url_menu,id_padre,n_orden,c_usua_alta,f_alta,n_nivel) 
values('Servicios Prestados Detalle','javascript:loadContentDiv(''ConsServPrestDetalle.action'')',4,20,1,'2014-02-03 12:30:00.0',2);

insert into INV_accesos (id_acceso,d_acceso,id_menu) values 
('CONS_SERV_PREST','Consulta de Serv Prest',24);

insert into INV_accesos (id_acceso,d_acceso,id_menu) values 
('CONS_SERV_PREST_DET','Consulta de Serv Prest Detalle',25);

insert into INV_accesos_por_perfiles (id_acceso,id_perfil,f_alta) values 
('CONS_SERV_PREST',1,'2014-02-24 10:26:00.0');

insert into INV_accesos_por_perfiles (id_acceso,id_perfil,f_alta) values 
('CONS_SERV_PREST_DET',1,'2014-02-24 10:26:00.0');



