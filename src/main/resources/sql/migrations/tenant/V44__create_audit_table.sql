CREATE TABLE m_audit_source (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    action_name VARCHAR(100),
    entity_name VARCHAR(100),
    resource_id bigint(20),
    data_as_json TEXT,
    maker_id bigint(20) NOT NULL,
    made_on_date TIMESTAMP NOT NULL,
    processing_result VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT `fk_audit_source_maker` FOREIGN KEY (`maker_id`) REFERENCES `m_appuser` (`id`)
);
