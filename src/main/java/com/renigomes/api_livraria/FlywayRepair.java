package com.renigomes.api_livraria;

import org.flywaydb.core.Flyway;
/**
 * flyway.repair(): Esse método é usado para reparar o metadado do Flyway no banco de dados. Especificamente, ele limpa entradas erradas e corrige a tabela de histórico de migrações (flyway_schema_history). Isso é útil quando ocorrem problemas durante a execução de migrações e você precisa ajustar o estado do histórico de migrações sem executar novamente as migrações.
 */
public class FlywayRepair {
    public static void main(String[] args) {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://172.17.0.2:5432/bookstorebdtest", "postgres", "rngazrcb")
                .load();
        flyway.repair();
    }
}
