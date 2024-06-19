-- ********************************************************************************
-- This script creates the database users and grants them the necessary permissions
-- ********************************************************************************

CREATE USER indoor_owner
WITH PASSWORD 'indoorpassword';

GRANT ALL
ON ALL TABLES IN SCHEMA public
TO indoor_owner;

GRANT ALL
ON ALL SEQUENCES IN SCHEMA public
TO indoor_owner;

CREATE USER indoor_appuser
WITH PASSWORD 'indoorpassword';

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public
TO indoor_appuser;

GRANT USAGE, SELECT
ON ALL SEQUENCES IN SCHEMA public
TO indoor_appuser;
