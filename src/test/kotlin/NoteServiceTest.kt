import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class NoteServiceTest {
    @Test
    fun add() {
        val testNote = Note(fromId = 1, date = 27112022)
        val (result) = NoteService.add(testNote)
        assertEquals(1, result)
    }

    @Before
    fun clearBeforeCreateCommentTrue() {
        NoteService.clear()
    }

    @Test
    fun createCommentTrue() {
        val testComment = Comment(id = 1, fromId = 11, date = 28112022, text = "test comment")
        val testNote = Note(fromId = 1, date = 27112022)
        NoteService.add(testNote)
        val result = NoteService.createComment(1, testComment)
        assertEquals(true, result)
    }

    @Test
    fun createCommentFalse() {
        val testComment = Comment(id = 1, fromId = 11, date = 28112022, text = "test comment")
        val testNote = Note(fromId = 1, date = 27112022)
        NoteService.add(testNote)
        NoteService.createComment(1, testComment)
        val result = NoteService.createComment(1, testComment)
        assertEquals(false, result)
    }

    @Test
    fun deleteTrue() {
        val testNote = Note(fromId = 1, date = 27112022)
        NoteService.add(testNote)
        val result = NoteService.delete(1)
        assertEquals(true, result)
    }

    @Test
    fun deleteFalse() {
        val testNote = Note(fromId = 1, date = 27112022)
        NoteService.add(testNote)
        val result = NoteService.delete(2)
        assertEquals(false, result)
    }

    @Test
    fun deleteCommentTrue() {
        val testNote = Note(fromId = 1, date = 27112022)
        val testComment = Comment(id = 1, fromId = 11, date = 28112022, text = "test comment")
        NoteService.add(testNote)
        NoteService.createComment(1, testComment)
        val result = NoteService.deleteComment(1, 1)
        assertEquals(true, result)
    }

    @Test
    fun deleteCommentFalse() {
        val testNote = Note(fromId = 1, date = 27112022)
        val testComment = Comment(id = 1, fromId = 11, date = 28112022, text = "test comment")
        NoteService.add(testNote)
        NoteService.createComment(1, testComment)
        NoteService.deleteComment(1, 1)
        val result = NoteService.deleteComment(1, 1)
        assertEquals(false, result)
    }

    @Test
    fun edit() {
        val testNote = Note(fromId = 1, date = 27112022)
        NoteService.add(testNote)
        val result = NoteService.edit(1, "new text")
        assertEquals(Note(id = 1, fromId = 1, date = 27112022, text = "new text"), result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun shouldThrowEdit() {
        val testNote = Note(fromId = 1, date = 27112022)
        NoteService.add(testNote)
        val noteId = 3
        NoteService.edit(noteId, "new text") ?: throw NoteNotFoundException("No note with id: $noteId")
    }

    @Test
    fun editCommentTrue() {
        val testNote = Note(fromId = 1, date = 27112022)
        val testComment = Comment(id = 1, fromId = 11, date = 28112022, text = "test comment")
        NoteService.add(testNote)
        NoteService.createComment(1, testComment)
        val result = NoteService.editComment(1, 1, "new text")
        assertEquals(true, result)
    }

    @Test
    fun editCommentFalse() {
        val testNote = Note(fromId = 1, date = 27112022)
        val testComment = Comment(id = 1, fromId = 11, date = 28112022, text = "test comment")
        NoteService.add(testNote)
        NoteService.createComment(1, testComment)
        NoteService.deleteComment(1, 1)
        val result = NoteService.editComment(1, 1, "new text")
        assertEquals(false, result)
    }

    @Test
    fun getById() {
        val testNote = Note(fromId = 1, date = 27112022)
        NoteService.add(testNote)
        val result = NoteService.getById(1)
        assertEquals(Note(id = 1, fromId = 1, date = 27112022), result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun shouldThrowGetById() {
        val testNote = Note(fromId = 1, date = 27112022)
        NoteService.add(testNote)
        val noteId = 2
        NoteService.getById(noteId) ?: throw NoteNotFoundException("No note with id: $noteId")
    }

    @Test
    fun getComments() {
        val testNote = Note(fromId = 1, date = 27112022)
        val testComment = Comment(id = 1, fromId = 11, date = 28112022, text = "test comment")
        NoteService.add(testNote)
        NoteService.createComment(1, testComment)
        val tempList = NoteService.getComments(1)
        val result = NoteService.getComments(1)
        assertEquals(tempList, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun shouldThrowGetComments() {
        val testNote = Note(fromId = 1, date = 27112022)
        val testComment = Comment(id = 1, fromId = 11, date = 28112022, text = "test comment")
        NoteService.add(testNote)
        NoteService.createComment(1, testComment)
        val noteId = 2
        NoteService.getComments(noteId) ?: throw NoteNotFoundException("No note with id: $noteId")
    }

    @Test
    fun restoreCommentTrue() {
        val testNote = Note(fromId = 1, date = 27112022)
        val testComment = Comment(id = 1, fromId = 11, date = 28112022, text = "test comment")
        NoteService.add(testNote)
        NoteService.createComment(1, testComment)
        val result = NoteService.restoreComment(1,1)
        assertEquals(true, result)
    }

    @Test
    fun restoreCommentFalse() {
        val testNote = Note(fromId = 1, date = 27112022)
        val testComment = Comment(id = 1, fromId = 11, date = 28112022, text = "test comment")
        NoteService.add(testNote)
        NoteService.createComment(1, testComment)
        NoteService.deleteComment(1, 1)
        val result = NoteService.restoreComment(1,1)
        assertEquals(false, result)
    }
}