SELECT DISTINCT REPLACE(INITCAP(c.table_name), '_', '') || '.' ||
                LOWER(SUBSTR(REPLACE(INITCAP(c.column_name), '_', ''),1,1))||
                SUBSTR(REPLACE(INITCAP(c.column_name), '_', ''),2) || '=' ||
                c.comments,
                s.tname,
                s.colno
  FROM sys.col s, user_col_comments c,all_tables t
 WHERE s.tname = c.table_name
   AND s.cname = c.column_name
   AND t.table_name = s.tname
   AND t.owner = 'USER_NAME' --用户名
 ORDER BY s.tname, s.colno;
