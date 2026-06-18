package larionov.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@Table(name = "transactions")
public class TransactionEntity extends BaseEntity {

    @NotNull
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "sender_account_id",
            nullable = false
    )
    private AccountEntity senderAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "receiver_account_id",
            nullable = false
    )
    private AccountEntity receiverAccount;

}
