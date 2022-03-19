package util.database

import org.springframework.stereotype.Component
import util.database.Connection.executeQuery
import util.database.Connection.executeScalar
import util.database.Connection.executeUpdate
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

@Component
class DataBase(
    private val url: String,
    private val username: String,
    private val password: String,
) {

    fun clear() {
        val tableNames =
            executeQuery("select table_name from information_schema.tables where table_schema = 'public'") {
                getString(1)
            }
        tableNames.forEach { tableName ->
            executeUpdate("truncate table $tableName cascade")
        }
    }

    fun inTransaction(execute: Connection.() -> Unit) {
        val connection = DriverManager.getConnection(url, username, password)
        connection.use {
            connection.autoCommit = false
            execute(it)
            connection.commit()
        }
    }

    fun executeUpdate(sql: String, vararg parameters: Any): Int {
        return createConnection().use {
            it.executeUpdate(sql, *parameters)
        }
    }

    fun <T> executeQuery(sql: String, vararg parameters: Any, mapRow: ResultSet.() -> T): List<T> {
        return createConnection().use {
            val result = it.executeQuery(sql, *parameters) { mapRow() }
            result
        }
    }

    fun <T> executeScalar(sql: String, vararg parameters: Any): T {
        return createConnection().use {
            it.executeScalar(sql, *parameters)
        }
    }


    private fun createConnection() = DriverManager.getConnection(url, username, password)

}