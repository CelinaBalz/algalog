package br.com.algaworks.algalog.api.controller;

import br.com.algaworks.algalog.domain.model.Cliente;
import br.com.algaworks.algalog.domain.repository.ClienteRepository;
import br.com.algaworks.algalog.domain.service.CatalogoClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteRepository clienteRepository;
    private CatalogoClienteService catalogoClienteService;

    @GetMapping
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long clientId) {
        return clienteRepository.findById(clientId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    /*Customizar resultado*/
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
        return catalogoClienteService.salvar(cliente);
    }

    @PutMapping ("/{clientId}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long clientId,
                                             @Valid @RequestBody Cliente cliente) {
        if (!clienteRepository.existsById(clientId)) {
            return ResponseEntity.notFound().build();
        }
            cliente.setId(clientId);
            cliente = catalogoClienteService.salvar(cliente);

            return ResponseEntity.ok(cliente);
        }

        @DeleteMapping ("/{clientId}")
        public ResponseEntity<Void> remover(@PathVariable Long clientId){
             if (!clienteRepository.existsById(clientId)) {
                return ResponseEntity.notFound().build();
             }
             catalogoClienteService.excluir(clientId);

             return ResponseEntity.noContent().build();
        }

    }



