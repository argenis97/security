INSERT INTO application."role" (id,"name") VALUES
	 (1,'ADMIN'),
	 (52,'USER');
INSERT INTO application."permission" (id,role_id,"name") VALUES
	 (1,1,'WRITE_ORDER'),
	 (152,1,'WRITE_VENDOR'),
	 (202,1,'WRITE_USER'),
	 (252,52,'WRITE_ORDER');
INSERT INTO application."user" (id,role_id,email,"name","password") VALUES
	 (53,1,'a.rodriguez@gmail.com','ARodriguez','$2a$10$XpS.Tdd/M.5VT20RlEmKT.6y6mA.FUoOjtWYuiCkRXGvNFns8yZ2W'),
	 (103,52,'m.grayson@gmail.com','MGrayson','$2a$10$kWAI1GhKvB8PZsco5CwOSusUaofSGqlZ3JMFI9FVtXsT.FO3aCD92');
