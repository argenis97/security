INSERT INTO application."role" (id,"name") VALUES
	 (1,'ADMIN'),
	 (52,'USER');
INSERT INTO application."permission" (id,role_id,"name") VALUES
	 (1,1,'WRITE_ORDER'),
	 (152,1,'WRITE_VENDOR'),
	 (202,1,'WRITE_USER'),
	 (252,52,'WRITE_ORDER'),
	 (302,1,'WRITE_ROLE'),
	 (303,1,'DELETE_ROLE'),
	 (304,1,'READ_ROLE'),
	 (352,52,'WRITE_ROLE'),
	 (415,1,'READ_ORDER'),
	 (416,1,'DELETE_ORDER');
INSERT INTO application."permission" (id,role_id,"name") VALUES
	 (417,1,'READ_VENDOR'),
	 (418,1,'DELETE_VENDOR'),
	 (419,1,'READ_USER'),
	 (420,1,'DELETE_USER'),
	 (421,1,'WRITE_PRODUCT'),
	 (422,1,'READ_PRODUCT'),
	 (423,1,'DELETE_PRODUCT');
INSERT INTO application."user" (id,role_id,email,"name","password") VALUES
	 (53,1,'a.rodriguez@gmail.com','ARodriguez','$2a$10$XpS.Tdd/M.5VT20RlEmKT.6y6mA.FUoOjtWYuiCkRXGvNFns8yZ2W'),
	 (103,52,'m.grayson@gmail.com','MGrayson','$2a$10$kWAI1GhKvB8PZsco5CwOSusUaofSGqlZ3JMFI9FVtXsT.FO3aCD92');
