/* View: SOLUTIONS_VIEW */
CREATE VIEW SOLUTIONS_VIEW(
    SOLUTION_ID,
    SOLUTION_NAME,
    SOLUTION_INFO,
    HOSTNAME,
    DB_PATH,
    USERNAME,
    LM_DATE)
AS
select
s.solution_id,
s.solution_name,
s.solution_info,
s.hostname,
s.db_path,
s.username,
s.lm_date
from solutions s
;

/* View: PROJECTS_VIEW */
CREATE VIEW PROJECTS_VIEW(
    PROJECT_ID,
    PROJECT_NAME,
    SOLUTION_NAME,
    PROJECT_INFO,
    ENV,
    LM_DATE,
    TASK_COUNT)
AS
select
p.project_id,
p.project_name,
s.solution_name,
p.project_info,
p.env,
p.lm_date,
count (*) task_id
from projects p,
solutions s,
tasks t
where s.solution_id = p.solution_id
and t.project_id = p.project_id
group by
p.project_id,
p.project_name,
s.solution_name,
p.project_info,
p.env,
p.lm_date
;

/* View: TASKS_VIEW */
CREATE VIEW TASKS_VIEW(
    TASK_ID,
    PROJECT_NAME,
    PLUGIN_NAME,
    TASK_NR,
    TASK_NAME,
    TASK_INFO,
    PLUGIN_SETTINGS,
    PLUGIN_RESULT,
    START_TIME,
    STOP_TIME,
    STATUS,
    LM_DATE)
AS
select t.task_id,
p.project_name,
pl.plugin_name,
t.task_nr,
t.task_name,
t.task_info,
t.plugin_settings,
t.plugin_result,
t.start_time,
t.stop_time,
t.status,
t.lm_date
from tasks t,
projects p,
plugins pl
where
t.project_id = p.project_id
and t.plugin_id = pl.plugin_id
;

/* View: PLUGINS_VIEW */
CREATE VIEW PLUGINS_VIEW(
    PLUGIN_ID,
    PLUGIN_NAME,
    LOCATION,
    PLUGIN_INFO,
    LM_DATE)
AS
select pl.plugin_id,
pl.plugin_name,
pl.location,
pl.plugin_info,
pl.lm_date
from plugins pl
;


