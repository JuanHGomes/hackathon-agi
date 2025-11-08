package com.example.demo.controller;

import com.example.demo.dto.request.TaskRequest;
import com.example.demo.dto.response.TaskResponse;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

// 1. Tag para o Controller
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Tag(name = "Gerenciamento de Tarefas", description = "Operações de CRUD, atribuição e mudança de status de Tarefas.")
public class TaskController {

    public final TaskService taskService;

    @Operation(summary = "Cria uma nova tarefa", description = "Endpoint que permite a criação de uma tarefa. Requer as autoridades ADMIN ou MANAGER.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa criada com sucesso", content = @Content(schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso Negado. Usuário não tem as autoridades necessárias."),
            @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos.")
    })
    @PostMapping("/create")
    public ResponseEntity<TaskResponse> createTask (@RequestBody TaskRequest taskRequest){
        return ResponseEntity.ok(TaskMapper.toResponse(taskService.create(taskRequest)));
    }

    @Operation(summary = "Atribui uma tarefa a um usuário", description = "Define qual usuário será responsável pela execução de uma tarefa. Requer as autoridades ADMIN ou MANAGER.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atribuído com sucesso à tarefa", content = @Content(schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "404", description = "ID de Usuário ou Tarefa não encontrado.")
    })
    @PostMapping("/setUser/{idUser}/{idTask}")
    public ResponseEntity<TaskResponse> setUser(
            @Parameter(description = "ID (UUID) do usuário a ser atribuído à tarefa") @PathVariable UUID idUser,
            @Parameter(description = "ID (Long) da tarefa") @PathVariable Long idTask) {
        return ResponseEntity.ok(TaskMapper.toResponse(taskService.setTaskUser(idTask, idUser)));
    }


    @Operation(summary = "Lista todas as tarefas", description = "Retorna uma lista completa de todas as tarefas cadastradas. Requer as autoridades ADMIN ou MANAGER.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso", content = @Content(schema = @Schema(implementation = TaskResponse.class)))
    })
    @GetMapping()
    public List<TaskResponse> listTasks(){
        return taskService.listAll().stream().map(TaskMapper::toResponse).toList();
    }


    @Operation(summary = "Lista tarefas pendentes e em andamento por usuário", description = "Retorna todas as tarefas de um usuário específico que estão no status PENDENTE e EM ANDAMENTO.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tarefas pendentes e em andamento retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    })
    @GetMapping("/listByUserAndStatus/{userId}")
    public List<TaskResponse> listByUserAndStatus(
            @Parameter(description = "ID (UUID) do usuário") @PathVariable UUID userId){
        return taskService.listByUserAndStatusPending(userId).stream().map(TaskMapper::toResponse).toList();
    }


    @Operation(summary = "Altera o status de uma tarefa", description = "Atualiza o status de uma tarefa (ex: PENDENTE, EM_ANDAMENTO, CONCLUIDO).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status alterado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada."),
            @ApiResponse(responseCode = "400", description = "Status inválido fornecido.")
    })
    @PostMapping("/changeStatus/{idTask}")
    public TaskResponse changeStats(
            @Parameter(description = "ID (Long) da tarefa") @PathVariable Long idTask,
            @Parameter(description = "Novo status da tarefa (String)") @RequestParam String status){
        return TaskMapper.toResponse(taskService.changeStatus(idTask, status));
    }

    @Operation(summary = "Lista tarefas 'Em Andamento' por usuário", description = "Retorna todas as tarefas de um usuário específico que estão no status EM_ANDAMENTO.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tarefas 'Em Andamento' retornada com sucesso")
    })
    @GetMapping("/tasksByUserEmAndamento/{userId}")
    public List<TaskResponse> tasksByUserSatausEmAndamento(
            @Parameter(description = "ID (UUID) do usuário") @PathVariable UUID userId){
        return taskService.listEmAndamentoTasks(userId).stream().map(TaskMapper::toResponse).toList();
    }


    @Operation(summary = "Lista tarefas 'Concluídas' por usuário", description = "Retorna todas as tarefas de um usuário específico que estão no status CONCLUIDO.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tarefas 'Concluídas' retornada com sucesso")
    })
    @GetMapping("/tasksByUserConcluido/{userId}")
    public List<TaskResponse> tasksByUserStatusConcluido(
            @Parameter(description = "ID (UUID) do usuário") @PathVariable UUID userId){
        return taskService.listConcluidoTasks(userId).stream().map(TaskMapper::toResponse).toList();
    }
}