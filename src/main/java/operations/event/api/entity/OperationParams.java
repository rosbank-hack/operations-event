package operations.event.api.entity;

import com.google.common.base.MoreObjects;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "operation_params")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class OperationParams
        implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "service", nullable = false)
    private String service;

    @Column(name = "operation_id", nullable = false)
    private UUID operationId;

    @Type(type = "jsonb")
    @Column(name = "required_services", nullable = false, columnDefinition = "jsonb")
    private List<String> requiredServices;

    @Type(type = "jsonb")
    @Column(name = "received_from", nullable = false, columnDefinition = "jsonb")
    private List<String> receivedFrom;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OperationParams that = (OperationParams) o;
        return new EqualsBuilder()
                .append(id, that.id)
                .append(service, that.service)
                .append(operationId, that.operationId)
                .append(requiredServices, that.requiredServices)
                .append(receivedFrom, that.receivedFrom)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(service)
                .append(operationId)
                .append(requiredServices)
                .append(receivedFrom)
                .toHashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("service", service)
                .add("operationId", operationId)
                .add("requiredServices", requiredServices)
                .add("receivedFrom", receivedFrom)
                .toString();
    }
}
