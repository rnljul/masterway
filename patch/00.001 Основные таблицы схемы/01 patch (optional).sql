PROMPT rename
ALTER INDEX st_id RENAME TO rs_st_pk

BEGIN   
  FOR res IN
  (
    SELECT 'ALTER ' || object_type || ' ' || object_name || ' RENAME TO ' || 'RS_' || SUBSTR( object_name, 4)  cmd
      FROM user_objects
      WHERE regexp_like (object_name, '^MW_.+$')
  )
  LOOP
    EXECUTE IMMEDIATE res.cmd;
  END LOOP;      

  FOR res IN
  (
    SELECT 'ALTER TABLE ' || c.TABLE_NAME || ' RENAME CONSTRAINT ' || c.constraint_name || ' TO ' || 'RS_' || SUBSTR( c.constraint_name, 4) cmd
      FROM user_constraints c
      WHERE regexp_like (constraint_name, '^MW_.+$')
  )
  LOOP
    EXECUTE IMMEDIATE res.cmd;
  END LOOP; 
END;       
/
