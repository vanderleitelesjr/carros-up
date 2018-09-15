package br.com.livroandroid.carros.domain
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "carro")
class Carro : Serializable {
    @PrimaryKey
    var id: Long = 0

    var tipo = ""
    var nome = ""
    var desc = ""
    var urlFoto = ""
    var urlInfo = ""
    var urlVideo = ""
    var latitude = ""
    var longitude = ""

    override fun toString(): String {
        return "Carro(nome='$nome')" }
}