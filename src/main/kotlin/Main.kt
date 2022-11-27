data class Note(
    var id: Int = 0,
    val fromId: Int,
    val date: Int,
    val text: String = "empty note",
    var comment: MutableList<Comment> = emptyList<Comment>().toMutableList()
)

data class Comment(
    var id: Int,
    val fromId: Int,
    val date: Int,
    var text: String = "empty comment",
    var deleted: Boolean = false
)

object NoteService {
    private var noteIdCounter = 0
    private var notes: MutableList<Note> = emptyList<Note>().toMutableList()

    fun add(note: Note) {
        noteIdCounter += 1
        note.id = noteIdCounter
        notes.add(note)
    }

    fun createComment(noteId: Int, comment: Comment) {
        val tempNote = notes[noteId - 1]
        if (comment !in tempNote.comment) {
            tempNote.comment.add(comment)
        }
        notes[noteId - 1] = tempNote
    }

    fun delete(noteId: Int) {
        notes.removeAt(noteId - 1)
    }

    fun deleteComment(noteId: Int, commentId: Int) {
        notes[noteId - 1].comment[commentId - 1].deleted = true
    }

    fun edit(noteId: Int, text: String) {
        val tempNote = notes[noteId - 1].copy(text = text)
        notes[noteId - 1] = tempNote
    }

    fun editComment(noteId: Int, commentId: Int, text: String) {
        if (!notes[noteId - 1].comment[commentId - 1].deleted) { //если комментарий не удалён
            notes[noteId - 1].comment[commentId - 1].text = text
        }
    }

    fun getById(noteId: Int): Note {
        return notes[noteId - 1]
    }

    fun getComments(noteId: Int): List<Comment> {
        val existingComments: MutableList<Comment> = emptyList<Comment>().toMutableList()
        notes[noteId - 1].comment.forEach() { item ->
            if (!item.deleted) {
                existingComments.add(item) //возвращаем только неудалённые комментарии
            }
        }
        return existingComments
    }

    fun restoreComment(noteId: Int, commentId: Int) {
        if (!notes[noteId - 1].comment[commentId - 1].deleted) {
            notes[noteId - 1].comment[commentId - 1].deleted = true
        }
    }

    fun printNotes() {
        println(notes)
    }
}

fun main() {
    val note1 = Note(fromId = 1, date = 27112022)
    val note2 = Note(fromId = 2, date = 27112022)

    val comment1 = Comment(id = 1, fromId = 11, date = 28112022, text = "Comment 1")
    val comment2 = Comment(id = 2, fromId = 22, date = 29112022, text = "Comment 2")

    NoteService.add(note1)
    NoteService.add(note2)
    NoteService.createComment(1, comment1)
    NoteService.createComment(1, comment2)
    NoteService.edit(1, "new text")
    NoteService.editComment(1, 1, "change comment")
    NoteService.deleteComment(1, 1)
    println(NoteService.getComments(1))
    println(NoteService.getById(2))

    NoteService.delete(2)
    NoteService.printNotes()
}
