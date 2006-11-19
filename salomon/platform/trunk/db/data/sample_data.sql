/* Procedure inserts sample data to the Salomon DB */
create procedure insert_sample_data
as
declare variable tmp_solution_id integer;
declare variable tmp_plugin_id integer;
begin

	/* insert solution object */
	select gen_id(gen_solution_id, 1)
	from rdb$database
	into :tmp_solution_id;

	INSERT INTO SOLUTIONS (SOLUTION_ID, SOLUTION_NAME, SOLUTION_INFO, HOSTNAME, DB_PATH, USERNAME, PASSWD, C_DATE, LM_DATE)
	VALUES (:tmp_solution_id, 'Persons', 'Sample solution.', '', 'PERSONS.GDB', 'SYSDBA', 'masterkey', current_timestamp, current_timestamp);


	/* Insert plugins */
	select gen_id(gen_plugin_id, 1)
	from rdb$database
	into :tmp_plugin_id;

	INSERT INTO PLUGINS (PLUGIN_ID, PLUGIN_NAME, LOCATION, PLUGIN_INFO, PLUGIN_TYPE, C_DATE, LM_DATE)
	VALUES (:tmp_plugin_id, 'AttributeSet Creator', 'AttributeSetCreator.jar', 'Plugin creates attribute set.', 'L', current_timestamp, current_timestamp);


	select gen_id(gen_plugin_id, 1)
	from rdb$database
	into :tmp_plugin_id;
	INSERT INTO PLUGINS (PLUGIN_ID, PLUGIN_NAME, LOCATION, PLUGIN_INFO, PLUGIN_TYPE, C_DATE, LM_DATE)
	VALUES (:tmp_plugin_id, 'DataSet Creator', 'DataSetCreator.jar', 'Plugin creates dataset basing on conditons provided by the User.', 'L', current_timestamp, current_timestamp);


	select gen_id(gen_plugin_id, 1)
	from rdb$database
	into :tmp_plugin_id;
	INSERT INTO PLUGINS (PLUGIN_ID, PLUGIN_NAME, LOCATION, PLUGIN_INFO, PLUGIN_TYPE, C_DATE, LM_DATE)
	VALUES (:tmp_plugin_id, 'Random dataset creator', 'RandomDSCreator.jar', 'Creates random dataset.', 'L', current_timestamp, current_timestamp);
	
end;

/* Double slash separates statements */
//

/* Execution of the procedure above */
execute procedure insert_sample_data;

//

/* Dropping after execution */
drop procedure insert_sample_data;

