package my.ilya40umov.azblobstore.todo

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/todos")
class TodoController(
    private val todoService: TodoService
) {

    @PostMapping("/{name}")
    fun saveTodo(@PathVariable name: String, @RequestBody todoText: String?): ResponseEntity<String> {
        todoService.saveTodo(name, todoText ?: "no text")
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{name}")
    fun getTodo(@PathVariable name: String): ResponseEntity<String> {
        val todo: String = todoService.getTodo(name) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(todo)
    }

}