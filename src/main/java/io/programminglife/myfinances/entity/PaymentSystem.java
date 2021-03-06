package io.programminglife.myfinances.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="payment_system")
@Getter
@Setter
@NoArgsConstructor
public class PaymentSystem {
    
    @Id
    @SequenceGenerator(name = "payment_system_sequence", sequenceName = "payment_system_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_system_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "label", nullable = false, unique = true, columnDefinition = "TEXT")
    private String label;

    public PaymentSystem(String label) {
        this.label = label;
    }

}