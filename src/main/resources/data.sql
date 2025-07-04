INSERT INTO application."role" (id,"name") VALUES
	 (1,'ADMIN');
INSERT INTO application."permission" (id,role_id,"name") VALUES
	 (1,1,'WRITE_ORDER'),
	 (152,1,'WRITE_VENDOR');
INSERT INTO application."user" (id,role_id,email,"name","password") VALUES
	 (53,1,'a.rodriguez@gmail.com','ARodriguez','$2a$10$JyPYBbnsuw/PeZ.hBRU50u04JZIhcYee6BrdJYoX.gzIuby1HUfSK');
