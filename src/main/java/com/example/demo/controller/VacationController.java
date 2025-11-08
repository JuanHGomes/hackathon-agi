package com.example.demo.controller;

import com.example.demo.dto.request.VacationRequest;
import com.example.demo.dto.response.TaskResponse;
import com.example.demo.dto.response.VacationHistoryResponse;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.mapper.VacationMapper;
import com.example.demo.service.VacationHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "Férias e Histórico", description = "Gerenciamento de histórico de férias e transferência de tarefas.")
@RestController
@RequestMapping("/vacation")
@RequiredArgsConstructor
public class VacationController {
    private final VacationHistoryService vacationService;

    @Operation(
            summary = "Inicia o histórico de férias e transfere tarefas",
            description = "Registra um novo período de férias para o usuário de origem (originUserId) e transfere todas as tarefas 'Em Andamento' e 'Pendentes' dele para o usuário atual (currentUserId). Retorna a lista das tarefas que foram transferidas.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "IDs dos usuários envolvidos e datas de início/fim das férias.",
                    required = true,
                    content = @Content(schema = @Schema(implementation = VacationRequest.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Transferência de tarefas e registro de férias concluídos com sucesso. Retorna as tarefas afetadas.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TaskResponse.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário de origem ou usuário atual não encontrado."),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datas de férias inválidas ou conflito de dados.")
            }
    )
    @PostMapping
    public List<TaskResponse> newVacatio(@RequestBody VacationRequest request){
        return vacationService.newVacationHistory(request).stream()
                .map(TaskMapper::toResponse).toList();
    }
}