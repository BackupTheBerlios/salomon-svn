/******************************************************************************/
/****                                Views                                 ****/
/******************************************************************************/


/* View: VIEW_DATA_BASE_INFO */
CREATE VIEW VIEW_DATA_BASE_INFO(
    FIELD_NAME,
    RELATION_NAME,
    FIELD_ID,
    TYPE_NAME)
AS
select rf.rdb$field_name, rf.rdb$relation_name, rf.rdb$field_id, t.rdb$type_name
from
rdb$relation_fields rf,
rdb$relations r,
rdb$fields f,
rdb$types t
where rf.rdb$relation_name = r.rdb$relation_name
and r.rdb$relation_name not like '%RDB%'
and rf.rdb$field_source = f.rdb$field_name
and f.rdb$field_type = t.rdb$type
and t.rdb$field_name = 'RDB$FIELD_TYPE'
;
