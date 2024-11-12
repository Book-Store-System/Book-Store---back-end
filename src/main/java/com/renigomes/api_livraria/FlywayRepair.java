package com.renigomes.api_livraria;

import org.flywaydb.core.Flyway;

public class FlywayRepair {
    public static void main(String[] args) {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/bookstore_db", "postgres", "rngazrcb")
                .load();
        flyway.repair();
    }
}
