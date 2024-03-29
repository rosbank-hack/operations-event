package operations.event.api.entity;

import com.google.common.base.MoreObjects;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Accessors(chain = true)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Table(name = "operation", uniqueConstraints = @UniqueConstraint(columnNames = {"operation_id", "service"}))
public class OperationEntity
        implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "operation_id", nullable = false)
    private String operationId;

    @Column(name = "service", nullable = false)
    private String service;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "status")
    private String status;

    @Column(name = "extended_status")
    private String extendedStatus;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "source_id")
    private String sourceId;

    @Column(name = "mcc")
    private String mcc;

    @Column(name = "extra", length = 4096)
    private String extra;

    @Column(name = "ready")
    private boolean ready;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationEntity operationEntity = (OperationEntity) o;
        return new EqualsBuilder()
                .append(operationId, operationEntity.operationId)
                .append(name, operationEntity.name)
                .append(date, operationEntity.date)
                .append(status, operationEntity.status)
                .append(extendedStatus, operationEntity.extendedStatus)
                .append(amount, operationEntity.amount)
                .append(currency, operationEntity.currency)
                .append(mcc, operationEntity.mcc)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(operationId)
                .append(name)
                .append(date)
                .append(status)
                .append(extendedStatus)
                .append(amount)
                .append(currency)
                .append(mcc)
                .toHashCode();
    }

    @Override
    public String toString() {
        return MoreObjects
                .toStringHelper(this)
                .add("operationId", operationId)
                .add("name", name)
                .add("date", date)
                .add("status", status)
                .add("extendedStatus", extendedStatus)
                .add("amount", amount)
                .add("currency", currency)
                .add("mcc", mcc)
                .add("ready", ready)
                .toString();
    }
}
