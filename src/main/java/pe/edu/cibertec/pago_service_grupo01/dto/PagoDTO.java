package pe.edu.cibertec.pago_service_grupo01.dto;

import java.time.LocalDateTime;

public class PagoDTO {
    private Long idPago;
    private Long idPedido;
    private String metodoPago;
    private Double montoPagado;
    private LocalDateTime fechaPago;
    private String codigoAutorizacion;
    private String estadoPago;

    public PagoDTO() {
    }

    public PagoDTO(
            Long idPago,
            Long idPedido,
            String metodoPago,
            Double montoPagado,
            LocalDateTime fechaPago,
            String codigoAutorizacion,
            String estadoPago
    ) {
        this.idPago = idPago;
        this.idPedido = idPedido;
        this.metodoPago = metodoPago;
        this.montoPagado = montoPagado;
        this.fechaPago = fechaPago;
        this.codigoAutorizacion = codigoAutorizacion;
        this.estadoPago = estadoPago;
    }

    public Long getIdPago() {
        return idPago;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(Double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getCodigoAutorizacion() {
        return codigoAutorizacion;
    }

    public void setCodigoAutorizacion(String codigoAutorizacion) {
        this.codigoAutorizacion = codigoAutorizacion;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }
}
