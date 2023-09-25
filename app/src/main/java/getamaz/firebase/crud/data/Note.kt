package getamaz.firebase.crud.data

data class Note(
    val noteTitle:String,
    val noteDescription:String,
    val noteId : String? = null
){
    constructor() : this("","")
}
