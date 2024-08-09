ALTER TABLE plan.itineraries
ADD COLUMN parent_id bigint;

ALTER TABLE plan.itineraries
ADD CONSTRAINT fk_itineraries_parent_id
    FOREIGN KEY (parent_id)
    REFERENCES plan.itineraries(id) ON UPDATE NO ACTION ON DELETE CASCADE;