package com.example.viagens.data.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.viagens.data.model.User
import com.example.viagens.data.model.Viagem
import com.example.viagens.data.model.dao.UserDao
import com.example.viagens.data.model.dao.ViagemDao

@Database(entities = [User::class, Viagem::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun viagemDao(): ViagemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "viagens_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // Migração de versão 1 para 2 (adiciona imagemUri)
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE viagem ADD COLUMN imagemUri TEXT")
            }
        }

        // Migração de versão 2 para 3 (remove custo)
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Criar tabela temporária sem a coluna custo
                database.execSQL("""
                    CREATE TABLE viagem_temp (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        destino TEXT NOT NULL,
                        dataInicio TEXT NOT NULL,
                        dataFim TEXT NOT NULL,
                        descricao TEXT NOT NULL,
                        rating REAL NOT NULL,
                        imagemUri TEXT
                    )
                """)
                // Copiar dados da tabela original para a temporária
                database.execSQL("""
                    INSERT INTO viagem_temp (id, destino, dataInicio, dataFim, descricao, rating, imagemUri)
                    SELECT id, destino, dataInicio, dataFim, descricao, rating, imagemUri FROM viagem
                """)
                // Remover tabela original
                database.execSQL("DROP TABLE viagem")
                // Renomear tabela temporária
                database.execSQL("ALTER TABLE viagem_temp RENAME TO viagem")
            }
        }
    }
}