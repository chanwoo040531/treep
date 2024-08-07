create schema if not exists treep.plan;


-- Table: treep.plan.plans
CREATE TABLE IF NOT EXISTS "plan.plans" (
    "id"              INTEGER NOT NULL UNIQUE,
    "title"           VARCHAR,
    "budget"          DECIMAL,
    "start_date"      TIMESTAMPTZ,
    "end_date"        TIMESTAMPTZ,
    "created_at"      TIMESTAMPTZ,
    "last_updated_at" TIMESTAMPTZ,
    PRIMARY KEY ("id")
);

COMMENT ON TABLE "plan.plans" IS 'trip plans';

-- Table: treep.plan.itineraries
CREATE TABLE IF NOT EXISTS "plan.itineraries" (
    "id"              BIGINT NOT NULL UNIQUE,
    "plan_id"         BIGINT,
    "title"           VARCHAR,
    "description"     VARCHAR,
    "cost"            DECIMAL,
    "start_at"        TIMESTAMPTZ,
    "end_at"          TIMESTAMPTZ,
    "created_at"      TIMESTAMPTZ,
    "last_updated_at" TIMESTAMPTZ,
    PRIMARY KEY ("id")
);

COMMENT ON TABLE "plan.itineraries" IS 'itineraries of plan';


ALTER TABLE IF EXISTS "plan.itineraries"
ADD FOREIGN KEY ("plan_id") REFERENCES "plan.plans"("id") ON UPDATE NO ACTION ON DELETE CASCADE;