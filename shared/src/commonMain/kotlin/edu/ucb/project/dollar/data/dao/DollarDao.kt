package edu.ucb.project.dollar.data.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import edu.ucb.project.dollar.data.entity.DollarEntity

@Dao
interface DollarDao {
    @Query("SELECT * FROM dollars")
    suspend fun getList(): List<DollarEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dollar: DollarEntity)

    @Query("DELETE FROM dollars")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDollars(lists: List<DollarEntity>)
}