package pe.edu.cibertec.pago_service_grupo01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.cibertec.pago_service_grupo01.dto.PagoDTO;
import pe.edu.cibertec.pago_service_grupo01.service.PagoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<PagoDTO> obtenerPagoPorPedido(@PathVariable Long idPedido) {

        PagoDTO pago = pagoService.obtenerPagoPorPedido(idPedido);

        if (pago == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pago);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PagoDTO>> obtenerPagosPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(pagoService.obtenerPagosPorCliente(clienteId));
    }
}
