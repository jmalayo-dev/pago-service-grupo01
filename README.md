# pago-service-grupo01

Microservicio de procesamiento de pagos (Puerto **8072**). Se registra en Eureka como
`PAGO-SERVICE-GRUPO01` y se accede a través del API Gateway en `http://localhost:8070/api/v1/pagos/**`.

- Spring Boot 4.1.1 · Spring Cloud 2025.1.3 · Java 25
- Datos en memoria (no requiere base de datos)

## Endpoints

| Método | Ruta | Respuesta |
|---|---|---|
| GET | `/api/v1/pagos/pedido/{idPedido}` | `PagoDTO` del pedido (404 si no existe) |
| GET | `/api/v1/pagos/cliente/{clienteId}` | `List<PagoDTO>` del cliente (`[]` si no tiene) |

### PagoDTO

```json
{
  "idPago": 1,
  "idPedido": 100,
  "metodoPago": "TARJETA_CREDITO",
  "montoPagado": 250.0,
  "fechaPago": "2026-10-05T14:30:00",
  "codigoAutorizacion": "AUTH-20261005-001",
  "estadoPago": "APROBADO"
}
```

### Datos de prueba

| idPago | idPedido | clienteId | metodoPago | montoPagado | estadoPago |
|---|---|---|---|---|---|
| 1 | 100 | 1 | TARJETA_CREDITO | 250.00 | APROBADO |
| 2 | 101 | 2 | TRANSFERENCIA | 89.90 | APROBADO |
| 3 | 102 | 1 | TARJETA_CREDITO | 450.00 | RECHAZADO |
| 4 | 103 | 3 | TRANSFERENCIA | 120.50 | APROBADO |
| 5 | 100 | 1 | TARJETA_CREDITO | 175.00 | RECHAZADO |

> Si un pedido tiene varios pagos, `/pedido/{idPedido}` devuelve el primero de la lista.

## Cómo levantar

Desde la carpeta raíz `EVALUACIÓN DE LABORATORIO – T2`, en este orden (cada uno en su terminal):

```bash
# 1. Eureka
cd discovery-server-grupo01 && mvn spring-boot:run
# 2. Pago service
cd pago-service-grupo01 && mvn spring-boot:run
# 3. (Opcional, para probar Feign) Pedido service
cd pedido-service-grupo01 && mvn spring-boot:run
# 4. API Gateway
cd api-gateway-grupo01 && mvn spring-boot:run
```

También se puede desde IntelliJ ejecutando la clase `*Application` de cada proyecto.

Verifica en http://localhost:8761 que aparezca **PAGO-SERVICE-GRUPO01** (espera ~30 s
después de levantar para que el Gateway lo descubra).

## Pruebas con cURL

### 1. Directo al microservicio (8072)

```bash
curl http://localhost:8072/api/v1/pagos/pedido/100     # 200 - pago APROBADO de 250.0
curl http://localhost:8072/api/v1/pagos/pedido/102     # 200 - pago RECHAZADO
curl -i http://localhost:8072/api/v1/pagos/pedido/999  # 404 - no existe
curl http://localhost:8072/api/v1/pagos/cliente/1      # 200 - lista con 3 pagos
curl http://localhost:8072/api/v1/pagos/cliente/9      # 200 - []
```

### 2. A través del API Gateway (8070) — lo que pide el documento

```bash
curl http://localhost:8070/api/v1/pagos/pedido/101     # pago TRANSFERENCIA 89.9
curl http://localhost:8070/api/v1/pagos/cliente/3      # lista con 1 pago
```

### 3. Integración con pedido-service vía @FeignClient

pedido-service llama a este servicio internamente:

```bash
# pedido 100: monto 250.0 = pago APROBADO 250.0 -> estadoPedido "COMPLETADO"
curl http://localhost:8070/api/v1/pedidos/verificar-y-completar/100
# pedido 102: pago RECHAZADO -> estadoPedido sigue "PENDIENTE"
curl http://localhost:8070/api/v1/pedidos/verificar-y-completar/102
# consolidado del cliente 1: incluye historialPagos obtenido de pago-service
curl http://localhost:8070/api/v1/pedidos/consolidado-cliente/1
```

## Pruebas con Postman

1. Crear una colección **pago-service-grupo01**.
2. Agregar requests tipo **GET** (sin body ni headers):
   - `http://localhost:8070/api/v1/pagos/pedido/100` → Status **200**, `estadoPago = APROBADO`
   - `http://localhost:8070/api/v1/pagos/pedido/999` → Status **404**
   - `http://localhost:8070/api/v1/pagos/cliente/1` → Status **200**, arreglo de 3 elementos
   - `http://localhost:8070/api/v1/pedidos/verificar-y-completar/100` → `estadoPedido = COMPLETADO`
3. Presionar **Send** y comparar con los resultados esperados.

## Problemas comunes

- **503 / 404 en el Gateway**: el servicio aún no se registra en Eureka; espera 30 s y reintenta.
- **Puerto 8072 ocupado**: detén el proceso anterior (`lsof -i :8072`).
