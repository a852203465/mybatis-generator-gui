PRAGMA foreign_keys = false;

-- ----------------------------
-- Table structure for db_source
-- ----------------------------
CREATE TABLE IF NOT EXISTS "db_source" (
    "id" INTEGER(20) NOT NULL,
    "name" TEXT,
    "code" TEXT,
    "db_type" TEXT,
    "jdbc_url" TEXT,
    "driver_class_name" TEXT,
    "ip" TEXT,
    "port" TEXT,
    "database_name" TEXT,
    "schema_name" TEXT,
    "username" TEXT,
    "password" TEXT,
    "created_user" TEXT,
    "created_time" integer,
    "updated_user" text,
    "updated_time" integer,
    "description" TEXT,
    PRIMARY KEY ("id")
);

-- ----------------------------
-- Table structure for generator_config_info
-- ----------------------------

CREATE TABLE IF NOT EXISTS "generator_config_info" (
    "id" INTEGER(20) NOT NULL,
    "name" TEXT,
    "code" TEXT,
    "project_path" TEXT,
    "author" TEXT,
    "java_source_path" TEXT,
    "parent_path" TEXT,
    "entity_package" TEXT,
    "mapper_package" TEXT,
    "mapper_suffix_name" TEXT,
    "java_resource_path" TEXT,
    "mapper_xml_path" TEXT,
    "gen_vo" integer,
    "gen_bae_service" integer,
    "gen_dto" integer,
    "gen_query" integer,
    "use_lombok_plugin" integer,
    "override_xml" integer,
    "use_schema_prefix" integer,
    PRIMARY KEY ("id")
);

PRAGMA foreign_keys = true;
