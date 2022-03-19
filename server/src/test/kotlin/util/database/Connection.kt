package util.database

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

object Connection {
    fun <T> Connection.executeScalar(sql: String, vararg parameters: Any): T {
        return executeQuery(sql, *parameters) {
            getObject(1) as T
        }.first()
    }

    fun Connection.executeUpdate(sql: String, vararg parameters: Any): Int {
        return executeStatement(sql, *parameters) {
            executeUpdate()
        }
    }

    fun <T> Connection.executeQuery(
        sql: String,
        vararg parameters: Any,
        mapRow: ResultSet.() -> T
    ): List<T> =
        executeStatement(sql, *parameters) {
            val resultSet = executeQuery()
            val result = mutableListOf<T>()
            while (resultSet.next()) {
                result.add(mapRow(resultSet))
            }
            result
        }

    private fun <T> Connection.executeStatement(
        sql: String,
        vararg parameters: Any,
        execute: PreparedStatement.() -> T
    ): T =
        prepareStatement(sql).use {
            parameters.forEachIndexed { index, value ->
                it.setObject(index + 1, value)
            }
            execute(it)
        }
}