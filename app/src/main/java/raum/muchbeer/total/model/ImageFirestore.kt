package raum.muchbeer.total.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "takeToFirestore")
data class ImageFirestore(
        @PrimaryKey(autoGenerate = false)
        val fileName : String,
        val imageUrl : String,
        val timestamp : String
) {
}