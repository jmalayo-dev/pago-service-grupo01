package pe.edu.cibertec.pago_service_grupo01.service;

import org.springframework.stereotype.Service;
import pe.edu.cibertec.pago_service_grupo01.dto.PagoDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PagoService {

    private final Map<Long, List<Long>> pedidosPorCliente = Map.of(
        1L, List.of(100L, 102L),
        2L, List.of(101L),
        3L, List.of(103L)
    );

    private final List<PagoDTO> listaPagos = new ArrayList<>();

    public PagoService() {
        listaPagos.add(
                new PagoDTO(
                    1L,
                    100L,
                    "TARJETA_CREDITO",
                    250.00,
                    LocalDateTime.of(2026, 10, 5, 14, 30, 0),
                    "AUTH-20261005-001",
                    "APROBADO"
                )
        );

        listaPagos.add(
                new PagoDTO(
                    2L,
                    101L,
                    "TRANSFERENCIA",
                    89.90,
                    LocalDateTime.of(2026, 10, 6, 10, 15, 0),
                    "AUTH-20261006-002",
                    "APROBADO"
                )
        );

        listaPagos.add(
                new PagoDTO(
                    3L,
                    102L,
                    "TARJETA_CREDITO",
                    450.00,
                    LocalDateTime.of(2026, 10, 7, 16, 45, 0),
                    "AUTH-20261007-003",
                    "RECHAZADO"
                )
        );

        listaPagos.add(
                new PagoDTO(
                    4L,
                    103L,
                    "TRANSFERENCIA",
                    120.50,
                    LocalDateTime.of(2026, 10, 8, 9, 0, 0),
                    "AUTH-20261008-004",
                    "APROBADO"
                )
        );

        listaPagos.add(
                new PagoDTO(
                    5L,
                    100L,
                    "TARJETA_CREDITO",
                    175.00,
                    LocalDateTime.of(2026, 10, 9, 11, 20, 0),
                    "AUTH-20261009-005",
                    "RECHAZADO"
                )
        );
    }

    public PagoDTO obtenerPagoPorPedido(Long idPedido) {
        return listaPagos.stream()
                .filter(p -> idPedido.equals(p.getIdPedido()))
                .findFirst()
                .orElse(null);
    }

    public List<PagoDTO> obtenerPagosPorCliente(Long clienteId) {
        List<Long> pedidosCliente = pedidosPorCliente.getOrDefault(clienteId, List.of());
        return listaPagos.stream()
                .filter(p -> pedidosCliente.contains(p.getIdPedido()))
                .toList();
    }
}
