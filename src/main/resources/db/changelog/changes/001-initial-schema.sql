CREATE SCHEMA IF NOT EXISTS w2m;

CREATE TABLE "starship"
(
"id" BigSerial NOT NULL,
"name" varchar(20),
"movie" varchar(20)
) WITH (autovacuum_enabled=true);

COMMENT ON TABLE "starship" IS 'Table for persisting starships';

COMMENT ON COLUMN "starship"."id" IS 'Unique example identifier';

COMMENT ON COLUMN "starship"."name" IS 'Name of the starship';

COMMENT ON COLUMN "starship"."movie" IS 'Name of the movie in which the starship appears';

ALTER TABLE "starship" ADD CONSTRAINT "starship_PK1" PRIMARY KEY ("id");
