package com.example.nyc_schools_test.model.local

import androidx.room.*


@Dao
interface SchoolDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalSChool(schoolListEntity: List<SchoolListEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalSat(schoolSatEntity: List<SchoolSatEntity>)

    @Query("DELETE FROM SchoolListEntity")
    suspend fun deleteAllSchoolLocalItem()

    @Query("DELETE FROM SchoolSatEntity")
    suspend fun deleteAllSatLocalItem()

    @Query("SELECT * FROM SchoolListEntity order by id")
    suspend fun getAllCachedSchools(): List<SchoolListEntity>

    @Query("SELECT * FROM SchoolSatEntity order by id")
    suspend fun getAllCachedSat(): List<SchoolSatEntity>

}


@Database(
    version = 1,
    entities = [SchoolSatEntity::class,
        SchoolListEntity::class],
    exportSchema = false
)
abstract class SchoolDB: RoomDatabase() {
    abstract fun schoolDao(): SchoolDao
}